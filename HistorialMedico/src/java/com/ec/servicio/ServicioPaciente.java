/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Paciente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioPaciente {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Paciente usuario) {

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

    public void eliminar(Paciente usuario) {

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

    public void modificar(Paciente usuario) {

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

    public Paciente findPacientePorNombre(String valor, Boolean estado) {

        List<Paciente> listaClientes = new ArrayList<Paciente>();
        Paciente usuarioObtenido = new Paciente();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Paciente u WHERE u.pacRuc LIKE :pacRuc OR u.pacNombre LIKE :pacNombre AND u.pacEstado=:pacEstado ORDER BY u.pacNombre ASC");
            query.setParameter("pacRuc", "%" + valor + "%");
            query.setParameter("pacNombre", "%" + valor + "%");
            query.setParameter("pacEstado", estado);
            listaClientes = (List<Paciente>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (Paciente usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindPacientePorNombre  " + e);
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<Paciente> finAll(String nombre) {
        List<Paciente> listaPacientes = new ArrayList<Paciente>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Paciente u");
//            query.setParameter("usuNombre", "%" + nombre + "%");
            listaPacientes = (List<Paciente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaPacientes;
    }

    public List<Paciente> finLike(String nombre, Boolean estado) {
        List<Paciente> listaPacientes = new ArrayList<Paciente>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Paciente u WHERE (u.pacNombres LIKE :pacNombre OR u.pacRuc LIKE :pacRuc) and u.pacEstado=:pacEstado ORDER BY u.pacNombres ASC");
            query.setParameter("pacNombre", "%" + nombre + "%");
            query.setParameter("pacRuc", "%" + nombre + "%");
            query.setParameter("pacEstado", estado);
            listaPacientes = (List<Paciente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error usuarios finAll " + e.getMessage());
        } finally {
            em.close();
        }

        return listaPacientes;
    }
}
