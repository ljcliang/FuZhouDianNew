package com.yiwo.fuzhoudian.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.adapter.ShangPinLabelAdapter;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.ShangPinLabelModel;
import com.yiwo.fuzhoudian.model.ShangPinServiceModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.fuzhoudian.utils.TokenUtils.getToken;

public class ShangPinLabelEditActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.rv)
    RecyclerView rvLabel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ShangPinLabelAdapter shangPinLabelAdapter;
    private List<ShangPinLabelModel.ObjBean> listShangPinLabel = new ArrayList<>();
    public static String EDIT_LABEL_BEAN = "label_bean";
    private ShangPinLabelModel.ObjBean editLabelMode;
    private boolean isEdit =  false;
    public static String NEW_ADD_LABEL_MODEL = "new_add_label_model";
    public static int ADD_SUCCESS_RESULT_CODE = 1;
    SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_pin_label_edit);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        if (getIntent().getSerializableExtra(EDIT_LABEL_BEAN)!=null){
            editLabelMode = (ShangPinLabelModel.ObjBean) getIntent().getSerializableExtra(EDIT_LABEL_BEAN);
            tvTitle.setText("修改标签");
            isEdit = true;
            edtName.setText(editLabelMode.getName());
            edtName.setSelection(edtName.getText().length());
        }else {
            tvTitle.setText("添加标签");
            isEdit = false;
        }
        initView();
    }
    private void initView() {
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtName.setHint("");
                }else {
                    if (edtName.getText().toString().equals("")){
                        edtName.setHint("在这里输入名称(4字以内)");
                    }
                }
            }
        });
        shangPinLabelAdapter = new ShangPinLabelAdapter(listShangPinLabel, new ShangPinLabelAdapter.OnItemChoosed() {
            @Override
            public void onChoosed(int pos) {
                edtName.setText(listShangPinLabel.get(pos).getName());
                edtName.setSelection(edtName.getText().length());
            }

            @Override
            public void onLongClick(int pos) {

            }
        });
        GridLayoutManager managerLabel = new GridLayoutManager(ShangPinLabelEditActivity.this,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerLabel.setOrientation(LinearLayoutManager.VERTICAL);
        rvLabel.setLayoutManager(managerLabel);
        rvLabel.setAdapter(shangPinLabelAdapter);
        ViseHttp.POST(NetConfig.tagList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.tagList))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson =  new Gson();
                                listShangPinLabel.clear();
                                listShangPinLabel.addAll(gson.fromJson(data,ShangPinLabelModel.class).getObj());
                                shangPinLabelAdapter.notifyDataSetChanged();
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
    @OnClick({R.id.rl_back,R.id.rl_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_save:
                if (edtName.getText().toString().equals("")){
                    toToast(ShangPinLabelEditActivity.this,"请输入标签名称");
                    break;
                }
                if (isEdit){
                    ViseHttp.POST(NetConfig.editMyTag)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.editMyTag))
                            .addParam("id", editLabelMode.getId())
                            .addParam("name",edtName.getText().toString())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            jsonObject.getJSONObject("obj").toString();
                                            ShangPinLabelModel.ObjBean bean = new ShangPinLabelModel.ObjBean();
                                            bean.setChecked(false);
                                            bean.setId((jsonObject.getJSONObject("obj").getString("id")));
                                            bean.setName(edtName.getText().toString());
                                            Intent intent = new Intent();
                                            intent.putExtra(NEW_ADD_LABEL_MODEL,bean);
                                            setResult(ADD_SUCCESS_RESULT_CODE,intent);
                                            toToast(ShangPinLabelEditActivity.this,"添加成功");
                                            onBackPressed();
                                        }else {
                                            toToast(ShangPinLabelEditActivity.this,"添加失败");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        toToast(ShangPinLabelEditActivity.this,"添加失败");
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    toToast(ShangPinLabelEditActivity.this,"添加失败");
                                }
                            });
                }else {
                    ViseHttp.POST(NetConfig.addMyTag)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addServe))
                            .addParam("uid", spImp.getUID())
                            .addParam("name",edtName.getText().toString())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            jsonObject.getJSONObject("obj").toString();
                                            ShangPinLabelModel.ObjBean bean = new ShangPinLabelModel.ObjBean();
                                            bean.setChecked(false);
                                            bean.setId((jsonObject.getJSONObject("obj").getString("id")));
                                            bean.setName(edtName.getText().toString());
                                            Intent intent = new Intent();
                                            intent.putExtra(NEW_ADD_LABEL_MODEL,bean);
                                            setResult(ADD_SUCCESS_RESULT_CODE,intent);
                                            toToast(ShangPinLabelEditActivity.this,"添加成功");
                                            onBackPressed();
                                        }else {
                                            toToast(ShangPinLabelEditActivity.this,"添加失败");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        toToast(ShangPinLabelEditActivity.this,"添加失败");
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    toToast(ShangPinLabelEditActivity.this,"添加失败");
                                }
                            });
                }
                break;
        }
    }
}
