package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class OracleConnection {
    private final String DRIVER = "oracle.jdbc.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private final String USER = "XAVIER";
    private final String PASSWORD = "1234";
    
    public Connection cadena;
    
    public OracleConnection() {
        this.cadena = null;
    }
    
    public Connection connect(){
        try{
            Class.forName(DRIVER);
            this.cadena = DriverManager.getConnection(URL, USER, PASSWORD);
            
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.cadena;
    }
}
