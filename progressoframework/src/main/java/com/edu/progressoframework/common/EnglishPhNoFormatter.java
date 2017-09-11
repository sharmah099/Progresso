package com.edu.progressoframework.common;


/**
 * Phone number formatter for EN locale
 */
public class EnglishPhNoFormatter extends BasePhNoFormatter
{
    public EnglishPhNoFormatter() 
    {    
    }
    
    public String format(String phoneNumber)
    {
        phoneNumber = getDigitsOnly(phoneNumber);
        
        char[] phNumber = phoneNumber.toCharArray();
        
        if (phNumber.length < 4 || phNumber[0] != '0')
        {
            return null;
        }
    
        // If first character isn't a zero then not valid
        // We test length to ensure we don't go outside the index range        
        
        String retVal = null;
        
        switch (phNumber[1])
        {
               case '5':
                   retVal = format("^(05\\d{3})(\\d{6})$", 
                                   phoneNumber, 
                                   new int[] { 5 });
                   break;

               case '1':
                   if (phNumber[2] != '1' && phNumber[3] != '1')
                   {
                       // normal 01xxx xxxxx(x) numbers
                       retVal = format("^(01\\d{3})(\\d{5,6})$", 
                                       phoneNumber, 
                                       new int[] { 5 });
                   }
                   else
                   {
                       // 011x or 01x1 
                       retVal = format("^(01\\d\\d)(\\d{3})(\\d{4})$", 
                                       phoneNumber, 
                                       new int[] { 4, 3 });
                   }
                   break;

               case '7':
                   retVal = format("^(07\\d{3})(\\d{6})$", 
                                   phoneNumber, 
                                   new int[] { 5 });
                   break;

               case '2':
                   retVal = format("^(02\\d)(\\d{4})(\\d{4})$", 
                                   phoneNumber, 
                                   new int[] { 3, 4 });
                   break;


               case '8':
                   String regex = null;
                   if (phoneNumber.startsWith("0800"))
                   {
                       regex = "^(0800)(\\d{6,7}|1111)$"; // Child Line breaks the 'standard' rules (0800 1111)
                   }
                   else if (phoneNumber.startsWith("0845"))
                   {
                       regex = "^(0845)(\\d{7}|4647)$";   // NHS Direct breaks the 'standard' rules (0845 4647)
                   }
                   else
                   {
                       regex = "^(08\\d\\d)(\\d{7})$";
                   }
                   retVal = format(regex, 
                                   phoneNumber, 
                                   new int[] { 4 });
                   break;

               case '3':
                   retVal = format("^(03\\d\\d)(\\d{3})(\\d{4})$", 
                                   phoneNumber, 
                                   new int[] { 4, 3 });
                   break;
        }
        
        return retVal;
    }
} 