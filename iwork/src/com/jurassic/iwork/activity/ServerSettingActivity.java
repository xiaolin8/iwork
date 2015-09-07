package com.jurassic.iwork.activity;

import com.jurassic.iwork.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServerSettingActivity extends BaseActivity {
	private EditText serverUrlEt;
	private EditText serverPortEt;
	private Button recoverServerSettingBtn;
	private String currentAccount;
	private String currentPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_setting);
	}
	
	/** 点击恢复初始服务器设置 */
	public void recoverServerSetting(View view) {
		
	}
}
