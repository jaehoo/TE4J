package com.oz.model.dto;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para dreccione
 * User: Hebert
 * Date: 2/01/12
 * Time: 04:45 PM
 * @author <a href="mailto:sock.sqt@gmail.com">Samuel Quintana</a>, 16/02/2012
 * @author <a href="mailto:jaehoo@gmail.com">Alberto Sanchez</a>, 19/02/2012
 *
 */
public class AddressDto implements Serializable{

    private Integer idAddress;
    private String street;

    private Boolean current;


	public Integer getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	@Override
	public String toString() {
		return "AddressDto [idAddress=" + idAddress + ", street=" + street
				+ ", current=" + current + "]";
	}

}
