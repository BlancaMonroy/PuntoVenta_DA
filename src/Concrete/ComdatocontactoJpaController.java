/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Model.Comdatocontacto;
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
public class ComdatocontactoJpaController implements Serializable {

    public ComdatocontactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comdatocontacto comdatocontacto) {
        if (comdatocontacto.getProproveedorList() == null) {
            comdatocontacto.setProproveedorList(new ArrayList<Proproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proproveedor> attachedProproveedorList = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListProproveedorToAttach : comdatocontacto.getProproveedorList()) {
                proproveedorListProproveedorToAttach = em.getReference(proproveedorListProproveedorToAttach.getClass(), proproveedorListProproveedorToAttach.getId());
                attachedProproveedorList.add(proproveedorListProproveedorToAttach);
            }
            comdatocontacto.setProproveedorList(attachedProproveedorList);
            em.persist(comdatocontacto);
            for (Proproveedor proproveedorListProproveedor : comdatocontacto.getProproveedorList()) {
                Comdatocontacto oldIdComDatoContactoOfProproveedorListProproveedor = proproveedorListProproveedor.getIdComDatoContacto();
                proproveedorListProproveedor.setIdComDatoContacto(comdatocontacto);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
                if (oldIdComDatoContactoOfProproveedorListProproveedor != null) {
                    oldIdComDatoContactoOfProproveedorListProproveedor.getProproveedorList().remove(proproveedorListProproveedor);
                    oldIdComDatoContactoOfProproveedorListProproveedor = em.merge(oldIdComDatoContactoOfProproveedorListProproveedor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comdatocontacto comdatocontacto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comdatocontacto persistentComdatocontacto = em.find(Comdatocontacto.class, comdatocontacto.getId());
            List<Proproveedor> proproveedorListOld = persistentComdatocontacto.getProproveedorList();
            List<Proproveedor> proproveedorListNew = comdatocontacto.getProproveedorList();
            List<Proproveedor> attachedProproveedorListNew = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListNewProproveedorToAttach : proproveedorListNew) {
                proproveedorListNewProproveedorToAttach = em.getReference(proproveedorListNewProproveedorToAttach.getClass(), proproveedorListNewProproveedorToAttach.getId());
                attachedProproveedorListNew.add(proproveedorListNewProproveedorToAttach);
            }
            proproveedorListNew = attachedProproveedorListNew;
            comdatocontacto.setProproveedorList(proproveedorListNew);
            comdatocontacto = em.merge(comdatocontacto);
            for (Proproveedor proproveedorListOldProproveedor : proproveedorListOld) {
                if (!proproveedorListNew.contains(proproveedorListOldProproveedor)) {
                    proproveedorListOldProproveedor.setIdComDatoContacto(null);
                    proproveedorListOldProproveedor = em.merge(proproveedorListOldProproveedor);
                }
            }
            for (Proproveedor proproveedorListNewProproveedor : proproveedorListNew) {
                if (!proproveedorListOld.contains(proproveedorListNewProproveedor)) {
                    Comdatocontacto oldIdComDatoContactoOfProproveedorListNewProproveedor = proproveedorListNewProproveedor.getIdComDatoContacto();
                    proproveedorListNewProproveedor.setIdComDatoContacto(comdatocontacto);
                    proproveedorListNewProproveedor = em.merge(proproveedorListNewProproveedor);
                    if (oldIdComDatoContactoOfProproveedorListNewProproveedor != null && !oldIdComDatoContactoOfProproveedorListNewProproveedor.equals(comdatocontacto)) {
                        oldIdComDatoContactoOfProproveedorListNewProproveedor.getProproveedorList().remove(proproveedorListNewProproveedor);
                        oldIdComDatoContactoOfProproveedorListNewProproveedor = em.merge(oldIdComDatoContactoOfProproveedorListNewProproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comdatocontacto.getId();
                if (findComdatocontacto(id) == null) {
                    throw new NonexistentEntityException("The comdatocontacto with id " + id + " no longer exists.");
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
            Comdatocontacto comdatocontacto;
            try {
                comdatocontacto = em.getReference(Comdatocontacto.class, id);
                comdatocontacto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comdatocontacto with id " + id + " no longer exists.", enfe);
            }
            List<Proproveedor> proproveedorList = comdatocontacto.getProproveedorList();
            for (Proproveedor proproveedorListProproveedor : proproveedorList) {
                proproveedorListProproveedor.setIdComDatoContacto(null);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
            }
            em.remove(comdatocontacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comdatocontacto> findComdatocontactoEntities() {
        return findComdatocontactoEntities(true, -1, -1);
    }

    public List<Comdatocontacto> findComdatocontactoEntities(int maxResults, int firstResult) {
        return findComdatocontactoEntities(false, maxResults, firstResult);
    }

    private List<Comdatocontacto> findComdatocontactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comdatocontacto.class));
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

    public Comdatocontacto findComdatocontacto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comdatocontacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getComdatocontactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comdatocontacto> rt = cq.from(Comdatocontacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
