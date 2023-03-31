/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "paciente")
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p")})
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paciente")
    private Integer idPaciente;
    @Column(name = "pac_ruc")
    private String pacRuc;
    @Column(name = "pac_nombre")
    private String pacNombre;
    @Column(name = "pac_domicilio")
    private String pacDomicilio;
    @Column(name = "pac_correo")
    private String pacCorreo;
    @Column(name = "pac_telefono")
    private String pacTelefono;
    @Column(name = "pac_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date pacFechaNacimiento;
    @Column(name = "pac_sexo")
    private String pacSexo;
    @Column(name = "pac_estado_civil")
    private String pacEstadoCivil;
    @Column(name = "pac_tipo_sangre")
    private String pacTipoSangre;
    @Column(name = "pac_aseguradora")
    private String pacAseguradora;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<VisitaMedica> visitaMedicaCollection;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;

    public Paciente() {
    }

    public Paciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPacRuc() {
        return pacRuc;
    }

    public void setPacRuc(String pacRuc) {
        this.pacRuc = pacRuc;
    }

    public String getPacNombre() {
        return pacNombre;
    }

    public void setPacNombre(String pacNombre) {
        this.pacNombre = pacNombre;
    }

    public String getPacDomicilio() {
        return pacDomicilio;
    }

    public void setPacDomicilio(String pacDomicilio) {
        this.pacDomicilio = pacDomicilio;
    }

    public String getPacCorreo() {
        return pacCorreo;
    }

    public void setPacCorreo(String pacCorreo) {
        this.pacCorreo = pacCorreo;
    }

    public String getPacTelefono() {
        return pacTelefono;
    }

    public void setPacTelefono(String pacTelefono) {
        this.pacTelefono = pacTelefono;
    }

    public Date getPacFechaNacimiento() {
        return pacFechaNacimiento;
    }

    public void setPacFechaNacimiento(Date pacFechaNacimiento) {
        this.pacFechaNacimiento = pacFechaNacimiento;
    }

    public String getPacSexo() {
        return pacSexo;
    }

    public void setPacSexo(String pacSexo) {
        this.pacSexo = pacSexo;
    }

    public String getPacEstadoCivil() {
        return pacEstadoCivil;
    }

    public void setPacEstadoCivil(String pacEstadoCivil) {
        this.pacEstadoCivil = pacEstadoCivil;
    }

    public String getPacTipoSangre() {
        return pacTipoSangre;
    }

    public void setPacTipoSangre(String pacTipoSangre) {
        this.pacTipoSangre = pacTipoSangre;
    }

    public String getPacAseguradora() {
        return pacAseguradora;
    }

    public void setPacAseguradora(String pacAseguradora) {
        this.pacAseguradora = pacAseguradora;
    }

    public Collection<VisitaMedica> getVisitaMedicaCollection() {
        return visitaMedicaCollection;
    }

    public void setVisitaMedicaCollection(Collection<VisitaMedica> visitaMedicaCollection) {
        this.visitaMedicaCollection = visitaMedicaCollection;
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
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Paciente[ idPaciente=" + idPaciente + " ]";
    }
    
}
