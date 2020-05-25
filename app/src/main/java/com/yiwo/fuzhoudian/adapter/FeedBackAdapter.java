package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.model.FeedBackModel;
import com.yiwo.fuzhoudian.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 * 意见反馈页面适配器
 */

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder> {
    private Context context;
    private List<FeedBackModel.ObjBean> data;
    SpImp spImp;

    public FeedBackAdapter(List<FeedBackModel.ObjBean> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_feedback, parent, false);
        this.context = parent.getContext();
        spImp = new SpImp(parent.getContext());
        ScreenAdapterTools.getInstance().loadView(view);
        FeedBackAdapter.ViewHolder holder = new FeedBackAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data.get(position).getBacktitle()==null||data.get(position).getBacktitle().equals("")){
            holder.tvReplay.setText("(暂未回复)");
        }else {
            holder.tvReplay.setText("回复\n"+data.get(position).getBacktitle());
        }
        holder.tvFanKui.setText(data.get(position).getFtitle());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFanKui;
        TextView tvReplay;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFanKui = itemView.findViewById(R.id.tv_fankui);
            tvReplay = itemView.findViewById(R.id.tv_replay);
        }
    }
}
