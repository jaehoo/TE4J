
package com.oz.model.dto;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Orbital Zero.
 * Date: 13/09/12
 * Time: 06:22 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class JasperTestDataSource implements JRDataSource {

    private static final Logger logger= LoggerFactory.getLogger(JasperTestDataSource.class);

    private int index=-1;
    private TestReportData reportData;

    public TestReportData getReportData() {
        return reportData;
    }

    public void setReportData(TestReportData reportData) {
        this.reportData = reportData;
    }

    @Override
    public boolean next() throws JRException {
        //return ++index<reportData.getUsers().size();
        return index++<0;
        //return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {

        logger.info("getting field:{}",jrField.getName());

        if("title".equals(jrField.getName())){
            return reportData.getTitle();
        }
        else if("impressDate".equals(jrField.getName())){
            return  reportData.getImpressDate();
        }
        else if("users".equals(jrField.getName())){

            return new JRBeanCollectionDataSource(reportData.getUsers());
        }
        else{
            return null;
        }

    }
}
