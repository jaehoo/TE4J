package com.oz.test;

import com.oz.model.dto.JasperTestDataSource;
import com.oz.model.dto.TestReportData;
import com.oz.model.dto.TestUser;
import com.oz.utils.AbstractJUnitTest;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created by Orbital Zero.
 * Date: 12/09/12
 * Time: 01:35 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TestCreateSingleReport extends AbstractJUnitTest {

    public static final Logger logger= LoggerFactory.getLogger(TestCreateSingleReport.class);

    private JREmptyDataSource emptyDataSource= new JREmptyDataSource();

    /**
     * Case 1, Make single hello world report... get file bytes and save into specific path
     */
    @Test
    public void testSingleJasperReport(){

        byte[] file = null;

        try{

            // Load Jasper Template
            String jsTemplate=TEMPLATE_PATH+ resources.JR_HELLO.getName();

            File templateReport= new File(jsTemplate);
            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());

            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);
            file = JasperRunManager.runReportToPdf(masterReport, dataModel, emptyDataSource);

            writeFile(file, getFilename("pdf"));

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }


    /**
     * Case 2, Make single hello world report... save into dest file with Jasper API
     */
    @Test
    public void testSingleJasperReport2(){

        try{

            // Load Jasper Reources
            String jsTemplate=TEMPLATE_PATH+ resources.JR_HELLO.getName();
            File templateReport= new File(jsTemplate);

            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());

            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(masterReport, dataModel, emptyDataSource);

            String output=System.getProperty("java.io.tmpdir")+"/"+getFilename("pdf");

            JasperExportManager.exportReportToPdfFile(jasperPrint, output);

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }

    /**
     * Case 3, Make single hello world report... save into dest file with Jasper API and retrive report bytes
     */
    @Test
    public void testSingleJasperReport3(){

        byte[] file = null;

        try{

            // Load Jasper Reources
            String jsTemplate=TEMPLATE_PATH+ resources.JR_HELLO.getName();
            File templateReport= new File(jsTemplate);

            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());

            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, dataModel, emptyDataSource);
            file = JasperExportManager.exportReportToPdf(jasperPrint);

            //persist
            writeFile(file, getFilename("pdf"));

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }

    /**
     * Make report with Static Images, using parameters to set images path...
     *
     * case 1: set absolute image path <b>WITH</b> parameter (include images path and file name)
     * case 2: set partial absolute path,<b>WITHOUT</b> file name (only imgae path, the file name is write into report)
     *
     * Include example watermark
     */
    @Test
    public void testImageReport(){

        byte[] file = null;

        try{

            // Load Jasper reousrce
            String jsTemplate=TEMPLATE_PATH+ resources.JR_IMAGE.getName();
            String image=IMG_PATH+ resources.IMG_LOGO.getName();

            File templateReport= new File(jsTemplate);
            File imageFile= new File(image);
            File imagePath= new File(IMG_PATH);

            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());
            logger.info("exists:{} - {}",imageFile.exists(),imageFile.getAbsolutePath());
            logger.info("exists:{} - imagepath:{}", imagePath.exists(), imagePath.getAbsolutePath());

            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");
            dataModel.put("image_logo", imageFile.getAbsolutePath());
            dataModel.put("IMG_PATH", imagePath.getAbsolutePath()+"/");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);
            file = JasperRunManager.runReportToPdf(masterReport,dataModel, emptyDataSource);

            writeFile(file, getFilename("pdf"));

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }


    /**
     * Make report with Bean List, using bean attributes as jasper fields
     */
    @Test
    public void testBeanReport(){

        byte[] file = null;

        try{

            List<TestUser> users= new ArrayList<TestUser>();

            for(int i=1 ; i<=10 ; i++){
                users.add(new TestUser((long)i,"user"+i, "pass"+i,"user@mail.com"+i));
            }

            // Load Jasper reousrce
            String jsTemplate=TEMPLATE_PATH+ resources.JR_BEAN.getName();

            File templateReport= new File(jsTemplate);

            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());


            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);

            file = JasperRunManager.runReportToPdf(masterReport, dataModel, new JRBeanCollectionDataSource(users));

            writeFile(file, getFilename("pdf"));

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }

    /**
     * Make report with Nested Bean, using bean attributes as jasper fields with Jasper Data Source
     *
     * Case 1: using table
     */
    @Test
    public void testBeanReport2(){

        byte[] file = null;

        try{


            JasperTestDataSource ds= new JasperTestDataSource();
            TestReportData dataBean= new TestReportData();
            List<TestUser> users= new ArrayList<TestUser>();

            for(int i=1 ; i<=10 ; i++){
                users.add(new TestUser((long)i,"user"+i, "pass"+i,"user@mail.com"+i));
            }


            dataBean.setTitle("Dynamic Bean Report 2");
            dataBean.setImpressDate(new Date());
            dataBean.setUsers(users);
            dataBean.setTableDataSource(new JRBeanCollectionDataSource(users));


            ds.setReportData(dataBean);

            // Load Jasper reousrce
            String jsTemplate=TEMPLATE_PATH+ resources.JR_BEAN2.getName();
            File templateReport= new File(jsTemplate);

            logger.info("exists:{} - {}",templateReport.exists(),templateReport.getAbsolutePath());


            // Put Data Model
            Map dataModel= new LinkedHashMap();
            dataModel.put("userName", "jaehoo");

            // Make report
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(templateReport);

            file = JasperRunManager.runReportToPdf(masterReport, dataModel, ds);
            //file = JasperRunManager.runReportToPdf(masterReport, dataModel, new JRBeanCollectionDataSource(dataBean.getUsers()));

            writeFile(file, getFilename("pdf"));

        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }

    }


    /*
    logger.info("Test single report");


    String template=null;

    if(data.getTemplateLoaderPath()!=null){
        template=data.getTemplateLoaderPath()+data.getTemplateName();
    }else{
        template=data.getTemplateName();
    }

    logger.debug("using template:{}",template);

    Resource tmpl=applicationContext.getResource(template);

    // get Resource Data Model and put into template

    Map dataModel= data.getDataModel();

    logger.debug("Data model:{}",dataModel);

    if(data.getResources()!=null && data.getResources().size()>0){

        //logger.debug("Adding images resources:{}",dataModel);

        Iterator<Map.Entry<String,String>> it= data.getResources().entrySet().iterator();

        logger.debug("image resource path:{}",data.getImageResourcePath());

        while(it.hasNext()){

            Map.Entry<String,String> pairs = (Map.Entry)it.next();

            Resource resource= null;

            String resourcePath=data.getImageResourcePath();


            if(resourcePath!=null){
                resourcePath+=pairs.getValue();
            }
            else{
                resourcePath=pairs.getValue();
            }

            logger.debug("Resource path:{}", resourcePath);

            resource=applicationContext.getResource(resourcePath);

            try{
                dataModel.put(pairs.getKey(),resource.getFile().getAbsolutePath());
            }
            catch ( IOException e){
                logger.warn("Can't load resource:{} ", pairs.getKey());
            }
        }

    }*/

    // process template with data


}
