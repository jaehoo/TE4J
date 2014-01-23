package com.oz.model.dto;

import java.util.Map;

/**
 * Date: 27/02/12
 * Time: 01:45 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class TemplateData <T>{

    protected String templateName;
    protected Map<String, String> resources;
    protected String templateLoaderPath;
    protected String imageResourcePath;
    protected Map<String, Object> dataModel;
    protected Object file;
    protected T BeanDataSource;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, String> getResources() {
        return resources;
    }

    public void setResources(Map<String, String> resources) {
        this.resources = resources;
    }

    public String getTemplateLoaderPath() {
        return templateLoaderPath;
    }

    public void setTemplateLoaderPath(String templateLoaderPath) {
        this.templateLoaderPath = templateLoaderPath;
    }

    public Map<String, Object> getDataModel() {
        return dataModel;
    }

    public void setDataModel(Map<String, Object> dataModel) {
        this.dataModel = dataModel;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }


    public String getImageResourcePath() {
        return imageResourcePath;
    }

    public void setImageResourcePath(String imageResourcePath) {
        this.imageResourcePath = imageResourcePath;
    }

    public T getBeanDataSource() {
        return BeanDataSource;
    }

    public void setBeanDataSource(T beanDataSource) {
        BeanDataSource = beanDataSource;
    }
}
