package com.yiwo.fuzhoudian.fragments.webfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yiwo.fuzhoudian.MainActivity;
import com.yiwo.fuzhoudian.MyApplication;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseWebFragment;
import com.yiwo.fuzhoudian.custom.XieYiDialog;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.LoginActivity;
import com.yiwo.fuzhoudian.pages.MyFriendActivity;
import com.yiwo.fuzhoudian.pages.UserAgreementActivity;
import com.yiwo.fuzhoudian.pages.renzheng.RenZheng0_BeginActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeDianPuGuanLiFragment extends BaseWebFragment implements View.OnClickListener {
    View rootView;
    @BindView(R.id.wv)
    WebView mWv;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    private View view;
    private Unbinder unbinder;

    private String url;
    private View vStaus;
    private Button btn;
    private SpImp spImp;
    private ReLoadBroadcastreceiver reLoadBroadcastreceiver = new ReLoadBroadcastreceiver();
    public static final String ACTION_RELOAD_WEB = "HomeDianPuGuanLiFragment.reload";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_dianpuguanli, null);
        unbinder = ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());
        initView(rootView);
        initRefresh();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_RELOAD_WEB);
        getContext().registerReceiver(reLoadBroadcastreceiver,intentFilter);

        Intent intent = new Intent();
        if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
            url = getArguments().getString("url");
            if (url != null) {
                initIntentSonic(url, mWv);
                mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
            }
        }else {
            intent.setClass(getContext(), LoginActivity.class);
            Log.d("sadasda","sdasdasd");
            startActivity(intent);
        }
        return rootView;
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                url = getArguments().getString("url");
                if (url != null) {
//                    initIntentSonic(url, mWv);
//                    mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
                    mWv.reload();
                }
                refreshLayout.finishRefresh(500);
            }
        });
        refreshLayout.setEnableLoadMore(false);
    }

//    private void showAgreeDialog() {
//        XieYiDialog dialog = new XieYiDialog(getContext(), new XieYiDialog.XieYiDialogListener() {
//            @Override
//            public void agreeBtnListen() {
//                spImp.setIsAgreeXieYi(true);
//                url = NetConfig.ShopHomeUrl + "" + spImp.getUID();
//                if (url != null) {
//                    if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
//                        url = getArguments().getString("url");
//                        if (url != null) {
//                            initIntentSonic(url, mWv);
//                            mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
//                        }
//                    }else {
//                        Intent intent = new Intent();
//                        intent.setClass(getContext(), LoginActivity.class);
//                        Log.d("sadasda","sdasdasd");
//                        startActivity(intent);
//                    }
//                }
////                initAsset();
////                initData();
//            }
//
//            @Override
//            public void disAgreeBtnListen() {
//                spImp.setIsAgreeXieYi(false);
//                MyApplication.getInstance().exit();
//            }
//
//            @Override
//            public void xieYiTextClickListen() {
//                Intent itA = new Intent(getContext(), UserAgreementActivity.class);
//                itA.putExtra("title", "用户协议");
//                itA.putExtra("url", NetConfig.userAgreementUrl);
//                startActivity(itA);
//            }
//
//            @Override
//            public void zhengCeTextClickListen() {
//                Intent itTk = new Intent(getActivity(), UserAgreementActivity.class);
//                itTk.putExtra("title", "隐私政策");
//                itTk.putExtra("url", NetConfig.userAgreementUrl1);
//                startActivity(itTk);
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//    }
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
//                if (vStaus.getVisibility() == View.VISIBLE){
//                    hideStaus();
//                }else {
//                    showStaus();
//                }
                RenZheng0_BeginActivity.openActivity(getContext());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(reLoadBroadcastreceiver);
    }

    public class ReLoadBroadcastreceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (!spImp.isAgree()){
//                showAgreeDialog();
//            }
            url = NetConfig.ShopHomeUrl + "" + spImp.getUID();
            if (url != null) {
                initIntentSonic(url, mWv);
                mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == LoginActivity.LOGIN_SUSS_RESULT ){
//            if (!spImp.isAgree()){
//                showAgreeDialog();
//            }
//            Log.d("sadasda","login_chengggon");
//            url = NetConfig.ShopHomeUrl + "" + spImp.getUID();
//            if (url != null) {
//                initIntentSonic(url, mWv);
//                mWv.addJavascriptInterface(new AndroidInterface(),"android");//交互
//            }
//        }
    }
}
