/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Concrete.exceptions.PreexistingEntityException;
import Model.Cattipoproveedor;
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
public class CattipoproveedorJpaController implements Serializable {

    public CattipoproveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cattipoproveedor cattipoproveedor) throws PreexistingEntityException, Exception {
        if (cattipoproveedor.getProproveedorList() == null) {
            cattipoproveedor.setProproveedorList(new ArrayList<Proproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proproveedor> attachedProproveedorList = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListProproveedorToAttach : cattipoproveedor.getProproveedorList()) {
                proproveedorListProproveedorToAttach = em.getReference(proproveedorListProproveedorToAttach.getClass(), proproveedorListProproveedorToAttach.getId());
                attachedProproveedorList.add(proproveedorListProproveedorToAttach);
            }
            cattipoproveedor.setProproveedorList(attachedProproveedorList);
            em.persist(cattipoproveedor);
            for (Proproveedor proproveedorListProproveedor : cattipoproveedor.getProproveedorList()) {
                Cattipoproveedor oldIdCatTipoProveedorOfProproveedorListProproveedor = proproveedorListProproveedor.getIdCatTipoProveedor();
                proproveedorListProproveedor.setIdCatTipoProveedor(cattipoproveedor);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
                if (oldIdCatTipoProveedorOfProproveedorListProproveedor != null) {
                    oldIdCatTipoProveedorOfProproveedorListProproveedor.getProproveedorList().remove(proproveedorListProproveedor);
                    oldIdCatTipoProveedorOfProproveedorListProproveedor = em.merge(oldIdCatTipoProveedorOfProproveedorListProproveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCattipoproveedor(cattipoproveedor.getId()) != null) {
                throw new PreexistingEntityException("Cattipoproveedor " + cattipoproveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cattipoproveedor cattipoproveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cattipoproveedor persistentCattipoproveedor = em.find(Cattipoproveedor.class, cattipoproveedor.getId());
            List<Proproveedor> proproveedorListOld = persistentCattipoproveedor.getProproveedorList();
            List<Proproveedor> proproveedorListNew = cattipoproveedor.getProproveedorList();
            List<Proproveedor> attachedProproveedorListNew = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListNewProproveedorToAttach : proproveedorListNew) {
                proproveedorListNewProproveedorToAttach = em.getReference(proproveedorListNewProproveedorToAttach.getClass(), proproveedorListNewProproveedorToAttach.getId());
                attachedProproveedorListNew.add(proproveedorListNewProproveedorToAttach);
            }
            proproveedorListNew = attachedProproveedorListNew;
            cattipoproveedor.setProproveedorList(proproveedorListNew);
            cattipoproveedor = em.merge(cattipoproveedor);
            for (Proproveedor proproveedorListOldProproveedor : proproveedorListOld) {
                if (!proproveedorListNew.contains(proproveedorListOldProproveedor)) {
                    proproveedorListOldProproveedor.setIdCatTipoProveedor(null);
                    proproveedorListOldProproveedor = em.merge(proproveedorListOldProproveedor);
                }
            }
            for (Proproveedor proproveedorListNewProproveedor : proproveedorListNew) {
                if (!proproveedorListOld.contains(proproveedorListNewProproveedor)) {
                    Cattipoproveedor oldIdCatTipoProveedorOfProproveedorListNewProproveedor = proproveedorListNewProproveedor.getIdCatTipoProveedor();
                    proproveedorListNewProproveedor.setIdCatTipoProveedor(cattipoproveedor);
                    proproveedorListNewProproveedor = em.merge(proproveedorListNewProproveedor);
                    if (oldIdCatTipoProveedorOfProproveedorListNewProproveedor != null && !oldIdCatTipoProveedorOfProproveedorListNewProproveedor.equals(cattipoproveedor)) {
                        oldIdCatTipoProveedorOfProproveedorListNewProproveedor.getProproveedorList().remove(proproveedorListNewProproveedor);
                        oldIdCatTipoProveedorOfProproveedorListNewProproveedor = em.merge(oldIdCatTipoProveedorOfProproveedorListNewProproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cattipoproveedor.getId();
                if (findCattipoproveedor(id) == null) {
                    throw new NonexistentEntityException("The cattipoproveedor with id " + id + " no longer exists.");
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
            Cattipoproveedor cattipoproveedor;
            try {
                cattipoproveedor = em.getReference(Cattipoproveedor.class, id);
                cattipoproveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cattipoproveedor with id " + id + " no longer exists.", enfe);
            }
            List<Proproveedor> proproveedorList = cattipoproveedor.getProproveedorList();
            for (Proproveedor proproveedorListProproveedor : proproveedorList) {
                proproveedorListProproveedor.setIdCatTipoProveedor(null);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
            }
            em.remove(cattipoproveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cattipoproveedor> findCattipoproveedorEntities() {
        return findCattipoproveedorEntities(true, -1, -1);
    }

    public List<Cattipoproveedor> findCattipoproveedorEntities(int maxResults, int firstResult) {
        return findCattipoproveedorEntities(false, maxResults, firstResult);
    }

    private List<Cattipoproveedor> findCattipoproveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cattipoproveedor.class));
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

    public Cattipoproveedor findCattipoproveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cattipoproveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getCattipoproveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cattipoproveedor> rt = cq.from(Cattipoproveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
