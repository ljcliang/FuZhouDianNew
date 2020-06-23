package com.yiwo.fuzhoudian.pages.webpages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.model.TiXianResultModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.TiXianShuoMingActivity;
import com.yiwo.fuzhoudian.pages.renzheng.RenZheng0_BeginActivity;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiaoShouMingXiActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;
    @BindView(R.id.titleTv)
    TextView mTitleTv;
    @BindView(R.id.webView)
    WebView mWebView;
    private SpImp spImp;
    private Dialog weiBoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_shou_ming_xi);
        ButterKnife.bind(this);
        initIntentSonic(getIntent().getStringExtra("url"), mWebView);
        spImp = new SpImp(this);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, XiaoShouMingXiActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_return,R.id.rl_tixian})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
            case R.id.rl_tixian:
                AlertDialog.Builder builder = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                builder.setMessage("确定提现？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tiXian();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(XiaoShouMingXiActivity.this, TiXianShuoMingActivity.class);
                        startActivity(intent);
                    }
                }).show();

                break;
        }
    }

    private void tiXian() {
        weiBoDialog = WeiboDialogUtils.createLoadingDialog(this,"加载中...");
        ViseHttp.POST(NetConfig.getMoneyFromWx)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getMoneyFromWx))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        WeiboDialogUtils.closeDialog(weiBoDialog);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                TiXianResultModel model = gson.fromJson(data,TiXianResultModel.class);
                                switch (model.getObj().getStatus()){
                                    case "0"://未认证
                                        AlertDialog.Builder builder = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                                        builder.setMessage("您还没有完成商户认证")
                                                .setPositiveButton("去认证", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        RenZheng0_BeginActivity.openActivity(XiaoShouMingXiActivity.this);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "1"://余额不足
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                                        builder1.setMessage("提现余额不足")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "2"://mei有绑定银行卡
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                                        builder2.setMessage("您还没有绑定银行卡")
                                                .setPositiveButton("查看绑定步骤", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent();
                                                        intent.setClass(XiaoShouMingXiActivity.this, TiXianShuoMingActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "3"://绑定银行卡youwu
                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                                        builder3.setMessage("您绑定的银行卡信息有误")
                                                .setPositiveButton("查看绑定步骤", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent();
                                                        intent.setClass(XiaoShouMingXiActivity.this, TiXianShuoMingActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "4"://提示成功
                                        AlertDialog.Builder builder4 = new AlertDialog.Builder(XiaoShouMingXiActivity.this);
                                        builder4.setMessage("提现申请成功，将在24小时内打入绑定银行账户，请注意查收")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(weiBoDialog);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();//返回上个页面
            return;
        } else {
            finish();
        }
    }
}
