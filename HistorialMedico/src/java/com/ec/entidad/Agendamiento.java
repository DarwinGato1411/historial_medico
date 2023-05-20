/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "agendamiento")
@NamedQueries({
    @NamedQuery(name = "Agendamiento.findAll", query = "SELECT a FROM Agendamiento a")})
public class Agendamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_agendamiento")
    private Integer idAgendamiento;
    @Column(name = "age_fecha")
    @Temporal(TemporalType.DATE)
    private Date ageFecha;
    @Column(name = "age_hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date ageHoraInicio;
    @Column(name = "age_hora_fin")
    @Temporal(TemporalType.TIME)
    private Date ageHoraFin;
    @Column(name = "age_observacion")
    private String ageObservacion;
    @Column(name = "age_registra")
    private Boolean ageRegistra;
    @Column(name = "age_finaliza")
    private Boolean ageFinaliza;
    @Column(name = "age_cancela")
    private Boolean ageCancela;
    @Column(name = "age_estado")
    private Boolean ageEstado;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne
    private Paciente idPaciente;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    public Agendamiento() {
    }

    public Agendamiento(Integer idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public Integer getIdAgendamiento() {
        return idAgendamiento;
    }

    public void setIdAgendamiento(Integer idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public Date getAgeFecha() {
        return ageFecha;
    }

    public void setAgeFecha(Date ageFecha) {
        this.ageFecha = ageFecha;
    }

    public Date getAgeHoraInicio() {
        return ageHoraInicio;
    }

    public void setAgeHoraInicio(Date ageHoraInicio) {
        this.ageHoraInicio = ageHoraInicio;
    }

    public Date getAgeHoraFin() {
        return ageHoraFin;
    }

    public void setAgeHoraFin(Date ageHoraFin) {
        this.ageHoraFin = ageHoraFin;
    }

    public String getAgeObservacion() {
        return ageObservacion;
    }

    public void setAgeObservacion(String ageObservacion) {
        this.ageObservacion = ageObservacion;
    }

    public Boolean getAgeRegistra() {
        return ageRegistra;
    }

    public void setAgeRegistra(Boolean ageRegistra) {
        this.ageRegistra = ageRegistra;
    }

    public Boolean getAgeFinaliza() {
        return ageFinaliza;
    }

    public void setAgeFinaliza(Boolean ageFinaliza) {
        this.ageFinaliza = ageFinaliza;
    }

    public Boolean getAgeCancela() {
        return ageCancela;
    }

    public void setAgeCancela(Boolean ageCancela) {
        this.ageCancela = ageCancela;
    }

    public Boolean getAgeEstado() {
        return ageEstado;
    }

    public void setAgeEstado(Boolean ageEstado) {
        this.ageEstado = ageEstado;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgendamiento != null ? idAgendamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agendamiento)) {
            return false;
        }
        Agendamiento other = (Agendamiento) object;
        if ((this.idAgendamiento == null && other.idAgendamiento != null) || (this.idAgendamiento != null && !this.idAgendamiento.equals(other.idAgendamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Agendamiento[ idAgendamiento=" + idAgendamiento + " ]";
    }
    
}
