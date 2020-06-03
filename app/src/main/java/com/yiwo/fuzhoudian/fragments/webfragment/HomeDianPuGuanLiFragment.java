package com.yiwo.fuzhoudian.fragments.webfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseWebFragment;
import com.yiwo.fuzhoudian.utils.ShareUtils;

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
            mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
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
        vStaus.setVisibility(View.VISIBLE);
    }
    public void hideStaus() {
        vStaus.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                if (vStaus.getVisibility() == View.VISIBLE){
                    hideStaus();
                }else {
                    showStaus();
                }
                break;
        }
    }
    public class AndroidInterface extends Object{
        /**
         * 首页分享店铺交互方法
         * @param shopName 店铺名称
         * @param img 图片
         * @param info 介绍
         * @param shareUrl 分享链接
         */
        @JavascriptInterface
        public void toshareshop(String shopName,String img,String info,String shareUrl){
            share(shareUrl,shopName,info,img);
        }

        /**
         * 首页分享商品的交互方法   返回 商品名  商品图  商品信息  分享链接
         * @param goodName 商品名
         * @param img 商品图
         * @param info 商品信息
         * @param shareUrl 分享链接
         */
        @JavascriptInterface
        public void tosharegoods(String goodName,String img,String info,String shareUrl){
            share(shareUrl,goodName,info,img);
        }

        /**
         *
         * @param status  open打开状态   hidden隐藏状态
         */
        @JavascriptInterface
        public void showstyle(final String status){
            Log.d("asdasdas",status);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    if (status.equals("open")){
                        message.arg1 = 1;
                    }else {
                        message.arg1 = 0;
                    }
                    handler.sendMessage(message);
                }
            }).start();
//            if (status.equals("open")){
//                showStaus();
//            }else {
//                hideStaus();
//            }
//            btn.callOnClick();
        }
    }
    public void share (final String url, final String title, final String message, final String img){
        Log.d("shareshare",url);
        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        ShareUtils.shareWeb(getActivity(), url, title,
                                message, img, share_media);
                    }
                }).open();
    }
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0:
                    vStaus.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    vStaus.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
