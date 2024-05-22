package modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** POJO (Plain Old Java Objet) para gestionar los productos en la aplicacion
 * @author brodf */

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    /**
     * Campo autoincremental entero en nuestra base de datos
     */

    @Id
    @GenericGenerator(name="incrementId",strategy="increment") @GeneratedValue(generator="incrementId")
    @Column(name = "producto_id") private int codigo;
    @Column(name = "nombre") private String nombre;
    @Column(name = "precio") private double precio;
    @Column(name = "categoria") private String categoria;
    @Column(name = "unidades") private int unidades;



    @ManyToMany(mappedBy = "productos")  // Como se llame el atributo en la clase que asociemos
    private List<Cliente> clientes = new ArrayList<>();

    /* Un proveedor tendrá varios productos así que aquí tenemos un único proveedor */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor") // Como se llame en la tabla de productos
    private Proveedor proveedor;

    /* Como es autoincremental no asignamos codigo para persistir en Hibernate */
    public Producto(String nombre, double precio, String categoria, int unidades, Proveedor id_proveedor) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.unidades = unidades;
        this.proveedor = id_proveedor;
    }

    public Producto(String nombre, double precio, String categoria, int unidades) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.unidades = unidades;
    }

    /* Constructor vacío necesario para realizar consultas en Hibernate */
    public Producto(){}

    /* Necesitamos este constructor para leer */
    public Producto(int codigo, String nombre, double precio, String categoria, int unidades) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.unidades = unidades;
    }

    public void addCliente(Cliente cliente) {
        //producto.addCliente(this); // En el producto añadimos a su lista este Cliente
        clientes.add(cliente);
    }


    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void removeCliente(Cliente cliente) {
       clientes.remove(cliente);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

}
