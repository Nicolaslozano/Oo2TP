package modelo;

import java.time.LocalDate;

public class Empleado extends Persona {
	private String franjaHoraria;
	private boolean tipoEmpleado; // true = Vendedor, false = Gerente.
	private Local local;

	public Empleado() {
	}

	public Empleado(int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, long dni,
			String franjaHoraria, boolean tipoEmpleado, Local local) {
		super(idPersona,nombre, apellido, fechaNacimiento, dni);
		this.franjaHoraria = franjaHoraria;
		this.tipoEmpleado = tipoEmpleado;
		this.local = local;
	}

	public String getFranjaHoraria() {
		return franjaHoraria;
	}

	public void setFranjaHoraria(String franjaHoraria) {
		this.franjaHoraria = franjaHoraria;
	}

	public boolean isTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(boolean tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@Override
	public String toString() {
		return "Empleado [franjaHoraria=" + franjaHoraria + ", tipoEmpleado=" + tipoEmpleado + ", local=" + local
				+ ", idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
				+ fechaNacimiento + ", dni=" + dni + "]";
	}
	
	

}
