package com.mycompany.cotizacion;
import javax.swing.*;
public class jdgMenu extends JFrame {
 public jdgMenu(){super("Cotizacion - Menú");setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(850,550);setLocationRelativeTo(null);setContentPane(new JDesktopPane());JMenuBar barra=new JMenuBar();JMenu practicas=new JMenu("Prácticas");JMenuItem cot=new JMenuItem("Cotización"),salir=new JMenuItem("Salir");cot.addActionListener(e->new jdlCotizacion(this,true).setVisible(true));salir.addActionListener(e->dispose());practicas.add(cot);practicas.addSeparator();practicas.add(salir);barra.add(practicas);setJMenuBar(barra);}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new jdgMenu().setVisible(true));}
}
