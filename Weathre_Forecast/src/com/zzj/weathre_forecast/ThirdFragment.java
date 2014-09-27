package com.zzj.weathre_forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ThirdFragment extends Fragment implements BoradConstant,
		OnItemClickListener {
	public static ListView listView;
	public static TenAdapter adapter;
	public static List<HashMap<String, Object>> tens = new ArrayList<HashMap<String, Object>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_third, container, false);
		listView = (ListView) view.findViewById(R.id.list_tendays);
		adapter = new TenAdapter(getActivity().getApplicationContext(), tens);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 * 
	 * @Description: TODO
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		HashMap<String, Object> map = tens.get(position);
		Intent intent = new Intent(getActivity(), WeatherActivity.class);
		intent.putExtra(LIST, map);
		startActivity(intent);
	}
}
