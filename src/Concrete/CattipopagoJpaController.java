/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import Model.Cattipopago;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Pedpagospedidoproveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class CattipopagoJpaController implements Serializable {

    public CattipopagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cattipopago cattipopago) throws PreexistingEntityException, Exception {
        if (cattipopago.getPedpagospedidoproveedorList() == null) {
            cattipopago.setPedpagospedidoproveedorList(new ArrayList<Pedpagospedidoproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedpagospedidoproveedor> attachedPedpagospedidoproveedorList = new ArrayList<Pedpagospedidoproveedor>();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedorToAttach : cattipopago.getPedpagospedidoproveedorList()) {
                pedpagospedidoproveedorListPedpagospedidoproveedorToAttach = em.getReference(pedpagospedidoproveedorListPedpagospedidoproveedorToAttach.getClass(), pedpagospedidoproveedorListPedpagospedidoproveedorToAttach.getId());
                attachedPedpagospedidoproveedorList.add(pedpagospedidoproveedorListPedpagospedidoproveedorToAttach);
            }
            cattipopago.setPedpagospedidoproveedorList(attachedPedpagospedidoproveedorList);
            em.persist(cattipopago);
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedor : cattipopago.getPedpagospedidoproveedorList()) {
                Cattipopago oldIdCatTipoPagoOfPedpagospedidoproveedorListPedpagospedidoproveedor = pedpagospedidoproveedorListPedpagospedidoproveedor.getIdCatTipoPago();
                pedpagospedidoproveedorListPedpagospedidoproveedor.setIdCatTipoPago(cattipopago);
                pedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListPedpagospedidoproveedor);
                if (oldIdCatTipoPagoOfPedpagospedidoproveedorListPedpagospedidoproveedor != null) {
                    oldIdCatTipoPagoOfPedpagospedidoproveedorListPedpagospedidoproveedor.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedorListPedpagospedidoproveedor);
                    oldIdCatTipoPagoOfPedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(oldIdCatTipoPagoOfPedpagospedidoproveedorListPedpagospedidoproveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCattipopago(cattipopago.getId()) != null) {
                throw new PreexistingEntityException("Cattipopago " + cattipopago + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cattipopago cattipopago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cattipopago persistentCattipopago = em.find(Cattipopago.class, cattipopago.getId());
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorListOld = persistentCattipopago.getPedpagospedidoproveedorList();
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorListNew = cattipopago.getPedpagospedidoproveedorList();
            List<Pedpagospedidoproveedor> attachedPedpagospedidoproveedorListNew = new ArrayList<Pedpagospedidoproveedor>();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach : pedpagospedidoproveedorListNew) {
                pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach = em.getReference(pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach.getClass(), pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach.getId());
                attachedPedpagospedidoproveedorListNew.add(pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach);
            }
            pedpagospedidoproveedorListNew = attachedPedpagospedidoproveedorListNew;
            cattipopago.setPedpagospedidoproveedorList(pedpagospedidoproveedorListNew);
            cattipopago = em.merge(cattipopago);
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListOldPedpagospedidoproveedor : pedpagospedidoproveedorListOld) {
                if (!pedpagospedidoproveedorListNew.contains(pedpagospedidoproveedorListOldPedpagospedidoproveedor)) {
                    pedpagospedidoproveedorListOldPedpagospedidoproveedor.setIdCatTipoPago(null);
                    pedpagospedidoproveedorListOldPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListOldPedpagospedidoproveedor);
                }
            }
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListNewPedpagospedidoproveedor : pedpagospedidoproveedorListNew) {
                if (!pedpagospedidoproveedorListOld.contains(pedpagospedidoproveedorListNewPedpagospedidoproveedor)) {
                    Cattipopago oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor = pedpagospedidoproveedorListNewPedpagospedidoproveedor.getIdCatTipoPago();
                    pedpagospedidoproveedorListNewPedpagospedidoproveedor.setIdCatTipoPago(cattipopago);
                    pedpagospedidoproveedorListNewPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListNewPedpagospedidoproveedor);
                    if (oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor != null && !oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor.equals(cattipopago)) {
                        oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedorListNewPedpagospedidoproveedor);
                        oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor = em.merge(oldIdCatTipoPagoOfPedpagospedidoproveedorListNewPedpagospedidoproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cattipopago.getId();
                if (findCattipopago(id) == null) {
                    throw new NonexistentEntityException("The cattipopago with id " + id + " no longer exists.");
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
            Cattipopago cattipopago;
            try {
                cattipopago = em.getReference(Cattipopago.class, id);
                cattipopago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cattipopago with id " + id + " no longer exists.", enfe);
            }
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorList = cattipopago.getPedpagospedidoproveedorList();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedor : pedpagospedidoproveedorList) {
                pedpagospedidoproveedorListPedpagospedidoproveedor.setIdCatTipoPago(null);
                pedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListPedpagospedidoproveedor);
            }
            em.remove(cattipopago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cattipopago> findCattipopagoEntities() {
        return findCattipopagoEntities(true, -1, -1);
    }

    public List<Cattipopago> findCattipopagoEntities(int maxResults, int firstResult) {
        return findCattipopagoEntities(false, maxResults, firstResult);
    }

    private List<Cattipopago> findCattipopagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cattipopago.class));
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

    public Cattipopago findCattipopago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cattipopago.class, id);
        } finally {
            em.close();
        }
    }

    public int getCattipopagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cattipopago> rt = cq.from(Cattipopago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
