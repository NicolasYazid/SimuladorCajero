/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.uv.simuladorcajerofx.App;
import mx.uv.simuladorcajerofx.excepciones.LimiteDepositosException;
import mx.uv.simuladorcajerofx.excepciones.RegistroOperacionException;
import mx.uv.simuladorcajerofx.excepciones.SaldoInsuficienteException;
import mx.uv.simuladorcajerofx.modelo.beans.Cliente;
import mx.uv.simuladorcajerofx.modelo.beans.Cuenta;
import mx.uv.simuladorcajerofx.modelo.dao.CuentaDAO;

/**
 * FXML Controller class
 *
 * @author yazid
 */
public class OperacionController implements Initializable {

    private Cuenta cuenta;
    private Cliente cliente;
    private boolean deposito;
    @FXML
    private Label lbl_titulo;
    @FXML
    private TextField txt_importe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //---Leemos los datos de cuenta y cliente de los metadatos---// 
        this.cuenta = (Cuenta) App.getMetadato("cuenta");
        this.cliente = (Cliente) App.getMetadato("cliente");
        //---Leemos el metadato que define el tipo de operación---// 
        this.deposito = (Boolean) App.getMetadato("operacion-deposito");
        if (this.deposito) {
            this.lbl_titulo.setText("Registrar Depósito");
        } else {
            this.lbl_titulo.setText("Registrar Retiro");
        }
    }

    @FXML
    private void actionGuardar() {
        //---Leemos el importe del componente de la interfaz---//
        String txtimporte = txt_importe.getText();

        //---Validamos que no esté vacío o nulo---//
        if (txtimporte.isBlank()) {
            JavaFXUtils.mostrarError(
                    "Datos incompletos",
                    "Debes introducir el importe de la operación",
                    true);
            return;
        }

        //---Validamos que sea un número---//
        Double importe = null;
        try {
            importe = Double.parseDouble(txtimporte);
        } catch (NumberFormatException ex) {
            JavaFXUtils.mostrarError(
                    "Importe no válido",
                    "El importe no es válido...",
                    true);
            return;
        }

        this.txt_importe.setText("");

        //---Si pasa las validaciones realizamos la operación---//
        if (this.deposito) {
            depositar(importe);
        } else {
            retirar(importe);
        }
    }

    private void depositar(Double importe) {
        try {
            if (CuentaDAO.depositar(cuenta, importe)) {
                JavaFXUtils.mostrarMensaje(
                        "Depósito",
                        "Depósito realizado con éxito",
                        true);
            } else {
                JavaFXUtils.mostrarAdvertencia(
                        "Depósito",
                        "No se puede realizar el depósito...",
                        true);
            }

            //---Regresar a Escena principal---//
            try {
                App.setRoot("principal");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (LimiteDepositosException | RegistroOperacionException ex) {
            JavaFXUtils.mostrarError(
                    "Depósito",
                    ex.getMessage(),
                    false);
        }
    }

    private void retirar(Double importe) {
        try {
            if (CuentaDAO.retirar(cuenta, importe)) {
                JavaFXUtils.mostrarMensaje(
                        "Retiro",
                        "Retiro realizado con éxito",
                        true);
            } else {
                JavaFXUtils.mostrarAdvertencia(
                        "Retiro",
                        "No se puede realizar el retiro...",
                        true);
            }

            //---Regresar a Escena principal---//
            try {
                App.setRoot("principal");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SaldoInsuficienteException | RegistroOperacionException ex) {
            JavaFXUtils.mostrarError(
                    "Retiro",
                    ex.getMessage(),
                    false);
        }
    }

    @FXML
    private void actionCancelar() {
        //---Regresar a Escena principal---//
        try {
            App.setRoot("principal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
