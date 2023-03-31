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
@Table(name = "examen")
@NamedQueries({
    @NamedQuery(name = "Examen.findAll", query = "SELECT e FROM Examen e")})
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_examne")
    private Integer idExamne;
    @Column(name = "exa_descripcion")
    private String exaDescripcion;
    @Column(name = "exa_path")
    private String exaPath;
    @Column(name = "exa_activo")
    private Boolean exaActivo;
    @JoinColumn(name = "id_visita_medica", referencedColumnName = "id_visita_medica")
    @ManyToOne
    private VisitaMedica idVisitaMedica;

    public Examen() {
    }

    public Examen(Integer idExamne) {
        this.idExamne = idExamne;
    }

    public Integer getIdExamne() {
        return idExamne;
    }

    public void setIdExamne(Integer idExamne) {
        this.idExamne = idExamne;
    }

    public String getExaDescripcion() {
        return exaDescripcion;
    }

    public void setExaDescripcion(String exaDescripcion) {
        this.exaDescripcion = exaDescripcion;
    }

    public String getExaPath() {
        return exaPath;
    }

    public void setExaPath(String exaPath) {
        this.exaPath = exaPath;
    }

    public Boolean getExaActivo() {
        return exaActivo;
    }

    public void setExaActivo(Boolean exaActivo) {
        this.exaActivo = exaActivo;
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
        hash += (idExamne != null ? idExamne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examen)) {
            return false;
        }
        Examen other = (Examen) object;
        if ((this.idExamne == null && other.idExamne != null) || (this.idExamne != null && !this.idExamne.equals(other.idExamne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Examen[ idExamne=" + idExamne + " ]";
    }
    
}
