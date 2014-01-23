/**
 * 
 */
package com.oz.model.dto;

/**
 * Clase para manejar los paises, es utilizada para enviar al front-end para los
 * campos select.
 *
 * @author <a href="mailto:sock.sqt@gmail.com">Samuel Quintana</a>
 * ***********************
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class CountryDto {

	private Short idCountry;
	private String country;
	private String shortName;
	private String nationality;

	public CountryDto() {
		super();
	}

	public CountryDto(Short idCountry, String country, String shortName,
                      String nationality) {
		super();
		this.idCountry = idCountry;
		this.country = country;
		this.shortName = shortName;
		this.nationality = nationality;
	}
	
	public CountryDto(Short idCountry, String country, String shortName) {
		super();
		this.idCountry = idCountry;
		this.country = country;
		this.shortName = shortName;
	}

	public Short getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(Short idCountry) {
		this.idCountry = idCountry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "CountryDto [idCountry=" + idCountry + ", country=" + country
				+ ", shortName=" + shortName + ", nationality=" + nationality
				+ "]";
	}

}
