package com.jurassic.iwork.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.activity.AboutActivity;
import com.jurassic.iwork.activity.DisplaySettingActivity;
import com.jurassic.iwork.activity.GeneralSettingActivity;
import com.jurassic.iwork.activity.LoginActivity;
import com.jurassic.iwork.activity.MessageNoticeActivity;
import com.jurassic.iwork.activity.MyProfileActivity;
import com.jurassic.iwork.activity.SecuritySettingActivity;
import com.jurassic.iwork.db.UserDao;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.utils.BitmapUtil;

public class SettingHomeFragment extends Fragment implements OnClickListener {

	private LinearLayout my_profile_layout;// 我的资料
	private ImageView my_profile_logo;
	private LinearLayout ui_setting_layout;// 界面显示
	private LinearLayout message_notice_layout;// 消息通知
	private LinearLayout general_setting_layout;// 通用设置
	private LinearLayout secure_and_private_layout;// 安全隐私
	private RelativeLayout about_layout;// 关于
	private User user;
	private Button btn_logout;
	private Integer headpic;
	private String userid;
	private String username;
	private String englishname;
	private String signature;
	private String cellphone;
	private String officephone;
	private String email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting_home, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		my_profile_layout = (LinearLayout) getView().findViewById(
				R.id.my_headpic_layout);
		my_profile_layout.setOnClickListener(this);
		ui_setting_layout = (LinearLayout) getView().findViewById(
				R.id.ui_setting_layout);
		ui_setting_layout.setOnClickListener(this);
		message_notice_layout = (LinearLayout) getView().findViewById(
				R.id.message_notice_layout);
		message_notice_layout.setOnClickListener(this);
		general_setting_layout = (LinearLayout) getView().findViewById(
				R.id.general_setting_layout);
		general_setting_layout.setOnClickListener(this);
		secure_and_private_layout = (LinearLayout) getView().findViewById(
				R.id.secure_and_private_layout);
		secure_and_private_layout.setOnClickListener(this);
		about_layout = (RelativeLayout) getView().findViewById(
				R.id.about_layout);
		about_layout.setOnClickListener(this);
		my_profile_logo = (ImageView) getView().findViewById(
				R.id.my_profile_logo);
		btn_logout = (Button) getView().findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
		userid = MyApplication.getInstance().getUserName();// 获取当前登录的userId
		UserDao userDao = new UserDao(getActivity());
		user = userDao.getContactById(userid);
		if (user != null) {
			headpic = user.getHeadPic();
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
				my_profile_logo.setImageResource(headIds[headpic - 1]);
			else if (headpic > 1000) {
				Bitmap headimg = BitmapUtil.getImageFromAssetsFile(userid
						+ ".png");
				my_profile_logo.setImageBitmap(headimg);
			}
			username = user.getUserName();
			englishname = user.getEnglishName();
			signature = user.getSignature();
			cellphone = user.getCellPhone();
			officephone = user.getOfficePhone();
			email = user.getEmail();
			if (!TextUtils
					.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
				btn_logout.setText(getString(R.string.button_logout) + "("
						+ username + ")");
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_headpic_layout:// 我的资料
			startActivity(new Intent(getActivity(), MyProfileActivity.class)
					.putExtra("userid", userid).putExtra("username", username)
					.putExtra("englishname", englishname)
					.putExtra("headpic", headpic)
					.putExtra("signature", signature)
					.putExtra("cellphone", cellphone)
					.putExtra("officephone", officephone)
					.putExtra("email", email));
			break;
		case R.id.ui_setting_layout:// 界面显示
			startActivity(new Intent(getActivity(),
					DisplaySettingActivity.class));
			break;
		case R.id.message_notice_layout:// 消息通知
			startActivity(new Intent(getActivity(), MessageNoticeActivity.class));
			break;
		case R.id.general_setting_layout:// 通用设置
			startActivity(new Intent(getActivity(),
					GeneralSettingActivity.class));
			break;
		case R.id.secure_and_private_layout:// 安全与隐私
			startActivity(new Intent(getActivity(),
					SecuritySettingActivity.class));
			break;
		case R.id.about_layout:// 关于
			startActivity(new Intent(getActivity(), AboutActivity.class));
			break;
		case R.id.btn_logout: // 退出登陆
			logout();
			break;
		default:
			break;
		}
	}

	void logout() {
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("正在退出登陆..");
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		MyApplication.getInstance().logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						// 重新显示登陆页面
						(getActivity()).finish();
						startActivity(new Intent(getActivity(),
								LoginActivity.class));

					}
				});
			}

			@Override
			public void onProgress(int progress, String status) {

			}

			@Override
			public void onError(int code, String message) {

			}
		});
	}

}
