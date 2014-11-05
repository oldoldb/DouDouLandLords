package prefs;

import com.oldoldb.doudoulandlords.R;

import utils.Utils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingActivity extends Activity implements OnSharedPreferenceChangeListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_view);
		getFragmentManager().beginTransaction()
		.replace(R.id.content, new SettingsFragment())
		.commit();
	}
	
	public static class SettingsFragment extends PreferenceFragment
	{
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}	
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		boolean flag = sharedPreferences.getBoolean(key, true);
		if(!flag){
			Utils.stopBackMusic();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Utils.startBackMusic(this, R.raw.setting_music);
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Utils.stopBackMusic();
		super.onPause();
	}
}
