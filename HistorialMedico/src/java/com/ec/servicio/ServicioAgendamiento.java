/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Agendamiento;
import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioAgendamiento {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Agendamiento agendamiento) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(agendamiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar agendamiento " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Agendamiento agendamiento) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(agendamiento));
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en eliminar  agendamiento " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Agendamiento agendamiento) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(agendamiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en modificar agendamiento " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Agendamiento findAll() {

        List<Agendamiento> listaDatos = new ArrayList<Agendamiento>();
        Agendamiento agendamiento = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Agendamiento u ");
//            query.setParameter("opcDescripcion", "%" + valor + "%");
            listaDatos = (List<Agendamiento>) query.getResultList();
            if (listaDatos.size() > 0) {
                agendamiento = listaDatos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta agendamiento  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return agendamiento;
    }

    public Agendamiento findEstado(Boolean estado) {

        List<Agendamiento> listaDatos = new ArrayList<Agendamiento>();
        Agendamiento agendamiento = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Agendamiento u WHERE u.ageEstado=:ageEstado ");
            query.setParameter("ageEstado", Boolean.TRUE);
            listaDatos = (List<Agendamiento>) query.getResultList();
            if (listaDatos.size() > 0) {
                agendamiento = listaDatos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta agendamiento  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return agendamiento;
    }

    public List<Agendamiento> findByFechaRegistradoUsuario(Date ageFecha, Boolean ageRegistra, Usuario idUsuario) {

        List<Agendamiento> listaDatos = new ArrayList<Agendamiento>();

        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Agendamiento u WHERE u.ageFecha=:ageFecha and  u.ageRegistra=:ageRegistra  AND u.idUsuario=:idUsuario ORder BY u.ageHoraInicio ASC");
            query.setParameter("ageFecha", ageFecha);
            query.setParameter("ageRegistra", ageRegistra);
            query.setParameter("idUsuario", idUsuario);
            listaDatos = (List<Agendamiento>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en la consulta agendamiento  findByRegistrado  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

    public List<Agendamiento> findByFechaUsuario(Date ageFecha, Usuario idUsuario) {

        List<Agendamiento> listaDatos = new ArrayList<Agendamiento>();

        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Agendamiento u WHERE u.ageFecha=:ageFecha  AND u.idUsuario=:idUsuario");
            query.setParameter("ageFecha", ageFecha);
            query.setParameter("idUsuario", idUsuario);
            listaDatos = (List<Agendamiento>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta agendamiento  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

}
