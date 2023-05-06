/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Paciente;
import com.ec.entidad.Usuario;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioPaciente;

import com.ec.servicio.ServicioUsuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Darwin
 */
public class PacienteController {

    private Usuario usuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    UserCredential credential = new UserCredential();
    ServicioPaciente servicioPaciente = new ServicioPaciente();
    private List<Paciente> listaPaciente = new ArrayList<Paciente>();
    private String buscarPaciente = "";

    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);

    }

    public PacienteController() {

        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        buscarPaciente();
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void buscarLike() {

        buscarPaciente();
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void nuevoPaciente() {
        try {

            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/paciente.zul", null, null);
            window.doModal();
            buscarLike();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void eliminarPaciente(@BindingParam("valor") Paciente valor) {
        try {
            if (Messagebox.show("¿Esta seguro de eliminar el paciente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
                valor.setPacHabilitado(Boolean.FALSE);
                servicioPaciente.modificar(valor);
                buscarLike();
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void modificarPaciente(@BindingParam("valor") Paciente valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, Paciente> map = new HashMap<String, Paciente>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/paciente.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void registrarVisita(@BindingParam("valor") Paciente valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, Paciente> map = new HashMap<String, Paciente>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/visita_medica.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    private void buscarPaciente() {
        listaPaciente = servicioPaciente.finLike(buscarPaciente);
    }

    public List<Paciente> getListaPaciente() {
        return listaPaciente;
    }

    public void setListaPaciente(List<Paciente> listaPaciente) {
        this.listaPaciente = listaPaciente;
    }

    public String getBuscarPaciente() {
        return buscarPaciente;
    }

    public void setBuscarPaciente(String buscarPaciente) {
        this.buscarPaciente = buscarPaciente;
    }

}
