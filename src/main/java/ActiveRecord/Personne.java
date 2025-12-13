package ActiveRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;


public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public  Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public  Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {this.prenom = prenom;}


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

    public static ArrayList<Personne> findByName(String name) throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = new ArrayList<>();
        Connection dbc = DBConnection.getConnection();

        PreparedStatement pst = dbc.prepareStatement("SELECT * FROM personne where nom = ?");
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();

        while (rs.next()){
            personnes.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
        }
        return personnes;
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

        statement.executeUpdate("DROP TABLE IF EXISTS film");

        statement.executeUpdate("DROP TABLE IF EXISTS personne");
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

    public void save() throws SQLException, ClassNotFoundException {
        Connection dbc = DBConnection.getConnection();
        if (this.id == -1) {
            String sql = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
            PreparedStatement pst = dbc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, this.nom);
            pst.setString(2, this.prenom);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
            PreparedStatement pst = dbc.prepareStatement(sql);
            pst.setString(1, this.nom);
            pst.setString(2, this.prenom);
            pst.setInt(3, this.id);
            pst.executeUpdate();
        }
    }

}
