package com.oz.control.service.impl;

import com.oz.control.service.FileReaderService;
import com.oz.utils.ClassElement;
import com.oz.utils.Position;
import com.oz.utils.ReflectionUtils;
import com.oz.control.service.AbstractReaderService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
 */
@Service
public class ExcelReaderService extends AbstractReaderService implements FileReaderService {

    private static final Logger logger = LoggerFactory
            .getLogger(ExcelReaderService.class);

    private ReflectionUtils refUtils;

    public void setRefUtils(ReflectionUtils refUtils) {
        this.refUtils = refUtils;
    }

    /**
     *
     * @param is, Stream de datos del archivo.
     *
     * @return
     */
    public Map<String, Integer> getColPos(InputStream is){

        logger.debug("Getting column positions ...");

        Map<String, Integer> mapCols = null;
        Workbook workbook = null;
        Sheet sheet = null;
        Map<String, Integer> res = null;

        try {

            workbook = Workbook.getWorkbook(is);
            sheet = workbook.getSheet(0);

            Cell celda = null;
            int cols = sheet.getColumns();

            if(cols > 0){
                mapCols = new LinkedHashMap<String, Integer>();
            }
            else{
                throw  new NullPointerException("don't have columns to read");
            }

            // Traer todas las columnas
            for (int i = 0; i < cols; i++) {
                celda = sheet.getCell(i,0);
                mapCols.put(celda.getContents(), celda.getColumn());
            }

        } catch (BiffException ex) {
            logger.error("Api exception", ex);
        } catch (IOException ex) {
            logger.error("File to read Stream", ex);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }

            return mapCols;
        }
    }

    /**
     * Obtiene un mapa con el nombre de las columnas y sus posiciones
     * @param is, Stream de datos del Archivo.
     * @param columns
     * @return Mapa con las columnas y la posicion del Archivo.
     * @author <a href="mailto:jaehoo@gmail.com">Alberto Sánchez</a>
     */
    public Map<String, Integer> getColPosByNames(InputStream is, List<String> columns) {

        logger.debug("Getting Col names and positions ...");

        Map<String, Integer> mapCols =
                new LinkedHashMap<String, Integer>();
        Workbook workbook = null;
        Sheet sheet = null;
        Map<String, Integer> res = null;

        try {

            workbook = Workbook.getWorkbook(is);
            sheet = workbook.getSheet(0);

            Cell celda = null;
            int cols = sheet.getColumns();

            // Traer todas las columnas
            for (int i = 0; i < cols; i++) {
                celda = sheet.getCell(i,0);
                mapCols.put(celda.getContents(), celda.getColumn());
            }

            if (columns != null) {

                res = new LinkedHashMap<String, Integer>();
                for (String key : columns) {

                    Integer pos = mapCols.get(key);

                    if (pos != null) {
                        res.put(key, pos);
                    }
                }

                return mapCols=res;
            }

        } catch (BiffException ex) {
            logger.error("Api exception", ex);
        } catch (IOException ex) {
            logger.error("File to read Stream", ex);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }

            return mapCols;
        }

    }


    /**
     * Obtiene en un mapa la cabecera del archivo e indica la posicion de la
     * celda que utiliza.
     * @param sheet fuente de datos
     * @return Mapa con el nombre de la columna y su posicion
     */
    private Map<String, Integer> getColumnPositions(Sheet sheet) {
        return getColumnPositions(sheet, null);
    }


    /**
     * Obtiene en un mapa la cabecera del archivo e indica la posicion de la
     * celda que utiliza.
     *
     * @param sheet fuente de datos
     * @param mapColumns Mapa con los nombres de las columnas a extraer
     * @return Mapa con el nombre de la columna y su posicion
     */
    private Map<String, Integer> getColumnPositions(Sheet sheet
            , Map<String, String> mapColumns) {

        logger.debug("Read first line from file");

        Map<String, Integer> colPositions = null;
        int cols = sheet.getColumns();

        if (cols >= 0) {

            // Comprueba las columnas a extraer

            Cell cell;
            String colName;

            colPositions = new LinkedHashMap<String, Integer>();

            for (int y = 0; y < cols; y++) {

                cell = sheet.getCell(y, 0);
                colName = cell.getContents();

                if (mapColumns == null) {
                    // Traer todas las columnas

                    colPositions.put(colName, y);
                    logger.debug("colName:{}, pos:{}", colName, y);
                } else {
                    // Traer las columnas indicadas

                    Object key = mapColumns.get(colName);
                    if (key != null) {
                        colPositions.put(key.toString(), y);
                    }
                }


            }

            logger.info("colPosition:{}", colPositions);

        } else {
            throw new NullPointerException("Cant read First Line");
        }

        return colPositions;

    }

    /*
     * Convierte el stream de datos de un archivo xls en una lista con las instancias del tipo indicado <tt>T</tt>,
     * utiliza un mapa que indica la referencia entre la propiedad de la clase y el nombre de la columna a leer.
     *
     * @param is, Stream de datos.
     * @param mapClass Mapa con las propiedades de la clase y su equivalente en el archivo xls
     * @param beanClass Tipo de Instancia de la cual se van a tomar las propiedades.
     * @return List con las instancias.
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
//     public <T> List<T> toBeanList(InputStream is
//            , Map<String, Object> mapClass
//            , T beanClass) {
//
//        Workbook workbook = null;
//        Sheet sheet = null;
//
//        Map<String, Integer> mapCol = null;
//
//        Cell celda = null;
//        List<T> beanList = null;
//
//        Field[] fields = null;
//        T pojo = null;
//
//        try {
//
//            workbook = Workbook.getWorkbook(is);
//            sheet = workbook.getSheet(0);
//
//            int cols = sheet.getColumns();
//            int rows = sheet.getRows();
//
//            logger.info("cols:{}, rows:{}, ", cols,rows);
//
//            // Lee las posiciones de las columnas del archivo
//            mapCol = getColumnPositions(sheet);
//
//            //merge map positions
//            List<Position> xy = getPositionsFromMapKeysVsClassFields(mapCol, mapClass, beanClass);
//
//            beanList = new ArrayList<T>();
//
//            logger.debug("rows:{}", rows);
//            Field property = null;
//            String cel = null;
//
//            for (int rCount = 1; rCount < rows; rCount++) {
//                logger.debug("rCount:{}", rCount);
//
//                pojo = (T) beanClass.getClass().newInstance();
//                logger.debug("Instacia creada");
//
//                fields = pojo.getClass().getDeclaredFields();
//
//                for (Position d : xy) {
//                    celda = sheet.getCell(d.getX(), rCount);
//
//                    // Llenar lista con pojos (Reflection)
//                    cel = (celda.getContents());
//
//                    property = fields[d.getY()];
//
//                    logger.debug("value:{} ,set to: {}", cel,property);
//                    if (cel != null) {
//                        refUtils.setValueField(pojo, property, cel);
//                    }
//
//                }
//
//                beanList.add(pojo);
//
//            }
//
//            workbook.close();
//
//            logger.debug("Bean List:{}", beanList);
//
//        } catch (BiffException ex) {
//            logger.error("Api exception", ex);
//        } catch (IOException ex) {
//            logger.error("File to read Stream", ex);
//        } catch (IllegalArgumentException e) {
//            logger.error("", e);
//        } catch (IllegalAccessException e) {
//            logger.error("The bean instance is not accesible", e);
//        } catch (InstantiationException e) {
//            logger.error("Cant create bean instance", e);
//        } finally {
//            if (workbook != null) {
//                workbook.close();
//            }
//        }
//        return beanList;
//    }

    /**
     * Convierte el stream de datos de un archivo de excel plano en una lista de beans de acuerdo
     * a los objetos del mapa que se desean obtener.
     *
     * @param is Stream de datos del archivo.
     * @param beanMap Mapa de datos con las clases y la configuración de sus atributos, el key es el nombre calificado
     * de la clase (foo.bar.MyClass) y el value es el mapa con los nombres de las propiedades de los campos y su nombre
     * equivalente en el archivo.
     *
     * @return  List de beans con las instancias inicializadas.
     * @throws ClassNotFoundException
     *
     * @author <a href="mailto:jaehoo@gmail.com">Alberto Sánchez</a>
     */
    @Override
    public  List toBeanList(InputStream is, Map<String, Object> beanMap)
            throws ClassNotFoundException {

        logger.debug("Convert Data to Bean list...");

        Workbook workbook = null;
        Sheet sheet;
        Cell celda;

        Field[] fields;
        List beanList = null;
        Map<String, Integer> mapCol;

        try {

            workbook = Workbook.getWorkbook(is);
            sheet = workbook.getSheet(0);

            int cols = sheet.getColumns();
            int rows = sheet.getRows();

            logger.debug("cols:{}, rows:{}, ", cols, rows);

            // Lee las posiciones de las columnas del archivo
            mapCol = getColumnPositions(sheet);

            // Empatar las posiciones de las propiedades contra las posiciones de las columnas
            List<ClassElement> positions=mapUtilService.toClassElementList(beanMap,mapCol);

            if(positions.size()<=0) throw new NullArgumentException("No hay objetos por recuperar");

            beanList = new ArrayList();
            List innerList;
            Field property;
            Object nIns;
            String cel;

            if(positions.size()==1){ // One Single Class

                for (int rCount = 1; rCount < rows; rCount++) {
                    logger.debug("rCount:{}", rCount);

                    ClassElement el= positions.get(0);
                        List<Position> posList = ( List<Position> ) el.getPositions();
                        logger.debug("Record:{}, Positions:{}",rCount,posList);

                        logger.debug("Make instance...");
                        Class insClass=el.getClazz();
                        fields= insClass.getDeclaredFields();
                        nIns=insClass.newInstance();

                        for (Position d : posList) {
                            celda = sheet.getCell(d.getX(), rCount);

                            cel = (celda.getContents());
                            property = fields[d.getY()];

                            if (cel != null) {
                                refUtils.setValueField(nIns, property, cel);
                            }

                            //

                        }

                    beanList.add(nIns);

                }
            }
            else if(positions.size()>1){ // Multiple Classes

                for (int rCount = 1; rCount < rows; rCount++) {
                    logger.debug("rCount:{}", rCount);
                    innerList=new ArrayList();

                    for(ClassElement el: positions){
                        List<Position> posList = ( List<Position> ) el.getPositions();
                        logger.debug("Record:{}, Positions:{}",rCount,posList);

                        logger.debug("Make instance...");
                        Class insClass=el.getClazz();
                        fields= insClass.getDeclaredFields();
                        nIns=insClass.newInstance();

                        for (Position d : posList) {
                            celda = sheet.getCell(d.getX(), rCount);

                            cel = (celda.getContents());
                            property = fields[d.getY()];

                            if (cel != null) {
                                refUtils.setValueField(nIns, property, cel);
                            }
                        }

                        innerList.add(nIns);

                        if(el.getParentIndex()==-1){
                            beanList.add(nIns);
                        }

                        if(el.getParentIndex()!=-1 && el.getParentIndex()>=0){

                            Object oel=innerList.get(el.getParentIndex());
                            Field[] innerFields=oel.getClass().getDeclaredFields();

                            refUtils.setValueField(oel,innerFields[el.getFieldPosition()],nIns);
                        }


                    }

                    //beanList.add(innerList);
                }
            }

            workbook.close();

        } catch (BiffException ex) {
            logger.error("Api exception", ex);
        } catch (IOException ex) {
            logger.error("File to read Stream", ex);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("The bean instance is not accesible", e);
        } catch (InstantiationException e) {
            logger.error("Cant create bean instance", e);
        } finally {
            if ( workbook != null ) {
                workbook.close();
            }
        }
        return beanList;
    }
}


