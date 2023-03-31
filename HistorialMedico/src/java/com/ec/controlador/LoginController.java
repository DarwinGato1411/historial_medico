/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.seguridad.AutentificadorLogeo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.GrupoUsuarioEnum;
import com.ec.seguridad.UserCredential;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginController extends SelectorComposer<Component> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Textbox account1;
    @Wire
    Textbox password1;
    @Wire
    Label message;

    public void LoginController() {
    }

//    @Listen("onClick=#buttonEntrar; onOK=#loginWin")
    @Listen("onClick=#buttonEntrar;onOK=#loginWin")
    public void doLogin() {

        AutentificadorLogeo servicioAuth = new AutentificadorLogeo();
        if (servicioAuth.login(account.getValue(), password.getValue())) {
            Session sess = Sessions.getCurrent();
            UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
            System.out.println("ascacsa" + " " + cre.getNivelUsuario().intValue() + " " + GrupoUsuarioEnum.CANDIDATO.getCodigo());

            Executions.sendRedirect("/medico/historial.zul");
//            if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.CANDIDATO.getCodigo()) {
//                Executions.sendRedirect("/candidato/Historial.zul");
//
//            } else if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.EMPRESA.getCodigo()) {
//                Executions.sendRedirect("/empresa/empresa.zul");
//            } else {
//                Executions.sendRedirect("/empresa/empresa.zul");
//            }
        } else {

            Clients.showNotification("Usuario o Contraseña incorrecto. \n Contactese con el administrador.",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);

        }

    }

    @Listen("onClick=#buttonEntrar1")
    public void doLoginCandidato() {

        AutentificadorLogeo servicioAuth = new AutentificadorLogeo();
        if (servicioAuth.login(account1.getValue(), password1.getValue())) {
            Session sess = Sessions.getCurrent();
            UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
            System.out.println("ascacsa" + " " + cre.getNivelUsuario().intValue() + " " + GrupoUsuarioEnum.CANDIDATO.getCodigo());

            Executions.sendRedirect("/medico/historial.zul");
//            if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.CANDIDATO.getCodigo()) {
//                Executions.sendRedirect("/candidato/candidato.zul");
//
//            } else if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.EMPRESA.getCodigo()) {
//                Executions.sendRedirect("/empresa/empresa.zul");
//            } else {
//                Executions.sendRedirect("/empresa/empresa.zul");
//            }
        } else {

            Clients.showNotification("Usuario o Contraseña incorrecto. \n Contactese con el administrador.",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);

        }

    }

    @Listen("onClick = #btnRegistraCan")
    public void btnRegistraCan() {
        Window window = (Window) Executions.createComponents(
                    "/publico/nuevo/candidato.zul", null, null);
        window.doModal();
    }

    @Listen("onClick = #btnRegistraEmp")
    public void btnRegistraEmp() {
        Window window = (Window) Executions.createComponents(
                    "/publico/nuevo/empresa.zul", null, null);
        window.doModal();
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    @Listen("onClick = #buttonConsultar")
    public void buttonConsultar() {
        Executions.sendRedirect("/consultas.zul");
    }

    @Listen("onClick = #btnSolicitarPermiso")
    public void btnSolicitarPermiso() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/permiso.zul", null, null);
        window.doModal();
    }
}
