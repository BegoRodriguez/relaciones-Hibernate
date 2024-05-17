package dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import modelo.Cliente;
import modelo.Producto;
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
        sessionFactory = configuration.buildSessionFactory();
    }


    public Object findById(Class t, int id) {
        // Create Session Factory and auto-close with try-with-resources.

        // Inicializamos Objecto Sesión
        Session session = sessionFactory.openSession();
        Object ret = session.get(t, id);
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
}
