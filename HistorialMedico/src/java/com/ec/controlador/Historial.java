/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;


import com.ec.entidad.Usuario;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;

import com.ec.servicio.ServicioUsuario;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Darwin
 */
public class Historial {

  
    private Usuario usuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    UserCredential credential = new UserCredential();



    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;
    
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException, IOException {
        Selectors.wireComponents(view, this, false);
       
    }

    public Historial() {

        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
       
    }

   

 
    @Command
    @NotifyChange({"fileContent", "empresa", "fotoGeneral"})
    public void subirLogotipo() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            String nombre = media.getName();

            if (media.getByteData().length > 10 * 1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                return;
            }
//            filePath = parametrizar.getParBase() + File.separator + parametrizar.getParImagenes() + File.separator;

            File baseDir = new File(filePath);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }
            Files.copy(new File(filePath + File.separator + media.getName()),
                        media.getStreamData());
//            Usuario usuFoto = candidato.getIdUsuario();
//            usuFoto.setUsuFoto(filePath + File.separator + media.getName());
            System.out.println("PATH SUBIR " + filePath + File.separator + media.getName());
            fotoGeneral = new AImage("fotoPedido", Imagen_A_Bytes(filePath + File.separator + media.getName()));

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
    public void guardar() {

    }

    

    public AImage getFotoGeneral() {
        return fotoGeneral;
    }

    public void setFotoGeneral(AImage fotoGeneral) {
        this.fotoGeneral = fotoGeneral;
    }
    
    
    
}
