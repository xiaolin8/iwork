package com.jurassic.iwork.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jurassic.iwork.R;
import com.jurassic.iwork.utils.BitmapUtil;
import com.jurassic.iwork.widget.CustomQuestionDialog;

public class MyProfileActivity extends BaseActivity implements OnClickListener {

	private ImageView my_profile_logo;
	private TextView editUserId;
	private TextView editUserName;
	private EditText editEnglishName;
	private TextView editSingnature;
	private EditText editCellPhone;
	private EditText editOfficePhone;
	private EditText editEmail;
	private LinearLayout my_signature_layout;
	private LinearLayout my_headpic_layout;
	private TextView btn_save_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		my_profile_logo = (ImageView) this.findViewById(R.id.my_profile_logo);
		editUserId = (TextView) this.findViewById(R.id.editUserId);
		// editUserId.setKeyListener(null);// 设置为不可编辑
		editUserName = (TextView) this.findViewById(R.id.editUserName);
		// editUserName.setOnClickListener(null);// 设置为不可编辑
		editEnglishName = (EditText) this.findViewById(R.id.editEnglishName);
		editSingnature = (TextView) this.findViewById(R.id.editSingnature);
		// editSingnature.setKeyListener(null);
		editCellPhone = (EditText) this.findViewById(R.id.editCellPhone);
		editOfficePhone = (EditText) this.findViewById(R.id.editOfficePhone);
		editEmail = (EditText) this.findViewById(R.id.editEmail);
		my_signature_layout = (LinearLayout) this
				.findViewById(R.id.my_signature_layout);
		my_signature_layout.setOnClickListener(this);
		my_headpic_layout = (LinearLayout) this
				.findViewById(R.id.my_headpic_layout);
		my_headpic_layout.setOnClickListener(this);
		btn_save_all = (TextView) this.findViewById(R.id.btn_save_all);
		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btn_save_all.setTextColor(0xff2498AE);
				btn_save_all.setClickable(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};
		Intent intent = getIntent();
		int headpic = intent.getIntExtra("headpic", 0);
		String userid = intent.getStringExtra("userid");
		String username = intent.getStringExtra("username");
		String englishname = intent.getStringExtra("englishname");
		String signature = intent.getStringExtra("signature");
		String cellphone = intent.getStringExtra("cellphone");
		String officephone = intent.getStringExtra("officephone");
		String email = intent.getStringExtra("email");
		int[] headIds = { R.drawable.head1, R.drawable.head2, R.drawable.head3,
				R.drawable.head4, R.drawable.head5, R.drawable.head6,
				R.drawable.head7, R.drawable.head8, R.drawable.head9,
				R.drawable.head10, R.drawable.head11, R.drawable.head12,
				R.drawable.head13, R.drawable.head14, R.drawable.head15,
				R.drawable.head16, R.drawable.head17, R.drawable.head18,
				R.drawable.head19, R.drawable.head20, R.drawable.head21,
				R.drawable.head22, R.drawable.head23, R.drawable.head24,
				R.drawable.head25, R.drawable.head26, R.drawable.head27,
				R.drawable.head28, R.drawable.head29, R.drawable.head30,
				R.drawable.head31, R.drawable.head32, R.drawable.head33,
				R.drawable.head34, R.drawable.head35, R.drawable.head36,
				R.drawable.head37, R.drawable.head38, R.drawable.head39,
				R.drawable.head40, R.drawable.head41, R.drawable.head42,
				R.drawable.head43, R.drawable.head44, R.drawable.head45,
				R.drawable.head46, R.drawable.head47, R.drawable.head48,
				R.drawable.head49, R.drawable.head50, R.drawable.head51,
				R.drawable.head52, R.drawable.head53, R.drawable.head54,
				R.drawable.head55, R.drawable.head56, R.drawable.head57,
				R.drawable.head58, R.drawable.head59, R.drawable.head60,
				R.drawable.head61, R.drawable.head62, R.drawable.head63,
				R.drawable.head64, R.drawable.head65, R.drawable.head66,
				R.drawable.head67, R.drawable.head68 };
		if (headpic > 0 && headpic < 10000)
			my_profile_logo.setImageResource(headIds[headpic - 1]);
		else if (headpic > 1000) {
			Bitmap headimg = BitmapUtil.getImageFromAssetsFile(userid + ".png");
			my_profile_logo.setImageBitmap(headimg);
		}
		if (!TextUtils.isEmpty(userid)) {
			editUserId.setText(userid);
		}
		if (!TextUtils.isEmpty(username)) {
			editUserName.setText(username);
		}
		if (!TextUtils.isEmpty(englishname)) {
			editEnglishName.setText(englishname);
		}
		if (!TextUtils.isEmpty(signature)) {
			editSingnature.setText(signature);
		}
		if (!TextUtils.isEmpty(cellphone)) {
			editCellPhone.setText(cellphone);
		}
		if (!TextUtils.isEmpty(officephone)) {
			editOfficePhone.setText(officephone);
		}
		if (!TextUtils.isEmpty(email)) {
			editEmail.setText(email);
		}
		editEnglishName.addTextChangedListener(watcher);
		editSingnature.addTextChangedListener(watcher);
		editCellPhone.addTextChangedListener(watcher);
		editOfficePhone.addTextChangedListener(watcher);
		editEmail.addTextChangedListener(watcher);
	}

	public void save(View v) {
		btn_save_all.setTextColor(0xff666666);
		btn_save_all.setClickable(false);
		Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT)
				.show();
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 开启异步任务加载uers表数据
			new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					return data.getStringExtra("strSignature");
				}

				@Override
				protected void onPostExecute(String result) {
					editSingnature.setText(result);
				}

			}.execute(new Void[] {});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_headpic_layout:
			startActivityForResult(
					new Intent(this, ChooseHeadpicActivity.class), 0);
			break;
		case R.id.my_signature_layout:
		case R.id.editSingnature:
			Intent intent = new Intent();
			intent.setClass(this, EditSignatureActivity.class);
			Bundle bundle = new Bundle();
			String oldSignature = editSingnature.getText().toString();
			bundle.putString("oldSignature", oldSignature);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);// 这里采用startActivityForResult来做跳转，此处的0为一个依据，可以写其他的值，但一定要>=0
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (btn_save_all.getCurrentTextColor() == 0xff2498AE) {
				CustomQuestionDialog questionDialog = new CustomQuestionDialog(
						this, R.style.mydialog, R.layout.question_dialog,
						"提示", "是否放弃对资料的修改？", "放弃", "", "继续编辑");
				questionDialog
						.setOnLeftButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				questionDialog
						.setOnMiddleButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				questionDialog
						.setOnRightButtonListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				questionDialog.show();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}