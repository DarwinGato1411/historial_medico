/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @Column(name = "pac_nombres")
    private String pacNombres;
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
    @Column(name = "pac_apellidos")
    private String pacApellidos;
    @Column(name = "pac_edad")
    private Integer pacEdad;
    @Column(name = "pac_movil")
    private String pacMovil;
    @Column(name = "pac_alegias")
    private String pacAlegias;
    @Column(name = "pac_cirugias")
    private String pacCirugias;
    @Column(name = "pac_enfermedad_existente")
    private String pacEnfermedadExistente;
    @Column(name = "pac_enfermedad_familia")
    private String pacEnfermedadFamilia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pac_talla")
    private BigDecimal pacTalla;
    @Column(name = "pac_peso")
    private BigDecimal pacPeso;
    @Column(name = "pac_imc")
    private BigDecimal pacImc;
    @Column(name = "pac_tm_bucal")
    private String pacTmBucal;
    @Column(name = "pac_cuello")
    private String pacCuello;
    @Column(name = "pac_cintura")
    private String pacCintura;
    @Column(name = "pac_per_abdominal")
    private String pacPerAbdominal;
    @Column(name = "pac_pa")
    private String pacPa;
    @Column(name = "pac_fc")
    private String pacFc;
    @Column(name = "pac_sp02")
    private String pacSp02;
    @Column(name = "pac_fotografia")
    private String pacFotografia;
    @Column(name = "pac_ref_familiar")
    private String pacRefFamiliar;
    @Column(name = "pac_telefono_referencia")
    private String pacTelefonoReferencia;
    @Column(name = "pac_medicacion_actual")
    private String pacMedicacionActual;
    @Column(name = "pac_habilitado")
    private Boolean pacHabilitado;
    @Column(name = "pac_estado")
    private Boolean pacEstado;
    @Column(name = "pac_direccion_trabajo")
    private String pacDireccionTrabajo;
    @Column(name = "pac_cargo")
    private String pacCargo;
    @Column(name = "pac_otros")
    private String pacOtros;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<Agendamiento> agendamientoCollection;
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

    public String getPacNombres() {
        return pacNombres;
    }

    public void setPacNombres(String pacNombres) {
        this.pacNombres = pacNombres;
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

    public String getPacApellidos() {
        return pacApellidos;
    }

    public void setPacApellidos(String pacApellidos) {
        this.pacApellidos = pacApellidos;
    }

    public Integer getPacEdad() {
        return pacEdad;
    }

    public void setPacEdad(Integer pacEdad) {
        this.pacEdad = pacEdad;
    }

    public String getPacMovil() {
        return pacMovil;
    }

    public void setPacMovil(String pacMovil) {
        this.pacMovil = pacMovil;
    }

    public String getPacAlegias() {
        return pacAlegias;
    }

    public void setPacAlegias(String pacAlegias) {
        this.pacAlegias = pacAlegias;
    }

    public String getPacCirugias() {
        return pacCirugias;
    }

    public void setPacCirugias(String pacCirugias) {
        this.pacCirugias = pacCirugias;
    }

    public String getPacEnfermedadExistente() {
        return pacEnfermedadExistente;
    }

    public void setPacEnfermedadExistente(String pacEnfermedadExistente) {
        this.pacEnfermedadExistente = pacEnfermedadExistente;
    }

    public String getPacEnfermedadFamilia() {
        return pacEnfermedadFamilia;
    }

    public void setPacEnfermedadFamilia(String pacEnfermedadFamilia) {
        this.pacEnfermedadFamilia = pacEnfermedadFamilia;
    }

    public BigDecimal getPacTalla() {
        return pacTalla;
    }

    public void setPacTalla(BigDecimal pacTalla) {
        this.pacTalla = pacTalla;
    }

    public BigDecimal getPacPeso() {
        return pacPeso;
    }

    public void setPacPeso(BigDecimal pacPeso) {
        this.pacPeso = pacPeso;
    }

    public BigDecimal getPacImc() {
        return pacImc;
    }

    public void setPacImc(BigDecimal pacImc) {
        this.pacImc = pacImc;
    }

    public String getPacTmBucal() {
        return pacTmBucal;
    }

    public void setPacTmBucal(String pacTmBucal) {
        this.pacTmBucal = pacTmBucal;
    }

    public String getPacCuello() {
        return pacCuello;
    }

    public void setPacCuello(String pacCuello) {
        this.pacCuello = pacCuello;
    }

    public String getPacCintura() {
        return pacCintura;
    }

    public void setPacCintura(String pacCintura) {
        this.pacCintura = pacCintura;
    }

    public String getPacPerAbdominal() {
        return pacPerAbdominal;
    }

    public void setPacPerAbdominal(String pacPerAbdominal) {
        this.pacPerAbdominal = pacPerAbdominal;
    }

    public String getPacPa() {
        return pacPa;
    }

    public void setPacPa(String pacPa) {
        this.pacPa = pacPa;
    }

    public String getPacFc() {
        return pacFc;
    }

    public void setPacFc(String pacFc) {
        this.pacFc = pacFc;
    }

    public String getPacSp02() {
        return pacSp02;
    }

    public void setPacSp02(String pacSp02) {
        this.pacSp02 = pacSp02;
    }

    public String getPacFotografia() {
        return pacFotografia;
    }

    public void setPacFotografia(String pacFotografia) {
        this.pacFotografia = pacFotografia;
    }

    public String getPacRefFamiliar() {
        return pacRefFamiliar;
    }

    public void setPacRefFamiliar(String pacRefFamiliar) {
        this.pacRefFamiliar = pacRefFamiliar;
    }

    public String getPacTelefonoReferencia() {
        return pacTelefonoReferencia;
    }

    public void setPacTelefonoReferencia(String pacTelefonoReferencia) {
        this.pacTelefonoReferencia = pacTelefonoReferencia;
    }

    public String getPacMedicacionActual() {
        return pacMedicacionActual;
    }

    public void setPacMedicacionActual(String pacMedicacionActual) {
        this.pacMedicacionActual = pacMedicacionActual;
    }

    public Boolean getPacHabilitado() {
        return pacHabilitado;
    }

    public void setPacHabilitado(Boolean pacHabilitado) {
        this.pacHabilitado = pacHabilitado;
    }

    public Boolean getPacEstado() {
        return pacEstado;
    }

    public void setPacEstado(Boolean pacEstado) {
        this.pacEstado = pacEstado;
    }

    public String getPacDireccionTrabajo() {
        return pacDireccionTrabajo;
    }

    public void setPacDireccionTrabajo(String pacDireccionTrabajo) {
        this.pacDireccionTrabajo = pacDireccionTrabajo;
    }

    public String getPacCargo() {
        return pacCargo;
    }

    public void setPacCargo(String pacCargo) {
        this.pacCargo = pacCargo;
    }

    public String getPacOtros() {
        return pacOtros;
    }

    public void setPacOtros(String pacOtros) {
        this.pacOtros = pacOtros;
    }

    public Collection<Agendamiento> getAgendamientoCollection() {
        return agendamientoCollection;
    }

    public void setAgendamientoCollection(Collection<Agendamiento> agendamientoCollection) {
        this.agendamientoCollection = agendamientoCollection;
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
