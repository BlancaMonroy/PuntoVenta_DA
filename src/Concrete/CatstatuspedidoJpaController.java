/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import Model.Catstatuspedido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Pedpedidoproveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class CatstatuspedidoJpaController implements Serializable {

    public CatstatuspedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Catstatuspedido catstatuspedido) throws PreexistingEntityException, Exception {
        if (catstatuspedido.getPedpedidoproveedorList() == null) {
            catstatuspedido.setPedpedidoproveedorList(new ArrayList<Pedpedidoproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedpedidoproveedor> attachedPedpedidoproveedorList = new ArrayList<Pedpedidoproveedor>();
            for (Pedpedidoproveedor pedpedidoproveedorListPedpedidoproveedorToAttach : catstatuspedido.getPedpedidoproveedorList()) {
                pedpedidoproveedorListPedpedidoproveedorToAttach = em.getReference(pedpedidoproveedorListPedpedidoproveedorToAttach.getClass(), pedpedidoproveedorListPedpedidoproveedorToAttach.getId());
                attachedPedpedidoproveedorList.add(pedpedidoproveedorListPedpedidoproveedorToAttach);
            }
            catstatuspedido.setPedpedidoproveedorList(attachedPedpedidoproveedorList);
            em.persist(catstatuspedido);
            for (Pedpedidoproveedor pedpedidoproveedorListPedpedidoproveedor : catstatuspedido.getPedpedidoproveedorList()) {
                Catstatuspedido oldIdCatStatusPedidoOfPedpedidoproveedorListPedpedidoproveedor = pedpedidoproveedorListPedpedidoproveedor.getIdCatStatusPedido();
                pedpedidoproveedorListPedpedidoproveedor.setIdCatStatusPedido(catstatuspedido);
                pedpedidoproveedorListPedpedidoproveedor = em.merge(pedpedidoproveedorListPedpedidoproveedor);
                if (oldIdCatStatusPedidoOfPedpedidoproveedorListPedpedidoproveedor != null) {
                    oldIdCatStatusPedidoOfPedpedidoproveedorListPedpedidoproveedor.getPedpedidoproveedorList().remove(pedpedidoproveedorListPedpedidoproveedor);
                    oldIdCatStatusPedidoOfPedpedidoproveedorListPedpedidoproveedor = em.merge(oldIdCatStatusPedidoOfPedpedidoproveedorListPedpedidoproveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatstatuspedido(catstatuspedido.getId()) != null) {
                throw new PreexistingEntityException("Catstatuspedido " + catstatuspedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Catstatuspedido catstatuspedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catstatuspedido persistentCatstatuspedido = em.find(Catstatuspedido.class, catstatuspedido.getId());
            List<Pedpedidoproveedor> pedpedidoproveedorListOld = persistentCatstatuspedido.getPedpedidoproveedorList();
            List<Pedpedidoproveedor> pedpedidoproveedorListNew = catstatuspedido.getPedpedidoproveedorList();
            List<Pedpedidoproveedor> attachedPedpedidoproveedorListNew = new ArrayList<Pedpedidoproveedor>();
            for (Pedpedidoproveedor pedpedidoproveedorListNewPedpedidoproveedorToAttach : pedpedidoproveedorListNew) {
                pedpedidoproveedorListNewPedpedidoproveedorToAttach = em.getReference(pedpedidoproveedorListNewPedpedidoproveedorToAttach.getClass(), pedpedidoproveedorListNewPedpedidoproveedorToAttach.getId());
                attachedPedpedidoproveedorListNew.add(pedpedidoproveedorListNewPedpedidoproveedorToAttach);
            }
            pedpedidoproveedorListNew = attachedPedpedidoproveedorListNew;
            catstatuspedido.setPedpedidoproveedorList(pedpedidoproveedorListNew);
            catstatuspedido = em.merge(catstatuspedido);
            for (Pedpedidoproveedor pedpedidoproveedorListOldPedpedidoproveedor : pedpedidoproveedorListOld) {
                if (!pedpedidoproveedorListNew.contains(pedpedidoproveedorListOldPedpedidoproveedor)) {
                    pedpedidoproveedorListOldPedpedidoproveedor.setIdCatStatusPedido(null);
                    pedpedidoproveedorListOldPedpedidoproveedor = em.merge(pedpedidoproveedorListOldPedpedidoproveedor);
                }
            }
            for (Pedpedidoproveedor pedpedidoproveedorListNewPedpedidoproveedor : pedpedidoproveedorListNew) {
                if (!pedpedidoproveedorListOld.contains(pedpedidoproveedorListNewPedpedidoproveedor)) {
                    Catstatuspedido oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor = pedpedidoproveedorListNewPedpedidoproveedor.getIdCatStatusPedido();
                    pedpedidoproveedorListNewPedpedidoproveedor.setIdCatStatusPedido(catstatuspedido);
                    pedpedidoproveedorListNewPedpedidoproveedor = em.merge(pedpedidoproveedorListNewPedpedidoproveedor);
                    if (oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor != null && !oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor.equals(catstatuspedido)) {
                        oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor.getPedpedidoproveedorList().remove(pedpedidoproveedorListNewPedpedidoproveedor);
                        oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor = em.merge(oldIdCatStatusPedidoOfPedpedidoproveedorListNewPedpedidoproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catstatuspedido.getId();
                if (findCatstatuspedido(id) == null) {
                    throw new NonexistentEntityException("The catstatuspedido with id " + id + " no longer exists.");
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
            Catstatuspedido catstatuspedido;
            try {
                catstatuspedido = em.getReference(Catstatuspedido.class, id);
                catstatuspedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catstatuspedido with id " + id + " no longer exists.", enfe);
            }
            List<Pedpedidoproveedor> pedpedidoproveedorList = catstatuspedido.getPedpedidoproveedorList();
            for (Pedpedidoproveedor pedpedidoproveedorListPedpedidoproveedor : pedpedidoproveedorList) {
                pedpedidoproveedorListPedpedidoproveedor.setIdCatStatusPedido(null);
                pedpedidoproveedorListPedpedidoproveedor = em.merge(pedpedidoproveedorListPedpedidoproveedor);
            }
            em.remove(catstatuspedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Catstatuspedido> findCatstatuspedidoEntities() {
        return findCatstatuspedidoEntities(true, -1, -1);
    }

    public List<Catstatuspedido> findCatstatuspedidoEntities(int maxResults, int firstResult) {
        return findCatstatuspedidoEntities(false, maxResults, firstResult);
    }

    private List<Catstatuspedido> findCatstatuspedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Catstatuspedido.class));
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

    public Catstatuspedido findCatstatuspedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Catstatuspedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatstatuspedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Catstatuspedido> rt = cq.from(Catstatuspedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
