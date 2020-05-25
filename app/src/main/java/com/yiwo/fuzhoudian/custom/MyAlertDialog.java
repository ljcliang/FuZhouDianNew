package com.yiwo.fuzhoudian.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;


/**
 * Created by ljc on 2019/1/9.
 */

public class MyAlertDialog extends Dialog {

    private Context context;
    private DialogListener xieYiDialogListener;

    private TextView tvTitle,tvMessage,sureTv,cancleTv;
    private String strTitle,strMessage,strSure,strCancle;
    public MyAlertDialog(@NonNull Context context, String strTitle, String strMessage, String strSure, String strCancle, DialogListener listener) {
        super(context);
        this.context = context;
        this.xieYiDialogListener = listener;
        this.strCancle = strCancle;
        this.strSure = strSure;
        this.strMessage = strMessage;
        this.strTitle = strTitle;
    }
    public interface DialogListener{
        void agreeBtnListen();
        void disAgreeBtnListen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_my_alert, null);
        setContentView(view);
        sureTv = view.findViewById(R.id.btn_agree);
        cancleTv = view.findViewById(R.id.btn_disagree);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(strMessage);
        tvTitle.setText(strTitle);
        sureTv.setText(strSure);
        cancleTv.setText(strCancle);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.agreeBtnListen();
                dismiss();
            }
        });
        cancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.disAgreeBtnListen();
            }
        });
    }
}
