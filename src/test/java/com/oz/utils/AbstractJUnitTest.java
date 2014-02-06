package com.oz.utils;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Orbital Zero.
 * Date: 12/09/12
 * Time: 05:57 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public abstract class AbstractJUnitTest extends TestCase{

    public static final Logger logger= LoggerFactory.getLogger(AbstractJUnitTest.class);

    protected final String TEMPLATE_PATH="src/test/resources/templates/jasper/";
    protected final String IMG_PATH="src/test/resources/images/";
    protected final String TMP_PATH=System.getProperty("java.io.tmpdir")+"/";
    protected final Date date= new Date();
    protected ResourcesNames resources;

    /**
     * Write file into disk
     * @param file byte array
     * @param fileName file name to save
     */

    protected void writeFile(byte[] file, String fileName){

        if(file!= null && file.length>0){
            StringBuilder output= new StringBuilder();
            output.append(System.getProperty("java.io.tmpdir")+"/");
            output.append(fileName);

            //write output file
            File salida= new File(output.toString());
            FileOutputStream fo= null;
            try {
                fo = new FileOutputStream(salida);
                fo.write(file);
                fo.close();

                logger.info("file output:{}",salida.getAbsolutePath());

            } catch (FileNotFoundException e) {
                logger.error("Cant process file:",e);
            } catch (IOException e) {
                logger.error("Error to persist file",e);
            }
        }



    }

    /**
     * get random name
     * @param fileExtension
     * @return
     */
    protected final String getFilename(String fileExtension){
        String fileName=date.getTime()+TextUtils.getRandomString(2)+"."+fileExtension;
        logger.info("file name:{}", fileName);
        return fileName;
    }
}


