package com.yiwo.fuzhoudian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AndTools {

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
 
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }

    /**
     * 在某个Activity中隐藏输入法
     * 
     * @param context
     */
    public static void hideIME(Activity context) {
        try {
            ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception npe) {
            ;
        }
    }
    public static String getDeviceId(Activity context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return verName;
    }


    /**
     * 显示键盘
     * @param view
     */
    public static void showKeyboard(Activity context, View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Activity context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 获取键盘是否已经打开
     */
    public static boolean isKeyboardShowing(Activity context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        return imm.isAcceptingText();
    }
    
    private static PowerManager.WakeLock m_wakeLockObj = null;

    public static void ReleaseWakeLock() {
        if (m_wakeLockObj != null && m_wakeLockObj.isHeld()) {
            m_wakeLockObj.release();
        }
        m_wakeLockObj = null;
    }
    
    
    
    /** 判断手机是否root，不弹出root请求框<br/> */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    //未root手机，大部分手机没有这两个文件夹，小米手机有这个文件夹,因此还需要判断文件拥有者对这个文件是否具有可执行权限
    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(p!=null){
                p.destroy();
            }
        }
        return false;
    }

    /**
     * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param target
     * 需要转化bitmap参数
     * @param newWidth
     * 设置新的宽度
     * @return
     */
    public static Bitmap fitBitmap(Bitmap target, int newWidth)
    {
        if(target == null){
            return null;
        }
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix,true);
        return bmp;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (String param : arr) {
            try{
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
    //保存网络图片到相册
    public static void saveImageUrlToGallery( final Context context, String url) {
        Subscription subscribe = Observable.just(url).map(new Func1<String, File>() {
            @Override
            public File call(String s) {
                File file = null;
                try {
                    file = Glide.with(context).load(s).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return file;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        if (file != null) {
                            saveImageToGallery(context, file.getAbsolutePath());
                            Toast.makeText(context,"已保存至相册",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //保存图片到相册
    public static void saveImageToGallery(final Context context, String path) {
        File file = new File(path);
        //其次把文件插入到系统图库
        try {
            String filename = path.substring(path.lastIndexOf('/') + 1);
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), filename, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(uri);
                            context.sendBroadcast(mediaScanIntent);
                        }
                    });
        } else {
            String relationDir = file.getParent();
            File file1 = new File(relationDir);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file1.getAbsoluteFile())));
        }
    }


    public static void saveScreenShot(final Context context,Bitmap bitmap)  {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        String filename;//声明文件名
        // 以保存时间为文件名
         Date date = new Date(System.currentTimeMillis());
         SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
         filename =  sdf.format(date);
         File file = new File(extStorageDirectory, filename+".JPEG");
         //创建文件，第一个参数为路径，第二个参数为文件名
         try {
             outStream = new FileOutputStream(file);//创建输入流
             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
             outStream.close();
//             /**       这三行可以实现相册更新
               Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
               Uri uri = Uri.fromFile(file);intent.setData(uri);
             context.sendBroadcast(intent);
//               /这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！*/
             Toast.makeText(context,"已保存至相册！",
                     Toast.LENGTH_SHORT).show();
         } catch(Exception e) {
             Toast.makeText(context, "exception:" + e,
                     Toast.LENGTH_SHORT).show();
         }
    }
}

