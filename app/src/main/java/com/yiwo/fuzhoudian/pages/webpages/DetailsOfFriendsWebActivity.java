package com.yiwo.fuzhoudian.pages.webpages;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;
import com.yiwo.fuzhoudian.imagepreview.Consts;
import com.yiwo.fuzhoudian.imagepreview.ImagePreviewActivity;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.VideoActivity;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsOfFriendsWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.progresss_bar)
    ProgressBar progresss_bar;
    private String fmID;
    private String uid;
    private SpImp spImp;
    private String url;
    private boolean isFocus = false;
    private boolean isPraise = false;
    private boolean isStar = false;

    private SQLiteDatabase db;

    private String[] arrMuLuTitle;
    private PopupWindow popupWindow;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friends_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        fmID= getIntent().getStringExtra("fmid");
        spImp = new SpImp(DetailsOfFriendsWebActivity.this);
        uid = spImp.getUID();
        url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+fmID+"&uid="+uid;
        initIntentSonic(url,webView);
        Log.d("aaaa",url);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    progresss_bar.setVisibility(View.GONE);//加载完网页进度条消失
                    }else{
                    progresss_bar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progresss_bar.setProgress(newProgress);//设置进度值				}
                    }
            }
        });
        webView.addJavascriptInterface(new AndroidInterface(),"android");//交互
    }

    @Override
    protected void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }
    /**
     * 初始化表情面板
     */
    @OnClick({R.id.activity_details_of_friends_rl_back})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_details_of_friends_rl_back:
                onBackPressed();
                break;
        }
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void lookpostpicture(String json,String index){
//            [{"id":"958","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad155365614111.jpg","desc":""},
//              {"id":"959","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141238.jpg","desc":""},
//              {"id":"960","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141899.jpg","desc":""}]
            String strJson = new String(Base64.decode(json,Base64.DEFAULT));
            Log.d("交互了","json::"+json+"////\r\nindex::"+index);
            int position = Integer.parseInt(index);
            Log.d("交互解码了","json::"+strJson+"////\r\nindex::"+index);
            List<String> listPics = new ArrayList<>();
            List<String> listContent = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listPics.add(jsonObject.getString("imgurl"));
                    listContent.add(jsonObject.getString("desc"));
                }
                Intent intent1 = new Intent();
                intent1.setClass(DetailsOfFriendsWebActivity.this, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
//                isHasImageContent = getIntent().getBooleanExtra("hasImageContent",false);
//                if (isHasImageContent){
//                    contentList = getIntent().getStringArrayListExtra("imageContenList");
//                }
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        @JavascriptInterface
//        public void userinfo(String uid){
//            Intent intent = new Intent();
//            intent.setClass(DetailsOfFriendsWebActivity.this, PersonMainActivity1.class);
//            intent.putExtra("person_id", uid);
//            startActivity(intent);
//        }
//        @JavascriptInterface
//        public void aboutactivity(String pfID){//相关活动
//            //相关活动跳转
//            Intent intent = new Intent();
//            intent.putExtra("pfID", pfID);
//            intent.setClass(DetailsOfFriendsWebActivity.this, DetailsOfFriendTogetherWebActivity.class);
//            startActivity(intent);
//        }
//        @JavascriptInterface
//        public void jumpactivity(String pfID){//相关活动
//            //相关活动跳转
//            Intent intent = new Intent();
//            intent.putExtra("pfID", pfID);
//            intent.setClass(DetailsOfFriendsWebActivity.this, DetailsOfFriendTogetherWebActivity.class);
//            startActivity(intent);
//        }
//        @JavascriptInterface
//        public void pinglun(){//评论跳转
//            Intent intent = new Intent();
//            if (TextUtils.isEmpty(uid) || uid.equals("0")) {
//                intent.setClass(DetailsOfFriendsWebActivity.this, LoginActivity.class);
//                startActivity(intent);
//            } else {
//                intent.setClass(DetailsOfFriendsWebActivity.this, ArticleCommentActivity.class);
//                intent.putExtra("id", fmID);
//                startActivity(intent);
//            }
//        }
//        @JavascriptInterface
//        public void reportuser(String uId,String pId){//举报  评论人 的ID，评论ID
//            Intent intent = new Intent();
//            intent.setClass(DetailsOfFriendsWebActivity.this, JuBaoActivity.class);
//            intent.putExtra("pfID",pId);
//            intent.putExtra("reportUserID",uId);
//            intent.putExtra("type","3");
//            startActivity(intent);
//        }
        @JavascriptInterface
        public void jumpyouji(String fmID){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendsWebActivity.this, DetailsOfFriendsWebActivity.class);
            intent.putExtra("fmid", fmID);
            startActivity(intent);
        }
        @JavascriptInterface
        public void playVideo(String vid,String vname,String img,String vurl){
            startVideoACtivity(vid,vname,img,vurl);
        }
    }
    private void startVideoACtivity(String vid,String vname,String img,String vurl){
        Intent it = new Intent(DetailsOfFriendsWebActivity.this, VideoActivity.class);
        it.putExtra("videoUrl", vurl);
        it.putExtra("title", vname);
        it.putExtra("picUrl", img);
        it.putExtra("vid", vid);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
