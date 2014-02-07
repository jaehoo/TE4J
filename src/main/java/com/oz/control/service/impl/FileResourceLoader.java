package com.oz.control.service.impl;

import com.oz.control.service.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 05:10 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public class FileResourceLoader implements ResourceLoader<File, List<File>, String, String[]> {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileResourceLoader.class);

    public boolean validateIfResourceExist;

    public void setValidateIfResourceExist(boolean validateIfResourceExist) {
        this.validateIfResourceExist = validateIfResourceExist;
    }

    /**
     *
     * @param resourceName
     * @return
     */
    @Override
    public File getResource(String resourceName) {

        return get(resourceName);

    }

    /**
     *
     * @param resourcesNames
     * @return
     */
    @Override
    public List<File> getResources(String[] resourcesNames) {

        List<File> resources = null;

        if(resourcesNames!=null && resourcesNames.length>=1){
            resources= new ArrayList();

            for(String resource: resourcesNames){

                File res=get(resource);

                if(res!=null){
                    resources.add(res);
                }
            }
        }

        return resources;

    }


    /**
     *
     * @param resourceName
     * @return
     */
    private final File get(String resourceName)  {

        LOGGER.debug("try to get:{}",resourceName);
        File file= new File(resourceName);

        if(validateIfResourceExist && !file.exists()){
            LOGGER.error("Resource don´t exists: {}", resourceName);
            //throw  new FileNotFoundException("Resource don´t exists");
        }

        return file;

    }
}
