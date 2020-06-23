package com.yiwo.fuzhoudian.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.custom.EditContentDialog_L;
import com.yiwo.fuzhoudian.fragments.webfragment.HomeDianPuGuanLiFragment;
import com.yiwo.fuzhoudian.model.AllOrderFragmentModel;
import com.yiwo.fuzhoudian.model.SellerOrderModel;
import com.yiwo.fuzhoudian.pages.webpages.OrderInfoWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class FragmentAllOrderAdapter extends RecyclerView.Adapter<FragmentAllOrderAdapter.ViewHolder> {

    private Context context;
    private List<SellerOrderModel.ObjBean> data;
    private Activity activity;
    private BtnsOnCLickListenner btnsOnCLickListenner;
    public FragmentAllOrderAdapter(List<SellerOrderModel.ObjBean> data, Activity activity,BtnsOnCLickListenner btnsOnCLickListenner) {
        this.data = data;
        this.activity = activity;
        this.btnsOnCLickListenner = btnsOnCLickListenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_all_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_order_sn.setText("订单号:"+data.get(position).getOrder_sn());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_userhead);
        holder.tv_username.setText(data.get(position).getUsername());
        holder.tv_staus.setText(data.get(position).getStatusMes());
        String str_tv_goods_name = "";
        if (data.get(position).getGList().size()>0 && data.get(position).getGList().size()<2){
            SellerOrderModel.ObjBean.GListBean bean = data.get(position).getGList().get(0);
            holder.tv_goods_name.setText(bean.getGoodsName());
            Glide.with(context).load(bean.getGoodsImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.fragment_all_order_rv_iv);
            holder.tv_goods_info.setText(bean.getGoodsSpec());
            holder.ll_price.setVisibility(View.VISIBLE);
            holder.tv_goods_price.setText(bean.getPrice());
            holder.tv_goods_num.setText(bean.getBuyNum());
        }else if (data.get(position).getGList().size()>1){
            holder.tv_goods_name.setText("订单数量"+data.get(position).getGList().size()+"件");
            Glide.with(context).load(R.mipmap.many_goods).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.fragment_all_order_rv_iv);
            for (SellerOrderModel.ObjBean.GListBean bean : data.get(position).getGList()){
                str_tv_goods_name = str_tv_goods_name + bean.getGoodsName() + "、" ;
            }
            holder.tv_goods_info.setText(str_tv_goods_name);
            holder.ll_price.setVisibility(View.GONE);
        }
        holder.tv_btn_chudan.setVisibility(View.GONE);
        holder.tv_btn_jujuejiedan.setVisibility(View.GONE);
        holder.tv_btn_delete.setVisibility(View.GONE);
        holder.tv_btn_yipingjia.setVisibility(View.GONE);
        holder.tv_btn_tuikuan.setVisibility(View.GONE);
        holder.tv_btn_querenshouhuo.setVisibility(View.GONE);
//         1出单  拒绝接单       2 确认收货     3删除     4删除   已评价   5确认退款   6、7删除
        switch (data.get(position).getStatus()){

            case "0":

                break;
            case "1":
                holder.tv_btn_chudan.setVisibility(View.VISIBLE);
                holder.tv_btn_jujuejiedan.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.tv_btn_querenshouhuo.setVisibility(View.VISIBLE);
                break;
            case "3":
                holder.tv_btn_delete.setVisibility(View.VISIBLE);
                break;
            case "4":
                holder.tv_btn_delete.setVisibility(View.VISIBLE);
                holder.tv_btn_yipingjia.setVisibility(View.VISIBLE);
                break;
            case "5":
                holder.tv_btn_tuikuan.setVisibility(View.VISIBLE);
                break;
            case "6":
                holder.tv_btn_delete.setVisibility(View.VISIBLE);
                break;
            case "7":
                holder.tv_btn_delete.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        holder.tv_all_price.setText(data.get(position).getAllMoney());
        holder.rlDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderInfoWebActivity.start(context,data.get(position).getOrderMes());
            }
        });
        holder.tv_btn_chudan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定出单？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btnsOnCLickListenner.onChuLiDan(position,1,"");
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        holder.tv_btn_jujuejiedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditContentDialog_L contentDialog = new EditContentDialog_L(context, "请输入拒绝接单原因", new EditContentDialog_L.OnReturnListener() {
                    @Override
                    public void onReturn(String content) {
                        btnsOnCLickListenner.onChuLiDan(position,0,content);
                    }
                });
                contentDialog.show();
            }
        });
        holder.tv_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("删除订单？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btnsOnCLickListenner.onChuLiDan(position,2,"");
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });
        holder.tv_btn_tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(context, OrderCommentActivity.class);
