package com.oldoldb.doudoulandlords;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DrawTool {

	private  static DrawTool instance = null;
	
	private DrawTool()
	{
		
	}
	
	public static DrawTool getInstance()
	{
		if(instance == null){
			synchronized (DrawTool.class) {
				if(instance == null){
					instance = new DrawTool();
				}
			}
		}
		return instance;
	}
	
	public void drawDisableAction(BaseAction action, Canvas canvas)
	{
		drawAction(action, canvas);
	}
	public void drawPopCards(Canvas canvas, List<Card> playerPopCards, List<Card> leftPopCards, List<Card> rightPopCards)
	{
		drawCards(playerPopCards, canvas, false);
		drawCards(leftPopCards, canvas, false);
		drawCards(rightPopCards, canvas, false);
	}
	public void drawAction(Canvas canvas, UserAction yesAction, UserAction noAction, UserAction noteAction)
	{
		drawAction(yesAction, canvas);
		drawAction(noAction, canvas);
		drawAction(noteAction, canvas);
	}
	public void drawPlayerCards(int range, Canvas canvas, List<Card> playerCards)
	{
		drawCardsInRange(playerCards, canvas, range, false);
	}
	
	public void drawLeftCards(int range, Canvas canvas, List<Card> leftPopCards)
	{
		drawCardsInRange(leftPopCards, canvas, range, true);
	}
	
	public void drawRightCards(int range, Canvas canvas, List<Card> rightPopCards)
	{
		drawCardsInRange(rightPopCards, canvas, range, true);
	}
	public void drawBackground(Canvas canvas, Bitmap backgroundBitmap)
	{
		canvas.drawBitmap(backgroundBitmap, 0, 0, null);
	}	
	
	private void drawCard(Card card, Canvas canvas, boolean isBack)
	{
		if(isBack){
			drawCardBack(card, canvas);
		} else {
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
		}
	}
	private void drawCardBack(Card card , Canvas canvas)
	{
		canvas.drawBitmap(card.getBackBitmap(), card.getX(), card.getY(), null);
	}
	private void drawAction(BaseAction baseAction, Canvas canvas)
	{
		canvas.drawBitmap(baseAction.getBitmap(), baseAction.getX(), baseAction.getY(), null);
	}
	private void drawCards(List<Card> cards, Canvas canvas, boolean isBack)
	{
		for(Card card : cards){
			drawCard(card, canvas, isBack);
		}
	}
	
	private void drawCardsInRange(List<Card> cards, Canvas canvas, int range, boolean isBack)
	{
		for(int i=0;i<range;i++){
			drawCard(cards.get(i), canvas, isBack);
		}
	}
	
	public Bitmap getTargetSizeBitmap(Bitmap src, int targetWidth, int targetHeight)
	{
		return Bitmap.createScaledBitmap(src, targetWidth, targetHeight, true);
	}
}
