package com.oz.test;

import com.oz.control.service.LegacyTemplateEngine;
import com.oz.utils.AbstractJUnit4Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.oz.utils.ResourceBeans.S_JASPER_ENGINE;


/**
 * Created with IntelliJ IDEA.
 * Date: 31/05/12
 * Time: 05:44 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TestCreateJasperReport extends AbstractJUnit4Test {

    public static final Logger logger= LoggerFactory.getLogger(TestCreateJasperReport.class);

    @javax.annotation.Resource(name=S_JASPER_ENGINE)
    private LegacyTemplateEngine jasperEngine;




    //        JasperReportsPdfView masterReport = null;
//            try {
//                masterReport = (JasperReportsPdfView) JRLoader.loadObject(archivo);
//            } catch (JRException e) {
//                System.out.println("Error cargando el reporte maestro: " + e.getMessage());
//                System.exit(3);
//            }
////este es el parámetro, se pueden agregar más parámetros
////basta con poner mas parametro.put
//            Map parametro = new HashMap();
//            parametro.put("Grado", Grado);
//            parametro.put("Periodo", Periodo);
//
////Reporte diseñado y compilado con iReport
//            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, conn);
////Se lanza el Viewerde Jasper, no termina aplicación al salir
//            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
//            jviewer.setTitle("Geniz -Reporte");
//            jviewer.setVisible(true);
//
//            JRExporter exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(RutaCreado + "/Boletines " + Grado + ".pdf"));
//            exporter.exportReport();
//        } catch (Exception j) {
//            System.out.println("Mensaje de Error:" + j.getMessage());
//        }
}
