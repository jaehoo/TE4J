package com.oz.model.dto;

import java.io.Serializable;

/**
 * Date: 29/11/11
 * Time: 12:36 PM
 *
 * @author Ivan Amaya
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 *
 */
public class User implements Serializable{

    private Long idUser;
    private String username;
    private String password;
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private Boolean enabled;

    private Boolean confidentialAgreement;
    private Integer uniqueFormatStage;
    private Boolean securityOptions;
    private Boolean personData;

    private String idUserAbm;
    private Integer idPerson;

    private String createdBy;
    private String modifiedBy;


    private boolean addToElearning;
    private String type;

    private PersonDto person;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getConfidentialAgreement() {
        return confidentialAgreement;
    }

    public void setConfidentialAgreement(Boolean confidentialAgreement) {
        this.confidentialAgreement = confidentialAgreement;
    }

    public Integer getUniqueFormatStage() {
        return uniqueFormatStage;
    }

    public void setUniqueFormatStage(Integer uniqueFormatStage) {
        this.uniqueFormatStage = uniqueFormatStage;
    }

    public Boolean getSecurityOptions() {
        return securityOptions;
    }

    public void setSecurityOptions(Boolean securityOptions) {
        this.securityOptions = securityOptions;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getIdUserAbm() {
        return idUserAbm;
    }

    public void setIdUserAbm(String idUserAbm) {
        this.idUserAbm = idUserAbm;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public Boolean havePersonData() {
        return personData;
    }

    public void setPersonData(Boolean personData) {
        this.personData = personData;
    }


    public boolean isAddToElearning() {
        return addToElearning;
    }

    public void setAddToElearning(boolean addToElearning) {
        this.addToElearning = addToElearning;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "UserAbm{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", confidentialAgreement=" + confidentialAgreement +
                ", uniqueFormatStage=" + uniqueFormatStage +
                ", securityOptions=" + securityOptions +
                ", personData=" + personData +
                ", idUserAbm='" + idUserAbm + '\'' +
                ", idPerson=" + idPerson +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", addToElearning=" + addToElearning +
                ", type='" + type + '\'' +
                '}';
    }


}
