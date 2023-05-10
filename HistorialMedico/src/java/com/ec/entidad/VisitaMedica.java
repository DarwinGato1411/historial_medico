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
@Table(name = "visita_medica")
@NamedQueries({
    @NamedQuery(name = "VisitaMedica.findAll", query = "SELECT v FROM VisitaMedica v")})
public class VisitaMedica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_visita_medica")
    private Integer idVisitaMedica;
    @Column(name = "vis_fecha")
    @Temporal(TemporalType.DATE)
    private Date visFecha;
    @Column(name = "vis_diagnostico")
    private String visDiagnostico;
    @Column(name = "vis_cargar_cie10")
    private String visCargarCie10;
    @Column(name = "vis_reseta")
    private String visReseta;
    @Column(name = "vis_observacion")
    private String visObservacion;
    @Column(name = "vis_estado")
    private Boolean visEstado;
    @Column(name = "vis_certificado")
    private String visCertificado;
    @OneToMany(mappedBy = "idVisitaMedica")
    private Collection<Receta> recetaCollection;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne
    private Paciente idPaciente;
    @OneToMany(mappedBy = "idVisitaMedica")
    private Collection<Examen> examenCollection;

    public VisitaMedica() {
    }

    public VisitaMedica(Integer idVisitaMedica) {
        this.idVisitaMedica = idVisitaMedica;
    }

    public Integer getIdVisitaMedica() {
        return idVisitaMedica;
    }

    public void setIdVisitaMedica(Integer idVisitaMedica) {
        this.idVisitaMedica = idVisitaMedica;
    }

    public Date getVisFecha() {
        return visFecha;
    }

    public void setVisFecha(Date visFecha) {
        this.visFecha = visFecha;
    }

    public String getVisDiagnostico() {
        return visDiagnostico;
    }

    public void setVisDiagnostico(String visDiagnostico) {
        this.visDiagnostico = visDiagnostico;
    }

    public String getVisCargarCie10() {
        return visCargarCie10;
    }

    public void setVisCargarCie10(String visCargarCie10) {
        this.visCargarCie10 = visCargarCie10;
    }

    public String getVisReseta() {
        return visReseta;
    }

    public void setVisReseta(String visReseta) {
        this.visReseta = visReseta;
    }

    public String getVisObservacion() {
        return visObservacion;
    }

    public void setVisObservacion(String visObservacion) {
        this.visObservacion = visObservacion;
    }

    public Collection<Receta> getRecetaCollection() {
        return recetaCollection;
    }

    public void setRecetaCollection(Collection<Receta> recetaCollection) {
        this.recetaCollection = recetaCollection;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Collection<Examen> getExamenCollection() {
        return examenCollection;
    }

    public void setExamenCollection(Collection<Examen> examenCollection) {
        this.examenCollection = examenCollection;
    }

    public Boolean getVisEstado() {
        return visEstado==null?Boolean.TRUE:visEstado;
    }

    public void setVisEstado(Boolean visEstado) {
        this.visEstado = visEstado;
    }

    public String getVisCertificado() {
        return visCertificado;
    }

    public void setVisCertificado(String visCertificado) {
        this.visCertificado = visCertificado;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisitaMedica != null ? idVisitaMedica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitaMedica)) {
            return false;
        }
        VisitaMedica other = (VisitaMedica) object;
        if ((this.idVisitaMedica == null && other.idVisitaMedica != null) || (this.idVisitaMedica != null && !this.idVisitaMedica.equals(other.idVisitaMedica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.VisitaMedica[ idVisitaMedica=" + idVisitaMedica + " ]";
    }
    
}
