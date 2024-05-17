package modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** POJO (Plain Old Java Objet) para gestionar los clientes en la aplicacion
 * @author brodf */

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GenericGenerator(name="incrementId",strategy="increment") @GeneratedValue(generator="incrementId")
    @Column(name = "cliente_id") private int id_cliente;
    @Column(name = "nombre") private String nombre;
    @Column(name = "nif") private String nif;
    @Column(name = "direccion") private String direccion;

    @ManyToMany
    @JoinTable(
            name = "pedidos",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos = new ArrayList<>();   /* Un cliente puede realizar varios pedidos de diferentes productos,
    y un producto puede estar en varios pedidos de diferentes clientes */

    /* Como es autoincremental no asignamos codigo para persistir en Hibernate */
    public Cliente(String nombre, String nif, String direccion) {
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
    }

    /* Constructor vacío necesario para realizar consultas en Hibernate */
    public Cliente(){}

    /* Necesitamos este constructor para leer del XML */
    public Cliente(int id_cliente, String nombre, double precio, String categoria, int unidades) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto) {
        //producto.addCliente(this); // En el producto añadimos a su lista este Cliente
        productos.add(producto);
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getCodigo() {
        return id_cliente;
    }

    public void setCodigo(int codigo) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
