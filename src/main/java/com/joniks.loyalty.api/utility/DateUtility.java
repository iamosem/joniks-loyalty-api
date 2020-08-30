package com.joniks.lotalty.api.utility;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.joniks.lotalty.api.constants.JLAConstants;

public class DateUtility {
	public final static String[] DATE_FORMATS = new String[] { "M/d/yyyy hh:mm:ss a", "MM/dd/yy" };

	//
	// private final static DebugManager logger = DebugManager.getInstance(DateUtility.class);
	//
	// public static boolean isValidDateFormat_MMDDYYYY(String dateString) {
	// SimpleDateFormat formatter = new SimpleDateFormat(TSCConstants.MMDDYYYY);
	// formatter.setLenient(false);;
	// try {
	// Date date = formatter.parse(dateString);
	// DateUtils.parseDate(dateString,TSCConstants.MMDDYYYY); //parse for valid month, year, day integer values
	// } catch (ParseException e) {
	// logger.error("Date String provided is of invalid format: " + dateString);
	// return false;
	// }
	// return true;
	// }
	// public static Long convertDateStringToMilliSeconds(String dateString, boolean isStartDate){
	// SimpleDateFormat formatter = new SimpleDateFormat(TSCConstants.MMDDYYYY);
	// formatter.setLenient(false);
	// Date date;
	// try {
	// date = formatter.parse(dateString);
	// DateUtils.parseDate(dateString,TSCConstants.MMDDYYYY); //parse for valid month, year, day integer values
	// } catch (ParseException e) {
	// logger.error("Date String provided is of invalid format: " + dateString);
	// return 0L;
	// }
	//
	// Calendar cal;
	// cal = Calendar.getInstance();
	// cal.setTime(date);
	// if(isStartDate){
	// cal.set(Calendar.HOUR_OF_DAY, 0);
	// cal.set(Calendar.MINUTE, 0);
	// cal.set(Calendar.SECOND, 0);
	// cal.set(Calendar.MILLISECOND, 0);
	// }else{
	// cal.set(Calendar.HOUR_OF_DAY, 23);
	// cal.set(Calendar.MINUTE, 59);
	// cal.set(Calendar.SECOND, 59);
	// cal.set(Calendar.MILLISECOND, 999);
	// }
	// String dateInMilSec = new Long(cal.getTimeInMillis()).toString();
	//
	//
	// return new Long(dateInMilSec);
	// }
	//
	// public static Long convertDateTimeStringToMilliSeconds(String dateString, String hour, String minute, boolean isStartDate){
	// SimpleDateFormat formatter = new SimpleDateFormat(TSCConstants.MMDDYYYY);
	// formatter.setLenient(false);
	// Date date;
	// try {
	// date = formatter.parse(dateString); //parse for valid format
	// DateUtils.parseDate(dateString,TSCConstants.MMDDYYYY); //parse for valid month, year, day integer values
	// } catch (ParseException e) {
	// logger.error("Date String provided is of invalid format: " + dateString);
	// return 0L;
	// }
	//
	// Calendar cal;
	// cal = Calendar.getInstance();
	// cal.setTime(date);
	// if(isStartDate){
	// cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
	// cal.set(Calendar.MINUTE, Integer.parseInt(minute));
	// cal.set(Calendar.SECOND, 0);
	// cal.set(Calendar.MILLISECOND, 0);
	// }else{
	// cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
	// cal.set(Calendar.MINUTE, Integer.parseInt(minute));
	// cal.set(Calendar.SECOND, 59);
	// cal.set(Calendar.MILLISECOND, 999);
	// }
	// String dateInMilSec = new Long(cal.getTimeInMillis()).toString();
	//
	//
	// return new Long(dateInMilSec);
	// }

