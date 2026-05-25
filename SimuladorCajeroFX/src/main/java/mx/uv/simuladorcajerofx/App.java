package mx.uv.simuladorcajerofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {
    /**
     * Definimos una estructura de tipo HashMap para poder almacenar metadatos
     * entre las distintas escenas de la aplicación, bajo un esquema de
     * llave - valor.
     */
    private static HashMap<String, Object> metadatos;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //---Asignamos algunas propiedades al escenario actual---// 
        stage.setTitle("Simulador Cajero FX"); 
        stage.setMinWidth(700); 
        stage.setMinHeight(300); 
        stage.setMaxWidth(900); 
        stage.setMaxHeight(700); 
        //---Asignamos la escena inicial a nuestra interfaz de acceso---// 
        scene = new Scene(loadFXML("acceso"), 800, 600); 
        //---Cargamos la hoja de estilo creada en el proyecto---// 
        scene.getStylesheets().add( 
                App.class.getResource("/css/estilo.css").toExternalForm() 
        ); 
        stage.setScene(scene); 
        stage.show(); 
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * Método que permite agregar un valor al repositorio de metadatos
     * @param nombre
     * @param valor
     */
    public static void setMetadato(String nombre, Object valor) {
        if (metadatos == null) {
            metadatos = new HashMap<>();
        }
        metadatos.put(nombre, valor);
    }
    
    /**
     * Método que permite obtener un valor de los metadatos con base en su nombre
     * @param nombre
     * @return
     */
    public static Object getMetadato(String nombre) {
        if (metadatos == null) {
            return null;
        }
        return metadatos.get(nombre);
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
