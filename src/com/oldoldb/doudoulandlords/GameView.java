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

	private static final int DISPATCH_INTERVAL_TIME = 200;
	private Bitmap mBackCardBitmap;
	private Bitmap mBackgroundBitmap;
	private boolean mFirstLaunch = true;
	private boolean mDispatchCards = true;
	private int mIndexOfDispatchCard = 0;
	private int mIndexOfDispatchLeftCard = 0;
	private int mIndexOfDispatchRightCard = 0;
	private List<Card> mPlayerCards;
	private List<Card> mLeftCards;
	private List<Card> mRightCards; 
	private List<Card> mAllCards; 
	private int mDisplayWidth;
	private int mDisplayHeight;
	private int mPlayerCardWidth;
	private int mPlayerCardHeight;
	private int mActionWidth;
	private int mActionHeight;
	private BaseAction mYesAction;
	private BaseAction mNoAction;
	private BaseAction mNoteAction;
	private boolean mNeedShowAction = false;
	public GameView(Context context, int width, int height) {
		super(context);
		mDisplayWidth = width;
		mDisplayHeight = height;
		mPlayerCardWidth = Math.min(mDisplayWidth * 2 / 27, mDisplayWidth / 12);
		mPlayerCardHeight = Math.min(mDisplayHeight / 6, mDisplayHeight * 2 / 27);
		mActionWidth = mDisplayWidth / 9;
		mActionHeight = mDisplayHeight / 9;

		// TODO Auto-generated constructor stub
		initBitmaps();
		initCards();
		initActions();
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
	private void initActions()
	{
		mYesAction = new BaseAction(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_yes), mActionWidth, mActionHeight));
		mNoAction = new BaseAction(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_no), mActionWidth, mActionHeight));
		mNoteAction = new BaseAction(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_note), mActionWidth, mActionHeight));
		setActionPosition();
	}
	private void initBitmaps()
	{
		mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		mBackCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardbg1);
	}
	public void dispatchCards(int sum)
	{
		mDispatchCards = true;
		new CountDownTimer(DISPATCH_INTERVAL_TIME * (sum + 2), DISPATCH_INTERVAL_TIME) {
			
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
				mDispatchCards = false;
				mNeedShowAction = true;
				invalidate();
			}
		}.start();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		drawBackground(canvas);
		if(mDispatchCards){
			drawPlayerCards(mIndexOfDispatchCard, canvas);
			drawLeftCards(mIndexOfDispatchLeftCard, canvas);
			drawRightCards(mIndexOfDispatchRightCard, canvas);
			mIndexOfDispatchCard ++;
			mIndexOfDispatchLeftCard ++;
			mIndexOfDispatchRightCard ++;
		} else{
			drawPlayerCards(mPlayerCards.size(), canvas);
			drawLeftCards(mLeftCards.size(), canvas);
			drawRightCards(mRightCards.size(), canvas);
		}
		if(mNeedShowAction){
			drawAction(canvas);
		}
	}

	private void drawAction(Canvas canvas)
	{
		TestUtils.showLog(mYesAction.getX() + " , " + mNoteAction.getX() + " , " +mNoAction.getX() + " , " + mNoAction.getWidth());
		canvas.drawBitmap(mYesAction.getBitmap(), mYesAction.getX(), mYesAction.getY(), null);
		canvas.drawBitmap(mNoteAction.getBitmap(), mNoteAction.getX(), mNoteAction.getY(), null);
		canvas.drawBitmap(mNoAction.getBitmap(), mNoAction.getX(), mNoAction.getY(), null);
	}
	private void drawPlayerCards(int range, Canvas canvas)
	{
		for(int i=0;i<range;i++){
			drawDispatchCard(mPlayerCards.get(i), canvas);
		}
	}
	
	private void drawLeftCards(int range, Canvas canvas)
	{
		for(int i=0;i<range;i++){
			drawDispatchBackCard(mLeftCards.get(i), canvas);
		}
	}
	
	private void drawRightCards(int range, Canvas canvas)
	{
		for(int i=0;i<range;i++){
			drawDispatchBackCard(mRightCards.get(i), canvas);
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
	private Bitmap getTargetSizeBitmap(Bitmap src, int targetWidth, int targetHeight)
	{
		return Bitmap.createScaledBitmap(src, targetWidth, targetHeight, true);
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
				mAllCards.add(new Card(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), id), mPlayerCardWidth, mPlayerCardHeight), new CardType(Color.values()[i], j)));
			}
		}
		mAllCards.add(new Card(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a5_16), mPlayerCardWidth, mPlayerCardHeight), new CardType(Color.None, 16)));
		mAllCards.add(new Card(getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a5_17), mPlayerCardWidth, mPlayerCardHeight), new CardType(Color.None, 17)));
		
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

	private void setActionPosition()
	{
		int middleX = mDisplayWidth / 2;
		int middleY = mDisplayHeight - 4 * mActionHeight;
		mYesAction.setX(middleX - mYesAction.getWidth() / 2);
		mYesAction.setY(middleY);
		mNoteAction.setX(middleX + mNoteAction.getWidth() / 2);
		mNoteAction.setY(middleY);
		mNoAction.setX(middleX - mNoAction.getWidth() * 3 / 2);
		mNoAction.setY(middleY);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			TestUtils.showLog("ACTION_DOWN");
			break;
		case MotionEvent.ACTION_UP:
			TestUtils.showLog("ACTION_UP");
			handleActionUpEvent(event);
			break;
		default:
			break;
		}
		return true;
	}
	
	private void handleActionUpEvent(MotionEvent event)
	{
		int x = (int) event.getX();
		int y = (int) event.getY();
		int size = mPlayerCards.size();
		int index = -1;
		for(int i=0;i<size;i++){
			if(isTouchInCard(mPlayerCards.get(i), x, y)){
				index = i;
				break;
			}
		}
		if(index == -1){
			return ;
		}
		moveTouchCard(mPlayerCards.get(index));
	}
	
	private boolean isTouchInCard(Card card, int x, int y)
	{
		int l = card.getX();
		int t = card.getY();
		int r = l + card.getWidth() / 2;
		int b = t + card.getHeight() / 2;
		if(x>=l && x<=r && y>=t && y<=b){
			return true;
		}
		return false;
	}
	
	private void moveTouchCard(Card card)
	{
		if(card.isSelected()){
			card.setY(card.getY() + card.getHeight() / 2);
		} else {
			card.setY(card.getY() - card.getHeight() / 2);
		}
		card.setSelected(!card.isSelected());
		invalidate();
	}

	
}
