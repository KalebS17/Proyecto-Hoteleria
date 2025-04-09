package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Pago {
	private int idPago;
	private int idReserva;
	private double monto;
	private String metodoPago;
	private Date fechaPago;
	
	public Pago(int idPago, int idReserva, double monto, String metodoPago, Date fechaPago) {
		super();
		this.idPago = idPago;
		this.idReserva = idReserva;
		this.monto = monto;
		this.metodoPago = metodoPago;
		this.fechaPago = fechaPago;
	}
	
	public Pago() {

	}
	
	public int getIdPago() {
		return idPago;
	}
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	@Override
	public String toString() {
		return "Pago [idPago=" + idPago + ", idReserva=" + idReserva + ", monto=" + monto + ", metodoPago=" + metodoPago
				+ ", fechaPago=" + fechaPago + "]";
	}
	
	
	public static int montoACancelar(int idReserva) {
	    String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
	    int montoApagar = 0;

	    try {
	        // Cargar el driver JDBC
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	        // Establecer la conexión con try-with-resources para cerrar automáticamente
	        try (Connection conexion = DriverManager.getConnection(connectionURL);
	             PreparedStatement statement = conexion.prepareStatement("SELECT total FROM Reservas WHERE id_reserva = ?")) {
	             
	            // Asignar el parámetro a la consulta
	            statement.setInt(1, idReserva);
	            
	            // Ejecutar la consulta
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) { // Moverse al primer resultado antes de leer
	                    montoApagar = resultSet.getInt("total");
	                }
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar el Driver de SQL - JDBC", e.getMessage(), JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException exc) {
	        JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
	        System.out.print(exc.getMessage());
	    }

	    return montoApagar;
	}

	
	
		//statisctics pagos
	
	public static List<Double> getStatsPagos() {
	    List<Double> stats = new ArrayList<>();
	    String connectionURL = "jdbc:sqlserver://DESKTOP-ONGPC5N:1433;databaseName=HotelReservas;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

	    try (Connection conexion = DriverManager.getConnection(connectionURL);
	         Statement statement = conexion.createStatement();
	         ResultSet resultSet = statement.executeQuery(
	             "SELECT SUM(monto) AS total_ingresos, AVG(monto) AS ingreso_promedio, " +
	             "MAX(monto) AS pago_maximo, MIN(monto) AS pago_minimo, COUNT(*) AS total_pagos FROM Pagos")) {

	        if (resultSet.next()) {
	            stats.add(resultSet.getDouble("total_ingresos"));     // Ingresos Totales
	            stats.add(resultSet.getDouble("ingreso_promedio"));  // Ingreso Promedio
	            stats.add(resultSet.getDouble("pago_maximo"));       // Pago más Grande
	            stats.add(resultSet.getDouble("pago_minimo"));       // Pago más Pequeño
	            stats.add((double) resultSet.getInt("total_pagos")); // Número de Pagos 
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error en la conexión a la Base de Datos", e.getMessage(), JOptionPane.ERROR_MESSAGE);
	    }
	    return stats;
	}
	
	
}
