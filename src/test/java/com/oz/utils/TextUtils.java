package com.oz.utils;

/**
 * Created by Orbital Zero.
 * Date: 13/09/12
 * Time: 12:19 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TextUtils {

    private static final char[] CHARS={'0','1','2','3','4','5','6','7','8','9' ,'a',
            'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N',
            'O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public static String getRandomString(int longitud){


        char[] conjunto = new char[longitud];
        String pass;

        for(int i=0;i<longitud;i++){
            int el = (int)(Math.random()*CHARS.length);
            conjunto[i] = (char)CHARS[el];
        }
        pass = new String(conjunto);

        return pass;
    }
}
