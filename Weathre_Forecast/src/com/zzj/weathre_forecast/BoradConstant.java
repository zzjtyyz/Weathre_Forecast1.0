package com.zzj.weathre_forecast;

/**
 * 
 * @ClassName: BoradConstant
 * @Description: ����Ԥ����������ӿ�
 * @date 2014-9-21 ����10:29:07
 * @author ���ӽ�
 */
public interface BoradConstant {

	public static final String THIRD_RECEIVE = "com.zzj.weather.thirdReceive"; // FirstFragment��Ӧ�Ĺ㲥��
	public static final String SECOND_RECEIVE = "com.zzj.weather.secondReceive"; // SecondFragment��Ӧ�Ĺ㲥��
	public static final String FIRST_RECEIVE = "com.zzj.weather.firstReceive"; // ThirdFragment��Ӧ�Ĺ㲥��
	public static final String RECEIVE_CITY = "com.zzj.weather.city_receive"; // CityActivity��Ӧ�Ĺ㲥��

	/*
	 * URI_HOURLY or URI_CURRENT or URI_FOURDAYS or URI_TENDAYS
	 * +LOCAL_PATH���ǲ�ѯ24Сʱ����ǰ��4���ڣ�10�����������ݵ�json�����ַ LOCAL_PATH �Ƕ�Ӧ�ĳ�ɳ�ĵ�ַ
	 * CITY_URL+ʡ��ƴ��+.json���ǲ�ѯ��Ӧʡ�����г���
	 */
	public static final String URI_HOURLY = "http://api.wunderground.com/api/d8a97916b464ee95/hourly/lang:CN";
	public static final String URI_CURRENT = "http://api.wunderground.com/api/d8a97916b464ee95/conditions/lang:CN";
	public static final String URI_FOURDAYS = "http://api.wunderground.com/api/d8a97916b464ee95/forecast/lang:CN";
	public static final String URI_TENDAYS = "http://api.wunderground.com/api/d8a97916b464ee95/forecast10day/lang:CN";
	public static final String LOCAL_PATH = "/q/zmw:00000.1.57679.json";
	public static final String CITY_URL = "http://api.wunderground.com/api/d8a97916b464ee95/conditions/lang:CN/q/China/";

	public static final String FILE_CURRENT = "currenr.js"; // �ڲ��洢��ǰ����json���ݵ��ļ���
	public static final String FILE_DAY4 = "day4.js"; // �ڲ��洢4������json���ݵ��ļ���
	public static final String FILE_TENDAY = "day10.js"; // �ڲ��洢10������json���ݵ��ļ���
	public static final String FILE_HOURLY = "hourly.js"; // �ڲ��洢24Сʱ����json���ݵ��ļ���

	public static final String WEEKDAY_SHORT = "weekday_short"; // json��ֵ����������д��Ӧ����������
	public static final String TEMP_HIGH = "temp_high"; // json��ֵ��������¶�Ӧ����������
	public static final String TEMP_LOW = "temp_low"; // json��ֵ��������¶�Ӧ����������
	public static final String CONDITIONS = "conditions"; // json��ֵ��������״����Ӧ����������
	public static final String ICON_URL = "icon_url"; // json��ֵ������������ͼ���Ӧ����������
	public static final String MONTH = "month"; // json��ֵ�����·ݶ�Ӧ����������
	public static final String DAY = "day"; // json��ֵ�������ڶ�Ӧ����������
	public static final String AVEHUMIDITY = "avehumidity"; // json��ֵ����ƽ��ʪ�ȶ�Ӧ����������
	public static final String L = "l"; // json��ֵ���г����������Ӷ�Ӧ����������
	public static final String CITY = "city"; // json��ֵ���г��ж�Ӧ����������
	public static final String HOURLY_FORECAST = "hourly_forecast"; // json��ֵ���ж�Ӧ����������
	public static final String FCTTIME = "FCTTIME"; // json��ֵ���ж�Ӧ����������

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
	 * @Fields SIMPLEFORECAST : json�����Ӧ����������simpleforecast
	 */
	public static final String SIMPLEFORECAST = "simpleforecast";
	/**
	 * @Fields FORECAST : json�����Ӧ����������forecast
	 */
	public static final String FORECAST = "forecast";
	/**
	 * @Fields FORECASTDAY : json�����Ӧ����������forecastday
	 */
	public static final String FORECASTDAY = "forecastday";
	/**
	 * @Fields DATE : json�����Ӧ����������date
	 */
	public static final String DATE = "date";
	/**
	 * @Fields CELSIUS : json�����Ӧ�¶ȵ���������celsius
	 */
	public static final String CELSIUS = "celsius";
	/**
	 * @Fields HIGH : json�����Ӧ����������high
	 */
	public static final String HIGH = "high";
	/**
	 * @Fields LOW : json�����Ӧ����������low
	 */
	public static final String LOW = "low";
	public static final String CITYS = "citys"; // ����ʡ�����г��ж�Ӧ�ļ�
	public static final String PATH = "path"; // ���ݲ�ѯ��������·����Ӧ�ļ�
	public static final String INDEX = "index"; // ʡ�ݼ��ϵ��±�
	public static final String FLUSH = "flush"; // SharedPreferences���ж��Ƿ�ˢ�����ݶ�Ӧ�ļ�

	/**
	 * startActivityForResult�е�������ͷ�����
	 */
	public static final int requestCode = 0;
	public static final int responseCode = 1;
	/**
	 * @Fields CHARACTER : "utf-8"����
	 */
	public static final String CHARACTER = "utf-8";
	/**
	 * @Fields SUFFIX : �ڲ��ļ��Ĵ洢��׺
	 */
	public static final String SUFFIX = ".js";
	/**
	 * @Fields JSON_SUFFIX : .json
	 */
	public static final String JSON_SUFFIX = ".json";
	/**
	 * @Fields NET_ERROR : ��������
	 */
	public static final String NET_ERROR = "��������";
	/**
	 * @Fields NO_RESOURSE :û����Դ
	 */
	public static final String NO_RESOURSE = "û�����ϣ���������������";
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