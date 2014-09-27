package com.zzj.weathre_forecast;

import java.util.HashMap;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FourDayAdapter extends BaseAdapter implements BoradConstant {
	List<HashMap<String, Object>> data;
	LayoutInflater inflater;
	Context context;

	public FourDayAdapter(Context context, List<HashMap<String, Object>> data) {
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.item_current, parent, false);
			holder.week_short = (TextView) convertView.findViewById(R.id.week1);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.weather1);
			holder.temp_high = (TextView) convertView.findViewById(R.id.temp1);
			holder.temp_low = (TextView) convertView.findViewById(R.id.hum1);
			holder.weather = (TextView) convertView
					.findViewById(R.id.weathertext1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> map = getItem(position);
		holder.week_short.setText((CharSequence) map.get(WEEKDAY_SHORT));
		Picasso.with(context).load((String) map.get(ICON_URL))
				.into(holder.imageView);
		holder.temp_high.setText(map.get(TEMP_HIGH) + "бу");
		holder.temp_low.setText((String) map.get(TEMP_LOW) + "бу");
		holder.weather.setText((String) map.get(CONDITIONS));
		return convertView;
	}

	class ViewHolder {
		TextView week_short, temp_high, temp_low, weather;
		ImageView imageView;
	}
}
