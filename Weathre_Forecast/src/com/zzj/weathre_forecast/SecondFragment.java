package com.zzj.weathre_forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondFragment extends Fragment implements BoradConstant {
	public static GridView gridView;
	public static ImageView weatherIcon;
	public static TextView temp, weathertext, hum, wind;
	public static FourDayAdapter adapter;
	public static List<HashMap<String, Object>> currentList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_current, container,
				false);
		gridView = (GridView) view.findViewById(R.id.gridview);
		weatherIcon = (ImageView) view.findViewById(R.id.weather);
		temp = (TextView) view.findViewById(R.id.temp);
		weathertext = (TextView) view.findViewById(R.id.weathertext);
		hum = (TextView) view.findViewById(R.id.hum);
		wind = (TextView) view.findViewById(R.id.wind);
		currentList = new ArrayList<HashMap<String, Object>>();
		return view;
	}
	/*
	 * class SecondReceive extends BroadcastReceiver{
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { String
	 * action=intent.getAction(); if(action.equals(SECOND_RECEIVE)){ Weather
	 * weather=(Weather) intent.getSerializableExtra("weather");
	 * 
	 * @SuppressWarnings("unchecked") ArrayList<HashMap<String, Object>>
	 * list=(ArrayList<HashMap<String, Object>>)
	 * intent.getSerializableExtra("list");
	 * Picasso.with(context).load(weather.icon_url).into(weatherIcon);
	 * temp.setText(weather.temp_c+"бу"); weathertext.setText(weather.weahter);
	 * hum.setText("HUMIDITY"+weather.relative_humidity);
	 * wind.setText("WIND "+weather.wind_kph+" km/h "+weather.wind_dir);
	 * adapter=new FourDayAdapter(context, list); gridView.setAdapter(adapter);
	 * } }
	 * 
	 * }
	 */
}
