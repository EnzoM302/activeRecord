package ActiveRecord;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
        Personne.createTable();

        Personne p1 = new Personne(-1, "Spielberg", "Steven");
        Personne p2 = new Personne(-1, "Scott", "Ridley");
        Personne p3 = new Personne(-1, "Kubrick", "Stanley");
        Personne p4 = new Personne(-1, "Fincher", "David");

        p1.save();
        p2.save();
        p3.save();
        p4.save();

    }

    @AfterEach
    public void tearDown() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
    }

    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = Personne.findAll();
        String res = "[[1/Spielberg/Steven], [2/Scott/Ridley], [3/Kubrick/Stanley], [4/Fincher/David]]";
        assertEquals(res, personnes.toString());
    }

    @Test
    public void testFindById() throws SQLException, ClassNotFoundException {
        Personne personne = Personne.findById(1);
        String res = "[1/Spielberg/Steven]";
        assertEquals(res, personne.toString());
    }

    @Test
    public void testFindByName() throws SQLException, ClassNotFoundException {
        Personne personne = Personne.findByName("Spielberg");
        String res = "[1/Spielberg/Steven]";
        assertEquals(res, personne.toString());
    }



}
