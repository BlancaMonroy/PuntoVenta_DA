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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "proproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proproveedor.findAll", query = "SELECT p FROM Proproveedor p"),
    @NamedQuery(name = "Proproveedor.findById", query = "SELECT p FROM Proproveedor p WHERE p.id = :id"),
    @NamedQuery(name = "Proproveedor.findByStrRazonSocial", query = "SELECT p FROM Proproveedor p WHERE p.strRazonSocial = :strRazonSocial"),
    @NamedQuery(name = "Proproveedor.findByStrNombreCompania", query = "SELECT p FROM Proproveedor p WHERE p.strNombreCompania = :strNombreCompania"),
    @NamedQuery(name = "Proproveedor.findByStrRFC", query = "SELECT p FROM Proproveedor p WHERE p.strRFC = :strRFC")})
public class Proproveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "strRazonSocial")
    private String strRazonSocial;
    @Column(name = "strNombreCompania")
    private String strNombreCompania;
    @Column(name = "strRFC")
    private String strRFC;
    @Lob
    @Column(name = "imgFotoCompania")
    private byte[] imgFotoCompania;
    @JoinColumn(name = "idComDireccion", referencedColumnName = "id")
    @ManyToOne
    private Comdireccion idComDireccion;
    @JoinColumn(name = "idComDatoContacto", referencedColumnName = "id")
    @ManyToOne
    private Comdatocontacto idComDatoContacto;
    @JoinColumn(name = "idCatTipoProveedor", referencedColumnName = "id")
    @ManyToOne
    private Cattipoproveedor idCatTipoProveedor;
    @JoinColumn(name = "idProCatStatusProveedor", referencedColumnName = "id")
    @ManyToOne
    private Procatstatusproveedor idProCatStatusProveedor;
    @OneToMany(mappedBy = "idProveedor")
    private List<Proproducto> proproductoList;

    public Proproveedor() {
    }

    public Proproveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrRazonSocial() {
        return strRazonSocial;
    }

    public void setStrRazonSocial(String strRazonSocial) {
        this.strRazonSocial = strRazonSocial;
    }

    public String getStrNombreCompania() {
        return strNombreCompania;
    }

    public void setStrNombreCompania(String strNombreCompania) {
        this.strNombreCompania = strNombreCompania;
    }

    public String getStrRFC() {
        return strRFC;
    }

    public void setStrRFC(String strRFC) {
        this.strRFC = strRFC;
    }

    public byte[] getImgFotoCompania() {
        return imgFotoCompania;
    }

    public void setImgFotoCompania(byte[] imgFotoCompania) {
        this.imgFotoCompania = imgFotoCompania;
    }

    public Comdireccion getIdComDireccion() {
        return idComDireccion;
    }

    public void setIdComDireccion(Comdireccion idComDireccion) {
        this.idComDireccion = idComDireccion;
    }

    public Comdatocontacto getIdComDatoContacto() {
        return idComDatoContacto;
    }

    public void setIdComDatoContacto(Comdatocontacto idComDatoContacto) {
        this.idComDatoContacto = idComDatoContacto;
    }

    public Cattipoproveedor getIdCatTipoProveedor() {
        return idCatTipoProveedor;
    }

    public void setIdCatTipoProveedor(Cattipoproveedor idCatTipoProveedor) {
        this.idCatTipoProveedor = idCatTipoProveedor;
    }

    public Procatstatusproveedor getIdProCatStatusProveedor() {
        return idProCatStatusProveedor;
    }

    public void setIdProCatStatusProveedor(Procatstatusproveedor idProCatStatusProveedor) {
        this.idProCatStatusProveedor = idProCatStatusProveedor;
    }

    @XmlTransient
    public List<Proproducto> getProproductoList() {
        return proproductoList;
    }

    public void setProproductoList(List<Proproducto> proproductoList) {
        this.proproductoList = proproductoList;
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
        if (!(object instanceof Proproveedor)) {
            return false;
        }
        Proproveedor other = (Proproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Proproveedor[ id=" + id + " ]";
    }
    
}
