package org.example;

import dao.DAOGenerico;
import modelo.Cliente;
import modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static xml.ParseadorProductos.*;

public class MainPruebas {

    public static void main(String[] args) {
        ArrayList<Producto> productos = leerXMLConDOM();
        escribirXMLconDOM(productos);
        //persistirenBD(productos); //No quiero hacerlo cada vez solo una para llenar la BD

        /*DAOProducto daoProducto = new DAOProducto();
        // Ejemplo consulta
        System.out.println(daoProducto.findById(1).getNombre());
        System.out.println(daoProducto.findById(2).getNombre());

        // Ejemplo guardar y actualizar
        //daoProducto.save(new Producto("Camiseta de manga larga",29.99,"Ropa",8));
        Producto pu = daoProducto.findById(6);
        pu.setUnidades(6);
        daoProducto.update(pu);

        // Ejemplo listado
        List<Producto> listaProductos = daoProducto.findAll();
        Iterator<Producto> it = listaProductos.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            System.out.println(p.getCodigo()+ " "+ p.getNombre());
        } */

        DAOGenerico dao = new DAOGenerico();
        // Ejemplo genericos
        Cliente ccp = (Cliente) dao.findById(Cliente.class,1);

        Producto pg = (Producto) dao.findById(Producto.class,2);
        System.out.println("Con genericos :" + pg.getNombre());

        ccp.addProducto(pg);
        dao.update(ccp);

        // Ahora quiero que el cliente 1 pida productos 1 y 2 y guardarlos


    }
}
