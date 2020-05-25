package com.yiwo.fuzhoudian.pages.creatyouji;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.LabelChooseOneAdapter;
import com.yiwo.fuzhoudian.adapter.NewCreateFriendRemberIntercalationAdapter;
import com.yiwo.fuzhoudian.custom.MyAlertDialog;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.model.CityModel;
import com.yiwo.fuzhoudian.model.NewUserIntercalationPicModel;
import com.yiwo.fuzhoudian.model.UserLabelModel;
import com.yiwo.fuzhoudian.network.ActivityConfig;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.CityActivity;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.StringUtils;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class CreateYouJiAddInfoActivity extends TakePhotoActivity {

    @BindView(R.id.activity_create_friend_remember_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_create_friend_remember_rl_edit_title)
    RelativeLayout rlEditTitle;
    @BindView(R.id.activity_create_friend_remember_rl_activity_city)
    RelativeLayout rlSelectCity;
    @BindView(R.id.activity_create_friend_remember_tv_activity_city)
    EditText tvCity;
    @BindView(R.id.activity_create_friend_remember_rl_complete)
    RelativeLayout rlComplete;
    @BindView(R.id.activity_create_friend_remember_tv_title)
    EditText etTitle;
    @BindView(R.id.activity_create_friend_remember_rl_label)
    RelativeLayout rlLabel;
    @BindView(R.id.activity_create_friend_remember_tv_label)
    TextView tvLabel;
    @BindView(R.id.activity_create_friend_remember_tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.activity_create_friend_remember_rl_active_title)
    RelativeLayout rlActiveTitle;
    @BindView(R.id.activity_create_friend_remember_tv_active_title)
    TextView tvActiveTitle;
    @BindView(R.id.activity_create_intercalation_rv)
    RecyclerView recyclerView;

    private NewCreateFriendRemberIntercalationAdapter adapter;
    private LabelChooseOneAdapter labelAdapter;
    private List<NewUserIntercalationPicModel> mList;

    private PopupWindow popupWindow;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int REQUEST_CODE1 = 0x00000012;
    private static final int REQUEST_CODE_GET_CITY = 1;
    private static final int REQUEST_CODE_SUO_SHU_HUO_DONG = 2;
    private List<File> files = new ArrayList<>();

    /**
     * 标签id
     */
    private String yourChoiceId = "";

    private SpImp spImp;
    private String uid = "";

    private String images = "";

    private Dialog dialog;

    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";

    private String password;

    private List<UserLabelModel.ObjBean> labelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_youji_addinfo);
        ButterKnife.bind(this);
        spImp = new SpImp(CreateYouJiAddInfoActivity.this);
        tvCity.setText(spImp.getLastCreateYouJiAddress());
        tvLabel.setText(spImp.getLastCreateYouJiLabelText());
        yourChoiceId = spImp.getLastCreateYouJiLabelId();
        init();
        initUpData();
        mList.clear();
        List<String> list_path = getIntent().getStringArrayListExtra("paths");
        for (int i = 0;i<list_path.size();i++){
            Log.d("ddaad",list_path.get(i));
            NewUserIntercalationPicModel model = new NewUserIntercalationPicModel(list_path.get(i),"");
            if (i==0){
                model.setFirstPic(true);
            }else {
                model.setFirstPic(false);
            }
            mList.add(model);
            if ((!etTitle.getText().toString().equals(""))&&mList.size()>0){
                rlComplete.setVisibility(View.VISIBLE);
            }else {
                rlComplete.setVisibility(View.GONE);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化上传图片
     */
    private void initUpData() {

        mList = new ArrayList<>();
//        GridLayoutManager manager = new GridLayoutManager(CreateYouJiAddInfoActivity.this, 3);
//        recyclerView.setLayoutManager(manager);
        LinearLayoutManager manager = new LinearLayoutManager(CreateYouJiAddInfoActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new NewCreateFriendRemberIntercalationAdapter(mList);
        adapter.setDescribe(false);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new NewCreateFriendRemberIntercalationAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(CreateYouJiAddInfoActivity.this, REQUEST_CODE1); // 打开相册
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
                if ((!etTitle.getText().toString().equals(""))&&mList.size()>0){
                rlComplete.setVisibility(View.VISIBLE);
            }else {
                rlComplete.setVisibility(View.GONE);
            }
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnAddDescribeListener() {
            @Override
            public void onAddDescribe(int i, String s) {
                mList.get(i).setDescribe(s);
                adapter.notifyDataSetChanged();
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnSetFirstPicListienner() {
            @Override
            public void onSetFirst(final int postion) {
               final AlertDialog.Builder builder = new AlertDialog.Builder(CreateYouJiAddInfoActivity.this);
               builder.setMessage("设置为首图？")
                       .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               for (int i = 0; i<mList.size();i++){
                                   if (i == postion){
//                                       NewUserIntercalationPicModel model = mList.get(postion);
//                                       model.setFirstPic(true);
                                       mList.get(i).setFirstPic(true);
                                   }else {
                                       mList.get(i).setFirstPic(false);
                                   }
                               }
                               adapter.notifyDataSetChanged();
                           }
                       })
                       .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                           }
                       }).show();
            }
        });

    }

    private void init() {

        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                labelList = new ArrayList<>();
                                labelList.addAll(userLabelModel.getObj());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        etTitle.addTextChangedListener(textTitleWatcher);
    }

    TextWatcher textTitleWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvTitleNum.setText(temp.length()+"/300");
            if(temp.length()>=300){
                Toast.makeText(CreateYouJiAddInfoActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
            if ((!etTitle.getText().toString().equals(""))&&mList.size()>0){
                rlComplete.setVisibility(View.VISIBLE);
            }else {
                rlComplete.setVisibility(View.GONE);
            }
        }
    };

    @OnClick({R.id.activity_create_friend_remember_rl_back, R.id.activity_create_friend_remember_rl_edit_title,  R.id.activity_create_friend_remember_rl_activity_city,
             R.id.activity_create_friend_remember_rl_complete,
            R.id.activity_create_friend_remember_rl_label,
            R.id.activity_create_friend_remember_rl_active_title,R.id.rl_choose_address,R.id.btn_lijifabu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_create_friend_remember_rl_back:
                onBackPressed();
                break;
            case R.id.activity_create_friend_remember_rl_edit_title:
                break;
            case R.id.rl_choose_address:
                Intent it = new Intent(CreateYouJiAddInfoActivity.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "createYouJi");
                startActivityForResult(it, REQUEST_CODE_GET_CITY);
                break;
            case R.id.activity_create_friend_remember_rl_complete:
                next_tocaogao();
                break;
            case R.id.activity_create_friend_remember_rl_label:
                if (labelList.size()==0){
                    Toast.makeText(CreateYouJiAddInfoActivity.this,"暂无标签", Toast.LENGTH_SHORT).show();
                    break;
                }
                showFriendRememberLabelPop();
                break;
            case R.id.activity_create_friend_remember_rl_active_title:
                //活动标题
//                    Intent it_suoshu = new Intent(CreateYouJiAddInfoActivity.this, SuoShuHuoDongActivity.class);
//                    startActivityForResult(it_suoshu, REQUEST_CODE_SUO_SHU_HUO_DONG);
                break;
            case R.id.btn_lijifabu:
                fabu();
                break;
        }
    }

    private void showFriendRememberLabelPop() {
        View view = LayoutInflater.from(CreateYouJiAddInfoActivity.this).inflate(R.layout.popupwindow_createyouji_choose_label, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RelativeLayout rvCancel = view.findViewById(R.id.rv_cancel);
        RelativeLayout rvSure = view.findViewById(R.id.rv_sure);
        RecyclerView recyclerView = view.findViewById(R.id.rv);

        GridLayoutManager manager = new GridLayoutManager(CreateYouJiAddInfoActivity.this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        labelAdapter = new LabelChooseOneAdapter(labelList);
        recyclerView.setAdapter(labelAdapter);
        final String[] tmpLabelText = {""};
        final String[] tmpLabelId = {""};
        labelAdapter.setListener(new LabelChooseOneAdapter.OnSelectLabelListener() {
            @Override
            public void onSelete(int i) {
                for (UserLabelModel.ObjBean bean : labelList){
                    bean.setChoose(false);
                }
                labelList.get(i).setChoose(true);
                tmpLabelText[0] = labelList.get(i).getLname();
                tmpLabelId[0] = labelList.get(i).getLID();
                labelAdapter.notifyDataSetChanged();
            }
        });
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.popwindowbottom_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        rvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            popupWindow.dismiss();
            }
        });
        rvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLabel.setText(tmpLabelText[0]);
                yourChoiceId = tmpLabelId[0];
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String url = result.getImage().getCompressPath();
        Log.e("222", result.getImage().getCompressPath());

