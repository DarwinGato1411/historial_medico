/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Detalle;
import com.ec.entidad.Subcapitulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalle {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Detalle usuario) {

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

    public void eliminar(Detalle usuario) {

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

    public void modificar(Detalle usuario) {

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

    public Detalle findForVisitaMedica(String detDetalle) {

        List<Detalle> listaClientes = new ArrayList<Detalle>();
        Detalle usuarioObtenido = new Detalle();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Detalle u WHERE u.detDetalle=:detDetalle ORDER BY u.detDetalle ASC");
            query.setParameter("detDetalle", detDetalle);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<Detalle>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (Detalle usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindDetallePorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<Detalle> findBySubCapitulo(Subcapitulo subcapitulo, String valor) {

        List<Detalle> listaDatos = new ArrayList<Detalle>();
        Detalle usuarioObtenido = new Detalle();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Detalle u WHERE u.idSubcapitulo=:subcapitulo AND UPPER(u.detDetalle) LIKE :detDetalle ORDER BY u.detDetalle ASC");
            query.setParameter("subcapitulo", subcapitulo);
            query.setParameter("detDetalle", "%" + valor + "%");
            listaDatos = (List<Detalle>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindDetallePorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }
    public List<Detalle> findBySubCapituloLike( String valor) {

        List<Detalle> listaDatos = new ArrayList<Detalle>();
        Detalle usuarioObtenido = new Detalle();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Detalle u WHERE UPPER(u.detDetalle) LIKE :detDetalle ORDER BY u.detDetalle ASC");
//            query.setParameter("subcapitulo", subcapitulo);
            query.setParameter("detDetalle", "%" + valor + "%");
            listaDatos = (List<Detalle>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindDetallePorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

    public List<Detalle> finAll(String nombre) {
        List<Detalle> listaDetalles = new ArrayList<Detalle>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Detalle u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaDetalles = (List<Detalle>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDetalles;
    }
}
