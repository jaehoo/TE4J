package com.oz.control.service;

import com.oz.model.dto.Template;
import com.oz.utils.AbstractJUnit4Test;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import static com.oz.utils.ResourceBeans.*;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 07:29 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public class PDFTemplateEngineTest extends AbstractJUnit4Test {

    public static final Logger LOGGER = LoggerFactory.getLogger(PDFTemplateEngineTest.class);

    @Autowired
    protected ApplicationContext applicationContext;

    @Resource(name = S_FT_ENGINE)
    private TemplateEngine<Template, Map<String, Object>, byte[] > templateEngine;


    /**
     * Test to make single PDF
     * @throws Exception
     */
    @Test
    public void testProcess() throws Exception {

        LOGGER.info("Getting template:{}",JR_TEST);
        org.springframework.core.io.Resource resource=(org.springframework.core.io.Resource)
                applicationContext.getResource(JR_TEST);

        // Set Template
        Template tmpl = new Template();
        tmpl.setResource(resource.getFile());
        templateEngine.setTemplate(tmpl);

        // Set Data Model
        Map<String, Object> model= new HashMap<String, Object>();

        model.put("nombre","Amaya");
        model.put("fisrtName","Ivan");
        templateEngine.setDataModel(model);

        // process
        byte[] pdf = templateEngine.process();

        //write output into file
        writeFile(pdf, "single.pdf");

    }

    /**
     * Test to make single PDF
     * @throws Exception
     */
    @Test
    public void testProcess1() throws Exception {

        LOGGER.info("Getting template:{}",JR_TEST);
        org.springframework.core.io.Resource resource=(org.springframework.core.io.Resource)
                applicationContext.getResource(JR_TEST);

        // Set Template
        Template tmpl = new Template();
        tmpl.setResource(resource.getFile());
        templateEngine.setTemplate(tmpl);

        // Set Data Model
        Map<String, Object> model= new HashMap<String, Object>();
        model.put("id"      , "12345L");
        model.put("headerSingleFormat"   , LOGO_OZ_PNG);

        LOGGER.debug("report data:{}",model);


        model.put("nombre","Amaya");
        model.put("fisrtName","Ivan");
        templateEngine.setDataModel(model);

        // process
        byte[] pdf = templateEngine.process();

        //write output into file
        writeFile(pdf, "single.pdf");

    }

}
