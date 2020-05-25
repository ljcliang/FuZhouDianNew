package com.yiwo.fuzhoudian.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;

public class TitleMessageOkDialog extends Dialog {
    private String title,message,btnText;
    private TextView tvTitle,tvMessage,tvBtn;
    private OnBtnClickListenner onBtnClickListenner;
    public TitleMessageOkDialog(@NonNull Context context, String title, String message, String btnText, OnBtnClickListenner onBtnClickListenner) {
        super(context);
        this.btnText =btnText;
        this.title = title;
        this.message = message;
        this.onBtnClickListenner = onBtnClickListenner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_title_message,null);
        setContentView(view);
        tvBtn = view.findViewById(R.id.tv_btn);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvBtn.setText(btnText);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClickListenner.onclick(TitleMessageOkDialog.this);
            }
        });
    }

    public interface OnBtnClickListenner{
        void onclick(Dialog dialog);
    }
}
