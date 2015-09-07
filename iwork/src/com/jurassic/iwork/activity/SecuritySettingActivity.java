package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jurassic.iwork.R;

public class SecuritySettingActivity extends BaseActivity implements
		OnClickListener {

	/** 设置手势密码布局 */
	private RelativeLayout moreGestureCodeLayout;

	/** 设置清理痕迹布局 */
	private RelativeLayout morecleaning_trace;

	/** 设置同步最近聊天消息布局 */
	private RelativeLayout synChatRecordChbLayout;

	/** 设置修改登录密码布局 */
	private RelativeLayout modifyPwdbLayout;

	/** 打开同步最近聊天消息imageView */
	private ImageView iv_switch_open_synChatRecordChb;

	/** 关闭同步最近聊天消息imageView */
	private ImageView iv_switch_close_synChatRecordChb;

	private TextView moreGestureCodeTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_setting);
		moreGestureCodeLayout = (RelativeLayout) this
				.findViewById(R.id.moreGestureCodeLayout);
		morecleaning_trace = (RelativeLayout) this
				.findViewById(R.id.morecleaning_trace);
		synChatRecordChbLayout = (RelativeLayout) this
				.findViewById(R.id.synChatRecordChbLayout);
		modifyPwdbLayout = (RelativeLayout) this
				.findViewById(R.id.modifyPwdbLayout);
		moreGestureCodeTv = (TextView) this
				.findViewById(R.id.moreGestureCodeTv);
		moreGestureCodeLayout.setOnClickListener(this);
		morecleaning_trace.setOnClickListener(this);
		synChatRecordChbLayout.setOnClickListener(this);
		modifyPwdbLayout.setOnClickListener(this);
		iv_switch_open_synChatRecordChb = (ImageView) this
				.findViewById(R.id.iv_switch_open_synChatRecordChb);
		iv_switch_close_synChatRecordChb = (ImageView) this
				.findViewById(R.id.iv_switch_close_synChatRecordChb);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.moreGestureCodeLayout:
			if (moreGestureCodeTv.getText().toString().equals("未设置")) {
				startActivity(new Intent(this, GestureCodeNoticeActivity.class));
			}
			break;
		case R.id.synChatRecordChbLayout:
			if (iv_switch_open_synChatRecordChb.getVisibility() == View.VISIBLE) {
				iv_switch_open_synChatRecordChb.setVisibility(View.INVISIBLE);
				iv_switch_close_synChatRecordChb.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_synChatRecordChb.setVisibility(View.VISIBLE);
				iv_switch_close_synChatRecordChb.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.morecleaning_trace:
			startActivity(new Intent(this, CleanTraceSettingActivity.class));
			break;
		default:
			break;
		}
	}
}
