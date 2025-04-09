package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Usuario {
	private int idUsuario;
	private String nombreUsuario;
	private String contrasenia;
	private String rol;
	
	public Usuario(int idUsuario, String nombreUsuario, String contrasenia, String rol) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.rol = rol;
	}

	public Usuario() {
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Habitacion [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasenia="
				+ contrasenia + ", rol=" + rol + "]";
	}
	
	
	public List<Object[]> getAllRegis() {
	    List<Object[]> registros = new ArrayList<>();
	    String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

	    try {
	        // Cargar el driver de SQL Server
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	        // Establecer la conexión
	        Connection conexion = DriverManager.getConnection(url);

	        // Consulta SQL para contar registros en cada tabla
	        String consulta = "SELECT 'Clientes' AS Tabla, COUNT(*) AS Total FROM Clientes "
	                + "UNION ALL "
	                + "SELECT 'Habitaciones', COUNT(*) FROM Habitaciones "
	                + "UNION ALL "
	                + "SELECT 'Pagos', COUNT(*) FROM Pagos "
	                + "UNION ALL "
	                + "SELECT 'Empleados', COUNT(*) FROM Empleados "
	                + "UNION ALL "
	                + "SELECT 'Usuarios', COUNT(*) FROM Usuarios "
	                + "UNION ALL "
	                + "SELECT 'Reservas', COUNT(*) FROM Reservas "
	                + "UNION ALL "
	                + "SELECT 'Reserva Servicios', COUNT(*) FROM Reserva_Servicio "
	                + "UNION ALL "
	                + "SELECT 'Servicios', COUNT(*) FROM ServiciosAdicionales;";

	        // Crear y ejecutar la consulta
	        Statement statement = conexion.createStatement();
	        ResultSet result = statement.executeQuery(consulta);

	        // Recorrer los resultados y agregarlos a la lista
	        while (result.next()) {
	            String tabla = result.getString("Tabla");
	            int total = result.getInt("Total");
	            registros.add(new Object[]{tabla, total});
	        }

	        // Cerrar recursos
	        result.close();
	        statement.close();
	        conexion.close();

	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException exc) {
	        JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
	        System.out.print(exc.getMessage());
	    }

	    return registros;
	}
	
	
	
	
	
	//cargar reporte mensual
	public static void cargarReporteMensual(JTable tableReport) {
	    String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    String query = "SELECT YEAR(fecha_pago) AS anio, MONTH(fecha_pago) AS mes, SUM(monto) AS ingresos_totales " +
	                   "FROM Pagos " +
	                   "GROUP BY YEAR(fecha_pago), MONTH(fecha_pago) " +
	                   "ORDER BY anio DESC, mes DESC";

	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
	        model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

	        while (rs.next()) {
	            int anio = rs.getInt("anio");
	            int mes = rs.getInt("mes");
	            double ingresos = rs.getDouble("ingresos_totales");

	            model.addRow(new Object[]{anio, mes, ingresos});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar el reporte mensual: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
}
