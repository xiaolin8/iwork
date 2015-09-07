package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.jurassic.iwork.R;

public class CleanTraceSettingActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout cleanTrace_clean_perfile, cleanTrace_clean_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clean_trace);
		cleanTrace_clean_perfile = (LinearLayout) this
				.findViewById(R.id.cleanTrace_clean_perfile);
		cleanTrace_clean_perfile.setOnClickListener(this);
		cleanTrace_clean_setting = (LinearLayout) this
				.findViewById(R.id.cleanTrace_clean_setting);
		cleanTrace_clean_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cleanTrace_clean_perfile:
			startActivity(new Intent(this, CleanPersonalFileActivity.class));
			break;
		case R.id.cleanTrace_clean_setting:
			startActivity(new Intent(this, CleanSettingActivity.class));
			break;
		default:
			break;
		}
	}

}
