
package com.mycompany.cotizacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import com.toedter.calendar.JDateChooser;

public class jdlCotizacion extends JDialog {

    private final JTextField txtNumCotizacion = new JTextField(15);
    private final JTextField txtDescripcion = new JTextField(25);
    private final JTextField txtPrecio = new JTextField(12);

    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnCancelar = new JButton("Cancelar");
    private final JButton btnCerrar = new JButton("Cerrar");
    private final JButton btnDeshabilitar = new JButton("Deshabilitar");
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnHabilitar = new JButton("Habilitar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnNuevo = new JButton("Nuevo");

    private final JComboBox<String> cmbStatus =
            new JComboBox<>(new String[]{"Activas", "Inactivas"});

    private final JDateChooser jdcFecha = new JDateChooser();

    private final JLabel lblPagoInicial = new JLabel();
    private final JLabel lblPagoMensual = new JLabel();
    private final JLabel lblTotalFin = new JLabel();

    private final JRadioButton rdb12 = new JRadioButton("12 meses");
    private final JRadioButton rdb24 = new JRadioButton("24 meses");
    private final JRadioButton rdb36 = new JRadioButton("36 meses");
    private final JRadioButton rdb48 = new JRadioButton("48 meses");

    private final JSpinner spnPorcentaje =
            new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 1.0));

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new String[]{"Número", "Descripción", "Precio", "Plazo"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable tbDatos = new JTable(modelo);

    private final DecimalFormat formato =
            new DecimalFormat("$ #,##0.00");

    private final CotizacionDb db = new CotizacionDb();

    private int idActual = 0;
    private int statusActual = 1;

    public jdlCotizacion(Frame padre, boolean modal) {

        super(padre, "Cotización", modal);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(790, 470);
        setLocationRelativeTo(padre);

        initComponents();
        limpiar();
        deshabilitar();
        cargarTabla();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrarVentana();
            }
        });
    }

    private void initComponents() {

        JPanel base = new JPanel(new BorderLayout(12, 12));

        base.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        setContentPane(base);

        JPanel datos = new JPanel(new GridLayout(5, 2, 8, 5));

        JPanel numero = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0));

        numero.add(txtNumCotizacion);
        numero.add(btnBuscar);

        datos.add(new JLabel("Num. Cotización"));
        datos.add(numero);

        datos.add(new JLabel("Descripción"));
        datos.add(txtDescripcion);

        datos.add(new JLabel("Porcentaje"));
        datos.add(spnPorcentaje);

        datos.add(new JLabel("Precio"));
        datos.add(txtPrecio);

        JPanel plazos = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0));

        ButtonGroup grupo = new ButtonGroup();

        for (JRadioButton r : new JRadioButton[]{
            rdb12, rdb24, rdb36, rdb48
        }) {
            grupo.add(r);
            plazos.add(r);
            r.addActionListener(e -> calcular());
        }

        datos.add(new JLabel("Plazos"));
        datos.add(plazos);

        JPanel lateral = new JPanel(new GridLayout(6, 1, 4, 5));

        lateral.add(new JLabel("Fecha"));

        jdcFecha.setDateFormatString("dd/MM/yyyy");
        lateral.add(jdcFecha);

        lateral.add(btnNuevo);
        lateral.add(btnGuardar);
        lateral.add(btnDeshabilitar);
        lateral.add(btnHabilitar);

        JPanel arriba = new JPanel(new BorderLayout(12, 0));

        arriba.add(datos);
        arriba.add(lateral, BorderLayout.EAST);

        base.add(arriba, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(5, 5));

        JPanel resumen = new JPanel(new GridLayout(2, 3, 8, 8));

        resumen.setBackground(new Color(211, 207, 250));

        for (String texto : new String[]{
            "Pago inicial",
            "Total a financiar",
            "Pago mensual"
        }) {
            resumen.add(new JLabel(texto, SwingConstants.CENTER));
        }

        for (JLabel lbl : new JLabel[]{
            lblPagoInicial, lblTotalFin, lblPagoMensual
        }) {
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            resumen.add(lbl);
        }

        centro.add(resumen, BorderLayout.NORTH);

        JPanel tabla = new JPanel(new BorderLayout());

        JPanel filtro = new JPanel();

        filtro.add(new JLabel("Mis cotizaciones"));
        filtro.add(cmbStatus);

        tabla.add(filtro, BorderLayout.NORTH);
        tabla.add(new JScrollPane(tbDatos));

        centro.add(tabla);
        base.add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        botones.add(btnLimpiar);
        botones.add(btnCancelar);
        botones.add(btnCerrar);

        base.add(botones, BorderLayout.SOUTH);

        // EVENTOS DE LOS BOTONES

        btnNuevo.addActionListener(e -> {
            limpiar();
            habilitar();
            txtNumCotizacion.requestFocusInWindow();
        });

        btnGuardar.addActionListener(e -> guardarCotizacion());

        btnBuscar.addActionListener(e -> buscarCotizacion());

        btnHabilitar.addActionListener(e -> cambiarEstado(true));

        btnDeshabilitar.addActionListener(e -> cambiarEstado(false));

        btnLimpiar.addActionListener(e -> {
            limpiar();
            habilitar();
            txtNumCotizacion.requestFocusInWindow();
        });

        btnCancelar.addActionListener(e -> {
            limpiar();
            deshabilitar();
        });

        btnCerrar.addActionListener(e -> cerrarVentana());

        cmbStatus.addActionListener(e -> cargarTabla());

        spnPorcentaje.addChangeListener(e -> calcular());

        txtPrecio.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e) {
                calcular();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e) {
                calcular();
            }

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e) {
                calcular();
            }
        });

        tbDatos.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int fila = tbDatos.getSelectedRow();

                if (fila >= 0) {

                    txtNumCotizacion.setText(
                            modelo.getValueAt(fila, 0).toString());

                    buscarCotizacion();
                }
            }
        });
    }

    // LIMPIAR FORMULARIO

    public void limpiar() {

        idActual = 0;
        statusActual = 1;

        txtNumCotizacion.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");

        spnPorcentaje.setValue(0.0);

        rdb12.setSelected(true);

        jdcFecha.setDate(new java.util.Date());

        tbDatos.clearSelection();

        calcular();
    }

    // CONTROLAR LOS BOTONES

    private void controles(boolean activo) {

        txtNumCotizacion.setEnabled(true);
        txtDescripcion.setEnabled(activo);
        txtPrecio.setEnabled(activo);
        spnPorcentaje.setEnabled(activo);
        jdcFecha.setEnabled(activo);

        rdb12.setEnabled(activo);
        rdb24.setEnabled(activo);
        rdb36.setEnabled(activo);
        rdb48.setEnabled(activo);

        btnNuevo.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnGuardar.setEnabled(activo);
        btnLimpiar.setEnabled(activo);
        btnCancelar.setEnabled(activo);
        btnCerrar.setEnabled(true);

        btnHabilitar.setEnabled(idActual > 0 && statusActual == 0);
        btnDeshabilitar.setEnabled(idActual > 0 && statusActual == 1);
    }

    public void habilitar() {
        controles(true);
    }

    public void deshabilitar() {
        controles(false);
    }

    // VALIDAR INFORMACIÓN

    public boolean validar() {

        try {

            float precio = Float.parseFloat(
                    txtPrecio.getText().trim());

            return !txtNumCotizacion.getText().isBlank()
                    && !txtDescripcion.getText().isBlank()
                    && Float.isFinite(precio)
                    && precio > 0
                    && jdcFecha.getDate() != null;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int plazo() {

        if (rdb48.isSelected()) {
            return 48;
        }

        if (rdb36.isSelected()) {
            return 36;
        }

        if (rdb24.isSelected()) {
            return 24;
        }

        return 12;
    }

    // LLENAR OBJETO

    public Cotizacion llenarObjeto() {

        if (!validar()) {
            throw new IllegalArgumentException(
                    "Completa los campos obligatorios");
        }

        return new Cotizacion(
                idActual,
                txtNumCotizacion.getText().trim(),
                txtDescripcion.getText().trim(),
                ((Number) spnPorcentaje.getValue()).floatValue(),
                Float.parseFloat(txtPrecio.getText().trim()),
                plazo(),
                new java.sql.Date(jdcFecha.getDate().getTime()),
                statusActual
        );
    }

    // MOSTRAR DATOS

    public void mostrarDatos(Cotizacion c) {

        idActual = c.getIdCotizacion();
        statusActual = c.getStatus();

        txtNumCotizacion.setText(c.getNumCotizacion());
        txtDescripcion.setText(c.getDescripcion());
        txtPrecio.setText(String.valueOf(c.getPrecio()));

        spnPorcentaje.setValue((double) c.getPorcentaje());

        jdcFecha.setDate(c.getFecha());

        switch (c.getPlazo()) {

            case 24:
                rdb24.setSelected(true);
                break;

            case 36:
                rdb36.setSelected(true);
                break;

            case 48:
                rdb48.setSelected(true);
                break;

            default:
                rdb12.setSelected(true);
        }

        calcular();
        habilitar();
    }

    public void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(this, mensaje);
    }

    // CALCULAR PAGOS

    private void calcular() {

        float precio = 0;

        try {

            precio = Float.parseFloat(
                    txtPrecio.getText().trim());

            if (!Float.isFinite(precio) || precio < 0) {
                precio = 0;
            }

        } catch (NumberFormatException e) {
            precio = 0;
        }

        Cotizacion c = new Cotizacion();

        c.setPrecio(precio);

        c.setPorcentaje(
                ((Number) spnPorcentaje.getValue()).floatValue());

        c.setPlazo(plazo());

        lblPagoInicial.setText(
                formato.format(c.calcularPagoInicial()));

        lblTotalFin.setText(
                formato.format(c.calcularTotalFin()));

        lblPagoMensual.setText(
                formato.format(c.calcularPagoMensual()));
    }

    // GUARDAR Y ACTUALIZAR

    private void guardarCotizacion() {

        if (!validar()) {

            mostrarMensaje(
                    "Completa todos los campos correctamente");

            return;
        }

        Cotizacion c = llenarObjeto();

        boolean nuevo = idActual == 0;

        int resultado;

        if (nuevo) {
            resultado = db.insertar(c);
        } else {
            resultado = db.actualizar(c);
        }

        if (resultado >= 0) {

            mostrarMensaje(nuevo
                    ? "Cotizacion guardada correctamente"
                    : "Cotizacion actualizada correctamente");

            limpiar();
            deshabilitar();
            cargarTabla();

        } else {

            mostrarMensaje("Error al guardar: " + db.error);
        }
    }

    // BUSCAR COTIZACIÓN

    private void buscarCotizacion() {

        String numero = txtNumCotizacion.getText().trim();

        if (numero.isEmpty()) {

            mostrarMensaje("Ingresa el numero de cotizacion");

            return;
        }

        Cotizacion c = (Cotizacion) db.buscar(numero);

        if (c != null) {

            mostrarDatos(c);

        } else {

            limpiar();
            deshabilitar();
            mostrarMensaje("Cotizacion no encontrada. " + db.error);
        }
    }

    // HABILITAR Y DESHABILITAR REGISTROS

    private void cambiarEstado(boolean activo) {

        if (idActual == 0) {

            mostrarMensaje("Primero busca una cotizacion");

            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                activo
                        ? "Deseas habilitar esta cotizacion?"
                        : "Deseas deshabilitar esta cotizacion?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int resultado;

        if (activo) {
            resultado = db.habilitar(idActual);
        } else {
            resultado = db.deshabilitar(idActual);
        }

        if (resultado > 0) {

            mostrarMensaje(activo
                    ? "Cotizacion habilitada correctamente"
                    : "Cotizacion deshabilitada correctamente");

            limpiar();
            deshabilitar();
            cargarTabla();

        } else {

            mostrarMensaje("No se cambio el estado. " + db.error);
        }
    }

    // CARGAR TABLA DE MYSQL

    private void cargarTabla() {

        modelo.setRowCount(0);

        int status = cmbStatus.getSelectedIndex() == 0 ? 1 : 0;

        for (Cotizacion c : db.listar(status)) {

            modelo.addRow(new Object[]{
                c.getNumCotizacion(),
                c.getDescripcion(),
                c.getPrecio(),
                c.getPlazo()
            });
        }

        if (!db.error.isEmpty()) {
            mostrarMensaje("Error al cargar registros: " + db.error);
        }
    }

    // CERRAR VENTANA

    private void cerrarVentana() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas cerrar la ventana?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            dispose();
        }
    }
}
