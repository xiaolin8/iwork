package com.jurassic.iwork.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jurassic.iwork.R;
import com.jurassic.iwork.widget.MultiChoicDialog;
import com.jurassic.iwork.widget.SingleChoiceDialog;

public class KaoQinRemindActivity extends BaseActivity implements
		OnClickListener {

	/** 设置签到提醒布局 */
	private RelativeLayout rl_switch_signin_remind;

	/** 设置签退提醒布局 */
	private RelativeLayout rl_switch_signout_remind;

	/** 设置签到提前提醒时间布局 */
	private RelativeLayout rl_remind_time;

	/** 设置工作日布局 */
	private RelativeLayout rl_set_weekday;

	/** 设置铃音布局 */
	private RelativeLayout rl_switch_sound;

	/** 设置震动布局 */
	private RelativeLayout rl_switch_vibrate;

	private ImageView iv_switch_open_signin_remind;

	private ImageView iv_switch_close_signin_remind;

	private ImageView iv_switch_open_signout_remind;

	private ImageView iv_switch_close_signout_remind;

	private ImageView iv_switch_open_sound;

	private ImageView iv_switch_close_sound;

	private ImageView iv_switch_open_vibrate;

	private ImageView iv_switch_close_vibrate;

	private TextView remind_time, tixing_days;

	private SingleChoiceDialog mSingleChoiceDialog;
	private MultiChoicDialog mMultiChoicDialog;

	private List<String> mSingleDataList;
	private List<String> mMultiDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoqin_tixing);
		rl_switch_signin_remind = (RelativeLayout) this
				.findViewById(R.id.rl_switch_signin_remind);
		rl_switch_signout_remind = (RelativeLayout) this
				.findViewById(R.id.rl_switch_signout_remind);
		rl_remind_time = (RelativeLayout) this
				.findViewById(R.id.rl_remind_time);
		rl_set_weekday = (RelativeLayout) this
				.findViewById(R.id.rl_set_weekday);
		rl_switch_sound = (RelativeLayout) this
				.findViewById(R.id.rl_switch_sound);
		rl_switch_vibrate = (RelativeLayout) this
				.findViewById(R.id.rl_switch_vibrate);
		rl_switch_signin_remind.setOnClickListener(this);
		rl_switch_signout_remind.setOnClickListener(this);
		rl_remind_time.setOnClickListener(this);
		rl_set_weekday.setOnClickListener(this);
		rl_switch_sound.setOnClickListener(this);
		rl_switch_vibrate.setOnClickListener(this);
		iv_switch_open_signin_remind = (ImageView) this
				.findViewById(R.id.iv_switch_open_signin_remind);
		iv_switch_close_signin_remind = (ImageView) this
				.findViewById(R.id.iv_switch_close_signin_remind);
		iv_switch_open_signout_remind = (ImageView) this
				.findViewById(R.id.iv_switch_open_signout_remind);
		iv_switch_close_signout_remind = (ImageView) this
				.findViewById(R.id.iv_switch_close_signout_remind);
		iv_switch_open_sound = (ImageView) this
				.findViewById(R.id.iv_switch_open_sound);
		iv_switch_close_sound = (ImageView) this
				.findViewById(R.id.iv_switch_close_sound);
		iv_switch_open_vibrate = (ImageView) this
				.findViewById(R.id.iv_switch_open_vibrate);
		iv_switch_close_vibrate = (ImageView) this
				.findViewById(R.id.iv_switch_close_vibrate);
		remind_time = (TextView) this.findViewById(R.id.remind_time);
		tixing_days = (TextView) this.findViewById(R.id.tixing_days);
		initData();
	}

	public void initData() {
		mSingleDataList = new ArrayList<String>();
		mMultiDataList = new ArrayList<String>();
		boolean booleans[] = new boolean[7];

		mSingleDataList.add("提前5分钟");
		mSingleDataList.add("提前10分钟");
		mSingleDataList.add("提前15分钟");

		mMultiDataList.add("周一");
		mMultiDataList.add("周二");
		mMultiDataList.add("周三");
		mMultiDataList.add("周四");
		mMultiDataList.add("周五");
		mMultiDataList.add("周六");
		mMultiDataList.add("周日");

		initDialog(booleans);
	}

	public void initDialog(boolean[] booleans) {
		mSingleChoiceDialog = new SingleChoiceDialog(this, mSingleDataList);
		mSingleChoiceDialog.setTitle("请选择");
		mSingleChoiceDialog
				.setOnOKButtonListener(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int selItem = mSingleChoiceDialog.getSelectItem();
						String s = "";
						switch (selItem) {
						case 0:
							s = "提前5分钟";
							break;
						case 1:
							s = "提前10分钟";
							break;
						case 2:
							s = "提前15分钟";
							break;
						default:
							break;
						}
						remind_time.setText(s);
					}

				});

		mMultiChoicDialog = new MultiChoicDialog(this, mMultiDataList, booleans);
		mMultiChoicDialog.setTitle("请选择");
		mMultiChoicDialog
				.setOnOKButtonListener(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						boolean[] selItems = mMultiChoicDialog.getSelectItem();
						int size = selItems.length;
						StringBuffer stringBuffer = new StringBuffer();
						for (int i = 0; i < size; i++) {
							if (selItems[i]) {
								switch (i) {
								case 0:
									stringBuffer.append("周一 ");
									break;
								case 1:
									stringBuffer.append("周二 ");
									break;
								case 2:
									stringBuffer.append("周三 ");
									break;
								case 3:
									stringBuffer.append("周四 ");
									break;
								case 4:
									stringBuffer.append("周五 ");
									break;
								case 5:
									stringBuffer.append("周六 ");
									break;
								case 6:
									stringBuffer.append("周日 ");
									break;
								default:
									break;
								}
							}
						}
						tixing_days.setText(stringBuffer.toString());
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_signin_remind:
			if (iv_switch_close_signin_remind.getVisibility() == View.VISIBLE) {
				iv_switch_close_signin_remind.setVisibility(View.INVISIBLE);
				iv_switch_open_signin_remind.setVisibility(View.VISIBLE);
				rl_remind_time.setVisibility(View.GONE);
			} else {
				iv_switch_close_signin_remind.setVisibility(View.VISIBLE);
				iv_switch_open_signin_remind.setVisibility(View.INVISIBLE);
				rl_remind_time.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.rl_switch_signout_remind:
			if (iv_switch_close_signout_remind.getVisibility() == View.VISIBLE) {
				iv_switch_close_signout_remind.setVisibility(View.INVISIBLE);
				iv_switch_open_signout_remind.setVisibility(View.VISIBLE);
			} else {
				iv_switch_close_signout_remind.setVisibility(View.VISIBLE);
				iv_switch_open_signout_remind.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_remind_time:
			mSingleChoiceDialog.show();
			break;
		case R.id.rl_set_weekday:
			mMultiChoicDialog.show();
			break;
		case R.id.rl_switch_sound:
			if (iv_switch_close_sound.getVisibility() == View.VISIBLE) {
				iv_switch_close_sound.setVisibility(View.INVISIBLE);
				iv_switch_open_sound.setVisibility(View.VISIBLE);
			} else {
				iv_switch_close_sound.setVisibility(View.VISIBLE);
				iv_switch_open_sound.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.rl_switch_vibrate:
			if (iv_switch_close_vibrate.getVisibility() == View.VISIBLE) {
				iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
				iv_switch_open_vibrate.setVisibility(View.VISIBLE);
			} else {
				iv_switch_close_vibrate.setVisibility(View.VISIBLE);
				iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}

	}

	public void save(View v) {
		Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT)
				.show();
		finish();
	}
}
