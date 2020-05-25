package com.yiwo.fuzhoudian.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.MainActivity;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseFragment;
import com.yiwo.fuzhoudian.utils.AndTools;
import com.yiwo.fuzhoudian.utils.ShoppingCartUntils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.tv_dingwei)
    TextView tvDingwei;
    @BindView(R.id.tv_home_top_1)
    TextView tvHomeTop1;
    @BindView(R.id.v_home_top_1)
    View vHomeTop1;
    @BindView(R.id.tv_home_top_2)
    TextView tvHomeTop2;
    @BindView(R.id.v_home_top_2)
    View vHomeTop2;
    @BindView(R.id.tv_home_top_3)
    TextView tvHomeTop3;
    @BindView(R.id.v_home_top_3)
    View vHomeTop3;
    @BindView(R.id.vp)
    ViewPager vp;
    private Unbinder unbinder;

    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeGuanZhuFragment guanZhuFragment;
    private HomeTuiJianFragment tuiJianFragment;
    private HomeZhouBianFragment zhouBianFragment;
    PagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);
        initVpFragment();
        return rootView;
    }

    private void initVpFragment() {
        guanZhuFragment = new HomeGuanZhuFragment();
        guanZhuFragment.setAddToCartListenner(new HomeGuanZhuFragment.AddToCartListenner() {
            @Override
            public void addGoods(ImageView imageView) {
                ImageView ivCart = ((MainActivity)getActivity()).getCartImageView();
                RelativeLayout rlMain = ((MainActivity)getActivity()).getRlMain();
                ShoppingCartUntils.add2Cart(imageView,ivCart,getContext(),rlMain);
            }
        });
        tuiJianFragment = new HomeTuiJianFragment();
        zhouBianFragment = new HomeZhouBianFragment();
        fragmentList.add(guanZhuFragment);
        fragmentList.add(tuiJianFragment);
        fragmentList.add(zhouBianFragment);
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList);
        vp.setAdapter(pagerAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        select(0);
    }
    private void select(int index){
        tvHomeTop1.setTextSize(20);
        tvHomeTop1.setTypeface(tvHomeTop1.getTypeface(), Typeface.NORMAL);
        tvHomeTop2.setTextSize(20);
        tvHomeTop2.setTypeface(tvHomeTop2.getTypeface(), Typeface.NORMAL);
        tvHomeTop3.setTextSize(20);
        tvHomeTop3.setTypeface(tvHomeTop3.getTypeface(), Typeface.NORMAL);
        vHomeTop1.setVisibility(View.GONE);
        vHomeTop2.setVisibility(View.GONE);
        vHomeTop3.setVisibility(View.GONE);
        switch (index){
            case 0:
                tvHomeTop1.setTextSize(26);
                tvHomeTop1.setTypeface(tvHomeTop1.getTypeface(), Typeface.BOLD);
                vHomeTop1.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvHomeTop2.setTextSize(26);
                tvHomeTop2.setTypeface(tvHomeTop2.getTypeface(), Typeface.BOLD);
                vHomeTop2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvHomeTop3.setTextSize(26);
                tvHomeTop3.setTypeface(tvHomeTop3.getTypeface(), Typeface.BOLD);
                vHomeTop3.setVisibility(View.VISIBLE);
                break;
        }
    }
    class PagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments;
        private FragmentManager fm;
        public PagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.fragments = list;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_sousuo, R.id.ll_dingwei, R.id.rl_home_top_1, R.id.rl_home_top_2, R.id.rl_home_top_3})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_sousuo:
                break;
            case R.id.ll_dingwei:
                break;
            case R.id.rl_home_top_1:
                vp.setCurrentItem(0);
                break;
            case R.id.rl_home_top_2:
                vp.setCurrentItem(1);
                break;
            case R.id.rl_home_top_3:
                vp.setCurrentItem(2);
                break;
        }
    }
}
