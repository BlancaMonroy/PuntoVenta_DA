/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import Model.Cattipoproducto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Proproducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class CattipoproductoJpaController implements Serializable {

    public CattipoproductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cattipoproducto cattipoproducto) throws PreexistingEntityException, Exception {
        if (cattipoproducto.getProproductoList() == null) {
            cattipoproducto.setProproductoList(new ArrayList<Proproducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proproducto> attachedProproductoList = new ArrayList<Proproducto>();
            for (Proproducto proproductoListProproductoToAttach : cattipoproducto.getProproductoList()) {
                proproductoListProproductoToAttach = em.getReference(proproductoListProproductoToAttach.getClass(), proproductoListProproductoToAttach.getId());
                attachedProproductoList.add(proproductoListProproductoToAttach);
            }
            cattipoproducto.setProproductoList(attachedProproductoList);
            em.persist(cattipoproducto);
            for (Proproducto proproductoListProproducto : cattipoproducto.getProproductoList()) {
                Cattipoproducto oldIdCatTipoProductoOfProproductoListProproducto = proproductoListProproducto.getIdCatTipoProducto();
                proproductoListProproducto.setIdCatTipoProducto(cattipoproducto);
                proproductoListProproducto = em.merge(proproductoListProproducto);
                if (oldIdCatTipoProductoOfProproductoListProproducto != null) {
                    oldIdCatTipoProductoOfProproductoListProproducto.getProproductoList().remove(proproductoListProproducto);
                    oldIdCatTipoProductoOfProproductoListProproducto = em.merge(oldIdCatTipoProductoOfProproductoListProproducto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCattipoproducto(cattipoproducto.getId()) != null) {
                throw new PreexistingEntityException("Cattipoproducto " + cattipoproducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cattipoproducto cattipoproducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cattipoproducto persistentCattipoproducto = em.find(Cattipoproducto.class, cattipoproducto.getId());
            List<Proproducto> proproductoListOld = persistentCattipoproducto.getProproductoList();
            List<Proproducto> proproductoListNew = cattipoproducto.getProproductoList();
            List<Proproducto> attachedProproductoListNew = new ArrayList<Proproducto>();
            for (Proproducto proproductoListNewProproductoToAttach : proproductoListNew) {
                proproductoListNewProproductoToAttach = em.getReference(proproductoListNewProproductoToAttach.getClass(), proproductoListNewProproductoToAttach.getId());
                attachedProproductoListNew.add(proproductoListNewProproductoToAttach);
            }
            proproductoListNew = attachedProproductoListNew;
            cattipoproducto.setProproductoList(proproductoListNew);
            cattipoproducto = em.merge(cattipoproducto);
            for (Proproducto proproductoListOldProproducto : proproductoListOld) {
                if (!proproductoListNew.contains(proproductoListOldProproducto)) {
                    proproductoListOldProproducto.setIdCatTipoProducto(null);
                    proproductoListOldProproducto = em.merge(proproductoListOldProproducto);
                }
            }
            for (Proproducto proproductoListNewProproducto : proproductoListNew) {
                if (!proproductoListOld.contains(proproductoListNewProproducto)) {
                    Cattipoproducto oldIdCatTipoProductoOfProproductoListNewProproducto = proproductoListNewProproducto.getIdCatTipoProducto();
                    proproductoListNewProproducto.setIdCatTipoProducto(cattipoproducto);
                    proproductoListNewProproducto = em.merge(proproductoListNewProproducto);
                    if (oldIdCatTipoProductoOfProproductoListNewProproducto != null && !oldIdCatTipoProductoOfProproductoListNewProproducto.equals(cattipoproducto)) {
                        oldIdCatTipoProductoOfProproductoListNewProproducto.getProproductoList().remove(proproductoListNewProproducto);
                        oldIdCatTipoProductoOfProproductoListNewProproducto = em.merge(oldIdCatTipoProductoOfProproductoListNewProproducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cattipoproducto.getId();
                if (findCattipoproducto(id) == null) {
                    throw new NonexistentEntityException("The cattipoproducto with id " + id + " no longer exists.");
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
            Cattipoproducto cattipoproducto;
            try {
                cattipoproducto = em.getReference(Cattipoproducto.class, id);
                cattipoproducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cattipoproducto with id " + id + " no longer exists.", enfe);
            }
            List<Proproducto> proproductoList = cattipoproducto.getProproductoList();
            for (Proproducto proproductoListProproducto : proproductoList) {
                proproductoListProproducto.setIdCatTipoProducto(null);
                proproductoListProproducto = em.merge(proproductoListProproducto);
            }
            em.remove(cattipoproducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cattipoproducto> findCattipoproductoEntities() {
        return findCattipoproductoEntities(true, -1, -1);
    }

    public List<Cattipoproducto> findCattipoproductoEntities(int maxResults, int firstResult) {
        return findCattipoproductoEntities(false, maxResults, firstResult);
    }

    private List<Cattipoproducto> findCattipoproductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cattipoproducto.class));
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

    public Cattipoproducto findCattipoproducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cattipoproducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCattipoproductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cattipoproducto> rt = cq.from(Cattipoproducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
