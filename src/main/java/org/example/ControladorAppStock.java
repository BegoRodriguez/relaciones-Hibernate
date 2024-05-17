package org.example;

import dao.DAOGenerico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ControladorAppStock {

    public ControladorAppStock(JFrame vista, DAOGenerico dao) throws ParseException {

        JLabel titulo = new JLabel("APLICACION STOCK");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel botonera = new JPanel();
        JButton modificarDatos = new JButton("Modificar Datos");
        JButton pedirProductosAProveedor = new JButton("Pedir Productos a Proveedor");
        JButton gestionarVentaACliente = new JButton("Gestionar Venta a Cliente");

        botonera.add(modificarDatos);
        botonera.add(pedirProductosAProveedor);
        botonera.add(gestionarVentaACliente);

        modificarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorCRUD.createAndShowGUI(dao);
            }
        });

        pedirProductosAProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ControladorCRUD.createAndShowGUI();

            }
        });

        gestionarVentaACliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        /******* MENU *********/
        JMenuBar conversor = new JMenuBar();
        JMenu cv = new JMenu("Opciones Menú");
        JMenuItem convertirADolares = new JMenuItem("Convertir a Dolares");
        JMenuItem convertirAEuros = new JMenuItem("Convertir a Euros");
        JMenuItem abrirArchivo = new JMenuItem("Abrir archivo");
        cv.add(convertirADolares);
        cv.add(convertirAEuros);
        cv.add(abrirArchivo);
        conversor.add(cv);
        vista.setJMenuBar(conversor);

        // Eventos del menú
        convertirADolares.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    JFrame vista2 = new JFrame("Calculadora");
                    ControladorPedidos cr = new ControladorPedidos(vista2,modelo);
                    //vista2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    vista2.pack();
                    vista2.setVisible(true);
                    vista2.setLocationRelativeTo(null);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }*/
            }
        });

        convertirAEuros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        abrirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                int returnVal2 = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION){}
            }
        });

        JPanel todo = new JPanel();
        todo.setLayout(new BoxLayout(todo,BoxLayout.PAGE_AXIS));
        //todo.add(toolBar);
        /********** Margen ****************************/
        todo.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen de 20 píxeles en todos los lados
        todo.add(Box.createVerticalGlue());
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes
        todo.add(titulo);
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes
        todo.add(botonera);
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes

        vista.add(todo);

    }

    public static void main(String[] args) throws ParseException {
        JFrame vista = new JFrame("Aplicación Gestión Stock");
        //Calculadora modelo = new Calculadora();
        DAOGenerico dao = new DAOGenerico();

        new ControladorAppStock(vista,dao);
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.pack();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);

    }
}
