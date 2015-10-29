/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Concrete;

import Concrete.exceptions.NonexistentEntityException;
import Model.Comdireccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Comestadodireccion;
import Model.Proproveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blanca
 */
public class ComdireccionJpaController implements Serializable {

    public ComdireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comdireccion comdireccion) {
        if (comdireccion.getProproveedorList() == null) {
            comdireccion.setProproveedorList(new ArrayList<Proproveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comestadodireccion idEstado = comdireccion.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                comdireccion.setIdEstado(idEstado);
            }
            List<Proproveedor> attachedProproveedorList = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListProproveedorToAttach : comdireccion.getProproveedorList()) {
                proproveedorListProproveedorToAttach = em.getReference(proproveedorListProproveedorToAttach.getClass(), proproveedorListProproveedorToAttach.getId());
                attachedProproveedorList.add(proproveedorListProproveedorToAttach);
            }
            comdireccion.setProproveedorList(attachedProproveedorList);
            em.persist(comdireccion);
            if (idEstado != null) {
                idEstado.getComdireccionList().add(comdireccion);
                idEstado = em.merge(idEstado);
            }
            for (Proproveedor proproveedorListProproveedor : comdireccion.getProproveedorList()) {
                Comdireccion oldIdComDireccionOfProproveedorListProproveedor = proproveedorListProproveedor.getIdComDireccion();
                proproveedorListProproveedor.setIdComDireccion(comdireccion);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
                if (oldIdComDireccionOfProproveedorListProproveedor != null) {
                    oldIdComDireccionOfProproveedorListProproveedor.getProproveedorList().remove(proproveedorListProproveedor);
                    oldIdComDireccionOfProproveedorListProproveedor = em.merge(oldIdComDireccionOfProproveedorListProproveedor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comdireccion comdireccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comdireccion persistentComdireccion = em.find(Comdireccion.class, comdireccion.getId());
            Comestadodireccion idEstadoOld = persistentComdireccion.getIdEstado();
            Comestadodireccion idEstadoNew = comdireccion.getIdEstado();
            List<Proproveedor> proproveedorListOld = persistentComdireccion.getProproveedorList();
            List<Proproveedor> proproveedorListNew = comdireccion.getProproveedorList();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                comdireccion.setIdEstado(idEstadoNew);
            }
            List<Proproveedor> attachedProproveedorListNew = new ArrayList<Proproveedor>();
            for (Proproveedor proproveedorListNewProproveedorToAttach : proproveedorListNew) {
                proproveedorListNewProproveedorToAttach = em.getReference(proproveedorListNewProproveedorToAttach.getClass(), proproveedorListNewProproveedorToAttach.getId());
                attachedProproveedorListNew.add(proproveedorListNewProproveedorToAttach);
            }
            proproveedorListNew = attachedProproveedorListNew;
            comdireccion.setProproveedorList(proproveedorListNew);
            comdireccion = em.merge(comdireccion);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getComdireccionList().remove(comdireccion);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getComdireccionList().add(comdireccion);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Proproveedor proproveedorListOldProproveedor : proproveedorListOld) {
                if (!proproveedorListNew.contains(proproveedorListOldProproveedor)) {
                    proproveedorListOldProproveedor.setIdComDireccion(null);
                    proproveedorListOldProproveedor = em.merge(proproveedorListOldProproveedor);
                }
            }
            for (Proproveedor proproveedorListNewProproveedor : proproveedorListNew) {
                if (!proproveedorListOld.contains(proproveedorListNewProproveedor)) {
                    Comdireccion oldIdComDireccionOfProproveedorListNewProproveedor = proproveedorListNewProproveedor.getIdComDireccion();
                    proproveedorListNewProproveedor.setIdComDireccion(comdireccion);
                    proproveedorListNewProproveedor = em.merge(proproveedorListNewProproveedor);
                    if (oldIdComDireccionOfProproveedorListNewProproveedor != null && !oldIdComDireccionOfProproveedorListNewProproveedor.equals(comdireccion)) {
                        oldIdComDireccionOfProproveedorListNewProproveedor.getProproveedorList().remove(proproveedorListNewProproveedor);
                        oldIdComDireccionOfProproveedorListNewProproveedor = em.merge(oldIdComDireccionOfProproveedorListNewProproveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comdireccion.getId();
                if (findComdireccion(id) == null) {
                    throw new NonexistentEntityException("The comdireccion with id " + id + " no longer exists.");
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
            Comdireccion comdireccion;
            try {
                comdireccion = em.getReference(Comdireccion.class, id);
                comdireccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comdireccion with id " + id + " no longer exists.", enfe);
            }
            Comestadodireccion idEstado = comdireccion.getIdEstado();
            if (idEstado != null) {
                idEstado.getComdireccionList().remove(comdireccion);
                idEstado = em.merge(idEstado);
            }
            List<Proproveedor> proproveedorList = comdireccion.getProproveedorList();
            for (Proproveedor proproveedorListProproveedor : proproveedorList) {
                proproveedorListProproveedor.setIdComDireccion(null);
                proproveedorListProproveedor = em.merge(proproveedorListProproveedor);
            }
            em.remove(comdireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comdireccion> findComdireccionEntities() {
        return findComdireccionEntities(true, -1, -1);
    }

    public List<Comdireccion> findComdireccionEntities(int maxResults, int firstResult) {
        return findComdireccionEntities(false, maxResults, firstResult);
    }

    private List<Comdireccion> findComdireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comdireccion.class));
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

    public Comdireccion findComdireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comdireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getComdireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comdireccion> rt = cq.from(Comdireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
