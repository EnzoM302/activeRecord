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
        String res = "[[1/Spielberg/Steven], [2/Scott/Ridley], [3/Kubrick/Stanley], [4/Fincher/David]]";
        assertEquals(res, personnes.toString());
    }
}
