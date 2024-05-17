package xml;

import org.basex.core.*;
import org.basex.query.*;
import org.basex.query.iter.*;
import org.basex.query.value.item.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Ejemplo simple de consulta con BaseX
 *
 * @author BaseX Team 2005-24, BSD License
 */
public final class ConsultaSimpleBaseX {
    /** Database context. */
    static Context context = new Context();  // Contexto

    /**
     * Ejemplo de una consulta sencilla en XQuery.
     * @param args (ignored) command-line arguments
     * @throws IOException if an error occurs while serializing the results
     * @throws QueryException if an error occurs while evaluating the query
     * @throws BaseXException if a database command fails
     */
    public static void main(final String... args) throws IOException, QueryException {

        // Hacemos una consulta XQuery
        String variable = "nombre";
        String query =
                "for $x in doc('src/main/resources/xml/catalogo.xml')//" + variable + " return data($x)";

        try (QueryProcessor processor = new QueryProcessor(query, context)) {
            // Store the pointer to the result in an iterator:
            Iter iter = processor.iter();
            /* Nos llevamos los datos a un arrayList */
            ArrayList<String> listadoProductos = new ArrayList<String>();
            // Iterate through all items and serialize
            for (Item item; (item = iter.next()) != null; ) {
                //System.out.println("Item en java:" + item.toString());
                listadoProductos.add(item.toString());
            }

            System.out.println("Listado: " + listadoProductos);
        }

        // ------------------------------------------------------------------------
        // Flush output
        System.out.println();

    }
}
