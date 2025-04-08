package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Empleado {
	private int idEmpleado;
	private int idSupervisor;
	private String nombre;
	private String apellido;
	private String cargo;
	private double salario;
	private String email;
	private String telefono;
	
	public Empleado(int idEmpleado, int idSupervisor, String nombre, String apellido, String cargo, double salario,
			String email, String telefono) {
		super();
		this.idEmpleado = idEmpleado;
		this.idSupervisor = idSupervisor;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cargo = cargo;
		this.salario = salario;
		this.email = email;
		this.telefono = telefono;
	}

	public Empleado() {
		super();
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public int getIdSupervisor() {
		return idSupervisor;
	}

	public void setIdSupervisor(int idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", idSupervisor=" + idSupervisor + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", cargo=" + cargo + ", salario=" + salario + ", email=" + email
				+ ", telefono=" + telefono + "]";
	}
	
	//METODO PARA MOSTRAR TODOS LOS EMPLEADOS----------------------------------------------------------------------------------------------------------------------------------------------------
	 public List<String> showEmployees(){
	    	List<String> employees = new ArrayList<>();
	    	String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    	
	    	try {
	    		
	    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    		Connection conexion = DriverManager.getConnection(url);
	    		
	    		String consulta = "SELECT e.id_empleado, e.nombre, e.apellido, e.cargo, e.salario, e.email, e.telefono, s.nombre AS nombre_supervisor, u.nombre_usuario, u.password FROM Empleados e LEFT JOIN Usuarios u ON e.id_usuario = u.id_usuario LEFT JOIN Empleados s ON e.id_supervisor = s.id_empleado;";
	    		Statement statement = conexion.createStatement();
	    		ResultSet result = statement.executeQuery(consulta);
	    		
	    		while (result.next()) {
	    			
	    			int idempleado = result.getInt("id_empleado");
	    			String name = result.getString("nombre");
	    			String lastname = result.getString("apellido");
	    			String cargo = result.getString("cargo");
	    			double salary = result.getDouble("salario");
	    			String mail = result.getString("email");
	    			String telefono = result.getString("telefono");
	    			String nameSuper = result.getString("nombre_supervisor");
	    			String userName = result.getString("nombre_usuario");
	    			String passW = result.getString("password");

	    			if (nameSuper == null) {
	                    nameSuper = "Es GERENTE"; // Valor por defecto si es NULL
	                }
	    			
	    			
	    			String empleadoInfo = String.format("ID: %d | %s  %s |  %s | $ %.2f |  %s |  %s | Supervisor: %s | User: %s | Pswrd: %s |",
                            idempleado, name, lastname, cargo, salary, mail, telefono, nameSuper, userName, passW);
	    			
	    			employees.add(empleadoInfo);
	    			
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
	    	
	    	return employees;
	    }
	 
	 
	 
	 
	 //METODO PARA MOSTRAR LOS EMPLEADOS POR JERARQUIA
	 public List<String> getEmployeeHierarchy() {
		    List<String> employees = new ArrayList<>();
		    String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
		    
		    // Consulta SQL que une empleados con usuarios y supervisores
		    String query = "SELECT e.id_empleado, e.nombre, e.apellido, e.cargo, e.salario, e.email, e.telefono, "
		                 + "ISNULL(s.nombre, 'Sin supervisor') AS nombre_supervisor, "
		                 + "u.nombre_usuario, u.password "
		                 + "FROM Empleados e "
		                 + "LEFT JOIN Usuarios u ON e.id_usuario = u.id_usuario "
		                 + "LEFT JOIN Empleados s ON e.id_supervisor = s.id_empleado;";

		    try (Connection conexion = DriverManager.getConnection(url);
		         Statement statement = conexion.createStatement();
		         ResultSet result = statement.executeQuery(query)) {

		        while (result.next()) {
		            int idEmpleado = result.getInt("id_empleado");
		            String nombre = result.getString("nombre");
		            String apellido = result.getString("apellido");
		            String cargo = result.getString("cargo");
		            double salario = result.getDouble("salario");
		            String email = result.getString("email");
		            String telefono = result.getString("telefono");  
		            String supervisor = result.getString("nombre_supervisor");
		            String usuario = result.getString("nombre_usuario");
		            String password = result.getString("password");

		            if (usuario == null) usuario = "Sin usuario";
		            if (password == null) password = "Sin contraseña";

		            String empleadoInfo = String.format("| ID: %d | %s %s | %s |$ $%.2f |  %s |  %s | Supervisor: %s | User: %s | Pswrd: %s |",
		                    idEmpleado, nombre, apellido, cargo, salario, email, telefono, supervisor, usuario, password);
		            
		            employees.add(empleadoInfo);
		        }
		    } catch (SQLException exc) {
		        JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
		        System.out.print(exc.getMessage());
		    }
		    return employees;
		}



	
}
