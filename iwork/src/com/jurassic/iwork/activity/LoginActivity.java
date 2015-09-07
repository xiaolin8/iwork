package com.jurassic.iwork.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.MyHXSDKHelper;
import com.jurassic.iwork.R;
import com.jurassic.iwork.utils.CommonUtils;

public class LoginActivity extends BaseActivity {
	private EditText userAccountEt;
	private EditText userPwdEt;
	private String currentAccount;
	private String currentPassword;
	private boolean progressShow;
	private boolean autoLogin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果用户名密码都有，直接进入主页面
		if (MyHXSDKHelper.getInstance().isLogined()) {
			autoLogin = true;
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			return;
		}
		setContentView(R.layout.activity_login);
		userAccountEt = (EditText) findViewById(R.id.userAccountEt);
		userPwdEt = (EditText) findViewById(R.id.userPwdEt);
		if (MyApplication.getInstance().getUserName() != null) {
			userAccountEt.setText(MyApplication.getInstance().getUserName());
		}
	}

	/** 点击登录 */
	public void login(View view) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available,
					Toast.LENGTH_SHORT).show();
			return;
		}
		currentAccount = userAccountEt.getText().toString().trim();
		currentPassword = userPwdEt.getText().toString().trim();
		if (TextUtils.isEmpty(currentAccount)) {
			Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		progressShow = true;
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage("正在登陆...");
		pd.show();
		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(currentAccount, currentPassword,
				new EMCallBack() {

					@Override
					public void onSuccess() {
						if (!progressShow) {
							return;
						}
						// 登陆成功，保存用户名密码
						MyApplication.getInstance().setUserName(currentAccount);
						MyApplication.getInstance()
								.setPassword(currentPassword);
						runOnUiThread(new Runnable() {
							public void run() {
								pd.setMessage("正在获取好友和群聊列表...");
							}
						});
						try {
							// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
							// ** manually load all local groups and
							// conversations in case we are auto login
							EMGroupManager.getInstance().loadAllGroups();
							EMChatManager.getInstance().loadAllConversations();

							// 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
							EMGroupManager.getInstance().getGroupsFromServer();
						} catch (Exception e) {
							e.printStackTrace();
							// 取好友或者群聊失败，不让进入主页面，也可以不管这个exception继续进到主页面
							runOnUiThread(new Runnable() {
								public void run() {
									pd.dismiss();
									MyApplication.getInstance().logout(null);
									Toast.makeText(getApplicationContext(),
											"登录失败: 获取好友或群聊失败", 1).show();
								}
							});
							return;
						}
						if (!LoginActivity.this.isFinishing())
							pd.dismiss();
						// 进入主页面
						 startActivity(new Intent(LoginActivity.this,
						 MainActivity.class));
						 finish();
					}

					@Override
					public void onError(final int code, final String message) {
						if (!progressShow) {
							return;
						}
						runOnUiThread(new Runnable() {
							public void run() {
								pd.dismiss();
								Toast.makeText(getApplicationContext(),
										"登录失败: " + message, Toast.LENGTH_SHORT)
										.show();
							}
						});
					}

					@Override
					public void onProgress(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
	}

	/** 点击登录设置 */
	public void loginSet(View view) {
		// 进入登录设置页面
		startActivity(new Intent(LoginActivity.this,
				ServerSettingActivity.class));
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (autoLogin) {
			return;
		}
	}
}
