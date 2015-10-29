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
import Model.Proproducto;
import Model.Vendetalleventa;
import Model.Venventa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class VendetalleventaJpaController implements Serializable {

    public VendetalleventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendetalleventa vendetalleventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proproducto idProducto = vendetalleventa.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                vendetalleventa.setIdProducto(idProducto);
            }
            Venventa idVenVenta = vendetalleventa.getIdVenVenta();
            if (idVenVenta != null) {
                idVenVenta = em.getReference(idVenVenta.getClass(), idVenVenta.getId());
                vendetalleventa.setIdVenVenta(idVenVenta);
            }
            em.persist(vendetalleventa);
            if (idProducto != null) {
                idProducto.getVendetalleventaList().add(vendetalleventa);
                idProducto = em.merge(idProducto);
            }
            if (idVenVenta != null) {
                idVenVenta.getVendetalleventaList().add(vendetalleventa);
                idVenVenta = em.merge(idVenVenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendetalleventa vendetalleventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendetalleventa persistentVendetalleventa = em.find(Vendetalleventa.class, vendetalleventa.getId());
            Proproducto idProductoOld = persistentVendetalleventa.getIdProducto();
            Proproducto idProductoNew = vendetalleventa.getIdProducto();
            Venventa idVenVentaOld = persistentVendetalleventa.getIdVenVenta();
            Venventa idVenVentaNew = vendetalleventa.getIdVenVenta();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                vendetalleventa.setIdProducto(idProductoNew);
            }
            if (idVenVentaNew != null) {
                idVenVentaNew = em.getReference(idVenVentaNew.getClass(), idVenVentaNew.getId());
                vendetalleventa.setIdVenVenta(idVenVentaNew);
            }
            vendetalleventa = em.merge(vendetalleventa);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getVendetalleventaList().remove(vendetalleventa);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getVendetalleventaList().add(vendetalleventa);
                idProductoNew = em.merge(idProductoNew);
            }
            if (idVenVentaOld != null && !idVenVentaOld.equals(idVenVentaNew)) {
                idVenVentaOld.getVendetalleventaList().remove(vendetalleventa);
                idVenVentaOld = em.merge(idVenVentaOld);
            }
            if (idVenVentaNew != null && !idVenVentaNew.equals(idVenVentaOld)) {
                idVenVentaNew.getVendetalleventaList().add(vendetalleventa);
                idVenVentaNew = em.merge(idVenVentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendetalleventa.getId();
                if (findVendetalleventa(id) == null) {
                    throw new NonexistentEntityException("The vendetalleventa with id " + id + " no longer exists.");
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
            Vendetalleventa vendetalleventa;
            try {
                vendetalleventa = em.getReference(Vendetalleventa.class, id);
                vendetalleventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendetalleventa with id " + id + " no longer exists.", enfe);
            }
            Proproducto idProducto = vendetalleventa.getIdProducto();
            if (idProducto != null) {
                idProducto.getVendetalleventaList().remove(vendetalleventa);
                idProducto = em.merge(idProducto);
            }
            Venventa idVenVenta = vendetalleventa.getIdVenVenta();
            if (idVenVenta != null) {
                idVenVenta.getVendetalleventaList().remove(vendetalleventa);
                idVenVenta = em.merge(idVenVenta);
            }
            em.remove(vendetalleventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendetalleventa> findVendetalleventaEntities() {
        return findVendetalleventaEntities(true, -1, -1);
    }

    public List<Vendetalleventa> findVendetalleventaEntities(int maxResults, int firstResult) {
        return findVendetalleventaEntities(false, maxResults, firstResult);
    }

    private List<Vendetalleventa> findVendetalleventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendetalleventa.class));
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

    public Vendetalleventa findVendetalleventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendetalleventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendetalleventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendetalleventa> rt = cq.from(Vendetalleventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
