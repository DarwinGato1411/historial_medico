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
@Table(name = "horario")
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")})
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_horario")
    private Integer idHorario;
    @Column(name = "hor_numero_dia")
    private Integer horNumeroDia;
    @Column(name = "hor_nombre_dia")
    private String horNombreDia;
    @Column(name = "hor_inicio_man")
    @Temporal(TemporalType.TIME)
    private Date horInicioMan;
    @Column(name = "hor_fin_man")
    @Temporal(TemporalType.TIME)
    private Date horFinMan;
    @Column(name = "hor_inicio_tar")
    @Temporal(TemporalType.TIME)
    private Date horInicioTar;
    @Column(name = "hor_fin_tar")
    @Temporal(TemporalType.TIME)
    private Date horFinTar;
    @Column(name = "hor_intervalo")
    private Integer horIntervalo;
    @Column(name = "hor_estado")
    private Boolean horEstado;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;

    public Horario() {
    }

    public Horario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getHorNumeroDia() {
        return horNumeroDia;
    }

    public void setHorNumeroDia(Integer horNumeroDia) {
        this.horNumeroDia = horNumeroDia;
    }

    public String getHorNombreDia() {
        return horNombreDia;
    }

    public void setHorNombreDia(String horNombreDia) {
        this.horNombreDia = horNombreDia;
    }

    public Date getHorInicioMan() {
        return horInicioMan;
    }

    public void setHorInicioMan(Date horInicioMan) {
        this.horInicioMan = horInicioMan;
    }

    public Date getHorFinMan() {
        return horFinMan;
    }

    public void setHorFinMan(Date horFinMan) {
        this.horFinMan = horFinMan;
    }

    public Date getHorInicioTar() {
        return horInicioTar;
    }

    public void setHorInicioTar(Date horInicioTar) {
        this.horInicioTar = horInicioTar;
    }

    public Date getHorFinTar() {
        return horFinTar;
    }

    public void setHorFinTar(Date horFinTar) {
        this.horFinTar = horFinTar;
    }

    public Integer getHorIntervalo() {
        return horIntervalo;
    }

    public void setHorIntervalo(Integer horIntervalo) {
        this.horIntervalo = horIntervalo;
    }

    public Boolean getHorEstado() {
        return horEstado;
    }

    public void setHorEstado(Boolean horEstado) {
        this.horEstado = horEstado;
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
        hash += (idHorario != null ? idHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.idHorario == null && other.idHorario != null) || (this.idHorario != null && !this.idHorario.equals(other.idHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Horario[ idHorario=" + idHorario + " ]";
    }
    
}
