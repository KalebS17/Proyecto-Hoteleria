package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import model.Cliente;
import model.Pago;
import model.Reserva;
import model.Usuario;
import java.awt.Component;
import javax.swing.JTextPane;

public class OperadorEstadisticas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRegis;
	private JTable tableReserv;
	private JTable tableReport;
	private JTable tablePagos;
	private JTable tableRsrv;
	private JTable tableHabUp;
	private JTable tableHabDwn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperadorEstadisticas frame = new OperadorEstadisticas();
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
	public OperadorEstadisticas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuOperador mo = new MenuOperador();
				mo.setVisible(true);
				setVisible(false);
			}
		});
		btnSalir.setBackground(Color.RED);
		btnSalir.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		btnSalir.setBounds(10, 525, 330, 25);
		contentPane.add(btnSalir);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 864, 503);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Registros", null, panel, null);
		panel.setLayout(null);
		
		tableRegis = new JTable();
		tableRegis.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Donde", "Registros Totales"
			}
		));
		tableRegis.getColumnModel().getColumn(1).setPreferredWidth(103);
		JScrollPane scrollPane = new JScrollPane(tableRegis);
	        scrollPane.setBounds(10, 41, 350, 210);
	        panel.add(scrollPane);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Usuario dbManager = new Usuario();
                List<Object[]> datos = dbManager.getAllRegis();

                // Obtener el modelo de la tabla
                DefaultTableModel model = (DefaultTableModel) tableRegis.getModel();

                // Limpiar la tabla antes de agregar nuevos datos
                model.setRowCount(0);

                // Agregar los datos a la tabla
                for (Object[] fila : datos) {
                    model.addRow(fila);
                }
			}
		});
		btnNewButton.setBounds(10, 262, 350, 35);
		panel.add(btnNewButton);
		
		JLabel lblTotalDeRegistros = new JLabel("Total de Registros en cada Tabla del Sistema");
		lblTotalDeRegistros.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotalDeRegistros.setBounds(10, 11, 555, 19);
		panel.add(lblTotalDeRegistros);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("2", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblListarLas = new JLabel(" Listar las 5 habitaciones más y menos reservadas, basándose en el número de reservas.");
		lblListarLas.setBounds(10, 450, 555, 14);
		panel_1.add(lblListarLas);
		
		tableHabUp = new JTable();
		tableHabUp.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Numero de Habitacion", "Reservas Totales"
			}
		));
		tableHabUp.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableHabUp.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableHabUp.getColumnModel().getColumn(2).setPreferredWidth(95);
		JScrollPane scrollHabUp = new JScrollPane(tableHabUp);
		scrollHabUp.setBounds(10, 45, 278, 394); 
        panel_1.add(scrollHabUp); 
		
		
		
		
		tableHabDwn = new JTable();
		tableHabDwn.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Numero de Habitacion", "Reservas Totales"
				}
			));
			tableHabDwn.getColumnModel().getColumn(0).setPreferredWidth(30);
			tableHabDwn.getColumnModel().getColumn(1).setPreferredWidth(120);
			tableHabDwn.getColumnModel().getColumn(2).setPreferredWidth(95);
			JScrollPane scrollHabDwn = new JScrollPane(tableHabDwn);
			scrollHabDwn.setBounds(571, 45, 278, 394); 
	        panel_1.add(scrollHabDwn);
		
	        
	        
		JButton btnHabUD = new JButton("REFRESH");
		btnHabUD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Reserva.cargarDatosHabitaciones(tableHabUp, tableHabDwn);
				
			}
		});
		btnHabUD.setBounds(298, 373, 263, 66);
		panel_1.add(btnHabUD);
		
		JLabel lblHabitacionesMasReservadas = new JLabel("Habitaciones mas Reservadas");
		lblHabitacionesMasReservadas.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHabitacionesMasReservadas.setBounds(10, 11, 255, 19);
		panel_1.add(lblHabitacionesMasReservadas);
		
		JLabel lblHabitacionesMenosReservadas = new JLabel("Habitaciones menos Reservadas");
		lblHabitacionesMenosReservadas.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHabitacionesMenosReservadas.setBounds(604, 15, 255, 19);
		panel_1.add(lblHabitacionesMenosReservadas);
		
		
		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("3", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblRankingDeClientes = new JLabel("Ranking de clientes con más Reservas");
		lblRankingDeClientes.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRankingDeClientes.setBounds(10, 11, 555, 19);
		panel_2.add(lblRankingDeClientes);
		
		tableReserv = new JTable();
		tableReserv.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Cliente", "Numero de Resevas"
			}
		));
		tableReserv.getColumnModel().getColumn(2).setPreferredWidth(117);
		JScrollPane scrollPane3 = new JScrollPane(tableReserv);
        scrollPane3.setBounds(10, 41, 350, 399);
        panel_2.add(scrollPane3);
        
        JButton btnNewButton_1 = new JButton("Refresh");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Cliente.getRankingClientes(tableReserv);
        		
        	}
        });
        btnNewButton_1.setBounds(10, 441, 350, 23);
        panel_2.add(btnNewButton_1);
        
        JPanel panel_5 = new JPanel();
        tabbedPane.addTab("4", null, panel_5, null);
        panel_5.setLayout(null);
        
        JLabel lblConsultaCombinandoAl = new JLabel("Consulta combinando al menos 3 tablas (Habitaciones, Reservas y Pagos)");
        lblConsultaCombinandoAl.setBounds(10, 450, 555, 14);
        panel_5.add(lblConsultaCombinandoAl);
        
        JButton btnNewButton_1_1_1 = new JButton("Refresh");
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Reserva.cargarReserva(tableRsrv);
        		
        	}
        });
        btnNewButton_1_1_1.setBounds(10, 322, 555, 23);
        panel_5.add(btnNewButton_1_1_1);
        
        tableRsrv = new JTable();
        tableRsrv.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Habitacion", "Cliente", "Check In", "Check Out", "$$$"
        	}
        ));
        
        JScrollPane scrollRsrv = new JScrollPane(tableRsrv);
        scrollRsrv.setBounds(10, 41, 555, 270); 
        panel_5.add(scrollRsrv); 
        
        JLabel lblInformacionDeLas = new JLabel("Informacion de las Reservas:");
        lblInformacionDeLas.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblInformacionDeLas.setBounds(10, 11, 555, 19);
        panel_5.add(lblInformacionDeLas);
        
        
        
        
        JPanel panel_4 = new JPanel();
        tabbedPane.addTab("5", null, panel_4, null);
        panel_4.setLayout(null);
        
        JLabel lblPorcentage = new JLabel("Porcentaje de Habitaciones Ocupadas:");
        lblPorcentage.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblPorcentage.setBounds(10, 11, 555, 19);
        panel_4.add(lblPorcentage);
        
        JLabel lblPrcntg = new JLabel("0");
        lblPrcntg.setFont(new Font("Tahoma", Font.BOLD, 99));
        lblPrcntg.setBounds(20, 41, 800, 268);
        panel_4.add(lblPrcntg);

        
        JButton btnNewButton_1_1 = new JButton("Refresh");
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		 String url = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
        		    String query = "SELECT (COUNT(CASE WHEN estado = 'Ocupada' THEN 1 END) * 100.0) / COUNT(*) AS porcentaje_ocupadas FROM Habitaciones";

        		    try (Connection conn = DriverManager.getConnection(url);
        		         PreparedStatement stmt = conn.prepareStatement(query);
        		         ResultSet rs = stmt.executeQuery()) {

        		        if (rs.next()) {
        		            double porcentaje = rs.getDouble("porcentaje_ocupadas"); // Obtener el resultado
        		            lblPrcntg.setText(String.format("%.1f%%", porcentaje)); // Mostrar el porcentaje con 1 decimal y el símbolo %
        		        }

        		    } catch (SQLException exc) {
        		        exc.printStackTrace();
        		        JOptionPane.showMessageDialog(null, "Error al obtener el porcentaje de habitaciones ocupadas: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        		    }
        		
        	}
        });
        btnNewButton_1_1.setBounds(10, 441, 281, 23);
        panel_4.add(btnNewButton_1_1);
        
        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("6", null, panel_3, null);
        panel_3.setLayout(null);
        
        JLabel lblCalcularAgregadosUtilizando = new JLabel("Calcular agregados utilizando SUM, AVG, MAX, MIN y COUNT en una sola consulta");
        lblCalcularAgregadosUtilizando.setBounds(10, 450, 555, 14);
        panel_3.add(lblCalcularAgregadosUtilizando);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  List<Double> stats = Pago.getStatsPagos();
        	        
        	        if (!stats.isEmpty()) {
        	            DefaultTableModel model = (DefaultTableModel) tablePagos.getModel();
        	            model.setRowCount(0); // Limpiar la tabla
        	            
        	            // Agregar los datos en una fila
        	            model.addRow(new Object[] {
        	                stats.get(0), // Ingresos Totales (double)
        	                stats.get(1), // Ingreso Promedio (double)
        	                stats.get(2), // Pago más Grande (double)
        	                stats.get(3), // Pago más Pequeño (double)
        	                (int) Math.round(stats.get(4)) // Número de Pagos 
        	            });
        	        }
        	}
        });
        btnRefresh.setBounds(10, 122, 390, 23);
        panel_3.add(btnRefresh);
        
        tablePagos = new JTable();
        tablePagos.setFont(new Font("Tahoma", Font.BOLD, 15));
        tablePagos.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Ingresos Totales", "Ingreso Promedio", "Pago mas Grande", "Pago mas pequeno", "# de Pagos"
        	}
        ) {

        });
        tablePagos.getColumnModel().getColumn(0).setPreferredWidth(107);
        tablePagos.getColumnModel().getColumn(1).setPreferredWidth(99);
        tablePagos.getColumnModel().getColumn(2).setPreferredWidth(101);
        tablePagos.getColumnModel().getColumn(3).setPreferredWidth(107);
        
        JScrollPane scrollPagos = new JScrollPane(tablePagos);
        scrollPagos.setBounds(10, 11, 555, 100); // Ajusta posición y tamaño
        panel_3.add(scrollPagos); // Agregar el ScrollPane en lugar de la tabla directamente
        
        JPanel panel_7 = new JPanel();
        tabbedPane.addTab("7", null, panel_7, null);
        panel_7.setLayout(null);
        
        JLabel lblIdentificacinDeClientes = new JLabel("Identificación de clientes duplicados por nombre y email");
        lblIdentificacinDeClientes.setBounds(10, 450, 555, 14);
        panel_7.add(lblIdentificacinDeClientes);
        
        JLabel lblIdentificarClientesDuplicados = new JLabel("Identificar Clientes con mismo Nombre y Apellido");
        lblIdentificarClientesDuplicados.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblIdentificarClientesDuplicados.setBounds(10, 11, 555, 19);
        panel_7.add(lblIdentificarClientesDuplicados);
        
        
        JTextPane textPaneRepes = new JTextPane();
        textPaneRepes.setEditable(false);
        textPaneRepes.setFont(new Font("Tahoma", Font.ITALIC, 15));
        textPaneRepes.setBounds(10, 41, 375, 50);
        panel_7.add(textPaneRepes);
        
        
        JButton btnRefresh_1 = new JButton("Refresh");
        btnRefresh_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Cliente.getNamesRepes(textPaneRepes);
        		
        	}
        });
        btnRefresh_1.setBounds(10, 140, 375, 23);
        panel_7.add(btnRefresh_1);
        
       
        
        
        
        
        
        
        JButton btnReporteMensual = new JButton("Reporte Mensual");
        btnReporteMensual.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		 mostrarReporteMensual();
        		
        		
        	}
        });
        btnReporteMensual.setFont(new Font("Lucida Console", Font.PLAIN, 15));
        btnReporteMensual.setBackground(new Color(0, 128, 192));
        btnReporteMensual.setBounds(544, 526, 330, 25);
        contentPane.add(btnReporteMensual);
        
        
        

	}
	
	
	
	
	private void mostrarReporteMensual() {
	    JDialog dialog = new JDialog();
	    dialog.setTitle("Reporte Mensual");
	    dialog.setSize(500, 400);
	    dialog.setLocationRelativeTo(null); // Centrar en pantalla
	    dialog.setModal(true); // Bloquea la ventana principal hasta que se cierre

	    tableReport = new JTable();
		tableReport.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"AÑO", "MES", "INGRESOS"
			}
		));
		tableReport.getColumnModel().getColumn(2).setPreferredWidth(117);
	   
	    JScrollPane scrollPane = new JScrollPane(tableReport);
	    dialog.getContentPane().add(scrollPane);
	    
	    Usuario.cargarReporteMensual(tableReport);

	    dialog.setVisible(true);
	}
}
