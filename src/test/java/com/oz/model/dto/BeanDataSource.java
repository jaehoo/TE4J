package com.oz.model.dto;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: carlos
 * Date: 10/08/12
 * Time: 01:59 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class BeanDataSource {
    private String name;
    private List addresses=new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getAddresses() {
        return addresses;
    }

    public void setAddresses(List addresses) {
        this.addresses = addresses;
    }

    public void addAddress(AddressDto addressDto){
        this.addresses.add(addressDto);
    }

    public JRDataSource getAddressesDS(){
        return new JRBeanCollectionDataSource(addresses);
    }

    @Override
    public String toString() {
        return "BeanDataSource{" +
                "name='" + name + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
