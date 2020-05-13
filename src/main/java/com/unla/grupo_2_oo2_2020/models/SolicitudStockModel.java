package com.unla.grupo_2_oo2_2020.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SolicitudStockModel {

	private long idSolicitud;
	private LocalDate fecha;
	private ProductoModel producto;
	private int cantidad;
	private EmpleadoModel vendedor;
	private EmpleadoModel colaborador;
	private boolean aceptado;

	public SolicitudStockModel() {
	}

	public SolicitudStockModel(long idSolicitud, LocalDate fecha, ProductoModel producto, int cantidad, EmpleadoModel vendedor) {

		super();
		
		this.idSolicitud = idSolicitud;
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
		this.empleados.add(vendedor);

		this.aceptado = false;
	}

	public long getIdSolicitud() {
		return idSolicitud;
	}

	protected void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public ProductoModel getProducto() {
		return producto;
	}

	public void setProducto(ProductoModel producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public EmpleadoModel getVendedor() {

		return this.vendedor;
	}

	public void setVendedor(EmpleadoModel vendedor) {

		this.vendedor = vendedor;
	}

	public EmpleadoModel getColaborador() {

		return this.colaborador;
	}

	public void setColaborador(EmpleadoModel colaborador) {

		this.colaborador = colaborador;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	@Override
	public String toString() {
		return "SolicitudStock [fecha=" + fecha + ", producto=" + producto + ", cantidad=" + cantidad +
		 "empleados: "+ vendedor + colaborador + ", aceptado=" + aceptado + "]";
	}
}