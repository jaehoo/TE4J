package com.oz.control.service.impl;

import com.oz.model.dto.TemplateData;
import com.oz.utils.AbstractJUnit4Test;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.oz.utils.ResourceBeans.S_JASPER_ENGINE;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Orbital Zero.
 * Date: 10/09/12
 * Time: 11:24 AM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class JasperReportTemplateEngineTest extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(JasperReportTemplateEngineTest.class);

    @Resource(name=S_JASPER_ENGINE)
    private JasperReportLegacyTemplateEngine jasperEngine;

    @Test
    public void testMakeSinglePdfReport(){

        byte[] pdf = null;
        Map<String, Object> param= new HashMap<String, Object>();

        param.put("nombre","Amaya");
        param.put("fisrtName","Ivan");

        try {

            // Load Resource
            org.springframework.core.io.Resource resource=(org.springframework.core.io.Resource) applicationContext.getResource(JR_TEST);
            //Object src=this.getClass().getClassLoader().getResourceAsStream("/testReport.jasper");
            //File report=resource.getFile();

            logger.info("template exist?:{}",resource.getFile().exists());
            logger.info("Abs path:{}",resource.getFile().getAbsolutePath());

            // Make Report
            //JasperReport masterReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource(rutaInforme));
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(resource.getFile());

            pdf = JasperRunManager.runReportToPdf(masterReport, param, new JREmptyDataSource());

            writeFile(pdf, "test.pdf");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testMakePdfReportWithImages(){

        Map<String,Object> reportData= new HashMap<String,Object>();

        reportData.put("idAbm"      , 12345L);
        reportData.put("name"       , "José Alberto Sánchez");
        reportData.put("birthday"   , new Date());
        reportData.put("rfc"        , "ABCD123456HDFBB02");
        reportData.put("nationality", "MEXICANA");
        reportData.put("address"    , "AV. Siempre viva =)");
        reportData.put("colony"     , "... sub");
        reportData.put("poblation"  , "México DF");
        reportData.put("estate"     , "México");
        reportData.put("zipCode"    , 03520);
        reportData.put("telephone"  , "512437125365128");
        reportData.put("college"    , "SENIOR--");

        reportData.put("institute"  , "UNIVERSIDAD ICEL");
        reportData.put("dateIn"     , new Date());
        reportData.put("position"   , "DEVELOPER");
        reportData.put("nomina"     , "7615236hbkjbjk");
        reportData.put("jobEmail"   , "jaehoo@gmail.com");

        reportData.put("dateExam"   , new Date());
        reportData.put("figure"     , "Sotial Media Expert =P!!");

        logger.debug("report data:{}",reportData);

        Map resources=new LinkedHashMap();

        resources.put("headerSingleFormat"   , LOGO_OZ_PNG);
        resources.put("bgSingleFormat"       , LOGO_OZ_JPG);

        logger.debug("Resource Data:{}", reportData);

        TemplateData data= new TemplateData();

        logger.info(TEST_READY);

        /**
         * if TemplateLoaderPath is not defined, this code use useDefaultPaths of the appcontext
         * Examples values:
         *  for Webapps use this:
         *      setTemplateLoaderPath("/WEB-INF/templates/jasper/");
         *      setImageResourcePath("/images/");
         */
        //data.setTemplateLoaderPath("/templates/jasper/");
        //data.setImageResourcePath("/images/");

        data.setTemplateName("single_format.jasper");

        data.setDataModel(reportData);
        data.setResources(resources);

        // Create Report
        byte[] pdf =(byte [])jasperEngine.processTemplate(data);

        assertNotNull(pdf);

        logger.info("report created!!");

        writeFile(pdf, "single_format.pdf");

    }



}
