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
import Model.Vendetalleventa;
import Model.Venventa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class VenventaJpaController implements Serializable {

    public VenventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venventa venventa) {
        if (venventa.getVendetalleventaList() == null) {
            venventa.setVendetalleventaList(new ArrayList<Vendetalleventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vendetalleventa> attachedVendetalleventaList = new ArrayList<Vendetalleventa>();
            for (Vendetalleventa vendetalleventaListVendetalleventaToAttach : venventa.getVendetalleventaList()) {
                vendetalleventaListVendetalleventaToAttach = em.getReference(vendetalleventaListVendetalleventaToAttach.getClass(), vendetalleventaListVendetalleventaToAttach.getId());
                attachedVendetalleventaList.add(vendetalleventaListVendetalleventaToAttach);
            }
            venventa.setVendetalleventaList(attachedVendetalleventaList);
            em.persist(venventa);
            for (Vendetalleventa vendetalleventaListVendetalleventa : venventa.getVendetalleventaList()) {
                Venventa oldIdVenVentaOfVendetalleventaListVendetalleventa = vendetalleventaListVendetalleventa.getIdVenVenta();
                vendetalleventaListVendetalleventa.setIdVenVenta(venventa);
                vendetalleventaListVendetalleventa = em.merge(vendetalleventaListVendetalleventa);
                if (oldIdVenVentaOfVendetalleventaListVendetalleventa != null) {
                    oldIdVenVentaOfVendetalleventaListVendetalleventa.getVendetalleventaList().remove(vendetalleventaListVendetalleventa);
                    oldIdVenVentaOfVendetalleventaListVendetalleventa = em.merge(oldIdVenVentaOfVendetalleventaListVendetalleventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venventa venventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venventa persistentVenventa = em.find(Venventa.class, venventa.getId());
            List<Vendetalleventa> vendetalleventaListOld = persistentVenventa.getVendetalleventaList();
            List<Vendetalleventa> vendetalleventaListNew = venventa.getVendetalleventaList();
            List<Vendetalleventa> attachedVendetalleventaListNew = new ArrayList<Vendetalleventa>();
            for (Vendetalleventa vendetalleventaListNewVendetalleventaToAttach : vendetalleventaListNew) {
                vendetalleventaListNewVendetalleventaToAttach = em.getReference(vendetalleventaListNewVendetalleventaToAttach.getClass(), vendetalleventaListNewVendetalleventaToAttach.getId());
                attachedVendetalleventaListNew.add(vendetalleventaListNewVendetalleventaToAttach);
            }
            vendetalleventaListNew = attachedVendetalleventaListNew;
            venventa.setVendetalleventaList(vendetalleventaListNew);
            venventa = em.merge(venventa);
            for (Vendetalleventa vendetalleventaListOldVendetalleventa : vendetalleventaListOld) {
                if (!vendetalleventaListNew.contains(vendetalleventaListOldVendetalleventa)) {
                    vendetalleventaListOldVendetalleventa.setIdVenVenta(null);
                    vendetalleventaListOldVendetalleventa = em.merge(vendetalleventaListOldVendetalleventa);
                }
            }
            for (Vendetalleventa vendetalleventaListNewVendetalleventa : vendetalleventaListNew) {
                if (!vendetalleventaListOld.contains(vendetalleventaListNewVendetalleventa)) {
                    Venventa oldIdVenVentaOfVendetalleventaListNewVendetalleventa = vendetalleventaListNewVendetalleventa.getIdVenVenta();
                    vendetalleventaListNewVendetalleventa.setIdVenVenta(venventa);
                    vendetalleventaListNewVendetalleventa = em.merge(vendetalleventaListNewVendetalleventa);
                    if (oldIdVenVentaOfVendetalleventaListNewVendetalleventa != null && !oldIdVenVentaOfVendetalleventaListNewVendetalleventa.equals(venventa)) {
                        oldIdVenVentaOfVendetalleventaListNewVendetalleventa.getVendetalleventaList().remove(vendetalleventaListNewVendetalleventa);
                        oldIdVenVentaOfVendetalleventaListNewVendetalleventa = em.merge(oldIdVenVentaOfVendetalleventaListNewVendetalleventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venventa.getId();
                if (findVenventa(id) == null) {
                    throw new NonexistentEntityException("The venventa with id " + id + " no longer exists.");
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
            Venventa venventa;
            try {
                venventa = em.getReference(Venventa.class, id);
                venventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venventa with id " + id + " no longer exists.", enfe);
            }
            List<Vendetalleventa> vendetalleventaList = venventa.getVendetalleventaList();
            for (Vendetalleventa vendetalleventaListVendetalleventa : vendetalleventaList) {
                vendetalleventaListVendetalleventa.setIdVenVenta(null);
                vendetalleventaListVendetalleventa = em.merge(vendetalleventaListVendetalleventa);
            }
            em.remove(venventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venventa> findVenventaEntities() {
        return findVenventaEntities(true, -1, -1);
    }

    public List<Venventa> findVenventaEntities(int maxResults, int firstResult) {
        return findVenventaEntities(false, maxResults, firstResult);
    }

    private List<Venventa> findVenventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venventa.class));
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

    public Venventa findVenventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venventa> rt = cq.from(Venventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
