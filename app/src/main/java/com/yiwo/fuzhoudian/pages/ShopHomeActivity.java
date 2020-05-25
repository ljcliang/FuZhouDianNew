package com.yiwo.fuzhoudian.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.ShopHomeGoodsAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopHomeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_shop_home_tap1)
    TextView tvShopHomeTap1;
    @BindView(R.id.rl_shop_home_tap1)
    RelativeLayout rlShopHomeTap1;
    @BindView(R.id.tv_shop_home_tap2)
    TextView tvShopHomeTap2;
    @BindView(R.id.rl_shop_home_tap2)
    RelativeLayout rlShopHomeTap2;
    @BindView(R.id.tv_shop_home_tap3)
    TextView tvShopHomeTap3;
    @BindView(R.id.rl_shop_home_tap3)
    RelativeLayout rlShopHomeTap3;
    @BindView(R.id.tv_sousuo)
    TextView tvSousuo;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    //标签bar是否在顶部
    boolean tapOnTop = false;
    @BindView(R.id.ll_tab_bar)
    LinearLayout llTabBar;
    @BindView(R.id.v_staus)
    View vStaus;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        StatusBarUtils.setStatusBarTransparent(ShopHomeActivity.this);
        ButterKnife.bind(this);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (llTabBar.getHeight() - appBarLayout.getHeight() == i) {
                    if (!tapOnTop) {
                        vStaus.setVisibility(View.VISIBLE);
                        llTabBar.setBackgroundResource(R.drawable.bg_4e94a0);
                    }
                    tapOnTop = true;
                } else {
                    if (tapOnTop) {
                        vStaus.setVisibility(View.GONE);
                        llTabBar.setBackgroundResource(R.drawable.bg_ffffff);
//                        llTabBar.setBackgroundResource(R.drawable.bg_4e94a0_20dp_top_left);
                    }
                    tapOnTop = false;
                }
            }
        });
        selectKinds(1);
        selectTab(1);
        initRefresh();
        initRv();
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
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

    private void initRv() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        ShopHomeGoodsAdapter adapter = new ShopHomeGoodsAdapter(list);
// /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv1.setLayoutManager(mLayoutManager);
        rv1.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ShopHomeActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_back, R.id.rl_shop_home_tap1, R.id.rl_shop_home_tap2, R.id.rl_shop_home_tap3, R.id.tv_sousuo, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rv1, R.id.rv2, R.id.rv3})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_shop_home_tap1:
                selectTab(1);
                break;
            case R.id.rl_shop_home_tap2:
                selectTab(2);
                break;
            case R.id.rl_shop_home_tap3:
                selectTab(3);
                break;
            case R.id.tv_sousuo:
                break;
            case R.id.rl1:
                selectKinds(1);
                break;
            case R.id.rl2:
                selectKinds(2);
                break;
            case R.id.rl3:
                selectKinds(3);
                break;
            case R.id.rv1:
                break;
            case R.id.rv2:
                break;
            case R.id.rv3:
                break;
        }
    }

    private void selectTab(int i) {
        tvShopHomeTap1.setSelected(false);
        tvShopHomeTap2.setSelected(false);
        tvShopHomeTap3.setSelected(false);
        switch (i) {
            case 1:
                tvShopHomeTap1.setSelected(true);
                break;
            case 2:
                tvShopHomeTap2.setSelected(true);
                break;
            case 3:
                tvShopHomeTap3.setSelected(true);
                break;
        }
    }

    private void selectKinds(int i) {
        v1.setVisibility(View.GONE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        rl1.setSelected(false);
        rl2.setSelected(false);
        rl3.setSelected(false);
        switch (i) {
            case 1:
                rl1.setSelected(true);
                v1.setVisibility(View.VISIBLE);
                break;
            case 2:
                rl2.setSelected(true);
                v2.setVisibility(View.VISIBLE);
                break;
            case 3:
                rl3.setSelected(true);
                v3.setVisibility(View.VISIBLE);
                break;
        }
    }
}
