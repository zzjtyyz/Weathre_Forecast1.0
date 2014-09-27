package com.zzj.weathre_forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.zzj.weather_model.Weather;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ActionBar.OnNavigationListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity implements
		OnNavigationListener, BoradConstant {
	ViewPager pager; // �����ܹ����һ�����ViewPager�����FragmentActivityʹ��
	MyAdapter adapter; // �Զ����������̳�FragmentPagerAdapter
	ActionBar actionBar; // ����ActionBar
	// ����ViewPager�µ�3��ҳ�棬ΪV4����Fragment
	FirstFragment firstFragment;
	SecondFragment secondFragment;
	ThirdFragment thirdFragment;

	List<Fragment> fragments; // ����Fragment����
	private MyReceive myReceive; // ����㲥������
	SharedPreferences sp;
	// ���������Ԥ��������ʡ�ݣ���������actionbar����ʾ
	public static String[] citys = { "����", "�ӱ�", "����", "�Ĵ�", "����", "������", "����",
			"���", "����", "����", "���ɹ�", "�½�", "����", "���", "����", "����", "����", "����",
			"�ຣ", "�㽭", "�㶫", "����", "����", "����", "ɽ��", "����", "����", "�Ϻ�", "����",
			"����", "ɽ��" };
	public static String[] cityEn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.edit().putBoolean("start", false).commit();
		// ����XML�д�ļ���
		cityEn = getResources().getStringArray(R.array.city);
		// ����3��Fragment����˳����뼯��
		firstFragment = new FirstFragment();
		secondFragment = new SecondFragment();
		thirdFragment = new ThirdFragment();
		fragments = new ArrayList<>();
		fragments.add(firstFragment);
		fragments.add(secondFragment);
		fragments.add(thirdFragment);

		// ʵ����ViewPager��������
		pager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new MyAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		actionBar = getActionBar(); // ���actionbar
		actionBar.setDisplayShowTitleEnabled(true); // ����actionbar�������ʾ
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST); // Ϊactionbar���������б���ģʽ
		// actionbar�������б���������������
		ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_item, citys);

		// Ϊ�����б����ûص�����
		actionBar.setListNavigationCallbacks(listAdapter, this);
		// actionBar.setSelectedNavigationItem(21); //���������б�Ĭ��ѡ�к���

		// ����
		listAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ����Ԥ����ҳ����
		pager.setOffscreenPageLimit(2);
		// ���õ�ǰ��ʾҳ��
		pager.setCurrentItem(1);
		PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.tapStrip);
		tabStrip.setTabIndicatorColor(getResources().getColor(
				android.R.color.holo_orange_dark));
		// ������ʱע��㲥
		registerBroad();
		startService(new Intent(this, WeatherService.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 * 
	 * @Description: ��Activity����������һ�� ��onDestroy����ʱע���㲥
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(getApplication(), WeatherService.class);
		PendingIntent operation = PendingIntent.getService(
				getApplicationContext(), requestCode, intent, 0);
		am.cancel(operation);
		unregisterReceiver(myReceive);
	}

	/**
	 * 
	 * @Title: registerBroad
	 * @Description: Ϊ3��fragmentע��㲥������
	 * @return void
	 */
	private void registerBroad() {
		myReceive = new MyReceive();
		IntentFilter filter1 = new IntentFilter();
		filter1.addAction(FIRST_RECEIVE);
		filter1.addAction(SECOND_RECEIVE);
		filter1.addAction(THIRD_RECEIVE);
		filter1.addAction(CityService.ERROR);
		registerReceiver(myReceive, filter1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Ϊaction bar item���õ���¼�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, Settings.class));
			return true;
		} else if (id == R.id.action_refresh) {
			sp.edit().putString(PATH, LOCAL_PATH).putInt(INDEX, 21)
					.putString(CITY, "�Lɳ��").commit();
			actionBar.setTitle(citys[21]);
			startService(new Intent(this, WeatherService.class));
		} else if (id == R.id.action_about) {
			startActivity(new Intent(this, AboutActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * @ClassName: MyAdapter
	 * @Description: �Զ���FragmentPagerAdapter����������viwepager����
	 * @date 2014-9-21 ����9:48:57
	 * @author ���ӽ�
	 */
	class MyAdapter extends FragmentPagerAdapter {
		String[] titles = { "google", "apple", "microsoft" };

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return 3;
		}

		// PagerTitleStrip
		// PagerTabStrip
		// viewpage���ñ���
		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}

	class MyReceive extends BroadcastReceiver {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 * 
		 * @Description: �㲥���������ܷ��񷢹����Ĺ㲥�������
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d("action", action);
			if (action.equals(SECOND_RECEIVE)) {
				Weather weather = (Weather) intent
						.getSerializableExtra("weather");
				actionBar.setTitle(citys[sp.getInt(INDEX, 21)]);
				actionBar.setSubtitle(sp.getString(CITY, ""));
				ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) intent
						.getSerializableExtra(LIST);
				Picasso.with(context).load(weather.icon_url)
						.into(SecondFragment.weatherIcon);
				SecondFragment.temp.setText(sp.getString(TEMPERATURE, CELSIUS)
						.equals(CELSIUS) ? (weather.temp_c + "��")
						: (weather.temp_f + "��"));
				SecondFragment.weathertext.setText(weather.weahter);
				SecondFragment.hum.setText("HUMIDITY"
						+ weather.relative_humidity);
				String wind_speed = sp.getString(WIND, "km/h");
				if (wind_speed.equals("km/h")) {
					SecondFragment.wind.setText("WIND " + weather.wind_kph
							+ wind_speed + weather.wind_dir);
				} else {
					SecondFragment.wind.setText("WIND " + weather.wind_mph
							+ wind_speed + weather.wind_dir);
				}
				SecondFragment.adapter = new FourDayAdapter(context, list);
				SecondFragment.gridView.setAdapter(SecondFragment.adapter);
			} else if (action.equals(THIRD_RECEIVE)) {
				ThirdFragment.tens = (ArrayList<HashMap<String, Object>>) intent
						.getSerializableExtra(LIST);
				ThirdFragment.adapter = new TenAdapter(context,
						ThirdFragment.tens);
				ThirdFragment.listView.setAdapter(ThirdFragment.adapter);
			} else if (action.equals(FIRST_RECEIVE)) {
				ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) intent
						.getSerializableExtra(LIST);
				FirstFragment.adapter = new HourlyAdapter(context, list);
				FirstFragment.listView.setAdapter(FirstFragment.adapter);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.OnNavigationListener#onNavigationItemSelected(int,
	 * long)
	 * 
	 * @Description: actionbar�����б�������¼� �������actionbar���� �����չʾ��Ӧʡ�ݵ����г����б�
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		String city = cityEn[itemPosition];
		if (!sp.getBoolean("start", false)) {
			sp.edit().putBoolean("start", true).commit();
			return true;
		}
		if (city.equals("Tianjin") || city.equals("Jilin")
				|| city.equals("Henan") || city.equals("Shanghai")
				|| city.equals("Hong Kong")) {
			sp.edit().putInt(INDEX, itemPosition).putString(CITY, city)
					.putString(PATH, "/q/China/" + city + JSON_SUFFIX).commit();
			startService(new Intent(this, WeatherService.class));
		} else {
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(INDEX, itemPosition);
			startActivity(intent);
		}
		return true;
	}
}