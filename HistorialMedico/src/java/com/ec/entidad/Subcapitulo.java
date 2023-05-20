/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "subcapitulo")
@NamedQueries({
    @NamedQuery(name = "Subcapitulo.findAll", query = "SELECT s FROM Subcapitulo s")})
public class Subcapitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subcapitulo")
    private Integer idSubcapitulo;
    @Column(name = "sub_detalle")
    private String subDetalle;
    @Column(name = "ciu_estado")
    private Boolean ciuEstado;
    @Column(name = "sub_codigo")
    private String subCodigo;
    @JoinColumn(name = "id_capitulo", referencedColumnName = "id_capitulo")
    @ManyToOne
    private Capitulo idCapitulo;
    @OneToMany(mappedBy = "idSubcapitulo")
    private Collection<Detalle> detalleCollection;

    public Subcapitulo() {
    }

    public Subcapitulo(Integer idSubcapitulo) {
        this.idSubcapitulo = idSubcapitulo;
    }

    public Integer getIdSubcapitulo() {
        return idSubcapitulo;
    }

    public void setIdSubcapitulo(Integer idSubcapitulo) {
        this.idSubcapitulo = idSubcapitulo;
    }

    public String getSubDetalle() {
        return subDetalle;
    }

    public void setSubDetalle(String subDetalle) {
        this.subDetalle = subDetalle;
    }

    public Boolean getCiuEstado() {
        return ciuEstado;
    }

    public void setCiuEstado(Boolean ciuEstado) {
        this.ciuEstado = ciuEstado;
    }

    public String getSubCodigo() {
        return subCodigo;
    }

    public void setSubCodigo(String subCodigo) {
        this.subCodigo = subCodigo;
    }

    public Capitulo getIdCapitulo() {
        return idCapitulo;
    }

    public void setIdCapitulo(Capitulo idCapitulo) {
        this.idCapitulo = idCapitulo;
    }

    public Collection<Detalle> getDetalleCollection() {
        return detalleCollection;
    }

    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
        this.detalleCollection = detalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubcapitulo != null ? idSubcapitulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subcapitulo)) {
            return false;
        }
        Subcapitulo other = (Subcapitulo) object;
        if ((this.idSubcapitulo == null && other.idSubcapitulo != null) || (this.idSubcapitulo != null && !this.idSubcapitulo.equals(other.idSubcapitulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Subcapitulo[ idSubcapitulo=" + idSubcapitulo + " ]";
    }
    
}
