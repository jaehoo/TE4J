package com.oz.control.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract definition for reader Services
 * <br/>
 * Date: 27/03/12
 * Time: 07:32 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */

public abstract class AbstractReaderService{

    private static final Logger logger= LoggerFactory.getLogger(AbstractReaderService.class);

    protected MapUtilService mapUtilService;

    public void setMapUtilService(MapUtilService mapUtilService) {
        this.mapUtilService = mapUtilService;
    }

}
