package com.oldoldb.doudoulandlords;

public class CardType {

	private CardColor.Color color;
	private int value;
	public CardColor.Color getColor() {
		return color;
	}

	public void setColor(CardColor.Color color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public CardType(CardColor.Color color, int value)
	{
		this.color = color;
		this.value = value;
	}
	
}
