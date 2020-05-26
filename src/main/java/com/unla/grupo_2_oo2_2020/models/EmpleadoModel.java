package com.unla.grupo_2_oo2_2020.models;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.unla.grupo_2_oo2_2020.helpers.StaticValuesHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data @EqualsAndHashCode(callSuper = false) @NoArgsConstructor
public class EmpleadoModel extends PersonaModel {
	@NotNull(message = StaticValuesHelper.FRANJAHORARIA_REQUIRED)
	private String franjaHoraria;
	private boolean tipoEmpleado; // true = Vendedor, false = Gerente.
	@Min(value=1, message = StaticValuesHelper.LOCAL_REQUIRED)
	private long idLocal;

	public EmpleadoModel(long idPersona,String nombre, String apellido, LocalDate fechaNacimiento, int dni,
			String franjaHoraria, boolean tipoEmpleado, long idLocal) {
		super(idPersona,nombre, apellido, fechaNacimiento, dni);
		this.franjaHoraria = franjaHoraria;
		this.tipoEmpleado = tipoEmpleado;
		this.idLocal = idLocal;

	}

}
