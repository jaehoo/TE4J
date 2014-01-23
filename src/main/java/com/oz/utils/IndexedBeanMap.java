package com.oz.utils;

/**
 * Created by IntelliJ IDEA.
 * User: carlos
 * Date: 2/10/12
 * Time: 07:17 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class IndexedBeanMap<K,V> {

    private K key;
    private V value;
    private Integer keyIndex;
    private Class castTo;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Integer getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(Integer keyIndex) {
        this.keyIndex = keyIndex;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Class getCastTo() {
        return castTo;
    }

    public void setCastTo(Class castTo) {
        this.castTo = castTo;
    }
}
