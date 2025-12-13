package ActiveRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {

    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne personne) {
        this.titre = titre;
        this.id = -1;
        this.id_real = personne.getId();
    }

    private Film(int id, String titre, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {this.titre = titre;}

    public int getId(){
        return id;
    }

    public static Film findById(int id) throws SQLException, ClassNotFoundException {
        Connection connect = DBConnection.getConnection();
        String sql = "SELECT * FROM film WHERE id = ?";
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Film(rs.getInt("id"), rs.getString("titre"), rs.getInt("id_real"));
        }
        return null;
    }

    public Personne getRealisateur() throws SQLException, ClassNotFoundException {
        return Personne.findById(this.id_real);
    }

    public void save() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        if (this.id_real == -1) {
            throw new RealisateurAbsentException("Réalisateur non identifié en base (id_real = -1)");
        }

        Connection connect = DBConnection.getConnection();
        
        if (this.id == -1) {
            String sql = "INSERT INTO film (titre, id_real) VALUES (?, ?)";
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.titre);
            ps.setInt(2, this.id_real);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE film SET titre = ?, id_real = ? WHERE id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, this.titre);
            ps.setInt(2, this.id_real);
            ps.setInt(3, this.id);
            ps.executeUpdate();
        }
    }

    public void delete() throws SQLException, ClassNotFoundException {
        if (this.id != -1) {
            Connection connect = DBConnection.getConnection();
            PreparedStatement ps = connect.prepareStatement("DELETE FROM film WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            this.id = -1;
        }
    }

    public static void createTable() throws SQLException, ClassNotFoundException {
        Connection connect = DBConnection.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS film (" +
                "id INT(11) NOT NULL AUTO_INCREMENT, " +
                "titre VARCHAR(40) NOT NULL, " +
                "id_real INT(11) NOT NULL, " +
                "PRIMARY KEY (id))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(sql);
    }

    public static void deleteTable() throws SQLException, ClassNotFoundException {
        Connection connect = DBConnection.getConnection();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS film");
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException, ClassNotFoundException {
        ArrayList<Film> films = new ArrayList<>();
        if (p.getId() == -1) return films; 

        Connection connect = DBConnection.getConnection();
        String sql = "SELECT * FROM film WHERE id_real = ?";
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setInt(1, p.getId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            films.add(new Film(rs.getInt("id"), rs.getString("titre"), rs.getInt("id_real")));
        }
        return films;
    }




}
