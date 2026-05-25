/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.uv.simuladorcajerofx.App;
import mx.uv.simuladorcajerofx.modelo.beans.Cliente;
import mx.uv.simuladorcajerofx.modelo.beans.Cuenta;
import mx.uv.simuladorcajerofx.modelo.dao.ClienteDAO;
import mx.uv.simuladorcajerofx.modelo.dao.CuentaDAO;

/**
 * FXML Controller class
 *
 * @author yazid
 */
public class AccesoController {

    @FXML
    private TextField txt_tarjeta;
    @FXML
    private PasswordField txt_nip;

    @FXML
    private void actionEntrar() {
        String tarjeta = txt_tarjeta.getText();
        String nip = txt_nip.getText();
        if (tarjeta.isBlank() || nip.isBlank()) {
            JavaFXUtils.mostrarError(
                    "Datos incompletos",
                    "Debes introducir tu número de tarjeta y nip",
                    true);
            return;
        }
        Cuenta cuenta = CuentaDAO.buscarCuenta(tarjeta, nip);
        if (cuenta != null && cuenta.getNumeroCuenta() != null) {
            Cliente cliente = ClienteDAO.buscarCliente(cuenta.getIdCliente());
            //---Guardamos la cuenta y cliente en los metadatos---// 
            App.setMetadato("cuenta", cuenta);
            App.setMetadato("cliente", cliente);
            //---Mostramos mensaje de bienvenida---// 
            JavaFXUtils.mostrarMensaje(
                    "Acceso autorizado",
                    "Bienvenido " + cliente.getNombreCompleto(),
                    false);
            //---Cambiar a Escena principal---// 
            try {
                App.setRoot("principal");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JavaFXUtils.mostrarAdvertencia(
                    "Cuenta no válida",
                    "No se encontró ningún cliente con esos datos de cuenta",
                    true);
        }
    }

    @FXML
    private void actionCancelar() {
        Platform.exit();
    }
}
