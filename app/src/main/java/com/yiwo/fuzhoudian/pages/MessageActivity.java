package com.yiwo.fuzhoudian.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.HomeMessageCenterModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

    private Context context = MessageActivity.this;

    @BindView(R.id.tv1_tv)
    TextView tvSystemContent;
    @BindView(R.id.tv_tongzhi_time)
    TextView tvSystemTime;
    @BindView(R.id.iv_hot1)
    ImageView ivSystem;
    @BindView(R.id.tv2_tv)
    TextView tvHotContent;
    @BindView(R.id.tv_remen_time)
    TextView tvHotTime;
    @BindView(R.id.iv_hot2)
    ImageView ivHot;
    @BindView(R.id.tv3_tv)
    TextView tvPrivateMessageContent;
    @BindView(R.id.tv_sixin_time)
    TextView tvPrivateMessageTime;
    @BindView(R.id.iv_hot3)
    ImageView ivPrivateMessage;

    //好友消息
    @BindView(R.id.tv4_tv)
    TextView tv_friend_content;
    @BindView(R.id.tv_friend_time)
    TextView tv_friend_time;
    @BindView(R.id.iv_hot4)
    ImageView iv_friend_new_message;
    private SpImp spImp;

    @BindView(R.id.iv_point_zc)
    ImageView ivPointZC;
    @BindView(R.id.iv_point_yq)
    ImageView ivPointYQ;
    @BindView(R.id.iv_point_pa)
    ImageView ivPointPA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(MessageActivity.this);
        spImp = new SpImp(context);

        initData();

    }

    private void initData() {

        ViseHttp.POST(NetConfig.homeMessageCenterUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homeMessageCenterUrl))
                .addParam("user_id", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("22222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomeMessageCenterModel model = gson.fromJson(data, HomeMessageCenterModel.class);

                                if (model.getObj().getCollect().getType().equals("1")){
                                    ivPointZC.setVisibility(View.VISIBLE);
                                }
                                if (model.getObj().getComment().getType().equals("1")){
                                    ivPointPA.setVisibility(View.VISIBLE);
                                }
                                if (model.getObj().getYq().getType().equals("1")){
                                    ivPointYQ.setVisibility(View.VISIBLE);
                                }

                                tvHotContent.setText(model.getObj().getHot().getMessage());
                                tvHotTime.setText(model.getObj().getHot().getTime());
                                if (model.getObj().getHot().getType().equals("1")) {
                                    ivHot.setVisibility(View.VISIBLE);
                                }
                                tvSystemContent.setText(model.getObj().getSystem().getMessage());
                                tvSystemTime.setText(model.getObj().getSystem().getTime());
                                if (model.getObj().getSystem().getType().equals("1")) {
                                    ivSystem.setVisibility(View.VISIBLE);
                                }
//                                tvInvitationContent.setText(model.getObj().getYq().getMessage());
//                                tvInvitationTime.setText(model.getObj().getYq().getTime());
//                                if (model.getObj().getYq().getType().equals("1")) {
//                                    ivInvitation.setVisibility(View.VISIBLE);
//                                }
//                                tvCommentContent.setText(model.getObj().getComment().getMessage());
//                                tvCommentTime.setText(model.getObj().getComment().getTime());
//                                if (model.getObj().getComment().getType().equals("1")) {
//                                    ivComment.setVisibility(View.VISIBLE);
//                                }
                                //好友消息
                                tv_friend_content.setText(model.getObj().getFriends().getMessage());
                                tv_friend_time.setText(model.getObj().getFriends().getTime());
                                if (model.getObj().getFriends().getType().equals("1")) {
                                    iv_friend_new_message.setVisibility(View.VISIBLE);
                                }
                                //私信消息
                                tvPrivateMessageContent.setText(model.getObj().getPrivate().getMessage());
                                tvPrivateMessageTime.setText(model.getObj().getPrivate().getTime());
                                if (model.getObj().getPrivate().getType().equals("1")) {
                                    ivPrivateMessage.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.rl_back, R.id.rl_hot_message, R.id.rl_tongzhi_message, R.id.rl_shoudaoyaoqing, R.id.rl_pinglun,
            R.id.rl_friend_message,R.id.rl_private_message,R.id.rl_z_and_sc})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_z_and_sc:
                ivPointZC.setVisibility(View.GONE);
                intent.setClass(context, ZAndScActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_hot_message:
                ivHot.setVisibility(View.GONE);
                intent.setClass(context, MessageViewActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.rl_tongzhi_message:
                ivSystem.setVisibility(View.GONE);
                intent.setClass(context, MessageViewActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
//            case R.id.rl_shoudaoyaoqing:
////                ivPointYQ.setVisibility(View.GONE);
////                intent.setClass(context, MessageInvitationActivity.class);
////                startActivity(intent);
//                break;
//            case R.id.rl_pinglun:
//                ivPointPA.setVisibility(View.GONE);
//                intent.setClass(context, PinglunAtActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.rl_private_message:
//                ivPrivateMessage.setVisibility(View.GONE);
//                intent.setClass(context,PrivateMessageActivity.class);
//                startActivity(intent);
//                break;
            case R.id.rl_friend_message:
                iv_friend_new_message.setVisibility(View.GONE);
                intent.setClass(context,MessageFriendsActivity.class);
                startActivity(intent);
                break;
        }
    }

}
