/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author gato
 */
public class ServicioGeneral {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void eliminarExamenesRecetas(Integer idVisitaMedica) {
        try {

            em = HelperPersistencia.getEMF();

            em.getTransaction().begin();

            StoredProcedureQuery queryStore = em.createStoredProcedureQuery("eliminar_examen_receta");
            queryStore.registerStoredProcedureParameter("id_visita_param", Integer.class, ParameterMode.IN);

            queryStore.setParameter("id_visita_param", idVisitaMedica);
            queryStore.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error generarEvaluacion " + e.getMessage());
        } finally {
            em.close();
        }

    }

}
