package com.jurassic.iwork.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jurassic.iwork.R;
import com.jurassic.iwork.widget.CustomQuestionDialog;

public class KaoQinSigninActivity extends BaseActivity implements
		OnClickListener, AMapLocationListener, Runnable {
	private LocationManagerProxy aMapLocManager = null;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private ProgressDialog progressDialog;
	private Handler handler = new Handler();
	/** 签到布局 */
	private RelativeLayout rl_switch_signin;

	/** 签退布局 */
	private RelativeLayout rl_switch_signout;

	private TextView tv_singin, time_signin, time_signout;

	private ImageView image_signin;
	private ImageView image_signout;

	private ImageView time_signin_more;
	private ImageView time_signout_more;

	private CustomQuestionDialog questionDialog;

	private int signinORout = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoqin_signin);

		rl_switch_signin = (RelativeLayout) this
				.findViewById(R.id.rl_switch_signin);
		rl_switch_signout = (RelativeLayout) this
				.findViewById(R.id.rl_switch_signout);
		rl_switch_signin.setOnClickListener(this);
		rl_switch_signout.setOnClickListener(this);
		time_signin_more = (ImageView) this.findViewById(R.id.time_signin_more);
		time_signout_more = (ImageView) this
				.findViewById(R.id.time_signout_more);
		image_signin = (ImageView) this.findViewById(R.id.image_signin);
		image_signout = (ImageView) this.findViewById(R.id.image_signout);
		time_signin = (TextView) this.findViewById(R.id.time_signin);
		time_signout = (TextView) this.findViewById(R.id.time_signout);
		tv_singin = (TextView) this.findViewById(R.id.tv_singin);
		String text = "考勤时段：08:30 - 17:30";
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.rgb(255, 178, 65)), 5, 18,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_singin.setText(style);
	}

	public void remindSign(View v) {
		startActivity(new Intent(this, KaoQinRemindActivity.class));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_signin:
			if (time_signin.getText().equals("test")) {
				progressDialog = new ProgressDialog(this);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage("正在确定你的位置...");

				progressDialog.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface arg0) {
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}
				});

				progressDialog.show();
				// 进行定位
				aMapLocManager = LocationManagerProxy.getInstance(this);
				/*
				 * mAMapLocManager.setGpsEnable(false);//
				 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
				 * Location API定位采用GPS和网络混合定位方式
				 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
				 */
				aMapLocManager.requestLocationUpdates(
						LocationProviderProxy.AMapNetwork, 2000, 10, this);
				handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
				signinORout = 0;
			}
			break;
		case R.id.rl_switch_signout:
			if (time_signout.getText().equals("test")) {
				progressDialog = new ProgressDialog(this);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage("正在确定你的位置...");

				progressDialog.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface arg0) {
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}
				});

				progressDialog.show();
				aMapLocManager = LocationManagerProxy.getInstance(this);
				/*
				 * mAMapLocManager.setGpsEnable(false);//
				 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
				 * Location API定位采用GPS和网络混合定位方式
				 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
				 */
				aMapLocManager.requestLocationUpdates(
						LocationProviderProxy.AMapNetwork, 2000, 10, this);
				handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
				signinORout = 1;
			}
			break;
		default:
			break;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocation();// 停止定位
	}

	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destory();
		}
		aMapLocManager = null;
	}

	@Override
	public void run() {
		if (aMapLocation == null) {
			stopLocation();// 销毁掉定位
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onLocationChanged",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/**
	 * 混合定位回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			this.aMapLocation = location;// 判断超时机制
			// 不知道为什么获取的时间与准确时间之间总是存在着一定差距
			// long time = location.getTime();
			// Date date2 = new Date(time);
			// date2.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
					Locale.getDefault());
			final String strTime = sdf.format(new Date());
			String desc = "", txtLeftBtn = "", txtMiddleBtn = "", txtRightBtn = "", txtStatus = "", txtPosition = "";
			int hour = new Date().getHours();
			int minute = new Date().getMinutes();
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				desc = locBundle.getString("desc").replaceAll(" ", "");
			}
			switch (signinORout) {
			case 0:
				if (hour < 8 && hour > 5 && minute >= 0 && minute <= 30) {
					txtLeftBtn = "提交";
					txtMiddleBtn = "";
					txtRightBtn = "取消";
					txtPosition = "	当前签到位置：" + desc;
					txtStatus = "\n您的签到数据正常，是否提交？";
				} else {
					txtLeftBtn = "直接提交";
					txtMiddleBtn = "补充后提交";
					txtRightBtn = "放弃打卡";
					txtPosition = "	当前签到位置：" + desc;
					txtStatus = "\n您的签到时间不符合公司要求，是否补充图片或文字说明？";
				}
				questionDialog = new CustomQuestionDialog(this,
						R.style.mydialog, R.layout.question_dialog, "提示",
						txtPosition + txtStatus, txtLeftBtn, txtMiddleBtn,
						txtRightBtn);
				questionDialog
						.setOnLeftButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								image_signin
										.setBackgroundResource(R.drawable.check_ok);
								time_signin.setText(strTime);
								time_signin.setVisibility(View.VISIBLE);
								time_signin_more.setVisibility(View.INVISIBLE);
							}
						});
				questionDialog
						.setOnMiddleButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(new Intent(
										KaoQinSigninActivity.this,
										KaoQinAdditionActivity.class));
							}
						});
				questionDialog
						.setOnRightButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				questionDialog.show();
				break;
			case 1:
				if ((hour == 17 && minute >= 30) || (hour > 17)) {
					txtLeftBtn = "提交";
					txtMiddleBtn = "";
					txtRightBtn = "取消";
					txtPosition = "	当前签退位置：" + desc;
					txtStatus = "\n您的签退数据正常，是否补充图片或文字说明？";
				} else {
					txtLeftBtn = "直接提交";
					txtMiddleBtn = "补充后提交";
					txtRightBtn = "放弃打卡";
					txtPosition = "	当前签退位置：" + desc;
					txtStatus = "\n您的签退时间不符合公司要求，是否提交？";
				}
				questionDialog = new CustomQuestionDialog(this,
						R.style.mydialog, R.layout.question_dialog, "提示",
						txtPosition + txtStatus, txtLeftBtn, txtMiddleBtn,
						txtRightBtn);
				questionDialog
						.setOnLeftButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								image_signout
										.setBackgroundResource(R.drawable.check_ok);
								time_signout.setText(strTime);
								time_signout.setVisibility(View.VISIBLE);
								time_signout_more.setVisibility(View.INVISIBLE);
							}
						});
				questionDialog
						.setOnMiddleButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(new Intent(
										KaoQinSigninActivity.this,
										KaoQinAdditionActivity.class));
							}
						});
				questionDialog.show();
				break;
			default:
				break;
			}
			stopLocation();// 销毁掉定位
		}
	}
}
