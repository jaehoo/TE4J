package com.oz.control.service;

import com.oz.model.dto.TemplateData;
import org.springframework.stereotype.Service;

/**
 * Date: 27/02/12
 * Time: 01:39 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
@Service
public interface TemplateEngine {

    /**
     * Procesa la platilla con los datos y genera el archivo
     *
     * @param data datos y recursos para la pantilla
     * @return Object, resultado del proceso de la platinlla, puede ser
     * un arreglo de bytes, texto, un archivo pdf, etc... depende de la configuración
     * del servicion que lo implemente.
     */
    public Object processTemplate(TemplateData data);

}
