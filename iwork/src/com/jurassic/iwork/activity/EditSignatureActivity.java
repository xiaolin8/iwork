package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jurassic.iwork.R;

public class EditSignatureActivity extends BaseActivity {

	private EditText editSignature;
	private TextView strNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_signature);
		editSignature = (EditText) this.findViewById(R.id.editSignature);
		strNum = (TextView) this.findViewById(R.id.strNum);
		Bundle myBundle = this.getIntent().getExtras();
		String oldSignature = myBundle.getString("oldSignature");
		if (oldSignature != null) {
			editSignature.setText(oldSignature);
		}
		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int strLength = editSignature.getText().toString().length();
				strNum.setText(127 - strLength + "");
				if (127 - strLength <= 0) {
					strNum.setTextColor(android.graphics.Color.RED);
				}
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
		editSignature.addTextChangedListener(watcher);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_OK, new Intent().putExtra("strSignature",
					editSignature.getText().toString()));
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		int strLength = editSignature.getText().toString().length();
		if (strLength > 127)
			setResult(
					RESULT_OK,
					new Intent().putExtra("strSignature", editSignature
							.getText().toString().subSequence(0, 127)));
		else {
			setResult(RESULT_OK, new Intent().putExtra("strSignature",
					editSignature.getText().toString()));
		}
		finish();
	}

	public void back(View v) {
		int strLength = editSignature.getText().toString().length();
		if (strLength > 127)
			setResult(
					RESULT_OK,
					new Intent().putExtra("strSignature", editSignature
							.getText().toString().subSequence(0, 127)));
		else {
			setResult(RESULT_OK, new Intent().putExtra("strSignature",
					editSignature.getText().toString()));
		}
		finish();
	}

}
