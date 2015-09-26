package com.jurassic.iwork.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.util.HanziToPinyin;
import com.jurassic.applib.controller.HXSDKHelper;
import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.db.UserDao;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.myview.LockPatternUtils;
import com.jurassic.iwork.myview.LockPatternView;
import com.jurassic.iwork.myview.LockPatternView.Cell;
import com.jurassic.iwork.utils.CommonUtils;

public class GestureUnlockActivity extends BaseActivity {
	private LockPatternView mLockPatternView;
	private boolean progressShow;
	private CountDownTimer mCountdownTimer = null;
	private EditText username;
	private TextView mHeadTextView;
	private Animation mShakeAnim;// 提醒文字的动态效果
	private Handler mHandler = new Handler();
	private int mFailedPatternAttemptsSinceLastTimeout = 0;// 在距离上次超时之前我已经错误了几次
	public static final int FAILED_ATTEMPTS_BEFORE_TIMEOUT = 5;// 最多可以错误输入多少次
	private SharedPreferences mySharedPreferences;
	private boolean isOffLine;// 当前网络状态是否是离线

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null)
			mCountdownTimer.cancel();
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mLockPatternView.clearPattern();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_login);
		username = (EditText) findViewById(R.id.username);
		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
		// 实例化SharedPreferences对象（第一步）
		mySharedPreferences = getSharedPreferences("Account",
				Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String name = mySharedPreferences.getString("username", "");
		username.setText(name);
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
		// 如果是离线状态，那么直接验证存储在preference里的用户名和密码就行了
		isOffLine = !CommonUtils.isNetWorkConnected(this);
	}

	protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

		public void onPatternStart() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternCleared() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternDetected(List<LockPatternView.Cell> pattern) {
			if (pattern == null)
				return;
			final String strname = username.getText().toString();// 获取到文本框里的用户名
			final byte[] hashPwd = null;// 经过自定义加密后的密码
			if (strname.isEmpty()) {
				Toast.makeText(getApplicationContext(), "登录失败: 请输入用户名！",
						Toast.LENGTH_SHORT).show();
				mLockPatternView.clearPattern();
				return;
			}
			if (pattern.size() < LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
				Toast.makeText(getApplicationContext(), "输入长度不够，请重试",
						Toast.LENGTH_SHORT).show();
				return;
			}
			progressShow = true;
			final ProgressDialog pd = new ProgressDialog(
					GestureUnlockActivity.this);
			pd.setCanceledOnTouchOutside(false);
			pd.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					progressShow = false;
				}
			});
			pd.setMessage("正在登陆...");
			pd.show();
			// 根据是否是离线登录，采用不同的登录方法
			if (!isOffLine) {
				// 调用sdk登陆方法登陆聊天服务器
				EMChatManager.getInstance().login(strname, "123456",
						new EMCallBack() {
							@Override
							public void onSuccess() {
								if (!progressShow) {
									return;
								}
								// 登陆成功，保存用户名密码
								MyApplication.getInstance()
										.setUserName(strname);
								MyApplication.getInstance().setPassword(
										"hashPwd");
								// 实例化SharedPreferences.Editor对象（第二步）
								Editor editor = mySharedPreferences.edit();
								// 用putString的方法保存数据
								editor.putString("username", strname);
								// 提交当前数据
								editor.commit();
								runOnUiThread(new Runnable() {
									public void run() {
										pd.setMessage("正在获取好友和讨论组列表...");
									}
								});
								try {
									// ** 第一次登录或者之前logout后，加载所有本地群和会话
									EMGroupManager.getInstance()
											.loadAllGroups();
									EMChatManager.getInstance()
											.loadAllConversations();
									Map<String, User> userlist = new HashMap<String, User>();
									// 检查本地数据库是否已存在好友列表
									String DB_NAME = "/data"
											+ Environment.getDataDirectory()
													.getAbsolutePath()
											+ "/com.jurassic.iwork/databases/"
											+ HXSDKHelper.getInstance()
													.getHXId() + ".db";
									if (new File(DB_NAME).exists()) {
										// 加载数据库中的uers表到userlist中
										// 存入db
										UserDao dao = new UserDao(
												GestureUnlockActivity.this
														.getApplicationContext());
										userlist = dao.getContactList();
										// 存入内存
										MyApplication.getInstance()
												.setContactList(userlist);
									} else {
										List<String> usernames = EMContactManager
												.getInstance()
												.getContactUserNames();
										for (String username : usernames) {
											User user = new User();
											user.setUsername(username);
											String nickName = "";
											// 获取这个username对应的nickName
											URL url = new URL(
													MyApplication
															.getInstance()
															.getString(
																	R.string.server_url)
															+ "/user/getNickById?UserId="
															+ username);
											HttpURLConnection conn = (HttpURLConnection) url
													.openConnection();
											conn.setRequestMethod("GET");
											conn.setConnectTimeout(5000);
											int code = conn.getResponseCode();
											if (code == 200) {
												InputStream stream = conn
														.getInputStream();
												// 把输入流的内容转换为字符串
												try {
													ByteArrayOutputStream baos = new ByteArrayOutputStream();
													int len = 0;
													byte[] buffer = new byte[1024];
													while ((len = stream
															.read(buffer)) != -1) {
														baos.write(buffer, 0,
																len);
													}
													baos.close();
													stream.close();
													nickName = new String(baos
															.toByteArray());
												} catch (Exception e) {
													e.printStackTrace();
												}
											}

											if (!nickName.equals(""))
												user.setNick(nickName);
											setUserHearder(username, user);
											userlist.put(username, user);
										}
										// 添加user"申请与通知"
										User newFriends = new User();
										newFriends
												.setUsername(Constant.NEW_FRIENDS_USERNAME);
										newFriends.setNick("申请与通知");
										newFriends.setHeader("");
										userlist.put(
												Constant.NEW_FRIENDS_USERNAME,
												newFriends);
										// 添加"讨论组"
										User groupUser = new User();
										groupUser
												.setUsername(Constant.GROUP_USERNAME);
										groupUser.setNick("联系组");
										groupUser.setHeader("");
										userlist.put(Constant.GROUP_USERNAME,
												groupUser);

										// 存入内存
										MyApplication.getInstance()
												.setContactList(userlist);
										// 存入db
										UserDao dao = new UserDao(
												GestureUnlockActivity.this
														.getApplicationContext());
										List<User> users = new ArrayList<User>(
												userlist.values());
										dao.saveContactList(users);
									}
									// 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
									EMGroupManager.getInstance()
											.getGroupsFromServer();
									if (!GestureUnlockActivity.this
											.isFinishing())
										pd.dismiss();
									// 进入主页面
									startActivity(new Intent(
											GestureUnlockActivity.this,
											MainActivity.class));
									finish();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onError(final int code,
									final String message) {
								if (!progressShow) {
									return;
								}
								runOnUiThread(new Runnable() {

									public void run() {
										pd.dismiss();
										Toast.makeText(getApplicationContext(),
												"登录失败: " + message,
												Toast.LENGTH_SHORT).show();
										if (!isOffLine) {
											pwdErrorHandler();
										}
									}
								});
							}

							@Override
							public void onProgress(int arg0, String arg1) {
							}
						});
			} else {
				// 如果采用离线登录方式，那么。。。
				// 进入主页面
				startActivity(new Intent(GestureUnlockActivity.this,
						MainActivity.class));
				finish();
			}
		}

		private void pwdErrorHandler() {
			mFailedPatternAttemptsSinceLastTimeout++;
			int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
					- mFailedPatternAttemptsSinceLastTimeout;
			if (retry > 0) {
				mHeadTextView.setText("用户名或密码错误，还有" + retry + "次机会");
				mHeadTextView.setTextColor(Color.RED);
				mHeadTextView.startAnimation(mShakeAnim);
			} else if (retry == 0) {
				mHeadTextView.setText("");
				mLockPatternView.setEnabled(false);
				Toast.makeText(getApplicationContext(), "您已5次输错用户名或密码，请30秒后再试",
						Toast.LENGTH_SHORT).show();
			}
			new Handler().postDelayed(new Runnable() {
				public void run() {
					mLockPatternView.clearPattern();// 在1秒后将红色轨迹删除
				}
			}, 500);
			if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
				mHandler.postDelayed(attemptLockout, 2000);
			}
		}

		@Override
		public void onPatternCellAdded(List<Cell> pattern) {
		}
	};

	Runnable attemptLockout = new Runnable() {

		@Override
		public void run() {
			mLockPatternView.clearPattern();
			mLockPatternView.setEnabled(false);
			mCountdownTimer = new CountDownTimer(
					LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
					// 最好能把还剩多少秒存起来，这样就让他钻不了退出程序就不用等30秒的空子
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putInt("secondsRemaining", secondsRemaining);
					// 提交当前数据
					editor.commit();
					if (secondsRemaining > 0) {
						mHeadTextView.setText(secondsRemaining + " 秒后重试");
					} else {
						mHeadTextView.setText("请绘制手势密码");
						mHeadTextView.setTextColor(Color.BLACK);
					}
				}

				@Override
				public void onFinish() {
					mLockPatternView.setEnabled(true);
					mFailedPatternAttemptsSinceLastTimeout = 0;
				}
			}.start();
		}
	};

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 */
	protected void setUserHearder(String username, User user) {
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
	}

	public void btn_unlock_forget_click(View view) {
		Intent intent = new Intent(GestureUnlockActivity.this,
				UnlockForgetActivity.class);
		// 打开新的Activity
		startActivity(intent);
		finish();
	}
}
