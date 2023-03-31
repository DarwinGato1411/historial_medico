/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Subcapitulo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioSubCapitulo;
import com.ec.utilitario.SubcapituloCapitulo;

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
public class NuevoSubCapitulo {

    ServicioSubCapitulo servicioSubCapitulo = new ServicioSubCapitulo();
    private Subcapitulo entidad = new Subcapitulo();
    UserCredential credential = new UserCredential();

    private String accion = "create";
    @Wire
    Window wPregunta;

    public NuevoSubCapitulo() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());

    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") SubcapituloCapitulo valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor.getSubcapitulo() != null) {
            this.entidad = valor.getSubcapitulo();
            entidad.setIdCapitulo(valor.getCapitulo());
            accion = "update";
        } else {
            this.entidad = new Subcapitulo();
            entidad.setIdCapitulo(valor.getCapitulo());
            accion = "create";

        }

    }

    @Command
    public void guardar() {
        if (entidad.getSubDetalle() != null) {

            if (accion.equals("create")) {
                servicioSubCapitulo.crear(entidad);
                //  Messagebox.show("Guardado con exito");

                wPregunta.detach();
            } else {
                servicioSubCapitulo.modificar(entidad);
                // Messagebox.show("Guardado con exito");

                wPregunta.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Subcapitulo getEntidad() {
        return entidad;
    }

    public void setEntidad(Subcapitulo entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
