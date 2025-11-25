package ActiveRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne {
    private int id = -1;
    private String nom;
    private String prenom;

    public  Personne(int id, String nom, String prenom) {
        this.id ++;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static Personne findById(int id) throws SQLException, ClassNotFoundException {
        Connection connet = DBConnection.getConnection();
        PreparedStatement ps = connet.prepareStatement("SELECT * FROM personne WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Personne p = new Personne(id, rs.getString("nom"), rs.getString("prenom"));
        return p;
    }


    
}
