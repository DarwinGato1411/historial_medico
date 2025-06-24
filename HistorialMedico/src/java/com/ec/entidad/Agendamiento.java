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

    @Column(name = "c1")
    private String c1;
    @Column(name = "c2")
    private String c2;
    @Column(name = "c3")
    private String c3;
    @Column(name = "c4")
    private String c4;
    @Column(name = "c5")
    private String c5;
    @Column(name = "c6")
    private String c6;
    @Column(name = "c7")
    private String c7;
    @Column(name = "c8")
    private String c8;
    @Column(name = "c9")
    private String c9;
    @Column(name = "c10")
    private String c10;
    @Column(name = "c11")
    private String c11;
    @Column(name = "c12")
    private String c12;
    @Column(name = "c13")
    private String c13;
    @Column(name = "c14")
    private String c14;
    @Column(name = "c15")
    private String c15;
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

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public String getC5() {
        return c5;
    }

    public void setC5(String c5) {
        this.c5 = c5;
    }

    public String getC6() {
        return c6;
    }

    public void setC6(String c6) {
        this.c6 = c6;
    }

    public String getC7() {
        return c7;
    }

    public void setC7(String c7) {
        this.c7 = c7;
    }

    public String getC8() {
        return c8;
    }

    public void setC8(String c8) {
        this.c8 = c8;
    }

    public String getC9() {
        return c9;
    }

    public void setC9(String c9) {
        this.c9 = c9;
    }

    public String getC10() {
        return c10;
    }

    public void setC10(String c10) {
        this.c10 = c10;
    }

    public String getC11() {
        return c11;
    }

    public void setC11(String c11) {
        this.c11 = c11;
    }

    public String getC12() {
        return c12;
    }

    public void setC12(String c12) {
        this.c12 = c12;
    }

    public String getC13() {
        return c13;
    }

    public void setC13(String c13) {
        this.c13 = c13;
    }

    public String getC14() {
        return c14;
    }

    public void setC14(String c14) {
        this.c14 = c14;
    }

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }

    
    
}
