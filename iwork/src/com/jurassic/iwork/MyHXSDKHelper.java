package com.jurassic.iwork;

import java.util.Map;

import android.content.Intent;

import com.easemob.EMCallBack;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.jurassic.applib.controller.HXSDKHelper;
import com.jurassic.applib.model.HXSDKModel;
import com.jurassic.iwork.activity.MainActivity;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.utils.CommonUtils;

/**
 * My UI HX SDK helper class which subclass HXSDKHelper
 * 
 * @author HXL
 */
public class MyHXSDKHelper extends HXSDKHelper {

	/**
	 * contact list in cache
	 */
	private Map<String, User> contactList;

	@Override
	protected void initHXOptions() {
		super.initHXOptions();
		// you can also get EMChatOptions to set related SDK options
		// EMChatOptions options = EMChatManager.getInstance().getChatOptions();
	}

	@Override
	protected OnMessageNotifyListener getMessageNotifyListener() {
		// 取消注释，app在后台，有新消息来时，状态栏的消息提示换成自己写的
		return new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				// 设置状态栏的消息提示，可以根据message的类型做相应提示
	              String ticker = CommonUtils.getMessageDigest(message, appContext);
	              if(message.getType() == Type.TXT)
	                  ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
	              return message.getFrom() + ": " + ticker;
			}

			@Override
			public String onSetNotificationTitle(EMMessage arg0) {
				return null;
			}

			@Override
			public int onSetSmallIcon(EMMessage arg0) {
				return 0;
			}

			@Override
			public String onLatestMessageNotify(EMMessage arg0, int arg1,
					int arg2) {
				return null;
			}
		};
	}

	@Override
	protected OnNotificationClickListener getNotificationClickListener() {
		return new OnNotificationClickListener() {

			@Override
			public Intent onNotificationClick(EMMessage message) {
				return null;
			}
		};
	}

	@Override
	protected void onConnectionConflict() {
		Intent intent = new Intent(appContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("conflict", true);
		appContext.startActivity(intent);
	}

	@Override
	protected void initListener() {
		super.initListener();
	}

	@Override
	protected HXSDKModel createModel() {
		return new MyHXSDKModel(appContext);
	}

	/**
	 * get demo HX SDK Model
	 */
	public MyHXSDKModel getModel() {
		return (MyHXSDKModel) hxModel;
	}

	/**
	 * 获取内存中好友user list
	 * 
	 * @return
	 */
	public Map<String, User> getContactList() {
		if (getHXId() != null && contactList == null) {
			contactList = ((MyHXSDKModel) getModel()).getContactList();
		}

		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 * 
	 * @param contactList
	 */
	public void setContactList(Map<String, User> contactList) {
		this.contactList = contactList;
	}

	@Override
	public void logout(final EMCallBack callback) {
		super.logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				setContactList(null);
				getModel().closeDB();
				if (callback != null) {
					callback.onSuccess();
				}
			}

			@Override
			public void onError(int code, String message) {

			}

			@Override
			public void onProgress(int progress, String status) {
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

		});
	}
}
