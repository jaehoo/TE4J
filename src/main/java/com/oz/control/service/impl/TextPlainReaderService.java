package com.oz.control.service.impl;

import com.oz.control.service.FileReaderService;
import com.oz.control.service.MapUtilService;
import com.oz.utils.ClassElement;
import com.oz.utils.Position;
import com.oz.utils.ReflectionUtils;
import com.csvreader.CsvReader;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Funciones para la lectura y extracción de datos de los archivos de texto.
 *
 */
@Service
public class TextPlainReaderService  implements FileReaderService {

    private static Logger logger= LoggerFactory.getLogger(TextPlainReaderService.class);

    protected MapUtilService mapUtilService;
    private ReflectionUtils refUtils= null;
    private char delimiter;
    private static final char DEFAULT_DELIMITER=',';


    public void setMapUtilService(MapUtilService mapUtilService) {
        this.mapUtilService = mapUtilService;
    }

    public void setRefUtils(ReflectionUtils refUtils) {
        this.refUtils = refUtils;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Get column positions from excel input stream
     * @param is, stream data from file.
     *
     * @return
     */
    public Map<String, Integer> getPositions(InputStream is){
        return getPositions(is, null);
    }

    /**
     * Obtiene un mapa con el nombre de las columnas y las posiciones seleccionadas por su nombre
     * @param is, Stream de datos del archivo.
     * @param columns Nombres de las columnas a recuperar
     *
     * @return Nombres de las columnas y su posición númerica.
     */
    public Map<String, Integer> getPositions(InputStream is, List<String> columns) {

        logger.debug("Getting positions by column names...");

        Map<String, Integer> res = null;
        CsvReader reader= null;
        InputStreamReader read= null;

        try {
            read = new InputStreamReader(is);
            reader = new CsvReader(read);

            res= getColumnPositions(reader,columns);

            logger.debug("ColPositions, size:{} , \n {}",res.size(),res);
        } catch (IOException e) {

            logger.error("Error al leer el Archivo", e);

        } finally {
            if (reader != null) {
                reader.close();
            }
            return res;
        }
    }

    /**
     * Convierte el stream de datos de un archivo de texto plano en una lista de beans de acuerdo
     * a los objetos del mapa que se desean obtener.
     *
     * @param is Stream de datos del archivo.
     * @param beanMap Mapa de datos con las clases y la configuración de sus atributos, el key es el nombre calificado
     * de la clase (foo.bar.MyClass) y el value es el mapa con los nombres de las propiedades de los campos y su nombre
     * equivalente en el archivo.
     *
     * @return Lista con el arreglo de datos de las instancias creadas.
     
     */
    @Override
    public List toBeanList(InputStream is, Map<String, Object> beanMap) throws ClassNotFoundException {

        logger.debug("Convert Data to Bean list...");

        Map<String, Integer> mapCol;
        Field[] fields;
        InputStreamReader read;
        CsvReader reader = null;
        List beanList = null;

        try {

            read= new InputStreamReader(is);
            reader= new CsvReader(read);

            if(delimiter!= '\u0000'){
                reader.setDelimiter(delimiter);
            }
            else {
                reader.setDelimiter(DEFAULT_DELIMITER);
            }

            // Lee las posiciones de las columnas del archivo
            mapCol = getColumnPositions(reader);

            // Empatar las posiciones de las propiedades contra las posiciones de las columnas
            List<ClassElement> positions=mapUtilService.getMatchedPositions(beanMap, mapCol);

            if(positions.size()<=0) throw new NullArgumentException("No hay objetos por recuperar");

            beanList = new ArrayList();
            List innerList;
            List innerList2;
            Field property;
            Object nIns;
            int i=0;

            if(positions.size()==1){ // One Single Class

               while(reader.readRecord()){

                   ClassElement el=positions.get(0);

                   List<Position> posList = ( List<Position> ) el.getPositions();
                   logger.debug("Record:{}, Positions:{}",i,posList);

                   Class insClass=el.getClazz();
                   fields= insClass.getDeclaredFields();
                   nIns=insClass.newInstance();

                   for (Position d : posList) {

                       String content=reader.get(d.getX());
                       property = fields[d.getY()];

                       refUtils.setValueField(nIns,property,content);

                   }
                   beanList.add(nIns);

                   i++;
                }
            }
            else if(positions.size()>1){ // Multiple Classes

                while(reader.readRecord()){

                    innerList=new ArrayList();
                    innerList2= new ArrayList();


                    for(ClassElement el: positions){

                        List<Position> posList = ( List<Position> ) el.getPositions();
                        logger.debug("Record:{}, Positions:{}",i,posList);

                        Class insClass=el.getClazz();
                        fields= insClass.getDeclaredFields();
                        nIns=insClass.newInstance();

                        for (Position d : posList) {

                            String content=reader.get(d.getX());
                            property = fields[d.getY()];

                            refUtils.setValueField(nIns,property,content);

                        }
                        innerList.add(nIns);

                        if(el.getParentIndex()==-1){
                            innerList2.add(nIns);
                           // beanList.add(nIns);
                        }

                        if(el.getParentIndex()!=-1 && el.getParentIndex()>=0){

                            Object oel=innerList.get(el.getParentIndex());
                            Field[] innerFields=oel.getClass().getDeclaredFields();

                            refUtils.setValueField(oel,innerFields[el.getFieldPosition()],nIns);
                        }
                    }

                    beanList.add(innerList2);
                    i++;
                }

            }


        } catch (IOException ex) {
            logger.error("Cant read Stream", ex);
        } catch (IllegalAccessException e) {
            logger.error("The bean instance is not accesible", e);
        } catch (InstantiationException e) {
            logger.error("Cant create bean instance", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return beanList;

    }

    /**
     * Obtiene el nombre de las columnas y sus posiciones númericas del archivo.
     * @param reader Fuente de lectura
     *
     * @return
     * @throws java.io.IOException
     */
    private Map<String, Integer> getColumnPositions(CsvReader reader) throws IOException {
        return getColumnPositions(reader, null);
    }

    /**
     * Obtiene un mapa con el nombre de la columnas  y  su posición  númerica en el archivo
     * @param reader
     * @param columns
     * @return
     * @throws java.io.IOException
     */
    private Map<String, Integer> getColumnPositions(CsvReader reader
            ,List<String> columns) throws IOException {

        logger.debug("Read first line from file");

        Map<String, Integer> mapCols = null;
        Map<String, Integer> res = null;

        int col;

        // Leer el número total de columnas
        if (reader!=null && reader.readRecord()) {
            col = reader.getColumnCount();
        } else {
            throw new NullPointerException("Sin datos");
        }

        logger.debug("Columns:{}", col);

        mapCols = new LinkedHashMap<String, Integer>();

        if (columns != null) {
            // Traer todas las columnas indicadas en el mapa

            for (int i = 0; i < col; i++) {
                mapCols.put(reader.get(i), i);
            }

            res = new LinkedHashMap<String, Integer>();
            for (String key : columns) {

                Integer pos = mapCols.get(key);

                if (pos != null) {
                    res.put(key, pos);
                }
            }

        } else {
            // regresa todas las columnas de la cabecera

            for (int i = 0; i < col; i++) {
                mapCols.put(reader.get(i), i);
            }

            res=mapCols;
        }


        return res;
    }

     /* *//**
     * Convierte el stream de datos en una lista de beans del tipo indicado <tt>T</tt>,
     * utiliza un mapa que indica la nombre de la propiedad de la clase y el nombre de la columna del archivo..
     *
     * @param is, Stream de datos.
     * @param mapClass Mapa con los  nombres de las propiedades de la clase y su nombre equivalente
     * de la columna del archivo
     * @param beanClass Instancia de la cual se va a tomar las propiedades..
     * @param <T> Tipo de dato para realizar el casting.
     *
     * @return Lista con las instancias creadas.
     *//*
    public <T> List<T> toBeanList(InputStream is
            , Map<String, Object> mapClass
            , T beanClass) {

        Map<String, Integer> mapCol = null;
        List<T> beanList = null;

        InputStreamReader read = null;
        CsvReader reader = null;

        Field[] fields = null;
        T pojo = null;

        try {

            read= new InputStreamReader(is);
            reader= new CsvReader(read);

            if(delimiter!= '\u0000'){
                reader.setDelimiter(delimiter);
            }
            else {
                reader.setDelimiter(DEFAULT_DELIMITER);
            }

            // Lee las posiciones de las columnas del archivo
            mapCol = getColumnPositions(reader);

            List<Position> xy = getMatchedPositions(mapCol, mapClass, beanClass);

            beanList = new ArrayList<T>();

            Field property= null;
            while(reader.readRecord()){

                pojo = (T) beanClass.getClass().newInstance();
                logger.debug("Instacia creada");

                // Llenar lista con pojos (Reflection)
                fields = pojo.getClass().getDeclaredFields();

                for (Position d : xy) {

                    String content=reader.get(d.getX());
                    property = fields[d.getY()];

                    logger.debug("value:{} ,set to: {}", content,property);

                    refUtils.setValueField(pojo,property,content);

                }
                beanList.add(pojo);
                //i++;
            }


        } catch (IOException ex) {
            logger.error("File to read Stream", ex);
        } catch (IllegalAccessException e) {
            logger.error("The bean instance is not accesible", e);
        } catch (InstantiationException e) {
            logger.error("Cant create bean instance", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return beanList;
    }*/
}