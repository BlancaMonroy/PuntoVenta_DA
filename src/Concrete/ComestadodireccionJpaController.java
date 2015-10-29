/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Comdireccion;
import Model.Comestadodireccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class ComestadodireccionJpaController implements Serializable {

    public ComestadodireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comestadodireccion comestadodireccion) throws PreexistingEntityException, Exception {
        if (comestadodireccion.getComdireccionList() == null) {
            comestadodireccion.setComdireccionList(new ArrayList<Comdireccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comdireccion> attachedComdireccionList = new ArrayList<Comdireccion>();
            for (Comdireccion comdireccionListComdireccionToAttach : comestadodireccion.getComdireccionList()) {
                comdireccionListComdireccionToAttach = em.getReference(comdireccionListComdireccionToAttach.getClass(), comdireccionListComdireccionToAttach.getId());
                attachedComdireccionList.add(comdireccionListComdireccionToAttach);
            }
            comestadodireccion.setComdireccionList(attachedComdireccionList);
            em.persist(comestadodireccion);
            for (Comdireccion comdireccionListComdireccion : comestadodireccion.getComdireccionList()) {
                Comestadodireccion oldIdEstadoOfComdireccionListComdireccion = comdireccionListComdireccion.getIdEstado();
                comdireccionListComdireccion.setIdEstado(comestadodireccion);
                comdireccionListComdireccion = em.merge(comdireccionListComdireccion);
                if (oldIdEstadoOfComdireccionListComdireccion != null) {
                    oldIdEstadoOfComdireccionListComdireccion.getComdireccionList().remove(comdireccionListComdireccion);
                    oldIdEstadoOfComdireccionListComdireccion = em.merge(oldIdEstadoOfComdireccionListComdireccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComestadodireccion(comestadodireccion.getId()) != null) {
                throw new PreexistingEntityException("Comestadodireccion " + comestadodireccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comestadodireccion comestadodireccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comestadodireccion persistentComestadodireccion = em.find(Comestadodireccion.class, comestadodireccion.getId());
            List<Comdireccion> comdireccionListOld = persistentComestadodireccion.getComdireccionList();
            List<Comdireccion> comdireccionListNew = comestadodireccion.getComdireccionList();
            List<Comdireccion> attachedComdireccionListNew = new ArrayList<Comdireccion>();
            for (Comdireccion comdireccionListNewComdireccionToAttach : comdireccionListNew) {
                comdireccionListNewComdireccionToAttach = em.getReference(comdireccionListNewComdireccionToAttach.getClass(), comdireccionListNewComdireccionToAttach.getId());
                attachedComdireccionListNew.add(comdireccionListNewComdireccionToAttach);
            }
            comdireccionListNew = attachedComdireccionListNew;
            comestadodireccion.setComdireccionList(comdireccionListNew);
            comestadodireccion = em.merge(comestadodireccion);
            for (Comdireccion comdireccionListOldComdireccion : comdireccionListOld) {
                if (!comdireccionListNew.contains(comdireccionListOldComdireccion)) {
                    comdireccionListOldComdireccion.setIdEstado(null);
                    comdireccionListOldComdireccion = em.merge(comdireccionListOldComdireccion);
                }
            }
            for (Comdireccion comdireccionListNewComdireccion : comdireccionListNew) {
                if (!comdireccionListOld.contains(comdireccionListNewComdireccion)) {
                    Comestadodireccion oldIdEstadoOfComdireccionListNewComdireccion = comdireccionListNewComdireccion.getIdEstado();
                    comdireccionListNewComdireccion.setIdEstado(comestadodireccion);
                    comdireccionListNewComdireccion = em.merge(comdireccionListNewComdireccion);
                    if (oldIdEstadoOfComdireccionListNewComdireccion != null && !oldIdEstadoOfComdireccionListNewComdireccion.equals(comestadodireccion)) {
                        oldIdEstadoOfComdireccionListNewComdireccion.getComdireccionList().remove(comdireccionListNewComdireccion);
                        oldIdEstadoOfComdireccionListNewComdireccion = em.merge(oldIdEstadoOfComdireccionListNewComdireccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comestadodireccion.getId();
                if (findComestadodireccion(id) == null) {
                    throw new NonexistentEntityException("The comestadodireccion with id " + id + " no longer exists.");
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
            Comestadodireccion comestadodireccion;
            try {
                comestadodireccion = em.getReference(Comestadodireccion.class, id);
                comestadodireccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comestadodireccion with id " + id + " no longer exists.", enfe);
            }
            List<Comdireccion> comdireccionList = comestadodireccion.getComdireccionList();
            for (Comdireccion comdireccionListComdireccion : comdireccionList) {
                comdireccionListComdireccion.setIdEstado(null);
                comdireccionListComdireccion = em.merge(comdireccionListComdireccion);
            }
            em.remove(comestadodireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comestadodireccion> findComestadodireccionEntities() {
        return findComestadodireccionEntities(true, -1, -1);
    }

    public List<Comestadodireccion> findComestadodireccionEntities(int maxResults, int firstResult) {
        return findComestadodireccionEntities(false, maxResults, firstResult);
    }

    private List<Comestadodireccion> findComestadodireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comestadodireccion.class));
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

    public Comestadodireccion findComestadodireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comestadodireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getComestadodireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comestadodireccion> rt = cq.from(Comestadodireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
