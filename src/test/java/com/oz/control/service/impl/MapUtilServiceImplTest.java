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


    /**
     * Convert JSON String with two or more elements
     */
    @Test
    public void testMultipleJsonToMap(){

        String json="{'User':{a:1,b:2, name:'José Alberto',age:26, value:true},"+" 'Labi':{c:3,d:4}}";

        Map map = mapUtilService.toMap(json);
        logger.info("map size:{}",map.size());
        print(map);

    }

    /**
     * Convert Single JSON String with a one element
     */
    @Test
    public void testJsonToMap(){


        String json="{'user':{name:'José',ap:'Sánchez',am:'GOnzález', " +
                "age:26, date:'28/12/1985',value:true,createdOn:'28/12/1985'}}";

        Map map = mapUtilService.toMap(json);
        logger.info("map size:{}",map.size());
        print(map);

    }


    /**
     * Try to convert json to map from Resource Properties elements, the elements into map are ordered
     * @throws Exception if test Fail into process
     */
    @Test
    public void testJsonToMap2() throws Exception {


        // Case 1

        String jsonValues=testProperties.getProperty("test.json.map.1");
        Map srcMap = mapUtilService.toMap(jsonValues);

        logger.info("map src={}",srcMap);

        Map result =mapUtilService.getPositions(srcMap);
        assertNotNull(result);

        logger.info("result map:{}", result);
        logger.info("******************");


        // Case 2
        jsonValues=testProperties.getProperty("test.json.map.2");
        srcMap = mapUtilService.toMap(jsonValues);

        logger.info("map src={}",srcMap);

        result =mapUtilService.getPositions(srcMap);
        assertNotNull(result);

        logger.info("result map:{}", result);
        logger.info("******************");

    }



    /**
     * Convert Single JSON String with a one element
     */
    @Test
    public void testJsonToDynaBeanMap(){


        String json="{'user':{name:'José',ap:'Sánchez',am:'GOnzález', " +
                "age:26, date:'28/12/1985',value:true,createdOn:'28/12/1985'}}";

        Map map = mapUtilService.toDynaBeanMap(json);
        logger.info("map size:{}",map.size());
        print(map);

    }

    /**
     * Try convert Json to Map with DynaBean elements
     */
    @Test
    public void testJsonToDynaBeanMap2(){

        Map mapConfig= mapUtilService.toDynaBeanMap(
                "{" +
                        "User:{name:'nombre',ap:'apellido paterno',am:'apellido materno'}" +
                        ",Labi:{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}" +
                        "}");

        

        Set keys=mapConfig.entrySet();
        Iterator<Map.Entry> it= keys.iterator();

        while(it.hasNext()){

            Map.Entry entry=it.next();

            boolean isDynaBean=entry.getValue() instanceof DynaBean;
            logger.info("isDynaBean:{} , {}",isDynaBean,entry.getValue().getClass());

            if(isDynaBean){

                DynaBean bean=(DynaBean) entry.getValue();

                Map res=mapUtilService.toMap(bean);

                logger.info("map res:{}",res);

            }

        }

    }

    /**
     * Try to convert complex json to Map
     */
    @Test
    public void testComplexJsonToMap(){

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

        

        for(String json: jsonStrings){

            Map map=mapUtilService.toMap(json);
            assertNotNull(map);
            logger.info("Map :{}",map);
            logger.info("Next Element...");
        }

    }

    /**
     * Try to convert complex json to Map
     */
    @Test
    public void testComplexJsonToMap2(){

        String[] jsonStrings={
                "{com.oz.model.dto.PersonDto:"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno',"
                        +"com.oz.model.dto.AddressDto:{street:'curp'}"
                        +"}"
                        +"}"
        };

        

        Map map= null;
        for(String json: jsonStrings){

            logger.info(json);

            map=mapUtilService.toMap(json);
            assertNotNull(map);
            //logger.info("Map :{}",map);
            logger.info("== Next Element...");
        }

        printMap(map);


    }



    /**
     * Test try to get matched positions between Json Map with nested properties and specific fields to match
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testMatchedPositions() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String jsonString=
                "{"+com.oz.model.dto.PersonDto.class.getName()+":"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno'"
                        +", Address:{street:'curp'}" // This property is skipped
                        +", country:{country:'curp'}"
                        +"}"
                        +"}";

        Map beanMap=mapUtilService.toMap(jsonString);
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

        

        Object result=mapUtilService.getMatchedPositions(beanMap, mapCol);
        logger.info("Result:{}",result);

    }

    /**
     * Test try to get matched positions between Json Map with qualified class name and specific fields to match
     * de de clase por el nombre calificado
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testMatchedPositions2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String jsonString= "{"
                + BeanReaderTest.class.getName()
                + ":{name:'nombre',ap:'apellido paterno',am:'apellido materno'}"
                + ","+BeanReaderTest.class.getName() // NOMBRE CALIFICADO
                + ":{age:'edad', date:'fecha',value:'actualizar',createdOn:'fecha registro'}"
                +"}";

        Map beanMap=mapUtilService.toMap(jsonString);

        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);

        

        Object result=mapUtilService.getMatchedPositions(beanMap, mapCol);

        logger.info("Result:{}",result);


    }

    /**
     * Test try to get matched positions between Json Map with nested properties and specific fields to match
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testMatchedPositions3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String jsonString=
                "{"+com.oz.model.dto.PersonDto.class.getName()+":"
                        +"{"
                        + "name:'nombre',firstName:'apellido paterno'"
                        +", address:{street:'curp'}"
                        +", country:{country:'curp'}"
                        +"}"
                        +"}";

        Map beanMap=mapUtilService.toMap(jsonString);
        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email",3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp",5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);

        

        Object result=mapUtilService.getMatchedPositions(beanMap, mapCol);

        logger.info("Result:{}",result);

    }

    /**
     * Test try to get matched positions between Json Map with qualified class name and specific fields to match
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void testMatchedPositions4() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

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

        Map beanMap=mapUtilService.toMap(json5);
        Map mapCol= new LinkedHashMap();

        mapCol.put("nombre",0);
        mapCol.put("apellido paterno",1);
        mapCol.put("apellido materno",2);
        mapCol.put("email", 3);
        mapCol.put("fecha nacimiento",4);
        mapCol.put("curp", 5);
        mapCol.put("fecha registro",6);
        mapCol.put("nomina",7);


        List<ClassElement> container=mapUtilService.getMatchedPositions(beanMap, mapCol);

        logger.info("size:{}, result:{}", container.size(), container );

        logger.info("{}",container.get(1));
        logger.info("{}", com.oz.control.service.impl.A.class.getDeclaredFields()[2]);
    }

    /**
     *
     */
    @Test
    public void testMergeBeanFieldsVsMapKeysByMapConfig(){
        String jsonConfig="{ PN : {" + com.oz.control.service.impl.l.class.getName()
                + ":{test_1:'test1' , test_2:'test2' , test_3:'test3'}}}";
        Map mapConfig = mapUtilService.toMap(jsonConfig);
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
        Map mapConfig=mapUtilService.toMap(json);
        logger.info("mapConfig={}",mapConfig);

//        List result=mapUtilService.mergeBeanFieldsVsMapKeysByMapConfig(com.oz.control.service.impl.l.class,mapValues,mapConfig);

//        logger.info("result={}",result);
          */
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