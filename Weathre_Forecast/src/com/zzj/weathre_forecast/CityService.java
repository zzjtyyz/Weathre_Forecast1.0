/**
 * 
 */
package com.zzj.weathre_forecast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * @ClassName: CityService
 * @Description: TODO
 * @date 2014-9-20 下午10:57:25
 * @author 周钟江
 */
public class CityService extends IntentService implements BoradConstant {
	/**
	 * @Fields ERROR : TODO
	 */
	public static final String ERROR = "com.zzj.error";

	public CityService() {
		super("CityService");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 * 
	 * @Description: 异步处理数据
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		String city = intent.getStringExtra(CITY);
		if (city == null) {
			throw new NullPointerException();
		}
		try {
			loadPerference(city);
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), NET_ERROR,
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		try {
			String json = doGetJson(city);
			ArrayList<HashMap<String, String>> citys = jsonCity(json);
			Intent receive = new Intent(RECEIVE_CITY);
			receive.putExtra(CITYS, (Serializable) citys);
			sendBroadcast(receive);
		} catch (IOException | JSONException e) {
			Toast.makeText(getApplicationContext(), NO_RESOURSE,
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * @Title: loadPerference
	 * @Description: 通过SharedPreferences判断是否为刷新并且网络是否开启以下载资源到工程内部
	 * @param @param string
	 * @return void
	 * @throws
	 */
	private void loadPerference(String city) throws IOException {
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		File f = new File(getApplicationContext().getFilesDir(), city + SUFFIX);
		if (info != null && info.isConnected()) {
			URL url = new URL(CITY_URL + city.replace(" ", "%20") + JSON_SUFFIX);
			Log.d("url", url.toString());
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			InputStream in = connection.getInputStream();
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStream fos = openFileOutput(city + SUFFIX, MODE_PRIVATE);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			in.close();
			fos.close();
			connection.disconnect();
		} else if (!f.exists()) {
			Toast.makeText(getApplicationContext(), NO_RESOURSE,
					Toast.LENGTH_LONG).show();
			onDestroy();
		}
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @Title: doGetJson
	 * @Description: 读取内部的json文件得到json对象
	 * @param city
	 * @return void
	 * @throws
	 */
	private String doGetJson(String city) throws IOException {
		FileInputStream in = openFileInput(city + SUFFIX);
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
	 * @Title: jsonCity
	 * @Description: 解析json对象获取所有城市的集合
	 * @param josn
	 * @return ArrayList<HashMap<String, String>>
	 * @throws
	 */
	private ArrayList<HashMap<String, String>> jsonCity(String json)
			throws JSONException {
		JSONObject response = new JSONObject(json).getJSONObject("response");
		JSONArray results = response.getJSONArray("results");
		ArrayList<HashMap<String, String>> citys = new ArrayList<>();
		for (int i = 0; i < results.length(); i++) {
			JSONObject obj = (JSONObject) results.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			String city = obj.getString(CITY);
			map.put(CITY, city);
			String l = obj.getString(L);
			map.put(L, l + JSON_SUFFIX);
			citys.add(map);
		}
		return citys;
	}
}
