package com.yiwo.fuzhoudian.pages.renzheng;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.FabuShangpinIntercalationPicsAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.model.LaoWuZhieYeModel;
import com.yiwo.fuzhoudian.model.UpLoadShangPinImgIntercalationPicModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.pages.FaBu_XiuGaiShangPinActivity;
import com.yiwo.fuzhoudian.sp.SpImp;

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

import static com.yiwo.fuzhoudian.utils.TokenUtils.getToken;

public class RenZheng1_LaoWuEditInfoActivity extends BaseActivity {

    @BindView(R.id.rl_set_return)
    RelativeLayout rlSetReturn;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_choose_sex)
    LinearLayout llChooseSex;
    @BindView(R.id.edt_person_num)
    EditText edtPersonNum;
    @BindView(R.id.edt_tel)
    EditText edtTel;
    @BindView(R.id.tv_zhuanye)
    TextView tvZhuanye;
    @BindView(R.id.ll_choose_zhuanye)
    LinearLayout llChooseZhuanye;
    @BindView(R.id.edt_gongzuo_shijian)
    EditText edtGongzuoShijian;
    @BindView(R.id.rv_choose_pics)
    RecyclerView rvChoosePics;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private List<UpLoadShangPinImgIntercalationPicModel> mList;
    private FabuShangpinIntercalationPicsAdapter adapter;
    private static final int REQUEST_CODE_CHOOSE_IMG = 1001;
    private SpImp spImp;
    private Dialog dialog;
    private List<File> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng1__lao_wu_edit_info);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initUpData();
    }
    /**
     * 初始化上传图片
     */
    private void initUpData() {

        mList = new ArrayList<>();
//        GridLayoutManager manager = new GridLayoutManager(CreateFriendRememberActivity1.this, 3);
//        recyclerView.setLayoutManager(manager);
        LinearLayoutManager manager = new LinearLayoutManager(RenZheng1_LaoWuEditInfoActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvChoosePics.setLayoutManager(manager);
        adapter = new FabuShangpinIntercalationPicsAdapter(mList);
        adapter.setDescribe(false);
        rvChoosePics.setAdapter(adapter);
        adapter.setListener(new FabuShangpinIntercalationPicsAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(RenZheng1_LaoWuEditInfoActivity.this, REQUEST_CODE_CHOOSE_IMG); // 打开相册
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnAddDescribeListener() {
            @Override
            public void onAddDescribe(int i, String s) {
                mList.get(i).setDescribe(s);
                adapter.notifyDataSetChanged();
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnSetFirstPicListienner() {
            @Override
            public void onSetFirst(final int postion) {
            }
        });
    }
    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng1_LaoWuEditInfoActivity.class);
        context.startActivity(intent);
    }
    private void save(){
        if (TextUtils.isEmpty(edtName.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tvSex.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edtPersonNum.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edtTel.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tvZhuanye.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请选择专业", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edtGongzuoShijian.getText())){
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请输入工作经验", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mList.size() == 0) {
            Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "请上传案例图片", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = WeiboDialogUtils.createLoadingDialog(RenZheng1_LaoWuEditInfoActivity.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getPicId().equals("-1")) {
                        if (mList.get(i).getFirstPic()) {
                            list.add(0, mList.get(i).getPic());
                        } else {
                            list.add(mList.get(i).getPic());
                        }
                    }
                }
                if (list.size() == 0) {
                    e.onNext(map);
                }
                Luban.with(RenZheng1_LaoWuEditInfoActivity.this)
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
                                        map.put("user_files[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
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
                /*
                username 姓名
                 uid  登陆用户id
                 sfz  身份证号码
                 tel 联系电话
                 sex 性别 0男 1 女
                 work_year 工作经验
                 job 专业名( 从 接口 action/ac_user/attestationJob  获取所有专业名称 选择一个 把汉字传过来 不传数字)
                 user_files 图片
                 */
                Log.d("lwAttestation","getUID()::"+spImp.getUID());
                ViseHttp.UPLOAD(NetConfig.lwAttestation)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.lwAttestation))
                        .addParam("uid", spImp.getUID())
                        .addParam("username", edtName.getText().toString())
                        .addParam("sfz", edtPersonNum.getText().toString())
                        .addParam("tel", edtTel.getText().toString())
                        .addParam("sex", tvSex.getText().equals("男")?"0":"1")
                        .addParam("work_year", edtGongzuoShijian.getText().toString())
                        .addParam("job", tvZhuanye.getText().toString())
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("lwAttestation", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                                        WeiboDialogUtils.closeDialog(dialog);
                                        finish();
                                    } else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("lwAttestation", errMsg);
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
    @OnClick({R.id.rl_set_return, R.id.ll_choose_sex, R.id.ll_choose_zhuanye, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_set_return:
                onBackPressed();
                break;
            case R.id.ll_choose_sex:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RenZheng1_LaoWuEditInfoActivity.this);
                alertDialog.setTitle("请选择性别")
                        .setItems(new String[]{"男", "女"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvSex.setText(i==0?"男":"女");
                            }
                        }).show();
                break;
            case R.id.ll_choose_zhuanye:
                ViseHttp.UPLOAD(NetConfig.attestationJob)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.attestationJob))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("attestationJob", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        LaoWuZhieYeModel laoWuZhieYeModel = gson.fromJson(data,LaoWuZhieYeModel.class);
                                        final String strZhiYe[] =new String[laoWuZhieYeModel.getObj().size()];
                                        for (int i = 0;i<laoWuZhieYeModel.getObj().size();i++){
                                            strZhiYe[i] = laoWuZhieYeModel.getObj().get(i).getJobname();
                                        }
                                        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(RenZheng1_LaoWuEditInfoActivity.this);
                                        alertDialog1.setTitle("请选择职业")
                                                .setItems(strZhiYe, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        tvZhuanye.setText(strZhiYe[i]);
                                                    }
                                                }).show();

                                    } else {
                                        Toast.makeText(RenZheng1_LaoWuEditInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    WeiboDialogUtils.closeDialog(dialog);
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("attestationJob", errMsg);
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
                break;
            case R.id.tv_next:
                save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_IMG && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new UpLoadShangPinImgIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
    }
}
