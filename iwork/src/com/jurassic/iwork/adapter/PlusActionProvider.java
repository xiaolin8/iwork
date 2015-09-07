package com.jurassic.iwork.adapter;

import android.R;
import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class PlusActionProvider extends ActionProvider{

	private Context context;  
	
	public PlusActionProvider(Context context) {
		super(context);
		this.context = context;  
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();  
        subMenu.add("群聊")  
                .setIcon(R.drawable.dialog_frame)  
                .setOnMenuItemClickListener(new OnMenuItemClickListener() {  
                    @Override  
                    public boolean onMenuItemClick(MenuItem item) {  
                        return true;  
                    }  
                });  
	}

	@Override  
    public boolean hasSubMenu() {  
        return true;  
    }  
}
