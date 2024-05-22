package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Producto;
import modelo.Proveedor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.descriptor.java.ObjectJavaType;

import java.util.List;

/** Patrón de diseño DAO (objeto de acceso a datos). Capa de aplicación responsable de acceder a los datos */

public class DAOGenerico {

    SessionFactory sessionFactory;
    Configuration configuration;

    public DAOGenerico(){

        // Create Configuration
        configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ///****** Ojo!!!! Aquí tendremos que añadir tantas clases como tengamos en la app
        configuration.addAnnotatedClass(Cliente.class);
        configuration.addAnnotatedClass(Producto.class);
        configuration.addAnnotatedClass(Proveedor.class);
        sessionFactory = configuration.buildSessionFactory();
    }


    public Object findById(Class t, int id) {
        // Create Session Factory and auto-close with try-with-resources.

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        return session.get(t, id);
    }

    public void save(Object objeto) {

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(objeto);
        tx1.commit();
        session.close();
    }

    public void update(Object objeto) {

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(objeto); // En lugar de update (para many to many)
        tx1.commit();
        session.close();

    }

    public void delete(Object objeto) {

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(objeto);
        tx1.commit();
        session.close();

    }

    public <T> List<T> findAll(Class t) {

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(t);
        criteria.from(t);

        return (List<T>) session.createQuery(criteria).getResultList();

    }

    /** Conseguir todos los productos de un cliente
     * @see: <a hRef="https://www.baeldung.com/jpa-join-types">Enlace del que he tomado la información</a>
     * */
    public List<Producto> getProductosDeCliente (int clienteId) {

        Session session = sessionFactory.openSession();
        TypedQuery<Producto> query
                = session.createQuery(
                "SELECT c.productos FROM Cliente c WHERE c.id_cliente = :clienteId ", Producto.class);

        query.setParameter("clienteId", clienteId);
        return query.getResultList();
    }

    /** Conseguir todos los productos de un proveedor */
    public List<Producto> getProductosDeProveedor (int proveedorId) {

        Session session = sessionFactory.openSession();

        TypedQuery<Producto> query
                = session.createQuery(
                "SELECT p.productos FROM Proveedor p WHERE p.idProveedor = :proveedorId ", Producto.class);
        /* Ojo!!! el idProveedor el mismo que en la clase;*/
        query.setParameter("proveedorId", proveedorId);
        return query.getResultList();
    }

    public List<Proveedor> getProveedoresPorNombre (String nombre) {

        Session session = sessionFactory.openSession();

        TypedQuery<Proveedor> query
                = session.createQuery(
                "SELECT p FROM Proveedor p WHERE p.nombre = :nombre ", Proveedor.class);
        query.setParameter("nombre", nombre);
        return query.getResultList();
    }

    public List<Proveedor> findByNombre(String nombre) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Proveedor> criteria = builder.createQuery(Proveedor.class);
        Root<Proveedor> root = criteria.from(Proveedor.class);
        criteria.select(root).where(builder.equal(root.get("nombre"), nombre));

        List<Proveedor> proveedors = session.createQuery(criteria).getResultList();

        session.close();

        return proveedors;
    }


}
