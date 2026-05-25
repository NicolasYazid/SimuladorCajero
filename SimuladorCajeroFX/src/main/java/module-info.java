module mx.uv.simuladorcajerofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens mx.uv.simuladorcajerofx to javafx.fxml;
    opens mx.uv.simuladorcajerofx.controladores to javafx.fxml;
    opens mx.uv.simuladorcajerofx.modelo.beans to javafx.base;
    exports mx.uv.simuladorcajerofx;
}
