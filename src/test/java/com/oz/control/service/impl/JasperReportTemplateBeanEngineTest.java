package com.oz.control.service.impl;

import com.oz.control.service.LegacyTemplateEngine;
import com.oz.model.dto.AddressDto;
import com.oz.model.dto.BeanDataSource;
import com.oz.model.dto.TemplateData;
import com.oz.utils.AbstractJUnit4Test;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.oz.utils.ResourceBeans.S_BEAN_JASPER_ENGINE;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/08/12
 * Time: 05:48 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class JasperReportTemplateBeanEngineTest extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(JasperReportTemplateBeanEngineTest.class);

    @Resource(name=S_BEAN_JASPER_ENGINE)
    private LegacyTemplateEngine jasperBeanEngine;

    @Test
    public void testMakePdfReportWithBeanDataSource(){
        Map<String,Object> reportData= new HashMap<String,Object>();

        reportData.put("id"      , "12345L");

        Map resources=new LinkedHashMap();

        resources.put("headerSingleFormat"   , LOGO_OZ_PNG);

        logger.debug("Resource Data:{}", reportData);

        AddressDto addressDto=new AddressDto();
        addressDto.setStreet("amores No.1 Col. del valle");

        AddressDto addressDto2=new AddressDto();
        addressDto2.setStreet("amores No.2 Col. del valle");

        BeanDataSource beanDataSource=new BeanDataSource();
        beanDataSource.setName("Carlos A. Garcia");
        beanDataSource.addAddress(addressDto);
        beanDataSource.addAddress(addressDto2);

        logger.debug("DataSource:{}", beanDataSource);

        TemplateData data= new TemplateData();

        logger.info(TEST_READY);

        data.setTemplateName("report2.jasper");

        data.setDataModel(reportData);
        data.setResources(resources);
        data.setBeanDataSource(beanDataSource);

        byte[] pdf =(byte [])jasperBeanEngine.processTemplate(data);

        assertNotNull(pdf);

        writeFile(pdf, "report2.pdf");


    }


}
