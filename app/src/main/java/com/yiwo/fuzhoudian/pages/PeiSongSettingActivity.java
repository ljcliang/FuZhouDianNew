package com.yiwo.fuzhoudian.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.PeiSongSettingModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeiSongSettingActivity extends BaseActivity {

    @BindView(R.id.rl_set_return)
    RelativeLayout rlSetReturn;
    @BindView(R.id.iv_ck_daodian)
    ImageView ivCkDaodian;
    @BindView(R.id.ll_daodianziti)
    LinearLayout llDaodianziti;
    @BindView(R.id.iv_ck_peisong)
    ImageView ivCkPeisong;
    @BindView(R.id.ll_peisongdaojia)
    LinearLayout llPeisongdaojia;
    @BindView(R.id.edt_peisongfei)
    EditText edtPeisongfei;
    @BindView(R.id.edt_peisongfanwei)
    EditText edtPeisongfanwei;
    @BindView(R.id.edt_gouman)
    EditText edtGouman;
    @BindView(R.id.tv_btn_sure)
    TextView tvBtnSure;
    private boolean daoodianziti = false;
    private boolean peisongdaojia  = false;

    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pei_song_setting);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getSendSet)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.getSendSet))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PeiSongSettingModel model =  gson.fromJson(data, PeiSongSettingModel.class);
                                peisongdaojia = model.getObj().getPs().equals("0");
                                if (peisongdaojia){
                                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_true);
                                }else {
                                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_false);
                                }
                                daoodianziti = model.getObj().getZt().equals("0");
                                if (daoodianziti){
                                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_true);
                                }else {
                                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_false);
                                }
                                edtGouman.setText(model.getObj().getNoMoney());
                                edtPeisongfei.setText(model.getObj().getMoney());
                                edtPeisongfanwei.setText(model.getObj().getCanGet());
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

    @OnClick({R.id.rl_set_return, R.id.ll_daodianziti, R.id.ll_peisongdaojia, R.id.tv_btn_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_set_return:
                onBackPressed();
                break;
            case R.id.ll_daodianziti:
//                if (!daoodianziti){
//                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_true);
//                }else {
//                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_false);
//                }
//                daoodianziti = !daoodianziti;
                break;
            case R.id.ll_peisongdaojia:
                if (!peisongdaojia){
                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_true);
                }else {
                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_false);
                }
                peisongdaojia = !peisongdaojia;
                break;
            case R.id.tv_btn_sure:
                save();
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(edtPeisongfanwei.getText().toString())){
            toToast(this,"请输入配送范围");
            return;
        }
        if (TextUtils.isEmpty(edtPeisongfei.getText().toString())){
            toToast(this,"请输入配送费");
            return;
        }
        ViseHttp.POST(NetConfig.sendSet)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sendSet))
                .addParam("uid", spImp.getUID())
                .addParam("money",edtPeisongfei.getText().toString())
                .addParam("noMoney",edtGouman.getText().toString())
                .addParam("canGet",edtPeisongfanwei.getText().toString())
//                .addParam("zt",daoodianziti ? "0" : "1")
                .addParam("zt","0")
                .addParam("ps",peisongdaojia ? "0" : "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(PeiSongSettingActivity.this,"保存成功！");
                                finish();
                            }else {
                                toToast(PeiSongSettingActivity.this,"保存失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(PeiSongSettingActivity.this,"保存失败");
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(PeiSongSettingActivity.this,"保存失败");
                    }
                });
    }
}
