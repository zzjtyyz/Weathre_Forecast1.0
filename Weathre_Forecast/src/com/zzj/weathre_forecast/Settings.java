package com.zzj.weathre_forecast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.replace(android.R.id.content, new SettingsFragment())
					.commit();
		}
	}

	class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener, BoradConstant {

		private ListPreference temp;
		private ListPreference wind_speed;
		private ListPreference rainfall;
		private ListPreference update;
		private CheckBoxPreference cb;
		private SharedPreferences sp;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);
			sp = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			temp = (ListPreference) findPreference(TEMPERATURE);
			temp.setSummary(sp.getString(TEMPERATURE, CELSIUS));
			wind_speed = (ListPreference) findPreference(WIND);
			wind_speed.setSummary(sp.getString(WIND, "km/h"));
			rainfall = (ListPreference) findPreference(RAINFALL);
			rainfall.setSummary(sp.getString(RAINFALL, "mm"));
			update = (ListPreference) findPreference(UPDATE);
			update.setSummary(sp.getString(UPDATE, "1 hour"));
			cb = (CheckBoxPreference) findPreference(SWITCH);
			temp.setOnPreferenceChangeListener(this);
			wind_speed.setOnPreferenceChangeListener(this);
			rainfall.setOnPreferenceChangeListener(this);
			update.setOnPreferenceChangeListener(this);
			cb.setOnPreferenceChangeListener(this);
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			Editor edit = sp.edit();
			System.out.println(newValue);
			if (preference instanceof ListPreference) {
				edit.putString(preference.getKey(), (String) newValue);
				preference.setSummary((CharSequence) newValue);
				// 设置更新时间
				if (preference == update) {
					// 获取系统闹铃管理处理更新操作
					AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
					Intent intent = new Intent(getApplication(),
							WeatherService.class);
					PendingIntent operation = PendingIntent.getService(
							getApplicationContext(), requestCode, intent, 0);
					am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0,
							Long.parseLong(newValue.toString()), operation);
				}
			} else if (preference instanceof CheckBoxPreference) {
				edit.putBoolean(preference.getKey(), (boolean) newValue);
			}
			edit.commit();
			startService(new Intent(getApplicationContext(),
					WeatherService.class));
			return true;
		}

	}
}
