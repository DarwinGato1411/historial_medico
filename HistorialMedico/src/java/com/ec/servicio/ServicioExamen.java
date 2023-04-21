/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Examen;
import com.ec.entidad.VisitaMedica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioExamen {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Examen examen) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(examen);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar examen");
        } finally {
            em.close();
        }

    }

    public void eliminar(Examen examen) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(examen));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  examen" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Examen examen) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(examen);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar examen");
        } finally {
            em.close();
        }

    }

//    public Examen findForVisiMedica(VisitaMedica idVisitaMedica) {
//
//        List<Examen> listaClientes = new ArrayList<Examen>();
//        Examen examenObtenido = new Examen();
//        try {
//            //Connection connection = em.unwrap(Connection.class);
//
//            em = HelperPersistencia.getEMF();
//            em.getTransaction().begin();
//            Query query = em.createQuery("SELECT u FROM Examen u WHERE u.idVisitaMedica=:idVisitaMedica ORDER BY u.idVisitaMedica.visFecha ASC");
//            query.setParameter("idVisitaMedica", idVisitaMedica);
////            query.setParameter("pacNombre", "%" + valor + "%");
//            listaClientes = (List<Examen>) query.getResultList();
//            if (listaClientes.size() > 0) {
//                for (Examen examen : listaClientes) {
//                    examenObtenido = examen;
//                }
//            } else {
//                examenObtenido = null;
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("Error en lsa consulta examen  FindExamenPorNombre  " + e.getMessage());
//        } finally {
//            em.close();
//        }
//
//        return examenObtenido;
//    }

    public List<Examen> findForVisiMedica(VisitaMedica idVisitaMedica) {

        List<Examen> listaClientes = new ArrayList<Examen>();
        Examen examenObtenido = new Examen();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Examen u WHERE u.idVisitaMedica=:idVisitaMedica ORDER BY u.idVisitaMedica.visFecha ASC");
            query.setParameter("idVisitaMedica", idVisitaMedica);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<Examen>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta examen  FindExamenPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<Examen> finAll(String nombre) {
        List<Examen> listaExamens = new ArrayList<Examen>();
        try {
            System.out.println("Entra a consultar examens");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Examen u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaExamens = (List<Examen>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error examens finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaExamens;
    }
}
