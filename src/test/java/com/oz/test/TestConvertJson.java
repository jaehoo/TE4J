package com.oz.test;

import com.oz.control.service.MapUtilService;
import com.oz.utils.AbstractJUnit4Test;
import com.oz.utils.BeanReaderTest;
import net.sf.ezmorph.MorphUtils;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import org.apache.commons.beanutils.DynaBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.oz.utils.ResourceBeans.S_MAP_UTILS;

/**
 * Test to convert JSON String to Java Map
 *
 * Date: 29/03/12
 * Time: 12:36 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TestConvertJson extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(TestConvertJson.class);

    @Resource(name = S_MAP_UTILS)
    private MapUtilService mapUtilService;

    /**
     * Convert JSON String with two or more elements
     */
    @Test
    public void testMultipleJsonToMap(){

        String json="{'User':{a:1,b:2, name:'José Alberto',age:26, value:true},"+" 'Labi':{c:3,d:4}}";
        //String json="{'user':{name:'José',ap:'Sánchez',am:'GOnzález', age:26, date:'28/12/1985',
        // value:true,createdOn:'28/12/1985'}}";

        Map map = mapUtilService.convertJsonToMap(json);
        logger.info("map size:{}",map.size());
        print(map);

    }

    /**
     * Convert Single JSON String with a one element
     */
    @Test
    public void testSingleJsonToMap(){


        String json="{'user':{name:'José',ap:'Sánchez',am:'GOnzález', " +
                "age:26, date:'28/12/1985',value:true,createdOn:'28/12/1985'}}";

        Map map = mapUtilService.convertJsonToMap(json);
        logger.info("map size:{}",map.size());
        print(map);

    }

    /**
     * To print Map content
     * @param map input
     */
    public void print(Map map){

        Set keys=map.entrySet();
        Iterator<Map.Entry> it=keys.iterator();

        MorpherRegistry morpherRegistry = new MorpherRegistry();
        MorphUtils.registerStandardMorphers(morpherRegistry);

        while (it.hasNext()){

            Map.Entry entry= it.next();


            if(entry instanceof DynaBean){
                DynaBean dynaBean=(DynaBean) entry.getValue();

                logger.info("{}:{}",dynaBean.getClass(),dynaBean);

                morpherRegistry.registerMorpher( new BeanMorpher( BeanReaderTest.class, morpherRegistry ) );
                BeanReaderTest myBeanTest = (BeanReaderTest)
                        morpherRegistry.morph( BeanReaderTest.class, dynaBean );

                logger.info("MyBean:{}", myBeanTest);
            }
            else {
                logger.info("{} {}:{}",new Object[]{entry.getValue().getClass(),entry.getKey(),entry.getValue()});
            }

        }

    }


}
