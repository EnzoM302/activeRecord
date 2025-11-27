package ActiveRecord.src.main.java.ActiveRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la classe {@link Personne}.
 * <p>
 * Cette classe vérifie les principales fonctionnalités de la classe Personne :
 * - Création et suppression de la table en base
 * - Sauvegarde d'instances de Personne
 * - Recherche de personnes par différents critères (tous, par ID, par nom)
 * </p>
 *
 * Les tests utilisent une base temporaire recréée avant chaque test
 * et supprimée après chaque exécution afin d'assurer l'indépendance des cas de test.
 */
public class TestPersonne {
    
    /**
     * Méthode exécutée avant chaque test.
     * <p>
     * Elle supprime la table Personne si elle existe, la recrée,
     * puis insère quatre personnes de test (Spielberg, Scott, Kubrick, Fincher).
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */

    
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        Personne.deleteTable();
        Personne.createTable();

        Personne p1 = new Personne(-1, "Spielberg", "Steven");
        Personne p2 = new Personne(-1, "Scott", "Ridley");
        Personne p3 = new Personne(-1, "Kubrick", "Stanley");
        Personne p4 = new Personne(-1, "Fincher", "David");

        p1.savePersonne();
        p2.savePersonne();
        p3.savePersonne();
        p4.savePersonne();

    }

    /**
     * Méthode exécutée après chaque test.
     * <p>
     * Elle supprime la table Personne afin de nettoyer la base
     * et garantir l'indépendance des tests.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */


    @AfterEach
    public void tearDown() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
    }


    /**
     * Teste la méthode {@link Personne#findAll()}.
     * <p>
     * Vérifie que toutes les personnes insérées en base sont correctement
     * retrouvées et que leur représentation textuelle correspond au résultat attendu.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */

    
    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = Personne.findAll();
        String res = "[[1/Spielberg/Steven], [2/Scott/Ridley], [3/Kubrick/Stanley], [4/Fincher/David]]";
        assertEquals(res, personnes.toString());
    }

    /**
     * Teste la méthode {@link Personne#findById(int)}.
     * <p>
     * Vérifie que la recherche par identifiant retourne bien la personne attendue
     * (Spielberg avec l'ID 1).
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */
    @Test
    public void testFindById() throws SQLException, ClassNotFoundException {
        Personne personne = Personne.findById(1);
        String res = "[1/Spielberg/Steven]";
        assertEquals(res, personne.toString());
    }

    /**
     * Teste la méthode {@link Personne#findByName(String)}.
     * <p>
     * Vérifie que la recherche par nom retourne bien la personne attendue
     * (Spielberg).
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */
    @Test
    public void testFindByIdInexistant() throws SQLException, ClassNotFoundException {
        Personne personne = Personne.findById(999);
        assertNull(personne);
    }

    @Test
    public void testFindByName() throws SQLException, ClassNotFoundException {
        Personne personne = Personne.findByName("Spielberg");
        String res = "[1/Spielberg/Steven]";
        assertEquals(res, personne.toString());
    }
}
