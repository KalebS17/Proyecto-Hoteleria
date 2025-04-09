package model;

public class ReservaServicio {
    private Reserva reserva; // Referencia a Reserva
    private ServicioAdicional servicio; // Referencia a ServicioAdicional
    private int cantidad;
    
	public ReservaServicio(Reserva reserva, ServicioAdicional servicio, int cantidad) {
		this.reserva = reserva;
		this.servicio = servicio;
		this.cantidad = cantidad;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public ServicioAdicional getServicio() {
		return servicio;
	}

	public void setServicio(ServicioAdicional servicio) {
		this.servicio = servicio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
    
    
}