//        String oldName = url;
//        String newName = url.substring(0, url.lastIndexOf("."))+".png";
//        Log.e("222", newName);
//        renameFile(oldName, newName);
        images = url;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Log.e("222", scList.get(0));
            images = scList.get(0);
        }
        if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new NewUserIntercalationPicModel(pic.get(i), ""));
                if ((!etTitle.getText().toString().equals(""))&&mList.size()>0){
                rlComplete.setVisibility(View.VISIBLE);
            }else {
                rlComplete.setVisibility(View.GONE);
            }
            }
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_GET_CITY && data != null && resultCode == 1) {//选择城市
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            tvCity.setText(model.getName());
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 2) {//重置
            tvCity.setText("");
            tvCity.setHint("请选择或输入活动地点");
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 3) {//国际城市
            String city = data.getStringExtra("city");
            tvCity.setText(city);
        }
        if (requestCode == REQUEST_CODE_SUO_SHU_HUO_DONG && resultCode == 1){
//            GetFriendActiveListModel.ObjBean bean = (GetFriendActiveListModel.ObjBean) data.getSerializableExtra("suoshuhuodong");
//            yourChoiceActiveName = bean.getPftitle();
//            yourChoiceActiveId = bean.getPfID();
//            tvActiveTitle.setText(yourChoiceActiveName);
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        MyAlertDialog dialog = new MyAlertDialog(CreateYouJiAddInfoActivity.this, "是否保存在草稿箱",
                "保存在草稿箱可在草稿箱中继续编辑\n" + "不保存则内容将被清除", "保存", "不保存", new MyAlertDialog.DialogListener() {
            @Override
            public void agreeBtnListen() {
//                next_tocaogao();
            }

            @Override
            public void disAgreeBtnListen() {
                CreateYouJiAddInfoActivity.this.finish();
            }
        });
        if(TextUtils.isEmpty(etTitle.getText().toString())){
            CreateYouJiAddInfoActivity.this.finish();
        }
        //20190225 限制友记上传图片数量 1
        else if(mList.size()<1){
            CreateYouJiAddInfoActivity.this.finish();
        }else {
            dialog.show();
        }

    }


    private void fabu() {
        if(TextUtils.isEmpty(etTitle.getText().toString())){
            Toast.makeText(CreateYouJiAddInfoActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(tvLabel.getText().toString())){
            Toast.makeText(CreateYouJiAddInfoActivity.this, "请选择标签", Toast.LENGTH_SHORT).show();
            return;
        }
        //20190225 限制友记上传图片数量 1
        else if(mList.size()<1){
            Toast.makeText(CreateYouJiAddInfoActivity.this, "请至少上传1张照片", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(tvCity.getText().toString())){
            Toast.makeText(CreateYouJiAddInfoActivity.this, "请填写地点", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //判断如果填写开始时间和结束时间   结束时间必须大于开始时间
//            if (!tvTimeStart.getText().toString().equals("")&&!tvTimeEnd.getText().toString().equals("")){
//                if (StringUtils.getTimeCompareSize(tvTimeStart.getText().toString(),tvTimeEnd.getText().toString())==1){
//                    Toast.makeText(CreateYouJiAddInfoActivity.this, "活动开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
        }
        dialog = WeiboDialogUtils.createLoadingDialog(CreateYouJiAddInfoActivity.this, "请等待...");
        spImp.setLastCreateYouJiLabelText(tvLabel.getText().toString());
        spImp.setLastCreateYouJiLabelId(yourChoiceId);
        spImp.setLastCreateYouJiAddress(tvCity.getText().toString());
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getFirstPic()){
                        list.add(0,mList.get(i).getPic());
                    }else {
                        list.add(mList.get(i).getPic());
                    }
                }
                Luban.with(CreateYouJiAddInfoActivity.this)
                        .load(list)
                        .ignoreBy(100)
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                files.add(file);
                                Log.e("222", list.size() + "..." + files.size());
                                if (files.size() == list.size()) {
                                    for (int i = 0; i < files.size(); i++) {
                                        map.put("fmpic[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
//                                files.clear();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();

            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {
                ViseHttp.UPLOAD(NetConfig.userRelease)
                        .addHeader("Content-Type","multipart/form-data")
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userRelease))
                        .addParam("fmtitle", etTitle.getText().toString())
//                        .addParam("fmcontent", etContent.getText().toString())
                        .addParam("fmaddress", tvCity.getText().toString())
                        .addParam("uid", uid)
                        .addParam("fmlable", yourChoiceId)
//                        .addParam("fmgotime", tvTimeStart.getText().toString())
//                        .addParam("fmendtime", tvTimeEnd.getText().toString())
//                        .addParam("percapitacost", etPrice.getText().toString())
                        .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
//                        .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                        .addParam("accesspassword", password)
                        .addParam("type", "0")
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Toast.makeText(CreateYouJiAddInfoActivity.this, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                                        WeiboDialogUtils.closeDialog(dialog);
                                        finish();
                                    }else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(CreateYouJiAddInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(CreateYouJiAddInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("222", errMsg);
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {
                WeiboDialogUtils.closeDialog(dialog);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void next_tocaogao(){//仅保存至草稿
        dialog = WeiboDialogUtils.createLoadingDialog(CreateYouJiAddInfoActivity.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
//                        File file = new File(images);
//                        Luban.with(CreateFriendRememberActivity.this)
//                                .load(images)
//                                .ignoreBy(100)
//                                .filter(new CompressionPredicate() {
//                                    @Override
//                                    public boolean apply(String path) {
//                                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                                    }
//                                })
//                                .setCompressListener(new OnCompressListener() {
//                                    @Override
//                                    public void onStart() {
//                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                    }
//
//                                    @Override
//                                    public void onSuccess(File file) {
//                                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                                        e.onNext(file);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        // TODO 当压缩过程出现问题时调用
//                                    }
//                                }).launch();

                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPic());
                }
                Luban.with(CreateYouJiAddInfoActivity.this)
                        .load(list)
                        .ignoreBy(100)
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                files.add(file);
                                Log.e("222", list.size() + "..." + files.size());
                                if (files.size() == list.size()) {
                                    for (int i = 0; i < files.size(); i++) {
                                        map.put("fmpic[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
//                                files.clear();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        }).launch();

            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {
                ViseHttp.UPLOAD(NetConfig.userRelease)
                        .addHeader("Content-Type","multipart/form-data")
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userRelease))
                        .addParam("fmtitle", etTitle.getText().toString())
//                        .addParam("fmcontent", etContent.getText().toString())
                        .addParam("fmaddress", tvCity.getText().toString())
                        .addParam("uid", uid)
                        .addParam("fmlable", yourChoiceId)
//                        .addParam("fmgotime", tvTimeStart.getText().toString())
//                        .addParam("fmendtime", tvTimeEnd.getText().toString())
//                        .addParam("percapitacost", etPrice.getText().toString())
                        .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
//                        .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                        .addParam("accesspassword", password)
                        .addParam("type", "1")
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        CreateYouJiAddInfoActivity.this.finish();
                                        Toast.makeText(CreateYouJiAddInfoActivity.this,"已保存至草稿箱", Toast.LENGTH_SHORT).show();
                                    }else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(CreateYouJiAddInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(CreateYouJiAddInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {
                WeiboDialogUtils.closeDialog(dialog);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
