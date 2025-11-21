package ActiveRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String dbname;
    private String username;
    private String password;
    private String port;
    private String host;
    private Connection connect;
    private static DBConnection instance;

    private DBConnection(String dbname, String username, String password, String port, String host) {
        this.dbname = dbname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.host = host;
    }

    public static DBConnection getConnection(){
        if(instance == null){
            instance = new DBConnection("testpersonne", "root", "", "3306", "localhost");
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties connectionProps = new Properties();
            connectionProps.put("user", instance.username);
            connectionProps.put("password", instance.password);
            String urlDB = "jdbc:mysql://" + instance.host + ":";
            urlDB += instance.port + "/" + instance.dbname;
            System.out.println(urlDB);
            Connection connect = DriverManager.getConnection(urlDB, connectionProps);
            instance.connect = connect;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConnect(){
        return connect;
    }
}
