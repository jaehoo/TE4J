package com.oz.utils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Date: 31/03/12
 * Time: 09:26 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class ClassElement implements Serializable {

    private Class clazz;
    private Object positions;
    private Field[] fields;
    private int parent;
    private int nodeLevel;
    private int index;

    private Integer fieldPosition;
    private Integer parentIndex;

    public ClassElement() {
    }

    public ClassElement(Class clazz) {
        this.clazz=clazz;
    }

    public ClassElement(Class clazz, Object positions) {
        this.positions = positions;
        this.clazz = clazz;
    }

    public ClassElement(Class clazz, Object positions, Field[] fields) {
        this.positions = positions;
        this.clazz = clazz;
        this.fields=fields;
    }

    public ClassElement(Class clazz, Object positions, Field[] fields, Integer nodeLevel, Integer index, int parent) {
        this.clazz = clazz;
        this.positions = positions;
        this.fields = fields;
        this.nodeLevel = nodeLevel;
        this.index = index;
        this.parent=parent;

    }

    public Integer getFieldPosition() {
        return fieldPosition;
    }

    public void setFieldPosition(Integer fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getPositions() {
        return positions;
    }

    public void setPositions(Object positions) {
        this.positions = positions;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "\nClassElement{" +
                "parent="+ parent +
                ", nodeLevel=" + nodeLevel +
                ", parentIndex=" + parentIndex+
                ", index=" + index +
                ", fieldPosition="+ fieldPosition +
                ", clazz=" + clazz +
                "\t, positions="+ positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassElement that = (ClassElement) o;

        if (index != that.index) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return index;
    }
}