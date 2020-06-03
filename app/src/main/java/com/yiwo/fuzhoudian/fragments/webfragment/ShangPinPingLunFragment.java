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

public class ShangPinPingLunFragment extends BaseWebFragment {
    @BindView(R.id.wv)
    WebView mWv;
    private Unbinder unbinder;

    private String url;
    private Button btn;
    View rootView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_web_shang_pin_ping_lun, null);
        unbinder = ButterKnife.bind(this, rootView);
        url = getArguments().getString("url");
        if (url != null) {
            initIntentSonic(url, mWv);
        }
        return rootView;

    }

    public static ShangPinPingLunFragment newInstance(String url) {
        ShangPinPingLunFragment f = new ShangPinPingLunFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }
}
