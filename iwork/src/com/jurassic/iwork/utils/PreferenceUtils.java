package com.jurassic.iwork.utils;

import android.content.Context;

import com.jurassic.applib.utils.HXPreferenceUtils;

/**
 * 
 * @deprecated this class is deprecated, please use {@link HXPreferenceUtils}
 * 
 */
public class PreferenceUtils {

	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static PreferenceUtils mPreferenceUtils;

	private PreferenceUtils() {
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public synchronized static PreferenceUtils getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new PreferenceUtils();
			HXPreferenceUtils.init(cxt);
		}

		return mPreferenceUtils;
	}

	public void setSettingMsgNotification(boolean paramBoolean) {
		HXPreferenceUtils.getInstance().setSettingMsgNotification(paramBoolean);
	}

	public boolean getSettingMsgNotification() {
		return HXPreferenceUtils.getInstance().getSettingMsgNotification();
	}

	public void setSettingMsgSound(boolean paramBoolean) {
		HXPreferenceUtils.getInstance().setSettingMsgSound(paramBoolean);
	}

	public boolean getSettingMsgSound() {
		return HXPreferenceUtils.getInstance().getSettingMsgSound();
	}

	public void setSettingMsgVibrate(boolean paramBoolean) {
		HXPreferenceUtils.getInstance().setSettingMsgVibrate(paramBoolean);
	}

	public boolean getSettingMsgVibrate() {
		return HXPreferenceUtils.getInstance().getSettingMsgVibrate();
	}

	public void setSettingMsgSpeaker(boolean paramBoolean) {
		HXPreferenceUtils.getInstance().setSettingMsgSpeaker(paramBoolean);
	}

	public boolean getSettingMsgSpeaker() {
		return HXPreferenceUtils.getInstance().getSettingMsgSpeaker();
	}
}
