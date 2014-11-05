package model;

import android.graphics.Bitmap;

public class UserAction extends BaseAction{

	private boolean enable;
	private Bitmap touchBitmap;
	public Bitmap getTouchBitmap() {
		return touchBitmap;
	}
	public void setTouchBitmap(Bitmap touchBitmap) {
		this.touchBitmap = touchBitmap;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public UserAction(Bitmap bitmap, Bitmap touchBitmap)
	{
		super(bitmap);
		this.touchBitmap = touchBitmap;
		this.enable = true;
	}
}
