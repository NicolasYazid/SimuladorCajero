/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.controladores;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import mx.uv.simuladorcajerofx.App;

/**
 *
 * @author yazid
 */
public class JavaFXUtils {

    public static Double SEGUNDOS_CIERRE_AUTOMATICO = 1.5;

    public static void mostrarMensaje(String titulo,
                                        String mensaje,
                                        boolean cierreautomatico) {
        crearAlerta(Alert.AlertType.INFORMATION, titulo, mensaje, cierreautomatico);
    }

    public static void mostrarAdvertencia(String titulo,
                                            String mensaje,
                                            boolean cierreautomatico) {
        crearAlerta(Alert.AlertType.WARNING, titulo, mensaje, cierreautomatico);
    }

    public static void mostrarError(String titulo,
                                        String mensaje,
                                        boolean cierreautomatico) {
        crearAlerta(Alert.AlertType.ERROR, titulo, mensaje, cierreautomatico);
    }

    public static void crearAlerta(AlertType tipo,
                                    String titulo,
                                    String mensaje,
                                    boolean cierreautomatico) {
        //---Creamos el dialogo, en JavaFX se usa la clase Alert---// 
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.getDialogPane().autosize();
        //---Le aplicamos la hoja de estilo CSS---// 
        alerta.getDialogPane().getStylesheets().add(
                App.class.getResource("/css/estilo.css").toExternalForm()
        );
        //---Le asignamos a la alerta su clase de estilo---// 
        alerta.getDialogPane().getStyleClass().add(
                "alert-personalizada"
        );
        if (cierreautomatico) {
            alerta.show();
            PauseTransition espera = new PauseTransition(
                                Duration.seconds(SEGUNDOS_CIERRE_AUTOMATICO));
            espera.setOnFinished(e -> alerta.close());
            espera.play();
        } else {
            alerta.showAndWait();
        }
    }
}
