package com.jurassic.iwork.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jurassic.iwork.R;

public class CustomQuestionDialog extends Dialog implements OnClickListener {

	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	protected Context mContext;
	protected TextView mTVTitle, mTVContent;
	/** 确定按钮 **/
	protected TextView mButtonLeft;
	/** 中间按钮 **/
	protected TextView mButtonMiddle;
	/** 取消按钮 **/
	protected TextView mButtonCancel;

	protected String title, content, txtLeftBtn, txtMiddleBtn, txtRightBtn;

	protected OnClickListener mLeftClickListener, mMiddleClickListener,
			mRightClickListener;

	/**
	 * 自定义主题及布局的构造方法
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 * @param title
	 * @param content
	 * @param txtLeftBtn
	 * @param txtMiddleBtn
	 * @param txtRightBtn
	 */
	public CustomQuestionDialog(Context context, int theme, int resLayout,
			String title, String content, String txtLeftBtn,
			String txtMiddleBtn, String txtRightBtn) {
		super(context, theme);
		this.mContext = context;
		this.layoutRes = resLayout;
		this.title = title;
		this.content = content;
		this.txtLeftBtn = txtLeftBtn;
		this.txtMiddleBtn = txtMiddleBtn;
		this.txtRightBtn = txtRightBtn;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 指定布局
		this.setContentView(layoutRes);
		mTVTitle = (TextView) findViewById(R.id.tvTitle);
		mTVContent = (TextView) findViewById(R.id.tvContent);
		mTVTitle.setText(title);
		mTVContent.setText(content);
		// 根据id在布局中找到控件对象
		mButtonLeft = (Button) findViewById(R.id.btnLeft);
		mButtonMiddle = (Button) findViewById(R.id.btnMiddle);
		mButtonCancel = (Button) findViewById(R.id.btnRight);
		if (!txtMiddleBtn.equals(""))
			mButtonMiddle.setVisibility(View.VISIBLE);
		// 设置按钮的文本
		mButtonLeft.setText(txtLeftBtn);
		mButtonMiddle.setText(txtMiddleBtn);
		mButtonCancel.setText(txtRightBtn);
		// 设置按钮的文本颜色
		mButtonLeft.setTextColor(0xff1E90FF);
		mButtonMiddle.setTextColor(0xff1E90FF);
		mButtonCancel.setTextColor(0xff1E90FF);

		// 为按钮绑定点击事件监听器
		mButtonLeft.setOnClickListener(this);
		mButtonMiddle.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
	}

	public void setOnLeftButtonListener(OnClickListener onClickListener) {
		mLeftClickListener = onClickListener;
	}

	public void setOnMiddleButtonListener(OnClickListener onClickListener) {
		mMiddleClickListener = onClickListener;
	}

	public void setOnRightButtonListener(OnClickListener onClickListener) {
		mRightClickListener = onClickListener;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			onButtonLeft();
			break;
		case R.id.btnMiddle:
			onButtonMiddle();
			break;
		case R.id.btnRight:
			onButtonRight();
			break;
		}
	}

	protected void onButtonLeft() {
		dismiss();
		if (mLeftClickListener != null) {
			mLeftClickListener.onClick(this, 0);
		}
	}

	protected void onButtonMiddle() {
		if (mMiddleClickListener != null) {
			mMiddleClickListener.onClick(this, 0);
		}
		dismiss();
	}

	protected void onButtonRight() {
		dismiss();
	}
}
