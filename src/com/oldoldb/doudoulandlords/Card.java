package com.oldoldb.doudoulandlords;

import android.R.id;
import android.graphics.Bitmap;

public class Card extends BaseCard{

	private int width;
	private int height;
	private int x;
	private int y;
	private boolean isSelected;
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
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
	
	public Card(Bitmap bitmap, CardType cardType)
	{
		super(bitmap, cardType);
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
}
