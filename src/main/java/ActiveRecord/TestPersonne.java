package ActiveRecord;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {
    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = Personne.findAll();
        assertEquals(personnes.toString(),"[-1/Spielberg/Steven][0/Scott/Ridley][1/Kubrick/Stanley][2/Fincher/David]");

    }
}
