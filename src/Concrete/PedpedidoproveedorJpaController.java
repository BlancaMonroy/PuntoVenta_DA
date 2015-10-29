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
import Model.Catstatuspedido;
import Model.Pedpagospedidoproveedor;
import java.util.ArrayList;
import java.util.List;
import Model.Peddetallepedidoproveedor;
import Model.Pedpedidoproveedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class PedpedidoproveedorJpaController implements Serializable {

    public PedpedidoproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedpedidoproveedor pedpedidoproveedor) {
        if (pedpedidoproveedor.getPedpagospedidoproveedorList() == null) {
            pedpedidoproveedor.setPedpagospedidoproveedorList(new ArrayList<Pedpagospedidoproveedor>());
        }
        if (pedpedidoproveedor.getPeddetallepedidoproveedorList() == null) {
            pedpedidoproveedor.setPeddetallepedidoproveedorList(new ArrayList<Peddetallepedidoproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catstatuspedido idCatStatusPedido = pedpedidoproveedor.getIdCatStatusPedido();
            if (idCatStatusPedido != null) {
                idCatStatusPedido = em.getReference(idCatStatusPedido.getClass(), idCatStatusPedido.getId());
                pedpedidoproveedor.setIdCatStatusPedido(idCatStatusPedido);
            }
            List<Pedpagospedidoproveedor> attachedPedpagospedidoproveedorList = new ArrayList<Pedpagospedidoproveedor>();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedorToAttach : pedpedidoproveedor.getPedpagospedidoproveedorList()) {
                pedpagospedidoproveedorListPedpagospedidoproveedorToAttach = em.getReference(pedpagospedidoproveedorListPedpagospedidoproveedorToAttach.getClass(), pedpagospedidoproveedorListPedpagospedidoproveedorToAttach.getId());
                attachedPedpagospedidoproveedorList.add(pedpagospedidoproveedorListPedpagospedidoproveedorToAttach);
            }
            pedpedidoproveedor.setPedpagospedidoproveedorList(attachedPedpagospedidoproveedorList);
            List<Peddetallepedidoproveedor> attachedPeddetallepedidoproveedorList = new ArrayList<Peddetallepedidoproveedor>();
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListPeddetallepedidoproveedorToAttach : pedpedidoproveedor.getPeddetallepedidoproveedorList()) {
                peddetallepedidoproveedorListPeddetallepedidoproveedorToAttach = em.getReference(peddetallepedidoproveedorListPeddetallepedidoproveedorToAttach.getClass(), peddetallepedidoproveedorListPeddetallepedidoproveedorToAttach.getId());
                attachedPeddetallepedidoproveedorList.add(peddetallepedidoproveedorListPeddetallepedidoproveedorToAttach);
            }
            pedpedidoproveedor.setPeddetallepedidoproveedorList(attachedPeddetallepedidoproveedorList);
            em.persist(pedpedidoproveedor);
            if (idCatStatusPedido != null) {
                idCatStatusPedido.getPedpedidoproveedorList().add(pedpedidoproveedor);
                idCatStatusPedido = em.merge(idCatStatusPedido);
            }
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedor : pedpedidoproveedor.getPedpagospedidoproveedorList()) {
                Pedpedidoproveedor oldIdPedPedidoProveedorOfPedpagospedidoproveedorListPedpagospedidoproveedor = pedpagospedidoproveedorListPedpagospedidoproveedor.getIdPedPedidoProveedor();
                pedpagospedidoproveedorListPedpagospedidoproveedor.setIdPedPedidoProveedor(pedpedidoproveedor);
                pedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListPedpagospedidoproveedor);
                if (oldIdPedPedidoProveedorOfPedpagospedidoproveedorListPedpagospedidoproveedor != null) {
                    oldIdPedPedidoProveedorOfPedpagospedidoproveedorListPedpagospedidoproveedor.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedorListPedpagospedidoproveedor);
                    oldIdPedPedidoProveedorOfPedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(oldIdPedPedidoProveedorOfPedpagospedidoproveedorListPedpagospedidoproveedor);
                }
            }
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListPeddetallepedidoproveedor : pedpedidoproveedor.getPeddetallepedidoproveedorList()) {
                Pedpedidoproveedor oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListPeddetallepedidoproveedor = peddetallepedidoproveedorListPeddetallepedidoproveedor.getIdPedPedidoProveedor();
                peddetallepedidoproveedorListPeddetallepedidoproveedor.setIdPedPedidoProveedor(pedpedidoproveedor);
                peddetallepedidoproveedorListPeddetallepedidoproveedor = em.merge(peddetallepedidoproveedorListPeddetallepedidoproveedor);
                if (oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListPeddetallepedidoproveedor != null) {
                    oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListPeddetallepedidoproveedor.getPeddetallepedidoproveedorList().remove(peddetallepedidoproveedorListPeddetallepedidoproveedor);
                    oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListPeddetallepedidoproveedor = em.merge(oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListPeddetallepedidoproveedor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedpedidoproveedor pedpedidoproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedpedidoproveedor persistentPedpedidoproveedor = em.find(Pedpedidoproveedor.class, pedpedidoproveedor.getId());
            Catstatuspedido idCatStatusPedidoOld = persistentPedpedidoproveedor.getIdCatStatusPedido();
            Catstatuspedido idCatStatusPedidoNew = pedpedidoproveedor.getIdCatStatusPedido();
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorListOld = persistentPedpedidoproveedor.getPedpagospedidoproveedorList();
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorListNew = pedpedidoproveedor.getPedpagospedidoproveedorList();
            List<Peddetallepedidoproveedor> peddetallepedidoproveedorListOld = persistentPedpedidoproveedor.getPeddetallepedidoproveedorList();
            List<Peddetallepedidoproveedor> peddetallepedidoproveedorListNew = pedpedidoproveedor.getPeddetallepedidoproveedorList();
            if (idCatStatusPedidoNew != null) {
                idCatStatusPedidoNew = em.getReference(idCatStatusPedidoNew.getClass(), idCatStatusPedidoNew.getId());
                pedpedidoproveedor.setIdCatStatusPedido(idCatStatusPedidoNew);
            }
            List<Pedpagospedidoproveedor> attachedPedpagospedidoproveedorListNew = new ArrayList<Pedpagospedidoproveedor>();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach : pedpagospedidoproveedorListNew) {
                pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach = em.getReference(pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach.getClass(), pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach.getId());
                attachedPedpagospedidoproveedorListNew.add(pedpagospedidoproveedorListNewPedpagospedidoproveedorToAttach);
            }
            pedpagospedidoproveedorListNew = attachedPedpagospedidoproveedorListNew;
            pedpedidoproveedor.setPedpagospedidoproveedorList(pedpagospedidoproveedorListNew);
            List<Peddetallepedidoproveedor> attachedPeddetallepedidoproveedorListNew = new ArrayList<Peddetallepedidoproveedor>();
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListNewPeddetallepedidoproveedorToAttach : peddetallepedidoproveedorListNew) {
                peddetallepedidoproveedorListNewPeddetallepedidoproveedorToAttach = em.getReference(peddetallepedidoproveedorListNewPeddetallepedidoproveedorToAttach.getClass(), peddetallepedidoproveedorListNewPeddetallepedidoproveedorToAttach.getId());
                attachedPeddetallepedidoproveedorListNew.add(peddetallepedidoproveedorListNewPeddetallepedidoproveedorToAttach);
            }
            peddetallepedidoproveedorListNew = attachedPeddetallepedidoproveedorListNew;
            pedpedidoproveedor.setPeddetallepedidoproveedorList(peddetallepedidoproveedorListNew);
            pedpedidoproveedor = em.merge(pedpedidoproveedor);
            if (idCatStatusPedidoOld != null && !idCatStatusPedidoOld.equals(idCatStatusPedidoNew)) {
                idCatStatusPedidoOld.getPedpedidoproveedorList().remove(pedpedidoproveedor);
                idCatStatusPedidoOld = em.merge(idCatStatusPedidoOld);
            }
            if (idCatStatusPedidoNew != null && !idCatStatusPedidoNew.equals(idCatStatusPedidoOld)) {
                idCatStatusPedidoNew.getPedpedidoproveedorList().add(pedpedidoproveedor);
                idCatStatusPedidoNew = em.merge(idCatStatusPedidoNew);
            }
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListOldPedpagospedidoproveedor : pedpagospedidoproveedorListOld) {
                if (!pedpagospedidoproveedorListNew.contains(pedpagospedidoproveedorListOldPedpagospedidoproveedor)) {
                    pedpagospedidoproveedorListOldPedpagospedidoproveedor.setIdPedPedidoProveedor(null);
                    pedpagospedidoproveedorListOldPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListOldPedpagospedidoproveedor);
                }
            }
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListNewPedpagospedidoproveedor : pedpagospedidoproveedorListNew) {
                if (!pedpagospedidoproveedorListOld.contains(pedpagospedidoproveedorListNewPedpagospedidoproveedor)) {
                    Pedpedidoproveedor oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor = pedpagospedidoproveedorListNewPedpagospedidoproveedor.getIdPedPedidoProveedor();
                    pedpagospedidoproveedorListNewPedpagospedidoproveedor.setIdPedPedidoProveedor(pedpedidoproveedor);
                    pedpagospedidoproveedorListNewPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListNewPedpagospedidoproveedor);
                    if (oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor != null && !oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor.equals(pedpedidoproveedor)) {
                        oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor.getPedpagospedidoproveedorList().remove(pedpagospedidoproveedorListNewPedpagospedidoproveedor);
                        oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor = em.merge(oldIdPedPedidoProveedorOfPedpagospedidoproveedorListNewPedpagospedidoproveedor);
                    }
                }
            }
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListOldPeddetallepedidoproveedor : peddetallepedidoproveedorListOld) {
                if (!peddetallepedidoproveedorListNew.contains(peddetallepedidoproveedorListOldPeddetallepedidoproveedor)) {
                    peddetallepedidoproveedorListOldPeddetallepedidoproveedor.setIdPedPedidoProveedor(null);
                    peddetallepedidoproveedorListOldPeddetallepedidoproveedor = em.merge(peddetallepedidoproveedorListOldPeddetallepedidoproveedor);
                }
            }
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListNewPeddetallepedidoproveedor : peddetallepedidoproveedorListNew) {
                if (!peddetallepedidoproveedorListOld.contains(peddetallepedidoproveedorListNewPeddetallepedidoproveedor)) {
                    Pedpedidoproveedor oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor = peddetallepedidoproveedorListNewPeddetallepedidoproveedor.getIdPedPedidoProveedor();
                    peddetallepedidoproveedorListNewPeddetallepedidoproveedor.setIdPedPedidoProveedor(pedpedidoproveedor);
                    peddetallepedidoproveedorListNewPeddetallepedidoproveedor = em.merge(peddetallepedidoproveedorListNewPeddetallepedidoproveedor);
                    if (oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor != null && !oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor.equals(pedpedidoproveedor)) {
                        oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor.getPeddetallepedidoproveedorList().remove(peddetallepedidoproveedorListNewPeddetallepedidoproveedor);
                        oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor = em.merge(oldIdPedPedidoProveedorOfPeddetallepedidoproveedorListNewPeddetallepedidoproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedpedidoproveedor.getId();
                if (findPedpedidoproveedor(id) == null) {
                    throw new NonexistentEntityException("The pedpedidoproveedor with id " + id + " no longer exists.");
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
            Pedpedidoproveedor pedpedidoproveedor;
            try {
                pedpedidoproveedor = em.getReference(Pedpedidoproveedor.class, id);
                pedpedidoproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedpedidoproveedor with id " + id + " no longer exists.", enfe);
            }
            Catstatuspedido idCatStatusPedido = pedpedidoproveedor.getIdCatStatusPedido();
            if (idCatStatusPedido != null) {
                idCatStatusPedido.getPedpedidoproveedorList().remove(pedpedidoproveedor);
                idCatStatusPedido = em.merge(idCatStatusPedido);
            }
            List<Pedpagospedidoproveedor> pedpagospedidoproveedorList = pedpedidoproveedor.getPedpagospedidoproveedorList();
            for (Pedpagospedidoproveedor pedpagospedidoproveedorListPedpagospedidoproveedor : pedpagospedidoproveedorList) {
                pedpagospedidoproveedorListPedpagospedidoproveedor.setIdPedPedidoProveedor(null);
                pedpagospedidoproveedorListPedpagospedidoproveedor = em.merge(pedpagospedidoproveedorListPedpagospedidoproveedor);
            }
            List<Peddetallepedidoproveedor> peddetallepedidoproveedorList = pedpedidoproveedor.getPeddetallepedidoproveedorList();
            for (Peddetallepedidoproveedor peddetallepedidoproveedorListPeddetallepedidoproveedor : peddetallepedidoproveedorList) {
                peddetallepedidoproveedorListPeddetallepedidoproveedor.setIdPedPedidoProveedor(null);
                peddetallepedidoproveedorListPeddetallepedidoproveedor = em.merge(peddetallepedidoproveedorListPeddetallepedidoproveedor);
            }
            em.remove(pedpedidoproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedpedidoproveedor> findPedpedidoproveedorEntities() {
        return findPedpedidoproveedorEntities(true, -1, -1);
    }

    public List<Pedpedidoproveedor> findPedpedidoproveedorEntities(int maxResults, int firstResult) {
        return findPedpedidoproveedorEntities(false, maxResults, firstResult);
    }

    private List<Pedpedidoproveedor> findPedpedidoproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedpedidoproveedor.class));
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

    public Pedpedidoproveedor findPedpedidoproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedpedidoproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedpedidoproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedpedidoproveedor> rt = cq.from(Pedpedidoproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
