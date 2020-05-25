package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeGuanZhuDianPuAdapter extends RecyclerView.Adapter<HomeGuanZhuDianPuAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private Add2CartListenner add2CartListenner;
//    private SpImp spImp;
    public HomeGuanZhuDianPuAdapter(List<String> data,Add2CartListenner listenner){
        this.data = data;
        this.add2CartListenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_guanzhu_dianpu_item, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeGuanZhuDianPuAdapter.ViewHolder holder = new HomeGuanZhuDianPuAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        HomeGuanZhuDianPuShangPinAdapter shangPinAdapter = new HomeGuanZhuDianPuShangPinAdapter(datas, new HomeGuanZhuDianPuShangPinAdapter.AddClickListenner() {
            @Override
            public void addListen(int i, ImageView ivGoods) {
                add2CartListenner.addGoods(position,ivGoods);
            }
        });
        holder.rv.setLayoutManager(manager);
        holder.rv.setAdapter(shangPinAdapter);
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_head;
        TextView tv_shop_name,tv_level,tv_shop_tel,tv_address,tv_juli;
        ImageView iv_level;
        RecyclerView rv;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_shop_tel = itemView.findViewById(R.id.tv_shop_tel);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_juli = itemView.findViewById(R.id.tv_juli);
            iv_level = itemView.findViewById(R.id.iv_level);
            rv = itemView.findViewById(R.id.rv);
        }
    }
    public interface Add2CartListenner{
        void addGoods(int pos,ImageView addGoodIv);
    }
}
