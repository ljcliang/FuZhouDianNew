package com.yiwo.fuzhoudian.pages.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;
import com.yiwo.fuzhoudian.imagepreview.Consts;
import com.yiwo.fuzhoudian.imagepreview.ImagePreviewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeiRenZhengOrderWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_weidenglu_web);
        ButterKnife.bind(this);
        initIntentSonic(getIntent().getStringExtra("url"), mWebView);
        mWebView.addJavascriptInterface(new AndroidInterface(),"android");//交互
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void lookpostpicture(String json,String index){
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
                intent1.setClass(WeiRenZhengOrderWebActivity.this, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, WeiRenZhengOrderWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick(R.id.rl_return)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
        }
    }
}
