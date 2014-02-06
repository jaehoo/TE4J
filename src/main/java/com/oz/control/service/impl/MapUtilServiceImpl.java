package com.oz.control.service.impl;

import com.oz.control.service.MapUtilService;
import com.oz.utils.ClassElement;
import com.oz.utils.FileIndex;
import com.oz.utils.IndexedBeanMap;
import com.oz.utils.Position;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Convert functions for {@link Map} Objects,
 * Funciones para convertir los Objectos de tipo {@link Map}
 * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a> *
 */
@Service
public class MapUtilServiceImpl implements MapUtilService {

    private static final Logger logger = LoggerFactory
            .getLogger(MapUtilServiceImpl.class);

    private static final String labelClass="CLASS";
    private static final String labelPositions="POSITIONS";

    @Override
    public Map toMap(String jsonString) {

        logger.debug("Covert Json String to JSON Object ...");
        JSONObject json = JSONObject.fromObject(jsonString);

        logger.debug("Covert JSON Object to Map ...");
        Map mapConfig=(Map) JSONObject.toBean(json, LinkedHashMap.class);

        Map result=(Map) toMap(mapConfig);

        logger.debug("Map size:{} \n  content:{}",result.size(),result);

        return result;
    }

    @Override
    public Map toDynaBeanMap(String json) {

        JSONObject map = JSONObject.fromObject(json);
        return  (Map) JSONObject.toBean(map, HashMap.class);

    }

    @Override
    public Map toMap(DynaBean dynaBean) {

        DynaProperty[] dynaProperties=dynaBean.getDynaClass().getDynaProperties();

        if(dynaProperties.length>0){

            Map map=new LinkedHashMap();
            for(DynaProperty dp: dynaProperties){

                Object value=dynaBean.get(dp.getName());

                if( value instanceof DynaBean ){
                    map.put(dp.getName(), toMap((DynaBean) value));
                }else{
                    map.put( dp.getName(),value );
                }

                logger.debug("name:{}, value:{}",dp.getName(),value);
            }
            return map;
        }
        else{
            return null;
        }

    }


    /**
     * Convierte un objeto en un mapa, soporta {@link DynaBean} y {@link List}
     * @param o, objeto con el contenido.
     * @return {@link java.util.Map} si o es un Map, {@link java.util.List}  si o es una Lista, cuarlquier otro es invalido.
     */
    @Override
    public Object toMap(Object o){

        if(o instanceof Map){

            if(((Map) o).size()<=0) return null;

            Map resMap= new LinkedHashMap();

            Iterator<Map.Entry> it= ((Map)o).entrySet().iterator();

            Map.Entry entry= null;

            while (it.hasNext()){

                entry=it.next();

                if(entry.getValue() instanceof DynaBean){
                    resMap.put(entry.getKey(), toMap((DynaBean) entry.getValue()));
                }
                else if(entry.getValue() instanceof Map || entry.getValue() instanceof List){
                    resMap.put(entry.getKey(),toMap(entry.getValue()));
                }else{
                    resMap.put(entry.getKey(),entry.getValue());
                }

            }
            logger.debug("Map Size:{}",resMap.size());
            return resMap;

        }
        else if(o instanceof List){

            List list=(List) o;

            if(list.size()<0) return null;

            List resList= new ArrayList();

            for(Object e : list){

                if(e instanceof DynaBean){
                    resList.add(toMap((DynaBean) e));
                }
                else if(e instanceof Map || e instanceof List){
                    resList.add(toMap(e));
                }
            }

            return resList;

        }
        else if( o instanceof DynaBean){
            return toMap((DynaBean) o);
        }
        else{
            throw new UnsupportedOperationException("The object type don't be casted to Map...");
        }
    }

