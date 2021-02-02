package com.yiwo.fuzhoudian.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.OrderPagerAdapter;
import com.yiwo.fuzhoudian.base.BaseFragment;
import com.yiwo.fuzhoudian.fragments.order.AllOrderFragment;
import com.yiwo.fuzhoudian.pages.SAOYISAOActivity;
import com.yiwo.fuzhoudian.pages.webpages.OrderInfoWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class OrderFragment extends BaseFragment {

    View rootView;
    private Unbinder unbinder;
    @BindView(R.id.activity_my_order_tab)
    TabLayout mTb;
    @BindView(R.id.activity_my_order_viewpager)
    ViewPager mVp;
    @BindView(R.id.edt_search)
    EditText editSearch;
    @BindView(R.id.rl_saoyisao)
    RelativeLayout rl_saoyisao;
    private List<Fragment> mFragmentList;
    public static final int ER_WEI_MA_REQUEST_CODE = 1;
    private AllOrderFragment allOrderFragment,allOrderFragment1,allOrderFragment2,allOrderFragment3,allOrderFragment4;
    private void initFragment() {
        mFragmentList = new ArrayList<>();
        allOrderFragment = AllOrderFragment.newInstance(100);//全部订单
        allOrderFragment1 = AllOrderFragment.newInstance(1);//待处理
        allOrderFragment2 = AllOrderFragment.newInstance(2);//已处理
        allOrderFragment3 = AllOrderFragment.newInstance(3);//已完成
        allOrderFragment4 = AllOrderFragment.newInstance(4);//退款
        allOrderFragment.setListenner(new AllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment1.setListenner(new AllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment2.setListenner(new AllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment3.setListenner(new AllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment4.setListenner(new AllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment.hasChanged = true;
            }
        });
        mFragmentList.add(allOrderFragment);
        mFragmentList.add(allOrderFragment1);
        mFragmentList.add(allOrderFragment2);
        mFragmentList.add(allOrderFragment3);
        mFragmentList.add(allOrderFragment4);
    }
    private List<String> mTitleList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order, null);
//        ScreenAdapterTools.getInstance().loadView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }
    private void init() {
        initTitile();
        initFragment();
        //设置适配器
        mVp.setAdapter(new OrderPagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);
        mTb.getTabAt(0).select();
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                AllOrderFragment fragment = (AllOrderFragment) mFragmentList.get(i);
                if (fragment.hasChanged){
                    Log.d("changea=asdsad","//"+i);
                    fragment.refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待处理");
        mTitleList.add("已处理");
        mTitleList.add("已完成");
        mTitleList.add("退款");
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        for(int i = 0; i<mTitleList.size(); i++){
            mTb.addTab(mTb.newTab().setText(mTitleList.get(i)));
        }
    }
    public void refreshAll(){
        Log.d("sadasda", "refresh");
        switch (mVp.getCurrentItem()){
            case 0:
                allOrderFragment.refresh();
                break;
            case 1:
                allOrderFragment1.refresh();
                break;
            case 2:
                allOrderFragment2.refresh();
                break;
            case 3:
                allOrderFragment3.refresh();
                break;
            case 4:
                allOrderFragment4.refresh();
                break;

        }
    }

    @OnClick({R.id.ll_btn_serch,R.id.rl_saoyisao})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_btn_serch:
                mVp.setCurrentItem(0);
                allOrderFragment.refresh(editSearch.getText().toString());
                break;
            case R.id.rl_saoyisao:
                Intent intent = new Intent(getContext(), SAOYISAOActivity.class);
                startActivityForResult(intent, ER_WEI_MA_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ER_WEI_MA_REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    OrderInfoWebActivity.start(getContext(),result);
//                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
