package com.yiwo.fuzhoudian.pages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.model.ShopLocationInfoModel;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopLocationActivity extends BaseActivity {

    public LocationClient mLocationClient = null;
    @BindView(R.id.edt_weizhi_info)
    EditText edtWeiZhiInfo;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.bmapView)
    MapView mBmapView;
    BaiduMap mBaiduMap;
    @BindView(R.id.rl_set_return)
    RelativeLayout mRlSetReturn;
    @BindView(R.id.rl_save)
    RelativeLayout mRlSave;
    private MyLocationListener myListener = new MyLocationListener();
    LocationClientOption option = new LocationClientOption();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文第四步的说明
    GeoCoder mCoder = GeoCoder.newInstance();

    String shopLat = "";
    String shopLng = "";
    String address = "";
    SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        mBaiduMap = mBmapView.getMap();
//普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapClickListener(listener);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initOption();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getUserAddressLatLng)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.getUserAddressLatLng))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ShopLocationInfoModel model = gson.fromJson(data, ShopLocationInfoModel.class);
                                shopLat = model.getObj().getShopLat();
                                shopLng = model.getObj().getShopLng();
                                address = model.getObj().getAddress();
                                if (TextUtils.isEmpty(shopLat)||TextUtils.isEmpty(shopLng)){
                                    mLocationClient.start();
                                    edtWeiZhiInfo.setText(model.getObj().getAddress());
                                    Log.d("定位——————定位","没历史坐标");
                                }else {
                                    Log.d("定位——————定位","有历史坐标");
                                    LatLng point = new LatLng(Double.parseDouble(shopLat),Double.parseDouble(shopLng));
                                    handlePoint(point,false);
                                    MapStatus mMapStatus = new MapStatus.Builder().target(point)
                                            .zoom(18.0f).build();
                                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                                            .newMapStatus(mMapStatus);
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.zoom(18.0f);
                                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                                    edtWeiZhiInfo.setText(model.getObj().getAddress());
                                }
                                edtWeiZhiInfo.setSelection(edtWeiZhiInfo.getText().length());
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

    @OnClick({R.id.btn, R.id.rl_set_return, R.id.rl_save})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                mLocationClient.start();
                break;
            case R.id.rl_set_return:
                onBackPressed();
                break;
            case R.id.rl_save:
                save();
                break;
        }
    }

    private void save() {
        address = edtWeiZhiInfo.getText().toString();
        if (TextUtils.isEmpty(shopLat)||TextUtils.isEmpty(shopLng)||TextUtils.isEmpty(address)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("请在地图上选择店铺位置")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
            return;
        }
        ViseHttp.POST(NetConfig.userAddressLatLng)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.userAddressLatLng))
                .addParam("uid",spImp.getUID())
                .addParam("shopLat",shopLat)
                .addParam("shopLng",shopLng)
                .addParam("address",address)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(ShopLocationActivity.this,"保存成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(ShopLocationActivity.this,"保存失败");
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(ShopLocationActivity.this,"保存失败");
                    }
                });
    }

    private void initOption() {
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        option.setNeedNewVersionRgc(true);
//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            handlePoint(point,true);
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public void onMapPoiClick(MapPoi mapPoi) {
            handlePoint(mapPoi.getPosition(),true);
        }
    };

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            String town = location.getTown();    //获取乡镇信息
//            edtWeiZhiInfo.setText("经度：" + latitude + "\r\n纬度：" + longitude + "\r\n定位精度:" + radius + "\r\n坐标类型:" + coorType + "\r\n位置描述：" + location.getLocationDescribe() +
//                    "\r\n" + addr + "/" + country + "/" + province + "/" + city + "/" + district + "/" + street + "/" + adcode + "/" + town + "/");
            if (location == null || mBmapView == null) {
                return;
            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
            LatLng cenpt = new LatLng(location.getLatitude(),
                    location.getLongitude());
// 定义地图状态zoom表示缩放级别3-18
            MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
                    .zoom(18.0f).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mMapStatus);
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.zoom(18.0f);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
            mLocationClient.stop();
        }

        @Override
        public void onLocDiagnosticMessage(int i, int i1, String s) {
            super.onLocDiagnosticMessage(i, i1, s);
            Log.d("定位——————定位",i+"///"+i1+"///"+s);
            toToast(ShopLocationActivity.this,"无法定位到当前位置，请开启手机定位后重试");
            LatLng cenpt;
            if (TextUtils.isEmpty(shopLat)||TextUtils.isEmpty(shopLng)){
                cenpt = new LatLng(39.91327919129028,
                        116.40392813332507 );
            }else {
                cenpt = new LatLng(Double.parseDouble(shopLat),Double.parseDouble(shopLng));
            }

// 定义地图状态zoom表示缩放级别3-18
            MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
                    .zoom(18.0f).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mMapStatus);
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.zoom(18.0f);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
            mLocationClient.stop();
        }
    }


    /**
     * 处理点击获取到的坐标点
     *
     * @param point
     */
    private void handlePoint(final LatLng point, final boolean resetEdt) {
        //定义Maker坐标点
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.dingwei_datouzhen);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.clear();
        mBaiduMap.addOverlay(option);
        mCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    //行政区号
                    int adCode = reverseGeoCodeResult.getCityCode();
                    Log.d("infoinfo", reverseGeoCodeResult.getAddress() + "\r\n门牌号：" + reverseGeoCodeResult.getAddressDetail().streetNumber);
                    if (reverseGeoCodeResult.getPoiRegionsInfoList() != null) {
                        for (ReverseGeoCodeResult.PoiRegionsInfo info : reverseGeoCodeResult.getPoiRegionsInfoList()) {
                            Log.d("infoinfo附近地址", info.regionName + "//" + info.directionDesc);
                        }
                    }
                    if (resetEdt){
                        edtWeiZhiInfo.setText(reverseGeoCodeResult.getAddress() + "");
                        edtWeiZhiInfo.setSelection(edtWeiZhiInfo.getText().length());
                    }
                }
                shopLat = point.latitude+"";
                shopLng = point.longitude+"";
                address = edtWeiZhiInfo.getText().toString();
            }
        });
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(point)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBmapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mBmapView.onDestroy();
        mCoder.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBmapView.onPause();
    }
}
