package com.yiwo.fuzhoudian;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionEventListener;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.XieYiDialog;
import com.yiwo.fuzhoudian.fragments.MessageFragment;
import com.yiwo.fuzhoudian.fragments.MineFragment;
import com.yiwo.fuzhoudian.fragments.OrderFragment;
import com.yiwo.fuzhoudian.fragments.webfragment.HomeDianPuGuanLiFragment;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.LoginActivity;
import com.yiwo.fuzhoudian.pages.UserAgreementActivity;
import com.yiwo.fuzhoudian.pages.creatyouji.CreateYouJiActivity;
import com.yiwo.fuzhoudian.pages.renzheng.RenZheng0_BeginActivity;
import com.yiwo.fuzhoudian.pages.webpages.GuanLiGoodsWebActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.StatusBarUtils;
import com.yiwo.fuzhoudian.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.fuzhoudian.wangyiyunshipin.upload.controller.UploadController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_bottom_1)
    ImageView ivBottom1;
    @BindView(R.id.ll_btn_1)
    LinearLayout llBtn1;
    @BindView(R.id.iv_bottom_2)
    ImageView ivBottom2;
    @BindView(R.id.ll_btn_2)
    LinearLayout llBtn2;
    @BindView(R.id.iv_bottom_3)
    ImageView ivBottom3;//购物车图标
    @BindView(R.id.ll_btn_3)
    LinearLayout llBtn3;
    @BindView(R.id.iv_bottom_4)
    ImageView ivBottom4;
    @BindView(R.id.ll_btn_4)
    LinearLayout llBtn4;
    @BindView(R.id.iv_bottom_5)
    ImageView ivBottom5;
    @BindView(R.id.ll_btn_5)
    LinearLayout llBtn5;
    @BindView(R.id.tv_bottom_1)
    TextView tvBottom1;
    @BindView(R.id.tv_bottom_2)
    TextView tvBottom2;
    @BindView(R.id.tv_bottom_3)
    TextView tvBottom3;
    @BindView(R.id.tv_bottom_4)
    TextView tvBottom4;
    @BindView(R.id.tv_bottom_5)
    TextView tvBottom5;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.iv_point_new_chat_message)
    ImageView ivNewChatMessage;
    private long exitTime = 0;

    private List<Fragment> fragmentList = new ArrayList<>();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    //    HomeFragment homeFragment;
    HomeDianPuGuanLiFragment homeFragment;
    MessageFragment messageFragment;
    MineFragment mineFragment;
    OrderFragment orderFragment;
    String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO
            , Manifest.permission.CAMERA};
    List<String> mPermissionList = new ArrayList<>();
    private SpImp spImp;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getPermissions();
        StatusBarUtils.setStatusBarTransparent(this);
        spImp = new SpImp(this);
        uid = spImp.getUID();
        initUpLoadController();
        initFragment();
        initSessionListener();
        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus loginSyncStatus) {
                if (loginSyncStatus == LoginSyncStatus.SYNC_COMPLETED){
                    NIMClient.toggleNotification(true);
                }
            }
        },true);
    }

    private void initSessionListener() {
        NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                return false;
            }
        });
        NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                return false;
            }
        });
        NimUIKit.setSessionListener(new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {//设置头像点击监听
//                if (!message.getFromAccount().equals("tongbanxiaozhushou")){
//                    Intent intent = new Intent();
//                    intent.putExtra("person_id",message.getFromAccount());
//                    intent.putExtra("status","1");
//                    intent.setClass(context, PersonMainActivity1.class);
//                    context.startActivity(intent);
//                }
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {

            }

            @Override
            public void onAckMsgClicked(Context context, IMMessage message) {

            }

            @Override
            public void onMsgTextClicked(Context context, IMMessage message) {
                if (message.getSessionId().equals("fzddianjiaxiaozhushou") && message.getMsgType() == MsgTypeEnum.text) {//瞳伴小助手消息
                    Log.d("sadasda", message.getContent());
                    if (message.getContent().indexOf("订单") != -1) {
                        ((Activity) context).finish();
                        switchFragment(1);
                        orderFragment.refreshAll();
//                        if (orderFragment.isResumed()){
//                            Log.d("sadasda", "orderFragment.isNotHidden()!!!!");
//                            orderFragment.refreshAll();
//                        }
                    }
                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkHasNewChatmessage();
    }

    private void initFragment() {
//        homeFragment = new HomeFragment();
        homeFragment = HomeDianPuGuanLiFragment.newInstance(NetConfig.ShopHomeUrl + "" + spImp.getUID());
        orderFragment = new OrderFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(orderFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(mineFragment);

        fragmentTransaction.add(R.id.fl_container, homeFragment);
        fragmentTransaction.add(R.id.fl_container, orderFragment);
        fragmentTransaction.add(R.id.fl_container, messageFragment);
        fragmentTransaction.add(R.id.fl_container, mineFragment);
        fragmentTransaction.show(homeFragment).hide(orderFragment).hide(messageFragment).hide(mineFragment);
        fragmentTransaction.commit();
        select(0);
    }

    /**
     * 选择隐藏与显示的Fragment
     *
     * @param index 显示的Frgament的角标
     */
    public void switchFragment(int index) {
        select(index);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                fragmentTransaction.show(fragmentList.get(index));
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        backtrack();
    }

    /**
     * 退出销毁所有activity
     */
    private void backtrack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
            exitTime = 0;
        }
    }

    public void select(int i) {
        tvBottom1.setSelected(false);
        tvBottom2.setSelected(false);
        tvBottom4.setSelected(false);
        tvBottom5.setSelected(false);
        ivBottom1.setSelected(false);
        ivBottom2.setSelected(false);
        ivBottom4.setSelected(false);
        ivBottom5.setSelected(false);
        switch (i) {
            case 0:
                tvBottom1.setSelected(true);
                ivBottom1.setSelected(true);
                break;
            case 1:
                tvBottom2.setSelected(true);
                ivBottom2.setSelected(true);
                break;
            case 2:
                tvBottom4.setSelected(true);
                ivBottom4.setSelected(true);
                break;
            case 3:
                tvBottom5.setSelected(true);
                ivBottom5.setSelected(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UploadController.getInstance().suspend();
    }

    public ImageView getCartImageView() {
        return ivBottom3;
    }

    public RelativeLayout getRlMain() {
        return rlMain;
    }

    @OnClick({R.id.ll_btn_1, R.id.ll_btn_2, R.id.ll_btn_3, R.id.ll_btn_4, R.id.ll_btn_5})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_btn_1:

                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    switchFragment(0);
                    if (!spImp.getIfSign().equals("1")){//还没有完成认证
                        RenZheng0_BeginActivity.openActivity(MainActivity.this);
                    }
                } else {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_btn_2:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    switchFragment(1);
                } else {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_btn_3:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    intent.setClass(MainActivity.this, CreateYouJiActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_btn_4:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    switchFragment(2);
                } else {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_btn_5:
                switchFragment(3);
                break;
        }
    }

    public void getPermissions() {
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            init();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private void initUpLoadController() {
        UploadController.getInstance().init(MainActivity.this);
        UploadController.getInstance().loadVideoDataFromLocal(UploadType.SHORT_VIDEO);
//        UploadController.getInstance().attachUi(VideoUpLoadListActivity.this);
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    private void newMessageLis() {//新聊天消息监听
        Observer<List<IMMessage>> incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        ivNewChatMessage.setVisibility(View.VISIBLE);
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    private void checkHasNewChatmessage() {
        if (!isNetworkConnected(this)) {
            return;
        }
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
            if (NIMClient.getService(MsgService.class).getTotalUnreadCount() > 0) {//获取未读消息数
                ivNewChatMessage.setVisibility(View.VISIBLE);
            } else {
                ivNewChatMessage.setVisibility(View.GONE);
            }
        } else {
            ivNewChatMessage.setVisibility(View.GONE);
        }
        newMessageLis();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }
}
