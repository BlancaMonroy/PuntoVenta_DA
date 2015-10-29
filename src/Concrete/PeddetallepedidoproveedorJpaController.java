/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Model.Peddetallepedidoproveedor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Pedpedidoproveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class PeddetallepedidoproveedorJpaController implements Serializable {

    public PeddetallepedidoproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peddetallepedidoproveedor peddetallepedidoproveedor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedpedidoproveedor idPedPedidoProveedor = peddetallepedidoproveedor.getIdPedPedidoProveedor();
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor = em.getReference(idPedPedidoProveedor.getClass(), idPedPedidoProveedor.getId());
                peddetallepedidoproveedor.setIdPedPedidoProveedor(idPedPedidoProveedor);
            }
            em.persist(peddetallepedidoproveedor);
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor.getPeddetallepedidoproveedorList().add(peddetallepedidoproveedor);
                idPedPedidoProveedor = em.merge(idPedPedidoProveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peddetallepedidoproveedor peddetallepedidoproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peddetallepedidoproveedor persistentPeddetallepedidoproveedor = em.find(Peddetallepedidoproveedor.class, peddetallepedidoproveedor.getId());
            Pedpedidoproveedor idPedPedidoProveedorOld = persistentPeddetallepedidoproveedor.getIdPedPedidoProveedor();
            Pedpedidoproveedor idPedPedidoProveedorNew = peddetallepedidoproveedor.getIdPedPedidoProveedor();
            if (idPedPedidoProveedorNew != null) {
                idPedPedidoProveedorNew = em.getReference(idPedPedidoProveedorNew.getClass(), idPedPedidoProveedorNew.getId());
                peddetallepedidoproveedor.setIdPedPedidoProveedor(idPedPedidoProveedorNew);
            }
            peddetallepedidoproveedor = em.merge(peddetallepedidoproveedor);
            if (idPedPedidoProveedorOld != null && !idPedPedidoProveedorOld.equals(idPedPedidoProveedorNew)) {
                idPedPedidoProveedorOld.getPeddetallepedidoproveedorList().remove(peddetallepedidoproveedor);
                idPedPedidoProveedorOld = em.merge(idPedPedidoProveedorOld);
            }
            if (idPedPedidoProveedorNew != null && !idPedPedidoProveedorNew.equals(idPedPedidoProveedorOld)) {
                idPedPedidoProveedorNew.getPeddetallepedidoproveedorList().add(peddetallepedidoproveedor);
                idPedPedidoProveedorNew = em.merge(idPedPedidoProveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peddetallepedidoproveedor.getId();
                if (findPeddetallepedidoproveedor(id) == null) {
                    throw new NonexistentEntityException("The peddetallepedidoproveedor with id " + id + " no longer exists.");
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
            Peddetallepedidoproveedor peddetallepedidoproveedor;
            try {
                peddetallepedidoproveedor = em.getReference(Peddetallepedidoproveedor.class, id);
                peddetallepedidoproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peddetallepedidoproveedor with id " + id + " no longer exists.", enfe);
            }
            Pedpedidoproveedor idPedPedidoProveedor = peddetallepedidoproveedor.getIdPedPedidoProveedor();
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor.getPeddetallepedidoproveedorList().remove(peddetallepedidoproveedor);
                idPedPedidoProveedor = em.merge(idPedPedidoProveedor);
            }
            em.remove(peddetallepedidoproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peddetallepedidoproveedor> findPeddetallepedidoproveedorEntities() {
        return findPeddetallepedidoproveedorEntities(true, -1, -1);
    }

    public List<Peddetallepedidoproveedor> findPeddetallepedidoproveedorEntities(int maxResults, int firstResult) {
        return findPeddetallepedidoproveedorEntities(false, maxResults, firstResult);
    }

    private List<Peddetallepedidoproveedor> findPeddetallepedidoproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peddetallepedidoproveedor.class));
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

    public Peddetallepedidoproveedor findPeddetallepedidoproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peddetallepedidoproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeddetallepedidoproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peddetallepedidoproveedor> rt = cq.from(Peddetallepedidoproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
