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
@Table(name = "procatstatusproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procatstatusproveedor.findAll", query = "SELECT p FROM Procatstatusproveedor p"),
    @NamedQuery(name = "Procatstatusproveedor.findById", query = "SELECT p FROM Procatstatusproveedor p WHERE p.id = :id"),
    @NamedQuery(name = "Procatstatusproveedor.findByStrValor", query = "SELECT p FROM Procatstatusproveedor p WHERE p.strValor = :strValor"),
    @NamedQuery(name = "Procatstatusproveedor.findByStrDescripcion", query = "SELECT p FROM Procatstatusproveedor p WHERE p.strDescripcion = :strDescripcion")})
public class Procatstatusproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strValor")
    private String strValor;
    @Column(name = "strDescripcion")
    private String strDescripcion;
    @OneToMany(mappedBy = "idProCatStatusProveedor")
    private List<Proproveedor> proproveedorList;

    public Procatstatusproveedor() {
    }

    public Procatstatusproveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrValor() {
        return strValor;
    }

    public void setStrValor(String strValor) {
        this.strValor = strValor;
    }

    public String getStrDescripcion() {
        return strDescripcion;
    }

    public void setStrDescripcion(String strDescripcion) {
        this.strDescripcion = strDescripcion;
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
        if (!(object instanceof Procatstatusproveedor)) {
            return false;
        }
        Procatstatusproveedor other = (Procatstatusproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Procatstatusproveedor[ id=" + id + " ]";
    }
    
}
