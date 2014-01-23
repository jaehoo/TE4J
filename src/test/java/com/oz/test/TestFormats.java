package com.oz.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Date: 11/03/12
 * Time: 01:50 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TestFormats extends junit.framework.TestCase{

    public static final Logger logger= LoggerFactory.getLogger(TestFormats.class);

    @Test
    public void testFormats() {

        DateFormat df = DateFormat.getDateInstance();

        SimpleDateFormat dfa=new SimpleDateFormat("yyMMdd");

        DecimalFormat dfo= new DecimalFormat("#");
        dfo.setMinimumIntegerDigits(5);

        logger.info("DECIMAL FORMAT:{}" + dfo.format(3L));

        Calendar cal=Calendar.getInstance();

        System.out.println(cal.get(1));
        System.out.println(cal.get(2));
        System.out.println(cal.get(3));
        System.out.println(cal.get(4));
        System.out.println(cal.get(5));
        System.out.println(cal.get(6));

        logger.info("Simple Date Format:{}", dfa.format(cal.getTime()));

    }


}
