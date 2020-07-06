package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.RenZhengModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng0_BeginActivity extends BaseActivity {

    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    private SpImp spImp;
    private String sBindStaus = "";// 0 未上传  1审核中  2待授权  3已授权  4失败
    private String sRenZhengFeiStaus = "";// 0 未交费  1已缴费  2 不需缴费
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng0__begin);

        ButterKnife.bind(this);
        spImp = new SpImp(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViseHttp.POST(NetConfig.wxQuery)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("saasdasd",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                RenZhengModel model = gson.fromJson(data,RenZhengModel.class);
                                sBindStaus = model.getObj().getSign();
//                                sBindStaus = "2";
                                sRenZhengFeiStaus = model.getObj().getPay();
//                                sRenZhengFeiStaus = "1";
                                switch (sBindStaus){
                                    case "0":
                                        tvMessage.setText("您还没有通过认证\n认证之后开通店铺");
                                        tvNext.setVisibility(View.VISIBLE);
                                        tvNext.setText("开始认证");
                                        break;
                                    case "1":
                                        if (sRenZhengFeiStaus.equals("0")){
                                            tvMessage.setText("缴纳微信商户认证费");
                                            tvNext.setText("去缴纳");
                                            tvNext.setVisibility(View.VISIBLE);
                                        }else if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
                                            tvMessage.setText("审核中。。。");
                                            tvNext.setVisibility(View.GONE);
                                        }
                                        break;
                                    case "2":
                                        if (sRenZhengFeiStaus.equals("0")){
                                            tvMessage.setText("缴纳微信商户认证费");
                                            tvNext.setText("去缴纳");
                                            tvNext.setVisibility(View.VISIBLE);
                                        }else if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
                                            tvMessage.setText("资料审核已通过\n您还没有微信授权\n微信授权后开通店铺");
                                            tvNext.setVisibility(View.VISIBLE);
                                            tvNext.setText("去授权");
                                        }
                                        break;
                                    case "3":
                                        if (sRenZhengFeiStaus.equals("0")){
                                            tvMessage.setText("缴纳微信商户认证费");
                                            tvNext.setText("去缴纳");
                                            tvNext.setVisibility(View.VISIBLE);
                                        }else if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
                                            tvMessage.setText("验证成功！");
                                            tvNext.setVisibility(View.VISIBLE);
                                            tvNext.setText("完 成");
                                            spImp.setIfSign("1");
                                        }
                                        break;
                                    case "4":
                                        tvMessage.setText("认证失败，请重新上传资料！");
                                        tvNext.setVisibility(View.VISIBLE);
                                        tvNext.setText("重新认证");
                                        break;
                                }
                            }else {
//                                tvMessage.setText("店铺信息和身份信息未完善");
//                                tvNext.setText("去完善");
//                                bindStatus = 400 ;
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng0_BeginActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        switch (sBindStaus){
            case "0":
                RenZheng1_EditInfoActivity.openActivity(this);
                break;
            case "1":
                if (sRenZhengFeiStaus.equals("0")){
                    RenZheng3_RenZhengFeiActivity.openActivity(RenZheng0_BeginActivity.this);
                }
                break;
            case "2":
                if (sRenZhengFeiStaus.equals("0")){
                    RenZheng3_RenZhengFeiActivity.openActivity(RenZheng0_BeginActivity.this);
                }else {
                    RenZheng2_BindWXActivity.openActivity(RenZheng0_BeginActivity.this);
                }
                break;
            case "3":
                if (sRenZhengFeiStaus.equals("0")){
                    RenZheng3_RenZhengFeiActivity.openActivity(RenZheng0_BeginActivity.this);
                }else {
                    onBackPressed();
                }
                break;
            case "4":
                RenZheng1_EditInfoActivity.openActivity(this);
                break;
        }
    }
}
