package com.oz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 9/09/11
 * Time: 04:49 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class ReflectionUtils {

    private static final Logger logger= LoggerFactory
            .getLogger(ReflectionUtils.class);

    private DateFormat dateFormat;

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Coloca una cadena de texto como valor en el atributo de una instancia,
     * realiza el casting de deacuerdo con el tipo del del atributo.
     * @param beanInstance - instancia del bean a alterar
     * @param property - propiedad del bean
     * @param value - valor a colocar en la propiedad
     * @author <a href="mailto:jaehoo@gmail.com">Alberto Sánchez</a>
     * @throws IllegalAccessException si el valor no puede ser colocado en el field
     */
    public void setValueField(Object beanInstance, Field property, String value)
            throws IllegalAccessException {

        logger.debug("Value:{} ,set to:{}",value, property);

        try{

            if (property.getType().equals(String.class)) {

                property.setAccessible(true);
                property.set(beanInstance, value);

            } else if (property.getType().equals(int.class)
                    || property.getType().equals(Integer.class)) {
                if(value.isEmpty()){
                    property.setAccessible(true);
                    property.set(beanInstance, null);
                }else{
                    property.setAccessible(true);
                    property.set(beanInstance, Integer.valueOf(value));
                }

            } else if (property.getType().equals(double.class)
                    || property.getType().equals(Double.class)) {
                if(value.isEmpty()){
                    property.setAccessible(true);
                    property.set(beanInstance, null);
                }else {
                    property.setAccessible(true);
                    property.set(beanInstance, Double.valueOf(value));
                }
            } else if (property.getType().equals(boolean.class)
                    || property.getType().equals(Boolean.class)) {

                property.setAccessible(true);

                if(value.length()==1){
                    property.set(beanInstance, value.equals("1") ||value.equalsIgnoreCase("y") );
                }else {
                    property.set(beanInstance, Boolean.valueOf(value));
                }

            } else if (property.getType().equals(Date.class)) {
                property.setAccessible(true);
                property.set(beanInstance, dateFormat.parse(value));

                //OLD Version
                //property.set(beanInstance, Date.parse(value));
            }
            else if(property.getType().equals(java.sql.Date.class)) {
                property.setAccessible(true);
                property.set(beanInstance, new java.sql.Date(dateFormat.parse(value).getTime()));
            }
//            else if(property.getType() instanceof Object){ // for other types
//                property.setAccessible(true);
//                property.set(beanInstance, value);
//            }

        }catch(Exception ex){
            logger.error("Cant cast value into property", ex);
        }
    }


    /**
     * Coloca el objeto indicado como valor en el atributo de una instancia,
     * @param beanInstance - instancia del bean a alterar
     * @param property - propiedad del bean
     * @param value - valor a colocar en la propiedad
     *
     * @throws IllegalAccessException si el valor no puede ser colocado en el field
     */
    public void setValueField(Object beanInstance, Field property, Object value)
            throws IllegalAccessException {

        logger.debug("Value:{} ,set to:{}",value, property);

        try{

           if(property.getType() instanceof Object){ // for other types
                property.setAccessible(true);
                property.set(beanInstance, value);
           }

        }catch(Exception ex){
            logger.error("Cant cast value into property", ex);
        }
    }
}


