package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Empleado;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class OperadorEmpleados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperadorEmpleados frame = new OperadorEmpleados();
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
	public OperadorEmpleados() {
		
		Empleado Empl = new Empleado();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listaServicios = new JList();
		listaServicios.setBounds(10, 11, 820, 550);
		contentPane.add(listaServicios);
		
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
		btnSalir.setBounds(10, 572, 90, 28);
		contentPane.add(btnSalir);
		
		JButton btnEmpleados = new JButton("Empleados");
		btnEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<String> employees = Empl.showEmployees();
				
				listaServicios.setListData(employees.toArray(new String[0]));
				
			}
		});
		btnEmpleados.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnEmpleados.setBounds(110, 572, 130, 28);
		contentPane.add(btnEmpleados);
		
		JButton btnJerarquia = new JButton("Jerarquia");
		btnJerarquia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<String> employeesJerark = Empl.getEmployeeHierarchy();
				listaServicios.setListData(employeesJerark.toArray(new String[0]));
			}
		});
		btnJerarquia.setBackground(SystemColor.info);
		btnJerarquia.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnJerarquia.setBounds(250, 572, 130, 28);
		contentPane.add(btnJerarquia);
	}
}
