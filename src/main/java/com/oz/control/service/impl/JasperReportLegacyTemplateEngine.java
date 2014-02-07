package com.oz.control.service.impl;

import com.oz.control.service.LegacyTemplateEngine;
import com.oz.model.dto.TemplateData;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Date: 27/02/12
 * Time: 01:41 PM
 *
 * @web www.orbitalzero.com , www.orbitalzero.org
 * @autor <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
 */
@Service
public class JasperReportLegacyTemplateEngine implements LegacyTemplateEngine {

    @Autowired
    protected ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(JasperReportLegacyTemplateEngine.class);

    private boolean useDefaultPaths;
    private String defTemplatePath;
    private String defResourcePath;

    public void setUseDefaultPaths(boolean useDefaultPaths) {
        this.useDefaultPaths = useDefaultPaths;
    }

    public void setDefTemplatePath(String defTemplatePath) {
        this.defTemplatePath = defTemplatePath;
    }

    public void setDefResourcePath(String defResourcePath) {
        this.defResourcePath = defResourcePath;
    }

    /**
     * Procesa la platilla con los datos y genera el archivo PDF del reporte con el Jasper Reports
     * @param data datos y recursos para la plantilla
     * @return byte [] con el reporte en arreglo de bytes.
     */
    public Object processTemplate(TemplateData data) {

        //Get template File

        if(useDefaultPaths){
            data.setTemplateLoaderPath(defTemplatePath);
            data.setImageResourcePath(defResourcePath);
        }

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

        }

        // process template with data

        byte[] file = null;
        try{
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(tmpl.getFile());
            file = JasperRunManager.runReportToPdf(masterReport, dataModel, new JREmptyDataSource());
        }
        catch ( JRException e ){
            logger.error("Can't proccess template",e);
        }
        catch( IOException e ){
            logger.error("Can't proccess template",e);
        }

        return file;
    }


}
