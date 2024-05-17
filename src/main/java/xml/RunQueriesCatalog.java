package xml;

import org.basex.core.*;
import org.basex.core.cmd.*;
import org.basex.io.serial.*;
import org.basex.query.*;
import org.basex.query.iter.*;
import org.basex.query.value.*;
import org.basex.query.value.item.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This example demonstrates three variants how XQuery expressions can be
 * evaluated.
 *
 * @author BaseX Team 2005-24, BSD License
 */
public final class RunQueriesCatalog {
    /** Database context. */
    static Context context = new Context();

    /**
     * Runs the example code.
     * @param args (ignored) command-line arguments
     * @throws IOException if an error occurs while serializing the results
     * @throws QueryException if an error occurs while evaluating the query
     * @throws BaseXException if a database command fails
     */
    public static void main(final String... args) throws IOException, QueryException {
        System.out.println("=== RunQueries ===");

        // Evaluate the specified XQuery
        String query =
                "for $x in doc('src/main/resources/xml/catalogo.xml')//producto return data($x)";
        /*String query =
                "for $x in doc('src/main/resources/xml/productos.xml')//nombre return data($x)";*/

        // Process the query by using the database command
        System.out.println("\n* Use the database command:");

        query(query);

        // Directly use the query processor
        System.out.println("\n* Use the query processor:");

        process(query);

        // Iterate through all query results
        System.out.println("\n* Serialize each single result:");

        serialize(query);

        // Iterate through all query results
        System.out.println("\n* Convert each result to its Java representation:");

        iterate(query);

        // Uncomment this line to see how erroneous queries are handled
        // iterate("for error s$x in . return $x");

        // ------------------------------------------------------------------------
        // Flush output
        System.out.println();


    }

    /**
     * This method evaluates a query by using the database command.
     * The results are automatically serialized and printed.
     * @param query query to be evaluated
     * @throws BaseXException if a database command fails
     */
    static void query(final String query) throws BaseXException {
        System.out.println(new XQuery(query).execute(context));
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The resulting items are passed on to a serializer.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     */
    static void process(final String query) throws QueryException {
        // Create a query processor
        try(QueryProcessor processor = new QueryProcessor(query, context)) {
            // Execute the query
            Value result = processor.value();

            // Print result as string.
            System.out.println(result);
        }
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The results are iterated one by one and converted to their Java
     * representation, using {{@link Item#toJava()}. This variant is especially
     * efficient if large result sets are expected.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     */
    static void iterate(final String query) throws QueryException {
        // Create a query processor
        try(QueryProcessor processor = new QueryProcessor(query, context)) {
            // Store the pointer to the result in an iterator:
            Iter iter = processor.iter();

            /* Nos llevamos los datos a un arrayList */
            ArrayList<String> productos = new ArrayList<String>();
            // Iterate through all items and serialize
            for(Item item; (item = iter.next()) != null;) {
                System.out.println(item.toJava());
                productos.add(item.toString());
            }

            System.out.println(productos);
        }
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The results are iterated one by one and passed on to an serializer.
     * This variant is especially efficient if large result sets are expected.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     * @throws IOException if an error occurs while serializing the results
     */
    static void serialize(final String query) throws QueryException, IOException {
        // Create a query processor
        try(QueryProcessor processor = new QueryProcessor(query, context)) {

            // Store the pointer to the result in an iterator:
            Iter iter = processor.iter();

            // Create a serializer instance
            try(Serializer ser = processor.serializer(System.out)) {
                // Iterate through all items and serialize contents
                for(Item item; (item = iter.next()) != null;) {
                    ser.serialize(item);
                }

            }
            System.out.println();
        }
    }
}