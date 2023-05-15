/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.controlador.doa.ExamenDao;
import com.ec.controlador.doa.NuevaVisitaParam;
import com.ec.controlador.doa.RecetaDao;
import com.ec.entidad.Capitulo;
import com.ec.entidad.Detalle;
import com.ec.entidad.Examen;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Receta;
import com.ec.entidad.Subcapitulo;
import com.ec.entidad.VisitaMedica;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCapitulo;
import com.ec.servicio.ServicioDetalle;
import com.ec.servicio.ServicioExamen;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioReceta;
import com.ec.servicio.ServicioSubCapitulo;
import com.ec.servicio.ServicioVisitaMedica;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoVisita {

    ServicioVisitaMedica servicioVisitaMedica = new ServicioVisitaMedica();
    private VisitaMedica entidad = new VisitaMedica();
    UserCredential credential = new UserCredential();

    private String accion = "create";
    @Wire
    Window wVisita;
    @Wire
    Window wVCargarCie;
    /*Gestionar las recetas*/
    ServicioReceta servicioReceta = new ServicioReceta();
    private List<Receta> listaRecetas = new ArrayList<Receta>();
    private ListModelList<RecetaDao> listaRecetaModel;
//    private List<DetalleFacturaDAO> listaDetalleFacturaDAODatos = new ArrayList<DetalleFacturaDAO>();
    private Set<Receta> registroSelectedReceta = new HashSet<Receta>();

    /*Gestionar las recetas*/
    ServicioExamen servicioExamen = new ServicioExamen();
    private ListModelList<ExamenDao> listaExamenModel;
//    private List<DetalleFacturaDAO> listaDetalleFacturaDAODatos = new ArrayList<DetalleFacturaDAO>();
    private Set<ExamenDao> registroSelectedExamen = new HashSet<ExamenDao>();

    //subir imagen
    private String filePath;
    byte[] buffer = new byte[2024 * 1024];
    private AImage fotoGeneral = null;

    AMedia fileContent = null;

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = new Parametrizar();

    /*CARGAR CIE 10*/
    ServicioCapitulo servicioCapitulo = new ServicioCapitulo();
    ServicioSubCapitulo servicioSubCapitulo = new ServicioSubCapitulo();
    ServicioDetalle servicioDetalle = new ServicioDetalle();
    private List<Capitulo> listaCapitulo = new ArrayList<Capitulo>();
    private Capitulo capituloSelected = null;
    private Subcapitulo subCapituloSelected = null;
    private List<Subcapitulo> listaSubcapitulos = new ArrayList<Subcapitulo>();
    private List<Detalle> listaDetalles = new ArrayList<Detalle>();
    public static String CIE10 = "";

    private String buscarCapitulo = "";
    private String buscarSubCapitulo = "";
    private String buscarDetalle = "";


    /*MOstrar pdf*/
    //reporte
//    AMedia fileContent = null;
    Connection con = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") NuevaVisitaParam valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (valor.getTipo().equals("cie")) {
            buscarDetalleBD();
        } else {
            if (valor.getTipo().equals("modifica")) {
                this.entidad = valor.getVisita();

                accion = "update";

                if (listaRecetaModel == null) {
                    listaRecetaModel = new ListModelList();
                }
                if (listaExamenModel == null) {
                    listaExamenModel = new ListModelList();
                }
                getRecetaExamen();
            } else {
                listaRecetaModel = new ListModelList();
                listaExamenModel = new ListModelList();
                this.entidad = new VisitaMedica();
                this.entidad.setIdPaciente(valor.getIdPaciente());
                this.entidad.setVisFecha(new Date());
                this.entidad.setVisEstado(Boolean.TRUE);
                this.entidad.setVisCertificado("POR MEDIO DE LA PRESENTE CERTIFICO QUE EL PACIENTE ACUDE A CONSULTA ");
                accion = "create";

            }
        }

    }

    public NuevoVisita() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        parametrizar = servicioParametrizar.findActivo();

    }

    @Command
    @NotifyChange({"listaRecetaModel"})
    public void agregarItemReceta() {
        RecetaDao rec = new RecetaDao();
        rec.setRecM(Boolean.TRUE);
        rec.setRecT(Boolean.TRUE);
        rec.setRecN(Boolean.TRUE);
        ((ListModelList<RecetaDao>) listaRecetaModel).add(rec);
    }

    @Command
    @NotifyChange({"listaRecetaModel", "listaExamenModel"})
    public void getRecetaExamen() {

        List<Examen> listaExamRecup = servicioExamen.findForVisiMedica(entidad);
        for (Examen examen : listaExamRecup) {
            ExamenDao item = new ExamenDao();
            item.setPath(examen.getExaPath());
            item.setDescripcion(examen.getExaDescripcion());

            try {
                fotoGeneral = new AImage("fotoPedido", Imagen_A_Bytes(examen.getExaPath()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NuevoVisita.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NuevoVisita.class.getName()).log(Level.SEVERE, null, ex);
            }
            item.setFotoGeneral(fotoGeneral);
            ((ListModelList<ExamenDao>) listaExamenModel).add(item);
        }
        List<Receta> listaReceta = servicioReceta.findForVisitaMedica(entidad);
        for (Receta receta : listaReceta) {
            RecetaDao item = new RecetaDao();
            item.setMedicamento(receta.getRecMedicamento());
            item.setRecCantidad(receta.getRecCantidad());
            item.setRecM(receta.getRecM());
            item.setRecT(receta.getRecT());
            item.setRecN(receta.getRecN());
            item.setIndicacion(receta.getRecDescripcion());

            ((ListModelList<RecetaDao>) listaRecetaModel).add(item);
        }
    }

    @Command
    public void guardar() throws JRException, IOException, NamingException {
        if (entidad.getVisObservacion() != null) {

            if (accion.equals("create")) {

                servicioVisitaMedica.crear(entidad);
                //  Messagebox.show("Guardado con exito");

                Examen examen = new Examen();
                Receta receta = new Receta();
                for (ExamenDao examenDao : listaExamenModel) {
                    examen = new Examen();
                    examen.setExaActivo(Boolean.TRUE);
                    examen.setExaPath(examenDao.getPath());
                    examen.setExaDescripcion(examenDao.getDescripcion());
                    examen.setIdVisitaMedica(entidad);
                    servicioExamen.crear(examen);
                }
                for (RecetaDao recetaDao : listaRecetaModel) {
                    receta = new Receta();
                    receta.setRecDescripcion(recetaDao.getIndicacion());
                    receta.setRecMedicamento(recetaDao.getMedicamento());
                    receta.setIdVisitaMedica(entidad);
                    receta.setRecCantidad(recetaDao.getRecCantidad());
                    receta.setRecM(recetaDao.getRecM());
                    receta.setRecT(recetaDao.getRecT());
                    receta.setRecN(recetaDao.getRecN());
                    servicioReceta.crear(receta);
                }
                try {
                    reporteGeneral(entidad.getIdVisitaMedica());
                } catch (SQLException ex) {
                    Logger.getLogger(NuevoVisita.class.getName()).log(Level.SEVERE, null, ex);
                }
                wVisita.detach();
            } else {

                servicioVisitaMedica.modificar(entidad);
                servicioVisitaMedica.eliminarExamenesRecetas(entidad.getIdVisitaMedica());
                Examen examen = new Examen();
                Receta receta = new Receta();
                for (ExamenDao examenDao : listaExamenModel) {
                    examen = new Examen();
                    examen.setExaActivo(Boolean.TRUE);
                    examen.setExaPath(examenDao.getPath());
                    examen.setExaDescripcion(examenDao.getDescripcion());
                    examen.setIdVisitaMedica(entidad);

                    servicioExamen.crear(examen);
                }
                for (RecetaDao recetaDao : listaRecetaModel) {
                    receta = new Receta();
                    receta.setRecDescripcion(recetaDao.getIndicacion());
                    receta.setRecMedicamento(recetaDao.getMedicamento());
                    receta.setIdVisitaMedica(entidad);
                    receta.setRecCantidad(recetaDao.getRecCantidad());
                    receta.setRecM(recetaDao.getRecM());
                    receta.setRecT(recetaDao.getRecT());
                    receta.setRecN(recetaDao.getRecN());
                    servicioReceta.crear(receta);
                }

                wVisita.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public VisitaMedica getEntidad() {
        return entidad;
    }

    public void setEntidad(VisitaMedica entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<Receta> getListaRecetas() {
        return listaRecetas;
    }

    public void setListaRecetas(List<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    public ListModelList<ExamenDao> getListaExamenModel() {
        return listaExamenModel;
    }

    public void setListaExamenModel(ListModelList<ExamenDao> listaExamenModel) {
        this.listaExamenModel = listaExamenModel;
    }

    public ListModelList<RecetaDao> getListaRecetaModel() {
        return listaRecetaModel;
    }

    public void setListaRecetaModel(ListModelList<RecetaDao> listaRecetaModel) {
        this.listaRecetaModel = listaRecetaModel;
    }

    public Set<Receta> getRegistroSelectedReceta() {
        return registroSelectedReceta;
    }

    public void setRegistroSelectedReceta(Set<Receta> registroSelectedReceta) {
        this.registroSelectedReceta = registroSelectedReceta;
    }

    /*CARGAR LOS EXAMENES*/
    @Command
    @NotifyChange({"fileContent", "empresa", "fotoGeneral", "listaExamenModel"})
    public void subirExamen() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.util.media.AMedia) {
            String nombre = media.getName();

            if (!nombre.contains("pdf")) {
                Clients.showNotification("Debe cargar un archivo PDF ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);

                return;
            }

            if (media.getByteData().length > 10 * 1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                return;
            }
            filePath = parametrizar.getParBase() + File.separator + parametrizar.getParImagenes() + File.separator;

            File baseDir = new File(filePath);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }
            Files.copy(new File(filePath + File.separator + media.getName()),
                        media.getStreamData());
            filePath = filePath + File.separator + media.getName();
            System.out.println("pathhhhhhh " + filePath);
            File f = new File(filePath);
            // Messagebox.show(" dfdfdfdsfdsf" + filePath);

            FileInputStream fs = new FileInputStream(f);
            fs.read(buffer);
            fs.close();

            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            fileContent = new AMedia("report", "pdf", "application/pdf", is);

            ExamenDao dao = new ExamenDao();
            dao.setPath(filePath);
            dao.setDescripcion("Examen "+media.getName());
            dao.setFotoGeneral(fotoGeneral);
            ((ListModelList<ExamenDao>) listaExamenModel).add(dao);
//            listaExamenModel.add(dao);

        }

    }

    public byte[] Imagen_A_Bytes(String pathImagen) throws FileNotFoundException {
        String reportPath = "";
        reportPath = pathImagen;
        File file = new File(reportPath);

        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
//                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        }

        byte[] bytes = bos.toByteArray();
        return bytes;
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void verImagen(@BindingParam("valor") ExamenDao valor) {
        try {
////            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
//            final HashMap<String, AImage> map = new HashMap<String, AImage>();
//
//            map.put("valor", valor.getFotoGeneral());
//            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
//                        "/visor/visorimagen.zul", null, map);
//            window.doModal();
            File f = new File(valor.getPath());
            // Messagebox.show(" dfdfdfdsfdsf" + filePath);

            FileInputStream fs = new FileInputStream(f);
            fs.read(buffer);
            fs.close();
            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", is);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/visor/visorreporte.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void eliminarExamen(@BindingParam("valor") ExamenDao valor) {
        try {
            ((ListModelList<ExamenDao>) listaExamenModel).remove(valor);
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error no se puede eliminar" + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaPaciente", "buscarPaciente"})
    public void eliminarReceta(@BindingParam("valor") RecetaDao valor) {
        try {
            ((ListModelList<RecetaDao>) listaRecetaModel).remove(valor);
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error no se puede eliminar" + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"entidad"})
    public void cargarCie() {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, NuevaVisitaParam> map = new HashMap<String, NuevaVisitaParam>();
            NuevaVisitaParam param = new NuevaVisitaParam("cie", null);
            map.put("valor", param);

            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/cargarcie10.zul", null, map);
            window.doModal();
            String CIE10CONCAt = entidad.getVisCargarCie10() != null ? entidad.getVisCargarCie10() : "";
            CIE10CONCAt = CIE10CONCAt + "CIE10 " + CIE10.toUpperCase();
            entidad.setVisCargarCie10(CIE10CONCAt);
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }

    /*CARGAR cie 10*/
    private void buscarCapituloBD() {
        listaCapitulo = servicioCapitulo.finLike(buscarCapitulo);
    }

    private void buscarSubcapituloBD() {
        listaSubcapitulos = servicioSubCapitulo.findByCapitulo(capituloSelected, buscarSubCapitulo);
    }

    private void buscarDetalleBD() {
        listaDetalles = servicioDetalle.findBySubCapituloLike(buscarDetalle);
//        listaDetalles = servicioDetalle.findBySubCapitulo(subCapituloSelected, buscarDetalle);
    }

    @Command
    @NotifyChange({"listaSubcapitulos", "buscarSubCapitulo", "capituloSelected", "listaDetalles"})
    public void buscarSubCapitulo(@BindingParam("valor") Capitulo valor) {
        if (capituloSelected == null) {
            capituloSelected = valor;
        }
        subCapituloSelected = null;
        buscarSubcapituloBD();
        buscarDetalleBD();
    }

    @Command
    @NotifyChange({"listaCapitulo", "buscarCapitulo"})
    public void buscarCapitulo(@BindingParam("valor") Capitulo valor) {

        buscarCapituloBD();
    }

    @Command
    @NotifyChange({"listaDetalles", "buscar", "subCapituloSelected"})
    public void busacarDetalle() {
//        @BindingParam("valor") Subcapitulo valor
//        if (subCapituloSelected == null) {
//            subCapituloSelected = valor;
//        }
        buscarDetalleBD();
    }

    @Command
    @NotifyChange({"listaDetalles", "buscarDetalle", "subCapituloSelected"})
    public void seleccionarCie(@BindingParam("valor") Detalle valor) {
//        CIE10 = valor.getDetDetalle() + "/" + valor.getIdSubcapitulo().getSubDetalle() + "/" + valor.getIdSubcapitulo().getIdCapitulo().getCapDetalle();
        CIE10 = valor.getDetCodigo() + " - " + valor.getDetDetalle() + "\n";
        wVCargarCie.detach();
    }

    public List<Capitulo> getListaCapitulo() {
        return listaCapitulo;
    }

    public void setListaCapitulo(List<Capitulo> listaCapitulo) {
        this.listaCapitulo = listaCapitulo;
    }

    public Capitulo getCapituloSelected() {
        return capituloSelected;
    }

    public void setCapituloSelected(Capitulo capituloSelected) {
        this.capituloSelected = capituloSelected;
    }

    public Subcapitulo getSubCapituloSelected() {
        return subCapituloSelected;
    }

    public void setSubCapituloSelected(Subcapitulo subCapituloSelected) {
        this.subCapituloSelected = subCapituloSelected;
    }

    public List<Subcapitulo> getListaSubcapitulos() {
        return listaSubcapitulos;
    }

    public void setListaSubcapitulos(List<Subcapitulo> listaSubcapitulos) {
        this.listaSubcapitulos = listaSubcapitulos;
    }

    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getBuscarCapitulo() {
        return buscarCapitulo;
    }

    public void setBuscarCapitulo(String buscarCapitulo) {
        this.buscarCapitulo = buscarCapitulo;
    }

    public String getBuscarSubCapitulo() {
        return buscarSubCapitulo;
    }

    public void setBuscarSubCapitulo(String buscarSubCapitulo) {
        this.buscarSubCapitulo = buscarSubCapitulo;
    }

    public String getBuscarDetalle() {
        return buscarDetalle;
    }

    public void setBuscarDetalle(String buscarDetalle) {
        this.buscarDetalle = buscarDetalle;
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
