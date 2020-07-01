package com.yiwo.fuzhoudian.pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.ArticleCommentVideoAdapter;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.imagepreview.StatusBarUtils;
import com.yiwo.fuzhoudian.model.ActicleCommentVideoModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.model.YouJiListModel;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.tongban_emoticon.TbEmoticonFragment;
import com.yiwo.fuzhoudian.utils.ShareUtils;
import com.yiwo.fuzhoudian.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends FragmentActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.player)
    IjkVideoView ijkVideoView;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.rl_active)
    RelativeLayout rlActive;

    @BindView(R.id.ll_btns)
    LinearLayout ll_btns;
    @BindView(R.id.tv_pinglun_num)
    TextView tv_pinglun_num;

    @BindView(R.id.ll_edt)
    LinearLayout ll_edt;
    @BindView(R.id.ll_pinglun)
    LinearLayout ll_pinglun;
    @BindView(R.id.activity_video_rv)
    RecyclerView rv_pinglun;
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;
    private PopupWindow popupWindow;
    private String vid;
    private String url ;
    private String title ;
    private String picUrl;

    private SpImp spImp;
    private String uid = "";

    private boolean isComment = true;//评论  还是回复  true：评论；false 回复
    private String vcID = "";//当前欲回复评论的ID
    private int vPostion;//当前欲回复评论的位置
    private String vPingLunPageVID;//分页ID
    public YouJiListModel.ObjBean mode;//视频mode
