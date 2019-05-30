package at.schiebung.stefan.schober0008;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity
		implements SharedPreferences.OnSharedPreferenceChangeListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		if (key.equals("pfc_music"))
		{
			Vars.bm = android.preference.PreferenceManager.getDefaultSharedPreferences(this)
														  .getBoolean("pfc_music", true);
			Sounds.MusicKill();
			Sounds.MusicCreate(this);
			Sounds.MusicResume();
		}
	}

	public static class PrefsFragment extends PreferenceFragmentCompat
	{

		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
		{
			// Load the preferences from an XML resource
			setPreferencesFromResource(R.xml.preferences, rootKey);
		}
	}
	@Override
	public void onResume()
	{
		super.onResume();
		Sounds.MusicResume();
		PreferenceManager.getDefaultSharedPreferences(this)
		                 .registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onPause()
	{
		super.onPause();
		Sounds.MusicStop();
		PreferenceManager.getDefaultSharedPreferences(this)
		                 .unregisterOnSharedPreferenceChangeListener(this);
	}

}

