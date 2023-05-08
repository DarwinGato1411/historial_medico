/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Paciente;
import com.ec.entidad.Parametrizar;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioPaciente;
import com.ec.servicio.ServicioParametrizar;
import com.ec.utilitario.ArchivoUtils;
import com.ec.utilitario.InfoPersona;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Fileupload;
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

    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = new Parametrizar();

    private List<BigDecimal> listaTalla = new ArrayList<BigDecimal>();
    private List<BigDecimal> listaPeso = new ArrayList<BigDecimal>();

    public NuevoPaciente() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        parametrizar = servicioParametrizar.findActivo();

    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Paciente valor, @ContextParam(ContextType.VIEW) Component view) throws IOException {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.entidad = valor;
            accion = "update";
        } else {
            this.entidad = new Paciente();
            this.entidad.setPacFechaNacimiento(new Date());
            this.entidad.setPacEstado(Boolean.TRUE);
            accion = "create";
        }

        if (entidad.getPacFotografia() != null) {
            try {
                System.out.println("FOTOGRAFIA" + entidad.getPacFotografia());
                fotoGeneral = new AImage("fotografia", Imagen_A_Bytes(entidad.getPacFotografia()));
//                Imagen_A_Bytes(empresa.getIdUsuario().getUsuFoto());
            } catch (FileNotFoundException e) {
                System.out.println("error imagen " + e.getMessage());
            }
        }
//        BigDecimal inicial = BigDecimal.valueOf(0.60);
//        while (inicial.doubleValue() < BigDecimal.valueOf(3).doubleValue()) {
//            listaTalla.add(inicial);
//            inicial = inicial.add(BigDecimal.valueOf(0.01));
//        }
//        BigDecimal inicialPeso = BigDecimal.valueOf(10);
//        while (inicialPeso.doubleValue() < BigDecimal.valueOf(200).doubleValue()) {
//            listaPeso.add(inicialPeso);
//            inicialPeso = inicialPeso.add(BigDecimal.valueOf(0.1));
//        }

    }

    @Command
    @NotifyChange({"entidad"})
    public void calcularIMC() {
        if (entidad.getPacTalla() != null) {
            if (entidad.getPacTalla().doubleValue() > 0) {
                if (entidad.getPacTalla().doubleValue() > 0) {
                    BigDecimal tallaCentimetros = entidad.getPacTalla();
                    BigDecimal talla2 = tallaCentimetros.multiply(tallaCentimetros).setScale(5, RoundingMode.FLOOR);
                    BigDecimal IMC = entidad.getPacPeso() != null ? entidad.getPacPeso().doubleValue() > 0 ? entidad.getPacPeso().divide(talla2, 5, RoundingMode.FLOOR) : BigDecimal.ZERO : BigDecimal.ZERO;
                    entidad.setPacImc(ArchivoUtils.redondearDecimales(IMC, 2));
                }

            } else {
                entidad.setPacImc(BigDecimal.ZERO);
            }

        }

    }

    @Command
    @NotifyChange({"entidad"})
    public void buscarInformacion() throws URISyntaxException, IOException, XPathExpressionException, JSONException {
        InfoPersona aduana = new InfoPersona();
        String nombre = "";
        if (entidad.getPacRuc() != null) {
            if (!entidad.getPacRuc().equals("")) {
                String cedulaBuscar = "";
                if (entidad.getPacRuc().length() == 13) {
                    cedulaBuscar = entidad.getPacRuc();
                    nombre = ArchivoUtils.obtenerPorRuc(cedulaBuscar);
                    entidad.setPacNombres(nombre);

                } else if (entidad.getPacRuc().length() == 10) {
                    cedulaBuscar = entidad.getPacRuc();
                    aduana = ArchivoUtils.obtenerPorCedula(cedulaBuscar);
                    entidad.setPacNombres(aduana.getNombre());
                    entidad.setPacDomicilio(aduana.getDireccion());
                }

            }
        }

    }

    @Command
    @NotifyChange("entidad")
    public void obtenerEdad() {
        if (entidad.getPacFechaNacimiento() != null) {
            BigDecimal edad = ArchivoUtils.obtenerEdad(entidad.getPacFechaNacimiento());
            entidad.setPacEdad(edad.intValue());
        }
    }

    @Command
    public void guardar() {
        if (entidad.getPacRuc() != null
                    && entidad.getPacNombres() != null
                    && entidad.getPacMovil() != null
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

    @Command
    @NotifyChange({"fileContent", "entidad", "fotoGeneral"})
    public void subirFotografia() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            String nombre = media.getName();

            if (media.getByteData().length > 10 * 1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                return;
            }
            filePath = parametrizar.getParBase() + File.separator + parametrizar.getParImagenes() + File.separator + "FOTO";

            File baseDir = new File(filePath);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }
            Files.copy(new File(filePath + File.separator + media.getName()),
                        media.getStreamData());

            entidad.setPacFotografia(filePath + File.separator + media.getName());
            System.out.println("PATH SUBIR " + filePath + File.separator + media.getName());
            fotoGeneral = new AImage("fotografia", Imagen_A_Bytes(filePath + File.separator + media.getName()));

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

    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }

    public List<BigDecimal> getListaTalla() {
        return listaTalla;
    }

    public void setListaTalla(List<BigDecimal> listaTalla) {
        this.listaTalla = listaTalla;
    }

    public List<BigDecimal> getListaPeso() {
        return listaPeso;
    }

    public void setListaPeso(List<BigDecimal> listaPeso) {
        this.listaPeso = listaPeso;
    }

}