//    private EmotionMainFragment emotionMainFragment;
    private TbEmoticonFragment emotionMainFragment;
    private Dialog dialog;
    private StandardVideoController controller;
    private boolean isYouJuVideo = false;

    //评论列表
    private ArticleCommentVideoAdapter articleCommentVideoAdapter;
    private List<ActicleCommentVideoModel.ObjBean> data_pinglun = new ArrayList<>();
    private int pinglun_page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        StatusBarUtils.setStatusBarTransparent(this);
        ButterKnife.bind(this);
        spImp = new SpImp(VideoActivity.this);
        uid = spImp.getUID();
        initData();
        if (!isYouJuVideo){
            initPingLun();
        }
        initEmotionMainFragment();
        ll_edt.setVisibility(View.INVISIBLE);
    }

    private void initPingLun() {

        LinearLayoutManager manager = new LinearLayoutManager(VideoActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pinglun.setLayoutManager(manager);
        articleCommentVideoAdapter = new ArticleCommentVideoAdapter(data_pinglun);
        articleCommentVideoAdapter.setOnReplyListener(new ArticleCommentVideoAdapter.OnReplyListener() {
            @Override
            public void onReply(int position, String id) {
                vcID = id;
                vPostion = position;
                isComment = false;
                ll_edt.setVisibility(View.VISIBLE);
                emotionMainFragment.showKeyBoard();
                emotionMainFragment.setHint("回复:"+data_pinglun.get(position).getUsername());
            }
        });
        articleCommentVideoAdapter.setDeletePinLunLis(new ArticleCommentVideoAdapter.OnDeletePinLun() {
            @Override
            public void onDeletePinLun(final String id, String content) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
                builder.setMessage("是否删除“"+content+"”")
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePinglun(id);
                            }
                        }).setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        rv_pinglun.setAdapter(articleCommentVideoAdapter);
        ViseHttp.POST(NetConfig.videoReviewsList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                .addParam("vID",vid)
                .addParam("page","")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                data_pinglun.clear();
                                data_pinglun.addAll(model.getObj());
                                articleCommentVideoAdapter.notifyDataSetChanged();
                                if (data_pinglun.size()>0){
                                    vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                }else {
                                    vPingLunPageVID = "";
                                }
                                pinglun_page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        refresh_layout.setRefreshHeader(new ClassicsHeader(VideoActivity.this));
        refresh_layout.setRefreshFooter(new ClassicsFooter(VideoActivity.this));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.videoReviewsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                        .addParam("vID",vid)
                        .addParam("page","")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                        data_pinglun.clear();
                                        data_pinglun.addAll(model.getObj());
                                        articleCommentVideoAdapter.notifyDataSetChanged();
                                        if (data_pinglun.size()>0){
                                            vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                        }else {
                                            vPingLunPageVID = "";
                                        }
                                        pinglun_page = 2;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refresh_layout.finishRefresh(1000);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refresh_layout.finishRefresh(1000);
                            }
                        });
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.videoReviewsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                        .addParam("vID",vid)
                        .addParam("page",vPingLunPageVID+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                        data_pinglun.addAll(model.getObj());
                                        articleCommentVideoAdapter.notifyDataSetChanged();
                                        if (data_pinglun.size()>0){
                                            vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                        }else {
                                            vPingLunPageVID = "";
                                        }
                                        pinglun_page++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refresh_layout.finishLoadMore(1000);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refresh_layout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }

    private void deletePinglun(String id) {
        ViseHttp.POST(NetConfig.managerComments)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.managerComments))
                .addParam("type", "1")
                .addParam("delID",id)
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(VideoActivity.this,"删除成功");
//                                initData();
                                initPingLun();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(VideoActivity.this,"删除失败："+errCode+"/"+errMsg);
                    }
                });
    }

    private void initData() {
        mode = (YouJiListModel.ObjBean) getIntent().getSerializableExtra("data");
        if (mode!=null){
            url = mode.getVurl();
            title = mode.getFmtitle();
            vid = mode.getFmID();
            picUrl = mode.getFmpic();
        }else {
            url = getIntent().getStringExtra("videoUrl");
            title = getIntent().getStringExtra("title")!=null?getIntent().getStringExtra("title"):null;
            vid = getIntent().getStringExtra("vid")!=null?getIntent().getStringExtra("vid"):null;
            picUrl = getIntent().getStringExtra("picUrl")!=null?getIntent().getStringExtra("picUrl"):null;
            //注意：友聚活动WEB页播放视频只有视频链接
            if (vid == null||title == null||picUrl ==null){
                isYouJuVideo = true;
                ll_btns.setVisibility(View.GONE);
                rlActive.setVisibility(View.GONE);
            }else {
                isYouJuVideo = false;
                ll_btns.setVisibility(View.VISIBLE);
                rlActive.setVisibility(View.VISIBLE);
                Log.d("vidvidvid",vid);
            }
        }
        if (!isYouJuVideo){
            ViseHttp.POST(NetConfig.videoNumInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoNumInfo))
                    .addParam("vID",vid)
                    .addParam("uid",spImp.getUID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    Log.d("codecode",data);
                                    String status = jsonObject.getJSONObject("obj").getString("status");
                                    String zan_num = jsonObject.getJSONObject("obj").getString("praise_num");
                                    String pinglun_num = jsonObject.getJSONObject("obj").getString("comment_num");
                                    tv_pinglun_num.setText(pinglun_num);
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
        tvVideoTitle.setText(title == null?"":title);

        //高级设置（可选，须在start()之前调用方可生效）
//        PlayerConfig playerConfig = new PlayerConfig.Builder()
//                .enableCache() //启用边播边缓存功能
////                .autoRotate() //启用重力感应自动进入/退出全屏功能
////                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
//                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
//                .savingProgress() //保存播放进度
//                .disableAudioFocus() //关闭AudioFocusChange监听
//                .setLooping() //循环播放当前正在播放的视频
//                .build();
//        ijkVideoView.(playerConfig);
        ijkVideoView.setUrl(url); //设置视频地址
//        ijkVideoView.setTitle(title); //设置视频标题
         controller = new StandardVideoController(this);
         ijkVideoView.setLooping(true);
        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        ijkVideoView.start(); //开始播放，不调用则不自动播放

    }
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        emotionMainFragment = TbEmoticonFragment.newInstance(TbEmoticonFragment.class,new Bundle());
        emotionMainFragment.setCommitListenner(new TbEmoticonFragment.OnCommitListenner() {
            @Override
            public void onCommitListen(String string) {
                if (TextUtils.isEmpty(string)) {
                    toToast(VideoActivity.this, "请输入评论...");
                } else {
                    if (isComment){
                        toComment(string);
                    }else {
                        toReplay(emotionMainFragment.getEdt(),vcID,vPostion);
                    }
                }
            }
        });
        transaction.replace(R.id.fl_emotionview_main,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }
    @OnClick({R.id.iv_back, R.id.rl_active,R.id.iv_pinglun,R.id.iv_close,R.id.rl_back})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_active:
//                showActivePopupwindow();
                break;
            case R.id.iv_pinglun:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(VideoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                startPingLunShowAnim();
                break;
            case R.id.iv_close:
                startPingLunHideAnim();
                break;
        }
    }
    private void toZan() {
        dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
        ViseHttp.POST(NetConfig.videoPraise)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoPraise))
                .addParam("id", vid)
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("aaaaaa",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String status = jsonObject.getJSONObject("obj").getString("status");
                                String num = jsonObject.getJSONObject("obj").getString("num");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        WeiboDialogUtils.closeDialog(dialog);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                        toToast(VideoActivity.this,"操作失败！");
                    }
                });
    }

    private void startPingLunShowAnim(){
        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(500l);     //设置动画的过渡时间
        ll_pinglun.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_pinglun.setVisibility(View.VISIBLE);
                ll_pinglun.startAnimation(ctrlAnimation);
            }
        }, 200);

    }
    private void startPingLunHideAnim(){
        //关闭键盘 和表情fragment
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        emotionMainFragment.goneKeyboard();
//        emotionMainFragment.clearEdt();
        isComment = true;
        emotionMainFragment.setHint("请输入评论...");
//        /设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
        ctrlAnimation.setDuration(500l);     //设置动画的过渡时间
        ll_pinglun.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_pinglun.setVisibility(View.GONE);
                ll_pinglun.startAnimation(ctrlAnimation);
            }
        }, 200);
    }
    /**
     * 提交评论
     */
    private void toComment(final String comment) {
        if (TextUtils.isEmpty(comment)){
            toToast(VideoActivity.this,"请输入内容");
            return;
        }
            dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
            ViseHttp.POST(NetConfig.videoReviews)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviews))
                    .addParam("id", vid)
                    .addParam("fctitle", comment)
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            WeiboDialogUtils.closeDialog(dialog);
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    toToast(VideoActivity.this, "评论成功");
                                    emotionMainFragment.goneKeyboard();
                                    emotionMainFragment.clearEdt();
                                    Gson gson = new Gson();
                                    ActicleCommentVideoModel.ObjBean model = gson.fromJson(jsonObject.getJSONObject("obj").toString(),ActicleCommentVideoModel.ObjBean.class);
                                    data_pinglun.add(0,model);
                                    articleCommentVideoAdapter.notifyDataSetChanged();
                                    rv_pinglun.scrollToPosition(0);
                                }else {
                                    toToast(VideoActivity.this, "评论失败");
                                }
                            } catch (JSONException e) {
                                toToast(VideoActivity.this, "评论失败");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                            toToast(VideoActivity.this, "评论失败"+errCode+":"+errMsg);
                        }
                    });
    }
    //回复评论
    private void toReplay(EditText editText, String vcID, final int pingLunPostion){
        if (TextUtils.isEmpty(editText.getText().toString())){
            toToast(VideoActivity.this,"请输入内容");
            return;
        }
        dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
        ViseHttp.POST(NetConfig.replyVideoReviews)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.replyVideoReviews))
                .addParam("userID", uid)
                .addParam("vcID", vcID)
                .addParam("vinfo", editText.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        WeiboDialogUtils.closeDialog(dialog);
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                toToast(VideoActivity.this, "回复成功");
                                emotionMainFragment.goneKeyboard();
                                emotionMainFragment.clearEdt();
                                Gson gson = new Gson();
                                ActicleCommentVideoModel.ObjBean.ReplyListBean bean = gson.fromJson(jsonObject.getJSONObject("obj").toString(),ActicleCommentVideoModel.ObjBean.ReplyListBean.class);
                                data_pinglun.get(pingLunPostion).getReplyList().add(bean);
                                articleCommentVideoAdapter.notifyDataSetChanged();
                                rv_pinglun.scrollToPosition(pingLunPostion);
                                isComment = true;
                                emotionMainFragment.setHint("请输入评论...");
                                emotionMainFragment.clearEdt();
                            }else {
                                toToast(VideoActivity.this, "回复失败");
                            }
                        } catch (JSONException e) {
                            toToast(VideoActivity.this, "回复失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
//                        isComment = true;
//
//                        emotionMainFragment.clearEdt();
                        toToast(VideoActivity.this, "回复失败"+errCode+":"+errMsg);
                    }
                });
    }
    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN&&ll_pinglun.isShown()&&!isTouchView(new View[]{ll_pinglun},event)){
//            startPingLunHideAnim();
        }
        if (event.getAction() == MotionEvent.ACTION_UP&&!isTouchView(new View[]{ll_btns,ivBack,rlActive,rl_back},event)){
            LinearLayout bottomContainer = controller.findViewById(com.dueeeke.videocontroller.R.id.bottom_container);
            ll_btns.setVisibility(bottomContainer.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            ivBack.setVisibility(bottomContainer.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            rlActive.setVisibility(bottomContainer.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            if (!isYouJuVideo){
                ll_btns.setVisibility(bottomContainer.getVisibility() == View.GONE ? View.VISIBLE:View.GONE);
                rlActive.setVisibility(bottomContainer.getVisibility() == View.GONE ? View.VISIBLE:View.GONE);
            }
            ivBack.setVisibility(bottomContainer.getVisibility() == View.GONE ? View.VISIBLE:View.GONE);

//            if (bottomContainer == View.GONE){
//                controller.hide();
//            }else {
//                controller.show();
//            }
        }
        return super.dispatchTouchEvent(event);
    }

    //是否触摸在指定view上面,对某个控件过滤
     public boolean isTouchView(View[] views, MotionEvent ev) {
        if(views ==null|| views.length==0)
            return false;
        int[] location =new int[2];
        for(View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(ev.getX() > x && ev.getX() < (x + view.getWidth())&& ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }return false;
    }
    public void toToast(Context c, String content){
        Toast.makeText(c,content,Toast.LENGTH_SHORT).show();
    }
    public String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }
    private void deleteYouJiOrVideo(final String type, final String delID) {// 传type 0删除游记 1删除视频  delID要删除的ID   userID登录用户的ID
        Log.d("删除视频：：",""+delID);
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
        builder.setMessage("确定删除此视频？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ViseHttp.POST(NetConfig.managerInfo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.managerInfo))
                        .addParam("type", type)
                        .addParam("delID",delID)
                        .addParam("userID",spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        toToast(VideoActivity.this,"删除成功！");
                                        Intent intent = new Intent();
                                        intent.putExtra("deleteID",vid);
                                        intent.setAction("android.friendscometogether.HomeFragment.Video");
//                                        intent.setAction("android.friendscometogether.HomeFragment.GuanZhu");
//                                        intent.setAction("android.friendscometogether.HomeFragment.TuiJian_Youji");
                                        //发送广播
                                        sendBroadcast(intent);
                                        finish();
                                        finish();
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
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
