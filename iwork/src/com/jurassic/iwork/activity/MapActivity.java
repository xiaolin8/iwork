package com.jurassic.iwork.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.jurassic.iwork.R;

public class MapActivity extends BaseActivity implements LocationSource,
		AMapLocationListener {

	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker marker;// 定位雷达小图标
	private float zoom = 16f;// 地图缩放级别
	private ProgressDialog progressDialog;
	private Button sendButton = null;
	private double geoLat;
	private double geoLng;
	private String desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		sendButton = (Button) findViewById(R.id.btn_location_send);
		init();
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("latitude", 0);
		if (latitude == 0) {
			showMapWithLocation();
		} else {
			double longtitude = intent.getDoubleExtra("longitude", 0);
			String address = intent.getStringExtra("address");
			showMap(latitude, longtitude, address);
		}
	}

	private void showMap(double latitude, double longtitude, String address) {
		sendButton.setVisibility(View.GONE);
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		// 用给定的经纬度构造一个GeoPoint，单位是微度(度*1E6)
		LatLng marker1 = new LatLng(latitude, longtitude); // 设置中心点和缩放比例
		aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
		aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
	}

	private void showMapWithLocation() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在确定你的位置...");

		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface arg0) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Log.d("map", "cancel retrieve location");
				finish();
			}
		});

		progressDialog.show();
		mapView.invalidate();
	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	/**
	 * 设置些amap的属性
	 */
	private void setUpMap() {
		marker = aMap.addMarker(new MarkerOptions().visible(false));// 设置默认蓝色定位图标不显示
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜
		myLocationStyle.radiusFillColor(Color.TRANSPARENT);
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setMyLocationRotateAngle(180);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
	}

	@Override
	public void onLocationChanged(Location location) {
		if (mListener != null && location != null) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			mListener.onLocationChanged(location);// 显示系统小蓝点
			marker.setPosition(new LatLng(location.getLatitude(), location
					.getLongitude()));// 定位雷达小图标
			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		status = 12;
	}

	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null && aLocation != null) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
			marker.setPosition(new LatLng(aLocation.getLatitude(), aLocation
					.getLongitude()));// 定位雷达小图标
			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
			geoLat = aLocation.getLatitude();
			geoLng = aLocation.getLongitude();
			desc = "";
			Bundle locBundle = aLocation.getExtras();
			if (locBundle != null) {
				desc = locBundle.getString("desc");
				desc = desc.replaceAll(" ", "");
			}
			sendButton.setEnabled(true);
		}
	}

	public void sendLocation(View view) {
		Intent intent = this.getIntent();
		intent.putExtra("latitude", geoLat);
		intent.putExtra("longitude", geoLng);
		intent.putExtra("address", desc);
		this.setResult(RESULT_OK, intent);
		finish();
		overridePendingTransition(R.anim.slide_in_from_left,
				R.anim.slide_out_to_right);
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方�?
			 * ，第�?个参数是定位provider，第二个参数时间�?短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听�??
			 */
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	/** 停止定位 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	/** 方法必须重写 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/** 方法必须重写 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/** 方法必须重写 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/** 方法必须重写 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
