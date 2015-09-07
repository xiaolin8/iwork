package com.jurassic.iwork.widget;

import java.util.List;

import android.content.Context;

import com.jurassic.iwork.R;
import com.jurassic.iwork.adapter.SingleChoicAdapter;
import com.jurassic.iwork.utils.AbstractChoickDialogUtils;

public class SingleChoiceDialog extends AbstractChoickDialog {

	private SingleChoicAdapter<String> mSingleChoicAdapter;

	public SingleChoiceDialog(Context context, List<String> list) {
		super(context, list);

		initData();
	}

	protected void initData() {
		mSingleChoicAdapter = new SingleChoicAdapter<String>(mContext, mList,
				R.drawable.selector_checkbox2);

		mListView.setAdapter(mSingleChoicAdapter);
		mListView.setOnItemClickListener(mSingleChoicAdapter);

		AbstractChoickDialogUtils.setListViewHeightBasedOnChildren(mListView);

	}

	public int getSelectItem() {
		return mSingleChoicAdapter.getSelectItem();
	}

}
