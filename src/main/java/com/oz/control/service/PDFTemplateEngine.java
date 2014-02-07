package com.oz.control.service;

import com.oz.model.dto.Template;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 06:33 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public class PDFTemplateEngine implements TemplateEngine<Template, Map<String, Object>, byte[] > {

    public static final Logger LOGGER = LoggerFactory.getLogger(PDFTemplateEngine.class);

    private Template template;
    private Map<String,Object> dataModel;

    @Override
    public void setTemplate(Template template) {
        this.template= template;
    }

    @Override
    public void setDataModel(Map<String, Object> dataModel) {
        this.dataModel = dataModel;
    }


    @Override
    public byte[] process() {

        byte[] file = null;
        try{
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(template.getResource());
            file = JasperRunManager.runReportToPdf(masterReport, dataModel, new JREmptyDataSource());
        }
        catch ( JRException e ){
            LOGGER.error("Can't process template",e);
        }

        return file;

    }
}
