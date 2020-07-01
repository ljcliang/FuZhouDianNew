package com.yiwo.fuzhoudian.tongban_emoticon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.utils.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TbEmoticonFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.comment_rl)//整个评论视图
    public RelativeLayout comment_rl;
    @BindView(R.id.comment_et_comment)//评论视图输入框
    EditText comment_et_comment;
    @BindView(R.id.ll_xiaolian)//评论视图表情按钮
    LinearLayout comment_ll_xiaolian;
    @BindView(R.id.comment_rl_comment)//评论视图提交按钮
    RelativeLayout comment_rl_comment;
    @BindView(R.id.rl_emoticons)
    RelativeLayout rl_emoticons;
    @BindView(R.id.rv_emotion)//表情列表
            RecyclerView rv_emotion;
    @BindView(R.id.tv_btn_delete)//删除按钮
    TextView tv_btn_delete;
    private boolean firstShowKey = true;
    private List<EmoticonModel> emoticonModelList = new ArrayList<>();
    private EmoticonAdapter emoticonAdapter;

    private OnCommitListenner commitListenner;
    private int rl_emoticons_height = 800;
    String [] emoticonName = EmotionNames.NAMES;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tb_emoticon, container, false);
        unbinder = ButterKnife.bind(this,rootView);
        initdata();
        initHeightListen();
        initRv();//初始化表情列表
        return rootView;
    }
    public EditText getEdt(){
        return comment_et_comment;
    }
    private void initdata() {
        for (int i = 0;i<emoticonName.length;i++){
            EmoticonModel model = new EmoticonModel();
            model.setName(emoticonName[i]);
            int res = getResources().getIdentifier("em_"+(i+1), "mipmap",getContext().getPackageName());
            Log.d("sadasda",res+"");
            model.setResources(res);
            model.setId(i+1);
            emoticonModelList.add(model);
        }
    }
    private void initHeightListen() {
        //注册软键盘的监听
        SoftKeyBoardListener.setListener(getActivity(),
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                        if (firstShowKey){
                            rl_emoticons_height = height;
                            firstShowKey = false;
                        }
                        ViewGroup.LayoutParams layoutParams = rl_emoticons.getLayoutParams();
                        layoutParams.height = rl_emoticons_height;
                        rl_emoticons.setLayoutParams(layoutParams);
                        rl_emoticons.setVisibility(View.INVISIBLE);
//                        if (rl_emoticons.getVisibility() == View.GONE){
//                            ViewGroup.LayoutParams layoutParams = rl_emoticons.getLayoutParams();
//                            layoutParams.height = rl_emoticons_height;
//                            rl_emoticons.setLayoutParams(layoutParams);
//                            rl_emoticons.setVisibility(View.VISIBLE);
//                        }
                    }

                    @Override
                    public void keyBoardHide(int height) {

//                        showEmoticonKeyboard();
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initRv() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),7);
        rv_emotion.setLayoutManager(gridLayoutManager);
        emoticonAdapter = new EmoticonAdapter(emoticonModelList, new EmoticonAdapter.OnClickListenner() {
            @Override
            public void onClick(int postion) {
                comment_et_comment.append(emoticonModelList.get(postion).getName());
            }
        });
        rv_emotion.setAdapter(emoticonAdapter);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
    @OnClick({R.id.comment_rl_comment,R.id.tv_btn_delete,R.id.ll_xiaolian})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.comment_rl_comment:
                if (commitListenner!=null){
                    commitListenner.onCommitListen(comment_et_comment.getText().toString());
                }
                break;
            case R.id.tv_btn_delete:
                String string = comment_et_comment.getText().toString();
                if (!TextUtils.isEmpty(string)){
                    if (string.charAt(string.length()-1) == ']'){
                        comment_et_comment.setText(string.substring(0,string.lastIndexOf("[")));
                        comment_et_comment.setSelection(comment_et_comment.getText().length());
                    }else {
                        int keyCode = KeyEvent.KEYCODE_DEL;
                        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
                        comment_et_comment.dispatchKeyEvent(keyEvent);
                    }
                }
                break;
            case R.id.ll_xiaolian:
                if (rl_emoticons.getVisibility() != View.VISIBLE){
                    showEmoticonKeyboard();
                }else {
                    showKeyboard(comment_et_comment);
                }
                break;
        }
    }
    private void hideKeyBoard(){//隐藏输入框以下的表情面板
        if (this.isVisible()){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
            rl_emoticons.setVisibility(View.INVISIBLE);
        }
    }
    public void goneKeyboard(){
        if (this.isVisible()){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
            rl_emoticons.setVisibility(View.GONE);
        }
    }
    public OnCommitListenner getCommitListenner() {
        return commitListenner;
    }

    public void setCommitListenner(OnCommitListenner commitListenner) {
        this.commitListenner = commitListenner;
    }
    public void clearEdt(){
        comment_et_comment.setText("");
    }
    public interface OnCommitListenner{
        void onCommitListen(String string);
    }
    public void  showKeyBoard(){
        showKeyboard(comment_et_comment);
    }
    public void setHint(String s){
        comment_et_comment.setText("");
        comment_et_comment.setHint(s);
    }
    //弹出软键盘
    private void showKeyboard(EditText editText) {
        //其中editText为dialog中的输入框的 EditText
        if (editText != null) {
//            emotionMainFragment.hideEmotionKeyboard();
            rl_emoticons.setVisibility(View.INVISIBLE);
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }
    public void showEmoticonKeyboard(){
        rl_emoticons.getLayoutParams().height = rl_emoticons_height;
        rl_emoticons.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
