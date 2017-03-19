
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
@Table(name = "PROCESS_REWORK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessRework.findAll", query = "SELECT p FROM ProcessRework p")
    ,@NamedQuery(name = "ProcessRework.findByProcessName", query = "SELECT p FROM ProcessRework p WHERE p.processName = :processName")})
public class ProcessRework implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PROCESS_NAME")
    private String processName;
    
    @Column(name = "PROCESS_PATH")
    private String processPath;

    public String getProcessPath() {
        return processPath;
    }

    public void setProcessPath(String processPath) {
        this.processPath = processPath;
    }
    
    public ProcessRework() {
    }

    public ProcessRework(String processName,String processPath) {
        this.processName = processName;
        this.processPath=processPath;
    }

 

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processName != null ? processName.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessRework)) {
            return false;
        }
        ProcessRework other = (ProcessRework) object;
        if ((this.processName == null && other.processName != null) || (this.processName != null && !this.processName.equals(other.processName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reworkweb.entities.ProcessRework[ processName=" + processName + " ]";
    }
    
}
