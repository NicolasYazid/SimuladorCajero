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
import mx.uv.simuladorcajerofx.App;
import mx.uv.simuladorcajerofx.modelo.beans.Cliente;
import mx.uv.simuladorcajerofx.modelo.beans.Cuenta;

/**
 * FXML Controller class
 *
 * @author yazid
 */
public class PrincipalController implements Initializable {
    
    private Cuenta cuenta;
    private Cliente cliente;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_tarjeta;
    @FXML
    private Label lbl_saldo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //---Leemos los datos de cuenta y cliente de los metadatos---// 
        this.cuenta = (Cuenta)App.getMetadato("cuenta"); 
        this.cliente = (Cliente)App.getMetadato("cliente"); 
        //---Mostramos en la interfaz el nombre, tarjeta y saldo---// 
        this.lbl_nombre.setText(cliente.getNombreCompleto()); 
        this.lbl_tarjeta.setText(cuenta.getNumeroTarjeta()); 
        this.lbl_saldo.setText(String.format("$ %,.2f", cuenta.getSaldo()));
    }    

    @FXML
    private void actionDepositar() {
        //---Agregamos a los metadatos el tipo de operación a realizar---// 
        //--- true = deposito---// 
        App.setMetadato("operacion-deposito", true); 
        //---Cargar Escena operacion---// 
        try{ 
            App.setRoot("operacion"); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    }

    @FXML
    private void actionRetirar() {
        //---Agregamos a los metadatos el tipo de operación a realizar---// 
        //--- false = retiro---// 
        App.setMetadato("operacion-deposito", false); 
        //---Cargar Escena operacion---// 
        try{ 
            App.setRoot("operacion"); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    }

    @FXML
    private void actionHistorial() {
        //---Cargar Escena Historial---//
        try {
            App.setRoot("historial");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionSalir() {
        //---Limpiar metadatos---// 
        App.setMetadato("cuenta", null); 
        App.setMetadato("cliente", null); 
        //---Regresar a escena Acceso---// 
        try { 
            App.setRoot("acceso"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}
