package com.jurassic.iwork.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.widget.Sidebar;

/** 简单的好友Adapter实现 */
public class ContactAdapter extends ArrayAdapter<User> implements
		SectionIndexer {

	private LayoutInflater layoutInflater;
	private SparseIntArray positionOfSection;
	private SparseIntArray sectionOfPosition;
	private Sidebar sidebar;
	private int res;

	public ContactAdapter(Context context, int resource, List<User> objects,
			Sidebar sidebar) {
		super(context, resource, objects);
		this.res = resource;
		this.sidebar = sidebar;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;// 只有一种Item类型
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(res, null);
		}

		ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
		TextView unreadMsgView = (TextView) convertView
				.findViewById(R.id.unread_msg_number);
		TextView nameTextview = (TextView) convertView.findViewById(R.id.name);
		TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
		User user = getItem(position);
		// 设置nick，这里不涉及到完整user，用username代替nick显示
		String username = user.getUserId();
		String nickname;
		if (user.getUserName() != null)
			nickname = user.getUserName();
		else
			nickname = user.getNick();
		String header = user.getHeader();
		Integer headpic = user.getHeadPic();
		int[] headIds = { R.drawable.head1, R.drawable.head2, R.drawable.head3,
				R.drawable.head4, R.drawable.head5, R.drawable.head6,
				R.drawable.head7, R.drawable.head8, R.drawable.head9,
				R.drawable.head10, R.drawable.head11, R.drawable.head12,
				R.drawable.head13, R.drawable.head14, R.drawable.head15,
				R.drawable.head16, R.drawable.head17, R.drawable.head18,
				R.drawable.head19, R.drawable.head20, R.drawable.head21,
				R.drawable.head22, R.drawable.head23, R.drawable.head24,
				R.drawable.head25, R.drawable.head26, R.drawable.head27,
				R.drawable.head28, R.drawable.head29, R.drawable.head30,
				R.drawable.head31, R.drawable.head32, R.drawable.head33,
				R.drawable.head34, R.drawable.head35, R.drawable.head36,
				R.drawable.head37, R.drawable.head38, R.drawable.head39,
				R.drawable.head40, R.drawable.head41, R.drawable.head42,
				R.drawable.head43, R.drawable.head44, R.drawable.head45,
				R.drawable.head46, R.drawable.head47, R.drawable.head48,
				R.drawable.head49, R.drawable.head50, R.drawable.head51,
				R.drawable.head52, R.drawable.head53, R.drawable.head54,
				R.drawable.head55, R.drawable.head56, R.drawable.head57,
				R.drawable.head58, R.drawable.head59, R.drawable.head60,
				R.drawable.head61, R.drawable.head62, R.drawable.head63,
				R.drawable.head64, R.drawable.head65, R.drawable.head66,
				R.drawable.head67, R.drawable.head68 };
		if (position == 0 || header != null
				&& !header.equals(getItem(position - 1).getHeader())) {
			if ("".equals(header)) {
				tvHeader.setVisibility(View.GONE);
			} else {
				tvHeader.setVisibility(View.VISIBLE);
				tvHeader.setText(header);
			}
		} else {
			tvHeader.setVisibility(View.GONE);
		}
		// 显示申请与通知item
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
			nameTextview.setText(user.getNick());
			avatar.setImageResource(R.drawable.new_friends_icon);
			if (user.getUnreadMsgCount() > 0) {
				unreadMsgView.setVisibility(View.VISIBLE);
				unreadMsgView.setText(user.getUnreadMsgCount() + "");
			} else {
				unreadMsgView.setVisibility(View.INVISIBLE);
			}
		} else if (username.equals(Constant.GROUP_USERNAME)) {
			// 群聊item
			nameTextview.setText(user.getNick());
			avatar.setImageResource(R.drawable.groups_icon);
		} else {
			if (nickname != null && !nickname.equals(""))
				nameTextview.setText(nickname);
			else
				nameTextview.setText(username);
			if (unreadMsgView != null)
				unreadMsgView.setVisibility(View.INVISIBLE);
			if (headpic > 0 && headpic < 10000)
				avatar.setImageResource(headIds[headpic - 1]);
			else if (headpic > 1000) {
				Bitmap headimg = getImageFromAssetsFile(username + ".png");
				avatar.setImageBitmap(headimg);
			}
		}
		return convertView;
	}

	/**
	 * 从Assets中读取图片
	 */
	private Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap image = null;
		AssetManager am = MyApplication.applicationContext.getResources()
				.getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	@Override
	public User getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	public int getPositionForSection(int section) {
		return positionOfSection.get(section);
	}

	public int getSectionForPosition(int position) {
		return sectionOfPosition.get(position);
	}

	@Override
	public Object[] getSections() {
		positionOfSection = new SparseIntArray();
		sectionOfPosition = new SparseIntArray();
		int count = getCount();
		List<String> list = new ArrayList<String>();
		list.add(getContext().getString(R.string.search_header));
		positionOfSection.put(0, 0);
		sectionOfPosition.put(0, 0);
		for (int i = 0; i < count; i++) {
			String letter = getItem(i).getHeader();
			System.err.println("contactadapter getsection getHeader:" + letter
					+ " name:" + getItem(i).getUsername());
			int section = list.size() - 1;
			if (list.get(section) != null && !list.get(section).equals(letter)) {
				list.add(letter);
				section++;
				positionOfSection.put(section, i);
			}
			sectionOfPosition.put(i, section);
		}
		return list.toArray(new String[list.size()]);
	}

}
