package com.yiwo.fuzhoudian.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseFragment;
import com.yiwo.fuzhoudian.model.UserModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.AllRememberActivity;
import com.yiwo.fuzhoudian.pages.FaBu_XiuGaiShangPinActivity;
import com.yiwo.fuzhoudian.pages.LoginActivity;
import com.yiwo.fuzhoudian.pages.MessageActivity;
import com.yiwo.fuzhoudian.pages.MyCommentActivity;
import com.yiwo.fuzhoudian.pages.MyContactActivity;
import com.yiwo.fuzhoudian.pages.MyInformationActivity;
import com.yiwo.fuzhoudian.pages.MyPicturesActivity;
import com.yiwo.fuzhoudian.pages.MyVideosActivity;
import com.yiwo.fuzhoudian.pages.SetActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.wangyiyunshipin.VideoUpLoadListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.rl_xiaoxi)
    RelativeLayout mRlXiaoxi;
    @BindView(R.id.rl_dongtai)
    RelativeLayout mRlDongtai;
    @BindView(R.id.rl_shangpin)
    RelativeLayout mRlShangpin;
    @BindView(R.id.rl_guanzhu)
    RelativeLayout mRlGuanzhu;
    @BindView(R.id.ll_daichuli)
    LinearLayout mLlDaichuli;
    @BindView(R.id.ll_yichuli)
    LinearLayout mLlYichuli;
    @BindView(R.id.ll_yiwancheng)
    LinearLayout mLlYiwancheng;
    @BindView(R.id.ll_tuikuan)
    LinearLayout mLlTuikuan;
    @BindView(R.id.rl_bottom_1)
    RelativeLayout mRlBottom1;
    @BindView(R.id.rl_bottom_2)
    RelativeLayout mRlBottom2;
    @BindView(R.id.rl_bottom_3)
    RelativeLayout mRlBottom3;
    @BindView(R.id.rl_bottom_4)
    RelativeLayout mRlBottom4;
    @BindView(R.id.rl_bottom_5)
    RelativeLayout mRlBottom5;
    @BindView(R.id.rl_bottom_6)
    RelativeLayout mRlBottom6;
    @BindView(R.id.rl_bottom_7)
    RelativeLayout mRlBottom7;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_kinds)
    TextView mTvKinds;
    @BindView(R.id.tv_num1)
    TextView mTvNum1;
    @BindView(R.id.tv_num2)
    TextView mTvNum2;
    @BindView(R.id.tv_num3)
    TextView mTvNum3;
    @BindView(R.id.tv_num4)
    TextView mTvNum4;
    private View view;
    private Unbinder unbinder;
    private SpImp spImp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());
        return rootView;

    }

    @OnClick({R.id.iv_head, R.id.tv_level, R.id.rl_xiaoxi, R.id.rl_dongtai, R.id.rl_shangpin, R.id.rl_guanzhu,
            R.id.ll_daichuli, R.id.ll_yichuli, R.id.ll_yiwancheng, R.id.ll_tuikuan,
            R.id.rl_bottom_1, R.id.rl_bottom_2, R.id.rl_bottom_3, R.id.rl_bottom_4, R.id.rl_bottom_5, R.id.rl_bottom_6, R.id.rl_bottom_7, R.id.rl_bottom_8})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_head:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MyInformationActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_level:
                break;
            case R.id.rl_xiaoxi:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_dongtai:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), AllRememberActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_shangpin:
                intent.setClass(getContext(), FaBu_XiuGaiShangPinActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_guanzhu:
                break;
            case R.id.ll_daichuli:
                break;
            case R.id.ll_yichuli:
                break;
            case R.id.ll_yiwancheng:
                break;
            case R.id.ll_tuikuan:
                break;
            case R.id.rl_bottom_1://我的相册
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MyPicturesActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_2://我的视频
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MyVideosActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_3://通讯录
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MyContactActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_4://我的评论
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), MyCommentActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_5://浏览历史
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
//                    intent.setClass(getContext(), LookHistoryActivity.class);
//                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_6://上传列表
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    VideoUpLoadListActivity.startVideoUpLoadListActivity(getContext(), null);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_7://设置
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), SetActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_bottom_8://配送设置
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(getContext(), SetActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
//            tvNotLogin.setVisibility(View.GONE);
//            rlContent.setVisibility(View.VISIBLE);
            ViseHttp.POST(NetConfig.userInformation)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userInformation))
                    .addParam("uid", spImp.getUID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Gson gson = new Gson();
                                    UserModel userModel = gson.fromJson(data, UserModel.class);
                                    Glide.with(getContext()).load(userModel.getObj().getHeadeimg()).apply(new RequestOptions()
                                            .placeholder(R.mipmap.my_head)
                                            .error(R.mipmap.my_head)).into(mIvHead);
//                                    if (TextUtils.isEmpty(userModel.getObj().getHeadeimg())) {
//                                        Picasso.with(getContext()).load(R.mipmap.my_head).into(ivAvatar);
//                                    } else {
//                                        Picasso.with(getContext()).load(userModel.getObj().getHeadeimg()).into(ivAvatar);
//                                    }
                                    mTvName.setText("昵称: " + userModel.getObj().getUsername());
                                    if (TextUtils.isEmpty(userModel.getObj().getUserautograph())) {

                                    } else {
                                        mTvKinds.setText(userModel.getObj().getUserautograph());
                                    }
                                    mTvNum1.setText(userModel.getObj().getNews() + "");
                                    mTvNum2.setText(userModel.getObj().getFriendnote() + "");
                                    mTvNum3.setText(userModel.getObj().getFocusonnews() + "");
                                    mTvNum4.setText(userModel.getObj().getActivitymessage() + "");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
//            tvNotLogin.setVisibility(View.VISIBLE);
//            rlContent.setVisibility(View.GONE);
            Glide.with(getContext()).load("null").into(mIvHead);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
