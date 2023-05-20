/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Horario;
import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioHorario {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Horario horario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(horario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar horario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Horario horario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(horario));
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en eliminar  horario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Horario horario) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(horario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en modificar horario " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Horario findAll() {

        List<Horario> listaDatos = new ArrayList<Horario>();
        Horario horario = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Horario u ");
//            query.setParameter("opcDescripcion", "%" + valor + "%");
            listaDatos = (List<Horario>) query.getResultList();
            if (listaDatos.size() > 0) {
                horario = listaDatos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta horario  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return horario;
    }

    public Horario findActivo() {

        List<Horario> listaDatos = new ArrayList<Horario>();
        Horario horario = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Horario u WHERE u.parActivo=:parActivo ");
            query.setParameter("parActivo", Boolean.TRUE);
            listaDatos = (List<Horario>) query.getResultList();
            if (listaDatos.size() > 0) {
                horario = listaDatos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta horario  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return horario;
    }

    public Horario findByDia(Usuario idUsuario, Integer horNumeroDia) {

        List<Horario> listaDatos = new ArrayList<Horario>();
        Horario horario = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Horario u WHERE u.horNumeroDia=:horNumeroDia  AND u.idUsuario=:idUsuario");
            query.setParameter("horNumeroDia", horNumeroDia);
            query.setParameter("idUsuario", idUsuario);
            listaDatos = (List<Horario>) query.getResultList();
            if (listaDatos.size() > 0) {
                horario = listaDatos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta horario  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return horario;
    }

    public List<Horario> findByUsuario(Usuario idUsuario) {

        List<Horario> listaDatos = new ArrayList<Horario>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Horario u WHERE  u.idUsuario=:idUsuario ORDER BY u.horNumeroDia ASC");
            query.setParameter("idUsuario", idUsuario);
            listaDatos = (List<Horario>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Error en lsa consulta horario  findActivo  " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

}
