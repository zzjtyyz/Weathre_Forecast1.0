package com.zzj.weathre_forecast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FirstFragment extends Fragment {
	static ListView listView;
	static HourlyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_first, container, false);
		listView = (ListView) view.findViewById(R.id.list_24hours);
		return view;
	}
}
