package com.yiwo.fuzhoudian.pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.FeedBackAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.FeedBackModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    SpImp spImp;
    @BindView(R.id.activity_feedback_et_content)
    EditText contentEt;
    @BindView(R.id.activity_feedback_bt_submit)
    Button submitBt;
    @BindView(R.id.activity_feedback_rv)
    RecyclerView feedbackRv;
    FeedBackAdapter adapter;
    private List<FeedBackModel.ObjBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp =new SpImp(this);

        initData();
    }

    @OnClick({R.id.activity_feedback_rl_back,R.id.activity_feedback_bt_submit})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.activity_feedback_rl_back:
                finish();
                break;
            case R.id.activity_feedback_bt_submit:
//                toToast(this,"提交");
                submit();
                break;
        }
    }

    public void initData(){
        ViseHttp.POST(NetConfig.historicalFeedBackUrl)
                .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.historicalFeedBackUrl))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i("101101",data);
                        FeedBackModel model = new Gson().fromJson(data,FeedBackModel.class);
                        if (model.getCode()==200){
                            list = model.getObj();
                            LinearLayoutManager manager = new LinearLayoutManager(FeedbackActivity.this) {
                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            };
                            manager.setOrientation(LinearLayoutManager.VERTICAL);
                            feedbackRv.setLayoutManager(manager);
                            adapter = new FeedBackAdapter(list);
                            feedbackRv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    public void initList(List<FeedBackModel.ObjBean> data){

    }

    public void submit(){
        if(!StringUtils.isEmpty(contentEt.getText().toString())){
            ViseHttp.POST(NetConfig.submitFeedBackUrl)
                    .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.submitFeedBackUrl))
                    .addParam("uid",spImp.getUID())
                    .addParam("content",contentEt.getText().toString())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.i("110011",data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    toToast(FeedbackActivity.this,jsonObject.getString("message"));
                                    ViseHttp.POST(NetConfig.historicalFeedBackUrl)
                                            .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.historicalFeedBackUrl))
                                            .addParam("uid",spImp.getUID())
                                            .request(new ACallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {
                                                    Log.i("101101",data);
                                                    FeedBackModel model = new Gson().fromJson(data,FeedBackModel.class);
                                                    if (model.getCode()==200){
                                                        contentEt.setText("");
                                                        list.clear();
                                                        list.addAll(model.getObj());
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }

                                                @Override
                                                public void onFail(int errCode, String errMsg) {

                                                }
                                            });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
            toToast(FeedbackActivity.this,"内容不能为空");
        }

    }
}
