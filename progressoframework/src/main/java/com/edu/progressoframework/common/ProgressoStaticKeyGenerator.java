package com.edu.progressoframework.common;


import com.edu.progressoframework.app.BaseApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that returns generated static keys for various
 * uses within the progresso application. This does not ensure
 * security of application but enables defence in depth.
 */
public class ProgressoStaticKeyGenerator
{
	/**
	 * Singleton instance of the static key generator
	 */
    private static ProgressoStaticKeyGenerator singletonInstance;

	/**
	 * Returns the singletonInstance of the static key generator
	 */
    public static ProgressoStaticKeyGenerator getInstance() {
 
        if ( singletonInstance == null ) {
            synchronized ( ProgressoStaticKeyGenerator.class ) {
                if ( singletonInstance == null ) {
                    singletonInstance = new ProgressoStaticKeyGenerator();
                }
            }
        }
 
        return singletonInstance;
    }
 
	/**
	 * Private constructor
	 */
    private ProgressoStaticKeyGenerator()
	{
    }
    
    /**
     * Returns a static key of type string
     */
    public String getString()
    {
    	String name = BaseApplication.class.getName();
    	String modifiedName = name.replace(".", "");
    	return scramble(modifiedName);	
    }
    
    /**
	 * Function to return the password
	 * as string
	 */
	public String getTagPasswordString()
	{		
		String str = BaseApplication.class.getSimpleName();
		System.out.println(str);
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray()) {
		    sb.append((int)c);
		}
			
		return sb.substring(0, 8);		
	}
	
	  /**
     * Function called to scramble the user database password
     */
    private String scramble(String str)
    {
		//create an array as required by the function
		char[] charArr = str.toCharArray();
		
		int strLength = str.length();
		
		List<Character> charList = new ArrayList<Character>();
		for (int i = 0; i < strLength; i++) {
			charList.add(charArr[i]);
		}
		
		//rotate it for half the distance of string length
		int distance = strLength/2;
		Collections.rotate(charList, distance);
		
		//swap the characters
		for (int i = 0; i < distance; i++) {
			Collections.swap(charList, i, strLength - i - 1);
		}
		
		StringBuilder sb = new StringBuilder();
		for (Character c : charList) {
			sb.append(c);
		}		
		
		return sb.toString();
    }
}
