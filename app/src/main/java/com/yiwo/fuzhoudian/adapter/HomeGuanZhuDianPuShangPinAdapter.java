package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeGuanZhuDianPuShangPinAdapter extends RecyclerView.Adapter<HomeGuanZhuDianPuShangPinAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private AddClickListenner listenner;
//    private SpImp spImp;
    public HomeGuanZhuDianPuShangPinAdapter(List<String> data,AddClickListenner listenner){
        this.data = data;
        this.listenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_guanzhu_dianpu_shangpin_item, parent, false);
        HomeGuanZhuDianPuShangPinAdapter.ViewHolder holder = new HomeGuanZhuDianPuShangPinAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.home_goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.addListen(position,holder.home_goods_add);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView home_goods_add,iv;
        public ViewHolder(View itemView) {
            super(itemView);
            home_goods_add = itemView.findViewById(R.id.iv_add);
            iv = itemView.findViewById(R.id.iv);

        }
    }
    public interface AddClickListenner{
        void addListen(int i,ImageView ivGoods);
    }
}
