/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Capitulo;
import com.ec.entidad.Paciente;
import com.ec.entidad.Subcapitulo;
import com.ec.entidad.VisitaMedica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSubCapitulo {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Subcapitulo usuario) {

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

    public void eliminar(Subcapitulo usuario) {

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

    public void modificar(Subcapitulo usuario) {

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

    public Subcapitulo findForVisitaMedica(String subDetalle) {

        List<Subcapitulo> listaClientes = new ArrayList<Subcapitulo>();
        Subcapitulo usuarioObtenido = new Subcapitulo();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Subcapitulo u WHERE u.subDetalle=:subDetalle ORDER BY u.subDetalle ASC");
            query.setParameter("subDetalle", subDetalle);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<Subcapitulo>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (Subcapitulo usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindSubcapituloPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<Subcapitulo> findByCapitulo(Capitulo capitulo) {

        List<Subcapitulo> listaDatos = new ArrayList<Subcapitulo>();
        Subcapitulo usuarioObtenido = new Subcapitulo();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Subcapitulo u WHERE u.idCapitulo=:idCapitulo ORDER BY u.subDetalle ASC");
            query.setParameter("idCapitulo", capitulo);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaDatos = (List<Subcapitulo>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindSubcapituloPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

    public List<Subcapitulo> finAll(String nombre) {
        List<Subcapitulo> listaSubcapitulos = new ArrayList<Subcapitulo>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Subcapitulo u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaSubcapitulos = (List<Subcapitulo>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaSubcapitulos;
    }
}
