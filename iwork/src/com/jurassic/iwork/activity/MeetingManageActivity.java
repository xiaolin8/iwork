package com.jurassic.iwork.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.db.MeetingRoomDao;
import com.jurassic.iwork.db.UserDao;
import com.jurassic.iwork.domain.MeetingRoom;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.fragment.MeetingListFragment;
import com.jurassic.iwork.fragment.MeetingRoomListFragment;
import com.jurassic.iwork.myview.PagerSlidingTabStrip;
import com.jurassic.iwork.utils.StringUtil;

public class MeetingManageActivity extends FragmentActivity {

	private TextView title;

	/** 会议安排界面的Fragment */
	public MeetingListFragment meetingListFragment;
	/** 会议室界面的Fragment */
	public MeetingRoomListFragment meetingRoomListFragment;

	/** PagerSlidingTabStrip的实例 */
	public PagerSlidingTabStrip tabs;

	/** 获取当前屏幕的密度 */
	private DisplayMetrics dm;
	public ViewPager pager;

	// 如何获取当前显示的是第几个tab
	public int tabIndex;

	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_manage);
		dm = getResources().getDisplayMetrics();
		InitViewPager();
		InitPagerSlidingTabStrip();
		setTabsValue();
		title = (TextView) this.findViewById(R.id.title2);
		title.setText("会议管理");
	}

	private void InitPagerSlidingTabStrip() {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		tabs.setViewPager(pager);
	}

	private void InitViewPager() {
		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		// pager.setCurrentItem(0);
	}

	/** 对PagerSlidingTabStrip的各项属性进行赋值。 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
		tabs.setOnPageChangeListener(new mPageChangeListener());
	}

	public class mPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {
			// arg0 :当前页面，及你点击滑动的页面
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			// arg1:当前页面偏移的百分比
			// arg2:当前页面偏移的像素位置
		}

		@Override
		public void onPageSelected(int position) {
			if (position == 0)
				title.setText("会议安排");
			else
				title.setText("会议室");
		}

	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "会议安排", "会议室" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (meetingListFragment == null) {
					meetingListFragment = new MeetingListFragment();
				}
				return meetingListFragment;
			case 1:
				if (meetingRoomListFragment == null) {
					meetingRoomListFragment = new MeetingRoomListFragment();
				}
				return meetingRoomListFragment;
			default:
				return null;
			}
		}
	}

	public void btn_new_meetingOnClick(View v) {
		startActivity(new Intent(this, NewMeetingActivity.class));
	}

	@Override
	protected void onStart() {
		super.onStart();

		// 开启异步任务加载uers表数据
		new AsyncTask<Void, Void, String>() {

			private long start;

			@Override
			protected String doInBackground(Void... params) {
				start = System.currentTimeMillis();
				// 检查本地数据库是否已存在MeetingRoom表
				MeetingRoomDao dao = new MeetingRoomDao(
						MeetingManageActivity.this.getApplicationContext());
				// 如果不存在就下载表，存在就更新表
				if (dao.dbHelper.isTableExists("MeetingRoom")) {
					// return null;
				}
				String jsonString = null;
				URL url;
				try {
					url = new URL(MyApplication.getInstance().getString(
							R.string.server_url)
							+ "/MeetingRoom/GetMeetingRooms");
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
							jsonString = new String(baos.toByteArray());
							return jsonString;

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
				return jsonString;
			};

			// 在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用
			@Override
			protected void onPostExecute(String result) {
				if (!StringUtil.isEmpty(result)) {
					// JSON串转会议室对象列表
					List<MeetingRoom> rooms = JSON.parseArray(result,
							MeetingRoom.class);
					// 存入db
					MeetingRoomDao dao = new MeetingRoomDao(
							MeetingManageActivity.this.getApplicationContext());
					dao.saveMeetingRoomList(rooms);
				}
			}

		}.execute(new Void[] {});

	}

}
