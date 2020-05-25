package com.yiwo.fuzhoudian.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.model.ShangPinServiceModel;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.yiwo.fuzhoudian.utils.TokenUtils.getToken;

public class ShangPinServiceAdapter extends RecyclerView.Adapter<ShangPinServiceAdapter.ViewHolder> {
    private Context context;
    private List<ShangPinServiceModel.ObjBean> data;
    private SpImp spImp;
    private boolean canChoose ;
    public ShangPinServiceAdapter(List<ShangPinServiceModel.ObjBean> data, boolean canChoose){
        this.data = data;
        this.canChoose = canChoose;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shangpin_service, parent, false);
        ShangPinServiceAdapter.ViewHolder holder = new ShangPinServiceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvInfo.setText(data.get(position).getInfo());
        holder.tvName.setText(data.get(position).getName());
        holder.ivChecked.setImageResource(data.get(position).isChecked() ? R.mipmap.checkbox_red_true:R.mipmap.checkbox_red_false);
        if (canChoose){
            holder.rlAll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("删除此条服务后，已选此服务的商品将不再显示该服务，是否删除？")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ViseHttp.POST(NetConfig.delServe)
                                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.delServe))
                                            .addParam("uid", spImp.getUID())
                                            .addParam("sID",data.get(position).getId())
                                            .request(new ACallback<String>() {
                                                @Override
                                                public void onSuccess(String data_s) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(data_s);
                                                        if (jsonObject.getInt("code") == 200){
                                                            Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
                                                            data.remove(position);
                                                            notifyDataSetChanged();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onFail(int errCode, String errMsg) {

                                                }
                                            });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return false;
                }
            });
            holder.rlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).isChecked()){
                        data.get(position).setChecked(false);
                        notifyDataSetChanged();
                    }else {
                        int chekedNum = 0;
                        for (ShangPinServiceModel.ObjBean bean : data){
                            if (bean.isChecked()){
                                chekedNum++;
                            }
                        }
                        if (chekedNum>2){
                            Toast.makeText(context,"最多选择三个服务",Toast.LENGTH_SHORT).show();
                        }else {
                            data.get(position).setChecked(true);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvInfo;
        ImageView ivChecked;
        RelativeLayout rlAll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            ivChecked = itemView.findViewById(R.id.iv_checked);
            rlAll = itemView.findViewById(R.id.rl_all);
        }
    }
    public interface OnItemsChoosedListenner{
        void onChoosed(List<ShangPinServiceModel.ObjBean> chooseServices);
    }
}
