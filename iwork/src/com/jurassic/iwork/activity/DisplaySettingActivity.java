package com.jurassic.iwork.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jurassic.iwork.R;

public class DisplaySettingActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout chat_bg_layout, font_size_layout;
	final String[] arrayFruit = new String[] { "苹果", "橘子", "草莓", "香蕉" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_setting);
		chat_bg_layout = (LinearLayout) this.findViewById(R.id.chat_bg_layout);
		font_size_layout = (LinearLayout) this
				.findViewById(R.id.font_size_layout);
//		AlertDialog dialog = new AlertDialog();
//		dialog.setTitle("你喜欢吃哪种水果?");
//		dialog.setContentView(R.layout.question_dialog);
//		dialog.showDialog(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_bg_layout:
			// startActivity(new Intent(this, AboutActivity.class));
			Toast.makeText(this, "当前已为最新版本！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.font_size_layout:
			break;
		default:
			break;
		}
	}

	public void back(View v) {
		finish();
	}
}
