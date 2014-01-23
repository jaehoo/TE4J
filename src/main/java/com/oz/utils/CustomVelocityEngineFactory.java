package com.oz.utils;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.ui.velocity.VelocityEngineFactory;

import java.io.IOException;

/**
 * Date: 24/02/12
 * Time: 03:10 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class CustomVelocityEngineFactory extends VelocityEngineFactory
	implements FactoryBean<VelocityEngine>, InitializingBean, ResourceLoaderAware {


    private VelocityEngine velocityEngine;

    @Override
	public void afterPropertiesSet() throws IOException, VelocityException {
		this.velocityEngine = createVelocityEngine();
	}

    @Override
	public VelocityEngine getObject() {
		return this.velocityEngine;
	}

    @Override
	public Class<? extends VelocityEngine> getObjectType() {
		return VelocityEngine.class;
	}
    @Override
	public boolean isSingleton() {
		return true;
	}

    @Override
    public void setResourceLoaderPath(String loaderPath){
        setResourceLoaderPath(loaderPath);
    }

}
