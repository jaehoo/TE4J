package com.oz.test;

import com.oz.utils.AbstractJUnit4Test;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.oz.utils.ResourceBeans.S_VL_ENGINE;

/**
 * Created with IntelliJ IDEA.
 * Date: 31/05/12
 * Time: 07:45 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TestCreateVelocityTemplates extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(TestCreateVelocityTemplates.class);


    @Resource(name = S_VL_ENGINE )
    private VelocityEngine velocityEngine;

    @Test
    public void testCreateSingleHtmlTemplate(){

        Map model = new HashMap();
        model.put("var1", "JAEHOO");
        model.put("var2", "my_test@mail.com");

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, VL_TEST_TPL, model);

        writeTextFile(text,"simple-template-page.html");

    }

    private void writeTextFile(String source, String fileName){

        try {

            String fileOut=PATH_TMP+fileName;

            logger.info("writing file...");
            logger.info("file: {} ",fileOut);

            PrintWriter out = new PrintWriter(new FileWriter(fileOut));

            // Write text to file
            out.println(source);
            out.close();

            logger.info("file writed -- ok");
        } catch ( IOException e ){
            e.printStackTrace();
        }

    }

}
