package com.jurassic.iwork.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easemob.chat.EMChatManager;
import com.jurassic.applib.controller.HXSDKHelper;
import com.jurassic.iwork.R;

public class GeneralSettingActivity extends BaseActivity implements
		OnClickListener {

	/** 设置2G/3G/4G自动接收图片布局 */
	private RelativeLayout rl_switch_network_picture;

	/** 设置2G/3G/4G联系人头像更新布局 */
	private RelativeLayout rl_switch_network_headpic;

	/** 设置回车键发送消息布局 */
	private RelativeLayout rl_switch_enter_send;

	/** 打开新消息通知imageView */
	private ImageView iv_switch_open_network_picture;

	/** 关闭新消息通知imageView */
	private ImageView iv_switch_close_network_picture;

	/** 打开新消息通知imageView */
	private ImageView iv_switch_open_network_headpic;

	/** 关闭新消息通知imageView */
	private ImageView iv_switch_close_network_headpic;

	/** 打开新消息通知imageView */
	private ImageView iv_switch_open_enter_send;

	/** 关闭新消息通知imageView */
	private ImageView iv_switch_close_enter_send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_setting);
		rl_switch_network_picture = (RelativeLayout) this
				.findViewById(R.id.rl_switch_clean_remind);
		rl_switch_network_headpic = (RelativeLayout) this
				.findViewById(R.id.rl_switch_network_headpic);
		rl_switch_enter_send = (RelativeLayout) this
				.findViewById(R.id.rl_switch_enter_send);
		rl_switch_network_picture.setOnClickListener(this);
		rl_switch_network_headpic.setOnClickListener(this);
		rl_switch_enter_send.setOnClickListener(this);
		iv_switch_open_network_picture = (ImageView) this
				.findViewById(R.id.iv_switch_open_network_picture);
		iv_switch_close_network_picture = (ImageView) this
				.findViewById(R.id.iv_switch_close_network_picture);
		iv_switch_open_network_headpic = (ImageView) this
				.findViewById(R.id.iv_switch_open_network_headpic);
		iv_switch_close_network_headpic = (ImageView) this
				.findViewById(R.id.iv_switch_close_network_headpic);
		iv_switch_open_enter_send = (ImageView) this
				.findViewById(R.id.iv_switch_open_enter_send);
		iv_switch_close_enter_send = (ImageView) this
				.findViewById(R.id.iv_switch_close_enter_send);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_clean_remind:
			if (iv_switch_open_network_picture.getVisibility() == View.VISIBLE) {
				iv_switch_open_network_picture.setVisibility(View.INVISIBLE);
				iv_switch_close_network_picture.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_network_picture.setVisibility(View.VISIBLE);
				iv_switch_close_network_picture.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_switch_network_headpic:
			if (iv_switch_open_network_headpic.getVisibility() == View.VISIBLE) {
				iv_switch_open_network_headpic.setVisibility(View.INVISIBLE);
				iv_switch_close_network_headpic.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_network_headpic.setVisibility(View.VISIBLE);
				iv_switch_close_network_headpic.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_switch_enter_send:
			if (iv_switch_open_enter_send.getVisibility() == View.VISIBLE) {
				iv_switch_open_enter_send.setVisibility(View.INVISIBLE);
				iv_switch_close_enter_send.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_enter_send.setVisibility(View.VISIBLE);
				iv_switch_close_enter_send.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}

}