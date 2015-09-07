package com.jurassic.iwork.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	/** 时间日期格式化到年月日时分秒. */
	public static final String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";

	/** 时间日期格式化到年月日. */
	public static final String dateFormatYMD = "yyyy-MM-dd";

	/** 时间日期格式化到年月. */
	public static final String dateFormatYM = "yyyy-MM";

	/** 时间日期格式化到年月日时分. */
	public static final String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

	/** 时间日期格式化到月日. */
	public static final String dateFormatMD = "MM/dd";

	/** 时分秒. */
	public static final String dateFormatHMS = "HH:mm:ss";

	/** 时分. */
	public static final String dateFormatHM = "HH:mm";

	/** 上午. */
	public static final String AM = "AM";

	/** 下午. */
	public static final String PM = "PM";

	/**
	 * 描述：String类型的日期时间转化为Date类型.
	 * 
	 * @param strDate
	 *            String形式的日期时间
	 * @param format
	 *            格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return Date Date类型日期时间
	 */
	public static Date getDateByFormat(String strDate, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = mSimpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 描述：Date类型转化为String类型.
	 * 
	 * @param date
	 *            the date
	 * @param format
	 *            the format
	 * @return String String类型日期时间
	 */
	public static String getStringByFormat(Date date, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		String strDate = null;
		try {
			strDate = mSimpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * 描述：计算两个日期所差的天数.
	 * 
	 * @param milliseconds1
	 *            the milliseconds1
	 * @param milliseconds2
	 *            the milliseconds2
	 * @return int 所差的天数
	 */
	public static int getOffectDay(long milliseconds1, long milliseconds2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(milliseconds1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(milliseconds2);
		// 先判断是否同年
		int y1 = calendar1.get(Calendar.YEAR);
		int y2 = calendar2.get(Calendar.YEAR);
		int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
		int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
		int maxDays = 0;
		int day = 0;
		if (y1 - y2 > 0) {
			maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 + maxDays;
		} else if (y1 - y2 < 0) {
			maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 - maxDays;
		} else {
			day = d1 - d2;
		}
		return day;
	}

	/**
	 * 描述：计算两个日期所差的小时数.
	 * 
	 * @param date1
	 *            第一个时间的毫秒表示
	 * @param date2
	 *            第二个时间的毫秒表示
	 * @return int 所差的小时数
	 */
	public static int getOffectHour(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
		int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
		int h = 0;
		int day = getOffectDay(date1, date2);
		h = h1 - h2 + day * 24;
		return h;
	}

	/**
	 * 描述：计算两个日期所差的分钟数.
	 * 
	 * @param date1
	 *            第一个时间的毫秒表示
	 * @param date2
	 *            第二个时间的毫秒表示
	 * @return int 所差的分钟数
	 */
	public static int getOffectMinutes(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int m1 = calendar1.get(Calendar.MINUTE);
		int m2 = calendar2.get(Calendar.MINUTE);
		int h = getOffectHour(date1, date2);
		int m = 0;
		m = m1 - m2 + h * 60;
		return m;
	}

	/**
	 * 功能描述：返回月
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 描述：获取指定日期时间的字符串,用于导出想要的格式.
	 * 
	 * @param strDate
	 *            String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
	 * @param format
	 *            输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String 转换后的String类型的日期时间
	 */
	public static String getStringByFormat(String strDate, String format) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
					dateFormatYMDHMS);
			c.setTime(mSimpleDateFormat.parse(strDate));
			SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
			mDateTime = mSimpleDateFormat2.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;
	}

	/**
	 * 描述：根据时间返回格式化后的时间的描述. 小于1小时显示多少分钟前 大于1小时显示今天＋实际日期，大于今天全部显示实际时间
	 * 
	 * @param strDate
	 *            the str date
	 * @param outFormat
	 *            the out format
	 * @return the string
	 */
	public static String formatDateStr2Desc(String strDate, String outFormat) {

		DateFormat df = new SimpleDateFormat(dateFormatYMDHMS);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c2.setTime(df.parse(strDate));
			c1.setTime(new Date());
			int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
			if (d == 0) {
				int h = getOffectHour(c1.getTimeInMillis(),
						c2.getTimeInMillis());
				if (h > 0) {
					return "今天" + getStringByFormat(strDate, dateFormatHM);
					// return h + "小时前";
				} else if (h < 0) {
					// return Math.abs(h) + "小时后";
				} else if (h == 0) {
					int m = getOffectMinutes(c1.getTimeInMillis(),
							c2.getTimeInMillis());
					if (m > 0) {
						return m + "分钟前";
					} else if (m < 0) {
						// return Math.abs(m) + "分钟后";
					} else {
						return "刚刚";
					}
				}

			} else if (d > 0) {
				if (d == 1) {
					// return "昨天"+getStringByFormat(strDate,outFormat);
				} else if (d == 2) {
					// return "前天"+getStringByFormat(strDate,outFormat);
				}
			} else if (d < 0) {
				if (d == -1) {
					// return "明天"+getStringByFormat(strDate,outFormat);
				} else if (d == -2) {
					// return "后天"+getStringByFormat(strDate,outFormat);
				} else {
					// return Math.abs(d) +
					// "天后"+getStringByFormat(strDate,outFormat);
				}
			}

			String out = getStringByFormat(strDate, outFormat);
			if (!StringUtil.isEmpty(out)) {
				return out;
			}
		} catch (Exception e) {
		}

		return strDate;
	}
}
