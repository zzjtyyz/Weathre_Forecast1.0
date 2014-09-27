/**
 * 
 */
package com.zzj.weathre_forecast;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @ClassName: CityActivity
 * @Description: ���б���ʽ��ʾ���г��еĻ
 * @date 2014-9-20 ����10:44:55
 * @author ���ӽ�
 */
public class CityActivity extends Activity implements BoradConstant,
		OnItemClickListener {

	private BroadcastReceiver receiver;
	ListView listView;
	ArrayList<HashMap<String, String>> citys;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_first);
		// ע��㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction(RECEIVE_CITY);
		receiver = new MyReceive();
		registerReceiver(receiver, filter);

		listView = (ListView) findViewById(R.id.list_24hours);

		// ���������첽��ȡѡ��ʡ�ݵ����г���
		Intent service = new Intent(this, CityService.class);
		Intent intent = getIntent();
		index = intent.getIntExtra(INDEX, 21);
		service.putExtra(CITY, MainActivity.cityEn[index]);
		startService(service);
		listView.setOnItemClickListener(this);
	}

	/**
	 * ҳ������ʱע��ע��
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	/**
	 * 
	 * @ClassName: MyReceive
	 * @Description: �㲥���������³����б�
	 * @date 2014-9-21 ����11:10:39
	 * @author ���ӽ�
	 */
	class MyReceive extends BroadcastReceiver {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			citys = (ArrayList<HashMap<String, String>>) intent
					.getSerializableExtra(CITYS);
			String[] from = { CITY };
			int[] to = { android.R.id.text1 };
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					citys, android.R.layout.simple_list_item_1, from, to);
			listView.setAdapter(adapter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 * 
	 * @Description: ѡ����к���������رճ���ѡ��ҳ��
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String path = citys.get(position).get(L);
		sp.edit().putString(PATH, path).putInt(INDEX, index)
				.putString(CITY, citys.get(position).get(CITY)).commit();
		startService(new Intent(this, WeatherService.class));
		finish();
	}
}
