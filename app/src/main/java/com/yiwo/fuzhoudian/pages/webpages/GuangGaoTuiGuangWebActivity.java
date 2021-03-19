package com.yiwo.fuzhoudian.pages.webpages;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.imagepreview.Consts;
import com.yiwo.fuzhoudian.imagepreview.ImagePreviewActivity;
import com.yiwo.fuzhoudian.model.OrderToPayModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.network.UMConfig;
import com.yiwo.fuzhoudian.pages.renzheng.RenZheng3_RenZhengFeiActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuangGaoTuiGuangWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_set_return)
    RelativeLayout rlSetReturn;
    @BindView(R.id.webView)
    WebView webView;

    private Dialog dialog;
    private  String uid = "";
    private String url ="";
    private SpImp spImp;
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chong_zhi_web);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(GuangGaoTuiGuangWebActivity.this, null);
        spImp = new SpImp(this);
        uid =spImp.getUID();
        //https://fzd.91yiwo.com/index.php/action/ac_ad/index?uid=用户id
        url = NetConfig.BaseUrl+"action/ac_ad/index?uid="+uid;
        initIntentSonic(url,webView);
        webView.addJavascriptInterface(new AndroidInterface(),"android");//交互
    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,GuangGaoTuiGuangWebActivity.class);
        context.startActivity(intent);
    }
    public class AndroidInterface extends Object{
        //gotocz(充值金额，支付方式)
        @JavascriptInterface
        public void gotocz(String payMoney,String type){
            dialog = WeiboDialogUtils.createLoadingDialog(GuangGaoTuiGuangWebActivity.this,"加载中...");
            ViseHttp.POST(NetConfig.adOrder)
                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.adOrder))
                    .addParam("paytype", type)
                    .addParam("money", payMoney)
                    .addParam("uid", spImp.getUID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject pay = new JSONObject(data);
                                if (pay.getInt("code") == 100) {
                                    Gson gson1 = new Gson();
                                    OrderToPayModel model1 = gson1.fromJson(data, OrderToPayModel.class);
                                    wxPay(model1.getObj());
                                    toToast(GuangGaoTuiGuangWebActivity.this,model1.getMessage());
                                    WeiboDialogUtils.closeDialog(dialog);
                                }else {
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            toToast(GuangGaoTuiGuangWebActivity.this,errMsg);
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    });
        }
    }
    @OnClick({R.id.rl_set_return, R.id.webView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_set_return:
                onBackPressed();
                break;
            case R.id.webView:
                break;
        }
    }
    public void wxPay(OrderToPayModel.ObjBean model){
        api.registerApp(UMConfig.WECHAT_APPID);
        PayReq req = new PayReq();
        req.appId = model.getAppId();
        req.partnerId = model.getPartnerId();
        req.prepayId = model.getPrepayId();
        req.nonceStr = model.getNonceStr();
        req.timeStamp = model.getTimestamp()+"";
        req.packageValue = model.getPackageX();
        req.sign = model.getSign();
        req.extData = "app data";
        api.sendReq(req);
    }
}