//                intent.putExtra("orderid", data.get(position).getOID());
//                intent.putExtra("type","0");
//                context.startActivity(intent);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("确定退款？")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        btnsOnCLickListenner.onTuiKuan(position);
                                    }
                                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
        holder.tv_btn_yipingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(context, OrderCommentActivity.class);
//                intent.putExtra("orderid", data.get(position).getOID());
//                intent.putExtra("type","0");
//                context.startActivity(intent);
                btnsOnCLickListenner.onYiPingJia(position);
            }
        });
        holder.tv_btn_querenshouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定买家已收货？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btnsOnCLickListenner.onChuLiDan(position,3,"");
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlDetails;
        private ImageView iv_userhead,fragment_all_order_rv_iv;
        private TextView tv_username,tv_staus,tv_goods_name,tv_goods_info,tv_goods_price,tv_goods_num,tv_all_price,tv_order_sn,
                tv_btn_delete,tv_btn_yipingjia,tv_btn_tuikuan,tv_btn_chudan,tv_btn_jujuejiedan,tv_btn_querenshouhuo;
        private LinearLayout ll_price;
        public ViewHolder(View itemView) {
            super(itemView);
            rlDetails = itemView.findViewById(R.id.fragment_all_order_rv_rl_details);
            tv_order_sn = itemView.findViewById(R.id.tv_order_sn);
            iv_userhead = itemView.findViewById(R.id.iv_userhead);
            fragment_all_order_rv_iv = itemView.findViewById(R.id.fragment_all_order_rv_iv);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_staus = itemView.findViewById(R.id.tv_staus);
            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_goods_info = itemView.findViewById(R.id.tv_goods_info);
            tv_goods_price = itemView.findViewById(R.id.tv_goods_price);

            tv_goods_price = itemView.findViewById(R.id.tv_goods_price);
            tv_goods_num = itemView.findViewById(R.id.tv_goods_num);
            tv_all_price = itemView.findViewById(R.id.tv_all_price);

            tv_btn_delete = itemView.findViewById(R.id.tv_btn_delete);
            tv_btn_yipingjia = itemView.findViewById(R.id.tv_btn_yipingjia);
            tv_btn_tuikuan = itemView.findViewById(R.id.tv_btn_tuikuan);
            tv_btn_chudan = itemView.findViewById(R.id.tv_btn_chudan);
            tv_btn_jujuejiedan = itemView.findViewById(R.id.tv_btn_jujuejiedan);
            tv_btn_querenshouhuo = itemView.findViewById(R.id.tv_btn_querenshouhuo);
            ll_price = itemView.findViewById(R.id.ll_price);
        }
    }
    public interface BtnsOnCLickListenner{
        /**
         *
         * @param postion
         * @param type type 操作类型  0拒绝接单  1出单  2删除 3确定收货,type == 0 时传入拒绝原因
         */
        void onChuLiDan(int postion,int type,String juJueYuanYin);//type 操作类型  0拒绝接单  1出单  2删除
        void onYiPingJia(int postion);
        void onTuiKuan(int postion);
    }
}
