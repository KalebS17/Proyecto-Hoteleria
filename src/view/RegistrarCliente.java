package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdbc.ConnectionSQL;
import model.Cliente;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class RegistrarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfApellido;
	private JTextField tfNumerot;
	private JTextField tfMail;
	private JTextField tfCedula;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarCliente frame = new RegistrarCliente();
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
	public RegistrarCliente() {
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listaClientes = new JList();
		listaClientes.setBounds(10, 149, 594, 266);
		contentPane.add(listaClientes);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuPrincipal mp = new MenuPrincipal();
				mp.setVisible(true);
				setVisible(false);
			}
		});
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBackground(Color.RED);
		btnSalir.setBounds(10, 426, 90, 28);
		contentPane.add(btnSalir);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Cliente showClient = new Cliente();
				
				List<String> clientes = showClient.getTodosLosClientes();

                // Actualizar la JList con los datos obtenidos
                listaClientes.setListData(clientes.toArray(new String[0]));
			}
		});
		btnRefrescar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnRefrescar.setBounds(110, 426, 280, 28);
		contentPane.add(btnRefrescar);
		
		
		JLabel lblIRegistrarCliente = new JLabel("Registrar Cliente:");
		lblIRegistrarCliente.setHorizontalAlignment(SwingConstants.LEFT);
		lblIRegistrarCliente.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblIRegistrarCliente.setBounds(10, 15, 260, 33);
		contentPane.add(lblIRegistrarCliente);
		
		JLabel lblNombreC = new JLabel("Nombre:");
		lblNombreC.setBounds(10, 59, 90, 14);
		contentPane.add(lblNombreC);
		
		JLabel lblApellidoC = new JLabel("Apellido:");
		lblApellidoC.setBounds(110, 59, 90, 14);
		contentPane.add(lblApellidoC);
		
		JLabel lblMailC1 = new JLabel("Correo Electronico:");
		lblMailC1.setBounds(320, 59, 120, 14);
		contentPane.add(lblMailC1);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(210, 59, 100, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblDocumentoDeIdentidad = new JLabel("Documento de Identidad:");
		lblDocumentoDeIdentidad.setBounds(450, 59, 150, 14);
		contentPane.add(lblDocumentoDeIdentidad);
		
		tfName = new JTextField();
		tfName.setBounds(10, 84, 86, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(110, 84, 86, 20);
		contentPane.add(tfApellido);
		
		tfNumerot = new JTextField();
		tfNumerot.setColumns(10);
		tfNumerot.setBounds(210, 84, 86, 20);
		contentPane.add(tfNumerot);
		
		tfMail = new JTextField();
		tfMail.setText("@gmail.com");
		tfMail.setColumns(10);
		tfMail.setBounds(320, 84, 120, 20);
		contentPane.add(tfMail);
		
		tfCedula = new JTextField();
		tfCedula.setText("Cedula");
		tfCedula.setColumns(10);
		tfCedula.setBounds(450, 84, 120, 20);
		contentPane.add(tfCedula);
		
		JButton btnAddClient = new JButton("Agregar Cliente");
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// revisamos si hay tfs en blanco
				if(tfName.getText().trim().isEmpty() || 
					       tfApellido.getText().trim().isEmpty() || 
					       tfNumerot.getText().trim().isEmpty() || 
					       tfMail.getText().trim().isEmpty() || 
					       tfCedula.getText().trim().isEmpty() || 
					       tfCedula.getText().equals("Cedula")) 
				{
					JOptionPane.showMessageDialog(null, "!UPS! Dejaste campos en Blanco", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String numt = tfNumerot.getText().trim();
				String cedula = tfCedula.getText().trim();
				String nombre = tfName.getText();
	            String apellido = tfApellido.getText();
	            String email = tfMail.getText();

				
				if (!numt.matches("\\d+")) { // d+ significa uno o más dígitos, si numerot no contiene solo números, la condición se evalúa como true y se ejecuta el error del JOptionPAne
					JOptionPane.showMessageDialog(null, "UPS!!! Asegurate de que tu numero telefonico no tenga LETRAS !", "Error", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				
				if (!cedula.matches("\\d+") || cedula.equals("Cedula")) {
					JOptionPane.showMessageDialog(null, "Ten cuidado, tu cedula solo puede contener números", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Cliente cliente = new Cliente();
				cliente.agregarCliente(nombre, apellido, email, numt, cedula);
				
			}
		});
		btnAddClient.setBackground(new Color(0, 255, 128));
		btnAddClient.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnAddClient.setBounds(400, 426, 204, 28);
		contentPane.add(btnAddClient);
	}
}
