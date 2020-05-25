package com.yiwo.fuzhoudian.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.model.ShangPinServiceModel;
import com.yiwo.fuzhoudian.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShangPinServiceEditActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_info)
    EditText edtInfo;

    public static String NEW_ADD_SERVICE_MODEL = "new_add_service_model";
    public static int ADD_SUCCESS_RESULT_CODE = 1;
    SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_pin_service_edit);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initView();
    }

    private void initView() {
        edtInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtInfo.setHint("");
                }else {
                    if (edtInfo.getText().toString().equals("")){
                        edtInfo.setHint("在这里输入说明(200字以内)");
                    }
                }
            }
        });
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtName.setHint("");
                }else {
                    if (edtName.getText().toString().equals("")){
                        edtName.setHint("在这里输入（6字以内）");
                    }
                }
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
                    toToast(ShangPinServiceEditActivity.this,"请输入服务名称");
                    break;
                }
                if (edtInfo.getText().toString().equals("")){
                    toToast(ShangPinServiceEditActivity.this,"请输入服务说明");
                    break;
                }
                ViseHttp.POST(NetConfig.addServe)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addServe))
                        .addParam("uid", spImp.getUID())
                        .addParam("name",edtName.getText().toString())
                        .addParam("info",edtInfo.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        jsonObject.getJSONObject("obj").toString();
                                        ShangPinServiceModel.ObjBean bean = gson.fromJson(jsonObject.getJSONObject("obj").toString(),ShangPinServiceModel.ObjBean.class);
                                        Intent intent = new Intent();
                                        intent.putExtra(NEW_ADD_SERVICE_MODEL,bean);
                                        setResult(ADD_SUCCESS_RESULT_CODE,intent);
                                        toToast(ShangPinServiceEditActivity.this,"添加成功");
                                        onBackPressed();
                                    }else {
                                        toToast(ShangPinServiceEditActivity.this,"添加失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
        }
    }
}
