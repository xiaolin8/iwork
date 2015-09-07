package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jurassic.iwork.R;

public class GestureCodeNoticeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_code);
	}

	public void createGesture(View v) {
		startActivity(new Intent(this, CreateGestureCodeActivity.class));
	}
}
