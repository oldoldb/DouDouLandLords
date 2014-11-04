package com.oldoldb.doudoulandlords;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;


public class Utils {

	private static MediaPlayer mPlayer;
	public static boolean inTouchArea(int x, int y, int l, int t, int r, int b)
	{
		return x>=l && x<=r && y>=t && y<=b;
	}
	public static void startBackMusic(Context context, int resId)
    {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		boolean flag = true;
		if(resId == R.raw.enter_music){
			flag = sharedPreferences.getBoolean(context.getString(R.string.pref_enter_music_key), true);
		} else if(resId == R.raw.game_music){
			flag = sharedPreferences.getBoolean(context.getString(R.string.pref_game_music_key), true);
		}
		if(!flag){
			return ;
		}
		if(mPlayer != null && mPlayer.isPlaying()){
			return ;
		}
    	mPlayer = MediaPlayer.create(context, resId);
        if(mPlayer != null){
        	mPlayer.start();
        }
    }
	public static void stopBackMusic()
    {
    	if(mPlayer != null){
    		mPlayer.stop();
    		mPlayer.release();
    		mPlayer = null;
    	}
    }
	
}
