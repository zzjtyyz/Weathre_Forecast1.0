package com.zzj.weather_model;

import java.io.Serializable;

public class Weather implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1025534588874914273L;
	public String icon_url;
	public int temp_c;
	public int temp_f;
	public String weahter;
	public int wind_mph;
	public int wind_kph;
	public String wind_dir;
	public String relative_humidity;
	public String city;
	public int wind_degrees;
	public String pressure_mb;
	public String pressure_in;
	public int dewpoint_f;
	public int dewpoint_c;
	public String feelslike_f;
	public String feelslike_c;
	public String visibility_mi;
	public String visibility_km;
	public String precip_1hr_in;
	public String precip_1hr_metric;
	public String precip_today_in;
	public String precip_today_metric;
	public Weather(String icon_url, int temp_f,int temp_c, String weahter,
			int wind_mph,int wind_kph, String wind_dir, String relative_humidity,String city) {
		super();
		this.icon_url = icon_url;
		this.temp_f=temp_f;
		this.temp_c = temp_c;
		this.weahter = weahter;
		this.wind_mph = wind_mph;
		this.wind_kph = wind_kph;
		this.wind_dir = wind_dir;
		this.relative_humidity = relative_humidity;
		this.city=city;
	}
	
}
