package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class ShopHomeGoodsAdapter extends RecyclerView.Adapter<ShopHomeGoodsAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    public ShopHomeGoodsAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shop_home_shangpin_item, parent, false);
        ShopHomeGoodsAdapter.ViewHolder holder = new ShopHomeGoodsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
