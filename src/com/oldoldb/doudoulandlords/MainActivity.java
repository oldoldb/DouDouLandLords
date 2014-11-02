package com.oldoldb.doudoulandlords;

import android.content.Intent;
import android.os.Bundle;
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
        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_start_new_game:
			Intent intent = new Intent(MainActivity.this, GameActivity.class);
			startActivity(intent);
			break;
		case R.id.button_exit_game:
			finish();
			break;
		default:
			break;
		}
	}
    
}
