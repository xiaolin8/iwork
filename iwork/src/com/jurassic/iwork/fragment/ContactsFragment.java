package com.jurassic.iwork.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.activity.ChatActivity;
import com.jurassic.iwork.activity.GroupsActivity;
import com.jurassic.iwork.adapter.ContactAdapter;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.widget.Sidebar;

public class ContactsFragment extends Fragment {
	private ContactAdapter adapter;
	private InputMethodManager inputMethodManager;
	private ListView listView;
	private Sidebar sidebar;
	private List<User> contactList;
	private boolean hidden;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();
		inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		listView = (ListView) getView().findViewById(R.id.list);
		sidebar = (Sidebar) getView().findViewById(R.id.sidebar);
		sidebar.setListView(listView);
		contactList = new ArrayList<User>();
		// 获取设置contactlist
		getContactList();
		// 设置adapter
		adapter = new ContactAdapter(getActivity(), R.layout.row_contact,
				contactList, sidebar);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String userid = adapter.getItem(position).getUserId();
				if (Constant.NEW_FRIENDS_USERNAME.equals(userid)) {
					// 进入申请与通知页面
					// User user = MyApplication.getInstance().getContactList()
					// .get(Constant.NEW_FRIENDS_USERNAME);
					// user.setUnreadMsgCount(0);
					// startActivity(new Intent(getActivity(),
					// NewFriendsMsgActivity.class));
				} else if (Constant.GROUP_USERNAME.equals(userid)) {
					// 进入群聊列表页面
					startActivity(new Intent(getActivity(),
							GroupsActivity.class));
				} else {
					// 直接进入聊天页面
					startActivity(new Intent(getActivity(), ChatActivity.class)
							.putExtra("nickName",
									adapter.getItem(position).getUserName())
							.putExtra("userId", userid));
				}
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏软键盘
				if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getActivity().getCurrentFocus() != null)
						inputMethodManager.hideSoftInputFromWindow(
								getActivity().getCurrentFocus()
										.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
		registerForContextMenu(listView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// 得到当前被选中的item信息
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		String title = ((TextView) info.targetView.findViewById(R.id.name))
				.getText().toString();
		// 设置ContextMenu标题
		menu.setHeaderTitle(title);
		// 长按前两个不弹menu
		if (((AdapterContextMenuInfo) menuInfo).position > 1) {
			getActivity().getMenuInflater().inflate(
					R.menu.context_contact_list, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.btn_talkOnline) {
			// 跳转到聊天界面
			// 得到当前被选中的item信息
			final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			String userId = adapter.getItem(info.position).getUsername();
			String nick = ((TextView) info.targetView.findViewById(R.id.name))
					.getText().toString();
			startActivity(new Intent(getActivity(), ChatActivity.class)
					.putExtra("nickName", nick).putExtra("userId", userId));
			// 必须要把userId传过去
			return true;
		} else if (item.getItemId() == R.id.btn_call) {
			// 获取该人电话，并拨打
			return true;
		} else if (item.getItemId() == R.id.btn_sms) {
			// 获取该人电话，并发送短信
			return true;
		} else if (item.getItemId() == R.id.btn_viewDetail) {
			// 跳转到详细资料界面
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
			refresh();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
		}
	}

	// 刷新ui
	public void refresh() {
		try {
			// 可能会在子线程中调到这方法
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					getContactList();
					adapter.notifyDataSetChanged();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取联系人列表，并排序 */
	private void getContactList() {
		contactList.clear();
		// 获取本地好友列表(一定不要在内存中获取，而要在数据库中获取)
		Map<String, User> users = MyApplication.getInstance().getContactList();
		Iterator<Entry<String, User>> iterator = users.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, User> entry = iterator.next();
			if (!entry.getKey().equals(Constant.NEW_FRIENDS_USERNAME)
					&& !entry.getKey().equals(Constant.GROUP_USERNAME)
					&& !entry.getKey().equals(
							MyApplication.getInstance().getUserName()))// 不要把自己本身加到联系人里面去
				contactList.add(entry.getValue());
		}
		// 排序
		Collections.sort(contactList, new Comparator<User>() {

			@Override
			public int compare(User lhs, User rhs) {
				return lhs.getUsername().compareTo(rhs.getUsername());
			}
		});

		// 加入"群聊"
		contactList.add(0, users.get(Constant.GROUP_USERNAME));
		// 添加"申请与通知"到首位
		contactList.add(0, users.get(Constant.NEW_FRIENDS_USERNAME));
	}
}
