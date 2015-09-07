package com.jurassic.applib.model;

/**
 * UI Demo HX Model implementation
 */

import com.jurassic.applib.utils.HXPreferenceUtils;
import com.jurassic.iwork.db.UserDao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * HuanXin default SDK Model implementation
 * @author easemob
 *
 */
public class DefaultHXSDKModel extends HXSDKModel{
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PWD = "pwd";
    UserDao dao = null;
    protected Context context = null;
    
    public DefaultHXSDKModel(Context ctx){
        context = ctx;
        HXPreferenceUtils.init(context);
    }
    
    @Override
    public void setSettingMsgNotification(boolean paramBoolean) {
        HXPreferenceUtils.getInstance().setSettingMsgNotification(paramBoolean);
    }

    @Override
    public boolean getSettingMsgNotification() {
        return HXPreferenceUtils.getInstance().getSettingMsgNotification();
    }

    @Override
    public void setSettingMsgSound(boolean paramBoolean) {
        HXPreferenceUtils.getInstance().setSettingMsgSound(paramBoolean);
    }

    @Override
    public boolean getSettingMsgSound() {
        return HXPreferenceUtils.getInstance().getSettingMsgSound();
    }

    @Override
    public void setSettingMsgVibrate(boolean paramBoolean) {
        HXPreferenceUtils.getInstance().setSettingMsgVibrate(paramBoolean);
    }

    @Override
    public boolean getSettingMsgVibrate() {
        return HXPreferenceUtils.getInstance().getSettingMsgVibrate();
    }

    @Override
    public void setSettingMsgSpeaker(boolean paramBoolean) {
        HXPreferenceUtils.getInstance().setSettingMsgSpeaker(paramBoolean);
    }

    @Override
    public boolean getSettingMsgSpeaker() {
        return HXPreferenceUtils.getInstance().getSettingMsgSpeaker();
    }

    /*
     * 获取好友花名册
     * @see com.easemob.applib.model.HXSDKModel#getUseHXRoster()
     */
    @Override
    public boolean getUseHXRoster() {
        return false;
    }

    @Override
    public boolean saveHXId(String hxId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit().putString(PREF_USERNAME, hxId).commit();
    }

    @Override
    public String getHXId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_USERNAME, null);
    }

    @Override
    public boolean savePassword(String pwd) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit().putString(PREF_PWD, pwd).commit();    
    }

    @Override
    public String getPwd() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_PWD, null);
    }
}
