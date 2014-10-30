package com.oldoldb.doudoulandlords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.oldoldb.doudoulandlords.CardColor.Color;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View{

	private Bitmap mBackCardBitmap;
	private Bitmap mBackgroundBitmap;
	private boolean mFirstLaunch = true;
	private boolean mDispatchCards = false;
	private int mIndexOfDispatchCard = 0;
	private int mIndexOfDispatchLeftCard = 0;
	private int mIndexOfDispatchRightCard = 0;
	private List<Card> mPlayerCards;
	private List<Card> mLeftCards;
	private List<Card> mRightCards; 
	private List<Card> mAllCards; 
	private int mDisplayWidth;
	private int mDisplayHeight;
	private Context mContext;
	public GameView(Context context, int width, int height) {
		super(context);
		mDisplayWidth = width;
		mDisplayHeight = height;
		// TODO Auto-generated constructor stub
		initBitmaps();
		initCards();
	}

	public void showDialog()
	{
		new AlertDialog.Builder(getContext())
		.setTitle("Start New Game")
		.setPositiveButton("Go", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mFirstLaunch = true;
				dispatchCards(mPlayerCards.size());
			}
		}).show();
	}
	private void initBitmaps()
	{
		mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		mBackCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardbg1);
	}
	public void dispatchCards(int sum)
	{
		mDispatchCards = true;
		new CountDownTimer(200 * (sum + 2), 200) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if(mIndexOfDispatchCard <= mPlayerCards.size()){
					invalidate();
				}
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				mIndexOfDispatchCard = 0;
				mIndexOfDispatchLeftCard = 0;
				mIndexOfDispatchRightCard = 0;
			}
		}.start();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		drawBackground(canvas);
		if(mDispatchCards){
			for(int i=0;i<mIndexOfDispatchCard;i++){
				drawDispatchCard(mPlayerCards.get(i), canvas);
				drawDispatchBackCard(mLeftCards.get(i), canvas);
				drawDispatchBackCard(mRightCards.get(i), canvas);
			}
			mIndexOfDispatchCard ++;
			mIndexOfDispatchLeftCard ++;
			mIndexOfDispatchRightCard ++;
		}
	}

	private void drawBackground(Canvas canvas)
	{
		mBackgroundBitmap = Bitmap.createScaledBitmap(mBackgroundBitmap, canvas.getWidth(), canvas.getHeight(), true);
		canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
	}
	private void drawDispatchCard(Card card, Canvas canvas)
	{
		canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
	}
	private void drawDispatchBackCard(Card card, Canvas canvas)
	{
		canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
	}
	
	private void shuffleCards()
	{
		int size = mAllCards.size();
		Random random = new Random();
		for(int i=1;i<size;i++){
			int index = random.nextInt(i+1);
			if(index != i){
				Collections.swap(mAllCards, index, i);
			}
		}
	}
	private void initCards()
	{
		mAllCards = new ArrayList<Card>();
		String base = "a";
		for(int i=1;i<=4;i++){
			String name = base + i + "_";
			for(int j=3;j<=15;j++){
				String temp = name + j;
				ApplicationInfo applicationInfo = getContext().getApplicationInfo();
				int id = getResources().getIdentifier(temp, "drawable", applicationInfo.packageName);
				mAllCards.add(new Card(BitmapFactory.decodeResource(getResources(), id), new CardType(Color.values()[i], j)));
			}
		}
		mAllCards.add(new Card(BitmapFactory.decodeResource(getResources(), R.drawable.a5_16), new CardType(Color.None, 16)));
		mAllCards.add(new Card(BitmapFactory.decodeResource(getResources(), R.drawable.a5_17), new CardType(Color.None, 17)));
		
		for(int i=0;i<54;i++){
			TestUtils.showLog(mAllCards.get(i).getCardType().getValue() + " , " + mAllCards.get(i).getCardType().getColor().ordinal());
		}
		shuffleCards();
		
		mPlayerCards = new ArrayList<Card>();
		mLeftCards = new ArrayList<Card>();
		mRightCards = new ArrayList<Card>();
		
		for(int i=0;i<17;i++){
			mPlayerCards.add(mAllCards.get(3*i));
			mLeftCards.add(mAllCards.get(3*i+1));
			mRightCards.add(mAllCards.get(3*i+2));
		}
		
		sortCards();
		setCardPosition();
	}
	
	private void sortCards()
	{
		Collections.sort(mPlayerCards);
		Collections.sort(mLeftCards);
		Collections.sort(mRightCards);
	}
	
	private void setCardPosition()
	{
		int startX = (mDisplayWidth - 9 * mPlayerCards.get(0).getWidth()) / 2;
		int startY = (mDisplayHeight - 2 * mPlayerCards.get(0).getHeight());
		for(int i=0;i<17;i++){
			Card card = mPlayerCards.get(i);
			card.setX(startX + i * card.getWidth() / 2);
			card.setY(startY);
		}
		
		startX = (mLeftCards.get(0).getWidth());
		int startX2 = mDisplayWidth - 2 * mRightCards.get(0).getWidth();
		startY = (mDisplayHeight - 9 * mPlayerCards.get(0).getHeight()) / 2;
		for(int i=0;i<17;i++){
			Card card = mLeftCards.get(i);
			card.setX(startX);
			card.setY(startY + i * card.getHeight() / 2);
			card = mRightCards.get(i);
			card.setX(startX2);
			card.setY(startY + i * card.getHeight() / 2);
		}
	}

}
