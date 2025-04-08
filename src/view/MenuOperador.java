package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Reserva;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class MenuOperador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuOperador frame = new MenuOperador();
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
	public MenuOperador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 225));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnHabitacion = new JButton("Habitaciones");
		btnHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OperadorHabitaciones oH = new OperadorHabitaciones();
				oH.setVisible(true);
				setVisible(false);
				
			}
		});
		btnHabitacion.setBackground(new Color(255, 182, 193));
		btnHabitacion.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnHabitacion.setBounds(10, 11, 364, 50);
		contentPane.add(btnHabitacion);
		
		JButton btnEmpleados = new JButton("Empleados");
		btnEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OperadorEmpleados oE = new OperadorEmpleados();
				oE.setVisible(true);
				setVisible(false);
			}
		});
		btnEmpleados.setBackground(new Color(255, 182, 193));
		btnEmpleados.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnEmpleados.setBounds(10, 72, 364, 50);
		contentPane.add(btnEmpleados);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				InicioSesion is = new InicioSesion();
				is.setVisible(true);
				setVisible(false);
			}	
		});
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBounds(10, 517, 364, 33);
		contentPane.add(btnSalir);
		
		JButton btnModuloEmpleado = new JButton("Modulo Empleado");
		btnModuloEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuPrincipal mp = new MenuPrincipal();
				mp.setVisible(true);

			}
		});
		btnModuloEmpleado.setBackground(new Color(255, 182, 193));
		btnModuloEmpleado.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnModuloEmpleado.setBounds(10, 456, 364, 50);
		contentPane.add(btnModuloEmpleado);
		
		JButton btnAnalisis = new JButton("ESTADISTICAS");
		btnAnalisis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OperadorEstadisticas oE = new OperadorEstadisticas();
				oE.setVisible(true);
				setVisible(false);
			}
		});
		btnAnalisis.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnAnalisis.setBackground(new Color(219, 112, 147));
		btnAnalisis.setBounds(10, 133, 364, 50);
		contentPane.add(btnAnalisis);
		
		JButton btnBuscarReservaPor = new JButton("Buscar Reserva por Nombre");
		btnBuscarReservaPor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String searchName;
			boolean validName = false;
			
				
				
				do {
					
					searchName = JOptionPane.showInputDialog("Ingrese el Nombre Asociado a la Reserva");
					
					if (searchName == null ) {
						JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
		                System.exit(0);
					}
					if(searchName.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Error: El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
		                continue;
					}
					if (!searchName.matches("^[\\p{L}]+$")) {
		                JOptionPane.showMessageDialog(null, "Error: El nombre solo puede contener letras (sin números, espacios o caracteres especiales).", "Error", JOptionPane.ERROR_MESSAGE);
		                continue;
		            }
					
					//si llega aqui es valido el nombre
					validName = true;
				}while(!validName);
				
				
				
				List<Object[]> resultados = Reserva.buscarReservasXNombre(searchName);
				if (resultados.isEmpty()) {
					
					JOptionPane.showMessageDialog(null, "No se encontraron reservas con ese nombre");
					
				} else {
					mostrarBusqueda(resultados);
				}
				
			}});
		btnBuscarReservaPor.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnBuscarReservaPor.setBackground(new Color(255, 182, 193));
		btnBuscarReservaPor.setBounds(10, 194, 364, 27);
		contentPane.add(btnBuscarReservaPor);
	}
	
	public void mostrarBusqueda(List<Object[]> datos) {
		
		String[] columnas = {
		        "ID Cliente", "Nombre", "Apellido", 
		        "ID Reserva", "Check In", "Check Out", 
		        "Total", "Estado" 
		    };
		    
		    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		    for (Object[] fila : datos) {
		        modelo.addRow(fila);
		    }

		    JTable tabla = new JTable(modelo);
		    JScrollPane scroll = new JScrollPane(tabla);

		    JDialog dialogo = new JDialog();
		    dialogo.setTitle("Reservas encontradas");
		    dialogo.setModal(true);
		    dialogo.setSize(800, 300);
		    dialogo.setLocationRelativeTo(null);
		    dialogo.add(scroll);
		    dialogo.setVisible(true);
		
	}

}
