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
@Table(name = "peddetallepedidoproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peddetallepedidoproveedor.findAll", query = "SELECT p FROM Peddetallepedidoproveedor p"),
    @NamedQuery(name = "Peddetallepedidoproveedor.findById", query = "SELECT p FROM Peddetallepedidoproveedor p WHERE p.id = :id"),
    @NamedQuery(name = "Peddetallepedidoproveedor.findByIdProducto", query = "SELECT p FROM Peddetallepedidoproveedor p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Peddetallepedidoproveedor.findByIdProveedor", query = "SELECT p FROM Peddetallepedidoproveedor p WHERE p.idProveedor = :idProveedor"),
    @NamedQuery(name = "Peddetallepedidoproveedor.findByIntCantidad", query = "SELECT p FROM Peddetallepedidoproveedor p WHERE p.intCantidad = :intCantidad"),
    @NamedQuery(name = "Peddetallepedidoproveedor.findByDecSubtotal", query = "SELECT p FROM Peddetallepedidoproveedor p WHERE p.decSubtotal = :decSubtotal")})
public class Peddetallepedidoproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idProducto")
    private Integer idProducto;
    @Column(name = "idProveedor")
    private Integer idProveedor;
    @Column(name = "intCantidad")
    private Integer intCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decSubtotal")
    private BigDecimal decSubtotal;
    @JoinColumn(name = "idPedPedidoProveedor", referencedColumnName = "id")
    @ManyToOne
    private Pedpedidoproveedor idPedPedidoProveedor;

    public Peddetallepedidoproveedor() {
    }

    public Peddetallepedidoproveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIntCantidad() {
        return intCantidad;
    }

    public void setIntCantidad(Integer intCantidad) {
        this.intCantidad = intCantidad;
    }

    public BigDecimal getDecSubtotal() {
        return decSubtotal;
    }

    public void setDecSubtotal(BigDecimal decSubtotal) {
        this.decSubtotal = decSubtotal;
    }

    public Pedpedidoproveedor getIdPedPedidoProveedor() {
        return idPedPedidoProveedor;
    }

    public void setIdPedPedidoProveedor(Pedpedidoproveedor idPedPedidoProveedor) {
        this.idPedPedidoProveedor = idPedPedidoProveedor;
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
        if (!(object instanceof Peddetallepedidoproveedor)) {
            return false;
        }
        Peddetallepedidoproveedor other = (Peddetallepedidoproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Peddetallepedidoproveedor[ id=" + id + " ]";
    }
    
}
