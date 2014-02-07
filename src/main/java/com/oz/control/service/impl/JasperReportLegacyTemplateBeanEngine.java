package com.oz.control.service.impl;

import com.oz.control.service.LegacyTemplateEngine;
import com.oz.model.dto.TemplateData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: carlos
 * Date: 10/08/12
 * Time: 01:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class JasperReportLegacyTemplateBeanEngine implements LegacyTemplateEngine {

    @Autowired
    protected ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(JasperReportLegacyTemplateBeanEngine.class);

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
    @Override
    public Object processTemplate(TemplateData data) {
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
                    logger.debug("Exists:{}",resource.getFile().exists());
                    dataModel.put(pairs.getKey(),resource.getFile().getAbsolutePath());//TODO CREATE NEW MAP ONLY FOR IMAGES
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
            if(data.getBeanDataSource()!=null){
                List ds=new ArrayList();
                ds.add(data.getBeanDataSource());
                file = JasperRunManager.runReportToPdf(masterReport, dataModel, new JRBeanCollectionDataSource(ds));
            }else
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