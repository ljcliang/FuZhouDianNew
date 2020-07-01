package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.RenZhengModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.AndTools;
import com.yiwo.fuzhoudian.utils.QRCodeUtil;
import com.yiwo.fuzhoudian.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng2_BindWXActivity extends BaseActivity {

    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;
    @BindView(R.id.tv_tishi)
    TextView tvTiShi;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_finsh)
    TextView tvFinsh;
    private SpImp spImp;

    private String sBindStaus = ""; // 0 未上传  1审核中  2待授权  3已授权  4失败
    private String sRenZhengFeiStaus = "";
    private Bitmap bitmapEWM;
    private String renzhengUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng2__bind_w_x);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkWx();
    }

    private void checkWx() {
        ViseHttp.POST(NetConfig.wxQuery)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                RenZhengModel model = gson.fromJson(data, RenZhengModel.class);
                                sBindStaus = model.getObj().getSign();
                                sRenZhengFeiStaus = model.getObj().getPay();
                                renzhengUrl = model.getObj().getUrl();
                                bitmapEWM = QRCodeUtil.createQRCodeBitmap(renzhengUrl,320,320);
                                Glide.with(RenZheng2_BindWXActivity.this).load(bitmapEWM).into(iv_erweima);
                                switch (sBindStaus) {
                                    case "0":
//                                    tvMessage.setText("您还没有通过认证\\n认证之后开通店铺");
//                                    tvNext.setVisibility(View.VISIBLE);
//                                    tvNext.setText("开始认证");
                                        break;
                                    case "1":
//                                    tvMessage.setText("审核中。。。");
//                                    tvNext.setVisibility(View.GONE);
                                        break;
                                    case "2":
//                                    tvMessage.setText("您还没有微信授权\\n微信授权后开通店铺");
                                        tvTiShi.setText("保存二维码图片到相册，在微信中识别打开，确认授权即可，或者复制授权链接，在微信中粘贴链接（可发送给文件传输助手或自己等），打开确认授权即可。");
                                        tvFinsh.setVisibility(View.GONE);
                                        tvSave.setVisibility(View.VISIBLE);
                                        tvCopy.setVisibility(View.VISIBLE);
                                        break;
                                    case "3":
                                        if (sRenZhengFeiStaus.equals("0")){
                                            tvTiShi.setText("未缴纳微信认证费");
                                            tvFinsh.setText("去缴纳");
                                            tvFinsh.setVisibility(View.VISIBLE);
                                            tvSave.setVisibility(View.GONE);
                                            tvCopy.setVisibility(View.GONE);
                                        }else if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
                                            tvTiShi.setText("微信授权成功！");
                                            iv_erweima.setVisibility(View.GONE);
                                            tvFinsh.setVisibility(View.VISIBLE);
                                            tvSave.setVisibility(View.GONE);
                                            tvCopy.setVisibility(View.GONE);
                                            spImp.setIfSign("1");
                                        }
                                        break;
                                    case "4":
//                                    tvMessage.setText("认证失败，请重新上传资料！");
//                                    tvNext.setVisibility(View.VISIBLE);
//                                    tvNext.setText("重新认证");
                                        break;
                                }
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

    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng2_BindWXActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_save, R.id.tv_copy, R.id.tv_finsh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                AndTools.saveScreenShot(RenZheng2_BindWXActivity.this, bitmapEWM);
                break;
            case R.id.tv_copy:
                StringUtils.copy(renzhengUrl,RenZheng2_BindWXActivity.this);
                toToast(RenZheng2_BindWXActivity.this,"已复制到剪切板");
                break;
            case R.id.tv_finsh:
                if (sRenZhengFeiStaus.equals("0")){
                    RenZheng3_RenZhengFeiActivity.openActivity(RenZheng2_BindWXActivity.this);
                }else if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
                    onBackPressed();
                }
                break;
        }
    }
}
