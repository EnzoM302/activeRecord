package ActiveRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBConnectionTest {

    // test qui vérifier qu'il n'existe qu'un seul objet connexion vers la base même si la connexion est demandée à plusieurs reprises
    @Test  
    public void testConnectionUnique() throws SQLException, ClassNotFoundException {
        Connection conn1 = DBConnection.getConnection();
        Connection conn2 = DBConnection.getConnection();
        Connection conn3 = DBConnection.getConnection();

        assertEquals(conn1, conn2);
        assertEquals(conn2, conn3);
        assertEquals(conn1, conn3);

    }

    // test qui verifie que type de la connexion soit bien java.sql.Connection
    @Test
    public void testConnectionType() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        assertTrue(connection instanceof Connection,"La connexion doit être de type java.sql.Connection");
    }


}

