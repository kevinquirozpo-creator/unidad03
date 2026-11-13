package com.mycompany.unidad003;

import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Menú principal de prácticas.
 * Todos los componentes tienen su diseño en jdgMenu.form para NetBeans.
 */
public class jdgMenu extends javax.swing.JDialog {

    public jdgMenu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    /** Este constructor permite crear la ventana desde otros formularios. */
    public jdgMenu() {
        this((java.awt.Frame) null, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        escritorio = new javax.swing.JDesktopPane();
        barraMenu = new javax.swing.JMenuBar();
        menuPracticas = new javax.swing.JMenu();
        itemTerreno = new javax.swing.JMenuItem();
        itemCotizacion = new javax.swing.JMenuItem();
        itemProducto = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mis Prácticas - UNIDAD003");
        getContentPane().setLayout(new java.awt.BorderLayout());

        escritorio.setBackground(new java.awt.Color(232, 237, 242));
        escritorio.setPreferredSize(new java.awt.Dimension(800, 510));
        getContentPane().add(escritorio, java.awt.BorderLayout.CENTER);

        menuPracticas.setText("Mis Practicas");

        itemTerreno.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemTerreno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/unidad003/icons/terreno.png")));
        itemTerreno.setText("Terreno");
        itemTerreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTerrenoActionPerformed(evt);
            }
        });
        menuPracticas.add(itemTerreno);

        itemCotizacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/unidad003/icons/cotizacion.png")));
        itemCotizacion.setText("Cotizacion");
        itemCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCotizacionActionPerformed(evt);
            }
        });
        menuPracticas.add(itemCotizacion);

        itemProducto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/unidad003/icons/producto.png")));
        itemProducto.setText("Registro Producto");
        itemProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemProductoActionPerformed(evt);
            }
        });
        menuPracticas.add(itemProducto);
        menuPracticas.addSeparator();

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/unidad003/icons/salir.png")));
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuPracticas.add(itemSalir);

        barraMenu.add(menuPracticas);
        setJMenuBar(barraMenu);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemTerrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTerrenoActionPerformed
        // Si ya esta abierto, lo trae al frente en lugar de abrir otro.
        for (JInternalFrame abierto : escritorio.getAllFrames()) {
            if (abierto instanceof jifTerreno && !abierto.isClosed()) {
                try {
                    abierto.setIcon(false);
                    abierto.setSelected(true);
                } catch (PropertyVetoException ex) {
                    // Aunque no se pueda seleccionar, se puede traer al frente.
                }
                abierto.toFront();
                return;
            }
        }
        jifTerreno ventanaTerreno = new jifTerreno();
        escritorio.add(ventanaTerreno);
        ventanaTerreno.setLocation(24, 24);
        ventanaTerreno.setVisible(true);
        try {
            ventanaTerreno.setSelected(true);
        } catch (PropertyVetoException ex) {
            ventanaTerreno.toFront();
        }
    }//GEN-LAST:event_itemTerrenoActionPerformed

    private void itemCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCotizacionActionPerformed
        JOptionPane.showMessageDialog(this, "El formulario de Cotización todavía no está creado.");
    }//GEN-LAST:event_itemCotizacionActionPerformed

    private void itemProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemProductoActionPerformed
        JOptionPane.showMessageDialog(this, "El formulario de Registro Producto todavía no está creado.");
    }//GEN-LAST:event_itemProductoActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir de Mis Prácticas?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_itemSalirActionPerformed

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            jdgMenu ventana = new jdgMenu((java.awt.Frame) null, false);
            ventana.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenuItem itemCotizacion;
    private javax.swing.JMenuItem itemProducto;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemTerreno;
    private javax.swing.JMenu menuPracticas;
    // End of variables declaration//GEN-END:variables
}
