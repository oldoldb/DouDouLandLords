package com.oldoldb.doudoulandlords;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

public class GameActivity extends Activity {

	private GameView mGameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mGameView = new GameView(this, dm.widthPixels, dm.heightPixels);
		setContentView(mGameView);
		mGameView.showDialog();
	}

	
}
