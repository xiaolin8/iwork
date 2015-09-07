package com.jurassic.iwork.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jurassic.iwork.R;

public class CleanSettingActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_switch_notification;
	private ImageView iv_switch_open_clean_remind,
			iv_switch_close_clean_remind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clean_setting);
		rl_switch_notification = (RelativeLayout) this
				.findViewById(R.id.rl_switch_notification);
		rl_switch_notification.setOnClickListener(this);
		iv_switch_open_clean_remind = (ImageView) this
				.findViewById(R.id.iv_switch_open_clean_remind);
		iv_switch_close_clean_remind = (ImageView) this
				.findViewById(R.id.iv_switch_close_clean_remind);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_notification:
			if (iv_switch_open_clean_remind.getVisibility() == View.VISIBLE) {
				iv_switch_open_clean_remind.setVisibility(View.INVISIBLE);
				iv_switch_close_clean_remind.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_clean_remind.setVisibility(View.VISIBLE);
				iv_switch_close_clean_remind.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}

}
