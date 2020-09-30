package com.yiwo.fuzhoudian.pages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SAOYISAOActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_a_o_y_i_s_a_o);
        ButterKnife.bind(this);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
//        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
//            @Override
//            public void callBack(Exception e) {
//                if (e == null) {
//
//                } else {
//                    Log.e("TAG", "callBack: ", e);
//                }
//            }
//        });
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            SAOYISAOActivity.this.setResult(RESULT_OK, resultIntent);
            SAOYISAOActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            SAOYISAOActivity.this.setResult(RESULT_OK, resultIntent);
            SAOYISAOActivity.this.finish();
        }
    };

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
