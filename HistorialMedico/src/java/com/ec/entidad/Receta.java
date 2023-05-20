/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "receta")
@NamedQueries({
    @NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")})
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_receta")
    private Integer idReceta;
    @Column(name = "rec_medicamento")
    private String recMedicamento;
    @Column(name = "rec_descripcion")
    private String recDescripcion;
    @Column(name = "host")
    private String host;
    @Column(name = "rec_cantidad")
    private String recCantidad;
    @Column(name = "rec_m")
    private Boolean recM;
    @Column(name = "rec_t")
    private Boolean recT;
    @Column(name = "rec_n")
    private Boolean recN;
    @JoinColumn(name = "id_visita_medica", referencedColumnName = "id_visita_medica")
    @ManyToOne
    private VisitaMedica idVisitaMedica;

    public Receta() {
    }

    public Receta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public String getRecMedicamento() {
        return recMedicamento;
    }

    public void setRecMedicamento(String recMedicamento) {
        this.recMedicamento = recMedicamento;
    }

    public String getRecDescripcion() {
        return recDescripcion;
    }

    public void setRecDescripcion(String recDescripcion) {
        this.recDescripcion = recDescripcion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRecCantidad() {
        return recCantidad;
    }

    public void setRecCantidad(String recCantidad) {
        this.recCantidad = recCantidad;
    }

    public Boolean getRecM() {
        return recM;
    }

    public void setRecM(Boolean recM) {
        this.recM = recM;
    }

    public Boolean getRecT() {
        return recT;
    }

    public void setRecT(Boolean recT) {
        this.recT = recT;
    }

    public Boolean getRecN() {
        return recN;
    }

    public void setRecN(Boolean recN) {
        this.recN = recN;
    }

    public VisitaMedica getIdVisitaMedica() {
        return idVisitaMedica;
    }

    public void setIdVisitaMedica(VisitaMedica idVisitaMedica) {
        this.idVisitaMedica = idVisitaMedica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceta != null ? idReceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receta)) {
            return false;
        }
        Receta other = (Receta) object;
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Receta[ idReceta=" + idReceta + " ]";
    }
    
}
