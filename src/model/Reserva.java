package model;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Reserva {
	private int idReserva;
	private int idCliente;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String estado;
	private double total;
	private int idHabitacion;
	
	public Reserva(int idReserva, int idCliente, Date fechaEntrada, Date fechaSalida, String estado, double total,
			int idHabitacion) {
		super();
		this.idReserva = idReserva;
		this.idCliente = idCliente;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.estado = estado;
		this.total = total;
		this.idHabitacion = idHabitacion;
	}

	public Reserva() {
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	
	
	
	
	//MOSTRAR RESERVAS CONFIRMADAS
	
	public static List<String[]> getReservasC() {
	    List<String[]> reservasConf = new ArrayList<>();
	    String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

	    try {
	        // Cargar el driver JDBC
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	        // Establecer la conexión
	        Connection conexion = DriverManager.getConnection(connectionURL);

	        // Crear la consulta SQL
	        String sql = "select id_reserva, id_cliente, id_habitacion, fecha_entrada, fecha_salida, id_empleado, total FROM Reservas WHERE estado = 'Confirmada';";
	        Statement statement = conexion.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);

	        // Procesar los resultados
	        while (resultSet.next()) {
	            int idReserva = resultSet.getInt("id_reserva");
	            int idCliente = resultSet.getInt("id_cliente");
	            int idHabitacion = resultSet.getInt("id_habitacion");
	            int idEmpleado = resultSet.getInt("id_empleado");
	            String fechaIn = resultSet.getString("fecha_entrada");
	            String fechaOut = resultSet.getString("fecha_salida");
	            double total = resultSet.getDouble("total");
	            
	            // Crear un array con los datos de la reserva
	            String[] reserva = {
	                String.valueOf(idReserva),
	                String.valueOf(idCliente),
	                String.valueOf(idHabitacion),
	                String.valueOf(idEmpleado),
	                fechaIn,
	                fechaOut,
	                String.valueOf(total)
	            };
	            
	            // Añadir a la lista
	            reservasConf.add(reserva);
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

	    return reservasConf;
	}
	
	//
	
	
	
	//ELIMINAR UNA RESERVA
	
	public static boolean cancelarReserva(int idReserva) {
	    String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    
	    try {
	        // Cargar el driver JDBC
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        
	        // Establecer la conexión
	        Connection conexion = DriverManager.getConnection(connectionURL);
	        
	        // Crear la consulta SQL para actualizar en lugar de eliminar
	        String sql = "UPDATE Reservas SET estado = 'Cancelada' WHERE id_reserva = ?";
	        
	        PreparedStatement statement = conexion.prepareStatement(sql);
	        statement.setInt(1, idReserva);
	        
	        // Ejecutar la consulta
	        int filasAfectadas = statement.executeUpdate();
	        
	        // Cerrar recursos
	        statement.close();
	        conexion.close();
	        
	        // Retornar true si se actualizó correctamente
	        return filasAfectadas > 0;
	        
	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, 
	                                     "Error al cargar el Driver de SQL - JDBC", 
	                                     e.getMessage(), 
	                                     JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException exc) {
	        JOptionPane.showMessageDialog(null, 
	                                     "Error en la conexión a la Base de Datos", 
	                                     exc.getMessage(), 
	                                     JOptionPane.ERROR_MESSAGE);
	        System.out.print(exc.getMessage());
	    }
	    
	    return false;
	}

	
	
	//
	
	
	
	
	//VALIDAR DATOS DE LA RESERVA
	public static boolean validarCampos(JTextField tfClienteID, JTextField tfHabitacionID, JTextField tfDateIn, JTextField tfDateOut) {
        // Validar que no haya campos vacíos
        if (tfClienteID.getText().trim().isEmpty() || tfHabitacionID.getText().trim().isEmpty() ||
            tfDateIn.getText().trim().isEmpty() || tfDateOut.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que ClienteID y HabitacionID sean números
        try {
            int clienteID = Integer.parseInt(tfClienteID.getText().trim());
            int habitacionID = Integer.parseInt(tfHabitacionID.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ClienteID y HabitacionID deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar formato de fechas (YYYY-MM-dd)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // No permitir fechas inválidas como 2023-02-30

        try {
            Date fechaIn = sdf.parse(tfDateIn.getText().trim());
            Date fechaOut = sdf.parse(tfDateOut.getText().trim());

            // Validar que fechaIn sea menor que fechaOut
            if (fechaIn.after(fechaOut)) {
                JOptionPane.showMessageDialog(null, "La fecha de entrada debe ser menor que la fecha de salida.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "El formato de las fechas debe ser YYYY-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Si todas las validaciones pasan
        return true;
    }
	
	
	
	
	
	//METODO PARA CREAR LA RESERVA-------------------------------------------------------------------------

	public int crearReserva(int clienteID, int habitacionID, String fechaIn, String fechaOut, int idEmpleado, String estadoReserva) {
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int idReserva = -1;
		
		try {
			
			String connectionUrl = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	        conn = DriverManager.getConnection(connectionUrl);
		
		// Validar datos de entrada

		if (fechaIn == null || fechaIn.isEmpty() || fechaOut == null || fechaOut.isEmpty()) {
		JOptionPane.showMessageDialog(null, "Las fechas no pueden estar vacías", 
		                           "Error de validación", JOptionPane.ERROR_MESSAGE);
		return -1;
		}
		
		// Convertir strings de fecha a objetos Date
		java.sql.Date sqlFechaIn = null;
		java.sql.Date sqlFechaOut = null;
		
		try {
		// Convertir String a Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilFechaIn = sdf.parse(fechaIn);
		java.util.Date utilFechaOut = sdf.parse(fechaOut);
		
		// Convertir util.Date a sql.Date
		sqlFechaIn = new java.sql.Date(utilFechaIn.getTime());
		sqlFechaOut = new java.sql.Date(utilFechaOut.getTime());
		
		// Validar que la fecha de entrada sea anterior a la de salida
		if (sqlFechaIn.compareTo(sqlFechaOut) >= 0) {
		   JOptionPane.showMessageDialog(null, "La fecha de entrada debe ser anterior a la fecha de salida", 
		                               "Error de validación", JOptionPane.ERROR_MESSAGE);
		   return -1;
		}
		
		// Validar que la fecha de entrada no sea anterior a la fecha actual
		java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
		if (sqlFechaIn.compareTo(hoy) < 0) {
		   JOptionPane.showMessageDialog(null, "La fecha de entrada no puede ser anterior a hoy", 
		                               "Error de validación", JOptionPane.ERROR_MESSAGE);
		   return -1;
		}
		} catch (ParseException e) {
		JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use YYYY-MM-DD", 
		                           "Error de formato", JOptionPane.ERROR_MESSAGE);
		return -1;
		}
		
		// Obtener el precio de la habitación
		ps = conn.prepareStatement("SELECT precio_noche FROM Habitaciones WHERE id_habitacion = ?");
		ps.setInt(1, habitacionID);
		rs = ps.executeQuery();
		
		if (!rs.next()) {
		JOptionPane.showMessageDialog(null, "No se encontró la habitación con ID: " + habitacionID, 
		                           "Error", JOptionPane.ERROR_MESSAGE);
		return -1;
		}
		
		BigDecimal precioPorNoche = rs.getBigDecimal("precio_noche");
		
		// Calcular el número de noches
		long diferenciaMillis = sqlFechaOut.getTime() - sqlFechaIn.getTime();
		int numNoches = (int) (diferenciaMillis / (1000 * 60 * 60 * 24));
		
		// Calcular el total
		BigDecimal total = precioPorNoche.multiply(new BigDecimal(numNoches));
		
		// Mostrar confirmación con detalles de la reserva
		int confirmacion = JOptionPane.showConfirmDialog(null,
		"Detalles de la reserva:\n\n" +
		"Cliente ID: " + clienteID + "\n" +
		"Habitación ID: " + habitacionID + "\n" +
		"Fecha de entrada: " + fechaIn + "\n" +
		"Fecha de salida: " + fechaOut + "\n" +
		"Número de noches: " + numNoches + "\n" +
		"Precio por noche: $" + precioPorNoche + "\n" +
		"Total: $" + total + "\n\n" +
		"¿Desea confirmar esta reserva?",
		"Confirmar Reserva", JOptionPane.YES_NO_OPTION);
		
		if (confirmacion != JOptionPane.YES_OPTION) {
		return -1; // Usuario canceló la operación
		}
		
		// Llamar al stored procedure
		cs = conn.prepareCall("{CALL sp_CrearReserva(?, ?, ?, ?, ?, ?, ?)}");
		cs.setInt(1, clienteID);
		cs.setInt(2, habitacionID);
		cs.setDate(3, sqlFechaIn);
		cs.setDate(4, sqlFechaOut);
		cs.setInt(5, idEmpleado);
		cs.setString(6, estadoReserva);
		cs.setBigDecimal(7, total);
		
		// Ejecutar y obtener resultado
		rs = cs.executeQuery();
		
		if (rs.next()) {
		idReserva = rs.getInt("id_reserva");
		JOptionPane.showMessageDialog(null, "Reserva creada exitosamente.\n" +
		                           "ID de Reserva: " + idReserva +
		                           "\nTotal: $" + total,
		                           "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
		}
		
		} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Error al crear la reserva: " + e.getMessage(), 
		                       "Error SQL", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		} finally {
		// Cerrar todos los recursos
		try {
		if (rs != null) rs.close();
		if (ps != null) ps.close();
		if (cs != null) cs.close();
		if (conn != null) conn.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		}
		
		return idReserva;
		}
	
    
	
	
	//satisctics
	public static void cargarReserva(JTable tableRsrv) {
	    String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    String query = "SELECT h.numero AS habitacion, c.nombre AS cliente, r.fecha_entrada, r.fecha_salida, p.monto " +
	                   "FROM Reservas r " +
	                   "JOIN Habitaciones h ON r.id_habitacion = h.id_habitacion " +
	                   "JOIN Clientes c ON r.id_cliente = c.id_cliente " +
	                   "LEFT JOIN Pagos p ON r.id_reserva = p.id_reserva;";

	    DefaultTableModel modelo = (DefaultTableModel) tableRsrv.getModel();
	    modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

	    try (Connection conexion = DriverManager.getConnection(connectionURL);
	         Statement statement = conexion.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        while (resultSet.next()) {
	            Object[] fila = {
	                resultSet.getInt("habitacion"),  
	                resultSet.getString("cliente"),  
	                resultSet.getDate("fecha_entrada"),  
	                resultSet.getDate("fecha_salida"),  
	                resultSet.getBigDecimal("monto") // Puede ser null si no hay pago
	            };
	            modelo.addRow(fila);
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al obtener datos de reservas: " + e.getMessage(), 
	                                      "Error de BD", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	public static void cargarDatosHabitaciones(JTable tableHabUp, JTable tableHabDwn) {
		
		String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
		
        try (Connection connection = DriverManager.getConnection(url)) {
            // Consulta para las 5 habitaciones más reservadas
            String queryTop5 = "SELECT TOP 5 h.id_habitacion, h.numero, COUNT(r.id_reserva) AS total_reservas " +
                               "FROM Habitaciones h " +
                               "LEFT JOIN Reservas r ON h.id_habitacion = r.id_habitacion " +
                               "GROUP BY h.id_habitacion, h.numero " +
                               "ORDER BY total_reservas DESC";
            
            // Consulta para las 5 habitaciones menos reservadas
            String queryBottom5 = "SELECT TOP 5 h.id_habitacion, h.numero, COUNT(r.id_reserva) AS total_reservas " +
                                  "FROM Habitaciones h " +
                                  "LEFT JOIN Reservas r ON h.id_habitacion = r.id_habitacion " +
                                  "GROUP BY h.id_habitacion, h.numero " +
                                  "ORDER BY total_reservas ASC";
            
            // Limpiar modelos de tabla existentes
            DefaultTableModel modelTop = (DefaultTableModel) tableHabUp.getModel();
            DefaultTableModel modelBottom = (DefaultTableModel) tableHabDwn.getModel();
            modelTop.setRowCount(0);
            modelBottom.setRowCount(0);
            
            // Ejecutar consulta para habitaciones más reservadas
            try (PreparedStatement pstmtTop = connection.prepareStatement(queryTop5);
                 ResultSet rsTop = pstmtTop.executeQuery()) {
                
                while (rsTop.next()) {
                    int idHabitacion = rsTop.getInt("id_habitacion");
                    int numero = rsTop.getInt("numero");
                    int totalReservas = rsTop.getInt("total_reservas");
                    
                    modelTop.addRow(new Object[]{idHabitacion, numero, totalReservas});
                }
            }
            
            // Ejecutar consulta para habitaciones menos reservadas
            try (PreparedStatement pstmtBottom = connection.prepareStatement(queryBottom5);
                 ResultSet rsBottom = pstmtBottom.executeQuery()) {
                
                while (rsBottom.next()) {
                    int idHabitacion = rsBottom.getInt("id_habitacion");
                    int numero = rsBottom.getInt("numero");
                    int totalReservas = rsBottom.getInt("total_reservas");
                    
                    modelBottom.addRow(new Object[]{idHabitacion, numero, totalReservas});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al cargar datos de habitaciones: " + ex.getMessage(), 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
	}
	
	
	
	//Buscar reserva por Nombre
	public static List<Object[]> buscarReservasXNombre(String nombreCliente) {
	    List<Object[]> resultados = new ArrayList<>();

	    try {
	        Connection conexion = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;");
	        CallableStatement cs = conexion.prepareCall("{call sp_BuscarReservasPorNombre(?)}");

	        cs.setString(1, nombreCliente);

	        ResultSet rs = cs.executeQuery();

	        while (rs.next()) {
	            Object[] fila = {
	                rs.getInt("id_cliente"),
	                rs.getString("nombre"),
	                rs.getString("apellido"),
	                rs.getInt("id_reserva"),
	                rs.getDate("fecha_entrada"),
	                rs.getDate("fecha_salida"),
	                rs.getBigDecimal("total"),
	                rs.getString("estado")  // <- Nuevo campo
	            };
	            resultados.add(fila);
	        }

	        rs.close();
	        cs.close();
	        conexion.close();

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al buscar reservas: " + e.getMessage());
	    }

	    return resultados;
	}


	
}
