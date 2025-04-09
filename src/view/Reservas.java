package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import jdbc.ConnectionSQL;
import model.Cliente;
import model.Habitacion;
import model.Reserva;
import model.ServicioAdicional;
import model.SessionManager;

import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reservas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfHabitacionID;
	private JTextField tfDateIn;
	private JTextField tfDateOut;
	private JTable roomsTable;
	private JTable clienTable;
	private JTextField tfClienteID;
	private JTable tableReserv;
	String[] opciones = {
	        "No agregar servicios adicionales",
	        "Desayuno buffet ($15.00)",
	        "Spa ($50.00)",
	        "Transporte aeropuerto ($30.00)"
	    };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas frame = new Reservas();
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
	public Reservas() {
		
		ConnectionSQL con = new ConnectionSQL();
		Cliente cliente = new Cliente();

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(128, 128, 128));
		separator.setBounds(470, 2, 4, 660);
		contentPane.add(separator);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuPrincipal mp = new MenuPrincipal();
				mp.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		btnSalir.setBackground(new Color(255, 0, 0));
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBounds(484, 622, 90, 28);
		contentPane.add(btnSalir);
		
		JLabel lblInfoHuesped = new JLabel("DATOS PARA LA RESERVA");
		lblInfoHuesped.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoHuesped.setFont(new Font("Yu Gothic UI", Font.BOLD, 25));
		lblInfoHuesped.setBounds(481, 11, 398, 22);
		contentPane.add(lblInfoHuesped);
		
		tfClienteID = new JTextField();
		tfClienteID.setColumns(10);
		tfClienteID.setBounds(484, 83, 123, 20);
		contentPane.add(tfClienteID);
		
		tfHabitacionID = new JTextField();
		tfHabitacionID.setColumns(10);
		tfHabitacionID.setBounds(484, 139, 123, 20);
		contentPane.add(tfHabitacionID);
		
		tfDateIn = new JTextField();
		tfDateIn.setForeground(new Color(128, 0, 0));
		tfDateIn.setText("YYYY-MM-dd");
		tfDateIn.setColumns(10);
		tfDateIn.setBounds(674, 83, 123, 20);
		contentPane.add(tfDateIn);
		
		tfDateOut = new JTextField();
		tfDateOut.setForeground(new Color(128, 0, 0));
		tfDateOut.setText("YYYY-MM-dd");
		tfDateOut.setColumns(10);
		tfDateOut.setBounds(674, 139, 123, 20);
		contentPane.add(tfDateOut);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(470, 170, 434, 2);
		contentPane.add(separator_1_1);
		
		JButton btnReservar = new JButton("Reservar y pasar a Pago");
		btnReservar.setBackground(new Color(0, 255, 0));
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ReservaPago rp = new ReservaPago();
				//rp.setVisible(true);

				
				if(Reserva.validarCampos(tfClienteID, tfHabitacionID, tfDateIn, tfDateOut) == true) {
					System.out.print("datos correctos");
					
					  int clienteID = Integer.parseInt(tfClienteID.getText().trim());
					  int habitacionID = Integer.parseInt(tfHabitacionID.getText().trim());
					  String fechaIn = tfDateIn.getText().trim();
					  String fechaOut = tfDateOut.getText().trim();
					  int idEmpleado = SessionManager.getInstance().getCurrentEmpleadoId(); 
					  //int idEmpleado = 1;
					  String estadoReserva = "Confirmada";
					  
					  Reserva reserva = new Reserva();
					  int idReserva = reserva.crearReserva(clienteID, habitacionID, fechaIn, fechaOut, idEmpleado, estadoReserva);
					  
					  if (idReserva > 0) {
						  
						  int seleccion = JOptionPane.showOptionDialog(null, "¿Desea agregar algún servicio adicional?", "Servicios Adicionales", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, 
								  opciones[0]);
						  
						  if (seleccion == 0) {
							  
							  ReservaPago rp = new ReservaPago(idReserva);
							  rp.setVisible(true);
							  
						  } else {
							  
							  int idServicio = 0;
						        switch (seleccion) {
						            case 1: idServicio = 1; break; // Desayuno buffet
						            case 2: idServicio = 2; break; // Spa
						            case 3: idServicio = 3; break; // Transporte aeropuerto
						        }
						        
						        if (idServicio > 0) {
						            // Pedir la cantidad del servicio adicional
						            String cantidadStr = JOptionPane.showInputDialog(null, 
						                "Ingrese la cantidad del servicio adicional seleccionado:", 
						                "Cantidad de Servicio", 
						                JOptionPane.QUESTION_MESSAGE);
						            
						            if (cantidadStr != null && !cantidadStr.trim().isEmpty()) {
						                try {
						                    int cantidad = Integer.parseInt(cantidadStr.trim());
						                    
						                    if (cantidad > 0) {
						                        // Llamar al método para insertar el servicio adicional con la cantidad elegida
						                        ServicioAdicional.agregarServicioAReserva(idReserva, idServicio, cantidad);

						                        // Luego de agregar el servicio, abrir la pantalla de pago
						                        ReservaPago rp = new ReservaPago(idReserva);
						                        rp.setVisible(true);
						                    } else {
						                        JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
						                    }
						                } catch (NumberFormatException nfe) {
						                    JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
						                }
						            }
						        }
						  }
						  
					  }
					  
					  
					 
					  
					
				} else {
					
					JOptionPane.showMessageDialog(null, "Asegurate de Ingresar los Datos Correctamente", "Algo salio MAL!!!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnReservar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnReservar.setBounds(583, 622, 315, 28);
		contentPane.add(btnReservar);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 450, 639);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(222, 184, 135));
		tabbedPane.addTab("Rooms", null, panel, null);
		panel.setLayout(null);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.setBounds(10, 572, 425, 28);
		panel.add(btnRefrescar);
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			Habitacion showRoom = new Habitacion();
				
				 List<String> habitacionesDisponibles = showRoom.getHabitacionesDisponibles();
				 
	             DefaultTableModel model = (DefaultTableModel) roomsTable.getModel();
	             model.setRowCount(0);
	                // Actualizar la JList con los datos obtenidos
	             for (String habitacionInfo : habitacionesDisponibles) {
	                    // Dividir la cadena formateada en partes
	                    String[] partes = habitacionInfo.split("\\|");
	                    String id = partes[1].trim().split(":")[1].trim();
	                    String capacidad = partes[2].trim().split(":")[1].trim();
	                    String precio = partes[3].trim().split(":")[1].trim();

	                    // Agregar la fila a la tabla
	                    model.addRow(new Object[]{id, precio, capacidad});
	                }
				
				
			}
		});
		btnRefrescar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		
		roomsTable = new JTable();
		roomsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "PRECIO", "CAPACIDAD"
			}
		));
		JScrollPane scrollPane = new JScrollPane(roomsTable); // Agregar JTable a un JScrollPane
		scrollPane.setEnabled(false);
        scrollPane.setBounds(10, 11, 425, 550);
        panel.add(scrollPane);
		
        
        
        
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(205, 133, 63));
		tabbedPane.addTab("Clients", null, panel_1, null);
		panel_1.setLayout(null);
		
		clienTable = new JTable();
		clienTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Apellido", "Cedula"
			}
		));
		JScrollPane scrollPaneClientes = new JScrollPane(clienTable); // Agregar JTable a un JScrollPane
		scrollPaneClientes.setEnabled(false);
		scrollPaneClientes.setBounds(10, 11, 425, 535);
		panel_1.add(scrollPaneClientes);
		
		JButton btnRefrescarClientes = new JButton("Refrescar");
		btnRefrescarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<String> clientesLista = cliente.mostrarClientesBasics();
				
				// Obtener el modelo de la tabla
		        DefaultTableModel model = (DefaultTableModel) clienTable.getModel();
		        model.setRowCount(0);
		        
		        for (String clienteInfo : clientesLista) {
		            // Dividir la cadena formateada en partes
		            String[] partes = clienteInfo.split("\\|");
		            String id = partes[0].trim();
		            String nombreApellido = partes[1].trim();
		            String cedula = partes[2].trim();
		       
		            String[] nombreApellidoArray = nombreApellido.split(",");
		            String nombre = nombreApellidoArray[0].trim();
		            String apellido = nombreApellidoArray[1].trim();
		            
		            model.addRow(new Object[]{id, nombre, apellido, cedula});
		    
				
			}
			}});
		btnRefrescarClientes.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnRefrescarClientes.setBounds(10, 572, 425, 28);
		panel_1.add(btnRefrescarClientes);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 102, 0));
		tabbedPane.addTab("Resevas", null, panel_2, null);
		panel_2.setLayout(null);
		
		tableReserv = new JTable();
		tableReserv.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ReservaID", "ClienteID", "RoomID", "EmpleadoID", "IN", "OUT", "$$$"
			}
		));
		JScrollPane scrollR = new JScrollPane(tableReserv);
		scrollR.setEnabled(false);
		scrollR.setBounds(10, 11, 425, 550);
		panel_2.add(scrollR);
		
		JButton btnRefrescarReserva = new JButton("Refrescar");
		btnRefrescarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 List<String[]> reservasData = Reserva.getReservasC();
				    
				    // Obtener el modelo de la tabla
				    DefaultTableModel model = (DefaultTableModel) tableReserv.getModel();
				    
				    // Limpiar la tabla antes de agregar nuevos datos
				    model.setRowCount(0);
				    
				    // Agregar los datos a la tabla
				    for (String[] reserva : reservasData) {
				        model.addRow(reserva);
				    }
				
			}
		});
		btnRefrescarReserva.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnRefrescarReserva.setBackground(Color.GREEN);
		btnRefrescarReserva.setBounds(10, 572, 200, 28);
		panel_2.add(btnRefrescarReserva);
		
		JButton btnEliminar = new JButton("Eliminar ");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String input = JOptionPane.showInputDialog(null, 
                        "Ingrese el ID de la reserva a eliminar:", 
                        "Eliminar Reserva", 
                        JOptionPane.QUESTION_MESSAGE);

				// Verificar si el usuario canceló el diálogo o dejó el campo vacío
				if (input != null && !input.trim().isEmpty()) {
				try {
				// Convertir el input a entero (ID de reserva)
				int idReserva = Integer.parseInt(input.trim());
				
				// Llamar al método para eliminar la reserva
				boolean eliminado = Reserva.cancelarReserva(idReserva);
				
				// Mostrar mensaje de resultado
				if (eliminado) {
				JOptionPane.showMessageDialog(null, 
				                       "Reserva #" + idReserva + " eliminada correctamente", 
				                       "Éxito", 
				                       JOptionPane.INFORMATION_MESSAGE);

				} else {
				JOptionPane.showMessageDialog(null, 
				                       "No se pudo eliminar la reserva #" + idReserva, 
				                       "Error", 
				                       JOptionPane.ERROR_MESSAGE);
				}
				} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, 
				                   "Por favor, ingrese un número válido", 
				                   "Error", 
				                   JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnEliminar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnEliminar.setBackground(Color.GREEN);
		btnEliminar.setBounds(235, 572, 200, 28);
		panel_2.add(btnEliminar);
		
		JLabel lbClienteID = new JLabel("ID Del Cliente:");
		lbClienteID.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lbClienteID.setBounds(484, 58, 123, 14);
		contentPane.add(lbClienteID);
		

		
		JLabel lbHabitacion = new JLabel("ID De la Habitacion:");
		lbHabitacion.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lbHabitacion.setBounds(484, 114, 150, 14);
		contentPane.add(lbHabitacion);
		
		JLabel lbCheckIN = new JLabel("CHECK IN:");
		lbCheckIN.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lbCheckIN.setBounds(674, 60, 123, 14);
		contentPane.add(lbCheckIN);
		
		JLabel lbCheckOUT = new JLabel("CHECK IN:");
		lbCheckOUT.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lbCheckOUT.setBounds(674, 114, 123, 14);
		contentPane.add(lbCheckOUT);
		
		
	}
}
