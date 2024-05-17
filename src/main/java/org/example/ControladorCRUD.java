package org.example;

import dao.DAOGenerico;
import modelo.Cliente;
import modelo.Producto;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class ControladorCRUD implements ItemListener {
    JPanel cards; // Un panel que usa CardLayout
    final static String BUSQUEDA_DE_UN_ELEMENTO = "Busqueda de un elemento";
    final static String MOSTRAR_LISTADO = "Mostrar toda la lista";
    final static String INSERCION_DE_UN_ELEMENTO = "Insertar un elemento";
    final static String MODIFICACION_DE_UN_ELEMENTO = "Modificar un elemento";
    final static String ELIMINACION_DE_UN_ELEMENTO = "Eliminar un elemento";

    final static String[] columnasClientes = {"Id","Nombre","Nif","Dirección"};
    final static String[] columnasProductos = {"Id","Nombre","Categoría","Precio","Unidades"};

    public void addComponentToPane(Container pane, DAOGenerico dao) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = {MOSTRAR_LISTADO, BUSQUEDA_DE_UN_ELEMENTO, INSERCION_DE_UN_ELEMENTO,MODIFICACION_DE_UN_ELEMENTO,ELIMINACION_DE_UN_ELEMENTO };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        String clases[] = {"Cliente", "Producto","Proveedor" };
        JComboBox cbClases = new JComboBox(clases);
        cbClases.setEditable(false);
        JButton buscar = new JButton("Buscar");

        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 40));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        JComboBox cbClases2 = new JComboBox(clases);
        cbClases2.setEditable(false);

        // Panel para la búsqueda de un elemento.
        JPanel card1 = new JPanel();
        card1.setLayout(new BoxLayout(card1,BoxLayout.PAGE_AXIS));
        JPanel aux = new JPanel();
        aux.add(cbClases);
        aux.add(buscar);
        card1.add(aux);
        //Add the scroll pane to this panel.
        card1.add(scrollPane); // La tabla (modificar luego para cada uno)

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel dtm = new DefaultTableModel();

                switch (cbClases.getSelectedItem().toString()) {
                    case "Cliente":  // Obtenemos el listado del cliente
                        List<Cliente> listaClientes = dao.findAll(Cliente.class);
                        Iterator<Cliente> it = listaClientes.iterator();

                        dtm.setColumnIdentifiers(columnasClientes);

                        while (it.hasNext()) {
                            Cliente c = it.next();
                            String[] cliente_temp = new String[4];
                            cliente_temp[0] = ""+ c.getCodigo();
                            cliente_temp[1] = c.getNombre();
                            cliente_temp[2] = c.getNif();

                            cliente_temp[3] = ""+ c.getDireccion();

                            dtm.addRow(cliente_temp);
                        }
                        table.setModel((TableModel) dtm);

                        break;
                    case "Producto": // Obtenemos el listado de productos

                        List<Producto> listaProductos = dao.findAll(Producto.class);
                        Iterator<Producto> it2 = listaProductos.iterator();

                        dtm.setColumnIdentifiers(columnasProductos);

                        while (it2.hasNext()) {
                            Producto p = it2.next();
                            String[] producto_temp = new String[5];
                            producto_temp[0] = ""+ p.getCodigo();
                            producto_temp[1] = p.getNombre();
                            producto_temp[2] = p.getCategoria();
                            producto_temp[3] = ""+ p.getPrecio();
                            producto_temp[4] = ""+ p.getUnidades();

                            dtm.addRow(producto_temp);

                        }
                        table.setModel((TableModel) dtm);

                        break;

                    case "Proveedor": // Dibujar línea

                        break;
                }
            }
        });

        /***************** Buscar por ID ************************/

        JTable table2 = new JTable();
        table2.setPreferredScrollableViewportSize(new Dimension(500, 10));
        table2.setFillsViewportHeight(true);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        JPanel card2 = new JPanel();
        card2.setLayout(new BoxLayout(card2,BoxLayout.PAGE_AXIS));
        JPanel aux2 = new JPanel();
        JButton buscarPorId = new JButton("Buscar por id");
        JTextField jid = new JTextField("Id",5);

        aux2.add(cbClases2);
        aux2.add(jid);
        aux2.add(buscarPorId);
        card2.add(aux2);
        card2.add(scrollPane2); // La tabla (modificar luego para cada uno)

        buscarPorId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel dtm = new DefaultTableModel();
                int id = Integer.parseInt(jid.getText());

                switch (cbClases2.getSelectedItem().toString()) {
                    case "Cliente":  // Obtenemos el listado del cliente
                        Cliente c = (Cliente) dao.findById(Cliente.class,id);
                        dtm.setColumnIdentifiers(columnasClientes);
                        String[] cliente_temp = new String[4];
                        cliente_temp[0] = ""+ c.getCodigo();
                        cliente_temp[1] = c.getNombre();
                        cliente_temp[2] = c.getNif();

                        cliente_temp[3] = ""+ c.getDireccion();

                        dtm.addRow(cliente_temp);
                        table2.setModel((TableModel) dtm);


                        break;

                    case "Producto":  // Obtenemos el listado del cliente

                        Producto p = (Producto) dao.findById(Producto.class,id);
                       // System.out.println("Has pulsado el botón buscar:" + p.getNombre());

                        dtm.setColumnIdentifiers(columnasProductos);
                        String[] producto_temp = new String[5];
                        producto_temp[0] = ""+ p.getCodigo();
                        producto_temp[1] = p.getNombre();
                        producto_temp[2] = p.getCategoria();
                        producto_temp[3] = ""+ p.getPrecio();
                        producto_temp[4] = ""+ p.getUnidades();

                        dtm.addRow(producto_temp);

                        table2.setModel((TableModel) dtm);

                        break;

                    case "Proveedor":  // Obtenemos el listado del cliente


                        break;

                }



            }
        });

        /* Insertar elementos */
        JPanel card3 = new JPanel();
      //  card3.setLayout(new BoxLayout(card3,BoxLayout.PAGE_AXIS));

        JComboBox cbClases3 = new JComboBox(clases);
        cbClases3.setEditable(false);
        cbClases3.setSize(20,5);

        /* Opciones que tengo, puedo desplegar otra vez???? */

       JLabel insert = new JLabel("Formulario");
       card3.add(cbClases3);
       card3.add(insert);


        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, MOSTRAR_LISTADO);
        cards.add(card2, BUSQUEDA_DE_UN_ELEMENTO);
        cards.add(card3, INSERCION_DE_UN_ELEMENTO);

        cards.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen de 20 píxeles en todos los lados
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI(DAOGenerico dao) {
        //Create and set up the window.
        JFrame frame = new JFrame("Controlador CRUD");

        //Create and set up the content pane.
        ControladorCRUD demo = new ControladorCRUD();
        demo.addComponentToPane(frame.getContentPane(),dao);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(800,300);
    }

}