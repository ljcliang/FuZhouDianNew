package com.yiwo.fuzhoudian.fragments.webfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseWebFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeDianPuGuanLiFragment extends BaseWebFragment implements View.OnClickListener {
    View rootView;
    @BindView(R.id.wv)
    WebView mWv;
    private View view;
    private Unbinder unbinder;

    private String url;
    private View vStaus;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_dianpuguanli, null);
        unbinder = ButterKnife.bind(this, rootView);
        url = getArguments().getString("url");
        if (url != null) {
            initIntentSonic(url, mWv);
        }
        initView(rootView);
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

    private void initView(View rootView) {
        vStaus = (View) rootView.findViewById(R.id.v_staus);
        btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    public void showStaus() {
        if (vStaus.getVisibility() == View.VISIBLE){
            vStaus.setVisibility(View.GONE);
        }else {
            vStaus.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                showStaus();
                break;
        }
    }
}
