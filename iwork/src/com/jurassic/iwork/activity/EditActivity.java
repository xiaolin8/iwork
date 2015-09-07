package com.jurassic.iwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jurassic.iwork.R;

public class EditActivity extends BaseActivity {
	private EditText editText;
	private TextView tv_introduction;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_group_desc);

		editText = (EditText) findViewById(R.id.edittext);
		tv_introduction = (TextView) findViewById(R.id.tv_introduction);
		String title = getIntent().getStringExtra("title");
		String groupName = getIntent().getStringExtra("groupName");
		String groupDesc = getIntent().getStringExtra("groupDesc");
		if (title != null)
			((TextView) findViewById(R.id.tv_title)).setText(title);
		if (groupName != null)
			editText.setText(groupName);
		editText.setSelection(editText.length());
		if (groupDesc != null)
			tv_introduction.setText(groupDesc);
	}

	public void save(View view) {
		setResult(RESULT_OK, new Intent().putExtra("groupName", editText
				.getText().toString()).putExtra("groupDesc", tv_introduction
						.getText().toString()));
		finish();
	}
}
