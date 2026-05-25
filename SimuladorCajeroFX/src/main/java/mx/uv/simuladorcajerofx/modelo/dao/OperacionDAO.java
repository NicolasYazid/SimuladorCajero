/*
 * Copyright © 2026 Nicolás Cruz && Isaac Vazqués.
 * Todos los derechos reservados.
 *
 * Este software es de uso académico y privado.
 * Fecha de creación: 25 de mayo del 2026
 */
package mx.uv.simuladorcajerofx.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.simuladorcajerofx.modelo.MySQLConnectionManager;
import mx.uv.simuladorcajerofx.modelo.beans.Operacion;

/**
 *
 * @author yazid
 */
public class OperacionDAO {

    public static List<Operacion> getHistorialOperacionesCuenta(String numeroCuenta) {
        List<Operacion> lista = new ArrayList<>();
        try {
            MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
            String query = "SELECT * FROM operacion WHERE numeroCuenta = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();//SELECT 
            if (rs != null) {
                while (rs.next()) {
                    Operacion op = new Operacion();
                    op.setIdOperacion(rs.getInt("idOperacion"));
                    op.setNumeroCuenta(rs.getString("numeroCuenta"));
                    op.setIdTipo(rs.getInt("idTipo"));
                    op.setTiempoCreacion(rs.getString("tiempoCreacion"));
                    op.setImporte(rs.getDouble("importe"));
                    lista.add(op);
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public static boolean registrar(Operacion op) {
        if (op != null) {
            try {
                MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
                String query = "INSERT INTO operacion (numeroCuenta,idTipo,importe)"
                        + " VALUES(?,?,?);";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, op.getNumeroCuenta());
                ps.setInt(2, op.getIdTipo());
                ps.setDouble(3, op.getImporte());
                ps.executeUpdate();//INSERT - UPDATE - DELETE 
                conn.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
