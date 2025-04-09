package model;

import java.sql.CallableStatement;
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
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class Cliente {
	private int idCliente;
	private String nombre;
	private String apellido;
	private String email;
	private int telefono;
	private int documentoIdentidad;
	
	public Cliente(int idCliente, String nombre, String apellido, String email, int telefono, int documentoIdentidad) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.documentoIdentidad = documentoIdentidad;
	}

	public Cliente() {
		
	}

	public int getId_cliente() {
		return idCliente;
	}

	public void setId_cliente(int id_cliente) {
		this.idCliente = id_cliente;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(int documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}
	
	@Override
	public String toString() {
		return "Cliente [id_cliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
				+ email + ", telefono=" + telefono + ", documentoIdentidad=" + documentoIdentidad + "]";
	}
	
	
	
	
	 //metodo para agregar cliente con nuestro stored procedure ---------------------------------------------------------------------------------------------------------
    public void agregarCliente(String nombre, String apellido, String email, String telefono, String cedula) {
    	
    	String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
    	
    	try (Connection conexion = DriverManager.getConnection(connectionURL)){
    		
    		String sp_sql = "{call sp_AgregarCliente(?, ?, ?, ?, ?)}"; //stored procedure
    		CallableStatement storedPrcd = conexion.prepareCall(sp_sql); // llamamos al stored procedure
    		
    		storedPrcd.setString(1, nombre);
    		storedPrcd.setString(2, apellido);
    		storedPrcd.setString(3, email);
    		storedPrcd.setString(4, telefono);
    		storedPrcd.setString(5, cedula);
    		
    		storedPrcd.execute();
    		
    		JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente, Refresca la pagina!");
    		
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Error al agregar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    
    
    //MOSTRAR CLIENTES (DATOS BASICOS)
    public List<String> mostrarClientesBasics() {
        List<String> clientes = new ArrayList<>();
        String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(connectionURL);

            // Crear la consulta SQL
            String sql = "SELECT id_cliente, nombre, apellido, documento_identidad FROM Clientes;";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                int idCliente = resultSet.getInt("id_cliente");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String documentoIdentidad = resultSet.getString("documento_identidad");

                // Formatear la información como una cadena
                String clienteInfo = String.format(
                    " %d |  %s,  %s, | %s  |",
                    idCliente, nombre, apellido, documentoIdentidad
                );
                clientes.add(clienteInfo);
            }

            // Cerrar la conexión
            resultSet.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (SQLException exc) {
            JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.out.print(exc.getMessage());
        }

        return clientes;
    }
    //
  
    
    
    
    //Metodo para mostrar todos los clientes y su info ------------------------------------------------------------------------------------------------------------------------
    public List<String> getTodosLosClientes() {
        List<String> clientes = new ArrayList<>();
        String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(connectionURL);

            // Crear la consulta SQL
            String sql = "SELECT id_cliente, nombre, apellido, email, telefono, documento_identidad FROM Clientes";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                int idCliente = resultSet.getInt("id_cliente");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");
                String documentoIdentidad = resultSet.getString("documento_identidad");

                // Formatear la información como una cadena
                String clienteInfo = String.format(
                    "ID: %d | Nombre: %s,  %s, | %s  | #: %s  | Cedula: %s",
                    idCliente, nombre, apellido, email, telefono, documentoIdentidad
                );
                clientes.add(clienteInfo);
            }

            // Cerrar la conexión
            resultSet.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (SQLException exc) {
            JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.out.print(exc.getMessage());
        }

        return clientes;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    
    
    
    
    public static void getRankingClientes(JTable tableReserv) {
        String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
        String query = "SELECT TOP 10 c.id_cliente, c.nombre, COUNT(r.id_reserva) AS total_reservas " +
                       "FROM Clientes c " +
                       "JOIN Reservas r ON c.id_cliente = r.id_cliente " +
                       "GROUP BY c.id_cliente, c.nombre " +
                       "ORDER BY total_reservas DESC";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Obtener el modelo de la tabla y limpiar datos previos
            DefaultTableModel model = (DefaultTableModel) tableReserv.getModel();
            model.setRowCount(0); // Limpia la tabla antes de cargar los nuevos datos

            // Llenar la tabla con los datos de la consulta
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                int totalReservas = rs.getInt("total_reservas");

                model.addRow(new Object[]{idCliente, nombre, totalReservas});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el ranking de clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
	
	//staticstics
    
    public static void getNamesRepes(JTextPane textPaneRepes) {
        List<String> stats = new ArrayList<>();
        String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        try (Connection conexion = DriverManager.getConnection(connectionURL);
             Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(
                 "SELECT nombre, apellido, COUNT(*) AS repeticiones " +  
                 "FROM Clientes " +                                      
                 "GROUP BY nombre, apellido " +                          
                 "HAVING COUNT(*) > 1")) {    

            boolean hayDuplicados = false;

            while (resultSet.next()) {
                hayDuplicados = true;
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int repeticiones = resultSet.getInt("repeticiones");

                // Formato: "Nombre Apellido (X repeticiones)"
                String contenido = "El Nombre: " + nombre + " " + apellido + " Se repite [" + repeticiones + "] Veces";

                // Establecer el texto en el JTextPane
                textPaneRepes.setContentType("text/plain");
                textPaneRepes.setText(contenido);
            }

            // Si no hay duplicados, mostrar mensaje
            if (!hayDuplicados) {
                textPaneRepes.setContentType("text/plain");
                textPaneRepes.setText("No hay nombres repetidos en la base de datos.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", 
                e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

	
	
}
