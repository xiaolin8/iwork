package com.jurassic.iwork.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jurassic.iwork.R;
import com.jurassic.iwork.activity.MeetingManageActivity;
import com.jurassic.iwork.activity.NewsListActivity;

public class PublicZoneHomeFragment extends Fragment {

	private TextView title;
	private GridView maingv;
	private Fragment chatHomeFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_public_zone_home, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		chatHomeFragment = new ContactsFragment();
		title = (TextView) getActivity().findViewById(R.id.title);
		// 获取到GridView
		maingv = (GridView) getView().findViewById(R.id.publicZoneGridView);
		// 给gridview设置数据适配器
		maingv.setAdapter(new MainGridViewAdapter(getActivity()));
		// 点击事件
		maingv.setOnItemClickListener(new MainItemClickListener());
	}

	// 完成gridview 数据到界面的适配
	public class MainGridViewAdapter extends BaseAdapter {
		private String[] names = { "公司通讯录", "会议管理", "公司新闻", "公告通知" };
		private int[] icons = { R.drawable.main_icon_addressbook,
				R.drawable.main_icon_meeting, R.drawable.main_icon_news,
				R.drawable.main_icon_notice };
		private Context context;
		LayoutInflater infalter;

		public MainGridViewAdapter(Context context) {
			this.context = context;
			// 方法1 通过系统的service 获取到 试图填充器
			// infalter = (LayoutInflater)
			// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 方法2 通过layoutinflater的静态方法获取到 视图填充器
			infalter = LayoutInflater.from(context);
		}

		// 返回gridview里面有多少个条目
		public int getCount() {
			return names.length;
		}

		// 返回某个position对应的条目
		public Object getItem(int position) {
			return position;
		}

		// 返回某个position对应的id
		public long getItemId(int position) {
			return position;
		}

		// 返回某个位置对应的视图
		public View getView(int position, View convertView, ViewGroup parent) {
			// 把一个布局文件转换成视图
			View view = infalter.inflate(R.layout.layout_gridview_item, null);
			ImageView iv = (ImageView) view.findViewById(R.id.main_gv_iv);
			TextView tv = (TextView) view.findViewById(R.id.main_gv_tv);
			// 设置每一个item的名字和图标
			iv.setImageResource(icons[position]);
			tv.setText(names[position]);
			return view;
		}
	}

	private class MainItemClickListener implements OnItemClickListener {

		/**
		 * @param parent
		 *            代表当前的gridview
		 * @param view
		 *            代表点击的item
		 * @param position
		 *            当前点击的item在适配中的位置
		 * @param id
		 *            当前点击的item在哪一行
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				title.setText("公司通讯录");
				FragmentTransaction trx = getFragmentManager()
						.beginTransaction();
				trx.replace(R.id.content, chatHomeFragment).commit();
				// startActivity(new Intent(getActivity(), MailActivity.class));
				break;
			case 1:
				startActivity(new Intent(getActivity(),
						MeetingManageActivity.class));
				break;
			case 2:
				startActivity(new Intent(getActivity(), NewsListActivity.class));
				break;

			default:
				break;
			}
		}
	}

}
