package com.yiwo.fuzhoudian.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

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
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.EditContentDialog_L;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.adapter.MyVideosAdapter;
import com.yiwo.fuzhoudian.model.MyVideosModel;
import com.yiwo.fuzhoudian.pages.creatyouji.CreateYouJiActivity;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyVideosActivity extends BaseActivity {

    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private SpImp spImp;

    private List<MyVideosModel.ObjBean> list = new ArrayList<>();
    private MyVideosAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(MyVideosActivity.this);
        initData();
    }

    private void initData() {
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new MyVideosAdapter(list);
        adapter.setListener(new MyVideosAdapter.BtnClickListener() {
            @Override
            public void onDeleteClick(final int i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyVideosActivity.this);
                builder.setMessage("确定删除视频？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViseHttp.POST(NetConfig.videoDel)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.videoDel))
                                        .addParam("userID", spImp.getUID())
                                        .addParam("vID",list.get(i).getVID())
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                Log.d("sdasdas",data);
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200){
                                                        toToast(MyVideosActivity.this,"删除成功！");
                                                        list.remove(i);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFail(int errCode, String errMsg) {
                                                toToast(MyVideosActivity.this,errMsg+":"+errCode);
                                            }
                                        });
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }

            @Override
            public void onEditClick(final int i) {
                EditVideoTitleActivity.startEditVideoInfoActivity(MyVideosActivity.this, i ,list.get(i));
//                EditContentDialog_L dialog_l = new EditContentDialog_L(MyVideosActivity.this, "请输入新的标题", new EditContentDialog_L.OnReturnListener() {
//                    @Override
//                    public void onReturn(final String content) {
//                        ViseHttp.POST(NetConfig.videoEdit)
//                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.videoEdit))
//                                .addParam("vID",list.get(i).getVID())
//                                .addParam("userID",spImp.getUID())
//                                .addParam("vname",content)
//                                .request(new ACallback<String>() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        try {
//                                            JSONObject jsonObject = new JSONObject(data);
//                                            if (jsonObject.getInt("code") == 200){
//                                                toToast(MyVideosActivity.this,"修改成功");
//                                                list.get(i).setVname(content);
//                                                adapter.notifyDataSetChanged();
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFail(int errCode, String errMsg) {
//                                        toToast(MyVideosActivity.this,"修改失败");
//                                    }
//                                });
//                    }
//                });
//                dialog_l.show();
            }
        });
        recyclerView.setAdapter(adapter);
        ViseHttp.POST(NetConfig.myVideo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myVideo))
                .addParam("userID", spImp.getUID())
                .addParam("page",1+"")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") ==  200){
                                Gson gson = new Gson();
                                MyVideosModel model = gson.fromJson(data,MyVideosModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                page++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        refreshLayout.setRefreshHeader(new ClassicsHeader(MyVideosActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(MyVideosActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.myVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myVideo))
                        .addParam("userID", spImp.getUID())
                        .addParam("page",1+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") ==  200){
                                        Gson gson = new Gson();
                                        MyVideosModel model = gson.fromJson(data,MyVideosModel.class);
                                        list.clear();
                                        list.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page++;
                                    }
                                    refreshLayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    refreshLayout.finishRefresh(1000);
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
                ViseHttp.POST(NetConfig.myVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myVideo))
                        .addParam("userID", spImp.getUID())
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") ==  200){
                                        Gson gson = new Gson();
                                        MyVideosModel model = gson.fromJson(data,MyVideosModel.class);
                                        list.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page++;
                                    }
                                    refreshLayout.finishLoadMore(1000);
                                } catch (JSONException e) {
                                    refreshLayout.finishLoadMore(1000);
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

    @OnClick({R.id.activity_my_videos_rl_back,R.id.activity_my_videos_rl_add_video})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.activity_my_videos_rl_back:
                finish();
                break;
            case R.id.activity_my_videos_rl_add_video:
                Intent intent = new Intent();
                intent.setClass(MyVideosActivity.this, CreateYouJiActivity.class);
                intent.putExtra(CreateYouJiActivity.ONLY_ADD_VIDEO,true);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditVideoTitleActivity.REQUEST_CODE && resultCode == EditVideoTitleActivity.RESULT_CODE){
            list.remove(data.getIntExtra(EditVideoTitleActivity.RESULT_CHANGE_POSITION_KEY,-1));
            list.add(data.getIntExtra(EditVideoTitleActivity.RESULT_CHANGE_POSITION_KEY,-1),
                    (MyVideosModel.ObjBean)data.getSerializableExtra(EditVideoTitleActivity.RESULT_CHANGE_DATA));
            adapter.notifyDataSetChanged();
        }
    }
}
