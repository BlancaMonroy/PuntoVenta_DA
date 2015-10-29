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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pedpedidoproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedpedidoproveedor.findAll", query = "SELECT p FROM Pedpedidoproveedor p"),
    @NamedQuery(name = "Pedpedidoproveedor.findById", query = "SELECT p FROM Pedpedidoproveedor p WHERE p.id = :id"),
    @NamedQuery(name = "Pedpedidoproveedor.findByStrFolio", query = "SELECT p FROM Pedpedidoproveedor p WHERE p.strFolio = :strFolio"),
    @NamedQuery(name = "Pedpedidoproveedor.findByDteFechaPedido", query = "SELECT p FROM Pedpedidoproveedor p WHERE p.dteFechaPedido = :dteFechaPedido"),
    @NamedQuery(name = "Pedpedidoproveedor.findByDecTotal", query = "SELECT p FROM Pedpedidoproveedor p WHERE p.decTotal = :decTotal")})
public class Pedpedidoproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strFolio")
    private String strFolio;
    @Column(name = "dteFechaPedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dteFechaPedido;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decTotal")
    private BigDecimal decTotal;
    @OneToMany(mappedBy = "idPedPedidoProveedor")
    private List<Pedpagospedidoproveedor> pedpagospedidoproveedorList;
    @JoinColumn(name = "idCatStatusPedido", referencedColumnName = "id")
    @ManyToOne
    private Catstatuspedido idCatStatusPedido;
    @OneToMany(mappedBy = "idPedPedidoProveedor")
    private List<Peddetallepedidoproveedor> peddetallepedidoproveedorList;

    public Pedpedidoproveedor() {
    }

    public Pedpedidoproveedor(Integer id) {
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

    public Date getDteFechaPedido() {
        return dteFechaPedido;
    }

    public void setDteFechaPedido(Date dteFechaPedido) {
        this.dteFechaPedido = dteFechaPedido;
    }

    public BigDecimal getDecTotal() {
        return decTotal;
    }

    public void setDecTotal(BigDecimal decTotal) {
        this.decTotal = decTotal;
    }

    @XmlTransient
    public List<Pedpagospedidoproveedor> getPedpagospedidoproveedorList() {
        return pedpagospedidoproveedorList;
    }

    public void setPedpagospedidoproveedorList(List<Pedpagospedidoproveedor> pedpagospedidoproveedorList) {
        this.pedpagospedidoproveedorList = pedpagospedidoproveedorList;
    }

    public Catstatuspedido getIdCatStatusPedido() {
        return idCatStatusPedido;
    }

    public void setIdCatStatusPedido(Catstatuspedido idCatStatusPedido) {
        this.idCatStatusPedido = idCatStatusPedido;
    }

    @XmlTransient
    public List<Peddetallepedidoproveedor> getPeddetallepedidoproveedorList() {
        return peddetallepedidoproveedorList;
    }

    public void setPeddetallepedidoproveedorList(List<Peddetallepedidoproveedor> peddetallepedidoproveedorList) {
        this.peddetallepedidoproveedorList = peddetallepedidoproveedorList;
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
        if (!(object instanceof Pedpedidoproveedor)) {
            return false;
        }
        Pedpedidoproveedor other = (Pedpedidoproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Pedpedidoproveedor[ id=" + id + " ]";
    }
    
}
