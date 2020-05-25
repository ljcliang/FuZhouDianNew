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

public class HomeZhouBianLabelAdapter extends RecyclerView.Adapter<HomeZhouBianLabelAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
//    private SpImp spImp;
    public HomeZhouBianLabelAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_zhoubian_dianpu_label_item, parent, false);
        HomeZhouBianLabelAdapter.ViewHolder holder = new HomeZhouBianLabelAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    public interface OnSureClickListenner{
        void onSure();
    }
}
