/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.controlador.doa.NuevaVisitaParam;
import com.ec.entidad.Paciente;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Receta;
import com.ec.entidad.Usuario;
import com.ec.entidad.VisitaMedica;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioReceta;

import com.ec.servicio.ServicioUsuario;
import com.ec.servicio.ServicioVisitaMedica;
import com.ec.utilitario.ArchivoUtils;
import com.ec.utilitario.MailerClass;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Darwin
 */
public class VisitaController {

    private Usuario usuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    UserCredential credential = new UserCredential();
    ServicioVisitaMedica servicioVisitaMedica = new ServicioVisitaMedica();
    private List<VisitaMedica> listaVisitaMedicas = new ArrayList<VisitaMedica>();
    private Paciente pacienteSelected = new Paciente();
    private String buscar = "";

    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;

    /*Envio de correo*/
    ServicioReceta servicioReceta = new ServicioReceta();

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = new Parametrizar();

    /*MOstrar pdf*/
    //reporte
    AMedia fileContent = null;
    Connection con = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Paciente valor, @ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);
        pacienteSelected = valor;
        buscarVisita();
    }

    public VisitaController() {

        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        parametrizar = servicioParametrizar.findActivo();
    }

    @Command
    @NotifyChange({"listaVisitaMedicas", "buscar"})
    public void buscarLike() {

        buscarVisita();
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente","listaVisitaMedicas"})
    public void nuevaVisita() {
        try {
            final HashMap<String, NuevaVisitaParam> map = new HashMap<String, NuevaVisitaParam>();
            NuevaVisitaParam param = new NuevaVisitaParam("nuevo", null);
            param.setIdPaciente(pacienteSelected);
            map.put("valor", param);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/visita.zul", null, map);

            window.doModal();
            buscarVisita();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void modificarVisita(@BindingParam("valor") VisitaMedica valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, NuevaVisitaParam> map = new HashMap<String, NuevaVisitaParam>();
            NuevaVisitaParam param = new NuevaVisitaParam("modifica", valor);
            param.setIdPaciente(pacienteSelected);
            map.put("valor", param);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/visita.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void enviarReceta(@BindingParam("valor") VisitaMedica valor) throws UnsupportedEncodingException, JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        try {
            String pathEnvio = parametrizar.getParBase() + File.separator + parametrizar.getParImagenes() + File.separator + "Receta_" + valor.getIdVisitaMedica() + ".pdf";
            ArchivoUtils.reporteGeneralPdfMail(pathEnvio, valor.getIdVisitaMedica());
            MailerClass mailerClass = new MailerClass();
            String pathReceta[] = new String[1];
            pathReceta[0] = pathEnvio;
            mailerClass.sendMailSimple("darwinvinicio14_11@hotmail.com", "Saludos, estimado paciente envio su receta medica", pathReceta, "RECETA MEDICA");
        } catch (RemoteException e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void enviarWhastApp(@BindingParam("valor") VisitaMedica valor) throws UnsupportedEncodingException {
        try {

            List<Receta> receta = servicioReceta.findForVisitaMedica(valor);

            String recetaText = "";

            for (Receta receta1 : receta) {

                recetaText = recetaText + "MEDICAMENTO:" + receta1.getRecMedicamento() + "%20%20INDICACION:" + receta1.getRecDescripcion() + "%20%20";
            }
//            https://api.whatsapp.com/send?phone=993530018&text=Medicamente:%20Adorlan%20forte;%20Indicacion:%20Tomar%20una%20cada%208%20horas
            String contacto = valor.getIdPaciente() != null ? valor.getIdPaciente().getPacMovil() : "0993530018";
            String enviarSMS = "https://api.whatsapp.com/send?phone=593" + contacto.substring(1, contacto.length()) + "&text=" + recetaText;
            System.out.println("enviarSMS " + enviarSMS.replace(" ", "%20"));
//        Executions.sendRedirect("enviarSMS");
            Executions.getCurrent().sendRedirect(enviarSMS.replace(" ", "%20"), "_blank");
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    private void buscarVisita() {
        listaVisitaMedicas = servicioVisitaMedica.findForPaciente(pacienteSelected, buscar);
    }

    public List<VisitaMedica> getListaVisitaMedicas() {
        return listaVisitaMedicas;
    }

    public void setListaVisitaMedicas(List<VisitaMedica> listaVisitaMedicas) {
        this.listaVisitaMedicas = listaVisitaMedicas;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    @Command
    public void verReceta(@BindingParam("valor") VisitaMedica valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getIdVisitaMedica());
    }

    public void reporteGeneral(Integer idVisitaMedica) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + File.separator + "receta.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("IdVisitaMedica", idVisitaMedica);

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

}
