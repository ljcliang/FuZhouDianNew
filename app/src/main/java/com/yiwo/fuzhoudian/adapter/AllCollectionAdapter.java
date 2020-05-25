package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.model.UserCollectionModel;
//import com.yiwo.fuzhoudian.webpages.DetailsOfFriendTogetherWebActivity;
//import com.yiwo.fuzhoudian.webpages.DetailsOfFriendsWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class AllCollectionAdapter extends RecyclerView.Adapter<AllCollectionAdapter.ViewHolder> {

    private Context context;
    private List<UserCollectionModel.ObjBean> data;
    private OnCancelListener listener;

    public void setListener(OnCancelListener listener) {
        this.listener = listener;
    }

    public AllCollectionAdapter(List<UserCollectionModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_all_collection, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFtitle());
        holder.tv_name.setText(data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        if (data.get(position).getIfcaptain().equals("1")){
            holder.iv_level.setVisibility(View.VISIBLE);
            switch (data.get(position).getLevelName()){
                case "0":
                    holder.iv_level.setImageResource(R.mipmap.shop_lv_tong);
                    break;
                case "1":
                    holder.iv_level.setImageResource(R.mipmap.shop_lv_yin);
                    break;
                case "2":
                    holder.iv_level.setImageResource(R.mipmap.shop_lv_jin);
                    break;
                case "3":
                    holder.iv_level.setImageResource(R.mipmap.shop_lv_zuanshi);
                    break;
//                case "4":
//                    holder.iv_level.setImageResource(R.mipmap.level_zuanshi);
//                    break;
//                case "5":
//                    holder.iv_level.setImageResource(R.mipmap.level_huangguan);
//                    break;
            }
        }else {
            holder.iv_level.setVisibility(View.GONE);
        }
        if (data.get(position).getInsertatext().equals("1")){
            holder.rl_canyuxiezuo.setVisibility(View.GONE);
        }else {
            holder.rl_canyuxiezuo.setVisibility(View.VISIBLE);
        }
        holder.tv_canyuxiezuo_num.setText("团友共有"+data.get(position).getInNum()+"人参与写作");
        holder.tv_fabu_time.setText("发表时间："+data.get(position).getFtime());
        holder.tv_xiangguanhuodong.setText("关联活动："+data.get(position).getPftitle());
        holder.tvLookNum.setText(data.get(position).getFmlook()+"人阅读了这篇友记");
        Glide.with(context).load(data.get(position).getFpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                if(data.get(position).getFtype().equals("1")){
//                    intent.putExtra("pfID", data.get(position).getFtableid());
//                    intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
//                    context.startActivity(intent);
//                }else {
//                    intent.putExtra("fmid", data.get(position).getFtableid());
//                    intent.setClass(context, DetailsOfFriendsWebActivity.class);
//                    context.startActivity(intent);
//                }
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle,tv_name,tv_level,tv_fabu_time,tv_xiangguanhuodong,tv_canyuxiezuo_num;
        private TextView tvLookNum;
        private ImageView iv,iv_head,iv_level;
        private LinearLayout ll;
        private RelativeLayout rl_canyuxiezuo;
        private TextView tvCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_fabu_time = itemView.findViewById(R.id.tv_fabu_time);
            tv_xiangguanhuodong = itemView.findViewById(R.id.tv_xiangguanhuodong);
            tv_canyuxiezuo_num = itemView.findViewById(R.id.tv_canyuxiezuo_num);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_level = itemView.findViewById(R.id.tv_level);
            iv_level = itemView.findViewById(R.id.iv_level);
            tvLookNum = itemView.findViewById(R.id.tv_look_num);
            rl_canyuxiezuo = itemView.findViewById(R.id.rl_canyuxiezuo);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
        }
    }

    public interface OnCancelListener{
        void onCancel(int i);
    }

}
