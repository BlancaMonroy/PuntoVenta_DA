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
import Model.Cattipoproducto;
import Model.Proproducto;
import Model.Proproveedor;
import Model.Vendetalleventa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class ProproductoJpaController implements Serializable {

    public ProproductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proproducto proproducto) {
        if (proproducto.getVendetalleventaList() == null) {
            proproducto.setVendetalleventaList(new ArrayList<Vendetalleventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cattipoproducto idCatTipoProducto = proproducto.getIdCatTipoProducto();
            if (idCatTipoProducto != null) {
                idCatTipoProducto = em.getReference(idCatTipoProducto.getClass(), idCatTipoProducto.getId());
                proproducto.setIdCatTipoProducto(idCatTipoProducto);
            }
            Proproveedor idProveedor = proproducto.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getId());
                proproducto.setIdProveedor(idProveedor);
            }
            List<Vendetalleventa> attachedVendetalleventaList = new ArrayList<Vendetalleventa>();
            for (Vendetalleventa vendetalleventaListVendetalleventaToAttach : proproducto.getVendetalleventaList()) {
                vendetalleventaListVendetalleventaToAttach = em.getReference(vendetalleventaListVendetalleventaToAttach.getClass(), vendetalleventaListVendetalleventaToAttach.getId());
                attachedVendetalleventaList.add(vendetalleventaListVendetalleventaToAttach);
            }
            proproducto.setVendetalleventaList(attachedVendetalleventaList);
            em.persist(proproducto);
            if (idCatTipoProducto != null) {
                idCatTipoProducto.getProproductoList().add(proproducto);
                idCatTipoProducto = em.merge(idCatTipoProducto);
            }
            if (idProveedor != null) {
                idProveedor.getProproductoList().add(proproducto);
                idProveedor = em.merge(idProveedor);
            }
            for (Vendetalleventa vendetalleventaListVendetalleventa : proproducto.getVendetalleventaList()) {
                Proproducto oldIdProductoOfVendetalleventaListVendetalleventa = vendetalleventaListVendetalleventa.getIdProducto();
                vendetalleventaListVendetalleventa.setIdProducto(proproducto);
                vendetalleventaListVendetalleventa = em.merge(vendetalleventaListVendetalleventa);
                if (oldIdProductoOfVendetalleventaListVendetalleventa != null) {
                    oldIdProductoOfVendetalleventaListVendetalleventa.getVendetalleventaList().remove(vendetalleventaListVendetalleventa);
                    oldIdProductoOfVendetalleventaListVendetalleventa = em.merge(oldIdProductoOfVendetalleventaListVendetalleventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proproducto proproducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proproducto persistentProproducto = em.find(Proproducto.class, proproducto.getId());
            Cattipoproducto idCatTipoProductoOld = persistentProproducto.getIdCatTipoProducto();
            Cattipoproducto idCatTipoProductoNew = proproducto.getIdCatTipoProducto();
            Proproveedor idProveedorOld = persistentProproducto.getIdProveedor();
            Proproveedor idProveedorNew = proproducto.getIdProveedor();
            List<Vendetalleventa> vendetalleventaListOld = persistentProproducto.getVendetalleventaList();
            List<Vendetalleventa> vendetalleventaListNew = proproducto.getVendetalleventaList();
            if (idCatTipoProductoNew != null) {
                idCatTipoProductoNew = em.getReference(idCatTipoProductoNew.getClass(), idCatTipoProductoNew.getId());
                proproducto.setIdCatTipoProducto(idCatTipoProductoNew);
            }
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getId());
                proproducto.setIdProveedor(idProveedorNew);
            }
            List<Vendetalleventa> attachedVendetalleventaListNew = new ArrayList<Vendetalleventa>();
            for (Vendetalleventa vendetalleventaListNewVendetalleventaToAttach : vendetalleventaListNew) {
                vendetalleventaListNewVendetalleventaToAttach = em.getReference(vendetalleventaListNewVendetalleventaToAttach.getClass(), vendetalleventaListNewVendetalleventaToAttach.getId());
                attachedVendetalleventaListNew.add(vendetalleventaListNewVendetalleventaToAttach);
            }
            vendetalleventaListNew = attachedVendetalleventaListNew;
            proproducto.setVendetalleventaList(vendetalleventaListNew);
            proproducto = em.merge(proproducto);
            if (idCatTipoProductoOld != null && !idCatTipoProductoOld.equals(idCatTipoProductoNew)) {
                idCatTipoProductoOld.getProproductoList().remove(proproducto);
                idCatTipoProductoOld = em.merge(idCatTipoProductoOld);
            }
            if (idCatTipoProductoNew != null && !idCatTipoProductoNew.equals(idCatTipoProductoOld)) {
                idCatTipoProductoNew.getProproductoList().add(proproducto);
                idCatTipoProductoNew = em.merge(idCatTipoProductoNew);
            }
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getProproductoList().remove(proproducto);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getProproductoList().add(proproducto);
                idProveedorNew = em.merge(idProveedorNew);
            }
            for (Vendetalleventa vendetalleventaListOldVendetalleventa : vendetalleventaListOld) {
                if (!vendetalleventaListNew.contains(vendetalleventaListOldVendetalleventa)) {
                    vendetalleventaListOldVendetalleventa.setIdProducto(null);
                    vendetalleventaListOldVendetalleventa = em.merge(vendetalleventaListOldVendetalleventa);
                }
            }
            for (Vendetalleventa vendetalleventaListNewVendetalleventa : vendetalleventaListNew) {
                if (!vendetalleventaListOld.contains(vendetalleventaListNewVendetalleventa)) {
                    Proproducto oldIdProductoOfVendetalleventaListNewVendetalleventa = vendetalleventaListNewVendetalleventa.getIdProducto();
                    vendetalleventaListNewVendetalleventa.setIdProducto(proproducto);
                    vendetalleventaListNewVendetalleventa = em.merge(vendetalleventaListNewVendetalleventa);
                    if (oldIdProductoOfVendetalleventaListNewVendetalleventa != null && !oldIdProductoOfVendetalleventaListNewVendetalleventa.equals(proproducto)) {
                        oldIdProductoOfVendetalleventaListNewVendetalleventa.getVendetalleventaList().remove(vendetalleventaListNewVendetalleventa);
                        oldIdProductoOfVendetalleventaListNewVendetalleventa = em.merge(oldIdProductoOfVendetalleventaListNewVendetalleventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proproducto.getId();
                if (findProproducto(id) == null) {
                    throw new NonexistentEntityException("The proproducto with id " + id + " no longer exists.");
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
            Proproducto proproducto;
            try {
                proproducto = em.getReference(Proproducto.class, id);
                proproducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proproducto with id " + id + " no longer exists.", enfe);
            }
            Cattipoproducto idCatTipoProducto = proproducto.getIdCatTipoProducto();
            if (idCatTipoProducto != null) {
                idCatTipoProducto.getProproductoList().remove(proproducto);
                idCatTipoProducto = em.merge(idCatTipoProducto);
            }
            Proproveedor idProveedor = proproducto.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getProproductoList().remove(proproducto);
                idProveedor = em.merge(idProveedor);
            }
            List<Vendetalleventa> vendetalleventaList = proproducto.getVendetalleventaList();
            for (Vendetalleventa vendetalleventaListVendetalleventa : vendetalleventaList) {
                vendetalleventaListVendetalleventa.setIdProducto(null);
                vendetalleventaListVendetalleventa = em.merge(vendetalleventaListVendetalleventa);
            }
            em.remove(proproducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proproducto> findProproductoEntities() {
        return findProproductoEntities(true, -1, -1);
    }

    public List<Proproducto> findProproductoEntities(int maxResults, int firstResult) {
        return findProproductoEntities(false, maxResults, firstResult);
    }

    private List<Proproducto> findProproductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proproducto.class));
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

    public Proproducto findProproducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proproducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProproductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proproducto> rt = cq.from(Proproducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