    /**
     * Obtiene la posicion numerica de los atribuos de una clase
     * @param objectClass Clase a la cual se van a tomar los fields
     * @return mapa con el nombre de la propiedad y el número de la posición que tiene el Field en la clase.
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
    @Override
    public <T> Map<String, Integer> getPositions(T objectClass){

        Field[] fields = null;
        Map fieldPositions=null;
        Class clazz= null;

        if(objectClass instanceof Class){
            clazz=(Class)objectClass;
        }
        else{
            clazz= objectClass.getClass();
        }


        logger.debug("Field pos from:{}", clazz);

        fields=clazz.getDeclaredFields();

        if(fields.length>0){

            fieldPositions= new LinkedHashMap();
            int i = 0;

            for (Field f : fields) {
                fieldPositions.put(f.getName(), i++);
            }

            return  fieldPositions;

        }else {
            return null;
        }

    }

    /**
     * Obtiene las posiciones numericas de las propiedades de una clase por los nombres indicados.
     * @param objectClass Clase a la cual se van a tomar los fields
     * @param fieldNames Nombre de los fields a tomar de la clase
     * @return mapa con el nombre de la propiedad y el número de la posición que tiene el Field en la clase.
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
    @Override
    public <T> Map<String, Integer> getPositions(T objectClass, Map fieldNames){

        Field[] fields = null;
        Object campo = null;
        Map fieldPositions=null;

        Class clazz= null;

        if(objectClass instanceof Class){
            clazz = (Class) objectClass;
        }
        else{
            clazz= objectClass.getClass();
        }


        logger.debug("Class:{}", clazz);

        fields=clazz.getDeclaredFields();

        if(fields.length>0){

            fieldPositions= new LinkedHashMap();
            int i = 0;

            for (Field f : fields) {
                campo = fieldNames.get(f.getName());
                logger.debug("field name:{}", f.getName());

                if (campo != null) {
                    fieldPositions.put(f.getName(), i);
                    logger.debug("found:{}", f.getName());
                } else {
                    logger.debug("different");
                }

                i++;
            }

            return  fieldPositions;

        }else {
            return null;
        }

    }

    /**
     *  Obtiene las posiciones de las llaves de un mapa, las posiciones de las propiedades de una classe y genera
     *  una lista con los nombres que coinciden del mapa contra el nombre de los atributos de la clase, ejemplo:<br/>
     *
     *  <p>colNamePositions {"name"=0, "last name"=1 }</p>
     *  <p>Bean class fields {"name"=0, "lastName"=1 }</p>
     *
     *  <p>fieldNamesAndColNames {"name":"name", "lastName"="last name"}</p>
     *  Merge result:
     *  <p>[ [0,0], [1,1] ]</p>
     *
     * @param colNamesAndPositions Mapa con los nombres de la columna y su posición.
     * @param fieldNamesAndColNames Mapa con los nombres de las propiedades de la clase y su nombre equivalente de columna.
     * @param beanClass Clase para tomar las propiedades.
     *
     * @return lista con las posiciones por pares que se deben extraer de la fuente de datos.
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
    @Override
    public <T> List<Position> getMatchedPositions(Map colNamesAndPositions
            , Map fieldNamesAndColNames
            , T beanClass){

        logger.debug("Clase:{}", beanClass.getClass());

        Set properties = fieldNamesAndColNames.keySet();
        logger.debug("SET: {}", properties);

        Map<String, Integer> mapProperties = getPositions(beanClass, fieldNamesAndColNames);

        logger.debug("fieldNamesAndColNames: {}", fieldNamesAndColNames);
        logger.debug("mapProperties: {}", mapProperties);
        logger.debug("mapCol: {}", colNamesAndPositions);

        Iterator it = fieldNamesAndColNames.entrySet().iterator();
        List<Position> mapMergedPositions = new ArrayList<Position>();

        Map.Entry pairs = null;
        Integer posCol = null, posProp = null;

        // Obtiene las posiciones en coordenadas para extraer los datos
        while (it.hasNext()) {

            pairs = (Map.Entry) it.next();

            logger.debug("key:{}, val: {}", pairs.getKey(),
                    pairs.getValue());

            posCol = (Integer) colNamesAndPositions.get(pairs.getValue());
            posProp = (Integer) mapProperties.get(pairs.getKey());

            logger.debug("col pos(y):{} ,pro pos (x):{}", posCol,posProp);

            if (posCol != null & posProp != null) {
                mapMergedPositions.add(new Position(posCol, posProp));
            }
        }
        logger.debug("mapMergedPositions:{}", mapMergedPositions);

        return mapMergedPositions;
    }

    /**
     *  Analiza un Mapa de datos para localizar las clases y los atributos que coincidan contra los nombres de las columnas,
     *  genera una clase con el resumen de las posciciones. El analisis es recursivo.
     * @param src (List or Map) para analizar
     * @param clazz Clase con la cual se va a realizar la comparación.
     * @param fi Objeto para manipular el indice de la busqueda
     * @param container contenedor para acumular las clases
     * @param fieldPos posición de la propiedad en la que se realiza la busqueda, el valor inicial es cero 0
     * @param mapCol Mapa con los nombres de las columnas y sus posiciones del archivo de datos
     *
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
    private void toClassElement(Object src
            , Class clazz
            , final FileIndex fi
            , List container
            , int fieldPos
            , Map mapCol

    ){

        if(src instanceof Map){

            logger.debug("is Map!");

            Map nestedMap=(Map) src;

            if(nestedMap!= null && nestedMap.size()>=1){

                logger.debug("Calculate Positions... {}",clazz);

                List posList= getMatchedPositions(mapCol, nestedMap, clazz);

                logger.debug("parent: {} index: {}", fi.getParentIndex(), fi.getIndex());
                // ADD to container

                ClassElement cel= new ClassElement();
                cel.setClazz(clazz);
                cel.setIndex(fi.getIndex());
                cel.setFieldPosition(fieldPos);
                cel.setParentIndex(fi.getParentIndex());
                cel.setPositions(posList);

                fi.setParentIndex(fi.getIndex());
                fi.setIndex(fi.getIndex()+1);

                container.add(cel);

                int parentIndex=fi.getParentIndex();

                Iterator<Map.Entry> it= nestedMap.entrySet().iterator();

                Map positions= getPositions(clazz);

                while(it.hasNext()){

                    fi.setParentIndex(parentIndex);
                    Map.Entry entry=it.next();


                    if(entry.getValue() instanceof Map){

                        //int parentIdx=fi.getKeyIndex();

                        logger.debug("key:{}",entry.getKey());

                        Object pos=positions.get(entry.getKey());

                        if(pos!= null){
                            Integer position=(Integer) pos;

                            Field field=clazz.getDeclaredFields()[position];

                            logger.debug("field: {} : {}",position, field);


                            toClassElement(entry.getValue()
                                    ,field.getType()
                                    , fi
                                    , container
                                    , position
                                    , mapCol
                            );
                        }


                    }
                    else{
                        logger.debug("skip:{}", entry.getKey());
                    }

                }

            }

        }else if(src instanceof List){

            logger.debug("is List!... iterating");

            List nestedList=(List) src;

            int parentIndex=fi.getParentIndex();

            for(Object o : nestedList){

                fi.setParentIndex(parentIndex);

                toClassElement(o, clazz, fi, container, fieldPos, mapCol);
            }

        }

    }

    /**
     * Crea una lista con el analisis de las clases y las posiciones entre el mapa que describe las propiedades del bean contra
     * los nombres de las columnas y las pocisiones del archivo a convertir.
     * @param beanMap
     * @param colMap
     * @return Lista cd clases con el resumen de las posiciones.
     * @throws ClassNotFoundException
     *
     * @author <a href="mailto:jaehoo@gmail.com">Lic. José Alberto Sánchez</a>
     */
    public List<ClassElement> getMatchedPositions(Map beanMap, Map colMap) throws ClassNotFoundException {
        final FileIndex fileIndex= new FileIndex();

        fileIndex.setIndex(0);
        fileIndex.setParentIndex(-1);

        final int rootIndex=fileIndex.getParentIndex();

        List container= null;

        if(beanMap!= null && beanMap.size()>=1){

            container= new ArrayList();
            Iterator<Map.Entry> it=beanMap.entrySet().iterator();


            while(it.hasNext()){

                Map.Entry entry= it.next();
                Class clazz = Class.forName(entry.getKey().toString());

                logger.debug("Class:{}",clazz);

                //funcion recursiva
                toClassElement(entry.getValue(), clazz, fileIndex, container, 0, colMap);

                //fileIndex.setKeyIndex(fileIndex.getKeyIndex()+1);
                fileIndex.setParentIndex(rootIndex);
            }

        }
        return container;
    }

