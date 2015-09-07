package com.jurassic.iwork.activity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.EMNotifier;
import com.easemob.chat.GroupChangeListener;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;
import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.db.InviteMessgeDao;
import com.jurassic.iwork.db.UserDao;
import com.jurassic.iwork.domain.InviteMessage;
import com.jurassic.iwork.domain.InviteMessage.InviteMesageStatus;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.fragment.ChatHomeFragment;
import com.jurassic.iwork.fragment.PrivateZoneHomeFragment;
import com.jurassic.iwork.fragment.PublicZoneHomeFragment;
import com.jurassic.iwork.fragment.SettingHomeFragment;

public class MainActivity extends BaseActivity {

	private TextView title;
	/** 未读会话消息textview */
	private TextView unread_msg_number;
	/** 未读个人空间消息textview */
	private TextView unread_private_zone_number;
	/** 未读企业空间textview */
	private TextView unread_public_zone_number;

	/** 获取当前屏幕的密度 */
	private DisplayMetrics dm;

	/** 底部的导航按钮 */
	private Button[] mTabs;

	/** 指当前选中的是第几个Tab */
	private int currentTabIndex;

	/** 指当前选中的是第几个导航按钮 */
	private int index;

	public ChatHomeFragment chatHomeFragment;
	private PrivateZoneHomeFragment privateZoneHomeFragment;
	private PublicZoneHomeFragment publicZoneHomeFragment;
	private SettingHomeFragment settingHomeFragment;

	private FragmentTransaction fragmentTransaction;

	private Fragment[] fragments;

	private NewMessageBroadcastReceiver msgReceiver;

