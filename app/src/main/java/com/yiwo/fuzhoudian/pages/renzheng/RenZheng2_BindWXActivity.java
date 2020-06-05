package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng2_BindWXActivity extends BaseActivity {

    @BindView(R.id.tv_bind_wx)
    TextView tvBindWx;
    @BindView(R.id.iv_yingyezhizhao)
    ImageView ivYingyezhizhao;
    @BindView(R.id.rl_bind_wx)
    RelativeLayout rlBindWx;
    @BindView(R.id.tv_next)
    TextView tvNext;


    private UMShareAPI mShareAPI;
    private UMAuthListener umAuthListener;

    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng2__bind_w_x);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initUM();
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng2_BindWXActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_bind_wx, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bind_wx:
                mShareAPI.getPlatformInfo(RenZheng2_BindWXActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//绑定微信
                break;
            case R.id.tv_next:
                break;
        }
    }
    private void initUM() {
        mShareAPI = UMShareAPI.get(RenZheng2_BindWXActivity.this);
        umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {}
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                final String wx_unionid ;
                if (data!=null && data.size()>0){
                    wx_unionid = data.get("unionid");
                    for (Map.Entry<String,String> entry : data.entrySet()){
                        Log.d("weixindenglu::://KEY:",entry.getKey()+"||Value:"+entry.getValue());
                    }
                    Log.d("weixindenglu:UNIONID",wx_unionid);
//                    ViseHttp.POST(NetConfig.addWXUnionid)
//                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.addWXUnionid))
//                            .addParam("unionid",wx_unionid)
//                            .addParam("uid",spImp.getUID())
//                            .request(new ACallback<String>() {
//                                @Override
//                                public void onSuccess(String data) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(data);
//                                        if (jsonObject.getInt("code") == 200){
//                                            toToast(RenZheng2_BindWXActivity.this,"绑定成功！");
//                                            if (TextUtils.isEmpty(spImp.getWXUnionID())){
//                                                spImp.setWXUnionID(wx_unionid);
//                                            }
//                                            Log.d("weixindenglu:UNSPIMP:",spImp.getWXUnionID());
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFail(int errCode, String errMsg) {
//
//                                }
//                            });
                }
                // Logger.e("openid: " + data.get("uid"));
                // Logger.e("昵称: " + data.get("name"));
                // Logger.e("头像: " + data.get("iconurl"));
                // Logger.e("性别: " + data.get("gender"));
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        };
    }
}
