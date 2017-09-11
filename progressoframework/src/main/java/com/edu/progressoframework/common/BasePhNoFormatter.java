package com.edu.progressoframework.common;

import java.util.regex.Pattern;

/**
 * Base class for phone number formatter
 */
public abstract class BasePhNoFormatter 
{
    public abstract String format(String phoneNumber);
    
    /**
     * This method is used to format the given phone number
     * validate against given regular expression and format
     * it
     * @param regex - Regular expression
     * @param phoneNumber - Phone number
     * @param lengths - The group counts
     * @return Formatted phone number
     */
    public String format(String regex, String phoneNumber, int[] lengths)
    {
    	String retVal = null;
        
        boolean isSuccess = Pattern.matches(regex, phoneNumber);
        
        if (isSuccess)
        {
            if(lengths.length == 1)
            {
               int grpOneEndIndex = lengths[0];
               
               retVal = (phoneNumber.substring(0, grpOneEndIndex) 
                                + " " + 
                                phoneNumber.substring(grpOneEndIndex));
            }
            else if (lengths.length == 2)
            {
               int grpOneEndIndex = lengths[0];
               int grpTwoEndIndex = grpOneEndIndex + lengths[1];
               
               retVal = (phoneNumber.substring(0, grpOneEndIndex) 
                                + " " + 
                                phoneNumber.substring(grpOneEndIndex, grpTwoEndIndex) 
                                + " " + 
                                phoneNumber.substring(grpTwoEndIndex));
            }
        }
        
        return retVal;
    }
    
    /**
     * This method is used to get zone of 3 different countries
     * and returns phone formatter based on that.
     * @return BasePhNoFormatter
     */
    public static BasePhNoFormatter getInstance()
    {
        BasePhNoFormatter formatter = new EnglishPhNoFormatter();
        
        return formatter;
    }   

    /**
     * This method is used to extract digits only
     * @param str - Input string
     * @return String containing digits only
     */
    protected String getDigitsOnly(String str)
    {
        char[] chars = str.toCharArray();
        
        String retVal = "";
        for (int i=0; i < chars.length; i++)
        {
            if (chars[i] >= 48 && chars[i] <= 57)
            {
                //Allow 0 to 9 numerics only
                retVal += chars[i];
            }
        }
        
        return retVal;
    }
}