package com.yiwo.fuzhoudian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeZhouBianDianPuAdapter extends RecyclerView.Adapter<HomeZhouBianDianPuAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
//    private SpImp spImp;
    public HomeZhouBianDianPuAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_zhoubian_dianpu_item, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeZhouBianDianPuAdapter.ViewHolder holder = new HomeZhouBianDianPuAdapter.ViewHolder(view);
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
        ImageView iv;
        TextView tv_name,tv_info,tv_address,tv_guanzhu,tv_juli;
        RelativeLayout rl_all,rl_tel;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_info = itemView.findViewById(R.id.tv_info);
            iv = itemView.findViewById(R.id.iv);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);
            tv_juli = itemView.findViewById(R.id.tv_juli);
            rl_tel = itemView.findViewById(R.id.rl_tel);
            rl_all= itemView.findViewById(R.id.rl_all);
        }
    }
}
