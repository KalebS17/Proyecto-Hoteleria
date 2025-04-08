package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Pago;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ReservaPago extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblID;
	private static int idReserva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservaPago frame = new ReservaPago(idReserva);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param idReserva 
	 */
	public ReservaPago(int idReserva) {
		setTitle("Crear Pago");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 280);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Reserva:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 100, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblMonto = new JLabel("Monto a Cancelar:");
		lblMonto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMonto.setBounds(10, 80, 129, 14);
		contentPane.add(lblMonto);
		
		String reservaID = Integer.toString(idReserva);
		
		lblID = new JLabel(reservaID);
		lblID.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblID.setBounds(120, 13, 100, 14);
		contentPane.add(lblID);
		
		JComboBox<String> comboBoxPago = new JComboBox<>(); 
		comboBoxPago.setBounds(300, 40, 174, 22); 
		contentPane.add(comboBoxPago); 

		comboBoxPago.addItem("Efectivo");
		comboBoxPago.addItem("Tarjeta");
		comboBoxPago.addItem("Transferencia");
		
		
	
		
		JLabel lblMetodoDePago = new JLabel("Metodo de Pago");
		lblMetodoDePago.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMetodoDePago.setBounds(300, 7, 160, 22);
		contentPane.add(lblMetodoDePago);
		
		int monto = Pago.montoACancelar(idReserva);
		String montoStr = Integer.toString(monto);
		
		JLabel lblMontoCancelar = new JLabel(montoStr + "$");
		lblMontoCancelar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMontoCancelar.setBounds(158, 82, 100, 14);
		contentPane.add(lblMontoCancelar);
		
		JButton btnNewButton = new JButton("PROCESAR PAGO");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
		            // Establecer conexión con la base de datos
		            Connection conexion = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;");
		            BigDecimal monto = new BigDecimal(Pago.montoACancelar(idReserva)); 
		            String metodoPago = (String) comboBoxPago.getSelectedItem();
		            
		            // Obtener fecha actual
		            java.sql.Timestamp fechaActual = new java.sql.Timestamp(System.currentTimeMillis());
		            
		            // Preparar llamada al stored procedure
		            CallableStatement cstmt = conexion.prepareCall("{call InsertarPago(?, ?, ?, ?)}");
		            
		            // Establecer parámetros
		            cstmt.setInt(1, idReserva);
		            cstmt.setBigDecimal(2, monto);
		            cstmt.setString(3, metodoPago);
		            cstmt.setTimestamp(4, fechaActual);
		            
		            // Ejecutar el stored procedure
		            ResultSet rs = cstmt.executeQuery();
		            
		            // Procesar el resultado (obtener el ID del pago insertado)
		            if (rs.next()) {
		                int idPago = rs.getInt("id_pago");
		                JOptionPane.showMessageDialog(null, "Pago procesado correctamente. ID del pago: " + idPago);
		            }
		            
		            // Cerrar recursos
		            rs.close();
		            cstmt.close();
		            conexion.close();
		            
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error al procesar el pago: " + ex.getMessage());
		            ex.printStackTrace();
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Error: Verifique que los valores numéricos sean correctos.");
		        }
				
				
				setVisible(false);
		    	
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 128, 0));
		btnNewButton.setBounds(10, 200, 450, 30);
		contentPane.add(btnNewButton);
	}
}
