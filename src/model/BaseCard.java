package model;

import model.CardColor.Color;
import android.graphics.Bitmap;

public class BaseCard implements Comparable<BaseCard>{

	protected Bitmap bitmap;
	protected Bitmap backBitmap;
	public Bitmap getBackBitmap() {
		return backBitmap;
	}
	public void setBackBitmap(Bitmap backBitmap) {
		this.backBitmap = backBitmap;
	}
	protected CardType cardType;
	protected int sortValue;
	public int getSortValue() {
		return sortValue;
	}
	public void setSortValue(int sortValue) {
		this.sortValue = sortValue;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	public BaseCard(Bitmap bitmap, Bitmap backBitmap, CardType cardType)
	{
		this.bitmap = bitmap;
		this.backBitmap = backBitmap;
		this.cardType = cardType;
		this.sortValue = calculateSortValue(cardType);
	}
	private int calculateSortValue(CardType cardType)
	{
		if(cardType.getColor() == Color.None){
			if(cardType.getValue() == 16){
				return 64;
			} else{
				return 65;
			}
		}else{
			return cardType.getValue() * 4 + cardType.getColor().ordinal() % 4;
		}
	}
	
	
	@Override
	public int compareTo(BaseCard another) {
		// TODO Auto-generated method stub
		return -(another.getSortValue() - this.sortValue);
	}
}
