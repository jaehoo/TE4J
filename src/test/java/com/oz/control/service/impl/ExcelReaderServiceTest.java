package com.oz.control.service.impl;

import com.oz.control.service.FileReaderService;
import com.oz.utils.AbstractJUnit4Test;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

import static com.oz.utils.ResourceBeans.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Date: 16/03/12
 * Time: 12:09 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class ExcelReaderServiceTest extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(ExcelReaderServiceTest.class);

    @Resource(name = S_XLS_READER)
    private FileReaderService fileReaderService;

//    @Resource(name= S_MAP_UTILS)
//    private MapUtilService mapUtilService;

    private org.springframework.core.io.Resource file;


    @Before
    public void setUp(){
        file = applicationContext.getResource(TEST_XLS);
    }


    @Test
    public void testGetMapColPosFromFile() throws Exception {

        logger.info( TEST_READY );

        Map colPosition =fileReaderService.getPositions(file.getInputStream());

        assertNotNull( colPosition );

        logger.info( "MapColPositions:{}", colPosition  );

    }

    /**
     * Example to get positions by column name
     * @throws Exception
     */
    @Test
    public void testGetMapColPosFromFile2() throws Exception {

        logger.info( TEST_READY );

        List<String> list= new ArrayList<String>();
        list.add("nombre");
        list.add("apellido materno");

        logger.info("Getting positions from this columns:{}", list);

        Map colPosition =fileReaderService.getPositions(file.getInputStream(), list);

        assertNotNull( colPosition );

        logger.info( "MapColPositions:{}", colPosition  );

        

    }

//    @Test
//    public void testToBeanList() throws Exception {
//
//        logger.info( TEST_READY );
//
//        Map testMap = new LinkedHashMap();
//
//        testMap.put("name", "nombre");
//        testMap.put("ap", "apellido paterno");
//        testMap.put("am", "apellido materno");
//        testMap.put("age", "edad");
//        testMap.put("date", "fecha");
//        testMap.put("value", "actualizar");
//        testMap.put("createdOn", "fecha registro");
//
//
//        Map mapClass= mapUtilService.toMap("{"
//                + BeanReaderTest.class.getName()
//                + ":{'name':'nombre','ap':'apellido paterno','am':'apellido materno'"
//                + ", 'age':'edad', 'date':'fecha','value':'actualizar','createdOn':'fecha registro'}"
//        +"}");
//
//        //List<BeanReaderTest> res=fileReaderService.toBeanList(file.getInputStream(), mapClass, new BeanReaderTest());
//        List res=fileReaderService.toBeanList(file.getInputStream(), testMap);
//
//        assertNotNull(res);
//        printList(res);
//
//
//    }
//
//
//    @Test
//    public void testToBeanList2() throws Exception {
//
//        logger.info( TEST_READY );
//
//        Map mapClass= mapUtilService.toMap(
//                "{"
//                    + BeanReaderTest.class.getName()
//                    + ":{name:'nombre',ap:'apellido paterno',am:'apellido materno'}"
//                    + ","+BeanReaderTest.class.getName()//"A"+
//                    + ":{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}"
//                    //+ ","+ User.class.getName()+":{username:'nombre',email:'fecha'}"
//                +"}");
//
//        //List<BeanReaderTest> res=fileReaderService.toBeanList(file.getInputStream(), mapClass, new BeanReaderTest());
//        List res=fileReaderService.toBeanList(file.getInputStream(), mapClass);
//
//        assertNotNull(res);
//        printList(res);
//
//
//    }

}
