package game;

import com.oldoldb.doudoulandlords.R;

import utils.Utils;
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
		Utils.startBackMusic(this, R.raw.game_music);
		mGameView.startNewGame();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Utils.startBackMusic(this, R.raw.game_music);
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Utils.stopBackMusic();
		super.onPause();
	}
	
	
}
