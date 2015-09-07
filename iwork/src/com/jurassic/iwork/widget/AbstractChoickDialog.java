package com.jurassic.iwork.widget;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.jurassic.iwork.R;

public abstract class AbstractChoickDialog extends Dialog implements
		OnClickListener {

	protected Context mContext;
	protected View mParentView;

	protected TextView mTVTitle;
	protected TextView mButtonOK;
	protected TextView mButtonCancel;
	protected ListView mListView;

	protected List<String> mList;
	protected OnClickListener mOkClickListener;

	public AbstractChoickDialog(Context context, List<String> list) {
		super(context);
		// TODO Auto-generated constructor stub

		mContext = context;
		mList = list;

		initView(mContext);

	}

	protected void initView(Context context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alert_list_layout);
		mTVTitle = (TextView) findViewById(R.id.tvTitle);
		mButtonOK = (TextView) findViewById(R.id.btnOK);
		mButtonOK.setOnClickListener(this);
		mButtonCancel = (TextView) findViewById(R.id.btnRight);
		mButtonCancel.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.listView);

		Window dialogWindow = getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//
		// WindowManager m = dialogWindow.getWindowManager();
		// Display d = m.getDefaultDisplay();
		// lp.height = 2 * d.getHeight();
		// lp.width = 2 *d.getWidth();
		// lp.alpha = 0.8f;
		//
		// dialogWindow.setAttributes(lp);

		// lp.flags = lp.flags|WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// dialogWindow.setAttributes(lp);

		ColorDrawable dw = new ColorDrawable(0x0000ff00);
		dialogWindow.setBackgroundDrawable(dw);
	}

	public void setTitle(String title) {
		mTVTitle.setText(title);
	}

	public void setOnOKButtonListener(OnClickListener onClickListener) {
		mOkClickListener = onClickListener;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOK:
			onButtonOK();
			break;
		case R.id.btnRight:
			onButtonCancel();
			break;
		}
	}

	protected void onButtonOK() {
		dismiss();

		if (mOkClickListener != null) {
			mOkClickListener.onClick(this, 0);
		}
	}

	protected void onButtonCancel() {
		dismiss();

	}
}
