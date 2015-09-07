package com.jurassic.iwork.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.utils.StringUtil;

public class UserDetailsActivity extends Activity {

	private TextView per_info_name;
	private ImageView per_info_photo;
	private ImageView per_info_sex;
	private TextView per_info_account;
	private TextView per_info_feed;// XXX的同事圈
	private TextView per_info_department;
	private TextView per_info_office;// 办公电话
	private Button per_info_btnMakeMobileCall;// 拨打手机
	private Button per_info_btnSendMessage;// 发送短消息
	private Button per_info_btnMakeOfficeCall;// 拨打办公电话
	private TextView per_info_mobile;// 手机号码
	private TextView per_info_phone;// 电话号码
	private TextView per_info_position;// 职务
	private TextView per_info_mark;// 个性签名
	private Button per_info_to_voip_chat;// 即时通话
	private Button person_info_bq_link;// 传送文件/照片到电脑
	private String Username;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		initView();
		setUpView();
	}

	private void initView() {
		per_info_name = (TextView) findViewById(R.id.per_info_name);
		per_info_photo = (ImageView) findViewById(R.id.per_info_photo);
		per_info_sex = (ImageView) findViewById(R.id.per_info_sex);
		per_info_feed = (TextView) findViewById(R.id.per_info_feed);
		per_info_btnMakeMobileCall = (Button) findViewById(R.id.per_info_btnMakeMobileCall);
		per_info_btnSendMessage = (Button) findViewById(R.id.per_info_btnSendMessage);
		per_info_department = (TextView) findViewById(R.id.per_info_department);
		per_info_btnMakeOfficeCall = (Button) findViewById(R.id.per_info_btnMakeOfficeCall);
		per_info_mobile = (TextView) findViewById(R.id.per_info_mobile);
		per_info_office = (TextView) findViewById(R.id.per_info_office);
		per_info_mark = (TextView) findViewById(R.id.per_info_mark);
		// 拨打手机
		per_info_btnMakeMobileCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel://" + per_info_mobile.getText()));
				startActivity(intent);
			}
		});
		// 拨打办公电话
		per_info_btnMakeOfficeCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel://" + per_info_office.getText()));
				startActivity(intent);
			}
		});
		// 发送短信
		per_info_btnSendMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("smsto:" + per_info_mobile.getText());
				Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(sendIntent);
			}
		});
	}

	private void setUpView() {
		Username = getIntent().getStringExtra("userId");
		if (StringUtil.isNotEmpty(Username)) {
			// 获取本地好友列表(一定不要在内存中获取，而要在数据库中获取)
			Map<String, User> users = MyApplication.getInstance()
					.getContactList();
			Iterator<Entry<String, User>> iterator = users.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, User> entry = iterator.next();
				if (entry.getKey().equals(Username))
					user = entry.getValue();
			}
			String UserName = user.getUserName();
			String EnglishName = user.getEnglishName();
			String DeptId = user.getDeptId();
			Integer headpic = user.getHeadPic();
			Integer Gender = user.getGender();
			String Signature = user.getSignature();
			String OfficePhone = user.getOfficePhone();
			String Email = user.getEmail();
			String CellPhone = user.getCellPhone();
			String Position = user.getPosition();
			int[] headIds = { R.drawable.head1, R.drawable.head2,
					R.drawable.head3, R.drawable.head4, R.drawable.head5,
					R.drawable.head6, R.drawable.head7, R.drawable.head8,
					R.drawable.head9, R.drawable.head10, R.drawable.head11,
					R.drawable.head12, R.drawable.head13, R.drawable.head14,
					R.drawable.head15, R.drawable.head16, R.drawable.head17,
					R.drawable.head18, R.drawable.head19, R.drawable.head20,
					R.drawable.head21, R.drawable.head22, R.drawable.head23,
					R.drawable.head24, R.drawable.head25, R.drawable.head26,
					R.drawable.head27, R.drawable.head28, R.drawable.head29,
					R.drawable.head30, R.drawable.head31, R.drawable.head32,
					R.drawable.head33, R.drawable.head34, R.drawable.head35,
					R.drawable.head36, R.drawable.head37, R.drawable.head38,
					R.drawable.head39, R.drawable.head40, R.drawable.head41,
					R.drawable.head42, R.drawable.head43, R.drawable.head44,
					R.drawable.head45, R.drawable.head46, R.drawable.head47,
					R.drawable.head48, R.drawable.head49, R.drawable.head50,
					R.drawable.head51, R.drawable.head52, R.drawable.head53,
					R.drawable.head54, R.drawable.head55, R.drawable.head56,
					R.drawable.head57, R.drawable.head58, R.drawable.head59,
					R.drawable.head60, R.drawable.head61, R.drawable.head62,
					R.drawable.head63, R.drawable.head64, R.drawable.head65,
					R.drawable.head66, R.drawable.head67, R.drawable.head68 };
			if (headpic > 0 && headpic < 10000)
				per_info_photo.setImageResource(headIds[headpic - 1]);
			else if (headpic > 1000) {
				Bitmap headimg = getImageFromAssetsFile(Username + ".png");
				per_info_photo.setImageBitmap(headimg);
			}
			String strName;
			if (StringUtil.isNotEmpty(EnglishName)) {
				strName = UserName + "(" + EnglishName + ")";
			} else
				strName = UserName;
			per_info_name.setText(strName);
			if (Gender == 0)
				per_info_sex.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_sex_woman));
			per_info_feed.setText(UserName + "的同事圈");
			if (StringUtil.isNotEmpty(CellPhone))
				per_info_mobile.setText(CellPhone);
			if (StringUtil.isNotEmpty(OfficePhone))
				per_info_office.setText(OfficePhone);
			if (StringUtil.isNotEmpty(Position))
				per_info_position.setText(Position);
			if (StringUtil.isNotEmpty(Signature))
				per_info_mark.setText(Signature);
			else
				per_info_mark.setText("");
			// 开启异步任务加载uers表数据
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// 访问网络，把部门信息download下来
					String deptString = null;
					URL url;
					try {
						url = new URL(MyApplication.getInstance().getString(
								R.string.server_url)
								+ "/user/GetDept?UserId=" + Username);
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
								deptString = new String(baos.toByteArray());
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
					if (StringUtil.isNotEmpty(deptString))
						per_info_department.setText(deptString);
					return null;
				}

			}.execute(new Void[] {});

		}

	}

	/**
	 * 从Assets中读取图片
	 */
	private Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap image = null;
		AssetManager am = MyApplication.applicationContext.getResources()
				.getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	/** 点击进入聊天 */
	public void toChat(View view) {
		startActivity(new Intent(this, ChatActivity.class).putExtra("nickName",
				user.getNick()).putExtra("userId", Username));
	}

	/**
	 * 返回
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

}
