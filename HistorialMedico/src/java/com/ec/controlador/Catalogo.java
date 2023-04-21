/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Capitulo;
import com.ec.entidad.Detalle;
import com.ec.entidad.Subcapitulo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCapitulo;
import com.ec.servicio.ServicioDetalle;
import com.ec.servicio.ServicioSubCapitulo;
import com.ec.utilitario.DetalleSubcapitulo;
import com.ec.utilitario.SubcapituloCapitulo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author gato
 */
public class Catalogo {
    
    ServicioCapitulo servicioCapitulo = new ServicioCapitulo();
    ServicioSubCapitulo servicioSubCapitulo = new ServicioSubCapitulo();
    ServicioDetalle servicioDetalle = new ServicioDetalle();
    private List<Capitulo> listaCapitulo = new ArrayList<Capitulo>();
    private Capitulo capituloSelected = null;
    private Subcapitulo subCapituloSelected = null;
    private List<Subcapitulo> listaSubcapitulos = new ArrayList<Subcapitulo>();
    private List<Detalle> listaDetalles = new ArrayList<Detalle>();

    //reporte
    AMedia fileContent = null;
    Connection con = null;
    UserCredential credential = new UserCredential();
    
    private String buscarCapitulo = "";
    private String buscarSubCapitulo = "";
    private String buscarDetalle = "";
    
    public Catalogo() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        buscarCapitulo();
        
    }
    
    private void buscarCapitulo() {
        listaCapitulo = servicioCapitulo.finLike(buscarCapitulo);
    }
    
    private void buscarSubcapitulo() {
        listaSubcapitulos = servicioSubCapitulo.findByCapitulo(capituloSelected, buscarSubCapitulo);
    }
    
    private void buscarDetalle() {
        listaDetalles = servicioDetalle.findBySubCapitulo(subCapituloSelected, buscarDetalle);
    }
    
    @Command
    @NotifyChange({"listaSubcapitulos", "buscar", "capituloSelected", "listaDetalles"})
    public void buscarSubCapitulo(@BindingParam("valor") Capitulo valor) {
        capituloSelected = valor;
        subCapituloSelected = null;
        buscarSubcapitulo();
        buscarDetalle();
    }
    
    @Command
    @NotifyChange({"listaDetalles", "buscar", "subCapituloSelected"})
    public void busacarDetalle(@BindingParam("valor") Subcapitulo valor) {
        subCapituloSelected = valor;
        buscarDetalle();
    }
    
    @Command
    @NotifyChange({"listaCapitulo", "buscar"})
    public void nuevoCapitulo() {
        try {
            
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/capitulo.zul", null, null);
            window.doModal();
            buscarCapitulo();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }
    
    @Command
    @NotifyChange({"listaCapitulo", "buscar"})
    public void modificarCapitulo(@BindingParam("valor") Capitulo valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, Capitulo> map = new HashMap<String, Capitulo>();
            
            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/medico/nuevo/capitulo.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }
    
    @Command
    @NotifyChange({"listaSubcapitulos", "buscar"})
    public void nuevoSubcapitulo() {
        try {
            if (capituloSelected != null) {
                final HashMap<String, SubcapituloCapitulo> map = new HashMap<String, SubcapituloCapitulo>();
                SubcapituloCapitulo parametros = new SubcapituloCapitulo(capituloSelected, null);
                map.put("valor", parametros);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                            "/medico/nuevo/subcapitulo.zul", null, map);
                window.doModal();
                buscarSubcapitulo();
            } else {
                Clients.showNotification("Seleccione un capitulo ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }
    
    @Command
    @NotifyChange({"listaSubcapitulos", "buscar"})
    public void modificarSubcapitulo(@BindingParam("valor") Subcapitulo valor) {
        try {
            if (capituloSelected != null) {
                final HashMap<String, SubcapituloCapitulo> map = new HashMap<String, SubcapituloCapitulo>();
                SubcapituloCapitulo preguntaTest = new SubcapituloCapitulo(capituloSelected, valor);
                map.put("valor", preguntaTest);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                            "/medico/nuevo/subcapitulo.zul", null, map);
                window.doModal();
            } else {
                Clients.showNotification("Seleccione un capitulo ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
        
    }
    
    @Command
    @NotifyChange({"listaDetalles", "buscar"})
    public void nuevoDetalle() {
        try {
            if (subCapituloSelected != null) {
                final HashMap<String, DetalleSubcapitulo> map = new HashMap<String, DetalleSubcapitulo>();
                DetalleSubcapitulo parametro = new DetalleSubcapitulo(subCapituloSelected, null);
                map.put("valor", parametro);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                            "/medico/nuevo/detalle.zul", null, map);
                window.doModal();
                buscarDetalle();
            } else {
                Clients.showNotification("Seleccione un subcapitulo ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
    }
    
    @Command
    @NotifyChange({"listaDetalles", "buscar"})
    public void modificarDetalle(@BindingParam("valor") Detalle valor) {
        try {
            if (subCapituloSelected != null) {
                final HashMap<String, DetalleSubcapitulo> map = new HashMap<String, DetalleSubcapitulo>();
                DetalleSubcapitulo parametro = new DetalleSubcapitulo(subCapituloSelected, valor);
                map.put("valor", parametro);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                            "/medico/nuevo/detalle.zul", null, map);
                window.doModal();
            } else {
                Clients.showNotification("Seleccione un subcapitulo ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrio un error " + e.getMessage(),
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
        
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
    
}
