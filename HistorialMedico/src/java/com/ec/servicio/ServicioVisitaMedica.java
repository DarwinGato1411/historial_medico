/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Paciente;
import com.ec.entidad.VisitaMedica;
import com.ec.entidad.VisitaMedica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioVisitaMedica {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(VisitaMedica usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario");
        } finally {
            em.close();
        }

    }

    public void eliminar(VisitaMedica usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(usuario));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  usuario" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(VisitaMedica usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario");
        } finally {
            em.close();
        }

    }

    public VisitaMedica findForPaciente(Paciente idPaciente) {

        List<VisitaMedica> listaClientes = new ArrayList<VisitaMedica>();
        VisitaMedica usuarioObtenido = new VisitaMedica();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM VisitaMedica u WHERE u.idPaciente=:idPaciente ORDER BY u.visFecha DESC");
            query.setParameter("idPaciente", idPaciente);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<VisitaMedica>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (VisitaMedica usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindVisitaMedicaPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<VisitaMedica> finAll(String nombre) {
        List<VisitaMedica> listaVisitaMedicas = new ArrayList<VisitaMedica>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM VisitaMedica u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaVisitaMedicas = (List<VisitaMedica>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaVisitaMedicas;
    }
}
