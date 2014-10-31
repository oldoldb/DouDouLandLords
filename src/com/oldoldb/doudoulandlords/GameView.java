package com.oldoldb.doudoulandlords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.oldoldb.doudoulandlords.CardColor.Color;
import com.oldoldb.doudoulandlords.GameLogic.CombinationType;

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

	private static final int INVALID_INDEX = -1;
	private static final int INDEX_OF_PLAYER = 0;
	private static final int INDEX_OF_RIGHT = 1;
	private static final int INDEX_OF_LEFT = 2;
	private static final int INDEX_OF_NO = 0;
	private static final int INDEX_OF_YES = 1;
	private static final int INDEX_OF_NOTE = 2;
	private static final int DISPATCH_INTERVAL_TIME = 200;
	private static final int WAIT_AI_TIME = 1000;
	private static final int COLOR_NUM = 4;
	private static final int BASE_NUM = 17;
	private static final int PLAYER_NUM = 3;
	private static final int MIN_VALUE_CARD = 3;
	private static final int MAX_VALUE_CARD = 15;
	private static final int VALUE_OF_BLACK_JOKER = 16;
	private static final int VALUE_OF_RED_JOKER = 17;
	private Bitmap mBackCardBitmap;
	private Bitmap mBackgroundBitmap;
	private boolean mNeedDispatchCards = true;
	private int mIndexOfDispatchCard = 0;
	private int mIndexOfDispatchLeftCard = 0;
	private int mIndexOfDispatchRightCard = 0;
	private List<Card> mPlayerCards;
	private List<Card> mLeftCards;
	private List<Card> mRightCards; 
	private List<Card> mAllCards; 
	private List<Card> mPlayerPopCards;
	private List<Card> mLeftPopCards;
	private List<Card> mRightPopCards; 
	private List<Card> mLastPopCards; 
	private int mDisplayWidth;
	private int mDisplayHeight;
	private int mCardWidth;
	private int mCardHeight;
	private int mActionWidth;
	private int mActionHeight;
	private BaseAction mYesAction;
	private BaseAction mNoAction;
	private BaseAction mNoteAction;
	private boolean mNeedShowAction = false;
	private boolean mNeedShowPopCards = false;
	private GameAI mGameAI;
	private boolean mStartNewRound = true;
	private int mLastPopIndex;
	private GameLogic.CombinationType mLastPopType = CombinationType.NEWROUND;
	public GameView(Context context, int width, int height) {
		super(context);
		// TODO Auto-generated constructor stub
		initVariables(width, height);
		initBitmaps();
		initActions();
		initCards();
	}
	private void initCards()
	{
		String base = "a";
		for(int i=1;i<=COLOR_NUM;i++){
			String name = base + i + "_";
			for(int j=MIN_VALUE_CARD;j<=MAX_VALUE_CARD;j++){
				String temp = name + j;
				ApplicationInfo applicationInfo = getContext().getApplicationInfo();
				int id = getResources().getIdentifier(temp, "drawable", applicationInfo.packageName);
				mAllCards.add(new Card(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), id), mCardWidth, mCardHeight), new CardType(Color.values()[i], j)));
			}
		}
		mAllCards.add(new Card(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a5_16), mCardWidth, mCardHeight), new CardType(Color.None, VALUE_OF_BLACK_JOKER)));
		mAllCards.add(new Card(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a5_17), mCardWidth, mCardHeight), new CardType(Color.None, VALUE_OF_RED_JOKER)));
	}

	private void initStateForNewGame()
	{
		mIndexOfDispatchCard = 0;
		mIndexOfDispatchLeftCard = 0;
		mIndexOfDispatchRightCard = 0;
		mPlayerCards.clear();
		mLeftCards.clear();
		mRightCards.clear();
		mPlayerPopCards.clear();
		mLeftPopCards.clear();
		mRightPopCards.clear();
		mLastPopCards.clear();
		mLastPopType = CombinationType.NEWROUND;
		mLastPopIndex = INDEX_OF_PLAYER;
		clearCardsState();
	}
	private void initVariables(int width, int height)
	{
		mDisplayWidth = width;
		mDisplayHeight = height;
		mCardWidth = Math.min(mDisplayWidth * 2 / 27, mDisplayWidth / 12);
		mCardHeight = Math.min(mDisplayHeight / 6, mDisplayHeight * 2 / 27);
		mActionWidth = mDisplayWidth / 9;
		mActionHeight = mDisplayHeight / 9;
		mAllCards = new ArrayList<Card>();
		mPlayerPopCards = new ArrayList<Card>();
		mLeftPopCards = new ArrayList<Card>();
		mRightPopCards = new ArrayList<Card>();
		mPlayerCards = new ArrayList<Card>();
		mLeftCards = new ArrayList<Card>();
		mRightCards = new ArrayList<Card>();
		mLastPopCards = new ArrayList<Card>();
		mIndexOfDispatchCard = 0;
		mIndexOfDispatchLeftCard = 0;
		mIndexOfDispatchRightCard = 0;
		mLastPopIndex = INDEX_OF_PLAYER;
		mGameAI = GameAI.getInstance();
	}
	public void showDialog()
	{
		new AlertDialog.Builder(getContext())
		.setTitle("Start New Game")
		.setPositiveButton("Go", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startNewGame();
			}
		}).show();
	}
	private void clearCardsState()
	{
		for(Card card : mAllCards){
			card.setSelected(false);
		}
	}
	private void startNewGame()
	{
		setActionPosition();
		shuffleCards();
		fillInEachPlayer();
		sortCards();
		setCardPosition();
		dispatchCards(mPlayerCards.size());
	}
	private void fillInEachPlayer()
	{
		for(int i=0;i<BASE_NUM;i++){
			mPlayerCards.add(mAllCards.get(PLAYER_NUM * i));
			mLeftCards.add(mAllCards.get(PLAYER_NUM * i + 1));
			mRightCards.add(mAllCards.get(PLAYER_NUM * i + 2));
		}
	}
	private void initActions()
	{
		mYesAction = new BaseAction(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_yes), mActionWidth, mActionHeight));
		mNoAction = new BaseAction(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_no), mActionWidth, mActionHeight));
		mNoteAction = new BaseAction(Utils.getTargetSizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.action_note), mActionWidth, mActionHeight));
	}
	private void initBitmaps()
	{
		mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		mBackgroundBitmap = Bitmap.createScaledBitmap(mBackgroundBitmap, mDisplayWidth, mDisplayHeight, true);
		mBackCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardbg1);
	}
	public void dispatchCards(int sum)
	{
		mNeedDispatchCards = true;
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
				mNeedDispatchCards = false;
				mNeedShowAction = true;
				invalidate();
			}
		}.start();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		drawBackground(canvas);
		if(mNeedDispatchCards){
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
		if(mNeedShowPopCards){
			drawPopCards(canvas);
		}
	}
	
	private boolean isGameOver()
	{
		return mPlayerCards.isEmpty() || mLeftCards.isEmpty() || mRightCards.isEmpty();
	}

	private void drawPopCards(Canvas canvas)
	{
		Utils.drawCards(mPlayerPopCards, canvas);
		Utils.drawCards(mLeftPopCards, canvas);
		Utils.drawCards(mRightPopCards, canvas);
	}
	private void drawAction(Canvas canvas)
	{
		Utils.drawAction(mYesAction, canvas);
		Utils.drawAction(mNoAction, canvas);
		Utils.drawAction(mNoteAction, canvas);
	}
	private void drawPlayerCards(int range, Canvas canvas)
	{
		Utils.drawCardsInRange(mPlayerCards, canvas, range);
	}
	
	private void drawLeftCards(int range, Canvas canvas)
	{
		Utils.drawCardsInRange(mLeftCards, canvas, range);
	}
	
	private void drawRightCards(int range, Canvas canvas)
	{
		Utils.drawCardsInRange(mRightCards, canvas, range);
	}
	private void drawBackground(Canvas canvas)
	{
		canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
	}	
	private void shuffleCards()
	{
		Utils.shuffleCards(mAllCards);
	}
	
	private void sortCards()
	{
		Collections.sort(mPlayerCards);
		Collections.sort(mLeftCards);
		Collections.sort(mRightCards);
	}
	
	private void setCardPosition()
	{
		setPlayerCardsPosition();
		setLeftCardsPosition();
		setRightCardsPosition();
	}

	private void setPlayerCardsPosition()
	{
		int size = mPlayerCards.size();
		int startX = (mDisplayWidth - (size + 1) / 2  * mCardWidth) / 2;
		int startY = (mDisplayHeight - 2 * mCardHeight);
		for(int i=0;i<size;i++){
			Card card = mPlayerCards.get(i);
			card.setX(startX + i * card.getWidth() / 2);
			card.setY(startY);
		}
	}
	private void setLeftCardsPosition()
	{
		int size = mLeftCards.size();
		int startX = mCardWidth;
		int startY = (mDisplayHeight - (size + 1) / 2 * mCardHeight) / 2;
		for(int i=0;i<size;i++){
			Card card = mLeftCards.get(i);
			card.setX(startX);
			card.setY(startY + i * card.getHeight() / 2);
		}
	}
	private void setRightCardsPosition()
	{
		int size = mRightCards.size();
		int startX = mDisplayWidth - 2 * mCardWidth;
		int startY = (mDisplayHeight - (size + 1) / 2 * mCardHeight) / 2;
		for(int i=0;i<size;i++){
			Card card = mRightCards.get(i);
			card.setX(startX);
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
		int index = getTouchCardsIndex(event);
		if(index != INVALID_INDEX){
			moveTouchCard(mPlayerCards.get(index));
			return ;
		} 
		index = getTouchActionIndex(event);
		if(index != INVALID_INDEX){
			handleTouchActionEvent(index);
			return ;
		}
	}
	
	private void handleTouchActionEvent(int index)
	{
		if(index == INDEX_OF_NO){
			restoreTouchCards();
			mPlayerPopCards.clear();
		} else if(index == INDEX_OF_YES){
			List<Card> selectedCards = getSelectedCards();
			if(GameLogic.isMeetLogic(mLastPopType, mLastPopCards, selectedCards) || mLastPopIndex == INDEX_OF_PLAYER){
				popSelectedCards();
				mLastPopIndex = INDEX_OF_PLAYER;
			} else{
				restoreTouchCards();
				invalidate();
			}
		}
		inAITurn();
	}
	private List<Card> getSelectedCards()
	{
		List<Card> selectedCards = new ArrayList<Card>();
		int size = mPlayerCards.size();
		for(int i=0;i<size;i++){
			Card card = mPlayerCards.get(i);
			if(card.isSelected()){
				selectedCards.add(card);
			}
		}
		return selectedCards;
	}
	private void popSelectedCards()
	{
		mPlayerPopCards.clear();
		int size = mPlayerCards.size();
		for(int i=0;i<size;i++){
			Card card = mPlayerCards.get(i);
			if(card.isSelected()){
				mPlayerPopCards.add(card);
				mPlayerCards.remove(i);
				size--;
				i--;
			}
		}
		setPlayerPopCardsPosition();
		setPlayerCardsPosition();
		mNeedShowPopCards = true;
		invalidate();
		if(isGameOver()){
			initStateForNewGame();
			startNewGame();
			return ;
		}
		if(GameLogic.getCardsType(mPlayerPopCards) != CombinationType.NONE){
			mLastPopType = GameLogic.getCardsType(mPlayerPopCards);
			mLastPopCards.clear();
			mLastPopCards.addAll(mPlayerPopCards);
		}
	}
	
	private void inAITurn()
	{
		new CountDownTimer(WAIT_AI_TIME, WAIT_AI_TIME) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				handleRightTurn();
				new CountDownTimer(WAIT_AI_TIME, WAIT_AI_TIME) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						handleLeftTurn();
					}
				}.start();
			}
		}.start();
	}
	private void handleLeftTurn()
	{
		mLeftPopCards.clear();
		if(mLastPopIndex == INDEX_OF_LEFT){
			mLastPopType = CombinationType.NEWROUND;
		}
		getLeftPopCards();
		setLeftPopCardsPosition();
		setLeftCardsPosition();
		mNeedShowPopCards = true;
		invalidate();
		if(GameLogic.getCardsType(mLeftPopCards) != CombinationType.NONE){
			mLastPopType = GameLogic.getCardsType(mLeftPopCards);
			mLastPopCards.clear();
			mLastPopCards.addAll(mLeftPopCards);
			mLastPopIndex = INDEX_OF_LEFT;
		}
	}
	private void getLeftPopCards()
	{
		mGameAI.getAIPopCards(mLastPopType, mLastPopCards, mLeftCards, mLeftPopCards);
	}
	private void setLeftPopCardsPosition()
	{
		int size = mLeftPopCards.size();
		int startX = 3 * mCardWidth;
		int startY = (mDisplayHeight - (size + 1) * mCardHeight / 2) / 2;
		for(int i=0;i<size;i++){
			Card card = mLeftPopCards.get(i);
			card.setX(startX);
			card.setY(startY + i * card.getHeight() / 2);
		}
	}
	private void getRightPopCards()
	{
		mGameAI.getAIPopCards(mLastPopType, mLastPopCards, mRightCards, mRightPopCards);
	}
	private void handleRightTurn()
	{
		mRightPopCards.clear();
		if(mLastPopIndex == INDEX_OF_RIGHT){
			mLastPopType = CombinationType.NEWROUND;
		}
		getRightPopCards();
		setRightPopCardsPosition();
		setRightCardsPosition();
		mNeedShowPopCards = true;
		invalidate();
		if(GameLogic.getCardsType(mRightPopCards) != CombinationType.NONE){
			mLastPopType = GameLogic.getCardsType(mRightPopCards);
			mLastPopCards.clear();
			mLastPopCards.addAll(mRightPopCards);
			mLastPopIndex = INDEX_OF_RIGHT;
		}
	}
	
	private void setRightPopCardsPosition()
	{
		int size = mRightPopCards.size();
		int startX = (mDisplayWidth - 4 * mCardWidth);
		int startY = (mDisplayHeight - (size + 1) * mCardHeight / 2) / 2;
		for(int i=0;i<size;i++){
			Card card = mRightPopCards.get(i);
			card.setX(startX);
			card.setY(startY + i * card.getHeight() / 2);
		}
	}
	private void setPlayerPopCardsPosition()
	{
		int size = mPlayerPopCards.size();
		int startX = (mDisplayWidth - (size + 1)* mCardWidth / 2 ) / 2;
		int startY = mDisplayHeight - 4 * mCardHeight;
		for(int i=0;i<size;i++){
			Card card = mPlayerPopCards.get(i);
			card.setX(startX + i * card.getWidth() / 2);
			card.setY(startY);
		}
	}
	private int getTouchActionIndex(MotionEvent event)
	{
		int index = INVALID_INDEX;
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(Utils.inTouchArea(x, y, mNoAction.getX(), mNoAction.getY(), mNoAction.getX() + mNoAction.getWidth(), mNoAction.getY() + mNoAction.getHeight())){
			index = INDEX_OF_NO;
		} else if(Utils.inTouchArea(x, y, mYesAction.getX(), mYesAction.getY(), mYesAction.getX() + mYesAction.getWidth(), mYesAction.getY() + mYesAction.getHeight())){
			index = INDEX_OF_YES;
		} else if(Utils.inTouchArea(x, y, mNoteAction.getX(), mNoteAction.getY(), mNoteAction.getX() + mNoteAction.getWidth(), mNoteAction.getY() + mNoteAction.getHeight())){
			index = INDEX_OF_NOTE;
		}
		return index;
	}
	private int getTouchCardsIndex(MotionEvent event)
	{
		int x = (int) event.getX();
		int y = (int) event.getY();
		int size = mPlayerCards.size();
		int index = INVALID_INDEX;
		for(int i=0;i<size;i++){
			if(isTouchInCard(mPlayerCards.get(i), x, y)){
				index = i;
				break;
			}
		}
		return index;
	}
	private boolean isTouchInCard(Card card, int x, int y)
	{
		int l = card.getX();
		int t = card.getY();
		int r = l + card.getWidth() / 2;
		int b = t + card.getHeight() / 2;
		return Utils.inTouchArea(x, y, l, t, r, b);
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

	private void restoreTouchCards()
	{
		int size = mPlayerCards.size();
		for(int i=0;i<size;i++){
			Card card = mPlayerCards.get(i);
			if(card.isSelected()){
				card.setSelected(false);
				card.setY(card.getY() + card.getHeight() / 2);
			}
		}
		invalidate();
	}
}
