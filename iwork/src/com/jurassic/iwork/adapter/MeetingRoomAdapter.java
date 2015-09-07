package com.jurassic.iwork.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jurassic.iwork.R;
import com.jurassic.iwork.domain.MeetingRoom;

public class MeetingRoomAdapter extends ArrayAdapter<MeetingRoom> {

	private LayoutInflater layoutInflater;
	private List<MeetingRoom> meetingRoomList;
	private int res;

	public MeetingRoomAdapter(Context context, int resource,
			List<MeetingRoom> objects) {
		super(context, resource, objects);
		this.meetingRoomList = objects;
		this.res = resource;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.row_meeting_room,
					parent, false);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.room_city = (TextView) convertView.findViewById(R.id.city);
			holder.room_name = (TextView) convertView
					.findViewById(R.id.room_name);
			holder.next_sche = (TextView) convertView
					.findViewById(R.id.next_sche);
			holder.equi_proj = (ImageView) convertView
					.findViewById(R.id.equi_proj);
			holder.equi_wifi = (ImageView) convertView
					.findViewById(R.id.equi_wifi);
			holder.equi_board = (ImageView) convertView
					.findViewById(R.id.equi_board);
			holder.equi_video = (ImageView) convertView
					.findViewById(R.id.equi_video);
			holder.equi_audio = (ImageView) convertView
					.findViewById(R.id.equi_audio);
			holder.room_desc = (TextView) convertView
					.findViewById(R.id.room_desc);
			holder.status_icon = (ImageView) convertView
					.findViewById(R.id.status_icon);
			convertView.setTag(holder);
		}
		MeetingRoom room = getItem(position);
		holder.room_city.setText(room.getRoomAddr());
		holder.room_name.setText(room.getRoomName());
		holder.room_desc.setText("备注：" + room.getRoomDesc());
		holder.next_sche.setVisibility(View.GONE);
		holder.status_icon
.setBackgroundResource(R.drawable.room_free);
		String equipments = room.getEquipments();
		if (equipments.contains("a"))
			holder.equi_proj.setVisibility(View.VISIBLE);
		else
			holder.equi_proj.setVisibility(View.GONE);
		if (equipments.contains("b"))
			holder.equi_wifi.setVisibility(View.VISIBLE);
		else
			holder.equi_wifi.setVisibility(View.GONE);
		if (equipments.contains("c"))
			holder.equi_board.setVisibility(View.VISIBLE);
		else
			holder.equi_board.setVisibility(View.GONE);
		if (equipments.contains("d"))
			holder.equi_video.setVisibility(View.VISIBLE);
		else
			holder.equi_video.setVisibility(View.GONE);
		if (equipments.contains("e"))
			holder.equi_audio.setVisibility(View.VISIBLE);
		else
			holder.equi_audio.setVisibility(View.GONE);
		return convertView;
	}

	@Override
	public MeetingRoom getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	public static class ViewHolder {
		TextView room_city;
		TextView room_name;
		TextView room_addr;
		TextView room_desc;
		TextView next_sche;
		ImageView equi_proj;
		ImageView equi_wifi;
		ImageView equi_board;
		ImageView equi_video;
		ImageView equi_audio;
		ImageView status_icon;
	}

}