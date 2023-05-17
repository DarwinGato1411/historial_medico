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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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
import org.zkoss.zul.Messagebox;

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
    private String fechaFomateada = "";
    private String DatosPersonales = "";
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

    private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Paciente valor, @ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);
        pacienteSelected = valor;
        
        setFechaFomateada(new SimpleDateFormat("dd/MM/yyyy").format(pacienteSelected.getPacFechaNacimiento()));
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
    @NotifyChange({"listaPaciente", "buscarPaciente", "listaVisitaMedicas"})
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
    @NotifyChange({"listaVisitaMedicas", "buscar"})
    public void modificarVisita(@BindingParam("valor") VisitaMedica valor) {
        try {

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
    @NotifyChange({"listaVisitaMedicas", "buscar"})
    public void eliminarVisita(@BindingParam("valor") VisitaMedica valor) {
        try {
            if (Messagebox.show("¿Desea eliminar la visita?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

                valor.setVisEstado(Boolean.FALSE);
                servicioVisitaMedica.modificar(valor);
                buscarVisita();
            }
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
            mailerClass.sendMailSimple((valor.getIdPaciente().getPacCorreo() == null ? "consultoriospichincha@gmail.com"
                    : valor.getIdPaciente().getPacCorreo()), "Saludos, estimado paciente envio su receta medica", pathReceta,
                    "RECETA MEDICA");
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
        listaVisitaMedicas = servicioVisitaMedica.findForPaciente(pacienteSelected, buscar, Boolean.TRUE);
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
        reporteGeneral(valor, "REC");
    }

    @Command
    public void verCertificado(@BindingParam("valor") VisitaMedica valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor, "CER");
    }

    public void reporteGeneral(VisitaMedica valor, String tipo) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            if (tipo.equals("REC")) {
                reportPath = reportFile + File.separator + "receta.jasper";
            } else {
                reportPath = reportFile + File.separator + "certificado.jasper";
            }

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("IdVisitaMedica", valor.getIdVisitaMedica());
            parametros.put("logo", parametrizar.getParBase() + File.separator + parametrizar.getParImagenes() + File.separator + "logohm.png");
            Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
            calendar.add(Calendar.HOUR, valor.getVisReposo() == null ? 0 : valor.getVisReposo()); //el -3 indica que se le restaran 3 dias 
            Date fechaFinal = calendar.getTime();

            Calendar c = Calendar.getInstance();
            c.setTime(valor.getVisFecha());
            Integer dia = c.get(Calendar.DATE);
            Integer mes = c.get(Calendar.MONTH) + 1;
            Integer annio = c.get(Calendar.YEAR);
            Calendar cc = Calendar.getInstance();
            cc.setTime(fechaFinal);

            Integer diaF = cc.get(Calendar.DATE);
            Integer mesF = cc.get(Calendar.MONTH) + 1;
            Integer annioF = cc.get(Calendar.YEAR);

            String textoFechaInicio = (Convertir(String.valueOf(dia), Boolean.TRUE) + "de " + mesLetras(mes) + " del " + Convertir(String.valueOf(annio), Boolean.TRUE));
            String textoFechaFinal = (Convertir(String.valueOf(diaF), Boolean.TRUE) + "de " + mesLetras(mesF) + " del " + Convertir(String.valueOf(annioF), Boolean.TRUE));
//                System.out.println("FECHA EN TEXTO "+calendar.);
            System.out.println("FECHA INICIO " + dia + "   " + mes + "    " + annio + " " + textoFechaInicio.toLowerCase());
            System.out.println("FECHA FIN " + fechaFinal + "  " + textoFechaFinal.toLowerCase());
            parametros.put("fechaFinal", fechaFinal);
            parametros.put("fechaInicioText", textoFechaInicio.toLowerCase());
            parametros.put("fechaFinText", textoFechaFinal.toLowerCase());

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

    public Paciente getPacienteSelected() {
        return pacienteSelected;
    }

    public void setPacienteSelected(Paciente pacienteSelected) {
        this.pacienteSelected = pacienteSelected;
    }

    public String Convertir(String numero, boolean mayusculas) {
        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
//            parte_decimal = "y " + Num[1] ;
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal).toLowerCase();
            } else {
                return (literal.toLowerCase());
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }

    /* funciones para convertir los numeros a literales */
    private String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private String getDecenas(String num) {// 99                        
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }

    private String getCentenas(String num) {// 999 o 099
        if (Integer.parseInt(num) > 99) {//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else {//por Ej. 099 
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }

    private String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }

    }

    private String getMillones(String numero) { //000 000 000        
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (millon.length() > 1) {
            n = getCentenas(millon) + "millones ";
        } else {
            n = getUnidades(millon) + "millon ";
        }
        return n + getMiles(miles);
    }

    private String mesLetras(Integer mes) {

        String mesString;
        switch (mes) {
            case 1:
                mesString = "Enero";
                break;
            case 2:
                mesString = "Febrero";
                break;
            case 3:
                mesString = "Marzo";
                break;
            case 4:
                mesString = "Abril";
                break;
            case 5:
                mesString = "Mayo";
                break;
            case 6:
                mesString = "Junio";
                break;
            case 7:
                mesString = "Julio";
                break;
            case 8:
                mesString = "Agosto";
                break;
            case 9:
                mesString = "Septiembre";
                break;
            case 10:
                mesString = "Octubre";
                break;
            case 11:
                mesString = "Noviembre";
                break;
            case 12:
                mesString = "Diciembre";
                break;
            default:
                mesString = "Invalid month";
                break;
        }
        System.out.println(mesString);
        return mesString;
    }

    public String getFechaFomateada() {
        return fechaFomateada;
    }

    public void setFechaFomateada(String fechaFomateada) {
        this.fechaFomateada = fechaFomateada;
    }

    public String getDatosPersonales() {
        return DatosPersonales;
    }

    public void setDatosPersonales(String DatosPersonales) {
        this.DatosPersonales = DatosPersonales;
    }

}
