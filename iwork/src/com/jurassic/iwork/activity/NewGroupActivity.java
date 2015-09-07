package com.jurassic.iwork.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.jurassic.iwork.R;

public class NewGroupActivity extends BaseActivity {
	private EditText groupNameEditText;
	private ProgressDialog progressDialog;
	private EditText introductionEditText;
	private CheckBox memberCheckbox;
	private LinearLayout openInviteContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
		groupNameEditText = (EditText) findViewById(R.id.edit_group_name);
		introductionEditText = (EditText) findViewById(R.id.edit_group_introduction);
		memberCheckbox = (CheckBox) findViewById(R.id.cb_member_inviter);
		openInviteContainer = (LinearLayout) findViewById(R.id.ll_open_invite);
	}

	/**
	 * @param v
	 */
	public void save(View v) {
		String name = groupNameEditText.getText().toString();
		if (TextUtils.isEmpty(name)) {
			Intent intent = new Intent(this, AlertDialog.class);
			intent.putExtra("msg", "群组名称不能为空");
			startActivity(intent);
		} else {
			// 进通讯录选人
			startActivityForResult(
					new Intent(this, GroupPickContactsActivity.class).putExtra(
							"groupName", name), 0);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 新建群组
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在创建群聊...");
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();

			new Thread(new Runnable() {
				@Override
				public void run() {
					// 调用sdk创建群组方法
					String groupName = groupNameEditText.getText().toString()
							.trim();
					String desc = introductionEditText.getText().toString();
					String[] members = data.getStringArrayExtra("newmembers");
					try {
						// 创建不公开群
						EMGroupManager.getInstance().createPrivateGroup(
								groupName, desc, members,
								memberCheckbox.isChecked());
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								setResult(RESULT_OK);
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								Toast.makeText(NewGroupActivity.this,
										"创建群组失败:" + e.getLocalizedMessage(), 1)
										.show();
							}
						});
					}

				}
			}).start();
		}
	}

	public void back(View view) {
		finish();
	}
}
