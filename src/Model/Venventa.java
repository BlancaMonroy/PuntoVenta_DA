/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blanca
 */
@Entity
@Table(name = "venventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venventa.findAll", query = "SELECT v FROM Venventa v"),
    @NamedQuery(name = "Venventa.findById", query = "SELECT v FROM Venventa v WHERE v.id = :id"),
    @NamedQuery(name = "Venventa.findByStrFolio", query = "SELECT v FROM Venventa v WHERE v.strFolio = :strFolio"),
    @NamedQuery(name = "Venventa.findByDteFechaVenta", query = "SELECT v FROM Venventa v WHERE v.dteFechaVenta = :dteFechaVenta"),
    @NamedQuery(name = "Venventa.findByDecTotal", query = "SELECT v FROM Venventa v WHERE v.decTotal = :decTotal")})
public class Venventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strFolio")
    private String strFolio;
    @Column(name = "dteFechaVenta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dteFechaVenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decTotal")
    private BigDecimal decTotal;
    @OneToMany(mappedBy = "idVenVenta")
    private List<Vendetalleventa> vendetalleventaList;

    public Venventa() {
    }

    public Venventa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrFolio() {
        return strFolio;
    }

    public void setStrFolio(String strFolio) {
        this.strFolio = strFolio;
    }

    public Date getDteFechaVenta() {
        return dteFechaVenta;
    }

    public void setDteFechaVenta(Date dteFechaVenta) {
        this.dteFechaVenta = dteFechaVenta;
    }

    public BigDecimal getDecTotal() {
        return decTotal;
    }

    public void setDecTotal(BigDecimal decTotal) {
        this.decTotal = decTotal;
    }

    @XmlTransient
    public List<Vendetalleventa> getVendetalleventaList() {
        return vendetalleventaList;
    }

    public void setVendetalleventaList(List<Vendetalleventa> vendetalleventaList) {
        this.vendetalleventaList = vendetalleventaList;
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
        if (!(object instanceof Venventa)) {
            return false;
        }
        Venventa other = (Venventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Venventa[ id=" + id + " ]";
    }
    
}
