package com.oz.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Proporciona soporte para las pruebas con Spring y Junit,
 * define recursos comunes, proporciona el accesso al
 * Bean Factory de Spring.
 *
 * Date: 6/07/11
 * Time: 07:28 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:appcontext.xml"})
public abstract class AbstractJUnit4Test extends AbstractJUnit4SpringContextTests
        //AbstractTransactionalJUnit4SpringContextTests
        //AbstractTestNGSpringContextTests
        //AbstractJUnit4SpringContextTests
        //AbstractTransactionalJUnit4SpringContextTests
{
    private static final Logger logger= LoggerFactory.getLogger(AbstractJUnit4Test.class);

    protected static final String TEST_START = "TEST START";
    protected static final String TEST_READY = "******************************* TEST READY";
    protected static final String TEST_END = "TEST END";
    protected static String SEPARATOR="=================================================================";

    //Paths
    protected String PATH_TMP = System.getProperty("java.io.tmpdir")+"/";
    protected String PATH_IMG_VL = "classpath:images/";

    protected static final String LOGO_OZ_PNG="logo_oz.png";
    protected static final String LOGO_OZ_JPG="logo_oz.jpg";

    protected static final String VL_TEST_TPL="test.vm";





    /**
     *  Example to Load Resources with Spring AppContext:
     *
     *  - from abs path:        "file:/home/jaehoo/doc.pdf"
     *
     *  - from relative Maven path:
     *      "file:src/test/resources/files/MyFILE.xls"
     *
     *  - from classpath (Maven [ WITH ] modules):
     *      "classpath:templates/jasper/test_report.jasper"
     *
     *  - from classpath (Maven [ WITHOUT ] modules):
     *      "classpath:src/test/resources/templates/jasper/test_report.jasper"
     *      "file:src/main/webapp/images/"
     */

    protected String TEST_XLS = "classpath:files/TEST.xls";
    protected String TEST_CSV = "classpath:files/TEST.csv";
    protected String TEST_LOAD_CSV = "classpath:files/TEST_LOAD.csv";
    protected String JR_TEST = "classpath:templates/jasper/test_report.jasper";

    @Before
    public void before(){
        logger.info(TEST_START);
        logger.info(SEPARATOR);

    }

    @After
    public void after(){
        logger.info(SEPARATOR);
        logger.info(TEST_END);
    }

    protected void printList(List list){

        for(Object o: list){

            if(o instanceof List){
                printList((List)o);
            }else {
                logger.info("{}",o);
            }

        }

    }

    protected void printMap(Map map){

        if(map!=null){
            Iterator<Map.Entry> elements=map.entrySet().iterator();

            while(elements.hasNext()){

                Map.Entry e=elements.next();

                if(e.getValue() instanceof Map){
                    printMap((Map)e.getValue());
                }else {
                    logger.info("Entry:{} , value:{}", e.getKey(), e.getValue());
                }

            }
        }
        else {
            logger.warn("the Map is EMPTY!!!!");
        }


    }

}