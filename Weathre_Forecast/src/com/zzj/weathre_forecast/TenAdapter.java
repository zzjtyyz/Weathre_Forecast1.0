/**
 * 
 */
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

/**
 * @ClassName: TenAdapter 
 * @Description: TODO 
 * @date 2014-9-20 下午3:25:49 
 * @author 周钟江
 */
public class TenAdapter extends BaseAdapter implements BoradConstant{
	private List<HashMap<String, Object>> data;
	private LayoutInflater inflater;
	private Context context;
	public TenAdapter(Context context,List<HashMap<String, Object>> data) {
		this.data=data;
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 * @Description: TODO
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 * @Description: TODO
	 */
	@Override
	public HashMap<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 * @Description: TODO
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 * @Description: TODO
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_tendays, parent, false);
			holder.imageView=(ImageView) convertView.findViewById(R.id.weather_ten);
			holder.week_ten=(TextView) convertView.findViewById(R.id.week_ten);
			holder.date_ten=(TextView) convertView.findViewById(R.id.date_ten);
			holder.ave_ten=(TextView) convertView.findViewById(R.id.ave_ten);
			holder.temp_ten=(TextView) convertView.findViewById(R.id.temp_ten);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> map=data.get(position);
		Picasso.with(context).load((String) map.get(ICON_URL)).into(holder.imageView);
		holder.week_ten.setText((CharSequence) map.get(WEEKDAY_SHORT));
		holder.date_ten.setText(map.get(MONTH)+"月"+map.get(DAY));
		holder.temp_ten.setText(map.get(TEMP_HIGH)+"°/"+map.get(TEMP_LOW)+"°");
		holder.ave_ten.setText(map.get(AVEHUMIDITY)+"%");
		return convertView;
	}
	class ViewHolder{
		TextView week_ten,date_ten,ave_ten,temp_ten;
		ImageView imageView;
	}

}
