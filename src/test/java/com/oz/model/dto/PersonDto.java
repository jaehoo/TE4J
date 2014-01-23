package com.oz.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *         Date: 12/02/12
 *         Time: 04:26 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class PersonDto implements Serializable{

    private Integer idPerson;
    private String name;
    private String firstName;
    private String lastName;
    private String curp;
    private String rfc;
    private Date birthDate;
    private String birthday;

    private Boolean gender;
    private Integer numGender;
    private String genderName;

    private CountryDto country;
    private AddressDto address;

    private String email;

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getNumGender() {
        return numGender;
    }

    public void setNumGender(Integer numGender) {
        this.numGender = numGender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PersonDto [idPerson=" + idPerson
                + ", name=" + name
				+ ", firstName=" + firstName
                + ", lastName=" + lastName
				+ ", curp=" + curp
                + ", rfc=" + rfc
                + ", birthDate="+ birthDate
                + ", birthday=" + birthday
                + ", gender=" + gender
				+ ", numGender=" + numGender
                + ", genderName=" + genderName
				+ ", country=" + country
                + ", address=" + address
				+", email=" + email + "]";
	}
    
}
