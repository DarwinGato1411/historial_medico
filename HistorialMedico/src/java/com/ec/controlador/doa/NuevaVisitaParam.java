/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.doa;

import com.ec.entidad.Paciente;
import com.ec.entidad.VisitaMedica;

/**
 *
 * @author Darwin
 */
public class NuevaVisitaParam {

    private String tipo;
    private VisitaMedica visita;
    private Paciente idPaciente;

    public NuevaVisitaParam() {
    }

    public NuevaVisitaParam(String tipo, VisitaMedica visita) {
        this.tipo = tipo;
        this.visita = visita;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public VisitaMedica getVisita() {
        return visita;
    }

    public void setVisita(VisitaMedica visita) {
        this.visita = visita;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    

}
