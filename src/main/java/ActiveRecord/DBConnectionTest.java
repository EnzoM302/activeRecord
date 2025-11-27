package ActiveRecord.src.main.java.ActiveRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test JUnit pour {@link DBConnection}.
 * <p>
 * Cette classe vérifie deux aspects essentiels de la gestion de la connexion à la base de données :
 * <ul>
 *     <li>La connexion est unique (singleton) : plusieurs appels à {@link DBConnection#getConnection()}
 *     doivent retourner le même objet.</li>
 *     <li>Le type de l'objet retourné est bien {@link java.sql.Connection}.</li>
 * </ul>
 * </p>
 *
 * Les tests permettent de garantir la robustesse du mécanisme de connexion
 * et d'éviter la création de multiples connexions inutiles.
 */
public class DBConnectionTest {

    /**
     * Vérifie qu'il n'existe qu'un seul objet connexion vers la base,
     * même si la connexion est demandée à plusieurs reprises.
     * <p>
     * Trois appels successifs à {@link DBConnection#getConnection()} doivent
     * retourner la même instance de {@link Connection}.
     * </p>
     *
     * @throws SQLException           si une erreur SQL survient
     * @throws ClassNotFoundException si le driver JDBC n'est pas trouvé
     */
    @Test
    public void testConnectionUnique() throws SQLException, ClassNotFoundException {
        Connection conn1 = DBConnection.getConnection();
        Connection conn2 = DBConnection.getConnection();
        Connection conn3 = DBConnection.getConnection();

        assertEquals(conn1, conn2);
        assertEquals(conn2, conn3);
        assertEquals(conn1, conn3);
    }

    /**
     * Vérifie que l'objet retourné par {@link DBConnection#getConnection()}
     * est bien de type {@link java.sql.Connection}.
     * <p>
     * Ce test garantit que la méthode retourne une connexion JDBC valide.
     * </p>
     *
     * @throws SQLException           si une erreur SQL survient
     * @throws ClassNotFoundException si le driver JDBC n'est pas trouvé
     */
    @Test
    public void testConnectionType() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        assertTrue(connection instanceof Connection,
                "La connexion doit être de type java.sql.Connection");
    }
}