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
import mx.uv.simuladorcajerofx.excepciones.LimiteDepositosException;
import mx.uv.simuladorcajerofx.excepciones.RegistroOperacionException;
import mx.uv.simuladorcajerofx.excepciones.SaldoInsuficienteException;
import mx.uv.simuladorcajerofx.modelo.MySQLConnectionManager;
import mx.uv.simuladorcajerofx.modelo.beans.Cuenta;
import mx.uv.simuladorcajerofx.modelo.beans.Operacion;

/**
 *
 * @author yazid
 */
public class CuentaDAO {

    public static final Double LIMITE_DEPOSITOS = 200000.0;

    public static Cuenta buscarCuenta(String numeroTarjeta, String nip) {
        Cuenta cuenta = null;
        try {
            MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
            String query = "SELECT * FROM cuenta WHERE numeroTarjeta = ? AND nip = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, numeroTarjeta);
            ps.setString(2, nip);
            ResultSet rs = ps.executeQuery();//SELECT 
            if (rs != null && rs.next()) {
                cuenta = new Cuenta();
                cuenta.setNumeroCuenta(rs.getString("numeroCuenta"));
                cuenta.setIdCliente(rs.getInt("idCliente"));
                cuenta.setNumeroTarjeta(numeroTarjeta);
                cuenta.setNip(nip);
                cuenta.setSaldo(rs.getDouble("saldo"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cuenta;
    }

    public static void consultarSaldoActualizado(Cuenta cuenta) {
        if (cuenta != null && cuenta.getNumeroCuenta() != null) {
            try {
                MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
                String query = "SELECT saldo FROM cuenta WHERE numeroCuenta = ?;";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, cuenta.getNumeroCuenta());
                ResultSet rs = ps.executeQuery();//SELECT 
                if (rs != null && rs.next()) {
                    cuenta.setSaldo(rs.getDouble("saldo"));
                }
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean actualizarSaldo(Cuenta cuenta) {
        if (cuenta != null && cuenta.getNumeroCuenta() != null) {
            try {
                MySQLConnectionManager conn = MySQLConnectionManager.buildConnection();
                String query = "UPDATE cuenta SET saldo = ? WHERE numeroCuenta = ?;";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setDouble(1, cuenta.getSaldo());
                ps.setString(2, cuenta.getNumeroCuenta());
                ps.executeUpdate();//INSERT - UPDATE - DELETE 
                conn.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static boolean retirar(Cuenta cuenta, Double importe) throws
            SaldoInsuficienteException,
            RegistroOperacionException {
        if (cuenta != null && importe != null && importe > 0) {
            Double saldo = (cuenta.getSaldo() != null) ? cuenta.getSaldo() : 0.0;
            if (saldo < importe) {
                throw new SaldoInsuficienteException(
                        "No cuentas con saldo suficiente");
            }
            Operacion operacion = new Operacion(cuenta.getNumeroCuenta(), 2, importe);
            if (!OperacionDAO.registrar(operacion)) {
                throw new RegistroOperacionException(
                        "No se puede registrar el movimiento de retiro en la base de datos");
            }
            saldo -= importe;
            cuenta.setSaldo(saldo);
            if (!CuentaDAO.actualizarSaldo(cuenta)) {
                throw new RegistroOperacionException(
                        "No se puede actualizar el saldo en la base de datos");
            }
            CuentaDAO.consultarSaldoActualizado(cuenta);
            return true;
        }
        return false;
    }

    public static boolean depositar(Cuenta cuenta, Double importe) throws
            LimiteDepositosException,
            RegistroOperacionException {
        if (cuenta != null && importe != null && importe > 0) {
            Double saldo = (cuenta.getSaldo() != null) ? cuenta.getSaldo() : 0.0;
            if ((saldo + importe) > LIMITE_DEPOSITOS) {
                throw new LimiteDepositosException("No se puede depositar, "
                        + "el importe indicado superaría el límite de la cuenta");
            }
            Operacion operacion = new Operacion(cuenta.getNumeroCuenta(), 1, importe);
            if (!OperacionDAO.registrar(operacion)) {
                throw new RegistroOperacionException("No se puede registrar "
                        + "el movimiento de deposito en la base de datos");
            }
            saldo += importe;
            cuenta.setSaldo(saldo);
            if (!CuentaDAO.actualizarSaldo(cuenta)) {
                throw new RegistroOperacionException(
                        "No se puede actualizar el saldo en la base de datos");
            }
            CuentaDAO.consultarSaldoActualizado(cuenta);
            return true;
        }
        return false;
    }
}
