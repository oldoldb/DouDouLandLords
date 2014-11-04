package com.oldoldb.doudoulandlords;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.landlords_enter_view);
        Button startButton = (Button)findViewById(R.id.button_start_new_game);
        Button exitButton = (Button)findViewById(R.id.button_exit_game);
        Button settingButton = (Button)findViewById(R.id.button_setting_game);
        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        Utils.startBackMusic(this, R.raw.enter_music);
    }
    
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_start_new_game:
			Utils.stopBackMusic();
			Intent intent = new Intent(MainActivity.this, GameActivity.class);
			startActivity(intent);
			break;
		case R.id.button_setting_game:
			Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
			startActivity(intent2);
			break;
		case R.id.button_exit_game:
			Utils.stopBackMusic();
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Utils.startBackMusic(this, R.raw.enter_music);
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Utils.stopBackMusic();
		super.onPause();
	}
}
