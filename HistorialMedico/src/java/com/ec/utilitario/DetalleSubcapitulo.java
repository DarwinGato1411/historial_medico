/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.utilitario;

import com.ec.entidad.Detalle;
import com.ec.entidad.Subcapitulo;

/**
 *
 * @author Darwin
 */
public class DetalleSubcapitulo {
    private Subcapitulo subcapitulo;
    private Detalle detalle;

    public DetalleSubcapitulo(Subcapitulo subcapitulo, Detalle detalle) {
        this.subcapitulo = subcapitulo;
        this.detalle = detalle;
    }

    public Subcapitulo getSubcapitulo() {
        return subcapitulo;
    }

    public void setSubcapitulo(Subcapitulo subcapitulo) {
        this.subcapitulo = subcapitulo;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }
    
}
