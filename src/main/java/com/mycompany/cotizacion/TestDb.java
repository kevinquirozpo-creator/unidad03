package com.mycompany.cotizacion;

public class TestDb {

    public static void main(String[] args) {

        CotizacionDb db = new CotizacionDb();

        if (db.abrir()) {
            System.out.println("Conexion exitosa a MySQL");
            db.cerrar();
        } else {
            System.out.println("Error: " + db.error);
        }
    }
}