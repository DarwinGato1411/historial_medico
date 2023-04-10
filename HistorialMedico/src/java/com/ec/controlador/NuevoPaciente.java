/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Capitulo;
import com.ec.entidad.Paciente;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCapitulo;
import com.ec.servicio.ServicioPaciente;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoPaciente {

    ServicioPaciente servicio = new ServicioPaciente();
    private Paciente entidad = new Paciente();
    UserCredential credential = new UserCredential();

    private String accion = "create";
    @Wire
    Window wCapitulo;

    public NuevoPaciente() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());

    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Paciente valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.entidad = valor;
            accion = "update";
        } else {
            this.entidad = new Paciente();
            accion = "create";
        }

    }

    @Command
    public void guardar() {
        if (entidad.getPacRuc() != null
                && entidad.getPacNombres() != null
                && entidad.getPacApellidos() != null
                && entidad.getPacTelefono() != null
                && entidad.getPacCorreo() != null) {

            if (accion.equals("create")) {
                servicio.crear(entidad);
                //  Messagebox.show("Guardado con exito");

                wCapitulo.detach();
            } else {
                servicio.modificar(entidad);
                // Messagebox.show("Guardado con exito");

                wCapitulo.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Paciente getEntidad() {
        return entidad;
    }

    public void setEntidad(Paciente entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
