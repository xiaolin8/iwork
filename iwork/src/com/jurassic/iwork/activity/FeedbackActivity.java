package com.jurassic.iwork.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jurassic.iwork.R;

public class FeedbackActivity extends BaseActivity {
	private TextView feedback_softwarVersionTv;
	private TextView feedback_mobilePlatformTv;
	private TextView feedback_networkTypeTv;
	private TextView feedback_languageEnvironmentTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		feedback_softwarVersionTv = (TextView) this
				.findViewById(R.id.feedback_softwarVersionTv);
		feedback_softwarVersionTv.setText("V1.0.0");
		feedback_mobilePlatformTv = (TextView) this
				.findViewById(R.id.feedback_mobilePlatformTv);
		feedback_mobilePlatformTv.setText("Android "
				+ android.os.Build.VERSION.RELEASE);
		feedback_networkTypeTv = (TextView) this
				.findViewById(R.id.feedback_networkTypeTv);
		ConnectivityManager connectMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)
				feedback_networkTypeTv.setText("WiFi");
			else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				switch (info.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_CDMA:
					feedback_networkTypeTv.setText("电信2G");
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					feedback_networkTypeTv.setText("EDGE");
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					feedback_networkTypeTv.setText("电信3G");
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					feedback_networkTypeTv.setText("电信3G");
					break;
				case TelephonyManager.NETWORK_TYPE_GPRS:
					feedback_networkTypeTv.setText("GPRS");
					break;
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					feedback_networkTypeTv.setText("HSDPA");
					break;
				case TelephonyManager.NETWORK_TYPE_HSPA:
					feedback_networkTypeTv.setText("移动或联通2G");
					break;
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					feedback_networkTypeTv.setText("联通3G");
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					feedback_networkTypeTv.setText("联通3G");
					break;
				default:
					feedback_networkTypeTv.setText(info.getSubtype() + "");
					break;
				}
			}
		} else
			feedback_networkTypeTv.setText("网络故障！");
		feedback_languageEnvironmentTv = (TextView) this
				.findViewById(R.id.feedback_languageEnvironmentTv);
		feedback_languageEnvironmentTv.setText(getResources()
				.getConfiguration().locale.getLanguage());

	}

	public void save(View v) {
		// 应有ProgessDialog的
		Toast.makeText(this, "已收到，谢谢您的反馈建议！", Toast.LENGTH_SHORT).show();
		finish();
	}
}
