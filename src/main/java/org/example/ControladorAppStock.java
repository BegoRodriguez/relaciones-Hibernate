package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ControladorCalculadora {

    public ControladorCalculadora(JFrame vista,Calculadora modelo) throws ParseException {

        JLabel titulo = new JLabel("CALCULADORA");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelOperando1 = new JLabel("Operando 1");
        JTextField Operando1 = new JTextField(10);
        Operando1.setHorizontalAlignment(JTextField.CENTER);
        JPanel op1 = new JPanel();
        op1.add(labelOperando1);
        op1.add(Operando1);

        JLabel labelOperando2 = new JLabel("Operando 2");
        JTextField Operando2 = new JTextField(10);
        Operando2.setHorizontalAlignment(JTextField.CENTER);
        JPanel op2 = new JPanel();
        op2.add(labelOperando2);
        op2.add(Operando2);

        JPanel botonera = new JPanel();
        JButton suma = new JButton("Manejar clientes");
        JButton resta = new JButton("-");
        JButton multiplicacion = new JButton("*");
        JButton division = new JButton("/");

        botonera.add(suma);
        botonera.add(resta);
        botonera.add(multiplicacion);
        botonera.add(division);

        JLabel labelResultado = new JLabel("RESULTADO");
        //JTextField resultado = new JTextField(10);

        /* El "patrón" para el editor. Las # representan cifras. En la API puedes ver más. Ojo con el punto decimal, según el idioma puede ser una coma.*/

        MaskFormatter mascara = new MaskFormatter("###.###");
        // Se construye el JFormattedTextField pasándole la máscara
        JFormattedTextField resultado = new JFormattedTextField();
        resultado.setHorizontalAlignment(JTextField.CENTER);
        resultado.setColumns(10);
        resultado.setEditable(false);

        JPanel resul = new JPanel();
        resul.add(labelResultado);
        resul.add(resultado);

        suma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double Op1 = Double.parseDouble(Operando1.getText());
                double Op2 = Double.parseDouble(Operando2.getText());
                double res = modelo.suma(Op1,Op2);
                resultado.setValue(res);
            }
        });

        resta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double Op1 = Double.parseDouble(Operando1.getText());
                double Op2 = Double.parseDouble(Operando2.getText());
                double res = modelo.resta(Op1,Op2);
                resultado.setValue(res);
            }
        });

        multiplicacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double Op1 = Double.parseDouble(Operando1.getText());
                double Op2 = Double.parseDouble(Operando2.getText());
                double res = modelo.multiplacion(Op1,Op2);
                resultado.setValue(res);
            }
        });

        division.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double Op1 = Double.parseDouble(Operando1.getText());
                double Op2 = Double.parseDouble(Operando2.getText());
                if (Op2 == 0) {
                    JOptionPane.showMessageDialog(null,"No se puede dividir por cero","Alerta",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    double res = modelo.division(Op1, Op2);
                    resultado.setValue(res);
                }
            }
        });

        //JPanel botoneraConversion = new JPanel();
        //JButton convertirADolares = new JButton("convertirADolares");
        //JButton convertirAEuros = new JButton("convertirAEuros");
        //botoneraConversion.add(convertirADolares);
        //botoneraConversion.add(convertirAEuros);

        /******* MENU *********/
        JMenuBar conversor = new JMenuBar();
        JMenu cv = new JMenu("Conversor");
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
                double euros = Double.parseDouble(resultado.getText());
                double dolares = modelo.eurosADolares(euros);
                resultado.setValue(dolares);
            }
        });

        convertirAEuros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double dolares = Double.parseDouble(resultado.getText());
                double euros = modelo.dolaresAEuros(dolares);
                resultado.setValue(euros);

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

        /******* BARRA DE TAREAS *********/
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Llevar a una clase aparte si tengo tiempo
        ImageIcon imageIcon = new ImageIcon("./src/icons/dollar30.png"); // Load the image to an ImageIcon
        Image image = imageIcon.getImage(); // Transform it
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING); // Scale it smoothly
        imageIcon = new ImageIcon(newimg); // Transform it back
        JButton boton1 = new JButton(imageIcon);
        toolBar.add(boton1);



        ImageIcon imageIcon2 = new ImageIcon("./src/icons/euro20.png"); // Load the image to an ImageIcon
        JButton boton2 = new JButton(imageIcon2);
        toolBar.add(boton2);

        JPanel todo = new JPanel();
        todo.setLayout(new BoxLayout(todo,BoxLayout.PAGE_AXIS));
        todo.add(toolBar);
        /********** Margen ****************************/
        todo.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen de 20 píxeles en todos los lados
        todo.add(Box.createVerticalGlue());
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes
        todo.add(titulo);
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes
        todo.add(op1);
        todo.add(op2);
        todo.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre los componentes
        todo.add(botonera);
        todo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre los componentes
        todo.add(resul);
        //todo.add(botoneraConversion);

        vista.add(todo);

    }

    public static void main(String[] args) throws ParseException {
        JFrame vista = new JFrame("Calculadora");
        Calculadora modelo = new Calculadora();

        new ControladorCalculadora(vista,modelo);
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.pack();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);

    }
}
