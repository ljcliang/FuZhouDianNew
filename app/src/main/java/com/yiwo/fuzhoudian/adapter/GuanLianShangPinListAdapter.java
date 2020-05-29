package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.model.GuanLianShangPinModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class GuanLianShangPinListAdapter extends RecyclerView.Adapter<GuanLianShangPinListAdapter.ViewHolder> {

    private Context context;
    private List<GuanLianShangPinModel.ObjBean> data;
    private ItemClickListionner listionner;
    public GuanLianShangPinListAdapter(List<GuanLianShangPinModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_guan_lian_shang_pin, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getGoodsName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listionner.onClick(position);
//                ac.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListionner(ItemClickListionner listionner) {
        this.listionner = listionner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface ItemClickListionner{
        void onClick(int postion);
    }
}
