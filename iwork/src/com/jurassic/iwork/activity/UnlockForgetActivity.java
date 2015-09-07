package com.jurassic.iwork.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jurassic.iwork.R;
import com.jurassic.iwork.utils.StringUtil;

public class UnlockForgetActivity extends Activity {

	private EditText tvEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock_forget);
		tvEmail = (EditText) findViewById(R.id.tvEmail);
	}

	public void btn_getUnlockGesture_click(View view) {
		// 1.检查邮件地址输入是否正确
		if (StringUtil.isEmail(tvEmail.getText().toString())) {
			// TODO 2.请求服务器发送邮件
			Toast.makeText(this, "邮件发送成功！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "输入格式不正确！", Toast.LENGTH_SHORT).show();
			tvEmail.setText("");
		}
	}
}
