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
import mx.uv.simuladorcajerofx.modelo.MySQLConnectionManager;
import mx.uv.simuladorcajerofx.modelo.beans.Cliente;

/**
 *
 * @author yazid
 */
public class ClienteDAO {

    public static Cliente buscarCliente(Integer idCliente) {
        Cliente cliente = null;
        try {
            MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
            String query = "SELECT * FROM cliente WHERE idCliente = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();//SELECT 
            if (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(idCliente);
                cliente.setNombreCompleto(rs.getString("nombreCompleto"));
                cliente.setFechaNacimiento(rs.getString("fechaNacimiento"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cliente;
    }
}
