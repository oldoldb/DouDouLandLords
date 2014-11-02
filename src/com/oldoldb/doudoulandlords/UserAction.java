package com.oldoldb.doudoulandlords;

import android.graphics.Bitmap;

public class UserAction extends BaseAction{

	private boolean enable;
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public UserAction(Bitmap bitmap)
	{
		super(bitmap);
		this.enable = true;
	}
}
