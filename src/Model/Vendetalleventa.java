/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blanca
 */
@Entity
@Table(name = "vendetalleventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendetalleventa.findAll", query = "SELECT v FROM Vendetalleventa v"),
    @NamedQuery(name = "Vendetalleventa.findById", query = "SELECT v FROM Vendetalleventa v WHERE v.id = :id"),
    @NamedQuery(name = "Vendetalleventa.findByIntCantidad", query = "SELECT v FROM Vendetalleventa v WHERE v.intCantidad = :intCantidad"),
    @NamedQuery(name = "Vendetalleventa.findByDeTotal", query = "SELECT v FROM Vendetalleventa v WHERE v.deTotal = :deTotal")})
public class Vendetalleventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "intCantidad")
    private Integer intCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "deTotal")
    private BigDecimal deTotal;
    @JoinColumn(name = "idProducto", referencedColumnName = "id")
    @ManyToOne
    private Proproducto idProducto;
    @JoinColumn(name = "idVenVenta", referencedColumnName = "id")
    @ManyToOne
    private Venventa idVenVenta;

    public Vendetalleventa() {
    }

    public Vendetalleventa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntCantidad() {
        return intCantidad;
    }

    public void setIntCantidad(Integer intCantidad) {
        this.intCantidad = intCantidad;
    }

    public BigDecimal getDeTotal() {
        return deTotal;
    }

    public void setDeTotal(BigDecimal deTotal) {
        this.deTotal = deTotal;
    }

    public Proproducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Proproducto idProducto) {
        this.idProducto = idProducto;
    }

    public Venventa getIdVenVenta() {
        return idVenVenta;
    }

    public void setIdVenVenta(Venventa idVenVenta) {
        this.idVenVenta = idVenVenta;
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
        if (!(object instanceof Vendetalleventa)) {
            return false;
        }
        Vendetalleventa other = (Vendetalleventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Vendetalleventa[ id=" + id + " ]";
    }
    
}
