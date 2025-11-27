package ActiveRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;


public class Personne {
    private int id = -1;
    private String nom;
    private String prenom;

    public  Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public static Personne findById(int id) throws SQLException, ClassNotFoundException {
        Connection connet = DBConnection.getConnection();
        PreparedStatement ps = connet.prepareStatement("SELECT * FROM personne WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            Personne p = new Personne(id, rs.getString("nom"), rs.getString("prenom"));
            return p;
        }
        return null;
    }



    public static ArrayList<Personne> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = new ArrayList<>();
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("SELECT * FROM personne");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personnes.add(new Personne(id, nom, prenom));
        }
        return personnes;
    }

    public static Personne findByName(String name) throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("SELECT * FROM personne where nom = ?");
        pst.setString(1,name);
        ResultSet rs = pst.executeQuery();
        if (rs.next()){
            Personne p = new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
            return p;
        }
        return null;

    }


    public static void createTable() throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        String SQL = "CREATE TABLE IF NOT EXISTS personne (" +
                "id INT(11) NOT NULL AUTO_INCREMENT," +
                "nom VARCHAR(40) NOT NULL," +
                "prenom VARCHAR(40) NOT NULL," +
                "PRIMARY KEY (id)" +
                ")";
        try (Statement statement = dbc.createStatement()) {
            statement.executeUpdate(SQL);
        }
    }

    public static void deleteTable() throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        Statement statement = dbc.createStatement();
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        statement.executeUpdate("DROP TABLE IF EXISTS personne");
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
    }




    public static ArrayList<Personne>getPersonne() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personne = findAll();
        return personne;
    }
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public String toString() {
        return "[" + this.id + "/" + this.nom + "/" + this.prenom + "]";
    }


    public void delete() throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("DELETE FROM personne WHERE id = ?");
        pst.setInt(1, this.id);
        pst.executeUpdate();
        this.id = -1;
    }

    public void savePersonne() throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        PreparedStatement pst = dbc.prepareStatement("INSERT INTO personne (nom, prenom) VALUES (?, ?)");
        pst.setString(1, this.nom);
        pst.setString(2, this.prenom);
        pst.executeUpdate();
    }

}
