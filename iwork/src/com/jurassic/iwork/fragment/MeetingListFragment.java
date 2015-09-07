package com.jurassic.iwork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jurassic.iwork.R;
import com.jurassic.iwork.activity.NewMeetingActivity;

public class MeetingListFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.part_empty_sche_view, container, false);
	}

	public void btn_new_meetingOnClick(View v) {
		startActivity(new Intent(getActivity(), NewMeetingActivity.class));
	}

}