package com.oldoldb.doudoulandlords;

import android.graphics.Bitmap;

public class BaseAction {
	protected Bitmap bitmap;
	protected int x;
	protected int y;
	protected int width;
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	private int height;
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public BaseAction(Bitmap bitmap)
	{
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
}
