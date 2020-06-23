package com.yiwo.fuzhoudian.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TiXianShuoMingActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.btn_last_page)
    TextView btnLastPage;
    @BindView(R.id.btn_next_page)
    TextView btnNextPage;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<View> viewList = new ArrayList<>();
    private int[] images = new int[]{R.mipmap.tixian_1,R.mipmap.tixian_2,R.mipmap.tixian_3,R.mipmap.tixian_4,R.mipmap.tixian_5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xian_shuo_ming);
        ButterKnife.bind(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0;i<images.length;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        vp.setAdapter(new MyViewPager());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    btnLastPage.setVisibility(View.GONE);
                }else {
                    btnLastPage.setVisibility(View.VISIBLE);
                }
                if (i == viewList.size() -1){
                    btnNextPage.setVisibility(View.GONE);
                }else {
                    btnNextPage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vp.setCurrentItem(0);
    }

    @OnClick({R.id.rl_back, R.id.btn_last_page, R.id.btn_next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_last_page:
                last();
                break;
            case R.id.btn_next_page:
                next();
                break;
        }
    }
    private void next(){
        if (vp.getCurrentItem()<viewList.size()-1){
            vp.setCurrentItem(vp.getCurrentItem()+1);
        }else {
            toToast(TiXianShuoMingActivity.this,"已经是最后一张了");
        }
//        if (vp.getCurrentItem() == viewList.size() - 1){
//            btnLastPage.setVisibility(View.GONE);
//        }else {
//            btnLastPage.setVisibility(View.VISIBLE);
//        }
    }
    private void last(){
        if (vp.getCurrentItem()>0){
            vp.setCurrentItem(vp.getCurrentItem()-1);
        }else {
            toToast(TiXianShuoMingActivity.this,"已经是第一张了");
        }
//        if (vp.getCurrentItem() == 0){
//            btnLastPage.setVisibility(View.GONE);
//        }else {
//            btnLastPage.setVisibility(View.VISIBLE);
//        }
    }
    class MyViewPager extends PagerAdapter{

        @Override
        public int getCount() {
            if (viewList != null){
                return viewList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
