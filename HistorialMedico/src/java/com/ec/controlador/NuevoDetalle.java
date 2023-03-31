/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Detalle;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioDetalle;
import com.ec.utilitario.DetalleSubcapitulo;

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
public class NuevoDetalle {

    ServicioDetalle servicioDetalle = new ServicioDetalle();
    private Detalle entidad = new Detalle();
    UserCredential credential = new UserCredential();

    private String accion = "create";
    @Wire
    Window wPregunta;

    public NuevoDetalle() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());

    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") DetalleSubcapitulo valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor.getDetalle() != null) {
            this.entidad = valor.getDetalle();
            entidad.setIdSubcapitulo(valor.getSubcapitulo());
            accion = "update";
        } else {
            this.entidad = new Detalle();
            entidad.setIdSubcapitulo(valor.getSubcapitulo());

            accion = "create";

        }

    }

    @Command
    public void guardar() {
        if (entidad.getDetDetalle() != null) {

            if (accion.equals("create")) {
                servicioDetalle.crear(entidad);
                //  Messagebox.show("Guardado con exito");

                wPregunta.detach();
            } else {
                servicioDetalle.modificar(entidad);
                // Messagebox.show("Guardado con exito");

                wPregunta.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Detalle getEntidad() {
        return entidad;
    }

    public void setEntidad(Detalle entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
