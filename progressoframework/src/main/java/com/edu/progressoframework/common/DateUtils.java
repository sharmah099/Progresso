package com.edu.progressoframework.common;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class that provides date related utility functions
 */
public class DateUtils
{
    /**
     * Returns the difference in the two date (laterDate - earlierDate)
     * in the specified date part (days, months, years)
     * @param earlierDate	The first date
     * @param laterDate		The second date, if null, then current is assumed
     * @param timeUnit		Time units in which to return the difference
     * @return				The date difference in specified time unit
     * 
     * Note: this function will only return the difference between the dateParts,
     * and not any higher measures. e.g. between jan 2010 and february 2012, this
     * function will only reutrn 1 for difference in months.
     */
    public static int getDateDiff(Date earlierDate, Date laterDate, int datePart)
    {
        Calendar calDateOne = Calendar.getInstance();
        calDateOne.setTime(earlierDate);

        if (laterDate == null) {
            laterDate = new Date();
        }
        Calendar calDateTwo = Calendar.getInstance();
        calDateTwo.setTime(laterDate);

        //Get difference between the specified date parts
        return calDateTwo.get(datePart) - calDateOne.get(datePart);
    }

    /**
     * returns the day of the week this date lies on.
     * 0 = sunday
     * 1 = monday
     * etc
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * returns the date of the month this date lies on.
     * 0 = sunday
     * 1 = monday
     * etc...
     * 
     * Note: these values are not the same as Calendar.SUNDAY etc
     * as they do not use 0-based indices
     */
    public static int getDayOfMonth(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns a short string representing the day the date lies on.
     * MON, TUE, etc...
     */
    public static String getShortDayString(Date date)
    {
        return getShortDayString(getDayOfWeek(date));
    }

    /**
     * Returns a short string representing the day the date lies on.
     * MON, TUE, etc...
     */
    public static String getShortDayString(int i)
    {
        String ret;
        switch (i % 7)
        {
            case 0: ret = "SUN"; break;
            case 1: ret = "MON"; break;
            case 2: ret = "TUE"; break;
            case 3: ret = "WED"; break;
            case 4: ret = "THU"; break;
            case 5: ret = "FRI"; break;
            case 6: ret = "SAT"; break;
            default: ret = "";
        }

        return ret;
    }

    /**
     * This function returns a new date which has been 'increased'
     * by 'daysToAdd' days.
     * 
     * @param date the start date
     * @param daysToAdd the number of days to add to the start date
     * @return a new Date instance daysToAdd days after the start date
     */
    public static Date addDays(Date date, int daysToAdd)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, daysToAdd);
        return c.getTime();
    }

    /**
	 * This function is used to get current date and time
	 * excluding seconds and milliseconds
	 */
	public static Date getDateWithHHMM()
	{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return date;
	}

    /**
     * This function returns a new date which has been 'increased'
     * by 'hoursToAdd' hours.
     * 
     * @param date the start date
     * @param hoursToAdd the number of hours to add to the start date
     * @return a new Date instance hoursToAdd hours after the start date
     */
    public static Date addHours(Date date, int hoursToAdd)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (hoursToAdd > 24) {
            c.add(Calendar.DATE, hoursToAdd / 24);
            hoursToAdd %= 24;
        }

        if (c.get(Calendar.HOUR_OF_DAY) + hoursToAdd > 23) {
            c.add(Calendar.DATE, 1);
        }

        c.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        return c.getTime();
    }
    
    /**
     * This function returns a new date which has been 'increased'
     * by 'minsToAdd' mins
     * 
     * @param date the date which needs to be incremented
     * @param minsToAdd mins to add
     * @return a new date instance with 'minsToAdd' mins after the date
     */
    public static Date addMinutes(Date date, int minsToAdd)
    {
    	Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minsToAdd);
        
        return c.getTime();        
    }

    /**
     * Returns the difference in minutes between two dates.
     * 
     * Note: due to the wierdness of java's Date class this function should
     * not be trusted to work between dates which lie on different days
     * (promarily due to complications such as DST).
     */
    public static int getDifferenceMinutes(Date date1, Date date2)
    {
        return (int) (Math.abs(date1.getTime() - date2.getTime()) / (60.0*1000.0));
    }

    /**
     * returns a short string representing the hours and minutes between
     * the two given Dates, in the format 2h / 10m
     */
    public static String getDurationString(Date date1, Date date2)
    {
        int minutesDiff = getDifferenceMinutes(date1, date2);
        int hoursDiff = minutesDiff / 60;
        minutesDiff -= hoursDiff * 60;
        String durationText = "";
        //        if (hoursDiff > 0)
        //        {
        durationText += hoursDiff + "h";
        //        }
        //        if (hoursDiff > 0 && minutesDiff > 0)
        //        {
        durationText += " / ";
        //        }
        //        if (minutesDiff > 0)
        //        {
        durationText += minutesDiff + "m";
        //        }

        return durationText;
    }

    /**
     * returns the time in millis at midnight of the date
     * supplied by the time in milliseconds
     */
    public static long getStartOfDay(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getStartOfDay(Date date)
    {
        return getStartOfDay(date.getTime());
    }

    public static long getEndOfDay(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndOfDay(Date date)
    {
        return getEndOfDay(date.getTime());
    }
    
    /**
     * Function called to get the date part of passed date
     */
    public static long getDatePart(long timeInMillis)
    {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    
	/**
	 * Function called to get the UTC equivalent of the date passed in the parameter
	 */
	public static Date getUTCDate(long dateInMilis)
	{
		Date utcDate = new Date();
    	try {
    		Date date = new Date(dateInMilis);
    		
    		// first get the 'UTC' date in string format
    		SimpleDateFormat sdf = new SimpleDateFormat(Constants.w3DateFormatWithT, Locale.getDefault());
    		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    		String dateString = sdf.format(date);
    	
    		// convert back the 'UTC' date string to date
			SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.w3DateFormatWithT, Locale.getDefault());
			utcDate = dateFormatter.parse(dateString);
    		
    	}
    	catch(ParseException ex) {
    		
    	}
    	
    	return utcDate;		
	}
		
	/**
	 * Function return the date in string format based on the pattern passed
	 */
	public static String getDateString(Date date, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		String dateString = sdf.format(date);

		return dateString;
	}
	

	/**
	 * Function called to convert the date which is in string 
	 * into date format
	 */
	public static Date getDate(String strDate, String pattern)
	{  
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
		try {  
		    date = format.parse(strDate);  
		} 
		catch (ParseException e) {
		    e.printStackTrace();  
		}
		
		return date;
	}

    /**
     * Function called to get the passed date and time
     */
    public static Date getDateWithHHMM(String strTime, String pattern)
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            date = format.parse(strTime);
        }
        catch (ParseException e) {
        }

        Calendar initialDate = Calendar.getInstance();
        initialDate.setTime(date);

        return initialDate.getTime();
    }

	/**
	 * Function called to get the current date and passed time
	 * 
	 */
	public static Date getCurrentDateWithHHMM(String strTime, String pattern)
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
		try {
			date = format.parse(strTime);
		}
		catch (ParseException e) {
		}
		
		Calendar initialDate = Calendar.getInstance();
		initialDate.setTime(date);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, initialDate.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, initialDate.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, initialDate.get(Calendar.SECOND));

		return cal.getTime();
	}

	/**
	 * Function called to return the date pattern string
	 */
	public static String getDateInDeviceDateFormat(Context context, Date date)
	{
		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
		String datePattern = ((SimpleDateFormat)dateFormat).toPattern();
		
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern, Locale.getDefault());
		return formatter.format(date);
	}
	
	/**
	 * Function called to return the time pattern string 
	 */
	public static String getTimeInDeviceTimeFormat(Context context, Date date)
	{
		DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
		String timePattern = ((SimpleDateFormat)timeFormat).toPattern();

		SimpleDateFormat formatter = new SimpleDateFormat(timePattern, Locale.getDefault());
		return formatter.format(date);
	}
	
	/**
	 * Function called to return the date time pattern string 
	 */
	public static String getDateTimeInDeviceDateFormat(Context context, Date date)
	{
		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
		DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
		String dateTimePattern = ((SimpleDateFormat)dateFormat).toPattern() + " " + ((SimpleDateFormat)timeFormat).toPattern();

		SimpleDateFormat formatter = new SimpleDateFormat(dateTimePattern, Locale.getDefault());
		return formatter.format(date);
	}	
	
    /**
     * This function returns a minimum date time value
     */
    public static Calendar minDateTimeValue()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR, 00);
        calendar.set(Calendar.MINUTE, 00);
        return calendar;
    }
    
    /**
     * This function adds a date/time component to the given date
     * and returns the result
     * @param date		The date to which to ad
     * @param field		The field to add (date, day, hours, mins etc.)
     * @param value		The value to add
     * @return			The results of addition
     */
    public static Calendar add(Calendar date, int field, int value)
    {
    	date.add(field, value);
    	return date; 
    }
    
    /**
     * Function to convert UTC date to Local date
     */
    public static Date getLocalDate(Date utcDate)
    {
    	/*SimpleDateFormat sdf = new SimpleDateFormat(Constants.w3DateFormatWithT);
		sdf.setTimeZone(TimeZone.getDefault());
		Date localDate = new Date(utcDate.getTime() + TimeZone.getDefault().getOffset(utcDate.getTime()));*/
    	long utcMiliseconds = utcDate.getTime();
    	GregorianCalendar cal = new GregorianCalendar();
    	cal.setTimeInMillis(utcMiliseconds);
    	Date localDate = new Date(utcMiliseconds + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));
		
		return localDate;
    }

    /**
     * Function call to return extract date from date time
     */
    public static Date getDateWithoutTime(Date date) {
        Date extractDate = null;
        DateFormat formatter = new SimpleDateFormat(Constants.SimpleDateFormat);
        try {
            extractDate = formatter.parse(formatter.format(date));
        } catch (ParseException ex) {
            //TODO
        }
        return  extractDate;
    }

    /**
     *Function called to get date in EEEE dd MMMM yyyy format
     */
    public static Date getDate(Date date)
    {
        Date extractDate = null;
        DateFormat formatter =  new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
        try {
            extractDate = formatter.parse(formatter.format(date));
        } catch (ParseException ex) {
            //TODO
        }
        return  extractDate;
    }

    /**
     * Function called to get date based on duration added
     */
    public static Date getEndDate(Date date, int hoursToAdd, String pattern)
    {
        Date endDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hoursToAdd);
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        try{
            endDate = formatter.parse(formatter.format(calendar.getTime()));
        }
        catch (ParseException ex) {
            //TODO
        }
        return endDate;
    }

    /*
     * Function called to remove time from time in given format only
     */
    public static Date removeTime(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
