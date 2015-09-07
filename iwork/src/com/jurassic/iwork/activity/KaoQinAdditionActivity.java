package com.jurassic.iwork.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jurassic.iwork.R;

public class KaoQinAdditionActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoqin_addition);
	}

	public void SignIn(View v) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_signin:
			break;
		case R.id.rl_switch_signout:
			break;
		default:
			break;
		}

	}
}
