/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blanca
 */
@Entity
@Table(name = "proproducto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proproducto.findAll", query = "SELECT p FROM Proproducto p"),
    @NamedQuery(name = "Proproducto.findById", query = "SELECT p FROM Proproducto p WHERE p.id = :id"),
    @NamedQuery(name = "Proproducto.findByStrIdentificador", query = "SELECT p FROM Proproducto p WHERE p.strIdentificador = :strIdentificador"),
    @NamedQuery(name = "Proproducto.findByStrNombre", query = "SELECT p FROM Proproducto p WHERE p.strNombre = :strNombre"),
    @NamedQuery(name = "Proproducto.findByIntCantidad", query = "SELECT p FROM Proproducto p WHERE p.intCantidad = :intCantidad"),
    @NamedQuery(name = "Proproducto.findByDecPrecioCompra", query = "SELECT p FROM Proproducto p WHERE p.decPrecioCompra = :decPrecioCompra"),
    @NamedQuery(name = "Proproducto.findByDecPrecioVenta", query = "SELECT p FROM Proproducto p WHERE p.decPrecioVenta = :decPrecioVenta"),
    @NamedQuery(name = "Proproducto.findByStrDescripcion", query = "SELECT p FROM Proproducto p WHERE p.strDescripcion = :strDescripcion")})
public class Proproducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strIdentificador")
    private String strIdentificador;
    @Column(name = "strNombre")
    private String strNombre;
    @Column(name = "intCantidad")
    private Integer intCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decPrecioCompra")
    private BigDecimal decPrecioCompra;
    @Column(name = "decPrecioVenta")
    private BigDecimal decPrecioVenta;
    @Column(name = "strDescripcion")
    private String strDescripcion;
    @OneToMany(mappedBy = "idProducto")
    private List<Vendetalleventa> vendetalleventaList;
    @JoinColumn(name = "idCatTipoProducto", referencedColumnName = "id")
    @ManyToOne
    private Cattipoproducto idCatTipoProducto;
    @JoinColumn(name = "idProveedor", referencedColumnName = "id")
    @ManyToOne
    private Proproveedor idProveedor;

    public Proproducto() {
    }

    public Proproducto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrIdentificador() {
        return strIdentificador;
    }

    public void setStrIdentificador(String strIdentificador) {
        this.strIdentificador = strIdentificador;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public Integer getIntCantidad() {
        return intCantidad;
    }

    public void setIntCantidad(Integer intCantidad) {
        this.intCantidad = intCantidad;
    }

    public BigDecimal getDecPrecioCompra() {
        return decPrecioCompra;
    }

    public void setDecPrecioCompra(BigDecimal decPrecioCompra) {
        this.decPrecioCompra = decPrecioCompra;
    }

    public BigDecimal getDecPrecioVenta() {
        return decPrecioVenta;
    }

    public void setDecPrecioVenta(BigDecimal decPrecioVenta) {
        this.decPrecioVenta = decPrecioVenta;
    }

    public String getStrDescripcion() {
        return strDescripcion;
    }

    public void setStrDescripcion(String strDescripcion) {
        this.strDescripcion = strDescripcion;
    }

    @XmlTransient
    public List<Vendetalleventa> getVendetalleventaList() {
        return vendetalleventaList;
    }

    public void setVendetalleventaList(List<Vendetalleventa> vendetalleventaList) {
        this.vendetalleventaList = vendetalleventaList;
    }

    public Cattipoproducto getIdCatTipoProducto() {
        return idCatTipoProducto;
    }

    public void setIdCatTipoProducto(Cattipoproducto idCatTipoProducto) {
        this.idCatTipoProducto = idCatTipoProducto;
    }

    public Proproveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proproveedor idProveedor) {
        this.idProveedor = idProveedor;
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
        if (!(object instanceof Proproducto)) {
            return false;
        }
        Proproducto other = (Proproducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Proproducto[ id=" + id + " ]";
    }
    
}
