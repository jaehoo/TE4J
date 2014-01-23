package com.oz.control.service.impl;

import com.oz.control.service.FileReaderService;
import com.oz.control.service.MapUtilService;
import com.oz.model.dto.User;
import com.oz.utils.AbstractJUnit4Test;
import com.oz.utils.BeanReaderTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.oz.utils.ResourceBeans.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * Date: 21/03/12
 * Time: 02:02 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TextPlainReaderServiceTest extends AbstractJUnit4Test {

    private static final Logger logger=
            LoggerFactory.getLogger(TextPlainReaderServiceTest.class);

    @Resource(name = S_TXT_READER)
    private FileReaderService frs;

    @Resource(name= S_MAP_UTILS)
    private MapUtilService mapClassUtilService;


    @Resource(name= S_MAP_UTILS)
    private MapUtilService mapUtilService;

    @Test
    public void testGetMapColPosFromFile() throws Exception {

        org.springframework.core.io.Resource fileCSV=(org.springframework.core.io.Resource)
                applicationContext.getResource(TEST_CSV);


        logger.info(TEST_READY);

        List<String> getCols= new ArrayList<String>();

        getCols.add("NOMBRE");

        Map colPositions=frs.getColPos(fileCSV.getInputStream());

        logger.info("MapCol positions:{}",colPositions);
        assertNotNull(colPositions);

        colPositions=frs.getColPosByNames(fileCSV.getInputStream(), getCols);

        logger.info("MapCol positions:{}",colPositions);
        assertNotNull(colPositions);

    }

    /**
     * Prueba la creacion de la lista de beans a partir de un archivo de texto plano con una sola clase
     * @throws Exception
     */
    @Test 
    public void testToBeanList() throws Exception {

        
        org.springframework.core.io.Resource fileCSV=(org.springframework.core.io.Resource)
                applicationContext.getResource(TEST_CSV);

        logger.info(TEST_READY);

        Map mapClass= mapUtilService.convertJsonToMap(
                "{"
                    + BeanReaderTest.class.getName()
                    + ":{'name':'nombre','ap':'apellido paterno','am':'apellido materno'"
                    + ", 'age':'edad', 'date':'fecha','value':'actualizar','createdOn':'fecha registro'}"
                +"}");

        List<BeanReaderTest> list=frs.toBeanList(fileCSV.getInputStream(), mapClass);

        assertNotNull(list);
        printList(list);

        logger.info("Bean List:{}",list);
        
    }

    /**
     *  Prueba la creacion de la lista de beans a partir de un archivo de texto plano con varias classes
     * @throws Exception
     */
    @Test
    public void testToBeanList2() throws Exception {

        
        org.springframework.core.io.Resource fileCSV=(org.springframework.core.io.Resource)
                applicationContext.getResource(TEST_CSV);

        logger.info(TEST_READY);

        Map mapClass= mapUtilService.convertJsonToMap(
                "{"
                    + BeanReaderTest.class.getName()
                    + ":{name:'nombre',ap:'apellido paterno',am:'apellido materno'}"
                    + ","+BeanReaderTest.class.getName()//"A"+
                    + ":{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}"
                    //+ ","+ User.class.getName()+":{username:'nombre',email:'fecha'}"
                +"}");

        List list=frs.toBeanList(fileCSV.getInputStream(), mapClass);

        assertNotNull(list);
        printList(list);

        
    }

    @Test
    public void testToBeanList3() throws Exception {

        
        org.springframework.core.io.Resource fileCSV=(org.springframework.core.io.Resource)
                applicationContext.getResource(TEST_LOAD_CSV);

        logger.info(TEST_READY);

        String json6="{com.oz.model.dto.User:{" +
                "name:'nombre',firstName:'apellido paterno',lastName:'apellido materno'" +
                ",birthday:'fecha nacimiento',email:'email', curp:'curp'" +
                ",person:{name:'nombre',firstName:'apellido paterno',lastName:'apellido materno'}}}";

        String json="{"
                    + User.class.getName()+":{username:'curp', email:'email'}"
                    //+ ","+LaborInformationDto.class.getName()+":{area:'curp'}"
//                        +":{"
//                        + "username:'curp', email:'email'"
//                        + ",person:{name:'nombre',firstName:'apellido paterno',lastName:'apellido materno'}"
//                        +"}"

                +"}";

        Map mapClass= mapUtilService.convertJsonToMap(json6);

        List list=frs.toBeanList(fileCSV.getInputStream(), mapClass);

        assertNotNull(list);
        printList(list);

        
    }


}
