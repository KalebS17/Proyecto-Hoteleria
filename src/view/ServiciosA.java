package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.ServicioAdicional;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ServiciosA extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiciosA frame = new ServiciosA();
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
	public ServiciosA() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listaServicios = new JList();
		listaServicios.setBounds(10, 11, 514, 550);
		contentPane.add(listaServicios);
		
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
		btnSalir.setBounds(10, 572, 90, 28);
		contentPane.add(btnSalir);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ServicioAdicional serv = new ServicioAdicional();
				List<String> servicios = serv.getServicios();
				
				listaServicios.setListData(servicios.toArray(new String[0]));
			}
		});
		btnRefrescar.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnRefrescar.setBounds(244, 572, 280, 28);
		contentPane.add(btnRefrescar);
	}

}
