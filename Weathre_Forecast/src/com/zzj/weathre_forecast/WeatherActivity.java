/**
 * 
 */
package com.zzj.weathre_forecast;

import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: WeatherActivity
 * @Description: TODO
 * @date 2014-9-21 下午10:57:37
 * @author 周钟江
 */
public class WeatherActivity extends Activity implements BoradConstant {
	private TextView date, update_time, temp, conditions;
	private ImageView icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_item);
		date = (TextView) findViewById(R.id.date);
		update_time = (TextView) findViewById(R.id.update_time);
		temp = (TextView) findViewById(R.id.temp);
		conditions = (TextView) findViewById(R.id.conditions);
		icon = (ImageView) findViewById(R.id.icon_url);
		loadData();
	}

	/**
	 * @Title: loadData
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	@SuppressLint("DefaultLocale")
	@SuppressWarnings("unchecked")
	private void loadData() {
		Intent intent = getIntent();
		HashMap<String, Object> map = (HashMap<String, Object>) intent
				.getSerializableExtra(LIST);
		date.setText(map.get(WEEKDAY) + "--" + map.get(MONTH) + "月"
				+ map.get(DAY));
		Picasso.with(this).load((String) map.get(ICON_URL)).into(icon);
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		long lasttime = sp.getLong(FLUSH, 0);
		long time = System.currentTimeMillis() - lasttime;
		time /= 1000;
		long s = time % 60;
		long m = time / 60 % 60;
		long h = time / 3600 % 60;
		long d = time / 86400;
		String update = "";
		if (d > 0) {
			update = String.format("%2d天前更新", d);
		} else if (h > 0) {
			update = String.format("%2d小时%2d分钟前更新", h, m);
		} else if (m > 0) {
			update = String.format("%2d分钟%2d秒前更新", m, s);
		} else {
			update = String.format("%2d秒前更新", s);
		}
		update_time.setText(update);
		temp.setText(map.get(TEMP_HIGH) + "°/" + map.get(TEMP_LOW) + "°");
		conditions.setText((CharSequence) map.get(CONDITIONS));
	}
}
