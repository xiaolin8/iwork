package com.jurassic.iwork.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jurassic.iwork.R;
import com.jurassic.iwork.myview.PagerSlidingTabStrip;

public class ChatHomeFragment extends Fragment {
	private TextView title;
	/** 聊天记录界面的Fragment */
	public ChatAllHistoryFragment chatAllHistoryFragment;
	/** 联系人界面的Fragment */
	public ContactsFragment contactsFragment;

	/** PagerSlidingTabStrip的实例 */
	public PagerSlidingTabStrip tabs;

	/** 获取当前屏幕的密度 */
	private DisplayMetrics dm;
	public ViewPager pager;

	// 如何获取当前显示的是第几个tab
	public int tabIndex;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_home, null);
		dm = getResources().getDisplayMetrics();
		view.getId();
		InitViewPager(view);
		InitPagerSlidingTabStrip(view);
		setTabsValue();
		return view;
	}

	private void InitPagerSlidingTabStrip(View parentView) {
		tabs = (PagerSlidingTabStrip) parentView.findViewById(R.id.tabs);
		tabs.setViewPager(pager);
	}

	private void InitViewPager(View parentView) {
		title = (TextView) getActivity().findViewById(R.id.title);
		pager = (ViewPager) parentView.findViewById(R.id.viewpager);

		pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
		pager.setCurrentItem(0);
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
			// TODO Auto-generated method stub
			if(position==0)
				title.setText("会话");
			else
				title.setText("联系人");
//			switch (position) {
//			case 0:
//				title.setText("消息");
//			case 1:
//				title.setText("联系人");
//			default:
//				title.setText("123:" + position);
//				return;
//			}
			
		}

	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "会话", "联系人" };

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
				if (chatAllHistoryFragment == null) {
					chatAllHistoryFragment = new ChatAllHistoryFragment();
				}
				return chatAllHistoryFragment;
			case 1:
				if (contactsFragment == null) {
					contactsFragment = new ContactsFragment();
				}
				return contactsFragment;
			default:
				return null;
			}
		}
	}

}
