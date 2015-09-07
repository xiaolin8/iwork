package com.jurassic.iwork.widget;

import java.util.List;

import android.content.Context;

import com.jurassic.iwork.R;
import com.jurassic.iwork.adapter.MultiChoicAdapter;
import com.jurassic.iwork.utils.AbstractChoickDialogUtils;

public class MultiChoicDialog extends AbstractChoickDialog{

	private MultiChoicAdapter<String> mMultiChoicAdapter;

	public MultiChoicDialog(Context context, List<String> list, boolean[] flag) {
		super(context, list);
		
		initData(flag);
	}
	

	protected void initData(boolean flag[]) {
		// TODO Auto-generated method stub
		mMultiChoicAdapter = new MultiChoicAdapter<String>(mContext, mList, flag, R.drawable.selector_checkbox1);
		
		mListView.setAdapter(mMultiChoicAdapter);
		mListView.setOnItemClickListener(mMultiChoicAdapter);   
		
		AbstractChoickDialogUtils.setListViewHeightBasedOnChildren(mListView);

	}


	public boolean[] getSelectItem()
	{
		return mMultiChoicAdapter.getSelectItem();
	}
	
}
