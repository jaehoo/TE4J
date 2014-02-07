package com.oz.model.dto;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 07:21 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public class Template {

    private File resource;
    protected String name;


    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
