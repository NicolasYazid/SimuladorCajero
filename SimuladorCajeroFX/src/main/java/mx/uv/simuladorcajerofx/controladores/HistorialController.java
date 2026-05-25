/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.simuladorcajerofx.App;
import mx.uv.simuladorcajerofx.modelo.beans.Cliente;
import mx.uv.simuladorcajerofx.modelo.beans.Cuenta;
import mx.uv.simuladorcajerofx.modelo.beans.Operacion;
import mx.uv.simuladorcajerofx.modelo.dao.OperacionDAO;

/**
 * FXML Controller class
 *
 * @author yazid
 */
public class HistorialController implements Initializable {

    private Cuenta cuenta;
    private Cliente cliente;

    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_tarjeta;
    @FXML
    private Label lbl_saldo;
    @FXML
    private TableView<Operacion> tbl_datos;
    @FXML
    private TableColumn<Operacion, Integer> col_tipo;
    @FXML
    private TableColumn<Operacion, String> col_fecha;
    @FXML
    private TableColumn<Operacion, Double> col_importe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //---Leemos los datos de cuenta y cliente de los metadatos---//
        this.cuenta = (Cuenta) App.getMetadato("cuenta");
        this.cliente = (Cliente) App.getMetadato("cliente");

        //---Mostramos en la interfaz el nombre, tarjeta y saldo---//
        this.lbl_nombre.setText(cliente.getNombreCompleto());
        this.lbl_tarjeta.setText(cuenta.getNumeroTarjeta());
        this.lbl_saldo.setText(String.format("$%,.2f", cuenta.getSaldo()));

        //---Consultamos el historial de operaciones de la cuenta---//
        List<Operacion> lista_operaciones = OperacionDAO.getHistorialOperacionesCuenta(cuenta.getNumeroCuenta());

        /*---Ajustamos el modelo de la tabla indicando con qué propiedad
         del bean Operacion se van a mapear los datos de cada columna---*/
        col_tipo.setCellValueFactory(new PropertyValueFactory<>("idTipo"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("tiempoCreacion"));
        col_importe.setCellValueFactory(new PropertyValueFactory<>("importe"));

        //---Agregamos formatos a las columnas---//
        col_tipo.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == 1 ? "Deposito" : "Retiro");
                }
            }
        });

        col_importe.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$ %,.2f", item));
                }
            }
        });

        //---Cargamos los datos a la tabla---//
        tbl_datos.setItems(FXCollections.observableArrayList(lista_operaciones));
    }

    @FXML
    private void actionRegresar(ActionEvent event) {
        //---Regresar a la escena principal---//
        try {
            App.setRoot("principal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
