package com.oz.control.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * File reader to allow  column positions from source file.
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public interface FileReaderService {

    /**
     * Obtiene un mapa con los nombres y las posiciones de los campos del archivo.
     * @param is, Stream de datos del archivo.
     *
     * @return map<{@link String},{@link Integer}> mapa de datos.
     */
    public Map<String, Integer> getPositions(InputStream is);

    /**
     * Obtiene un mapa con los nombres y las posciones de las columnas indicadas del archivo.
     * @param is, Stream de datos del archivo.
     * @param columns nombre de las columnas a buscar en el archivo.
     *
     * @return map<{@link String},{@link Integer}> mapa de datos.
     */
    public Map<String, Integer> getPositions(InputStream is, List<String> columns);

    /**
     * Convierte el stream de datos en una lista de beans de acuerdo a los objetos del mapa que se desean obtener.
     * @param is Stream de datos del archivo.
     * @param beanMap Mapa de datos con las clases y la configuración de sus atributos, el key es el nombre calificado
     * de la clase (foo.bar.MyClass) y el value es el mapa con los nombres de las propiedades de los campos y su nombre
     * equivalente en el archivo.
     *
     * @return Lista con el arreglo de datos de las instancias creadas.
     */
    public List toBeanList(InputStream is, Map<String, Object> beanMap) throws ClassNotFoundException;

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
     */
//    public <T> List<T> toBeanList(InputStream is
//            , Map<String, Object> mapClass
//            , T beanClass) ;
}
