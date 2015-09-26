package com.jurassic.iwork.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.easemob.chat.EMContact;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.DateUtils;
import com.jurassic.iwork.Constant;
import com.jurassic.iwork.MyApplication;
import com.jurassic.iwork.R;
import com.jurassic.iwork.domain.User;
import com.jurassic.iwork.utils.SmileUtils;

/** 显示所有聊天记录adpater */
public class ChatAllHistoryAdapter extends ArrayAdapter<EMConversation> {

	private LayoutInflater inflater;
	private List<EMConversation> conversationList;
	private List<EMConversation> copyConversationList;
	private ConversationFilter conversationFilter;
	private User user;

	public ChatAllHistoryAdapter(Context context, int textViewResourceId,
			List<EMConversation> objects) {
		super(context, textViewResourceId, objects);
		this.conversationList = objects;
		copyConversationList = new ArrayList<EMConversation>();
		copyConversationList.addAll(objects);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_chat_history, parent,
					false);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.unreadLabel = (TextView) convertView
					.findViewById(R.id.unread_msg_number);
			holder.message = (TextView) convertView.findViewById(R.id.message);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.msgState = convertView.findViewById(R.id.msg_state);
			holder.list_item_layout = (RelativeLayout) convertView
					.findViewById(R.id.list_item_layout);
			convertView.setTag(holder);
		}
		if (position % 2 == 0) {
			holder.list_item_layout
					.setBackgroundResource(R.drawable.mm_listitem);
		} else {
			holder.list_item_layout
					.setBackgroundResource(R.drawable.mm_listitem_grey);
		}

		// 获取与此用户/群组的会话
		EMConversation conversation = getItem(position);
		// 获取用户username或者群组groupid
		final String username = conversation.getUserName();
		List<EMGroup> groups = EMGroupManager.getInstance().getAllGroups();
		EMContact contact = null;
		boolean isGroup = false;
		for (EMGroup group : groups) {
			if (group.getGroupId().equals(username)) {
				isGroup = true;
				contact = group;
				break;
			}
		}
		if (isGroup) {
			// 群聊消息，显示群聊头像
			holder.avatar.setImageResource(R.drawable.group_icon);
			holder.name.setText(contact.getNick() != null ? contact.getNick()
					: username);
		} else {
			// 本地或者服务器获取用户详情，以用来显示头像和nick
			// 获取本地好友列表(一定不要在内存中获取，而要在数据库中获取)
			Map<String, User> users = MyApplication.getInstance()
					.getContactList();
			Iterator<Entry<String, User>> iterator = users.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, User> entry = iterator.next();
				if (entry.getKey().equals(username))// 不要把自己本身加到联系人里面去
					user = entry.getValue();
			}
			Integer headpic = user.getHeadPic();
			int[] headIds = { R.drawable.head1, R.drawable.head2,
					R.drawable.head3, R.drawable.head4, R.drawable.head5,
					R.drawable.head6, R.drawable.head7, R.drawable.head8,
					R.drawable.head9, R.drawable.head10, R.drawable.head11,
					R.drawable.head12, R.drawable.head13, R.drawable.head14,
					R.drawable.head15, R.drawable.head16, R.drawable.head17,
					R.drawable.head18, R.drawable.head19, R.drawable.head20,
					R.drawable.head21, R.drawable.head22, R.drawable.head23,
					R.drawable.head24, R.drawable.head25, R.drawable.head26,
					R.drawable.head27, R.drawable.head28, R.drawable.head29,
					R.drawable.head30, R.drawable.head31, R.drawable.head32,
					R.drawable.head33, R.drawable.head34, R.drawable.head35,
					R.drawable.head36, R.drawable.head37, R.drawable.head38,
					R.drawable.head39, R.drawable.head40, R.drawable.head41,
					R.drawable.head42, R.drawable.head43, R.drawable.head44,
					R.drawable.head45, R.drawable.head46, R.drawable.head47,
					R.drawable.head48, R.drawable.head49, R.drawable.head50,
					R.drawable.head51, R.drawable.head52, R.drawable.head53,
					R.drawable.head54, R.drawable.head55, R.drawable.head56,
					R.drawable.head57, R.drawable.head58, R.drawable.head59,
					R.drawable.head60, R.drawable.head61, R.drawable.head62,
					R.drawable.head63, R.drawable.head64, R.drawable.head65,
					R.drawable.head66, R.drawable.head67, R.drawable.head68 };
			if (headpic > 0 && headpic < 10000)
				holder.avatar.setImageResource(headIds[headpic - 1]);
			else if (headpic > 1000) {
				Bitmap headimg = getImageFromAssetsFile(username + ".png");
				holder.avatar.setImageBitmap(headimg);
			}
			if (username.equals(Constant.GROUP_USERNAME)) {
				holder.name.setText("群聊");
			} else if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
				holder.name.setText("申请与通知");
			} else if (user.getRealName() != null) {
				holder.name.setText(user.getRealName());
			} else {
				holder.name.setText(username);
			}
			// 获取这个username对应的nickName
			// String nickName = null;
			// try {
			// nickName = new AsyncTask<Void, Void, String>() {
			//
			// @Override
			// protected String doInBackground(Void... params) {
			// URL url;
			// try {
			// url = new URL(MyApplication.getInstance()
			// .getString(R.string.server_url)
			// + "/user/getNickById?userName=" + username);
			// HttpURLConnection conn = (HttpURLConnection) url
			// .openConnection();
			// conn.setRequestMethod("GET");
			// conn.setConnectTimeout(5000);
			// int code = conn.getResponseCode();
			// if (code == 200) {
			// InputStream stream = conn.getInputStream();
			// // 把输入流的内容转换为字符串
			// try {
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// int len = 0;
			// byte[] buffer = new byte[1024];
			// while ((len = stream.read(buffer)) != -1) {
			// baos.write(buffer, 0, len);
			// }
			// baos.close();
			// stream.close();
			// return new String(baos.toByteArray());
			// } catch (Exception e) {
			// Log.i("Exception", "Exception");
			// }
			// }
			// } catch (MalformedURLException e1) {
			// Log.i("Exception", "MalformedURLException");
			// } catch (IOException e1) {
			// Log.i("Exception", "IOException");
			// }
			// return null;
			// }
			//
			// }.execute(new Void[] {}).get();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (ExecutionException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// if (nickName != null && !nickName.equals("")) {
			// holder.name.setText(nickName);
			// } else {
			// holder.name.setText(username);
			// }
		}

		if (conversation.getUnreadMsgCount() > 0) {
			// 显示与此用户的消息未读数
			holder.unreadLabel.setText(String.valueOf(conversation
					.getUnreadMsgCount()));
			holder.unreadLabel.setVisibility(View.VISIBLE);
		} else {
			holder.unreadLabel.setVisibility(View.INVISIBLE);
		}

		if (conversation.getMsgCount() != 0) {
			// 把最后一条消息的内容作为item的message内容
			EMMessage lastMessage = conversation.getLastMessage();
			holder.message
					.setText(
							SmileUtils.getSmiledText(
									getContext(),
									getMessageDigest(lastMessage,
											(this.getContext()))),
							BufferType.SPANNABLE);

			holder.time.setText(DateUtils.getTimestampString(new Date(
					lastMessage.getMsgTime())));
			if (lastMessage.direct == EMMessage.Direct.SEND
					&& lastMessage.status == EMMessage.Status.FAIL) {
				holder.msgState.setVisibility(View.VISIBLE);
			} else {
				holder.msgState.setVisibility(View.GONE);
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

	/**
	 * 根据消息内容和消息类型获取消息内容提示
	 * 
	 * @param message
	 * @param context
	 * @return
	 */
	private String getMessageDigest(EMMessage message, Context context) {
		String digest = "";
		switch (message.getType()) {
		case LOCATION: // 位置消息
			if (message.direct == EMMessage.Direct.RECEIVE) {
				// 从sdk中提到了ui中，使用更简单不犯错的获取string的方法
				// digest = EasyUtils.getAppResourceString(context,
				// "location_recv");
				digest = getStrng(context, R.string.location_recv);
				digest = String.format(digest, message.getFrom());
				return digest;
			} else {
				// digest = EasyUtils.getAppResourceString(context,
				// "location_prefix");
				digest = getStrng(context, R.string.location_prefix);
			}
			break;
		case IMAGE: // 图片消息
			ImageMessageBody imageBody = (ImageMessageBody) message.getBody();
			digest = getStrng(context, R.string.picture)
					+ imageBody.getFileName();
			break;
		case VOICE:// 语音消息
			digest = getStrng(context, R.string.voice);
			break;
		case VIDEO: // 视频消息
			digest = getStrng(context, R.string.video);
			break;
		case TXT: // 文本消息
			if (!message.getBooleanAttribute(
					Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				digest = txtBody.getMessage();
			} else {
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				digest = getStrng(context, R.string.voice_call)
						+ txtBody.getMessage();
			}
			break;
		case FILE: // 普通文件消息
			digest = getStrng(context, R.string.file);
			break;
		default:
			System.err.println("error, unknow type");
			return "";
		}

		return digest;
	}

	private static class ViewHolder {
		/** 和谁的聊天记录 */
		TextView name;
		/** 消息未读数 */
		TextView unreadLabel;
		/** 最后一条消息的内容 */
		TextView message;
		/** 最后一条消息的时间 */
		TextView time;
		/** 用户头像 */
		ImageView avatar;
		/** 最后一条消息的发送状态 */
		View msgState;
		/** 整个list中每一行总布局 */
		RelativeLayout list_item_layout;
	}

	String getStrng(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	@Override
	public Filter getFilter() {
		if (conversationFilter == null) {
			conversationFilter = new ConversationFilter(conversationList);
		}
		return conversationFilter;
	}

	private class ConversationFilter extends Filter {
		List<EMConversation> mOriginalValues = null;

		public ConversationFilter(List<EMConversation> mList) {
			mOriginalValues = mList;
		}

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				mOriginalValues = new ArrayList<EMConversation>();
			}
			if (prefix == null || prefix.length() == 0) {
				results.values = copyConversationList;
				results.count = copyConversationList.size();
			} else {
				String prefixString = prefix.toString();
				final int count = mOriginalValues.size();
				final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

				for (int i = 0; i < count; i++) {
					final EMConversation value = mOriginalValues.get(i);
					String username = value.getUserName();

					EMGroup group = EMGroupManager.getInstance().getGroup(
							username);
					if (group != null) {
						username = group.getGroupName();
					}

					// First match against the whole ,non-splitted value
					if (username.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = username.split(" ");
						final int wordCount = words.length;

						// Start at index 0, in case valueText starts with
						// space(s)
						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValues.add(value);
								break;
							}
						}
					}
				}

				results.values = newValues;
				results.count = newValues.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			conversationList.clear();
			conversationList.addAll((List<EMConversation>) results.values);
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}

		}

	}
}
