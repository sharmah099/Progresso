package com.edu.progressoframework.common;

/** 
 * Defines some commonly used constants.
 */
public class Constants 
{	
	/**
     * Empty string
     */
    public static String Empty = "";
    
    /**
     * blank string
     */
    public static String blankString = " ";
    
    /**
     * un-known
     */
    public static String Unknown = "Unknown";

    /**
     * Web service date format string
     */
    public static String w3DateFormatWithT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * date format without T
     */
    public static String w3DateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * Simple date format
     */
    public static String SimpleDateFormat = "yyyy-MM-dd";

    /**
     * Simple date format with time zone and millisecond
     */
    public static String SimpleDateFormatWithMilliSecAndTimeZone = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";
    
    /**
     * Simple date format with time zone
     */
    public static String SimpleDateFormatWithTimeZone = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Simple date format with offset
     */
    public static String SimpleDateFormatWithOffset = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

    /**
     * Simple date format with out show seconds
     */
    public static String SimpleDateTimeFormatWithoutSeconds = "yyyy-MM-dd'T'HH:mm";

    /**
     * Display date time in UK format
     */
    public static String DisplayDateTimeFormatSimple = "dd MMM yyyy HH:mm";
    
    /**
     * Simple time format
     */
    public static String SimpleTimeFormat = "HH:mm:ss";
    
    /**
     * Display date time in UK format
     */
    public static String DisplayDateTimeFormat = "dd/MM/yyyy HH:mm";

    /**
     * Display date in UK format
     */
    public static String DisplayDateFormat = "dd/MM/yyyy";

    /**
     * Display date time format with full zone
     */
    public static String DATE_FORMAT_FULL_ZONE = "EEE MMM dd HH:mm:ss Z yyyy";
    
    /**
     * Max PIN digits allowed
     */
    public static final int MAX_PIN_LENGTH = 9;

    /**
     *  time (60 min) to check with hard time out for re-authenticate the session
     */
    public static final int SESSION_EXPIRE_CHECK_TIME = 60;

    /**
     * Telerik product key
     */
    public static String telerikProductKey = "1b697b28b0c84dcc8d2b4e3bb432df17";

    /**
     *  Default Duration for planner
     */
    public static final int DEFAULT_DURATION_FOR_PLANNER = 12;

    /**
     *  Maximum Duration Limit for planner
     */
    public static final int MAXIMUM_DURATION_FOR_PLANNER = 48;

    /**
     * Maximum hours ago for planner
     */
    public static final int MAXIMUM_HOUR_AGO_FOR_PLANNER = 22;

}