	public static String convertMillisToHourFormat_HHmmss(Long milliSeconds) {
		try {
			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliSeconds), TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)), TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
			return hms;
		} catch (Exception e) {
			return null;
		}
	}

	// ASI492: transferred from CommonUtility
	public static String convertTimeStampToDateString(String timeStamp, String timeFormat) {
		Date date = new Date(Long.parseLong(timeStamp));
		Format format = new SimpleDateFormat(timeFormat);
		return format.format(date).toString();
	}

	// ASI492: transferred from CommonUtility
	public static String convertTimeStampToDateString(String timeStamp, String timeFormat, String timezone) {
		Date date = new Date(Long.parseLong(timeStamp));

		DateFormat format = new SimpleDateFormat(timeFormat);
		format.setTimeZone(TimeZone.getTimeZone(timezone));
		return format.format(date).toString();
	}

	public static void main(String[] args) {
		System.out.println(getFormattedDate(new Date(), JLAConstants.DATE_FORMAT_YYYYMMDDHHMMSS));
		// System.out.println("1 - " + convertTimeStampToDateString("1545172180", "yyyy-MM-dd HH:mm"));
		// System.out.println("2 - " + getFormattedDate(1545172180L * 1000, "yyyy-MM-dd HH:mm"));
		// System.out.println("3 - " + getCurrentDay() + " - " + convertDateStringToMilliSeconds(getCurrentDay(), true));
		// System.out.println("4 - " + getCurrentDay() + " - " + convertDateStringToMilliSeconds(getCurrentDay(), false));
		String from = "2019/01/09";
		String to = "";

		String currentDay = DateUtility.getCurrentDay();
		String fromDate = currentDay, toDate = currentDay;

		long fromDateTime = DateUtility.convertDateStringToMilliSeconds(from, true);
		long toDateTime = DateUtility.convertDateStringToMilliSeconds(to, false);
		System.out.println("ORIGINAL FROMDATE " + fromDate + " TODATE " + toDate);
		if (fromDateTime == 0L) {
			fromDateTime = DateUtility.convertDateStringToMilliSeconds(currentDay, true);
		} else {
			fromDate = from;
			fromDateTime = DateUtility.convertDateStringToMilliSeconds(from, true);
		}

		if (toDateTime == 0L) {
			toDateTime = DateUtility.convertDateStringToMilliSeconds(currentDay, false);
		} else {
			toDate = to;
			toDateTime = DateUtility.convertDateStringToMilliSeconds(to, false);
		}

		if (fromDateTime != 0L && toDateTime == 0L) {
			toDateTime = DateUtility.convertDateStringToMilliSeconds(currentDay, false);
			toDate = currentDay;
		}

		if (fromDateTime == 0L && toDateTime != 0L) {
			fromDateTime = DateUtility.convertDateStringToMilliSeconds(currentDay, true);
			fromDate = currentDay;
		}

		if (fromDateTime > toDateTime) {
			toDate = fromDate;
			toDateTime = DateUtility.convertDateStringToMilliSeconds(toDate, false);

		}
		System.out.println("FROM DATE - " + fromDate + " TO DATE - " + toDate);
	}

	// ex. Mon, 17 Jun 2013 20:17:34
	public static String getFormattedDate(Long dateInMills, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateInMills);
		SimpleDateFormat fmt = new SimpleDateFormat(format);// MM-dd-YY HH:mm:ss -> use constants!
		// fmt.setTimeZone(TimeZone.getTimeZone("EST"));
		// System.out.println("formater timezone: " + fmt.getTimeZone());
		return fmt.format(calendar.getTime());
	}

	public static String getFormattedDate(Date date, String format) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);// MM-dd-YY HH:mm:ss -> use constants!
			return fmt.format(date);
		} catch (Exception e) {

		}
		return "";
	}

	public static String getFormattedDate(Date date, String format, String timezone) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);// MM-dd-YY HH:mm:ss -> use constants!
			fmt.setTimeZone(TimeZone.getTimeZone(timezone));
			return fmt.format(date);
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * Description of the Method
	 *
	 * @param y YEAR
	 * @param m MONTH
	 * @param d DAY
	 * @return exact file name
	 */
	public static String parseDate(String y, String m, String d) {
		Date dt = new Date();
		Calendar cl = new GregorianCalendar();
		cl.setTime(dt);

		int dd = cl.get(Calendar.DAY_OF_MONTH);
		int mm = cl.get(Calendar.MONTH);
		int yy = cl.get(Calendar.YEAR);

		if (((y != null) && ((y = y.trim()).length() > 0)) | ((m != null) && ((m = m.trim()).length() > 0)) | ((d != null) && ((d = d.trim()).length() > 0))) {
			try {
				yy = Integer.parseInt(y);
			} catch (Exception ee) {
				System.out.println("Exception Generated " + ee.getMessage());
			}

			try {
				mm = Integer.parseInt(m) - 1;
			} catch (Exception ee) {
				System.out.println("Exception Generated " + ee.getMessage());
			}

			try {
				dd = Integer.parseInt(d);
			} catch (Exception ee) {
				System.out.println("Exception Generated " + ee.getMessage());
			}
		}
		cl.set(Calendar.DAY_OF_MONTH, dd);
		cl.set(Calendar.MONTH, mm);
		cl.set(Calendar.YEAR, yy);

		return (cl.getTime().getTime() + (3600 * 1000 * 24)) + "";
	}

	public static Long convertDateStringToMilliSeconds(String dateString, boolean isStartDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(JLAConstants.DATE_FORMAT_YYYYMMDD);
		formatter.setLenient(false);
		Date date;
		try {
			date = formatter.parse(dateString);
			DateUtils.parseDate(dateString, JLAConstants.DATE_FORMAT_YYYYMMDD); // parse for valid month, year, day integer values
		} catch (Exception e) {
			System.out.println("Date String provided is of invalid format: " + dateString);
			return 0L;
		}

		Calendar cal;
		cal = Calendar.getInstance();
		cal.setTime(date);
		if (isStartDate) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		// String dateInMilSec = new Long(cal.getTimeInMillis()).toString();

		return new Long(cal.getTimeInMillis());
	}

	public static String getCurrentDay() {
		return new SimpleDateFormat(JLAConstants.DATE_FORMAT_YYYYMMDD).format(new Date());
	}

	public static String getCurrentDay(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static int calculateAge(Date birthDate) {
		int years = 0;
		try {

			// create calendar object for birth day
			Calendar birthDayCal = Calendar.getInstance();
			birthDayCal.setTimeInMillis(birthDate.getTime());

			// create calendar object for current day
			long currentTime = System.currentTimeMillis();
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(currentTime);

			// Get difference between years
			years = now.get(Calendar.YEAR) - birthDayCal.get(Calendar.YEAR);
		} catch (Exception e) {

		}
		return years;
	}

	public static Date parseDateString(String dateString) {
		Date date = null;
		if (!StringUtils.isEmpty(dateString)) {
			for (String format : DATE_FORMATS) {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				try {
					date = formatter.parse(dateString);
					break;
				} catch (Exception e) {
					// System.out.println(dateString + " - " + format + " FAILED");
					date = null;
				}
			}
		}
		return date;
	}

}
