package com.jurassic.iwork.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jurassic.iwork.R;
import com.jurassic.iwork.adapter.MeetingRoomAdapter;
import com.jurassic.iwork.db.MeetingRoomDao;
import com.jurassic.iwork.domain.MeetingRoom;

public class MeetingRoomListFragment extends Fragment {
	private ListView listView;
	private List<MeetingRoom> meetingRoomList;
	private boolean hidden;
	private MeetingRoomAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_meeting_room, container,
				false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		listView = (ListView) getView().findViewById(R.id.meeting_room_list);
		meetingRoomList = getMeetingRoomList();
		// 设置adapter
		adapter = new MeetingRoomAdapter(getActivity(),
				R.layout.row_meeting_room, meetingRoomList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}

		});
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
					getMeetingRoomList();
					adapter.notifyDataSetChanged();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取会议室列表 */
	private List<MeetingRoom> getMeetingRoomList() {
		MeetingRoomDao dao = new MeetingRoomDao(getActivity());
		meetingRoomList = dao.getMeetingRoomList();
		return meetingRoomList;
	}
}