    /**
     *
     * @param map
     * @return
     */
    @Override
    public List<IndexedBeanMap> wrapIntoList(Map map){

        List elements= new ArrayList();
        IndexedBeanMap el;

        int index=0;
        for (Object mapEntry : map.entrySet())
        {
            Map.Entry entry=(Map.Entry) mapEntry;
            el= new IndexedBeanMap();

            if(entry.getValue() instanceof Map){
                el.setValue(wrapIntoList((Map) entry.getValue()));
            }else{
                el.setValue(entry.getValue());
            }

            el.setKey(entry.getKey());
            el.setKeyIndex(index);

            elements.add(el);

            index++;
        }

        return elements;
    }

    @Override
    public Map getPositions(Map map, boolean recursive) throws IllegalAccessException, InstantiationException {


        logger.debug("Map Class:{}", map.getClass());
        logger.debug("Map size:{}", map.size());

        Map resultMap = map.getClass().newInstance();

        int index=0;

        logger.debug("recursive read:{}", recursive);

        if(recursive){

            for (Object mapEntry : map.entrySet())
            {
                Map.Entry entry=(Map.Entry) mapEntry;

                if(entry.getValue() instanceof Map){
                    resultMap.put(entry.getKey(), getPositions((Map) entry.getValue()));
                }else{
                    resultMap.put(entry.getKey(),index);
                }

                index++;
            }

        }
        else {
            for (Object mapEntry : map.entrySet())
            {

                Map.Entry entry=(Map.Entry) mapEntry;
                resultMap.put(entry.getKey(),index);
                index++;
            }
        }

        logger.debug("Map result:{}", resultMap);

        return resultMap;


    }

    @Override
    public Map getPositions(Map map) throws IllegalAccessException, InstantiationException {

        return getPositions(map, false);

    }

    @Override
    public <T> void mergePositions(T beanInstance, Map srcMap, Map mapConfig) {


        // TODO get Bean Positions

        Map fieldPos= getPositions(beanInstance);


        IndexedBeanMap beanMap;
        int index=0;
        for(Object mapEntry: mapConfig.entrySet()){

            Map.Entry entry = (Map.Entry) mapEntry;

            logger.info("srcMap get key:{}", entry.getKey());
            Object value=srcMap.get(entry.getKey());


            if(value instanceof IndexedBeanMap){
                // TODO recursive
            }
            else{

                beanMap= new IndexedBeanMap();
                beanMap.setKey(entry.getKey());
                beanMap.setValue(value);
                beanMap.setKeyIndex(index);
            }



            index ++;


        }



        // TODO get Map index positions into WrapperIndex object
        List<IndexedBeanMap> listIndexedBeanMapPos =  wrapIntoList(srcMap);

        // TODO merge positions List<class, positions>



    }
}