package com.yiwo.fuzhoudian.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.yiwo.fuzhoudian.adapter.GuanZhuWoDeAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.GuanZhuWoDeModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanZhuActivity extends BaseActivity {

    private Context context = GuanZhuActivity.this;

    @BindView(R.id.rv_woguanzhude)
    RecyclerView rv_woguanzhude;
    @BindView(R.id.rv_guanzhuwode)
    RecyclerView rv_guanzhuwode;

    @BindView(R.id.guanzhu_refreshlayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.edt_search)
    EditText edt_search;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private SpImp spImp;
    private int page_woguanzhude = 1;
    private int page_guanzhuwode = 1;
    private int page_guanzhuhuodong = 1; //  关注活动接口无分页

    private PopupWindow popupWindow;
    private List<GuanZhuWoDeModel.ObjBean> mGuanZhuWoDeList ;
    private GuanZhuWoDeAdapter guanZhuWoDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu);
        spImp = new SpImp(GuanZhuActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(GuanZhuActivity.this);
        initRefresh();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(GuanZhuActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(GuanZhuActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.guanZhuWoDe)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                        .addParam("page", "1")
                        .addParam("userID", spImp.getUID())
                        .addParam("userName",edt_search.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                        mGuanZhuWoDeList.clear();
                                        mGuanZhuWoDeList.addAll(guanZhuWoDeModel.getObj());
                                        guanZhuWoDeAdapter.notifyDataSetChanged();
                                        page_guanzhuwode = 2 ;
                                        Log.e("222page_guanzhuwode", page_guanzhuwode+"");
                                        refreshLayout.finishRefresh(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.guanZhuWoDe)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                        .addParam("page", page_guanzhuwode+"")
                        .addParam("userID", spImp.getUID())
                        .addParam("userName",edt_search.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                        mGuanZhuWoDeList.addAll(guanZhuWoDeModel.getObj());
                                        guanZhuWoDeAdapter.notifyDataSetChanged();
                                        page_guanzhuwode++ ;
                                        Log.e("222page_guanzhuwode", page_guanzhuwode+"");
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }

    private void initData() {
        //-------------------关注我的---------------------
        ViseHttp.POST(NetConfig.guanZhuWoDe)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .addParam("userName",edt_search.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                mGuanZhuWoDeList = guanZhuWoDeModel.getObj();
                                guanZhuWoDeAdapter = new GuanZhuWoDeAdapter(mGuanZhuWoDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_guanzhuwode.setLayoutManager(manager);
                                rv_guanzhuwode.setAdapter(guanZhuWoDeAdapter);

                                page_guanzhuwode = 2;
                                Log.e("222", page_guanzhuwode+"");
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

    @OnClick({R.id.rl_back,R.id.tv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
               finish();
                break;
            case R.id.tv_search:
                ViseHttp.POST(NetConfig.guanZhuWoDe)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                        .addParam("page", "1")
                        .addParam("userName",edt_search.getText().toString())
                        .addParam("userID", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                        mGuanZhuWoDeList.clear();
                                        mGuanZhuWoDeList.addAll(guanZhuWoDeModel.getObj());
                                        guanZhuWoDeAdapter.notifyDataSetChanged();
                                        page_guanzhuwode = 2 ;
                                        Log.e("222page_guanzhuwode", page_guanzhuwode+"");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                            }
                        });
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
