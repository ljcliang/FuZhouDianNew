package com.yiwo.fuzhoudian.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeiSongSettingActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pei_song_setting);
        ButterKnife.bind(this);
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
                if (daoodianziti){
                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_true);
                }else {
                    ivCkDaodian.setImageResource(R.mipmap.checkbox_black_false);
                }
                daoodianziti = !daoodianziti;
                break;
            case R.id.ll_peisongdaojia:
                if (peisongdaojia){
                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_true);
                }else {
                    ivCkPeisong.setImageResource(R.mipmap.checkbox_black_false);
                }
                peisongdaojia = !peisongdaojia;
                break;
            case R.id.tv_btn_sure:
                break;
        }
    }
}
