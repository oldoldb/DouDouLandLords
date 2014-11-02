package com.oldoldb.doudoulandlords;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Utils {

	public static boolean inTouchArea(int x, int y, int l, int t, int r, int b)
	{
		return x>=l && x<=r && y>=t && y<=b;
	}
	
	public static void drawCard(Card card, Canvas canvas, boolean isBack)
	{
		if(isBack){
			drawCardBack(card, canvas);
		} else {
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
		}
	}
	public static void drawCardBack(Card card , Canvas canvas)
	{
		canvas.drawBitmap(card.getBackBitmap(), card.getX(), card.getY(), null);
	}
	public static void drawAction(BaseAction baseAction, Canvas canvas)
	{
		canvas.drawBitmap(baseAction.getBitmap(), baseAction.getX(), baseAction.getY(), null);
	}
	public static void drawCards(List<Card> cards, Canvas canvas, boolean isBack)
	{
		for(Card card : cards){
			drawCard(card, canvas, isBack);
		}
	}
	
	public static void drawCardsInRange(List<Card> cards, Canvas canvas, int range, boolean isBack)
	{
		for(int i=0;i<range;i++){
			drawCard(cards.get(i), canvas, isBack);
		}
	}
	
	public static Bitmap getTargetSizeBitmap(Bitmap src, int targetWidth, int targetHeight)
	{
		return Bitmap.createScaledBitmap(src, targetWidth, targetHeight, true);
	}
	
	public static void shuffleCards(List<Card> cards)
	{
		int size = cards.size();
		Random random = new Random();
		for(int i=1;i<size;i++){
			int index = random.nextInt(i+1);
			if(index != i){
				Collections.swap(cards, index, i);
			}
		}
	}
}
