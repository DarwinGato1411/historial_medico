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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "capitulo")
@NamedQueries({
    @NamedQuery(name = "Capitulo.findAll", query = "SELECT c FROM Capitulo c")})
public class Capitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_capitulo")
    private Integer idCapitulo;
    @Column(name = "cap_codigo")
    private Integer capCodigo;
    @Column(name = "cap_detalle")
    private String capDetalle;
    @OneToMany(mappedBy = "idCapitulo")
    private Collection<Subcapitulo> subcapituloCollection;

    public Capitulo() {
    }

    public Capitulo(Integer idCapitulo) {
        this.idCapitulo = idCapitulo;
    }

    public Integer getIdCapitulo() {
        return idCapitulo;
    }

    public void setIdCapitulo(Integer idCapitulo) {
        this.idCapitulo = idCapitulo;
    }

    public Integer getCapCodigo() {
        return capCodigo;
    }

    public void setCapCodigo(Integer capCodigo) {
        this.capCodigo = capCodigo;
    }

    public String getCapDetalle() {
        return capDetalle;
    }

    public void setCapDetalle(String capDetalle) {
        this.capDetalle = capDetalle;
    }

    public Collection<Subcapitulo> getSubcapituloCollection() {
        return subcapituloCollection;
    }

    public void setSubcapituloCollection(Collection<Subcapitulo> subcapituloCollection) {
        this.subcapituloCollection = subcapituloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapitulo != null ? idCapitulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capitulo)) {
            return false;
        }
        Capitulo other = (Capitulo) object;
        if ((this.idCapitulo == null && other.idCapitulo != null) || (this.idCapitulo != null && !this.idCapitulo.equals(other.idCapitulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Capitulo[ idCapitulo=" + idCapitulo + " ]";
    }
    
}
