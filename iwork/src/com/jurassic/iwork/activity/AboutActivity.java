package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jurassic.iwork.R;

public class AboutActivity extends BaseActivity implements OnClickListener {

	TextView checkUpdateNewTv;
	ImageView aboutMoreMarkIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		checkUpdateNewTv = (TextView) this.findViewById(R.id.checkUpdateNewTv);
		aboutMoreMarkIv = (ImageView) this.findViewById(R.id.aboutMoreMarkIv);
		RelativeLayout checkUpdateLayou, suggest_relativeLay, help_relativeLay;
		checkUpdateLayou = (RelativeLayout) this
				.findViewById(R.id.checkUpdateLayou);
		checkUpdateLayou.setOnClickListener(this);
		suggest_relativeLay = (RelativeLayout) this
				.findViewById(R.id.suggest_relativeLay);
		suggest_relativeLay.setOnClickListener(this);
		help_relativeLay = (RelativeLayout) this
				.findViewById(R.id.help_relativeLay);
		help_relativeLay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkUpdateLayou:
			// startActivity(new Intent(this, AboutActivity.class));
			Toast.makeText(this, "当前已为最新版本！", Toast.LENGTH_SHORT).show();
			checkUpdateNewTv.setVisibility(View.GONE);
			break;
		case R.id.suggest_relativeLay:
			startActivity(new Intent(this, FeedbackActivity.class));
			break;
		case R.id.help_relativeLay:
			aboutMoreMarkIv.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	public void back(View v) {
		finish();
	}
}
