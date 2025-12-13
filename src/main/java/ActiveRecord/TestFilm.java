package ActiveRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class  TestFilm {
    private Personne spielberg;
    private Personne scott;


    /**
     * Méthode exécutée avant chaque test.
     * <p>
     * Elle supprime les tables Film et Personne si elles existent, les recrée,
     * puis insère deux réalisateurs de test (Spielberg et Scott) pour les lier aux films.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        Film.deleteTable();
        Personne.deleteTable();

        Personne.createTable();
        Film.createTable();

        spielberg = new Personne(-1, "Spielberg", "Steven");
        spielberg.save();
        scott = new Personne(-1, "Scott", "Ridley");
        scott.save();
    }

    /**
     * Méthode exécutée après chaque test.
     * <p>
     * Elle supprime les tables Film et Personne afin de nettoyer la base
     * et garantir l'indépendance des tests.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     */
    @AfterEach
    public void tearDown() throws SQLException, ClassNotFoundException {
        Film.deleteTable();
        Personne.deleteTable();
    }


    /**
     * Teste la méthode {@link Film#findById(int)}.
     * <p>
     * Vérifie que la recherche par identifiant retourne bien le film attendu
     * (E.T. réalisé par Spielberg).
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     * @throws RealisateurAbsentException si le réalisateur n'est pas en base
     */
    @Test
    public void testFindById() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        Film f = new Film("E.T.", spielberg);
        f.save(); 
        
        f = Film.findById(f.getId());
        assertEquals("E.T.", f.getTitre());
    }


    /**
     * Teste la méthode {@link Film#getRealisateur()}.
     * <p>
     * Vérifie que l'on récupère correctement l'objet Personne (réalisateur)
     * associé à un film sauvegardé.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     * @throws RealisateurAbsentException si le réalisateur n'est pas en base
     */
    @Test
    public void testGetRealisateur() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        Film f = new Film("Alien", scott);
        f.save();
        
        Personne real = f.getRealisateur();
        assertEquals("Scott", real.getNom());
    }


    /**
     * Teste la méthode {@link Film#findByRealisateur(Personne)}.
     * <p>
     * Vérifie que la recherche des films par réalisateur retourne bien
     * la liste de tous les films associés à ce réalisateur.
     * </p>
     *
     * @throws SQLException              si une erreur SQL survient
     * @throws ClassNotFoundException    si le driver JDBC n'est pas trouvé
     * @throws RealisateurAbsentException si le réalisateur n'est pas en base
     */
    @Test
    public void testFindByRealisateur() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        Film f1 = new Film("Jurassic Park", spielberg);
        f1.save();
        Film f2 = new Film("Indiana Jones", spielberg);
        f2.save();
        
        ArrayList<Film> res = Film.findByRealisateur(spielberg);
        assertEquals(2, res.size());
    }


    /**
     * Teste la gestion des exceptions lors de la sauvegarde.
     * <p>
     * Vérifie que la sauvegarde d'un film échoue avec une {@link RealisateurAbsentException}
     * si le réalisateur n'a pas été préalablement sauvegardé en base (id = -1).
     * </p>
     */
    @Test
    public void testSaveAvecRealisateurNonSauvegarde() {
        Personne nouveau = new Personne(-1, "Nolan", "Christopher");
        
        Film f = new Film("Inception", nouveau);
        
        assertThrows(RealisateurAbsentException.class, () -> {f.save();});
    }

}
