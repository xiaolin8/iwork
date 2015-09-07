package com.jurassic.iwork.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.jurassic.applib.controller.HXSDKHelper;
import com.jurassic.iwork.R;

public class MessageNoticeActivity extends BaseActivity implements
		OnClickListener {

	/** 设置新消息通知布局 */
	private RelativeLayout rl_switch_notification;

	/** 设置声音布局 */
	private RelativeLayout rl_switch_sound;

	/** 设置震动布局 */
	private RelativeLayout rl_switch_vibrate;

	/** 设置夜间防骚扰布局 */
	private RelativeLayout rl_switch_block_nightmsg;

	/** 设置通知显示消息内容布局 */
	private RelativeLayout rl_switch_showmsg_content;

	/** 设置通知时指示灯闪烁 */
	private RelativeLayout rl_switch_blink;

	/** 打开新消息通知imageView */
	private ImageView iv_switch_open_notification;

	/** 关闭新消息通知imageview */
	private ImageView iv_switch_close_notification;

	/** 打开声音提示imageview */
	private ImageView iv_switch_open_sound;

	/** 关闭声音提示imageview */
	private ImageView iv_switch_close_sound;

	/** 打开消息震动提示 */
	private ImageView iv_switch_open_vibrate;

	/** 关闭消息震动提示 */
	private ImageView iv_switch_close_vibrate;

	/** 打开夜间防骚扰模式 */
	private ImageView iv_switch_open_block_nightmsg;

	/** 关闭夜间防骚扰模式 */
	private ImageView iv_switch_close_block_nightmsg;

	/** 关闭通知显示消息内容 */
	private ImageView iv_switch_unblock_msgcontent;

	/** 开启通知显示消息内容 */
	private ImageView iv_switch_block_msgcontent;

	/** 开启通知时指示灯闪烁 */
	private ImageView iv_switch_unblock_blink;

	/** 关闭通知时指示灯闪烁 */
	private ImageView iv_switch_block_blink;

	private EMChatOptions chatOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_notice);
		rl_switch_notification = (RelativeLayout) this
				.findViewById(R.id.rl_switch_clean_remind);
		rl_switch_sound = (RelativeLayout) this
				.findViewById(R.id.rl_switch_network_headpic);
		rl_switch_vibrate = (RelativeLayout) this
				.findViewById(R.id.rl_switch_vibrate);
		rl_switch_block_nightmsg = (RelativeLayout) this
				.findViewById(R.id.rl_switch_enter_send);
		rl_switch_showmsg_content = (RelativeLayout) this
				.findViewById(R.id.rl_switch_showmsg_content);
		rl_switch_blink = (RelativeLayout) this
				.findViewById(R.id.rl_switch_blink);
		rl_switch_notification.setOnClickListener(this);
		rl_switch_sound.setOnClickListener(this);
		rl_switch_vibrate.setOnClickListener(this);
		rl_switch_block_nightmsg.setOnClickListener(this);
		rl_switch_showmsg_content.setOnClickListener(this);
		rl_switch_blink.setOnClickListener(this);
		iv_switch_open_notification = (ImageView) this
				.findViewById(R.id.iv_switch_open_network_picture);
		iv_switch_close_notification = (ImageView) this
				.findViewById(R.id.iv_switch_close_notification);
		iv_switch_open_sound = (ImageView) this
				.findViewById(R.id.iv_switch_open_network_headpic);
		iv_switch_close_sound = (ImageView) this
				.findViewById(R.id.iv_switch_close_sound);
		iv_switch_open_vibrate = (ImageView) this
				.findViewById(R.id.iv_switch_open_vibrate);
		iv_switch_close_vibrate = (ImageView) this
				.findViewById(R.id.iv_switch_close_vibrate);
		iv_switch_open_block_nightmsg = (ImageView) this
				.findViewById(R.id.iv_switch_open_enter_send);
		iv_switch_close_block_nightmsg = (ImageView) this
				.findViewById(R.id.iv_switch_close_block_nightmsg);
		iv_switch_unblock_msgcontent = (ImageView) this
				.findViewById(R.id.iv_switch_unblock_msgcontent);
		iv_switch_block_msgcontent = (ImageView) this
				.findViewById(R.id.iv_switch_block_msgcontent);
		iv_switch_unblock_blink = (ImageView) this
				.findViewById(R.id.iv_switch_unblock_blink);
		iv_switch_block_blink = (ImageView) this
				.findViewById(R.id.iv_switch_block_blink);
		chatOptions = EMChatManager.getInstance().getChatOptions();
		if (chatOptions.getNotificationEnable()) {
			iv_switch_open_notification.setVisibility(View.VISIBLE);
			iv_switch_close_notification.setVisibility(View.INVISIBLE);
		} else {
			iv_switch_open_notification.setVisibility(View.INVISIBLE);
			iv_switch_close_notification.setVisibility(View.VISIBLE);
		}
		if (chatOptions.getNoticedBySound()) {
			iv_switch_open_sound.setVisibility(View.VISIBLE);
			iv_switch_close_sound.setVisibility(View.INVISIBLE);
		} else {
			iv_switch_open_sound.setVisibility(View.INVISIBLE);
			iv_switch_close_sound.setVisibility(View.VISIBLE);
		}
		if (chatOptions.getNoticedByVibrate()) {
			iv_switch_open_vibrate.setVisibility(View.VISIBLE);
			iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
		} else {
			iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
			iv_switch_close_vibrate.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_clean_remind:
			if (iv_switch_open_notification.getVisibility() == View.VISIBLE) {
				iv_switch_open_notification.setVisibility(View.INVISIBLE);
				iv_switch_close_notification.setVisibility(View.VISIBLE);
				rl_switch_sound.setVisibility(View.GONE);
				rl_switch_vibrate.setVisibility(View.GONE);
				chatOptions.setNotificationEnable(false);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel()
						.setSettingMsgNotification(false);
			} else {
				iv_switch_open_notification.setVisibility(View.VISIBLE);
				iv_switch_close_notification.setVisibility(View.INVISIBLE);
				rl_switch_sound.setVisibility(View.VISIBLE);
				rl_switch_vibrate.setVisibility(View.VISIBLE);
				chatOptions.setNotificationEnable(true);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel()
						.setSettingMsgNotification(true);
			}
			break;
		case R.id.rl_switch_network_headpic:
			if (iv_switch_open_sound.getVisibility() == View.VISIBLE) {
				iv_switch_open_sound.setVisibility(View.INVISIBLE);
				iv_switch_close_sound.setVisibility(View.VISIBLE);
				chatOptions.setNoticeBySound(false);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
			} else {
				iv_switch_open_sound.setVisibility(View.VISIBLE);
				iv_switch_close_sound.setVisibility(View.INVISIBLE);
				chatOptions.setNoticeBySound(true);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
			}
			break;
		case R.id.rl_switch_vibrate:
			if (iv_switch_open_vibrate.getVisibility() == View.VISIBLE) {
				iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
				iv_switch_close_vibrate.setVisibility(View.VISIBLE);
				chatOptions.setNoticedByVibrate(false);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel()
						.setSettingMsgVibrate(false);
			} else {
				iv_switch_open_vibrate.setVisibility(View.VISIBLE);
				iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
				chatOptions.setNoticedByVibrate(true);
				EMChatManager.getInstance().setChatOptions(chatOptions);
				HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
			}
			break;
		case R.id.rl_switch_enter_send:
			if (iv_switch_open_block_nightmsg.getVisibility() == View.VISIBLE) {
				iv_switch_open_block_nightmsg.setVisibility(View.INVISIBLE);
				iv_switch_close_block_nightmsg.setVisibility(View.VISIBLE);
			} else {
				iv_switch_open_block_nightmsg.setVisibility(View.VISIBLE);
				iv_switch_close_block_nightmsg.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_switch_showmsg_content:
			if (iv_switch_unblock_msgcontent.getVisibility() == View.VISIBLE) {
				iv_switch_unblock_msgcontent.setVisibility(View.INVISIBLE);
				iv_switch_block_msgcontent.setVisibility(View.VISIBLE);
			} else {
				iv_switch_unblock_msgcontent.setVisibility(View.VISIBLE);
				iv_switch_block_msgcontent.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_switch_blink:
			if (iv_switch_unblock_blink.getVisibility() == View.VISIBLE) {
				iv_switch_unblock_blink.setVisibility(View.INVISIBLE);
				iv_switch_block_blink.setVisibility(View.VISIBLE);
			} else {
				iv_switch_unblock_blink.setVisibility(View.VISIBLE);
				iv_switch_block_blink.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}
}
