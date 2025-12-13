package ActiveRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String dbname = "testpersonne";
    private String username = "root";
    private String password ="";
    private String port = "3306";
    private String host = "localhost";
    private static Connection instance;

    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + host + ":";
        urlDB += port + "/" + dbname;
        System.out.println(urlDB);
        instance = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        if(instance == null){
            new DBConnection();
        }
        return instance;
    }

    public static synchronized void setNomDB(String nom) {
        if(nom != null && !nom.equals(dbname)){
            dbname = nom;
            instance = null;
        }
    }
}
