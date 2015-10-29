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
import Model.Cattipopago;
import Model.Pedpagospedidoproveedor;
import Model.Pedpedidoproveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class PedpagospedidoproveedorJpaController implements Serializable {

    public PedpagospedidoproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedpagospedidoproveedor pedpagospedidoproveedor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cattipopago idCatTipoPago = pedpagospedidoproveedor.getIdCatTipoPago();
            if (idCatTipoPago != null) {
                idCatTipoPago = em.getReference(idCatTipoPago.getClass(), idCatTipoPago.getId());
                pedpagospedidoproveedor.setIdCatTipoPago(idCatTipoPago);
            }
            Pedpedidoproveedor idPedPedidoProveedor = pedpagospedidoproveedor.getIdPedPedidoProveedor();
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor = em.getReference(idPedPedidoProveedor.getClass(), idPedPedidoProveedor.getId());
                pedpagospedidoproveedor.setIdPedPedidoProveedor(idPedPedidoProveedor);
            }
            em.persist(pedpagospedidoproveedor);
            if (idCatTipoPago != null) {
                idCatTipoPago.getPedpagospedidoproveedorList().add(pedpagospedidoproveedor);
                idCatTipoPago = em.merge(idCatTipoPago);
            }
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor.getPedpagospedidoproveedorList().add(pedpagospedidoproveedor);
                idPedPedidoProveedor = em.merge(idPedPedidoProveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedpagospedidoproveedor pedpagospedidoproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedpagospedidoproveedor persistentPedpagospedidoproveedor = em.find(Pedpagospedidoproveedor.class, pedpagospedidoproveedor.getId());
            Cattipopago idCatTipoPagoOld = persistentPedpagospedidoproveedor.getIdCatTipoPago();
            Cattipopago idCatTipoPagoNew = pedpagospedidoproveedor.getIdCatTipoPago();
            Pedpedidoproveedor idPedPedidoProveedorOld = persistentPedpagospedidoproveedor.getIdPedPedidoProveedor();
            Pedpedidoproveedor idPedPedidoProveedorNew = pedpagospedidoproveedor.getIdPedPedidoProveedor();
            if (idCatTipoPagoNew != null) {
                idCatTipoPagoNew = em.getReference(idCatTipoPagoNew.getClass(), idCatTipoPagoNew.getId());
                pedpagospedidoproveedor.setIdCatTipoPago(idCatTipoPagoNew);
            }
            if (idPedPedidoProveedorNew != null) {
                idPedPedidoProveedorNew = em.getReference(idPedPedidoProveedorNew.getClass(), idPedPedidoProveedorNew.getId());
                pedpagospedidoproveedor.setIdPedPedidoProveedor(idPedPedidoProveedorNew);
            }
            pedpagospedidoproveedor = em.merge(pedpagospedidoproveedor);
            if (idCatTipoPagoOld != null && !idCatTipoPagoOld.equals(idCatTipoPagoNew)) {
                idCatTipoPagoOld.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedor);
                idCatTipoPagoOld = em.merge(idCatTipoPagoOld);
            }
            if (idCatTipoPagoNew != null && !idCatTipoPagoNew.equals(idCatTipoPagoOld)) {
                idCatTipoPagoNew.getPedpagospedidoproveedorList().add(pedpagospedidoproveedor);
                idCatTipoPagoNew = em.merge(idCatTipoPagoNew);
            }
            if (idPedPedidoProveedorOld != null && !idPedPedidoProveedorOld.equals(idPedPedidoProveedorNew)) {
                idPedPedidoProveedorOld.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedor);
                idPedPedidoProveedorOld = em.merge(idPedPedidoProveedorOld);
            }
            if (idPedPedidoProveedorNew != null && !idPedPedidoProveedorNew.equals(idPedPedidoProveedorOld)) {
                idPedPedidoProveedorNew.getPedpagospedidoproveedorList().add(pedpagospedidoproveedor);
                idPedPedidoProveedorNew = em.merge(idPedPedidoProveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedpagospedidoproveedor.getId();
                if (findPedpagospedidoproveedor(id) == null) {
                    throw new NonexistentEntityException("The pedpagospedidoproveedor with id " + id + " no longer exists.");
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
            Pedpagospedidoproveedor pedpagospedidoproveedor;
            try {
                pedpagospedidoproveedor = em.getReference(Pedpagospedidoproveedor.class, id);
                pedpagospedidoproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedpagospedidoproveedor with id " + id + " no longer exists.", enfe);
            }
            Cattipopago idCatTipoPago = pedpagospedidoproveedor.getIdCatTipoPago();
            if (idCatTipoPago != null) {
                idCatTipoPago.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedor);
                idCatTipoPago = em.merge(idCatTipoPago);
            }
            Pedpedidoproveedor idPedPedidoProveedor = pedpagospedidoproveedor.getIdPedPedidoProveedor();
            if (idPedPedidoProveedor != null) {
                idPedPedidoProveedor.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedor);
                idPedPedidoProveedor = em.merge(idPedPedidoProveedor);
            }
            em.remove(pedpagospedidoproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedpagospedidoproveedor> findPedpagospedidoproveedorEntities() {
        return findPedpagospedidoproveedorEntities(true, -1, -1);
    }

    public List<Pedpagospedidoproveedor> findPedpagospedidoproveedorEntities(int maxResults, int firstResult) {
        return findPedpagospedidoproveedorEntities(false, maxResults, firstResult);
    }

    private List<Pedpagospedidoproveedor> findPedpagospedidoproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedpagospedidoproveedor.class));
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

    public Pedpagospedidoproveedor findPedpagospedidoproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedpagospedidoproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedpagospedidoproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedpagospedidoproveedor> rt = cq.from(Pedpagospedidoproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
