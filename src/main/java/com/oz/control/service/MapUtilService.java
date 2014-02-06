package com.oz.control.service;

import com.oz.utils.ClassElement;
import com.oz.utils.IndexedBeanMap;
import com.oz.utils.Position;
import org.apache.commons.beanutils.DynaBean;

import java.util.List;
import java.util.Map;


/**
 * Map converter Utilities
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public interface MapUtilService {

    /**
     * Convierte una cadena de texto en un mapa, si tiene atributos recusrsivos los convierte en mapas.,
     * @param json cadena Json
     * @return  mapa con los valores de la cadena de texto
     */
    public Map toMap(String json);

    /**
     * Convierte una cadena de texto en un mapa con Bean dinamicos {@link org.apache.commons.beanutils.DynaBean}
     * @param json cadena de texto json
     * @return mapa con objetos dynamicos
     */
    public Map toDynaBeanMap(String json);

    /**
     *  Crea un mapa con los nombres de las propiedades dinamicas y sus valores.
     * @param dynaBean objeto con propiedades dinamicas
     * @return Nombres y valores de las propiedades dinamicas.
     */
    public Map toMap(DynaBean dynaBean);

    /**
     * Coloca los datos que contiene un objeto en un mapa, todas las referencias de los elementos y sus valores
     * son colocados y encapsulados en un nuevo mapa.
     * @param o, objeto con el contenido.
     * @return {@link java.util.Map} si es un Map, {@link java.util.List} si es una Lista, cuarlquier otro es invalido.
     */
    public Object toMap(Object o);

    /**
     * Get Numeric positions from map, create a new map and retrieve key values with numeric positions as values
     * , based on zero index. This function is not recursive.
     * @param map source map to read
     * @return new instanced map with original keys
     * @throws IllegalAccessException if cant Access to Map Class
     * @throws InstantiationException If cant create new map Instance
     */
    public Map getPositions(Map map) throws IllegalAccessException, InstantiationException;

    /**
     * Get Numeric positions from map, create a new map and retrieve key values with numeric positions as values
     * , based on zero index. This function is not recursive.
     * @param map source map to read
     * @param recursive read source as recursive and nested maps
     *
     * @return new instanced map with original keys
     * @throws IllegalAccessException if cant Access to Map Class
     * @throws InstantiationException If cant create new map Instance
     */
    public Map getPositions(Map map, boolean recursive) throws IllegalAccessException, InstantiationException;

    /**
     * Obtiene la posicion numerica de los atributos de una clase
     * @param objectClass Clase a la cual se van a tomar los fields
     * @return mapa con el nombre de la propiedad y el número de la posición que tiene el Field en la clase.
     */
    public <T> Map<String, Integer> getPositions(T objectClass);

    /**
     * Obtiene las posiciones numericas de las propiedades de una clase por los nombres indicados.
     * @param objectClass Clase a la cual se van a tomar los fields
     * @param fieldNames Nombre de los fields a tomar de la clase
     * @return mapa con el nombre de la propiedad y el número de la posición que tiene el Field en la clase.
     */
    public <T> Map<String, Integer> getPositions(T objectClass, Map fieldNames);

    /**
     * Analiza las propiedades del mapa de una clase contra el mapa de propiedades de una cadena JSON,
     * se obtiene la poscición de las propiedades que concuerden entre ambos mapas, esto crea un plano cartesiano
     * para determinar como se relacionan los atributos y obtener su posición númerica, es decir coordenadas.
     *
     *
     *
     * @param propertyMap Mapa de propiedades de la clase con el nombre de la propiedad como key y su posción númerica
     *                    dentro del mapa.
     * @param fieldMap Mapa con los nombres de los campos y su poscición númerica dentro el mapa.
     * @return Lista cd clases con el resumen de las posiciones.
     * @throws ClassNotFoundException
     *
     */
    public List<ClassElement> getMatchedPositions(Map propertyMap, Map fieldMap) throws ClassNotFoundException;

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
     */
    public <T> List<Position> getMatchedPositions(Map colNamesAndPositions
            , Map fieldNamesAndColNames
            , T beanClass);


    /**
     *
     * @param map
     * @return
     */
    public List<IndexedBeanMap> wrapIntoList(Map map);

    //<T> Map mergeBeanFieldsPosVsMapKeysPositionsByMapConfig(Map mapValues, Map mapConfig);

    /**
     *
     * @param beanInstance
     * @param srcMap
     * @param mapConfig
     * @param <T>
     */
    public <T> void mergePositions(T beanInstance, Map srcMap, Map mapConfig);


}

