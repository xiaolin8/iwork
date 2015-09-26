package com.jurassic.iwork.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;

import com.alibaba.fastjson.JSON;
import com.easemob.util.HanziToPinyin;
import com.jurassic.applib.controller.HXSDKHelper;
import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.db.UserDao;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.utils.StringUtil;

public class SplashActivity extends BaseActivity {

	private static final int sleepTime = 800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(2000);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// 开启异步任务加载uers表数据
		new AsyncTask<Void, Void, String>() {

			private long start;

			@Override
			protected String doInBackground(Void... params) {
				start = System.currentTimeMillis();
				// 检查本地数据库是否已存在好友列表
				String DB_NAME = "/data"
						+ Environment.getDataDirectory().getAbsolutePath()
						+ "/com.jurassic.iwork/databases/iwork.db";
				if (new File(DB_NAME).exists()) {
					long costTime = System.currentTimeMillis() - start;
					// 等待sleeptime时长
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 进入登录页面
					startActivity(new Intent(SplashActivity.this,
							LoginActivity.class));
					finish();
					return null;
				}
				String jsonString = null;
				URL url;
				try {
					url = new URL(MyApplication.getInstance().getString(
							R.string.server_url)
							+ "/user/GetAllUsers");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream stream = conn.getInputStream();
						// 把输入流的内容转换为字符串
						try {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							int len = 0;
							byte[] buffer = new byte[102400];
							while ((len = stream.read(buffer)) != -1) {
								baos.write(buffer, 0, len);
							}
							baos.close();
							stream.close();
							jsonString = new String(baos.toByteArray());
							return jsonString;

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return jsonString;
			};

			// 在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用
			@Override
			protected void onPostExecute(String result) {
				if (!StringUtil.isEmpty(result)) {
					// JSON串转用户对象列表
					List<User> users = JSON.parseArray(result, User.class);
					Map<String, User> userlist = new HashMap<String, User>();
					for (User user : users) {
						setUserHearder(user.getUserId(), user);
						user.setNick(user.getUserName());
						user.setUsername(user.getUserId());
						userlist.put(user.getUserId(), user);
					}
					// 添加user"申请与通知"
					User newFriends = new User();
					newFriends.setUserId(Constant.NEW_FRIENDS_USERNAME);
					newFriends.setUserName("申请与通知");
					newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
					newFriends.setNick("申请与通知");
					newFriends.setHeader("");
					users.add(newFriends);
					userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
					// 添加"讨论组"
					User groupUser = new User();
					groupUser.setUserId(Constant.GROUP_USERNAME);
					groupUser.setUserName("联系组");
					groupUser.setUsername(Constant.GROUP_USERNAME);
					groupUser.setNick("联系组");
					groupUser.setHeader("");
					users.add(groupUser);
					userlist.put(Constant.GROUP_USERNAME, groupUser);
					// 系统管理员

					// 存入内存
					MyApplication.getInstance().setContactList(userlist);
					// 存入db
					UserDao dao = new UserDao(
							SplashActivity.this.getApplicationContext());
					dao.saveContactList(users);
					long costTime = System.currentTimeMillis() - start;
					// 等待sleeptime时长
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 进入登录页面
					startActivity(new Intent(SplashActivity.this,
							LoginActivity.class));
					finish();
				}
			}

		}.execute(new Void[] {});

	}

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param userid
	 * @param user
	 */
	protected void setUserHearder(String userid, User user) {
		if (userid.equals(Constant.NEW_FRIENDS_USERNAME)
				|| userid.equals(Constant.GROUP_USERNAME)) {
			user.setHeader("");
		} else if (Character.isDigit(userid.charAt(0))) {
			user.setHeader("#");
		} else {
			user.setHeader(userid.substring(0, 1).toUpperCase());
		}
	}

}