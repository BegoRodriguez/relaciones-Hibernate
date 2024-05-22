package org.example;

import dao.DAOGenerico;
import modelo.Cliente;
import modelo.Producto;
import modelo.Proveedor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static xml.ParseadorProductos.escribirXMLconDOM;
import static xml.ParseadorProductos.leerXMLConDOM;

public class MainPruebas {

    public static void main(String[] args) {

        DAOGenerico dao = new DAOGenerico();

        List<Producto> listaProductos = dao.getProductosDeCliente(1);
        Iterator<Producto> it = listaProductos.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            System.out.println(p.getCodigo()+ " "+ p.getNombre());
        }

        listaProductos = dao.getProductosDeProveedor(2);
        Iterator<Producto> it2 = listaProductos.iterator();
        while (it2.hasNext()) {
            Producto p = it2.next();
            System.out.println(p.getCodigo()+ " "+ p.getNombre());
        }

        List<Proveedor> listaProvedores = dao.findByNombre("Zara");
        //List<Proveedor> listaProvedores = dao.getProveedoresPorNombre("Zara");
        Iterator<Proveedor> it3 = listaProvedores.iterator();
        while (it3.hasNext()) {
            Proveedor p = it3.next();
            System.out.println(p.getIdProveedor()+ " "+ p.getNombre());
        }

        /* Como escribir en la relacion uno a muchos y muchos a muchos */
        Proveedor pr = (Proveedor) dao.findById(Proveedor.class,1);

        /* Uno a muchos - Añadimos el proveedor creado al producto y cuando escribamos en el producto se va a escribir el id
        correspondienten en la tabla */
        Producto p = new Producto("Armadura Arco Iris",259.99,"Ropa",20,pr);
        Cliente c = new Cliente("Odin 2","16171110P","Asgard");

        /* Muchos a muchos - Tenemos que ser cuidadosos en guardar la entidad relacionada con la tabla (clientes)
        tiene la anotación que relaciona con pedidos y añadir la otra primero */
        c.addProducto(p);
        dao.save(p); // Primero hay que guardar el producto si es nuevo
        dao.save(c); // Insrta en clientes y en pedidos

    }
}
