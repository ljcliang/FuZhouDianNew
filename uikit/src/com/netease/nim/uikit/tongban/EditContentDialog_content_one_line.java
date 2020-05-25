package com.netease.nim.uikit.tongban;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.R;

/**
 * Created by Administrator on 2018/7/20.
 */

public class EditContentDialog_content_one_line extends Dialog {

    private Context context;
    private OnReturnListener listener;

    private RelativeLayout rlClose;
    private EditText etContent;
    private TextView tvTitle;
    private Button btnOk;
    private String strTitle;
    CharSequence ss = "";
    public EditContentDialog_content_one_line(@NonNull Context context, String title, OnReturnListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.strTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.tb_dialog_edit_content_one_line, null);
        setContentView(view);

        rlClose = view.findViewById(R.id.dialog_edit_content_rl_close);
        etContent = view.findViewById(R.id.dialog_edit_content_et_content);
        tvTitle = view.findViewById(R.id.dialog_edit_content_tv);
        tvTitle.setText(strTitle);
        btnOk = view.findViewById(R.id.dialog_edit_content_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener.onReturn(etContent.getText().toString())){
                    dismiss();
                };
            }
        });
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public interface OnReturnListener{
        boolean onReturn(String content);
    }
    public static void setEditTextInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
