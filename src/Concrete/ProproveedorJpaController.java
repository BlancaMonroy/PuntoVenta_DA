/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Comdireccion;
import Model.Comdatocontacto;
import Model.Cattipoproveedor;
import Model.Procatstatusproveedor;
import Model.Proproducto;
import Model.Proproveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class ProproveedorJpaController implements Serializable {

    public ProproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proproveedor proproveedor) {
        if (proproveedor.getProproductoList() == null) {
            proproveedor.setProproductoList(new ArrayList<Proproducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comdireccion idComDireccion = proproveedor.getIdComDireccion();
            if (idComDireccion != null) {
                idComDireccion = em.getReference(idComDireccion.getClass(), idComDireccion.getId());
                proproveedor.setIdComDireccion(idComDireccion);
            }
            Comdatocontacto idComDatoContacto = proproveedor.getIdComDatoContacto();
            if (idComDatoContacto != null) {
                idComDatoContacto = em.getReference(idComDatoContacto.getClass(), idComDatoContacto.getId());
                proproveedor.setIdComDatoContacto(idComDatoContacto);
            }
            Cattipoproveedor idCatTipoProveedor = proproveedor.getIdCatTipoProveedor();
            if (idCatTipoProveedor != null) {
                idCatTipoProveedor = em.getReference(idCatTipoProveedor.getClass(), idCatTipoProveedor.getId());
                proproveedor.setIdCatTipoProveedor(idCatTipoProveedor);
            }
            Procatstatusproveedor idProCatStatusProveedor = proproveedor.getIdProCatStatusProveedor();
            if (idProCatStatusProveedor != null) {
                idProCatStatusProveedor = em.getReference(idProCatStatusProveedor.getClass(), idProCatStatusProveedor.getId());
                proproveedor.setIdProCatStatusProveedor(idProCatStatusProveedor);
            }
            List<Proproducto> attachedProproductoList = new ArrayList<Proproducto>();
            for (Proproducto proproductoListProproductoToAttach : proproveedor.getProproductoList()) {
                proproductoListProproductoToAttach = em.getReference(proproductoListProproductoToAttach.getClass(), proproductoListProproductoToAttach.getId());
                attachedProproductoList.add(proproductoListProproductoToAttach);
            }
            proproveedor.setProproductoList(attachedProproductoList);
            em.persist(proproveedor);
            if (idComDireccion != null) {
                idComDireccion.getProproveedorList().add(proproveedor);
                idComDireccion = em.merge(idComDireccion);
            }
            if (idComDatoContacto != null) {
                idComDatoContacto.getProproveedorList().add(proproveedor);
                idComDatoContacto = em.merge(idComDatoContacto);
            }
            if (idCatTipoProveedor != null) {
                idCatTipoProveedor.getProproveedorList().add(proproveedor);
                idCatTipoProveedor = em.merge(idCatTipoProveedor);
            }
            if (idProCatStatusProveedor != null) {
                idProCatStatusProveedor.getProproveedorList().add(proproveedor);
                idProCatStatusProveedor = em.merge(idProCatStatusProveedor);
            }
            for (Proproducto proproductoListProproducto : proproveedor.getProproductoList()) {
                Proproveedor oldIdProveedorOfProproductoListProproducto = proproductoListProproducto.getIdProveedor();
                proproductoListProproducto.setIdProveedor(proproveedor);
                proproductoListProproducto = em.merge(proproductoListProproducto);
                if (oldIdProveedorOfProproductoListProproducto != null) {
                    oldIdProveedorOfProproductoListProproducto.getProproductoList().remove(proproductoListProproducto);
                    oldIdProveedorOfProproductoListProproducto = em.merge(oldIdProveedorOfProproductoListProproducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proproveedor proproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proproveedor persistentProproveedor = em.find(Proproveedor.class, proproveedor.getId());
            Comdireccion idComDireccionOld = persistentProproveedor.getIdComDireccion();
            Comdireccion idComDireccionNew = proproveedor.getIdComDireccion();
            Comdatocontacto idComDatoContactoOld = persistentProproveedor.getIdComDatoContacto();
            Comdatocontacto idComDatoContactoNew = proproveedor.getIdComDatoContacto();
            Cattipoproveedor idCatTipoProveedorOld = persistentProproveedor.getIdCatTipoProveedor();
            Cattipoproveedor idCatTipoProveedorNew = proproveedor.getIdCatTipoProveedor();
            Procatstatusproveedor idProCatStatusProveedorOld = persistentProproveedor.getIdProCatStatusProveedor();
            Procatstatusproveedor idProCatStatusProveedorNew = proproveedor.getIdProCatStatusProveedor();
            List<Proproducto> proproductoListOld = persistentProproveedor.getProproductoList();
            List<Proproducto> proproductoListNew = proproveedor.getProproductoList();
            if (idComDireccionNew != null) {
                idComDireccionNew = em.getReference(idComDireccionNew.getClass(), idComDireccionNew.getId());
                proproveedor.setIdComDireccion(idComDireccionNew);
            }
            if (idComDatoContactoNew != null) {
                idComDatoContactoNew = em.getReference(idComDatoContactoNew.getClass(), idComDatoContactoNew.getId());
                proproveedor.setIdComDatoContacto(idComDatoContactoNew);
            }
            if (idCatTipoProveedorNew != null) {
                idCatTipoProveedorNew = em.getReference(idCatTipoProveedorNew.getClass(), idCatTipoProveedorNew.getId());
                proproveedor.setIdCatTipoProveedor(idCatTipoProveedorNew);
            }
            if (idProCatStatusProveedorNew != null) {
                idProCatStatusProveedorNew = em.getReference(idProCatStatusProveedorNew.getClass(), idProCatStatusProveedorNew.getId());
                proproveedor.setIdProCatStatusProveedor(idProCatStatusProveedorNew);
            }
            List<Proproducto> attachedProproductoListNew = new ArrayList<Proproducto>();
            for (Proproducto proproductoListNewProproductoToAttach : proproductoListNew) {
                proproductoListNewProproductoToAttach = em.getReference(proproductoListNewProproductoToAttach.getClass(), proproductoListNewProproductoToAttach.getId());
                attachedProproductoListNew.add(proproductoListNewProproductoToAttach);
            }
            proproductoListNew = attachedProproductoListNew;
            proproveedor.setProproductoList(proproductoListNew);
            proproveedor = em.merge(proproveedor);
            if (idComDireccionOld != null && !idComDireccionOld.equals(idComDireccionNew)) {
                idComDireccionOld.getProproveedorList().remove(proproveedor);
                idComDireccionOld = em.merge(idComDireccionOld);
            }
            if (idComDireccionNew != null && !idComDireccionNew.equals(idComDireccionOld)) {
                idComDireccionNew.getProproveedorList().add(proproveedor);
                idComDireccionNew = em.merge(idComDireccionNew);
            }
            if (idComDatoContactoOld != null && !idComDatoContactoOld.equals(idComDatoContactoNew)) {
                idComDatoContactoOld.getProproveedorList().remove(proproveedor);
                idComDatoContactoOld = em.merge(idComDatoContactoOld);
            }
            if (idComDatoContactoNew != null && !idComDatoContactoNew.equals(idComDatoContactoOld)) {
                idComDatoContactoNew.getProproveedorList().add(proproveedor);
                idComDatoContactoNew = em.merge(idComDatoContactoNew);
            }
            if (idCatTipoProveedorOld != null && !idCatTipoProveedorOld.equals(idCatTipoProveedorNew)) {
                idCatTipoProveedorOld.getProproveedorList().remove(proproveedor);
                idCatTipoProveedorOld = em.merge(idCatTipoProveedorOld);
            }
            if (idCatTipoProveedorNew != null && !idCatTipoProveedorNew.equals(idCatTipoProveedorOld)) {
                idCatTipoProveedorNew.getProproveedorList().add(proproveedor);
                idCatTipoProveedorNew = em.merge(idCatTipoProveedorNew);
            }
            if (idProCatStatusProveedorOld != null && !idProCatStatusProveedorOld.equals(idProCatStatusProveedorNew)) {
                idProCatStatusProveedorOld.getProproveedorList().remove(proproveedor);
                idProCatStatusProveedorOld = em.merge(idProCatStatusProveedorOld);
            }
            if (idProCatStatusProveedorNew != null && !idProCatStatusProveedorNew.equals(idProCatStatusProveedorOld)) {
                idProCatStatusProveedorNew.getProproveedorList().add(proproveedor);
                idProCatStatusProveedorNew = em.merge(idProCatStatusProveedorNew);
            }
            for (Proproducto proproductoListOldProproducto : proproductoListOld) {
                if (!proproductoListNew.contains(proproductoListOldProproducto)) {
                    proproductoListOldProproducto.setIdProveedor(null);
                    proproductoListOldProproducto = em.merge(proproductoListOldProproducto);
                }
            }
            for (Proproducto proproductoListNewProproducto : proproductoListNew) {
                if (!proproductoListOld.contains(proproductoListNewProproducto)) {
                    Proproveedor oldIdProveedorOfProproductoListNewProproducto = proproductoListNewProproducto.getIdProveedor();
                    proproductoListNewProproducto.setIdProveedor(proproveedor);
                    proproductoListNewProproducto = em.merge(proproductoListNewProproducto);
                    if (oldIdProveedorOfProproductoListNewProproducto != null && !oldIdProveedorOfProproductoListNewProproducto.equals(proproveedor)) {
                        oldIdProveedorOfProproductoListNewProproducto.getProproductoList().remove(proproductoListNewProproducto);
                        oldIdProveedorOfProproductoListNewProproducto = em.merge(oldIdProveedorOfProproductoListNewProproducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proproveedor.getId();
                if (findProproveedor(id) == null) {
                    throw new NonexistentEntityException("The proproveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proproveedor proproveedor;
            try {
                proproveedor = em.getReference(Proproveedor.class, id);
                proproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proproveedor with id " + id + " no longer exists.", enfe);
            }
            Comdireccion idComDireccion = proproveedor.getIdComDireccion();
            if (idComDireccion != null) {
                idComDireccion.getProproveedorList().remove(proproveedor);
                idComDireccion = em.merge(idComDireccion);
            }
            Comdatocontacto idComDatoContacto = proproveedor.getIdComDatoContacto();
            if (idComDatoContacto != null) {
                idComDatoContacto.getProproveedorList().remove(proproveedor);
                idComDatoContacto = em.merge(idComDatoContacto);
            }
            Cattipoproveedor idCatTipoProveedor = proproveedor.getIdCatTipoProveedor();
            if (idCatTipoProveedor != null) {
                idCatTipoProveedor.getProproveedorList().remove(proproveedor);
                idCatTipoProveedor = em.merge(idCatTipoProveedor);
            }
            Procatstatusproveedor idProCatStatusProveedor = proproveedor.getIdProCatStatusProveedor();
            if (idProCatStatusProveedor != null) {
                idProCatStatusProveedor.getProproveedorList().remove(proproveedor);
                idProCatStatusProveedor = em.merge(idProCatStatusProveedor);
            }
            List<Proproducto> proproductoList = proproveedor.getProproductoList();
            for (Proproducto proproductoListProproducto : proproductoList) {
                proproductoListProproducto.setIdProveedor(null);
                proproductoListProproducto = em.merge(proproductoListProproducto);
            }
            em.remove(proproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proproveedor> findProproveedorEntities() {
        return findProproveedorEntities(true, -1, -1);
    }

    public List<Proproveedor> findProproveedorEntities(int maxResults, int firstResult) {
        return findProproveedorEntities(false, maxResults, firstResult);
    }

    private List<Proproveedor> findProproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proproveedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proproveedor findProproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proproveedor> rt = cq.from(Proproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
