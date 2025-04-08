package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.SessionManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfUserName;
	private JTextField tfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSesion frame = new InicioSesion();
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
	public InicioSesion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfUserName = new JTextField();
		tfUserName.setBounds(10, 67, 190, 20);
		contentPane.add(tfUserName);
		tfUserName.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(10, 123, 190, 20);
		contentPane.add(tfPassword);
		
		JLabel lblNewLabel = new JLabel("Bienvenido al Sistema Hotelero!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 270, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblUserName = new JLabel("Ingrese su Usuario:");
		lblUserName.setBounds(10, 42, 190, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Ingrese su Contraseña:");
		lblPassword.setBounds(10, 98, 190, 14);
		contentPane.add(lblPassword);
		
		JButton btnContinuar = new JButton("CONTINUAR");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (tfUserName.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Usuario y contraseña son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;
		        ResultSet rs = null;

		        String username = tfUserName.getText().trim();
		        String password = tfPassword.getText().trim();
		        
		        try {
		            conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;");


		            String sql = "SELECT u.rol, e.id_empleado FROM Usuarios u " +
		                         "LEFT JOIN Empleados e ON u.id_usuario = e.id_usuario " +
		                         "WHERE u.nombre_usuario = ? AND u.password = ?";
		            
		            stmt = conn.prepareStatement(sql);
		            stmt.setString(1, username);
		            stmt.setString(2, password);

		            rs = stmt.executeQuery();


		            if(rs.next()) {
		                // Authentication successful
		                String userRole = rs.getString("rol");
		                int empleadoId = rs.getInt("id_empleado");

		                // Store the role and employee ID in variables
		                String currentUserRole = userRole;
		                int currentEmpleadoId = empleadoId;
		                

		                
		                SessionManager.getInstance().setCurrentUserRole(userRole);
		                SessionManager.getInstance().setCurrentEmpleadoId(empleadoId); //GUARDAMOS LOS VALORES EN EL SESSIONMANAGER
		                

		                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. Rol: " + userRole + 
		                                            "\nID Empleado: " + empleadoId,
		                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

		                if(userRole.equals("Administrador")) {
		                    MenuOperador operador = new MenuOperador();
		                    // Pasar el ID de empleado al menú si es necesario
		                    // operador.setEmpleadoId(currentEmpleadoId);
		                    operador.setVisible(true);
		                    setVisible(false);
		                } else if(userRole.equals("Recepcionista")) {
		                    MenuPrincipal menu = new MenuPrincipal();
		                    // Pasar el ID de empleado al menú si es necesario
		                    // menu.setEmpleadoId(currentEmpleadoId);
		                    menu.setVisible(true);
		                    setVisible(false);
		                }
		            } else {
		                // Authentication failed
		                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos",
		                    "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch(SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + ex.getMessage(),
		                "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } finally {
		            // Close resources
		            try {
		                if(rs != null) rs.close();
		                if(stmt != null) stmt.close();
		                if(conn != null) conn.close();
		            } catch(SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
		    }
		});
		btnContinuar.setBounds(10, 177, 190, 23);
		contentPane.add(btnContinuar);
	}
}
