package com.yiwo.fuzhoudian.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

/**
 * Created by Administrator on 2018/9/4 0004.
 */

public class MyFriendDialog extends Dialog {

    private Context context;
    private TextView tvDelete;
    private TextView tvBlack;
    private TextView tvInformation;
    private TextView tvYaoQing;
    private OnMyFriendListener listener;
    private int type;

    public MyFriendDialog(@NonNull Context context, int type, OnMyFriendListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_my_friend, null);
        setContentView(view);
        tvDelete = view.findViewById(R.id.tv_delete_friend);
        tvBlack = view.findViewById(R.id.tv_add_black);
        tvInformation = view.findViewById(R.id.tv_information);
        tvYaoQing = view.findViewById(R.id.tv_yaoqing);
        if(type == 2){
            tvDelete.setText("彻底删除");
            tvBlack.setText("移出黑名单");
            tvInformation.setVisibility(View.GONE);
        }
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReturn(0);
                dismiss();
            }
        });
        tvBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReturn(1);
                dismiss();
            }
        });
        tvInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReturn(2);
                dismiss();
            }
        });
        tvYaoQing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturn(3);
                dismiss();
            }
        });
    }

    public interface OnMyFriendListener{
        void onReturn(int type);
    }

}
