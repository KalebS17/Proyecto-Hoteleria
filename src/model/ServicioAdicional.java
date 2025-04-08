package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ServicioAdicional {
    private int idServicio;
    private String nombre;
    private String descripcion;
    private double precio;
    
	public ServicioAdicional(int idServicio, String nombre, String descripcion, double precio) {
		super();
		this.idServicio = idServicio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public ServicioAdicional() {
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "ServicioAdicional [idServicio=" + idServicio + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", precio=" + precio + "]";
	}
	
	public List<String> getServicios(){
    	List<String> serviciosA = new ArrayList<>();
    	String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
    	
    	try {
    		
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		Connection conexion = DriverManager.getConnection(url);
    		
    		String consulta = "SELECT id_servicio, nombre, descripcion, precio FROM ServiciosAdicionales;";
    		Statement statement = conexion.createStatement();
    		ResultSet result = statement.executeQuery(consulta);
    		
    		while (result.next()) {
    			
    			int idServ = result.getInt("id_servicio");
    			String name = result.getString("nombre");
    			String desc = result.getString("descripcion");
    			double price = result.getDouble("precio");

    			
    			String habitacionInfo = String.format("ID: %d |  %s |  %s | $ %.2f |", idServ, name.toUpperCase(), desc, price);
    			serviciosA.add(habitacionInfo);
    			
    		}
    		
			result.close();
            statement.close();
            conexion.close();
    		
    		
    	} catch (ClassNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
    	} catch (SQLException exc) {
    		 JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
             System.out.print(exc.getMessage());
    	}
    	
    	return serviciosA;
    }
	
	
	//agregar servicio adicional:
	public static void agregarServicioAReserva(int idReserva, int idServicio, int cantidad) {
	    String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    
	    try (Connection conn = DriverManager.getConnection(url);
	         CallableStatement stmt = conn.prepareCall("{CALL InsertarReservaServicio(?, ?, ?)}")) {

	        stmt.setInt(1, idReserva);
	        stmt.setInt(2, idServicio);
	        stmt.setInt(3, cantidad);

	        stmt.execute();
	        JOptionPane.showMessageDialog(null, "Servicio agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al agregar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
    
    
    
}
