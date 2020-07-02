package com.yiwo.fuzhoudian.pages;

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

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.CityModel;
import com.yiwo.fuzhoudian.model.GuanLianShangPinModel;
import com.yiwo.fuzhoudian.model.MyVideosModel;
import com.yiwo.fuzhoudian.network.ActivityConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.wangyiyunshipin.TakeVideoFragment_new;

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

    private MyVideosModel videoItem;
    private SpImp spImp;
//    private Dialog dialog;
    private ProgressDialog progressDialog;
    private PopupWindow popupWindow;
    public  static void startEditVideoInfoActivity(Context context, MyVideosModel.ObjBean videos){
        Intent intent = new Intent(context, EditVideoTitleActivity.class);
        intent.putExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM,videos);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video_info);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(EditVideoTitleActivity.this);
        spImp = new SpImp(EditVideoTitleActivity.this);
        if (!TextUtils.isEmpty(spImp.getLastCreateVideoAddress())){
            tvCity.setText(spImp.getLastCreateVideoAddress());
        }else {
            tvCity.setText(spImp.getUserName());
        }
        editText.addTextChangedListener(textTitleWatcher);
        videoItem = (MyVideosModel) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
    }
    @OnClick({R.id.rl_back,R.id.activity_up_load_video_rl_complete,R.id.rl_choose_address,R.id.activity_create_friend_remember_rl_active_title})
     public void onClick(View view){
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
