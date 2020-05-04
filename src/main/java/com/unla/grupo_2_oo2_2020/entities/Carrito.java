package com.unla.grupo_2_oo2_2020.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity

public class Carrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCarrito;
	@OneToMany
	@JoinColumn(name = "idPedido")
	private Set<Pedido> listaPedidos;
	@Column (name = "fecha")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	public Carrito() {
	}

	public Carrito(LocalDate fecha) {

		this.fecha = fecha;
		this.listaPedidos = new HashSet<Pedido>();
	}

	public long getIdCarrito() {
		return idCarrito;
	}

	protected void setIdCarrito(long idCarrito) {
		this.idCarrito = idCarrito;
	}

	public Set<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(Set<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "\nCarrito [idCarrito=" + idCarrito + ", listaPedidos=" + listaPedidos + ", fecha=" + fecha + ", total="
				+ "]";
	}

}