package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.SessionManager;

import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnHabitacion = new JButton("Habitaciones");
		btnHabitacion.setBackground(new Color(176, 224, 230));
		btnHabitacion.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Habitaciones habitaciones = new Habitaciones();
				habitaciones.setVisible(true);
				setVisible(false);
			}
		});
		btnHabitacion.setBounds(10, 11, 364, 50);
		contentPane.add(btnHabitacion);
		
		JButton btnReservas = new JButton("Reservas");
		btnReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Reservas reservas = new Reservas();
				reservas.setVisible(true);
				setVisible(false);
				
			}
		});
		btnReservas.setBackground(new Color(176, 224, 230));
		btnReservas.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnReservas.setBounds(384, 72, 364, 50);
		contentPane.add(btnReservas);
		
		JButton btnPagos = new JButton("Pagos");
		btnPagos.setBackground(new Color(176, 224, 230));
		btnPagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPagos.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnPagos.setBounds(384, 11, 364, 50);
		contentPane.add(btnPagos);
		
		JButton btnServiciosAdicionales = new JButton("Servicios Adicionales");
		btnServiciosAdicionales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ServiciosA servA = new ServiciosA();
				servA.setVisible(true);
				setVisible(false);
				
			}
		});
		btnServiciosAdicionales.setBackground(new Color(176, 224, 230));
		btnServiciosAdicionales.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnServiciosAdicionales.setBounds(10, 72, 364, 50);
		contentPane.add(btnServiciosAdicionales);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String rol = SessionManager.getInstance().getCurrentUserRole();
				
				if( rol.equals("Administrador")){
					
					setVisible(false);
				} else {
					
					InicioSesion is = new InicioSesion();
					is.setVisible(true);
					setVisible(false);
					
				}
				
			}
		});
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBounds(10, 217, 734, 33);
		contentPane.add(btnSalir);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RegistrarCliente regis = new RegistrarCliente();
				regis.setVisible(true);
				setVisible(false);
			}
		});
		btnClientes.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnClientes.setBackground(new Color(176, 224, 230));
		btnClientes.setBounds(10, 133, 364, 50);
		contentPane.add(btnClientes);
	}
}
