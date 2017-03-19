/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reworkweb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mchamparini
 */
@Entity
@Table(name = "USER_REWORK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRework.findAll", query = "SELECT u FROM UserRework u")
    , @NamedQuery(name = "UserRework.findByNameUs", query = "SELECT u FROM UserRework u WHERE u.nameUs = :nameUs")
    , @NamedQuery(name = "UserRework.findByPasswordUs", query = "SELECT u FROM UserRework u WHERE u.passwordUs = :passwordUs")
    , @NamedQuery(name = "UserRework.CheckUser", query = "SELECT u FROM UserRework u WHERE u.passwordUs = :passwordUs and u.nameUs = :nameUs")})
public class UserRework implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NAME_US")
    private String nameUs;       
    @Size(max = 150)
    @Column(name = "PASSWORD_US")
    private String passwordUs;

    public UserRework() {
    }

    public UserRework(String nameUs) {
        this.nameUs = nameUs;
    }

    public String getNameUs() {
        return nameUs;
    }

    public void setNameUs(String nameUs) {
        this.nameUs = nameUs;
    }

    public String getPasswordUs() {
        return passwordUs;
    }

    public void setPasswordUs(String passwordUs) {
        this.passwordUs = passwordUs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nameUs != null ? nameUs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRework)) {
            return false;
        }
        UserRework other = (UserRework) object;
        if ((this.nameUs == null && other.nameUs != null) || (this.nameUs != null && !this.nameUs.equals(other.nameUs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reworkweb.entities.UserRework[ nameUs=" + nameUs + " ]";
    }
    
}
