package com.yiwo.fuzhoudian.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.adapter.ZAndScAdapter;
import com.yiwo.fuzhoudian.model.ZAndScModel;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZAndScActivity extends BaseActivity {

    private Context context = ZAndScActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private ZAndScAdapter adapter;
    private List<ZAndScModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zand_sc);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ZAndScActivity.this);
        spImp = new SpImp(context);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.zanAndSoucang)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.zanAndSoucang))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ZAndScModel model = gson.fromJson(data, ZAndScModel.class);
                                mList = model.getObj();
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(manager);
                                adapter = new ZAndScAdapter(mList);
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    @OnClick({R.id.rl_back,R.id.rl_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;

            case R.id.rl_clear:
                clearDatas();
                break;
        }
    }

    private void clearDatas() {
        ViseHttp.POST(NetConfig.clearAdmire)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.clearAdmire))
                .addParam("userID", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(context,"已清空");
                                mList.clear();
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

}
