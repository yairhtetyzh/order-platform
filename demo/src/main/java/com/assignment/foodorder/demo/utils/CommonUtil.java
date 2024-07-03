package com.assignment.foodorder.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonUtil {

	private static Logger logger = LogManager.getLogger(CommonUtil.class);

	public static String dateToString(String format, Date date) {
		if (date == null) {
			return "";
		}
		if (format == null || format.trim().isEmpty()) {
			format = CommonConstant.STD_DATE_FORMAT;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static Date stringToDate(String format, String dateString) {
		if (dateString == null || dateString.trim().isEmpty()) {
			return null;
		}
		if (format == null || format.trim().isEmpty()) {
			format = CommonConstant.STD_DATE_FORMAT;
		}
		try {
			return new SimpleDateFormat(format).parse(dateString);
		} catch (Exception e) {
			logger.error(">>> Exception occurs while converting string into date >>> " + e.getMessage());
		}
		return null;
	}

	public static double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
		// Convert latitude and longitude from degrees to radians
		double lat1Rad = Math.toRadians(lat1);
		double lon1Rad = Math.toRadians(lon1);
		double lat2Rad = Math.toRadians(lat2);
		double lon2Rad = Math.toRadians(lon2);

		// Haversine formula
		double deltaLat = lat2Rad - lat1Rad;
		double deltaLon = lon2Rad - lon1Rad;

		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// Distance in kilometers
		return CommonConstant.EARTH_RADIUS_KM * c;
	}

	public static double calculateTimeToArrive(double distance, double speedKmPerHour) {
		return distance / speedKmPerHour;
	}
}
