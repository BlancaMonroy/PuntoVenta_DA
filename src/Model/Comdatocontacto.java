/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blanca
 */
@Entity
@Table(name = "comdatocontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comdatocontacto.findAll", query = "SELECT c FROM Comdatocontacto c"),
    @NamedQuery(name = "Comdatocontacto.findById", query = "SELECT c FROM Comdatocontacto c WHERE c.id = :id"),
    @NamedQuery(name = "Comdatocontacto.findByStrNombreContacto", query = "SELECT c FROM Comdatocontacto c WHERE c.strNombreContacto = :strNombreContacto"),
    @NamedQuery(name = "Comdatocontacto.findByStrEmail", query = "SELECT c FROM Comdatocontacto c WHERE c.strEmail = :strEmail"),
    @NamedQuery(name = "Comdatocontacto.findByStrTelefonoMovil", query = "SELECT c FROM Comdatocontacto c WHERE c.strTelefonoMovil = :strTelefonoMovil"),
    @NamedQuery(name = "Comdatocontacto.findByStrTelefonoHome", query = "SELECT c FROM Comdatocontacto c WHERE c.strTelefonoHome = :strTelefonoHome"),
    @NamedQuery(name = "Comdatocontacto.findByStrObservaciones", query = "SELECT c FROM Comdatocontacto c WHERE c.strObservaciones = :strObservaciones")})
public class Comdatocontacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strNombreContacto")
    private String strNombreContacto;
    @Column(name = "strEmail")
    private String strEmail;
    @Column(name = "strTelefonoMovil")
    private String strTelefonoMovil;
    @Column(name = "strTelefonoHome")
    private String strTelefonoHome;
    @Column(name = "strObservaciones")
    private String strObservaciones;
    @OneToMany(mappedBy = "idComDatoContacto")
    private List<Proproveedor> proproveedorList;

    public Comdatocontacto() {
    }

    public Comdatocontacto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrNombreContacto() {
        return strNombreContacto;
    }

    public void setStrNombreContacto(String strNombreContacto) {
        this.strNombreContacto = strNombreContacto;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrTelefonoMovil() {
        return strTelefonoMovil;
    }

    public void setStrTelefonoMovil(String strTelefonoMovil) {
        this.strTelefonoMovil = strTelefonoMovil;
    }

    public String getStrTelefonoHome() {
        return strTelefonoHome;
    }

    public void setStrTelefonoHome(String strTelefonoHome) {
        this.strTelefonoHome = strTelefonoHome;
    }

    public String getStrObservaciones() {
        return strObservaciones;
    }

    public void setStrObservaciones(String strObservaciones) {
        this.strObservaciones = strObservaciones;
    }

    @XmlTransient
    public List<Proproveedor> getProproveedorList() {
        return proproveedorList;
    }

    public void setProproveedorList(List<Proproveedor> proproveedorList) {
        this.proproveedorList = proproveedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comdatocontacto)) {
            return false;
        }
        Comdatocontacto other = (Comdatocontacto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Comdatocontacto[ id=" + id + " ]";
    }
    
}
