package com.yiwo.fuzhoudian.fragments.webfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseWebFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeDianPuGuanLiFragment extends BaseWebFragment {
    View rootView;
    @BindView(R.id.wv)
    WebView mWv;
    private View view;
    private Unbinder unbinder;

    private String url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_dianpuguanli, null);
        unbinder = ButterKnife.bind(this, rootView);
        url = getArguments().getString("url");
        if (url!=null){
            initIntentSonic(url,mWv);
        }
        return rootView;
    }
    public static HomeDianPuGuanLiFragment newInstance(String url) {
        HomeDianPuGuanLiFragment f = new HomeDianPuGuanLiFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
