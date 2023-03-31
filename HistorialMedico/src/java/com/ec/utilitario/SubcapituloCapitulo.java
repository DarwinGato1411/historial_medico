/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.utilitario;

import com.ec.entidad.Capitulo;
import com.ec.entidad.Subcapitulo;

/**
 *
 * @author Darwin
 */
public class SubcapituloCapitulo {

    private Capitulo capitulo;
    private Subcapitulo subcapitulo;

    public SubcapituloCapitulo(Capitulo capitulo, Subcapitulo subcapitulo) {
        this.capitulo = capitulo;
        this.subcapitulo = subcapitulo;
    }

    public Capitulo getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    public Subcapitulo getSubcapitulo() {
        return subcapitulo;
    }

    public void setSubcapitulo(Subcapitulo subcapitulo) {
        this.subcapitulo = subcapitulo;
    }

}
