package com.zzj.weathre_forecast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.zzj.weather_model.Weather;

public class WeatherService extends IntentService implements BoradConstant {
	private String json_ten;
	private String json_four;
	private String json_current;
	private String current_uri;
	private String fourdays_uri;
	private String tendays_uri;
	private SharedPreferences sp;
	private String hourly_uri;
	private String json_hourly;

	public WeatherService() {
		super("WeatherService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String path = sp.getString(PATH, LOCAL_PATH).replace(" ", "%20");
		current_uri = URI_CURRENT + path;
		fourdays_uri = URI_FOURDAYS + path;
		tendays_uri = URI_TENDAYS + path;
		hourly_uri = URI_HOURLY + path;
		try {
			loadPreference(hourly_uri, FILE_HOURLY);
			json_hourly = doGetJson(hourly_uri, FILE_HOURLY);
			sendBroadcast(FIRST_RECEIVE);
			loadPreference(current_uri, FILE_CURRENT);
			json_current = doGetJson(current_uri, FILE_CURRENT);
			loadPreference(fourdays_uri, FILE_DAY4);
			json_four = doGetJson(fourdays_uri, FILE_DAY4);
			sendBroadcast(SECOND_RECEIVE);
			sendBroadcast(FIRST_RECEIVE);
			loadPreference(tendays_uri, FILE_TENDAY);
			json_ten = doGetJson(tendays_uri, FILE_TENDAY);
			sendBroadcast(THIRD_RECEIVE);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	private void sendBroadcast(String receive) throws JSONException {
		if (receive.equals(SECOND_RECEIVE)) {
			Weather weather = jsonCurrent(json_current);
			ArrayList<HashMap<String, Object>> list = jsonManyDay(json_four);
			Intent intent = new Intent(receive);
			intent.putExtra("weather", weather);
			intent.putExtra(LIST, list);
			sendBroadcast(intent);
		} else if (receive.equals(THIRD_RECEIVE)) {
			ArrayList<HashMap<String, Object>> list = jsonManyDay(json_ten);
			Intent intent = new Intent(receive);
			intent.putExtra(LIST, list);
			sendBroadcast(intent);
		} else if (receive.equals(FIRST_RECEIVE)) {
			ArrayList<HashMap<String, Object>> list = json24Hourly(json_hourly);
			Intent intent = new Intent(receive);
			intent.putExtra(LIST, list);
			sendBroadcast(intent);
		}
	}

	/**
	 * @Title: loadPreference
	 * @Description: 判断网络是否可用更新数据
	 * @param uri
	 *            数据对象网络地址
	 * @param file
	 *            存储在内部的文件名
	 * @param @throws MalformedURLException
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	private void loadPreference(String uri, String file)
			throws MalformedURLException, IOException {
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		File f = new File(getApplicationContext().getFilesDir(), file);
		if (info != null && info.isConnected()) {
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			InputStream in = connection.getInputStream();
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStream fos = openFileOutput(file, MODE_PRIVATE);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			in.close();
			fos.close();
			connection.disconnect();
			sp.edit().putLong(FLUSH, System.currentTimeMillis()).commit();
		} else if (!f.exists()) {
			Toast.makeText(getApplicationContext(), NO_RESOURSE,
					Toast.LENGTH_LONG).show();
			onDestroy();
		}
	}

	/**
	 * @throws JSONException
	 * @Title: doGetCurrent
	 * @Description: HttpClient执行Get请求返回json对象
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	private String doGetJson(String uri, String file) throws IOException,
			JSONException {
		FileInputStream in = openFileInput(file);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = in.read(buf)) != -1) {
			b.write(buf, 0, len);
		}
		String json = new String(b.toByteArray(), CHARACTER);
		in.close();
		b.close();
		return json;
	}

	/**
	 * @throws JSONException
	 * @Title: jsonCurrent
	 * @Description: 解析当前天气的Json对象
	 * @param json
	 * @return Weather
	 * @throws
	 */
	private Weather jsonCurrent(String json) throws JSONException {
		JSONObject current_observation = new JSONObject(json)
				.getJSONObject("current_observation");
		JSONObject display_location = current_observation
				.getJSONObject("display_location");
		String city = display_location.getString(CITY);
		String weahter = current_observation.getString("weather");
		int temp_f = current_observation.getInt("temp_f");
		int temp_c = current_observation.getInt("temp_c");
		String relative_humidity = current_observation
				.getString("relative_humidity");
		String wind_dir = current_observation.getString("wind_dir");
		int wind_mph = current_observation.getInt("wind_mph");
		int wind_kph = current_observation.getInt("wind_kph");
		String icon_url = current_observation.getString(ICON_URL);
		Weather weather = new Weather(icon_url, temp_f, temp_c, weahter,
				wind_mph, wind_kph, wind_dir, relative_humidity, city);
		return weather;
	}

	/**
	 * 
	 * @Title: jsonFour
	 * @Description: 解析多天天气的Json对象
	 * @param @param json
	 * @param @return
	 * @param @throws JSONException
	 * @return ArrayList<HashMap<String,Object>>
	 * @throws
	 */
	private ArrayList<HashMap<String, Object>> jsonManyDay(String json)
			throws JSONException {
		JSONObject forecast = new JSONObject(json).getJSONObject(FORECAST)
				.getJSONObject(SIMPLEFORECAST);
		JSONArray array = forecast.getJSONArray(FORECASTDAY);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < array.length(); i++) {
			HashMap<String, Object> map = new HashMap<>();
			JSONObject day = (JSONObject) array.get(i);
			int date = day.getJSONObject(DATE).getInt(DAY);
			int month = day.getJSONObject(DATE).getInt(MONTH);
			String weekday_short = day.getJSONObject(DATE).getString(
					WEEKDAY_SHORT);
			String weekday = day.getJSONObject(DATE).getString(WEEKDAY);
			String temperature = sp.getString(TEMPERATURE, CELSIUS);
			String temp_high = day.getJSONObject(HIGH).getString(temperature);
			String temp_low = day.getJSONObject(LOW).getString(temperature);
			String conditions = day.getString(CONDITIONS);
			String icon_url = day.getString(ICON_URL);
			int hum = day.getInt(AVEHUMIDITY);
			map.put(WEEKDAY_SHORT, weekday_short);
			map.put(WEEKDAY, weekday);
			map.put(MONTH, month);
			map.put(DAY, date);
			map.put(TEMP_HIGH, temp_high);
			map.put(TEMP_LOW, temp_low);
			map.put(CONDITIONS, conditions);
			map.put(ICON_URL, icon_url);
			map.put(AVEHUMIDITY, hum);
			list.add(map);
		}
		return list;
	}

	/**
	 * @throws JSONException
	 * @Title: json24Hourly
	 * @Description: 解析24小时天气的Json对象
	 * @param @param json_hourly2
	 * @param @return
	 * @return ArrayList<HashMap<String,Object>>
	 * @throws
	 */
	private ArrayList<HashMap<String, Object>> json24Hourly(String json)
			throws JSONException {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		JSONObject obj = new JSONObject(json);
		JSONArray hourly_forecast = obj.getJSONArray(HOURLY_FORECAST);
		for (int i = 0; i < hourly_forecast.length(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			JSONObject object = (JSONObject) hourly_forecast.get(i);
			JSONObject fcttime = object.getJSONObject(FCTTIME);
			String hour = fcttime.getString(HOUR);
			String mon = fcttime.getString(MON_PADDED);
			String day = fcttime.getString(MDAY_PADDED);
			JSONObject temp = object.getJSONObject(TEMP);
			String temperature = temp.getString(sp.getString(TEMPERATURE,
					CELSIUS).equals(CELSIUS) ? METRIC : ENGLISH);
			String condition = object.getString(CONDITION);
			String icon = object.getString(ICON_URL);
			String humidity = object.getString(HUMIDITY);
			map.put(MON_PADDED, mon);
			map.put(MDAY_PADDED, day);
			map.put(HOUR, hour);
			map.put(TEMP, temperature);
			map.put(CONDITION, condition);
			map.put(ICON_URL, icon);
			map.put(HUMIDITY, humidity);
			list.add(map);
		}
		return list;
	}
}