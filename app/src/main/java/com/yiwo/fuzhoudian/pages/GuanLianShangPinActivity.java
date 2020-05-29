package com.yiwo.fuzhoudian.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.GuanLianShangPinListAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.GuanLianShangPinModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanLianShangPinActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.edt)
    EditText editText;
    @BindView(R.id.tv_sousuo)
    TextView btn_sousuo;
    private  List<GuanLianShangPinModel.ObjBean> dataList = new ArrayList<>();

    GuanLianShangPinListAdapter adapter;
    SpImp spImp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_lian_shang_pin);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initRv();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.shopAboutGoods)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.shopAboutGoods))
                .addParam("searchword",editText.getText().toString())
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GuanLianShangPinModel model = gson.fromJson(data, GuanLianShangPinModel.class);
                                dataList.clear();
                                dataList.addAll(model.getObj());
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

    public void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(GuanLianShangPinActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new GuanLianShangPinListAdapter(dataList);
        adapter.setListionner(new GuanLianShangPinListAdapter.ItemClickListionner() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent();
                intent.putExtra("suoshuhuodong",dataList.get(postion));
                setResult(1,intent);
                finish();
            }
        });
        rv.setAdapter(adapter);
    }

    @OnClick({R.id.rl_back,R.id.tv_sousuo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sousuo:
                initData();
                break;
        }
    }

}
