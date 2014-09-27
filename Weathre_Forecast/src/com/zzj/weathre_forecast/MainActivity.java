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
	ViewPager pager; // 定义能够左右滑动的ViewPager，配合FragmentActivity使用
	MyAdapter adapter; // 自定义适配器继承FragmentPagerAdapter
	ActionBar actionBar; // 定义ActionBar
	// 定义ViewPager下的3个页面，为V4包的Fragment
	FirstFragment firstFragment;
	SecondFragment secondFragment;
	ThirdFragment thirdFragment;

	List<Fragment> fragments; // 定义Fragment集合
	private MyReceive myReceive; // 定义广播接收器
	SharedPreferences sp;
	// 定义的天气预报的所有省份，会设置在actionbar下显示
	public static String[] citys = { "安徽", "河北", "吉林", "四川", "北京", "黑龙江", "辽宁",
			"天津", "重庆", "河南", "内蒙古", "新疆", "福建", "香港", "宁夏", "云南", "甘肃", "湖北",
			"青海", "浙江", "广东", "湖南", "陕西", "广西", "山东", "贵州", "江苏", "上海", "海南",
			"江西", "山西" };
	public static String[] cityEn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.edit().putBoolean("start", false).commit();
		// 加载XML中存的集合
		cityEn = getResources().getStringArray(R.array.city);
		// 创建3个Fragment并按顺序放入集合
		firstFragment = new FirstFragment();
		secondFragment = new SecondFragment();
		thirdFragment = new ThirdFragment();
		fragments = new ArrayList<>();
		fragments.add(firstFragment);
		fragments.add(secondFragment);
		fragments.add(thirdFragment);

		// 实例化ViewPager，适配器
		pager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new MyAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		actionBar = getActionBar(); // 获得actionbar
		actionBar.setDisplayShowTitleEnabled(true); // 设置actionbar标题可显示
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST); // 为actionbar设置下拉列表导航模式
		// actionbar的下拉列表设置数组适配器
		ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_item, citys);

		// 为下拉列表设置回调方法
		actionBar.setListNavigationCallbacks(listAdapter, this);
		// actionBar.setSelectedNavigationItem(21); //设置下拉列表默认选中湖南

		// 设置
		listAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 设置预加载页面数
		pager.setOffscreenPageLimit(2);
		// 设置当前显示页面
		pager.setCurrentItem(1);
		PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.tapStrip);
		tabStrip.setTabIndicatorColor(getResources().getColor(
				android.R.color.holo_orange_dark));
		// 在启动时注册广播
		registerBroad();
		startService(new Intent(this, WeatherService.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 * 
	 * @Description: 与Activity的生命周期一样 在onDestroy销毁时注销广播
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
	 * @Description: 为3个fragment注册广播接收器
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
	 * 为action bar item设置点击事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, Settings.class));
			return true;
		} else if (id == R.id.action_refresh) {
			sp.edit().putString(PATH, LOCAL_PATH).putInt(INDEX, 21)
					.putString(CITY, "長沙市").commit();
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
	 * @Description: 自定义FragmentPagerAdapter适配器处理viwepager数据
	 * @date 2014-9-21 上午9:48:57
	 * @author 周钟江
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
		// viewpage设置标题
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
		 * @Description: 广播接收器接受服务发过来的广播更新组件
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
						.equals(CELSIUS) ? (weather.temp_c + "°")
						: (weather.temp_f + "°"));
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
	 * @Description: actionbar下拉列表导航点击事件 点击更新actionbar标题 启动活动展示对应省份的所有城市列表
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