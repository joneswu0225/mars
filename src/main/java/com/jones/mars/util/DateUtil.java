package com.jones.mars.util;


import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static final ThreadLocal<DateFormat> DEFAULT_FORMAT = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

	};

	public static final ThreadLocal<DateFormat> FULL_FORMAT = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		}

	};

	public static final ThreadLocal<DateFormat> FORMAT_FOR_RANDOM = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMssHHmmss");
		}

	};

	public static final ThreadLocal<DateFormat> DAY_FORMAT = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	public static final ThreadLocal<DateFormat> TIME_FORMAT = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}

	};

	private DateUtil() {

	}

	public static String dateToDateTimeStr(Date date) {
		return dateToDateTimeStr(date, DEFAULT_FORMAT.get());
	}

	public static String dateToDateTimeStr(Date date, DateFormat dateFormat) {
		return dateFormat.format(date);
	}

	public static String dateToDateStr(Date date) {
		return dateToDateTimeStr(date, DAY_FORMAT.get());
	}

	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		DateFormat sdf = DAY_FORMAT.get();
		Date smdate2 = sdf.parse(sdf.format(smdate));
		Date bdate2 = sdf.parse(sdf.format(bdate));

		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate2);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate2);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	public static Date dateAdd(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date parseDate(String dateStr) throws ParseException {
		return parseDate(dateStr, DAY_FORMAT.get());
	}

	public static Date parseDate(String dateStr, DateFormat format) throws ParseException {
		return format.parse(dateStr);
	}

	public static Date parseRequestTime(String requestTime) {
		// 因之前的文档错误，有的调用方传递了毫秒数，而有的调用方传递的是秒数，所以，这里我们需要根据长度做
		// 自动判断是秒数还是毫秒数
		long milli;
		if (StringUtils.isEmpty(requestTime)) {
			milli = System.currentTimeMillis();
		} else {
			long value = Long.parseLong(requestTime);
			if (requestTime.length() < 13) {
				milli = value * 1000;
			} else {
				milli = value;
			}
		}

		return new Date(milli);
	}

}
