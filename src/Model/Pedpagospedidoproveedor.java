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
@Table(name = "pedpagospedidoproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedpagospedidoproveedor.findAll", query = "SELECT p FROM Pedpagospedidoproveedor p"),
    @NamedQuery(name = "Pedpagospedidoproveedor.findById", query = "SELECT p FROM Pedpagospedidoproveedor p WHERE p.id = :id"),
    @NamedQuery(name = "Pedpagospedidoproveedor.findByDecMontoPago", query = "SELECT p FROM Pedpagospedidoproveedor p WHERE p.decMontoPago = :decMontoPago")})
public class Pedpagospedidoproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decMontoPago")
    private BigDecimal decMontoPago;
    @JoinColumn(name = "idCatTipoPago", referencedColumnName = "id")
    @ManyToOne
    private Cattipopago idCatTipoPago;
    @JoinColumn(name = "idPedPedidoProveedor", referencedColumnName = "id")
    @ManyToOne
    private Pedpedidoproveedor idPedPedidoProveedor;

    public Pedpagospedidoproveedor() {
    }

    public Pedpagospedidoproveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDecMontoPago() {
        return decMontoPago;
    }

    public void setDecMontoPago(BigDecimal decMontoPago) {
        this.decMontoPago = decMontoPago;
    }

    public Cattipopago getIdCatTipoPago() {
        return idCatTipoPago;
    }

    public void setIdCatTipoPago(Cattipopago idCatTipoPago) {
        this.idCatTipoPago = idCatTipoPago;
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
        if (!(object instanceof Pedpagospedidoproveedor)) {
            return false;
        }
        Pedpagospedidoproveedor other = (Pedpagospedidoproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Pedpagospedidoproveedor[ id=" + id + " ]";
    }
    
}
