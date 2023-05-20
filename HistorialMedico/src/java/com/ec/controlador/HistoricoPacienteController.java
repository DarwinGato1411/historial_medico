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
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioPaciente;

import com.ec.servicio.ServicioUsuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
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
public class HistoricoPacienteController {

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

    private Date fechaInicio = new Date();
    private Date fechafin = new Date();

    /*MOstrar pdf*/
    //reporte
    AMedia fileContent = null;
    Connection con = null;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);

    }

    public HistoricoPacienteController() {

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
    public void verHistorico(@BindingParam("valor") Paciente valor) {
        try {
            reporteGeneral(valor.getIdPaciente());
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    public void reporteGeneral(Integer idPaciente) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + File.separator + "historico.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("idPaciente", idPaciente);
            parametros.put("inicio", fechaInicio);
            parametros.put("fin", fechafin);

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/visor/visorreporte.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    private void buscarPaciente() {
        listaPaciente = servicioPaciente.finLike(buscarPaciente, credential.getUsuarioSistema(), Boolean.TRUE);
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

}
