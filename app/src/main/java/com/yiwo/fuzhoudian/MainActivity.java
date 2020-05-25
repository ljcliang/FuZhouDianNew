package com.yiwo.fuzhoudian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.fragments.HomeFragment;
import com.yiwo.fuzhoudian.fragments.MessageFragment;
import com.yiwo.fuzhoudian.fragments.MineFragment;
import com.yiwo.fuzhoudian.fragments.OrderFragment;
import com.yiwo.fuzhoudian.fragments.webfragment.HomeDianPuGuanLiFragment;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.LoginActivity;
import com.yiwo.fuzhoudian.pages.creatyouji.CreateYouJiActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.StatusBarUtils;
import com.yiwo.fuzhoudian.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.fuzhoudian.wangyiyunshipin.upload.controller.UploadController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.fuzhoudian.network.NetConfig.ShopHomeUrl;

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
    private long exitTime = 0;

    private List<Fragment> fragmentList = new ArrayList<>();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//    HomeFragment homeFragment;
    HomeDianPuGuanLiFragment homeFragment;
    MessageFragment messageFragment;
    MineFragment mineFragment;
    OrderFragment orderFragment;
    String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.RECORD_AUDIO
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
    }

    private void initFragment() {
//        homeFragment = new HomeFragment();
        homeFragment =  HomeDianPuGuanLiFragment.newInstance(NetConfig.ShopHomeUrl+""+spImp.getUID());
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
        fragmentTransaction.commit();
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
                switchFragment(0);
                break;
            case R.id.ll_btn_2:
                switchFragment(1);
                break;
            case R.id.ll_btn_3:
                intent.setClass(MainActivity.this, CreateYouJiActivity.class);
                startActivity(intent);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
                }
            }
        }
    }
}
