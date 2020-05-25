package com.yiwo.fuzhoudian.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.HomeGuanZhuDianPuAdapter;
import com.yiwo.fuzhoudian.adapter.HomeZhouBianDianPuAdapter;
import com.yiwo.fuzhoudian.adapter.HomeZhouBianLabelAdapter;
import com.yiwo.fuzhoudian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeZhouBianFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.rl_zhoubian)
    RelativeLayout rlZhoubian;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private View view;
    private Unbinder unbinder;

    private List<String> datas = new ArrayList<>();
    private HomeZhouBianDianPuAdapter homeGuanZhuDianPuAdapter;

    private HomeZhouBianLabelAdapter labelAdapter;
    private PopupWindow popupWindow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_zhoubian, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRv();
        return rootView;

    }
    private void initRv() {
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");

        homeGuanZhuDianPuAdapter = new HomeZhouBianDianPuAdapter(datas);
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(homeGuanZhuDianPuAdapter);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
            }
        });
    }
    private void showMuLuPopupwindow(View p_view) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_home_zhoubian_shaoxuan, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_label);
        TextView btnSure = view.findViewById(R.id.tv_btn);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(manager);
        List<String> list = new ArrayList<>();
        list.add("美妆护肤");
        list.add("养生保健");
        list.add("糖果烟酒");
        list.add("美味点心");
        list.add("服装鞋帽");
        list.add("服装鞋帽");
        labelAdapter= new HomeZhouBianLabelAdapter(list);
        recyclerView.setAdapter(labelAdapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.popwindow_anim_top_in_out);
        popupWindow.showAsDropDown(p_view);
        popupWindow.update();
    }
    @OnClick(R.id.rl_zhoubian)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_zhoubian:
                showMuLuPopupwindow(v);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
