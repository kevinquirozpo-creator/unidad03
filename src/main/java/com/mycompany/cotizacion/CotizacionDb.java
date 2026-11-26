package com.mycompany.cotizacion;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class CotizacionDb implements dbMetodos {

    private Connection conexion;
    private static String password;
    public String error = "";

    @Override
    public boolean abrir() {
        try {
            String url = "jdbc:mysql://localhost:3306/cotizaciones";
            String usuario = "root";

            if (password == null) {
                password = System.getenv("DB_PASSWORD");
            }

            if (password == null || password.isEmpty()) {

                JPasswordField campo = new JPasswordField();

                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        campo,
                        "Ingresa la contraseña de MySQL",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (opcion != JOptionPane.OK_OPTION) {
                    error = "Conexion cancelada";
                    return false;
                }

                password = new String(campo.getPassword());
            }

            conexion = DriverManager.getConnection(
                    url, usuario, password
            );

            error = "";
            return true;

        } catch (SQLException e) {
            error = e.getMessage();
            password = null;
            return false;
        }
    }

    @Override
    public boolean cerrar() {
        try {
            if (conexion != null) {
                conexion.close();
            }
            return true;

        } catch (SQLException e) {
            error = e.getMessage();
            return false;
        }
    }

    @Override
    public int insertar(Object obj) {
        Cotizacion c = (Cotizacion) obj;

        if (!abrir()) {
            return -1;
        }

        String sql = "INSERT INTO Cotizacion "
                + "(numCotizacion, descripcion, precio, "
                + "porcentaje, plazo, fecha, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, c.getNumCotizacion());
            p.setString(2, c.getDescripcion());
            p.setFloat(3, c.getPrecio());
            p.setFloat(4, c.getPorcentaje());
            p.setInt(5, c.getPlazo());
            p.setDate(6, c.getFecha());
            p.setInt(7, c.getStatus());

            p.executeUpdate();

            try (ResultSet r = p.getGeneratedKeys()) {
                if (r.next()) {
                    return r.getInt(1);
                }
            }

            return -1;

        } catch (SQLException e) {
            error = e.getMessage();
            return -1;

        } finally {
            cerrar();
        }
    }

    @Override
    public int actualizar(Object obj) {
        Cotizacion c = (Cotizacion) obj;

        if (!abrir()) {
            return -1;
        }

        String sql = "UPDATE Cotizacion SET "
                + "numCotizacion=?, descripcion=?, "
                + "precio=?, porcentaje=?, plazo=?, "
                + "fecha=?, status=? "
                + "WHERE idCotizacion=?";

        try (PreparedStatement p = conexion.prepareStatement(sql)) {

            p.setString(1, c.getNumCotizacion());
            p.setString(2, c.getDescripcion());
            p.setFloat(3, c.getPrecio());
            p.setFloat(4, c.getPorcentaje());
            p.setInt(5, c.getPlazo());
            p.setDate(6, c.getFecha());
            p.setInt(7, c.getStatus());
            p.setInt(8, c.getIdCotizacion());

            return p.executeUpdate();

        } catch (SQLException e) {
            error = e.getMessage();
            return -1;

        } finally {
            cerrar();
        }
    }

    @Override
    public int habilitar(int id) {
        return cambiarEstado(id, 1);
    }

    @Override
    public int deshabilitar(int id) {
        return cambiarEstado(id, 0);
    }

    private int cambiarEstado(int id, int status) {

        if (!abrir()) {
            return -1;
        }

        String sql = "UPDATE Cotizacion SET status=? "
                + "WHERE idCotizacion=?";

        try (PreparedStatement p = conexion.prepareStatement(sql)) {

            p.setInt(1, status);
            p.setInt(2, id);

            return p.executeUpdate();

        } catch (SQLException e) {
            error = e.getMessage();
            return -1;

        } finally {
            cerrar();
        }
    }

    @Override
    public Object buscar(int id) {
        return buscarCampo("idCotizacion", id);
    }

    @Override
    public Object buscar(String num) {
        return buscarCampo("numCotizacion", num);
    }

    private Cotizacion buscarCampo(String campo, Object valor) {

        if (!abrir()) {
            return null;
        }

        String sql = "SELECT * FROM Cotizacion WHERE "
                + campo + "=?";

        try (PreparedStatement p = conexion.prepareStatement(sql)) {

            p.setObject(1, valor);

            try (ResultSet r = p.executeQuery()) {

                if (r.next()) {
                    return new Cotizacion(
                            r.getInt("idCotizacion"),
                            r.getString("numCotizacion"),
                            r.getString("descripcion"),
                            r.getFloat("porcentaje"),
                            r.getFloat("precio"),
                            r.getInt("plazo"),
                            r.getDate("fecha"),
                            r.getInt("status")
                    );
                }
            }

        } catch (SQLException e) {
            error = e.getMessage();

        } finally {
            cerrar();
        }

        return null;
    }
}