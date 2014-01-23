package com.oz.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 29/03/12
 * Time: 11:36 AM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class BeanReaderTest implements Serializable{

    private String a;
    private String b;
    private Integer c;
    private Integer d;

    private String name;
    private Integer age;
    private String ap;
    private String am;
    private Date date;
    private Boolean value;
    private java.sql.Date createdOn;

    public BeanReaderTest(){}

    @Override
    public String toString() {
        return "BeanReaderTest{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", d=" + d +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", ap='" + ap + '\'' +
                ", am='" + am + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", createdOn=" + createdOn +
                '}';
    }
}
