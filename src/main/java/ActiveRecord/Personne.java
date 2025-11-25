package ActiveRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id = -1;
    private String nom;
    private String prenom;

    public  Personne(int id, String nom, String prenom) {
        this.id ++;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static ArrayList<Personne> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personne = new ArrayList<>();
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("SELECT * FROM personne");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personne.add(new Personne(id, nom, prenom));
        }
        return personne;
    }

    public static Personne findByName(String name) throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("SELECT * FROM personne where nom = ?");
        pst.setString(1,name);
        ResultSet rs = pst.executeQuery();
        return new Personne (rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
    }


}