	private android.app.AlertDialog.Builder conflictBuilder;
	// 账号在别处登录
	public boolean isConflict = false;
	private boolean isConflictDialogShow;
	private InviteMessgeDao inviteMessgeDao;
	private UserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean("isConflict", false)) {
			// 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
			// 三个fragment里加的判断同理
			finish();
			startActivity(new Intent(this, GestureUnlockActivity.class));
			return;
		}
		setContentView(R.layout.activity_main);
		setOverflowShowingAlways();

		chatHomeFragment = new ChatHomeFragment();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		// 添加显示第一个fragment
		fragmentTransaction.add(R.id.content, chatHomeFragment)
				.show(chatHomeFragment).commit();
		initView();
		if (getIntent().getBooleanExtra("conflict", false)
				&& !isConflictDialogShow)
			showConflictDialog();
		inviteMessgeDao = new InviteMessgeDao(this);
		userDao = new UserDao(this);
		fragments = new Fragment[4];
		fragments[0] = chatHomeFragment;
		// 注册一个接收消息的BroadcastReceiver
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(3);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

		// 注册一个透传消息的BroadcastReceiver
		IntentFilter cmdMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getCmdMessageBroadcastAction());
		cmdMessageIntentFilter.setPriority(3);
		registerReceiver(cmdMessageReceiver, cmdMessageIntentFilter);

		// setContactListener监听联系人的变化等
		EMContactManager.getInstance().setContactListener(
				new MyContactListener());
		// 注册一个监听连接状态的listener
		EMChatManager.getInstance().addConnectionListener(
				new MyConnectionListener());
		// 注册群聊相关的listener
		EMGroupManager.getInstance().addGroupChangeListener(
				new MyGroupChangeListener());
		// 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
		EMChat.getInstance().setAppInited();
	}

	/** 初始化组件 */
	private void initView() {
		title = (TextView) this.findViewById(R.id.title);
		unread_msg_number = (TextView) findViewById(R.id.unread_msg_number);
		unread_private_zone_number = (TextView) findViewById(R.id.unread_private_zone_number);
		unread_public_zone_number = (TextView) findViewById(R.id.unread_public_zone_number);

		mTabs = new Button[4];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_private_zone);
		mTabs[2] = (Button) findViewById(R.id.btn_public_zone);
		mTabs[3] = (Button) findViewById(R.id.btn_setting);
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		title.setText("会话");
	}

	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.btn_conversation:
			index = 0;
			// 因为MessageFragment肯定不为空，所以这里不用理会
			int currentPagerItem = chatHomeFragment.pager.getCurrentItem();
			if (currentPagerItem == 0)
				title.setText("会话");
			else if (currentPagerItem == 1)
				title.setText("联系人");
			break;
		case R.id.btn_private_zone:
			index = 1;
			title.setText("个人空间");
			if (privateZoneHomeFragment == null) {
				// 如果privateZoneHomeFragment为空，则创建一个并添加到界面上
				privateZoneHomeFragment = new PrivateZoneHomeFragment();
				fragments[1] = privateZoneHomeFragment;
			}
			break;
		case R.id.btn_public_zone:
			index = 2;
			title.setText("企业空间");
			if (publicZoneHomeFragment == null) {
				// 如果publicZoneHomeFragment为空，则创建一个并添加到界面上
				publicZoneHomeFragment = new PublicZoneHomeFragment();
				fragments[2] = publicZoneHomeFragment;
			}
			break;
		case R.id.btn_setting:
			index = 3;
			title.setText("设置");
			if (settingHomeFragment == null) {
				// 如果settingHomeFragment为空，则创建一个并添加到界面上
				settingHomeFragment = new SettingHomeFragment();
				fragments[3] = settingHomeFragment;
			}
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.content, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	/***
	 * 好友变化listener
	 * 
	 */
	private class MyContactListener implements EMContactListener {

		@Override
		public void onContactAdded(List<String> usernameList) {
			// 保存增加的联系人
			Map<String, User> localUsers = MyApplication.getInstance()
					.getContactList();
			Map<String, User> toAddUsers = new HashMap<String, User>();
			for (String username : usernameList) {
				User user = setUserHead(username);
				// 添加好友时可能会回调added方法两次
				if (!localUsers.containsKey(username)) {
					userDao.saveContact(user);
				}
				toAddUsers.put(username, user);
			}
			localUsers.putAll(toAddUsers);
			// 刷新ui
			if (currentTabIndex == 0 && chatHomeFragment != null
					&& chatHomeFragment.contactsFragment != null)
				chatHomeFragment.contactsFragment.refresh();
		}

		@Override
		public void onContactDeleted(final List<String> usernameList) {
			// 被删除
			Map<String, User> localUsers = MyApplication.getInstance()
					.getContactList();
			for (String username : usernameList) {
				localUsers.remove(username);
				userDao.deleteContact(username);
				inviteMessgeDao.deleteMessage(username);
			}
			runOnUiThread(new Runnable() {
				public void run() {
					// 如果正在与此用户的聊天页面
					if (ChatActivity.activityInstance != null
							&& usernameList
									.contains(ChatActivity.activityInstance
											.getToChatUsername())) {
						Toast.makeText(
								MainActivity.this,
								ChatActivity.activityInstance
										.getToChatUsername() + "此用户已被管理员移除", 1)
								.show();
						ChatActivity.activityInstance.finish();
					}
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 0 && chatHomeFragment != null
							&& chatHomeFragment.contactsFragment != null)
						chatHomeFragment.contactsFragment.refresh();
					else if (currentTabIndex == 0 && chatHomeFragment != null
							&& chatHomeFragment.chatAllHistoryFragment != null)
						chatHomeFragment.chatAllHistoryFragment.refresh();
				}
			});

		}

		@Override
		public void onContactInvited(String username, String reason) {
		}

		@Override
		public void onContactAgreed(String username) {
		}

		@Override
		public void onContactRefused(String username) {
		}
	}

	/** 连接监听listener */
	private class MyConnectionListener implements EMConnectionListener {

		@Override
		public void onConnected() {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (chatHomeFragment.chatAllHistoryFragment != null
							&& chatHomeFragment.chatAllHistoryFragment.errorItem != null)
						chatHomeFragment.chatAllHistoryFragment.errorItem
								.setVisibility(View.GONE);
				}

			});
		}

		@Override
		public void onDisconnected(final int error) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (error == EMError.CONNECTION_CONFLICT) {
						// 显示帐号在其他设备登陆dialog
						showConflictDialog();
					} else {
						chatHomeFragment.chatAllHistoryFragment.errorItem
								.setVisibility(View.VISIBLE);
						if (NetUtils.hasNetwork(MainActivity.this))
							chatHomeFragment.chatAllHistoryFragment.errorText
									.setText("连接不到聊天服务器");
						else
							chatHomeFragment.chatAllHistoryFragment.errorText
									.setText("当前网络不可用，请检查网络设置");
					}
				}

			});
		}
	}

	private class MyGroupChangeListener implements GroupChangeListener {

		@Override
		public void onInvitationReceived(String groupId, String groupName,
				String inviter, String reason) {
			boolean hasGroup = false;
			for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
				if (group.getGroupId().equals(groupId)) {
					hasGroup = true;
					break;
				}
			}
			if (!hasGroup)
				return;

			// 被邀请
			EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
			msg.setChatType(ChatType.GroupChat);
			msg.setFrom(inviter);
			msg.setTo(groupId);
			msg.setMsgId(UUID.randomUUID().toString());
			msg.addBody(new TextMessageBody(inviter + "邀请你加入了群聊"));
			// 保存邀请消息
			EMChatManager.getInstance().saveMessage(msg);
			// 提醒新消息
			EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();

			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 0)
						chatHomeFragment.chatAllHistoryFragment.refresh();
					// if (CommonUtils.getTopActivity(MainActivity.this).equals(
					// GroupsActivity.class.getName())) {
					// GroupsActivity.instance.onResume();
					// }
				}
			});

		}

		@Override
		public void onInvitationAccpted(String groupId, String inviter,
				String reason) {

		}

		@Override
		public void onInvitationDeclined(String groupId, String invitee,
				String reason) {

		}

		@Override
		public void onUserRemoved(String groupId, String groupName) {
			// 提示用户被T了，这里省略此步骤
			// 刷新ui
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						updateUnreadLabel();
						if (currentTabIndex == 0)
							chatHomeFragment.chatAllHistoryFragment.refresh();
						// if
						// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
						// {
						// GroupsActivity.instance.onResume();
						// }
					} catch (Exception e) {
					}

				}
			});
		}

		@Override
		public void onGroupDestroy(String groupId, String groupName) {
			// 群被解散
			// 提示用户群被解散,这里省略
			// 刷新ui
			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					if (currentTabIndex == 0)
						chatHomeFragment.chatAllHistoryFragment.refresh();
					// if
					// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
					// {
					// GroupsActivity.instance.onResume();
					// }
				}
			});

		}

		@Override
		public void onApplicationReceived(String groupId, String groupName,
				String applyer, String reason) {
			// 用户申请加入群聊
			InviteMessage msg = new InviteMessage();
			msg.setFrom(applyer);
			msg.setTime(System.currentTimeMillis());
			msg.setGroupId(groupId);
			msg.setGroupName(groupName);
			msg.setReason(reason);
			msg.setStatus(InviteMesageStatus.BEAPPLYED);
			notifyNewIviteMessage(msg);
		}

		@Override
		public void onApplicationAccept(String groupId, String groupName,
				String accepter) {
			// 加群申请被同意
			EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
			msg.setChatType(ChatType.GroupChat);
			msg.setFrom(accepter);
			msg.setTo(groupId);
			msg.setMsgId(UUID.randomUUID().toString());
			msg.addBody(new TextMessageBody(accepter + "同意了你的群聊申请"));
			// 保存同意消息
			EMChatManager.getInstance().saveMessage(msg);
			// 提醒新消息
			EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();

			runOnUiThread(new Runnable() {
				public void run() {
					updateUnreadLabel();
					// 刷新ui
					if (currentTabIndex == 0)
						chatHomeFragment.chatAllHistoryFragment.refresh();
					// if
					// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
					// {
					// GroupsActivity.instance.onResume();
					// }
				}
			});
		}

		@Override
		public void onApplicationDeclined(String groupId, String groupName,
				String decliner, String reason) {
			// 加群申请被拒绝，这里未实现
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isConflict) {
			updateUnreadLabel();
			updateUnread_private_zone_number();
			EMChatManager.getInstance().activityResumed();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isConflict", isConflict);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 保存提示新消息 */
	private void notifyNewIviteMessage(InviteMessage msg) {
	}

	/**
	 * set head
	 * 
	 * @param username
	 * @return
	 */
	User setUserHead(String username) {
		User user = new User();
		user.setUsername(username);
		String headerName = null;
		if (!TextUtils.isEmpty(user.getNick())) {
			headerName = user.getNick();
		} else {
			headerName = user.getUsername();
		}
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
			user.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			user.setHeader("#");
		} else {
			user.setHeader(HanziToPinyin.getInstance()
					.get(headerName.substring(0, 1)).get(0).target.substring(0,
					1).toUpperCase());
			char header = user.getHeader().toLowerCase().charAt(0);
			if (header < 'a' || header > 'z') {
				user.setHeader("#");
			}
		}
		return user;
	}

	/** 屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 注销广播接收者
		try {
			unregisterReceiver(msgReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(ackMessageReceiver);
		} catch (Exception e) {
		}
		if (conflictBuilder != null) {
			conflictBuilder.create().dismiss();
			conflictBuilder = null;
		}
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		int count = getUnreadMsgCountTotal();
		if (count > 0) {
			unread_msg_number.setText(String.valueOf(count));
			unread_msg_number.setVisibility(View.VISIBLE);
		} else {
			unread_msg_number.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 刷新申请与通知消息数
	 */
	public void updateUnread_private_zone_number() {
		runOnUiThread(new Runnable() {
			public void run() {
				int count = getUnreadAddressCountTotal();
				if (count > 0) {
					unread_private_zone_number.setText(String.valueOf(count));
					unread_private_zone_number.setVisibility(View.VISIBLE);
				} else {
					unread_private_zone_number.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	/**
	 * 获取未读申请与通知消息
	 * 
	 * @return
	 */
	public int getUnreadAddressCountTotal() {
		int unreadAddressCountTotal = 0;
		if (MyApplication.getInstance().getContactList()
				.get(Constant.NEW_FRIENDS_USERNAME) != null)
			unreadAddressCountTotal = MyApplication.getInstance()
					.getContactList().get(Constant.NEW_FRIENDS_USERNAME)
					.getUnreadMsgCount();
		return unreadAddressCountTotal;
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		return unreadMsgCountTotal;
	}

	/**
	 * 新消息广播接收者
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
			String from = intent.getStringExtra("from");
			// 消息id
			String msgId = intent.getStringExtra("msgid");
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			if (ChatActivity.activityInstance != null) {
				if (message.getChatType() == ChatType.GroupChat) {
					if (message.getTo().equals(
							ChatActivity.activityInstance.getToChatUsername()))
						return;
				} else {
					if (from.equals(ChatActivity.activityInstance
							.getToChatUsername()))
						return;
				}
			}

			// 注销广播接收者，否则在ChatActivity中会收到这个广播
			abortBroadcast();

			notifyNewMessage(message);

			// 刷新bottom bar消息未读数
			updateUnreadLabel();
			if (currentTabIndex == 0) {
				// 当前页面如果为聊天历史页面，刷新此页面
				if (chatHomeFragment.chatAllHistoryFragment != null) {
					chatHomeFragment.chatAllHistoryFragment.refresh();
				}
			}
		}
	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();

			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");

			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);

				if (msg != null) {

					if (ChatActivity.activityInstance != null) {
						if (msg.getChatType() == ChatType.Chat) {
							if (from.equals(ChatActivity.activityInstance
									.getToChatUsername()))
								return;
						}
					}

					msg.isAcked = true;
				}
			}

		}
	};

	/**
	 * 透传消息BroadcastReceiver
	 */
	private BroadcastReceiver cmdMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();
			// 获取cmd message对象
			String msgId = intent.getStringExtra("msgid");
			EMMessage message = intent.getParcelableExtra("message");
			// 获取消息body
			CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
			String action = cmdMsgBody.action;// 获取自定义action

			// 获取扩展属性 此处省略
			// message.getStringAttribute("");
			Toast.makeText(MainActivity.this, "收到透传：action：" + action,
					Toast.LENGTH_SHORT).show();
		}
	};

	/**
	 * 显示帐号在别处登录dialog
	 */
	private void showConflictDialog() {
		isConflictDialogShow = true;
		MyApplication.getInstance().logout(null);

		if (!MainActivity.this.isFinishing()) {
			// clear up global variables
			try {
				if (conflictBuilder == null)
					conflictBuilder = new android.app.AlertDialog.Builder(
							MainActivity.this);
				conflictBuilder.setTitle("下线通知");
				conflictBuilder.setMessage(R.string.connect_conflict);
				conflictBuilder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								conflictBuilder = null;
								finish();
								startActivity(new Intent(MainActivity.this,
										GestureUnlockActivity.class));
							}
						});
				conflictBuilder.setCancelable(false);
				conflictBuilder.create().show();
				isConflict = true;
			} catch (Exception e) {
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (getIntent().getBooleanExtra("conflict", false)
				&& !isConflictDialogShow)
			showConflictDialog();
	}
}
