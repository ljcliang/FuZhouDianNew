package com.yiwo.fuzhoudian.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.custom.TitleMessageOkDialog;
import com.yiwo.fuzhoudian.model.ShangPinUpLoadModel;
import com.yiwo.fuzhoudian.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class FaBuShangPinJiaGeAdapter extends RecyclerView.Adapter<FaBuShangPinJiaGeAdapter.ViewHolder> {

    private Context context;
    private List<ShangPinUpLoadModel.SpecBean> data;
    private SpImp spImp;
    private DeleteItemListenner listenner;
    private String message;
    public FaBuShangPinJiaGeAdapter(List<ShangPinUpLoadModel.SpecBean> data, DeleteItemListenner listenner, String message) {
        this.data = data;
        this.listenner = listenner;
        this.message = message;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fabushangpin_price, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position > 0){
            holder.rl_btn_delete.setVisibility(View.VISIBLE);
        }else {
            holder.rl_btn_delete.setVisibility(View.GONE);
        }
        holder.rl_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.deleteItem(position);
            }
        });
        holder.edt_jiage.setText(data.get(position).getNowPrice());
        holder.edt_yuan_jiage.setText(data.get(position).getOldPrice());
        holder.edt_guige.setText(data.get(position).getSpec());
        holder.edt_kucun.setText(data.get(position).getAllNum());
        holder.edt_fenxiaoticheng.setText(data.get(position).getFx_bonus());
        if (position == 0){
            holder.iv_jiage_tishi.setVisibility(View.VISIBLE);
        }else {
            holder.iv_jiage_tishi.setVisibility(View.GONE);
        }
        holder.iv_jiage_tishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleMessageOkDialog titleMessageOkDialog1 = new TitleMessageOkDialog(context, "",
                        message , "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog1.show();
            }
        });
        holder.edt_guige.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.get(position).setSpec(s.toString());
            }
        });
        holder.edt_guige.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                EditText et=(EditText)view;
                if (!b) {// 失去焦点
                    et.setHint(et.getTag().toString());
                } else {
                    String hint=et.getHint().toString();
                    et.setTag(hint);//保存预设字
                    et.setHint(null);
                }
            }
        });
        holder.edt_jiage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.get(position).setNowPrice(s.toString());
            }
        });
        holder.edt_yuan_jiage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.get(position).setOldPrice(s.toString());
            }
        });
        holder.edt_kucun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.get(position).setAllNum(s.toString());
            }
        });
        holder.edt_fenxiaoticheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                data.get(position).setFx_bonus(editable.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_btn_delete;
        EditText edt_jiage,edt_guige,edt_kucun,edt_yuan_jiage,edt_fenxiaoticheng;
        ImageView iv_jiage_tishi;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_btn_delete = itemView.findViewById(R.id.rl_btn_minus);
            edt_jiage = itemView.findViewById(R.id.edt_jiage);
            edt_guige = itemView.findViewById(R.id.edt_guige);
            edt_kucun = itemView.findViewById(R.id.edt_kucun);
            edt_yuan_jiage = itemView.findViewById(R.id.edt_yuan_jiage);
            iv_jiage_tishi = itemView.findViewById(R.id.iv_jiage_tishi);
            edt_fenxiaoticheng = itemView.findViewById(R.id.edt_fenxiaoticheng);
        }
    }
    public interface DeleteItemListenner{
        void deleteItem(int pos);
    }

}
