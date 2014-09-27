/**
 * 
 */
package com.zzj.weathre_forecast;

import java.util.ArrayList;
import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: HourlyAdapter
 * @Description: TODO
 * @date 2014-9-22 上午7:44:06
 * @author 周钟江
 */
public class HourlyAdapter extends BaseAdapter implements BoradConstant {
	private ArrayList<HashMap<String, Object>> data;
	private Context context;
	private LayoutInflater inflater;

	public HourlyAdapter(Context context,
			ArrayList<HashMap<String, Object>> data) {
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
			convertView = inflater.inflate(R.layout.item_first, parent, false);
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.date_hour);
			holder.hour = (TextView) convertView.findViewById(R.id.hour);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.weather_hour);
			holder.temp = (TextView) convertView.findViewById(R.id.temp_hour);
			holder.hum = (TextView) convertView.findViewById(R.id.hum_hour);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> map = getItem(position);
		Picasso.with(context).load((String) map.get(ICON_URL))
				.into(holder.imageView);
		holder.date.setText(map.get(MON_PADDED) + "月" + map.get(MDAY_PADDED));
		holder.hour.setText(map.get(HOUR) + ":00");
		holder.temp.setText((CharSequence) map.get(TEMP) + "°");
		holder.hum.setText((CharSequence) map.get(HUMIDITY) + "%");
		return convertView;
	}

	class ViewHolder {
		TextView date, hour, temp, hum;
		ImageView imageView;
	}
}
