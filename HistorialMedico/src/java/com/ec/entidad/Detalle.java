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
@Table(name = "detalle")
@NamedQueries({
    @NamedQuery(name = "Detalle.findAll", query = "SELECT d FROM Detalle d")})
public class Detalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "det_detalle")
    private String detDetalle;
    @Column(name = "det_codigo")
    private String detCodigo;
    @Column(name = "can_estado")
    private Boolean canEstado;
    @JoinColumn(name = "id_subcapitulo", referencedColumnName = "id_subcapitulo")
    @ManyToOne
    private Subcapitulo idSubcapitulo;

    public Detalle() {
    }

    public Detalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getDetDetalle() {
        return detDetalle;
    }

    public void setDetDetalle(String detDetalle) {
        this.detDetalle = detDetalle;
    }

    public Boolean getCanEstado() {
        return canEstado;
    }

    public void setCanEstado(Boolean canEstado) {
        this.canEstado = canEstado;
    }

    public Subcapitulo getIdSubcapitulo() {
        return idSubcapitulo;
    }

    public void setIdSubcapitulo(Subcapitulo idSubcapitulo) {
        this.idSubcapitulo = idSubcapitulo;
    }

    public String getDetCodigo() {
        return detCodigo;
    }

    public void setDetCodigo(String detCodigo) {
        this.detCodigo = detCodigo;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalle)) {
            return false;
        }
        Detalle other = (Detalle) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Detalle[ idDetalle=" + idDetalle + " ]";
    }
    
}
