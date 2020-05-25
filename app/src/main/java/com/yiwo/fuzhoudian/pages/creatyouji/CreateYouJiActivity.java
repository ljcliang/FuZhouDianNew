package com.yiwo.fuzhoudian.pages.creatyouji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.FragmentViewpagerAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.fragments.createyouji.CreateYouJiChoosePicsFragment;
import com.yiwo.fuzhoudian.utils.StatusBarUtils;
import com.yiwo.fuzhoudian.wangyiyunshipin.TakeVideoFragment_new;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateYouJiActivity extends BaseActivity {

    public static final String EXTRA_FROM_UPLOAD_NOTIFY = "extra_from_upload_notify"; //由上传通知启动
    public static final String ONLY_ADD_VIDEO = "add_video";
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp)
    ViewPager mViewPager;

    private FragmentManager mFragmentManager;
    private FragmentViewpagerAdapter mViewPagerFragmentAdapter;
    private List<Fragment> fragmentList;

    private ArrayList<String> mTitleDataList;
    private SimplePagerTitleView simplePagerTitleView;
    private boolean onlyShowAddVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_youji);
        ButterKnife.bind(CreateYouJiActivity.this);
        StatusBarUtils.setStatusBarTransparent(CreateYouJiActivity.this);
        onlyShowAddVideo = getIntent().getBooleanExtra(ONLY_ADD_VIDEO,false);
        mFragmentManager = getSupportFragmentManager();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(onlyShowAddVideo){
            fragmentList.get(0).onActivityResult(requestCode, resultCode, data);
        }else {
            fragmentList.get(1).onActivityResult(requestCode, resultCode, data);
        }
        Log.d("onActivityResulton___","requestCode:"+requestCode+"///"+"resultCode:"+resultCode);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        if (!onlyShowAddVideo){
            fragmentList.add(new CreateYouJiChoosePicsFragment());
        }
        fragmentList.add(new TakeVideoFragment_new());
//        fragmentList.add(new CreateFriendRememberNew_ChoosePicsFragment());
        mViewPagerFragmentAdapter = new FragmentViewpagerAdapter(mFragmentManager, fragmentList);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    StatusBarUtils.setStatusBar(CreateYouJiActivity.this, Color.TRANSPARENT);
                }else {
                    StatusBarUtils.setStatusBar(CreateYouJiActivity.this, R.color.colorPrimary);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTitleDataList = new ArrayList<>();
        if (!onlyShowAddVideo){
            mTitleDataList.add("相册");
        }
        mTitleDataList.add("视频");
//        mTitleDataList.add("拍照");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);  //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitleDataList.get(index));
                //设置字体
                simplePagerTitleView.setTextSize(18);
//                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.parseColor("#d84c37"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }
}
