package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ConnectionSQL {
    
    // URL de conexión
    
    public void connectToDatabase() {

        try {
        	String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        	
        	
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(connectionURL);
            
            // Confirmar conexión exitosa
            System.out.println("¡Conexión exitosa a la Base de Datos!");
            
            // Cerrar la conexión
            conexion.close();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (SQLException exc) {
            JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.out.print(exc.getMessage());            
        }
    }
    
    
    
   
}

