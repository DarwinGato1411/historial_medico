/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.doa;

/**
 *
 * @author Darwin
 */
public class RecetaDao {
    private String medicamento;
    private String indicacion;

    public RecetaDao() {
    }

    public RecetaDao(String medicamento, String indicacion) {
        this.medicamento = medicamento;
        this.indicacion = indicacion;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getIndicacion() {
        return indicacion;
    }

    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }
    
    
}
