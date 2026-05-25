/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.modelo.beans;

/**
 *
 * @author yazid
 */
public class Operacion {

    private Integer idOperacion;
    private String numeroCuenta;
    private Integer idTipo;
    private String tiempoCreacion;
    private Double importe;

    public Operacion() {
    }

    public Operacion(String numeroCuenta, Integer idTipo, Double importe) {
        this.numeroCuenta = numeroCuenta;
        this.idTipo = idTipo;
        this.importe = importe;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getTiempoCreacion() {
        return tiempoCreacion;
    }

    public void setTiempoCreacion(String tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
    
    @Override 
    public String toString() { 
        return "Operacion{" + "idOperacion=" + idOperacion + ", numeroCuenta=" +
                numeroCuenta + ", idTipo=" + idTipo + ", tiempoCreacion=" + tiempoCreacion + ", importe=" +
                importe + '}'; 
    }
    
}
