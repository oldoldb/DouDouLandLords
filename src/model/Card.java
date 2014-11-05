package model;

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
	
	public Card(Bitmap bitmap, Bitmap backBitmap, CardType cardType)
	{
		super(bitmap, backBitmap, cardType);
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Card card = new Card(bitmap, backBitmap, cardType);
		card.width = card.getBitmap().getWidth();
		card.height = card.getBitmap().getHeight();
		return card;
	}
	
}
