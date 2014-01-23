package com.oz.control.service.impl;

import com.oz.control.service.MapUtilService;

import com.oz.utils.AbstractJUnit4Test;
import com.oz.utils.BeanReaderTest;
import com.oz.utils.ClassElement;
import org.apache.commons.beanutils.DynaBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import static com.oz.utils.ResourceBeans.*;

import static org.junit.Assert.assertNotNull;

/**
 *
 *
 * Date: 12/03/12
 * Time: 04:34 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class MapUtilServiceImplTest extends AbstractJUnit4Test {

    private static Logger logger = LoggerFactory
            .getLogger(MapUtilServiceImplTest.class);

    @Resource(name= S_MAP_UTILS)
    private MapUtilService mapUtilService;

    @Resource(name = R_TEST_PROP)
    private Properties testProperties;

    @Test
    public void testConvertJsonToMap(){

        String[] jsonStrings={
                "{a:1,b:2, name:'José Alberto',age:26, value:true}"
                , "{'User':{a:1,b:2, name:'José Alberto',age:26, value:true}}"
                , "{com.oz.util.BeanMorpherTest:{name:'nombre',ap:'apellido paterno',am:'apellido materno'}}"
                ,
                "{"
                    +"User:{name:'nombre',ap:'apellido paterno',am:'apellido materno'}" +
                    ",Labi:{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}" +
                "}"
        };

        logger.info(TEST_READY);

        for(String json: jsonStrings){

            Map map=mapUtilService.convertJsonToMap(json);
            assertNotNull(map);
            logger.info("Map :{}",map);
            logger.info("Next Element...");
        }

    }

    @Test
    public void testConvertJsonToMap2(){

        String[] jsonStrings={
                "{com.oz.model.dto.PersonDto:"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno',"
                        +"com.oz.model.dto.AddressDto:{street:'curp'}"
                        +"}"
                        +"}"
        };

        logger.info(TEST_READY);

        Map map= null;
        for(String json: jsonStrings){

            logger.info(json);

            map=mapUtilService.convertJsonToMap(json);
            assertNotNull(map);
            //logger.info("Map :{}",map);
            logger.info("== Next Element...");
        }

        printMap(map);


    }

    @Test
    public void testConvertJsonToRawMap(){

        Map mapConfig= mapUtilService.convertJsonToRawMap(
                "{" +
                        "User:{name:'nombre',ap:'apellido paterno',am:'apellido materno'}" +
                        ",Labi:{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}" +
                "}");

        logger.info(TEST_READY);

        Set keys=mapConfig.entrySet();
        Iterator<Map.Entry> it= keys.iterator();

        while(it.hasNext()){

            Map.Entry entry=it.next();

            boolean isDynaBean=entry.getValue() instanceof DynaBean;
            logger.info("isDynaBean:{} , {}",isDynaBean,entry.getValue().getClass());

            if(isDynaBean){

                DynaBean bean=(DynaBean) entry.getValue();

                Map res=mapUtilService.convertDynaBeanToMap(bean);

                logger.info("map res:{}",res);

            }

        }

    }

    /**
     * Prueba la conversión de cadenas Json en Beans con propiedades anidadas
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void TestsMergePositions() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String jsonString=
                "{"+com.oz.model.dto.PersonDto.class.getName()+":"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno'"
                        +", Address:{street:'curp'}"
                        +", country:{country:'curp'}"
                        +"}"
                        +"}";

        Map beanMap=mapUtilService.convertJsonToMap(jsonString);
        logger.info(jsonString);
        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);

        logger.info(TEST_READY);

        Object result=mapUtilService.toClassElementList(beanMap, mapCol);

        logger.info("Result:{}",result);


    }

    /**
     * Prueba la conversión de cadenas Json en Listas de Beans
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void TestsMergePositions2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String jsonString= "{"
                + BeanReaderTest.class.getName()
                + ":{name:'nombre',ap:'apellido paterno',am:'apellido materno'}"
                + ","+BeanReaderTest.class.getName()//"A"+
                + ":{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}"
                //+ ","+ User.class.getName()+":{username:'nombre',email:'fecha'}"
                +"}";

        Map beanMap=mapUtilService.convertJsonToMap(jsonString);

        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);

        logger.info(TEST_READY);

        Object result=mapUtilService.toClassElementList(beanMap, mapCol);

        logger.info("Result:{}",result);


    }

    @Test
    public void testJson() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String jsonString=
                "{"+com.oz.model.dto.PersonDto.class.getName()+":"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno'"
                        +", address:{street:'curp'}"
                        +", country:{country:'curp'}"
                        +"}"
                        +"}";

        Map beanMap=mapUtilService.convertJsonToMap(jsonString);
        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);

        logger.info(TEST_READY);

        Object result=mapUtilService.toClassElementList(beanMap, mapCol);

        logger.info("Result:{}",result);

    }

    @Test
    public void testJson3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String json1="{"+com.oz.control.service.impl.A.class.getName()+":"
                +"{nom:'nombre',ap:'apellido paterno'}"
                +"}";

        String json2="{"
                + com.oz.control.service.impl.A.class.getName()+":"
                + "{nom:'nombre',ap:'apellido paterno'}"
                + ","+com.oz.control.service.impl.B.class.getName()+":"
                +"{nom:'nombre',ap:'apellido paterno'}"
                +"}";

        String json3="{"
                + com.oz.control.service.impl.A.class.getName()+":"
                + "{nom:'nombre',ap:'apellido paterno'}"
                + ","+com.oz.control.service.impl.A.class.getName()+":"
                +"{nom:'nombre',ap:'apellido paterno'}"
                +"}";

        String json4="{"+com.oz.control.service.impl.A.class.getName()+":"
                    +"{nom:'nombre', ap:'apellido paterno'"
                    +", pb:{nom:'nombre',ap:'apellido paterno'}"
                +"}"
                + ","+com.oz.control.service.impl.A.class.getName()+":"
                +"{nom:'nombre', ap:'apellido paterno'"
                +", pc:{nom:'nombre',ap:'apellido paterno'}"
                    +"}"
                +"}";

        String json5="{"+com.oz.control.service.impl.A.class.getName()+":"
                +"{pb:'nombre', pc:'apellido paterno'},"
                + com.oz.control.service.impl.A.class.getName()+":"
                +"{pb:'nombre', pc:'apellido paterno'},"
                + com.oz.control.service.impl.B.class.getName()+":"
                +"{nom:'nombre', ap:'apellido paternos'}"
                +"}";

        String json6="{"+com.oz.model.dto.PersonDto.class.getName()+":{" +
                "name:'nombre',firstName:'apellido paterno',lastName:'apellido materno'" +
                ",birthday:'fecha nacimiento',email:'email', curp:'curp'" +
                ",person:{name:'nombre',firstName:'apellido paterno',lastName:'apellido materno'}}}";

        Map beanMap=mapUtilService.convertJsonToMap(json5);
        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);


        List<ClassElement> container=mapUtilService.toClassElementList(beanMap, mapCol);

        logger.info("size:{}, result:{}", container.size(), container );

        logger.info("{}",container.get(1));
        logger.info("{}", com.oz.control.service.impl.A.class.getDeclaredFields()[2]);
    }

//    @Test
//    public void testGetMapKeyIndexes(){
//        Map map=new LinkedHashMap();
//        Map map1=new LinkedHashMap();
//        Map map2=new LinkedHashMap();
//        List list=new ArrayList();
//
//        map2.put("test4.2.1","first_1");
//        map2.put("test4.2.2","second_1");
//        map2.put("test4.2.3","qwerty_2");
//
//        map1.put("test4.1","first_1");
//        map1.put("test4.2",map2);
//        map1.put("test4.3","qwerty_2");
//
//        map.put("test1","first");
//        map.put("test2","second");
//        map.put("test3","qwerty");
//        map.put("test4",map1);
//        map.put("test5",list);
//        map.put("test6","ñlkjhg");
//
//        list.add("1");
//        list.add("2");
//        list.add(map2);
//
//        String jsonConfig="{ PN : {" + com.oz.control.service.impl.l.class.getName() + ":{test_1:'test1' , test_2:'test2' , test_3:'test3'}},PA : {" + com.oz.control.service.impl.l.class.getName() + ":{test_1:'test3' , test_2:'test3' , test_3:'test1'}}}";
//        Map mapConfig = mapUtilService.convertJsonToMap(jsonConfig);
//
//        List<IndexedBeanMap> mapWrappers = mapUtilService.getListKeyIndexes(mapConfig);
//        logger.info("recursive classes={}", mapWrappers);
//
//    }

    @Test
    public void testMergeBeanFieldsVsMapKeysByMapConfig(){
        String jsonConfig="{ PN : {" + com.oz.control.service.impl.l.class.getName() + ":{test_1:'test1' , test_2:'test2' , test_3:'test3'}}}";
        Map mapConfig = mapUtilService.convertJsonToMap(jsonConfig);
        logger.info("mapInfo={}",mapConfig);

       /* Map mapValues=new LinkedHashMap();
        mapValues.put("test1","first");
        mapValues.put("test2","second");
        mapValues.put("test3","qwerty");
        logger.info("mapValues={}",mapValues);
        String json=//"{"
               // + com.oz.control.service.impl.l.class.getName()+":"
                 "{test_1:'test1', test_3:'test2'}"  ;
//                + ","+com.oz.control.service.impl.B.class.getName()+":"
//                +"{nom:'nombre',ap:'apellido paterno'}"
                //+"}";
        Map mapConfig=mapUtilService.convertJsonToMap(json);
        logger.info("mapConfig={}",mapConfig);

//        List result=mapUtilService.mergeBeanFieldsVsMapKeysByMapConfig(com.oz.control.service.impl.l.class,mapValues,mapConfig);

//        logger.info("result={}",result);
          */
    }

    /**
     * Prueba el mencanismo para obtener las posiciones númericas de las keys contenidas en un mapa
     * @throws Exception if test Fail into process
     */
    @Test
    public void testGetMapPositions() throws Exception {

        // Case 1

        String jsonValues=testProperties.getProperty("test.json.map.1");
        Map srcMap = mapUtilService.convertJsonToMap(jsonValues);

        logger.info("map src={}",srcMap);
        logger.info(TEST_READY);

        Map result =mapUtilService.getMapPositions(srcMap);
        assertNotNull(result);

        logger.info("result map:{}", result);

        // Case 2
        jsonValues=testProperties.getProperty("test.json.map.2");
        srcMap = mapUtilService.convertJsonToMap(jsonValues);

        logger.info("map src={}",srcMap);
        logger.info(TEST_READY);

        result =mapUtilService.getMapPositions(srcMap);
        assertNotNull(result);

        logger.info("result map:{}", result);

    }
}

class l{
    public String test_1;
    public String test_2;
    public String test_3;
    
}
// CLASES DE PRUEBA

class A{
    public String nom;
    public String ap;
    public B pb;
    public C pc;
    public D pd;
    public A pa;

}

class B{

    public C pc;
    public String nom;
    public String ap;

}

class C{

    public String nom, ap;
    public D pd;
    String algo;

}

class D{
    String algo;
    String algo2;
    public A pa;

}