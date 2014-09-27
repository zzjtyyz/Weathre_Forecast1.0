package com.zzj.weathre_forecast;

/**
 * 
 * @ClassName: BoradConstant
 * @Description: 天气预报常量定义接口
 * @date 2014-9-21 上午10:29:07
 * @author 周钟江
 */
public interface BoradConstant {

	public static final String THIRD_RECEIVE = "com.zzj.weather.thirdReceive"; // FirstFragment对应的广播名
	public static final String SECOND_RECEIVE = "com.zzj.weather.secondReceive"; // SecondFragment对应的广播名
	public static final String FIRST_RECEIVE = "com.zzj.weather.firstReceive"; // ThirdFragment对应的广播名
	public static final String RECEIVE_CITY = "com.zzj.weather.city_receive"; // CityActivity对应的广播名

	/*
	 * URI_HOURLY or URI_CURRENT or URI_FOURDAYS or URI_TENDAYS
	 * +LOCAL_PATH就是查询24小时，当前，4天内，10天内天气数据的json网络地址 LOCAL_PATH 是对应的长沙的地址
	 * CITY_URL+省份拼音+.json就是查询对应省份所有城市
	 */
	public static final String URI_HOURLY = "http://api.wunderground.com/api/d8a97916b464ee95/hourly/lang:CN";
	public static final String URI_CURRENT = "http://api.wunderground.com/api/d8a97916b464ee95/conditions/lang:CN";
	public static final String URI_FOURDAYS = "http://api.wunderground.com/api/d8a97916b464ee95/forecast/lang:CN";
	public static final String URI_TENDAYS = "http://api.wunderground.com/api/d8a97916b464ee95/forecast10day/lang:CN";
	public static final String LOCAL_PATH = "/q/zmw:00000.1.57679.json";
	public static final String CITY_URL = "http://api.wunderground.com/api/d8a97916b464ee95/conditions/lang:CN/q/China/";

	public static final String FILE_CURRENT = "currenr.js"; // 内部存储当前天气json数据的文件名
	public static final String FILE_DAY4 = "day4.js"; // 内部存储4天天气json数据的文件名
	public static final String FILE_TENDAY = "day10.js"; // 内部存储10天天气json数据的文件名
	public static final String FILE_HOURLY = "hourly.js"; // 内部存储24小时天气json数据的文件名

	public static final String WEEKDAY_SHORT = "weekday_short"; // json名值对中星期缩写对应的名（键）
	public static final String TEMP_HIGH = "temp_high"; // json名值对中最高温对应的名（键）
	public static final String TEMP_LOW = "temp_low"; // json名值对中最低温对应的名（键）
	public static final String CONDITIONS = "conditions"; // json名值对中天气状况对应的名（键）
	public static final String ICON_URL = "icon_url"; // json名值对中天气网络图标对应的名（键）
	public static final String MONTH = "month"; // json名值对中月份对应的名（键）
	public static final String DAY = "day"; // json名值对中日期对应的名（键）
	public static final String AVEHUMIDITY = "avehumidity"; // json名值对中平均湿度对应的名（键）
	public static final String L = "l"; // json名值对中城市天气连接对应的名（键）
	public static final String CITY = "city"; // json名值对中城市对应的名（键）
	public static final String HOURLY_FORECAST = "hourly_forecast"; // json名值对中对应的名（键）
	public static final String FCTTIME = "FCTTIME"; // json名值对中对应的名（键）

	/**
	 * @Fields WEEKDAY : TODO
	 */
	public static final String WEEKDAY = "weekday";
	/**
	 * @Fields HUMIDITY : TODO
	 */
	public static final String HUMIDITY = "humidity";
	/**
	 * @Fields CONDITION : TODO
	 */
	public static final String CONDITION = "condition";
	/**
	 * @Fields ENGLISH : TODO
	 */
	public static final String ENGLISH = "english";
	/**
	 * @Fields METRIC : TODO
	 */
	public static final String METRIC = "metric";
	/**
	 * @Fields TEMP : TODO
	 */
	public static final String TEMP = "temp";
	/**
	 * @Fields MDAY_PADDED : TODO
	 */
	public static final String MDAY_PADDED = "mday_padded";
	/**
	 * @Fields MON_PADDED : TODO
	 */
	public static final String MON_PADDED = "mon_padded";
	public static final String HOUR = "hour";
	/**
	 * @Fields SIMPLEFORECAST : json对象对应的名（键）simpleforecast
	 */
	public static final String SIMPLEFORECAST = "simpleforecast";
	/**
	 * @Fields FORECAST : json对象对应的名（键）forecast
	 */
	public static final String FORECAST = "forecast";
	/**
	 * @Fields FORECASTDAY : json对象对应的名（键）forecastday
	 */
	public static final String FORECASTDAY = "forecastday";
	/**
	 * @Fields DATE : json对象对应的名（键）date
	 */
	public static final String DATE = "date";
	/**
	 * @Fields CELSIUS : json对象对应温度的名（键）celsius
	 */
	public static final String CELSIUS = "celsius";
	/**
	 * @Fields HIGH : json对象对应的名（键）high
	 */
	public static final String HIGH = "high";
	/**
	 * @Fields LOW : json对象对应的名（键）low
	 */
	public static final String LOW = "low";
	public static final String CITYS = "citys"; // 传递省份所有城市对应的键
	public static final String PATH = "path"; // 传递查询城市网络路径对应的键
	public static final String INDEX = "index"; // 省份集合的下标
	public static final String FLUSH = "flush"; // SharedPreferences中判断是否刷新数据对应的键

	/**
	 * startActivityForResult中的请求码和返回码
	 */
	public static final int requestCode = 0;
	public static final int responseCode = 1;
	/**
	 * @Fields CHARACTER : "utf-8"编码
	 */
	public static final String CHARACTER = "utf-8";
	/**
	 * @Fields SUFFIX : 内部文件的存储后缀
	 */
	public static final String SUFFIX = ".js";
	/**
	 * @Fields JSON_SUFFIX : .json
	 */
	public static final String JSON_SUFFIX = ".json";
	/**
	 * @Fields NET_ERROR : 网络有误
	 */
	public static final String NET_ERROR = "网络有误";
	/**
	 * @Fields NO_RESOURSE :没有资源
	 */
	public static final String NO_RESOURSE = "没有资料，请立即更新下载";
	/**
	 * @Fields SWITCH : TODO
	 */
	public static final String SWITCH = "cb";
	/**
	 * @Fields UPDATE : TODO
	 */
	public static final String UPDATE = "update";
	/**
	 * @Fields RAINFALL : TODO
	 */
	public static final String RAINFALL = "rainfall";
	/**
	 * @Fields WIND : wind
	 */
	public static final String WIND = "wind";
	/**
	 * @Fields TEMPERATURE : temperature
	 */
	public static final String TEMPERATURE = "temperature";
	/**
	 * @Fields LIST : TODO
	 */
	public static final String LIST = "list";
}