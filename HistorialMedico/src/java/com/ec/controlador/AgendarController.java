/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Agendamiento;
import com.ec.entidad.Paciente;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Usuario;
import com.ec.entidad.VisitaMedica;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioAgendamiento;
import com.ec.servicio.ServicioGeneral;
import com.ec.servicio.ServicioPaciente;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioUsuario;
import com.ec.servicio.ServicioVisitaMedicas;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Darwin
 */
public class AgendarController {

    ServicioGeneral servicioGeneral = new ServicioGeneral();
    private Usuario usuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();

    UserCredential credential = new UserCredential();

    //subir pdf
    private String filePath;
    private AImage fotoGeneral = null;
    private AImage fotoLogo = null;

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = new Parametrizar();

    private Date fechaAgendar = new Date();
    @Wire
    Calendar idCalendar;

    /*Agendamiento*/
    private List<Agendamiento> listaAgenda = new ArrayList<>();
    ServicioAgendamiento servicioAgendamiento = new ServicioAgendamiento();

    /*PACIENTE */
    @Wire
    Window wPacienteAgendar;
    ServicioPaciente servicioPaciente = new ServicioPaciente();
    private List<Paciente> listaPaciente = new ArrayList<Paciente>();
    private String buscarPaciente = "";
    public static Paciente pacienteSelected;

    /*VISITA medica*/
    ServicioVisitaMedicas servicioVisitaMedicas = new ServicioVisitaMedicas();

    public AgendarController() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        usuario = credential.getUsuarioSistema();
        parametrizar = servicioParametrizar.findActivo();
    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String recibido, @ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);

        if (recibido != null) {
            buscarPaciente();
        }

    }

    private void consultaAgenda() {

        listaAgenda = servicioAgendamiento.findByFechaRegistradoUsuario(fechaAgendar, Boolean.TRUE, usuario);
    }

    @Command
    @NotifyChange({"listaAgenda", "fechaAgendar"})
    public void agendar() {

        try {
            
        java.util.Calendar calendario = new GregorianCalendar();
        calendario.get(java.util.Calendar.DAY_OF_WEEK);
//        servicioUsuario.modificar(usuario);
        System.out.println("DIA DE LA SEMANA " + calendario.get(java.util.Calendar.DAY_OF_WEEK));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaAgendar);
        System.out.println("DIA DE LA SEMANA ACTUAL " + calendario.get(java.util.Calendar.DAY_OF_WEEK) + "DIA DE LA SEMANA seleccion " + calendar.get(Calendar.DAY_OF_WEEK));

        String mensaje = servicioGeneral.generarAgenda(usuario.getIdUsuario(), calendar.get(java.util.Calendar.DAY_OF_WEEK), fechaAgendar);
        consultaAgenda();

        } catch (Exception e) {
             Clients.showNotification("No se pudo generar la agenda  ",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }
//        if (mensaje.contains("CORRECTAMENTE")) {
//            Clients.showNotification(mensaje,
//                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 1000, true);
//        }
//
//        Clients.showNotification("Agendado correctamente... " + ArchivoUtils.formatoFecha(fechaAgendar) + "Seleccion el dia " + ArchivoUtils.diaSemanaLetras(calendar.get(Calendar.DAY_OF_WEEK)),
//                    Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaAgendar() {
        return fechaAgendar;
    }

    public void setFechaAgendar(Date fechaAgendar) {
        this.fechaAgendar = fechaAgendar;
    }

    public List<Agendamiento> getListaAgenda() {
        return listaAgenda;
    }

    public void setListaAgenda(List<Agendamiento> listaAgenda) {
        this.listaAgenda = listaAgenda;
    }

    /*paciente*/
    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void buscarLike() {

        buscarPaciente();
    }

    private void buscarPaciente() {
        listaPaciente = servicioPaciente.finLike(buscarPaciente, credential.getUsuarioSistema(), Boolean.TRUE);
    }

    @Command
    @NotifyChange({"listaAgenda", "buscarPaciente"})
    public void eliminarAgendamiento(@BindingParam("valor") Agendamiento valor) {
        if (Messagebox.show("¿Desea cancelar el agendamiento?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            List<VisitaMedica> lista = servicioVisitaMedicas.findForPacienteHoraUsuario(valor.getIdPaciente(), valor.getAgeFecha(), valor.getAgeHoraInicio(), usuario);
            if (!lista.isEmpty()) {

                servicioVisitaMedicas.eliminar(lista.get(0));
            }
            valor.setIdPaciente(null);
            servicioAgendamiento.modificar(valor);
            consultaAgenda();
        }
    }

    @Command
    @NotifyChange({"listaAgenda", "buscarPaciente"})
    public void modelPaciente(@BindingParam("valor") Agendamiento valor) {
        try {
            final HashMap<String, String> map = new HashMap<String, String>();

            map.put("valor", "buscar");
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/paciente_agendar.zul", null, map);
            window.doModal();
            valor.setIdPaciente(pacienteSelected);
            servicioAgendamiento.modificar(valor);
            if (servicioVisitaMedicas.findForPacienteHoraUsuario(pacienteSelected, valor.getAgeFecha(), valor.getAgeHoraInicio(), usuario).isEmpty()) {
                VisitaMedica medica = new VisitaMedica();
                medica.setIdPaciente(pacienteSelected);
                medica.setVisHora(valor.getAgeHoraInicio());
                medica.setVisHoraFin(valor.getAgeHoraFin());
                medica.setVisFecha(valor.getAgeFecha());
                medica.setVisEstado(Boolean.TRUE);
                medica.setVisObservacion("AGENDADO");
                servicioVisitaMedicas.crear(medica);
            }
            consultaAgenda();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 1000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void seleccionarPaciente(@BindingParam("valor") Paciente valor) {
        try {
            pacienteSelected = valor;

            wPacienteAgendar.detach();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
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

    public static Paciente getPacienteSelected() {
        return pacienteSelected;
    }

    public static void setPacienteSelected(Paciente pacienteSelected) {
        AgendarController.pacienteSelected = pacienteSelected;
    }

}
