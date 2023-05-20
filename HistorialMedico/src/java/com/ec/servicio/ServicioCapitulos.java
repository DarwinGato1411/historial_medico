/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Paciente;
import com.ec.entidad.Capitulo;
import com.ec.entidad.VisitaMedica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCapitulos {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Capitulo usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Capitulo usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(usuario));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  usuario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Capitulo usuario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Capitulo findForVisitaMedica(String capDetalle) {

        List<Capitulo> listaClientes = new ArrayList<Capitulo>();
        Capitulo usuarioObtenido = new Capitulo();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Capitulo u WHERE u.capDetalle=:capDetalle ORDER BY u.capDetalle ASC");
            query.setParameter("capDetalle", capDetalle);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<Capitulo>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (Capitulo usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindCapituloPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<Capitulo> finAll() {
        List<Capitulo> listaCapitulos = new ArrayList<Capitulo>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Capitulo u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaCapitulos = (List<Capitulo>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCapitulos;
    }

    public List<Capitulo> finLike(String valor) {
        List<Capitulo> listaCapitulos = new ArrayList<Capitulo>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Capitulo u WHERE UPPER(u.capDetalle) LIKE :capDetalle ORDER BY u.capDetalle ASC");
            query.setParameter("capDetalle", "%" + valor + "%");
            listaCapitulos = (List<Capitulo>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCapitulos;
    }
}
