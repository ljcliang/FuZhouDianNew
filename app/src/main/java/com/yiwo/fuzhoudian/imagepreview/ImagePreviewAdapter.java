package com.yiwo.fuzhoudian.imagepreview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.imagepreview.glideprogress.ProgressInterceptor;
import com.yiwo.fuzhoudian.imagepreview.glideprogress.ProgressListener;
import com.yiwo.fuzhoudian.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class ImagePreviewAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageList;
    private int itemPosition;
    private PhotoView photoView;
    public ImagePreviewAdapter(Context context, List<String> imageList, int itemPosition) {
        this.context = context;
        this.imageList = imageList;
        this.itemPosition = itemPosition;
    }

    @Override
    public int getCount() {
        return imageList==null?0:imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final PhotoView image = new PhotoView(context);
        image.setEnabled(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setMaximumScale(10.0F);
        image.setMinimumScale(0.8F);

//        ImageView imageView = new ImageView(context);
//        Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.image_preview_loading_rotate);
//        LinearInterpolator interpolator = new LinearInterpolator();
//        rotateAnimation.setInterpolator(interpolator);
//        imageView.startAnimation(rotateAnimation);

//        final ObjectAnimator anim = ObjectAnimator.ofInt(image, "ImageLevel", 0,3000);

//        final ObjectAnimator anim = ObjectAnimator.ofFloat(image,"rotation",0,359);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setDuration(3000);
//        anim.setRepeatCount(ObjectAnimator.INFINITE);
//
//        anim.start();
        ProgressInterceptor.addListener(imageList.get(position), new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.d("IMAGEPREVIEW::", "onProgress: " + progress);
            }
        });
//        Glide.with(context).load(imageList.get(position)).apply(options).thumbnail(Glide.with(imageView).load(placeholder == 0 ? R.drawable.img_loading : placeholder)).into(imageView);
        Glide.with(context).load(imageList.get(position))
                .apply(new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不使用缓存
//                .skipMemoryCache(true)
//                .format(DecodeFormat.PREFER_ARGB_8888)//设置图片解码格式
//                .placeholder(R.drawable.laotou)
                )
                .thumbnail(Glide.with(context).load(R.drawable.loadgif))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d("IMAGE", "onException: ");
//                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("IMAGE", "onResourceReady: ");
//                        anim.cancel();
                        return false;
                    }
                })
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setEnabled(false);
                ((Activity)context).onBackPressed();
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定保存图片到本地？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                download(imageList.get(position));
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });
        container.addView(image);
        return image;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        photoView = (PhotoView) object;
//        photoView.setTag(Utils.getNameByPosition(itemPosition,position));
//        photoView.setTransitionName(Utils.getNameByPosition(itemPosition,position));
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public PhotoView getPhotoView() {
        return photoView;
    }
    // 保存图片到手机
    public void download(final String url) {

        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
                    FutureTarget<File> future = Glide
                            .with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
//                    File pictureFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);;
                    File appDir = new File(pictureFolder ,"Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = "瞳伴图片_"+System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    FileUtils.copy(file, destFile);
                    // 最后通知图库更新
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));


                } catch (Exception e) {
                    Log.e("123132", e.getMessage());
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }
}
