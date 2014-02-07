package com.oz.control.service;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 05:06 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public interface ResourceLoader<T,T1,V,V1> {

    public T getResource(V resourceName);

    public T1 getResources(V1 resourceName);

}
