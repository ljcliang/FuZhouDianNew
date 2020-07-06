package com.yiwo.fuzhoudian.pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.CityModel;
import com.yiwo.fuzhoudian.model.GuanLianShangPinModel;
import com.yiwo.fuzhoudian.model.MyVideosModel;
import com.yiwo.fuzhoudian.network.ActivityConfig;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.wangyiyunshipin.TakeVideoFragment_new;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditVideoTitleActivity extends BaseActivity {

    @BindView(R.id.activity_up_load_video_tv_title)
    EditText editText;
    @BindView(R.id.activity_up_load_video_tv_title_num)
    TextView tv_num;
    @BindView(R.id.activity_create_friend_remember_tv_activity_city)
    EditText tvCity;
    @BindView(R.id.activity_create_friend_remember_tv_active_title)
    TextView tvAboutGoods;
    private static final int REQUEST_CODE_GET_CITY = 1;
    private static final int REQUEST_CODE_SUO_SHU_HUO_DONG = 2;

    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";

    private MyVideosModel.ObjBean videoItem;
    private SpImp spImp;

    public static final int RESULT_CODE =  1001;
    public static final int REQUEST_CODE =  1;
    public static final String RESULT_CHANGE_POSITION_KEY = "videoItem_posion";
    public int RESULT_CHANGE_POSITION = -1;
    public static final String RESULT_CHANGE_DATA = "videoItem";
//    private Dialog dialog;
    public  static void startEditVideoInfoActivity(Activity context, int posion, MyVideosModel.ObjBean videos){
        Intent intent = new Intent(context, EditVideoTitleActivity.class);
        intent.putExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM,videos);
        intent.putExtra(RESULT_CHANGE_POSITION_KEY,posion);
        context.startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video_info);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(EditVideoTitleActivity.this);
        editText.addTextChangedListener(textTitleWatcher);
        videoItem = (MyVideosModel.ObjBean) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
        RESULT_CHANGE_POSITION = getIntent().getIntExtra(RESULT_CHANGE_POSITION_KEY,-1);
        editText.setText(videoItem.getVname());
        tvCity.setText(videoItem.getAddress());
        tvAboutGoods.setText(videoItem.getGname());
        yourChoiceActiveName = videoItem.getGname();
        yourChoiceActiveId = videoItem.getVID();
    }
    @OnClick({R.id.rl_back,R.id.activity_up_load_video_rl_complete,R.id.rl_choose_address,R.id.activity_create_friend_remember_rl_active_title})
     public void onClick(final View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_choose_address:
                Intent it = new Intent(EditVideoTitleActivity.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "createYouJi");
                startActivityForResult(it, REQUEST_CODE_GET_CITY);
                break;
            case R.id.activity_create_friend_remember_rl_active_title:
                //活动标题
                Intent it_suoshu = new Intent(EditVideoTitleActivity.this, GuanLianShangPinActivity.class);
                startActivityForResult(it_suoshu, REQUEST_CODE_SUO_SHU_HUO_DONG);
                break;
            case R.id.activity_up_load_video_rl_complete:
                ViseHttp.POST(NetConfig.videoEdit)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.videoEdit))
                        .addParam("vID",videoItem.getVID())
                        .addParam("userID",spImp.getUID())
                        .addParam("vname",editText.getText().toString())
                        .addParam("about_good",yourChoiceActiveId)
                        .addParam("address",tvCity.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        toToast(EditVideoTitleActivity.this,"修改成功");
                                        Intent intent = new Intent();
                                        videoItem.setVname(editText.getText().toString());
                                        videoItem.setAddress(tvCity.getText().toString());
                                        videoItem.setGid(yourChoiceActiveId);
                                        videoItem.setGname(yourChoiceActiveName);
                                        intent.putExtra(RESULT_CHANGE_DATA,videoItem);
                                        intent.putExtra(RESULT_CHANGE_POSITION_KEY,RESULT_CHANGE_POSITION);
                                        setResult(RESULT_CODE,intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(EditVideoTitleActivity.this,"修改失败");
                            }
                        });
                break;
        }
    }
    TextWatcher textTitleWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tv_num.setText(temp.length()+"/30");
            if(temp.length()>=30){
                Toast.makeText(EditVideoTitleActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GET_CITY && data != null && resultCode == 1) {//选择城市
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            tvCity.setText(model.getName());
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 2) {//重置
            tvCity.setText("");
            tvCity.setHint("请选择或输入活动地点");
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 3) {//国际城市
            String city = data.getStringExtra("city");
            tvCity.setText(city);
        }
        if (requestCode == REQUEST_CODE_SUO_SHU_HUO_DONG && resultCode == 1){
            GuanLianShangPinModel.ObjBean bean = (GuanLianShangPinModel.ObjBean) data.getSerializableExtra("suoshuhuodong");
            yourChoiceActiveName = bean.getGoodsName();
            yourChoiceActiveId = bean.getGid();
            tvAboutGoods.setText(yourChoiceActiveName);
        }
    }
}
