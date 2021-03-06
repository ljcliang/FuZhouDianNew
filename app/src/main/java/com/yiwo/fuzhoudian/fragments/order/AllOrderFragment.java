package com.yiwo.fuzhoudian.fragments.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.FragmentAllOrderAdapter;
import com.yiwo.fuzhoudian.base.BaseFragment;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.model.SellerOrderModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.MyCommentActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/18.
 */

public class AllOrderFragment extends BaseFragment {

    @BindView(R.id.fragment_order_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_all_order_refreshLayout)
    RefreshLayout refreshLayout;

    private List<SellerOrderModel.ObjBean> mList = new ArrayList<>();
    private FragmentAllOrderAdapter adapter;
    private SpImp spImp;
    private String uid = "";
    private int page = 1;

    protected View mRootView;
    private static final int SDK_PAY_FLAG = 1;
    private String status = "100";
    public boolean hasChanged = false;
    private DataChangeListenner listenner;
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = initView();
            status = getArguments().getString(STATUS);
            initData1();
        }
        return mRootView;
    }
    public static String STATUS = "status";
    public static AllOrderFragment newInstance(int status){//status  不传或传100 全部     传1待处理  2已处理   3已完成   4退款
        Bundle args = new Bundle();
        args.putString(STATUS, status+"");
        AllOrderFragment fragment = new AllOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_all_order, null);
        ScreenAdapterTools.getInstance().loadView(view);

        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        uid = spImp.getUID();
        mList = new ArrayList<>();
        adapter = new FragmentAllOrderAdapter(mList, getActivity(), new FragmentAllOrderAdapter.BtnsOnCLickListenner() {
            @Override
            public void onChuLiDan(int postion, int type,String juJueYuanYin) {
                Log.d("sadasd",type+":::"+juJueYuanYin);
                chuLiDingDan(mList.get(postion),type,juJueYuanYin);
            }

            @Override
            public void onYiPingJia(int postion) {
                MyCommentActivity.open(getContext(),false);
            }

            @Override
            public void onTuiKuan(int postion) {
                tuikuan(mList.get(postion));
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public void refreshData(){
        refreshLayout.autoRefresh();
    }
    private void initData1() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.sellerOrder)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sellerOrder))
                        .addParam("page", "1")
                        .addParam("uid", uid)
                        .addParam("status", status)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        SellerOrderModel model = gson.fromJson(data, SellerOrderModel.class);
                                        page = 2;
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        refreshlayout.finishRefresh(500);
                                        listenner.onDataChange(-1);
                                    }
                                    refreshlayout.finishRefresh(500);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishRefresh(500);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.sellerOrder)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sellerOrder))
                        .addParam("page", page + "")
                        .addParam("uid", uid)
                        .addParam("status", status)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        SellerOrderModel model = gson.fromJson(data, SellerOrderModel.class);
                                        page = page + 1;
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        refreshlayout.finishLoadMore(500);
                                    }
                                    refreshlayout.finishLoadMore(500);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishLoadMore(500);
                            }
                        });
            }
        });
        ViseHttp.POST(NetConfig.sellerOrder)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sellerOrder))
                .addParam("page", "1")
                .addParam("uid", uid)
                .addParam("status", status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.d("asdsad","asdasdssa");
                                Gson gson = new Gson();
                                SellerOrderModel model = gson.fromJson(data, SellerOrderModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private void tuikuan(SellerOrderModel.ObjBean bean) {
        ViseHttp.POST(NetConfig.orderRefund)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.orderRefund))
                .addParam("uid", spImp.getUID())
                .addParam("orderID",bean.getId())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(getContext(),jsonObject.getString("message"));
                                refresh();
                                if (listenner!=null){
                                    listenner.onDataChange(-1);
                                }
                            }else {
                                toToast(getContext(),"退款失败："+jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(getContext(),errCode+":"+errMsg);
                    }
                });
    }

    private void chuLiDingDan(SellerOrderModel.ObjBean bean, final int type,String juJueYuanYin) {
        String quxiaoyuanyin = "";
        if (type ==0){//拒绝接单时
            quxiaoyuanyin = juJueYuanYin;
        }
        if (type == 3){//确定买家已经收货
            Log.d("sadasd",bean.getId());
            dialog = WeiboDialogUtils.createLoadingDialog(getContext(),"");
            ViseHttp.POST(NetConfig.getSureTime)
                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.getSureTime))
                    .addParam("orderID", bean.getId())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.d("sadasd",data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("obj");

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage(jsonObject1.getString("message"))
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            WeiboDialogUtils.closeDialog(dialog);
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    });
//            ViseHttp.POST(NetConfig.shopSureGetThings)
//                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.shopSureGetThings))
//                    .addParam("uid", spImp.getUID())
//                    .addParam("orderID",bean.getId())
//                    .request(new ACallback<String>() {
//                        @Override
//                        public void onSuccess(String data) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.getInt("code") == 200){
//                                    toToast(getContext(),jsonObject.getString("message"));
//                                    refresh();
//                                    if (listenner!=null){
//                                        listenner.onDataChange(type);
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int errCode, String errMsg) {
//
//                        }
//                    });
        }else { //  0拒绝接单  1出单  2删除
            ViseHttp.POST(NetConfig.sellerDoOrder)
                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sellerDoOrder))
                    .addParam("uid", spImp.getUID())
                    .addParam("orderID",bean.getId())
                    .addParam("type",type+"")
                    .addParam("qxyy",quxiaoyuanyin)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.d("sadasd",data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    toToast(getContext(),jsonObject.getString("message"));
                                    refresh();
                                    if (listenner!=null){
                                        listenner.onDataChange(type);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.d("sadasd",errCode+ errMsg);
                            toToast(getContext(),errCode+":"+ errMsg);
                        }
                    });
        }
    }
    public void refresh(){
        refresh("");
    }
    public void refresh(String search_word){

        ViseHttp.POST(NetConfig.sellerOrder)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.sellerOrder))
                .addParam("page", "1")
                .addParam("uid", uid)
                .addParam("status", status)
                .addParam("keyWord",search_word)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                SellerOrderModel model = gson.fromJson(data, SellerOrderModel.class);
                                page = 2;
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                hasChanged = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
//                    Map<String, String> result = (Map<String, String>) msg.obj;
//                    if(result.get("resultStatus").equals("9000")){
//                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
//                        getActivity().finish();
//                    }
                    break;
            }
        }

    };

    public void setListenner(DataChangeListenner listenner) {
        this.listenner = listenner;
    }

    public interface DataChangeListenner{
        /**
         * 有数据变化时调用接口，
         * @param type 操作状态   为-1时候
         */
        void onDataChange(int type);
    }
}
