package com.css.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wujilai
 * @version 2.0
 * @date Jul 25, 2013
 */
public class DateUtil {
	/** 
	 * 时间格式(yyyy-MM-dd)
	 * */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 
	 * 时间格式(yyyy-MM-dd HH:mm:ss) 
	 * */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/** 
	 * 时间格式(yyyy-MM-dd HH:mm) 
	 * */
	public final static String DATE_TIME_PATTERN_MM = "yyyy-MM-dd HH:mm";

    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT     = "yyyy-MM-dd";

    /**
     * 缺省的日期显示格式： yyyyMMddHHmmss
     */
    public static final String DEFAULT_DATEMS_FORMAT   = "yyyyMMddHHmmss";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    /**
     * 取得系统当前时间
     * 
     * @return
     */
    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期
     * 
     * @return 当前日期
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * 根据传入日期，将其格式化成pattern格式的日期类型，默认日期格式为yyyy-MM-dd
     * 
     * @author: wujilai
     * @creation: Oct 10, 2013 4:42:09 PM
     * @param date
     *            需要解析的日期
     * @param pattern
     *            解析日期的格式
     * @return: Date 返回指定格式的日期
     */
    public static Date getCuurentDate(Date date, String pattern) {
        Date cuurDate = null;
        try {
            if (pattern == null || pattern.trim().length() == 0) {
                pattern = DEFAULT_DATE_FORMAT;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            cuurDate = dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cuurDate;
    }
    
    /**
     * 得到用缺省方式格式化的当前日期及时间
     * 
     * @return 当前日期及时间
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }
    
    /**
     * 得到系统当前日期及时间，并用指定的方式格式化
     * 
     * @param pattern
     *            显示格式
     * @return 当前日期及时间
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }

    /**
     * 得到用指定方式格式化的日期
     * 
     * @param date
     *            需要进行格式化的日期
     * @param pattern
     *            显示格式
     * @return 日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getMSDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATEMS_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 得到当前年份
     * 
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到当前月份
     * 
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        // 用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     * 
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
     * 
     * @param days
     *            增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getNowDate(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
     * 
     * @param date
     *            基准日期
     * @param days
     *            增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     * 
     * @param months
     *            增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getNowDate(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
     * 
     * @param date
     *            基准日期
     * @param months
     *            增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数
     * 
     * @param date
     *            基准日期
     * @param amount
     *            增加的数量
     * @param field
     *            增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     * 
     * @param one
     *            第一个日期数，作为基准
     * @param two
     *            第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     * 
     * @param one
     *            第一个日期数，作为基准
     * @param two
     *            第二个日期数，作为比较
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        // 得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);

        // 得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);

        return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     * 
     * @param datestr
     *            需要解析的日期字符串
     * @param pattern
     *            日期字符串的格式，默认为“yyyy-MM-dd”的形式
     * @return 解析后的日期
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 返回给定日期中的月份中的最后一天
     * 
     * @param date
     *            基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        // 将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        // 减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }
    
    public static String toDate(String dateString, String patternString) {
    	try {
    		SimpleDateFormat df = new SimpleDateFormat(patternString);
			Date d = df.parse(dateString);
			return df.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    /**
     * 计算两个日期之间天数/yyyy-MM-dd格式时间
     * 
     * @author zfb
     */						       //小时间       //大时间			
    @SuppressWarnings("unused")
	public static String countDays(Date date1,Date date2){
    	Calendar calendar1 = Calendar.getInstance();
    	calendar1.setTime(date1);
    	Calendar calendar2 = Calendar.getInstance();
    	calendar2.setTime(date2);
    	int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
    	int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
    	int year1 = calendar1.get(Calendar.YEAR);
    	int year2 = calendar2.get(Calendar.YEAR);
    	if(year1 != year2){//不同年
    		int timeDistance = 0;
    		for(int i = year1; i < year2; i++){
    			if(i%4 == 0 && i%100 != 0 || i%400 == 0){//闰年
    				timeDistance += 366;
    			}else{//平年
    				timeDistance += 365;
    			}
    			Integer days = timeDistance + (day2 - day1) + 1;//请假 时间是要加 1 ，比如 从 15号请到20号，是请假6天，
    			return days.toString();
    		}
    	}else{//相同年
    		Integer days = day2 - day1 + 1;
    		return days.toString();
		}
    	return null;//为什么 这里 还需要 返回值
    }
    
    public static Date toDate(String dateString){
		if(dateString==null||dateString.equals("")||dateString.equals("<NULL>"))
			return null;
		String[] pattern = new String[]{"yyyy-MM","yyyyMM","yyyy/MM",   
                "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",   
                "yyyyMMddHHmmss","yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm",   
                            "yyyy-MM-dd HH:mm:ss",   
                            "yyyy/MM/dd HH:mm:ss"};   
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString, pattern);
		} catch (ParseException e) {
			System.out.println(dateString);
			e.printStackTrace();
		}
		return null;
	}
    
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 将时间戳转换为日期字符串(格式：yyyy-MM-dd HH:mm:ss)
     * @param timestamp
     * @return
     */
    public static String timestampToDate(Long timestamp){
        if(timestamp != null){
            SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
            return df.format(new Date(Long.parseUnsignedLong(timestamp.toString())));
        }
        return null;
    }
    
}
