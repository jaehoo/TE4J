package com.oz.control.service;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 6/02/14
 * Time: 06:25 PM
 *
 * @author <a href="jaehoo@gmail.com">Alberto Sánchez</a>
 *         Contact me by:
 *         <ul><li>Twitter: @jaehoox</li><ul>
 */
public interface TemplateEngine<P,D,T> {

    /**
     * Set template to process
     * @param template format
     */
    public void setTemplate(P template);

    /**
     * Data model to set into template
     * @param dataModel data will pass into template
     */
    public void setDataModel(D dataModel);

    /**
     * Generate template
     * @return Output result of merge template with data and resources
     */
    public T process();

}
