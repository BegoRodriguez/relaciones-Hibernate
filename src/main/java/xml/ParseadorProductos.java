package xml;

import modelo.Producto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ParseadorProductos {



    public static ArrayList<Producto> leerXMLConDOM(){
        // Definimos un array de productos por si queremos trabajar con ellos en nuestra app
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try {
            File file = new File("./src/main/resources/xml/productos.xml"); // Ruta al archivo XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Normalizar el documento (opcional)
            doc.getDocumentElement().normalize();

            // Obtener el nodo raíz
            Element root = doc.getDocumentElement();

            /* Estructura de la tienda
            <tienda>
                <producto codigo="P001">
                    <nombre>Camiseta de manga corta</nombre>
                    <descripcion>Camiseta de algodón para hombre, color negro</descripcion>
                    <precio>19.99</precio>
                    <categoria>Ropa</categoria>
                    <disponibilidad>En stock</disponibilidad>
                    <unidades>10</unidades>
                    <imagen>https://ejemplo.com/imagen1.jpg</imagen>
                </producto>
              </tienda> */

            // Obtener los nodos "productos"
            NodeList nodeList = root.getElementsByTagName("producto");
            System.out.println("Número de productos: " + nodeList.getLength());


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) { // Comprobamos que el nodo sea un elemento
                    Element producto = (Element) node;

                    System.out.println("\nProducto id: " + producto.getAttribute("codigo"));
                    System.out.println("Nombre: " + producto.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Precio: " + producto.getElementsByTagName("precio").item(0).getTextContent());
                    System.out.println("Categoria: " + producto.getElementsByTagName("categoria").item(0).getTextContent());
                    System.out.println("Unidades: " + producto.getElementsByTagName("unidades").item(0).getTextContent());

                    /* Si queremos guardar los productos en una clase en nuestro programa */
                    int codigo = Integer.parseInt(producto.getAttribute("codigo"));
                    String nombre = producto.getElementsByTagName("nombre").item(0).getTextContent();
                    double precio = Double.parseDouble(producto.getElementsByTagName("precio").item(0).getTextContent());
                    String categoria = producto.getElementsByTagName("categoria").item(0).getTextContent();
                    int unidades = Integer.parseInt(producto.getElementsByTagName("unidades").item(0).getTextContent());
                    productos.add(new Producto(codigo,nombre,precio,categoria,unidades));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * <p>Esta es una descripcion de un metodo
     * <a href="https://www.discoduroderoer.es/crear-xml-en-java">Enlace a la web de referencia</a>
     * </p>
     *
     * @param productos la lista de productos a parsear
     * @return devuelve un 1 si se ha escrito el archivo correctamente
     * @see <a href="https://www.discoduroderoer.es/crear-xml-en-java">Otra web</a>
     */

    public static int escribirXMLconDOM(ArrayList<Producto> productos) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Crear elementos y atributos según tus necesidades
            Element rootElement = document.createElement("tienda");

            Iterator<Producto> it = productos.iterator();
            while (it.hasNext()) {
                Producto p = it.next();
                Element productoElement = document.createElement("producto");
                productoElement.setAttribute("codigo", ""+p.getCodigo());

                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(p.getNombre()));
                //++nombre.appendChild(document.createTextNode("\r"));
                productoElement.appendChild(nombre);  // Nombre es hijo de producto


                Element precio = document.createElement("precio");
                precio.setTextContent(""+p.getPrecio());
                productoElement.appendChild(precio);  // Precio es hijo de producto

                Element categoria = document.createElement("categoria");
                categoria.setTextContent(p.getCategoria());
                productoElement.appendChild(categoria);  // Categoria es hijo de producto

                Element unidades = document.createElement("unidades");
                unidades.setTextContent(""+p.getUnidades());
                productoElement.appendChild(unidades);  //  Unidades es hijo de producto

                rootElement.appendChild(productoElement); // Producto es hijo de tienda
            }
            document.appendChild(rootElement);

            // Guardar el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("./src/main/resources/xml/catalogo.xml");
            transformer.transform(source, result);

            System.out.println("Archivo XML creado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void persistirenBD(ArrayList<Producto> productos) {

        // Create Configuration
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Producto.class);

        // Create Session Factory and auto-close with try-with-resources.
        try (SessionFactory sessionFactory
                     = configuration.buildSessionFactory()) {

            // Initialize Session Object
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Iterator<Producto> it = productos.iterator();
            while (it.hasNext()) {
                Producto p = it.next();
                session.persist(new Producto(p.getNombre(), p.getPrecio(), p.getCategoria(), p.getUnidades()));
            }

            session.getTransaction().commit();

        }
    }

}
