/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import Model.Procatstatusproveedor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Proproveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class ProcatstatusproveedorJpaController implements Serializable {

    public ProcatstatusproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procatstatusproveedor procatstatusproveedor) throws PreexistingEntityException, Exception {
        if (procatstatusproveedor.getProproveedorList() == null) {
            procatstatusproveedor.setProproveedorList(new ArrayList<Proproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proproveedor> attachedProproveedorList = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListProproveedorToAttach : procatstatusproveedor.getProproveedorList()) {
                proproveedorListProproveedorToAttach = em.getReference(proproveedorListProproveedorToAttach.getClass(), proproveedorListProproveedorToAttach.getId());
                attachedProproveedorList.add(proproveedorListProproveedorToAttach);
            }
            procatstatusproveedor.setProproveedorList(attachedProproveedorList);
            em.persist(procatstatusproveedor);
            for (Proproveedor proproveedorListProproveedor : procatstatusproveedor.getProproveedorList()) {
                Procatstatusproveedor oldIdProCatStatusProveedorOfProproveedorListProproveedor = proproveedorListProproveedor.getIdProCatStatusProveedor();
                proproveedorListProproveedor.setIdProCatStatusProveedor(procatstatusproveedor);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
                if (oldIdProCatStatusProveedorOfProproveedorListProproveedor != null) {
                    oldIdProCatStatusProveedorOfProproveedorListProproveedor.getProproveedorList().remove(proproveedorListProproveedor);
                    oldIdProCatStatusProveedorOfProproveedorListProproveedor = em.merge(oldIdProCatStatusProveedorOfProproveedorListProproveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProcatstatusproveedor(procatstatusproveedor.getId()) != null) {
                throw new PreexistingEntityException("Procatstatusproveedor " + procatstatusproveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procatstatusproveedor procatstatusproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procatstatusproveedor persistentProcatstatusproveedor = em.find(Procatstatusproveedor.class, procatstatusproveedor.getId());
            List<Proproveedor> proproveedorListOld = persistentProcatstatusproveedor.getProproveedorList();
            List<Proproveedor> proproveedorListNew = procatstatusproveedor.getProproveedorList();
            List<Proproveedor> attachedProproveedorListNew = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListNewProproveedorToAttach : proproveedorListNew) {
                proproveedorListNewProproveedorToAttach = em.getReference(proproveedorListNewProproveedorToAttach.getClass(), proproveedorListNewProproveedorToAttach.getId());
                attachedProproveedorListNew.add(proproveedorListNewProproveedorToAttach);
            }
            proproveedorListNew = attachedProproveedorListNew;
            procatstatusproveedor.setProproveedorList(proproveedorListNew);
            procatstatusproveedor = em.merge(procatstatusproveedor);
            for (Proproveedor proproveedorListOldProproveedor : proproveedorListOld) {
                if (!proproveedorListNew.contains(proproveedorListOldProproveedor)) {
                    proproveedorListOldProproveedor.setIdProCatStatusProveedor(null);
                    proproveedorListOldProproveedor = em.merge(proproveedorListOldProproveedor);
                }
            }
            for (Proproveedor proproveedorListNewProproveedor : proproveedorListNew) {
                if (!proproveedorListOld.contains(proproveedorListNewProproveedor)) {
                    Procatstatusproveedor oldIdProCatStatusProveedorOfProproveedorListNewProproveedor = proproveedorListNewProproveedor.getIdProCatStatusProveedor();
                    proproveedorListNewProproveedor.setIdProCatStatusProveedor(procatstatusproveedor);
                    proproveedorListNewProproveedor = em.merge(proproveedorListNewProproveedor);
                    if (oldIdProCatStatusProveedorOfProproveedorListNewProproveedor != null && !oldIdProCatStatusProveedorOfProproveedorListNewProproveedor.equals(procatstatusproveedor)) {
                        oldIdProCatStatusProveedorOfProproveedorListNewProproveedor.getProproveedorList().remove(proproveedorListNewProproveedor);
                        oldIdProCatStatusProveedorOfProproveedorListNewProproveedor = em.merge(oldIdProCatStatusProveedorOfProproveedorListNewProproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procatstatusproveedor.getId();
                if (findProcatstatusproveedor(id) == null) {
                    throw new NonexistentEntityException("The procatstatusproveedor with id " + id + " no longer exists.");
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
            Procatstatusproveedor procatstatusproveedor;
            try {
                procatstatusproveedor = em.getReference(Procatstatusproveedor.class, id);
                procatstatusproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procatstatusproveedor with id " + id + " no longer exists.", enfe);
            }
            List<Proproveedor> proproveedorList = procatstatusproveedor.getProproveedorList();
            for (Proproveedor proproveedorListProproveedor : proproveedorList) {
                proproveedorListProproveedor.setIdProCatStatusProveedor(null);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
            }
            em.remove(procatstatusproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procatstatusproveedor> findProcatstatusproveedorEntities() {
        return findProcatstatusproveedorEntities(true, -1, -1);
    }

    public List<Procatstatusproveedor> findProcatstatusproveedorEntities(int maxResults, int firstResult) {
        return findProcatstatusproveedorEntities(false, maxResults, firstResult);
    }

    private List<Procatstatusproveedor> findProcatstatusproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procatstatusproveedor.class));
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

    public Procatstatusproveedor findProcatstatusproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procatstatusproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcatstatusproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procatstatusproveedor> rt = cq.from(Procatstatusproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
