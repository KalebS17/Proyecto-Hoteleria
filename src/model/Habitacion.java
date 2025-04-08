package model;

import java.math.BigDecimal;
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

public class Habitacion {
    private int idHabitacion;
    private int numero;
    private String tipo;
    private int capacidad;
    private double precioNoche;
    private String estado;
    
	public Habitacion(int idHabitacion, int numero, String tipo, int capacidad, double precioNoche, String estado) {
		super();
		this.idHabitacion = idHabitacion;
		this.numero = numero;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.precioNoche = precioNoche;
		this.estado = estado;
	}
	
	public Habitacion() {
		
	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public double getPrecioNoche() {
		return precioNoche;
	}

	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Habitacion [idHabitacion=" + idHabitacion + ", numero=" + numero + ", tipo=" + tipo + ", capacidad="
				+ capacidad + ", precioNoche=" + precioNoche + ", estado=" + estado + "]";
	}
    
    
	
	
	
	 //Metodo para mostrar Habitaciones Disponibles(Reservas) -------------------------------------------------------------------------------------------------------------------
    public List<String> getHabitacionesDisponibles() {
        List<String> habitacionesDisponibles = new ArrayList<>();
        String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(connectionURL);

            // Crear la consulta SQL
            String sql = "SELECT id_habitacion, capacidad, precio_noche FROM Habitaciones WHERE estado = 'Disponible'";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                int idHabitacion = resultSet.getInt("id_habitacion");
                int capacidad = resultSet.getInt("capacidad");
                double precioNoche = resultSet.getDouble("precio_noche");

                // Formatear la información como una cadena
                String habitacionInfo = String.format("| ID de Habitacion: %d | Capacidad: %d | Precio: %.2f |", idHabitacion, capacidad, precioNoche);
                habitacionesDisponibles.add(habitacionInfo);
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

        return habitacionesDisponibles;
    }
    
    public List<String> getHabitaciones(){
    	List<String> habitacionesT = new ArrayList<>();
    	String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
    	
    	try {
    		
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		Connection conexion = DriverManager.getConnection(url);
    		
    		String consulta = "SELECT id_habitacion, numero, tipo, capacidad, precio_noche, estado FROM Habitaciones;";
    		Statement statement = conexion.createStatement();
    		ResultSet result = statement.executeQuery(consulta);
    		
    		while (result.next()) {
    			
    			int idHabitacion = result.getInt("id_habitacion");
    			int numHabitacion = result.getInt("numero");
    			String tipo = result.getString("tipo");
    			int capacity = result.getInt("capacidad");
    			double price = result.getDouble("precio_noche");
    			String estado = result.getString("estado");
    			
    			String habitacionInfo = String.format("|ID: %d |Num: %d |Tipo: %s |Capacidad: %d | $ %.2f | %s", idHabitacion, numHabitacion, tipo, capacity, price, estado);
    			habitacionesT.add(habitacionInfo);
    			
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
    	
    	return habitacionesT;
    }
    
    
    
    
    //METODO PARA ACTUALIZAR HABITACIONES
    public static void actualizarHabitacion(Connection conn, int idHabitacion, int versionActual) {
        try {
            // Primero obtenemos los datos actuales para mostrarlos como valores predeterminados
            String queryDatosActuales = "SELECT numero, tipo, capacidad, precio_noche, estado FROM Habitaciones WHERE id_habitacion = ?";
            PreparedStatement psDatosActuales = conn.prepareStatement(queryDatosActuales);
            psDatosActuales.setInt(1, idHabitacion);
            ResultSet rs = psDatosActuales.executeQuery();
            
            int numeroActual = 0;
            String tipoActual = "";
            int capacidadActual = 0;
            BigDecimal precioActual = BigDecimal.ZERO;
            String estadoActual = "";
            
            if (rs.next()) {
                numeroActual = rs.getInt("numero");
                tipoActual = rs.getString("tipo");
                capacidadActual = rs.getInt("capacidad");
                precioActual = rs.getBigDecimal("precio_noche");
                estadoActual = rs.getString("estado");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la habitación con ID: " + idHabitacion, 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Solicitar y validar número de habitación
            Integer nuevoNumero = null;
            while (nuevoNumero == null) {
                String numeroStr = JOptionPane.showInputDialog(null, "Número de habitación:", 
                                                       "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE);
                
                if (numeroStr == null) return; // El usuario canceló
                
                // Validar que no esté vacío
                if (numeroStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El número no puede estar vacío.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                // Validar que sea un número
                try {
                    nuevoNumero = Integer.parseInt(numeroStr.trim());
                    if (nuevoNumero <= 0) {
                        JOptionPane.showMessageDialog(null, "El número debe ser positivo.", 
                                                     "Error de validación", JOptionPane.ERROR_MESSAGE);
                        nuevoNumero = null;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            // Solicitar y validar tipo de habitación
            String[] tiposHabitacion = {"Individual", "Doble", "Suite", "Familiar"};
            String nuevoTipo = (String) JOptionPane.showInputDialog(null, "Tipo de habitación:", 
                                                           "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE,
                                                           null, tiposHabitacion, tipoActual);
            
            if (nuevoTipo == null) return; // El usuario canceló
            
            // Solicitar y validar capacidad
            Integer nuevaCapacidad = null;
            while (nuevaCapacidad == null) {
                String capacidadStr = JOptionPane.showInputDialog(null, "Capacidad de personas:", 
                                                         "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE);
                
                if (capacidadStr == null) return; // El usuario canceló
                
                if (capacidadStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La capacidad no puede estar vacía.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                try {
                    nuevaCapacidad = Integer.parseInt(capacidadStr.trim());
                    if (nuevaCapacidad <= 0) {
                        JOptionPane.showMessageDialog(null, "La capacidad debe ser positiva.", 
                                                     "Error de validación", JOptionPane.ERROR_MESSAGE);
                        nuevaCapacidad = null;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido para la capacidad.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            // Solicitar y validar precio
            BigDecimal nuevoPrecio = null;
            while (nuevoPrecio == null) {
                String precioStr = JOptionPane.showInputDialog(null, "Precio por noche:", 
                                                       "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE);
                
                if (precioStr == null) return; // El usuario canceló
                
                if (precioStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El precio no puede estar vacío.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                try {
                    nuevoPrecio = new BigDecimal(precioStr.trim().replace(",", "."));
                    if (nuevoPrecio.compareTo(BigDecimal.ZERO) <= 0) {
                        JOptionPane.showMessageDialog(null, "El precio debe ser positivo.", 
                                                     "Error de validación", JOptionPane.ERROR_MESSAGE);
                        nuevoPrecio = null;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un precio válido.", 
                                                 "Error de validación", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            // Solicitar y validar estado
            String[] estadosHabitacion = {"Disponible", "Ocupada", "Mantenimiento", "Fuera de Servicio"};
            String nuevoEstado = (String) JOptionPane.showInputDialog(null, "Estado de la habitación:", 
                                                             "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE,
                                                             null, estadosHabitacion, estadoActual);
            
            if (nuevoEstado == null) return; // El usuario canceló
            
            // Confirmación antes de actualizar
            int confirmar = JOptionPane.showConfirmDialog(null, 
                "¿Confirma actualizar la habitación con los siguientes datos?\n\n" +
                "Número: " + nuevoNumero + "\n" +
                "Tipo: " + nuevoTipo + "\n" +
                "Capacidad: " + nuevaCapacidad + "\n" +
                "Precio por noche: " + nuevoPrecio + "\n" +
                "Estado: " + nuevoEstado,
                "Confirmar Actualización", JOptionPane.YES_NO_OPTION);
            
            if (confirmar != JOptionPane.YES_OPTION) {
                return; // Usuario canceló la actualización
            }
            
            // Llamar al procedimiento almacenado
            CallableStatement cs = conn.prepareCall("{CALL sp_ActualizarHabitacion(?, ?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, idHabitacion);
            cs.setInt(2, nuevoNumero);
            cs.setString(3, nuevoTipo);
            cs.setInt(4, nuevaCapacidad);
            cs.setBigDecimal(5, nuevoPrecio);
            cs.setString(6, nuevoEstado);
            cs.setInt(7, versionActual);
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Habitación actualizada correctamente.", 
                                         "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            // Manejar errores SQL, especialmente los RAISERROR del procedimiento
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), 
                                         "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
}
