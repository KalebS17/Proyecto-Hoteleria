package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Habitacion;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class OperadorHabitaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableHabitaciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperadorHabitaciones frame = new OperadorHabitaciones();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OperadorHabitaciones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableHabitaciones = new JTable();
		tableHabitaciones.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "#", "Tipo", "Capacidad", "Precio", "Estado"
			}
		));
		JScrollPane scrollPane = new JScrollPane(tableHabitaciones);
		scrollPane.setBounds(10, 11, 824, 500);
		contentPane.add(scrollPane);
		
		
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Habitacion showRoom = new Habitacion();
		        List<String> habitaciones = showRoom.getHabitaciones();

		        DefaultTableModel model = (DefaultTableModel) tableHabitaciones.getModel();
		        model.setRowCount(0);

		        // Patrón regex para extraer valores correctamente
		        Pattern pattern = Pattern.compile("ID: (\\d+) \\|Num: (\\d+) \\|Tipo: ([^|]+) \\|Capacidad: (\\d+) \\| \\$ ([\\d,]+) \\| ([^|]+)");

		        for (String habitacionInfo : habitaciones) {
		            if (habitacionInfo == null || habitacionInfo.trim().isEmpty()) {
		                continue; // Saltar cadenas vacías
		            }

		            Matcher matcher = pattern.matcher(habitacionInfo);

		            if (matcher.find()) {
		                try {
		                    String id = matcher.group(1);
		                    String numero = matcher.group(2);
		                    String tipo = matcher.group(3).trim();
		                    String capacidad = matcher.group(4);
		                    String precio = matcher.group(5).replace(",", "."); // Cambia coma por punto
		                    String estado = matcher.group(6).trim();

		                    // Agregar la fila a la tabla
		                    model.addRow(new Object[]{id, numero, tipo, capacidad, precio, estado});
		                } catch (Exception ex) {
		                    System.out.println("Error procesando la cadena: " + habitacionInfo);
		                }
		            } else {
		                System.out.println("Formato incorrecto en la cadena: " + habitacionInfo);
		            }
		        }
		    }
		});
		btnRefrescar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnRefrescar.setBounds(10, 522, 425, 28);
		contentPane.add(btnRefrescar);
		
		JButton btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Habitacion editar = new Habitacion();
				
				try {
		            // Establece la conexión a tu base de datos
		            Connection conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;");
		            
		            // Solicitar ID de habitación
		            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID de la habitación a actualizar:", 
		                                              "Actualizar Habitación", JOptionPane.QUESTION_MESSAGE);
		            
		            if (idStr == null || idStr.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Operación cancelada.");
		                return;
		            }
		            
		            try {
		                int idHabitacion = Integer.parseInt(idStr.trim());
		                
		                // Obtener la versión actual
		                PreparedStatement ps = conn.prepareStatement("SELECT version FROM Habitaciones WHERE id_habitacion = ?");
		                ps.setInt(1, idHabitacion);
		                ResultSet rs = ps.executeQuery();
		                
		                if (rs.next()) {
		                    int versionActual = rs.getInt("version");
		                    System.out.print(versionActual);
		                    
		                    Habitacion.actualizarHabitacion(conn, idHabitacion, versionActual);
		                } else {
		                    JOptionPane.showMessageDialog(null, "No se encontró la habitación con ID: " + idHabitacion, 
		                                                 "Error", JOptionPane.ERROR_MESSAGE);
		                }
		                
		            } catch (NumberFormatException nfe) {
		                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", 
		                                             "Error", JOptionPane.ERROR_MESSAGE);
		            }
		            
		            conn.close();
		            
		        } catch (SQLException sqlE) {
		            JOptionPane.showMessageDialog(null, "Error de conexión: " + sqlE.getMessage(), 
		                                         "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
				
		});
		btnEditar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnEditar.setBounds(445, 522, 150, 28);
		contentPane.add(btnEditar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuOperador mo = new MenuOperador();
				mo.setVisible(true);
				setVisible(false);
			}	
		});
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBackground(Color.RED);
		btnSalir.setBounds(744, 522, 90, 28);
		contentPane.add(btnSalir);
	}
}
