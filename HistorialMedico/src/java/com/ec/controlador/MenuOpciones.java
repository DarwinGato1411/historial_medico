/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

/**
 *
 * @author gato
 */
public class MenuOpciones extends SelectorComposer<Component> {

    UserCredential credential = new UserCredential();
    private String acceso = "";

    public MenuOpciones() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;

    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (credential.getUsuarioSistema() != null) {

        }
    }

    @Listen("onClick = #btnPerfil")
    public void btnPerfil() {
        Executions.sendRedirect("/medico/perfil.zul");
    }

    @Listen("onClick = #btnCatalogo")
    public void btnCatalogo() {
        Executions.sendRedirect("/medico/catalogo.zul");
    }

    @Listen("onClick = #btnPaciente")
    public void btnPaciente() {
        Executions.sendRedirect("/medico/paciente.zul");
    }
    @Listen("onClick = #btnHistorico")
    public void btnHistorico() {
        Executions.sendRedirect("/medico/historico_paciente.zul");
    }

    @Command
    public void nuevoProducto() {

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/producto.zul", null, null);
        window.doModal();

    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}
