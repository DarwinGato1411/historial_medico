/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Paciente;
import com.ec.entidad.Receta;
import com.ec.entidad.RecetaAnteriorVista;
import com.ec.entidad.VisitaMedica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioReceta {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Receta usuario) {

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

    public void eliminar(Receta usuario) {

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

    public void modificar(Receta usuario) {

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

//    public Receta findForVisitaMedica(VisitaMedica idVisitaMedica) {
//
//        List<Receta> listaClientes = new ArrayList<Receta>();
//        Receta usuarioObtenido = new Receta();
//        try {
//            //Connection connection = em.unwrap(Connection.class);
//
//            em = HelperPersistencia.getEMF();
//            em.getTransaction().begin();
//            Query query = em.createQuery("SELECT u FROM Receta u WHERE u.idVisitaMedica=:idVisitaMedica ORDER BY u.idVisitaMedica.visFecha ASC");
//            query.setParameter("idVisitaMedica", idVisitaMedica);
////            query.setParameter("pacNombre", "%" + valor + "%");
//            listaClientes = (List<Receta>) query.getResultList();
//            if (listaClientes.size() > 0) {
//                for (Receta usuario : listaClientes) {
//                    usuarioObtenido = usuario;
//                }
//            } else {
//                usuarioObtenido = null;
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("Error en lsa consulta usuario  FindRecetaPorNombre  " + e.getMessage());
//        } finally {
//            em.close();
//        }
//
//        return usuarioObtenido;
//    }
    public List<Receta> findForVisitaMedica(VisitaMedica idVisitaMedica) {

        List<Receta> listaClientes = new ArrayList<Receta>();
        Receta usuarioObtenido = new Receta();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Receta u WHERE u.idVisitaMedica=:idVisitaMedica ORDER BY u.idVisitaMedica.visFecha ASC");
            query.setParameter("idVisitaMedica", idVisitaMedica);
//            query.setParameter("pacNombre", "%" + valor + "%");
            listaClientes = (List<Receta>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindRecetaPorNombre  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<RecetaAnteriorVista> findVisitaMedicaAnterior(Paciente paciente) {

        List<RecetaAnteriorVista> listaClientes = new ArrayList<RecetaAnteriorVista>();

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM RecetaAnteriorVista u where u.id_Paciente=:idPaciente");
            query.setParameter("idPaciente", paciente.getIdPaciente());
//            query.setParameter("pacNombre", "%" + valor + "%");
            query.setMaxResults(5);
            listaClientes = (List<RecetaAnteriorVista>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findVisitaMedicaAnterior  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<Receta> finAll(String nombre) {
        List<Receta> listaRecetas = new ArrayList<Receta>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Receta u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaRecetas = (List<Receta>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRecetas;
    }
}
