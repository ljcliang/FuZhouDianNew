package com.yiwo.fuzhoudian.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.HomeGuanZhuDianPuAdapter;
import com.yiwo.fuzhoudian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeGuanZhuFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private View view;
    private Unbinder unbinder;

    private List<String> datas = new ArrayList<>();
    private HomeGuanZhuDianPuAdapter homeGuanZhuDianPuAdapter;
    private AddToCartListenner addToCartListenner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_guanzhu, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRv();
        return rootView;

    }

    public AddToCartListenner getAddToCartListenner() {
        return addToCartListenner;
    }

    public void setAddToCartListenner(AddToCartListenner addToCartListenner) {
        this.addToCartListenner = addToCartListenner;
    }

    public interface AddToCartListenner{
        void addGoods(ImageView imageView);
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

        homeGuanZhuDianPuAdapter = new HomeGuanZhuDianPuAdapter(datas, new HomeGuanZhuDianPuAdapter.Add2CartListenner() {
            @Override
            public void addGoods(int pos, ImageView addGoodIv) {
                if (addToCartListenner!=null){
                    addToCartListenner.addGoods(addGoodIv);
                }
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext()){
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
