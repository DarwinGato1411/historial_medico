/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Capitulo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCapitulo;

import org.zkforge.json.simple.Test;
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
public class NuevoCapitulo {

    ServicioCapitulo servicioCapitulo = new ServicioCapitulo();
    private Capitulo entidad = new Capitulo();
    UserCredential credential = new UserCredential();

    private String accion = "create";
    @Wire
    Window wCapitulo;

    public NuevoCapitulo() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());

    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Capitulo valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.entidad = valor;
            accion = "update";
        } else {
            this.entidad = new Capitulo();
            accion = "create";

        }

    }

    @Command
    public void guardar() {
        if (entidad.getCapDetalle() != null) {

            if (accion.equals("create")) {
                servicioCapitulo.crear(entidad);
                //  Messagebox.show("Guardado con exito");

                wCapitulo.detach();
            } else {
                servicioCapitulo.modificar(entidad);
                // Messagebox.show("Guardado con exito");

                wCapitulo.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Capitulo getEntidad() {
        return entidad;
    }

    public void setEntidad(Capitulo entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
