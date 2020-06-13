package com.unla.grupo_2_oo2_2020.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo_2_oo2_2020.converters.EmpleadoConverter;
import com.unla.grupo_2_oo2_2020.converters.PedidoConverter;
import com.unla.grupo_2_oo2_2020.entities.Empleado;
import com.unla.grupo_2_oo2_2020.entities.Local;
import com.unla.grupo_2_oo2_2020.entities.Pedido;
import com.unla.grupo_2_oo2_2020.helpers.StaticValuesHelper;
import com.unla.grupo_2_oo2_2020.models.EmpleadoModel;
import com.unla.grupo_2_oo2_2020.models.PedidoModel;
import com.unla.grupo_2_oo2_2020.repository.IEmpleadoRepository;
import com.unla.grupo_2_oo2_2020.repository.IPedidoRepository;
import com.unla.grupo_2_oo2_2020.services.IEmpleadoService;
import com.unla.grupo_2_oo2_2020.services.ILocalService;
import com.unla.grupo_2_oo2_2020.services.IPedidoService;

@Service("empleadoService")
public class EmpleadoService implements IEmpleadoService {

	@Autowired
	@Qualifier("empleadoRepository")
	private IEmpleadoRepository empleadoRepository;
	
	
	@Autowired
	@Qualifier("pedidoConverter")
	private PedidoConverter pedidoConverter;
	
	@Autowired
	@Qualifier("empleadoConverter")
	private EmpleadoConverter empleadoConverter;
	
	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;
	
	@Autowired
	@Qualifier("localService")
	private ILocalService localService;

	@Override
	public Empleado findById(long idPersona) {

		return empleadoRepository.findByIdPersona(idPersona);
	}

	@Override
	public Empleado findByIdFetchEagerly(long idPersona) {

		return empleadoRepository.findByIdPersona_wDependencies(idPersona);
	}

	@Override
	public List<Empleado> findByLocal(Local local) {
		return empleadoRepository.findByLocal(local);
	}

	@Override
	public List<Empleado> getAll() {
		// TODO Auto-generated method stub
		return empleadoRepository.findAll();
	}

	@Override
	public EmpleadoModel insertOrUpdate(EmpleadoModel empleadoModel) {
		// TODO Auto-generated method stub
		Empleado empleado = empleadoConverter.modelToEntity(empleadoModel);

		empleado.setLocal(localService.findById(empleadoModel.getIdLocal()));

		empleadoRepository.save(empleado);
		return empleadoConverter.entityToModel(empleado);
	}

	@Override
	public double calcularSueldo(int mes, EmpleadoModel empleadoModel) {
		double sueldo = 0;
		double porcentajesueldo;

		for (Pedido pedido : pedidoService.findByLocal(localService.findById(empleadoModel.getIdLocal()))) {
			

			porcentajesueldo = 0;

			if (pedido.getFecha().getMonthValue() == mes) {

				if (pedido.getVendedorOriginal().getDni() == empleadoModel.getDni()
						&& pedido.getVendedorAuxiliar() == null) {

					porcentajesueldo = pedidoService.getTotal(pedidoConverter.entityToModel(pedido))
							* StaticValuesHelper.COMISION_ORIGINAL;
					sueldo += porcentajesueldo;
					
					}else if (pedido.getVendedorAuxiliar() != null) {

					if (pedido.getVendedorOriginal().getDni() == empleadoModel.getDni()) {
						porcentajesueldo = pedidoService.getTotal(pedidoConverter.entityToModel(pedido))
								* StaticValuesHelper.COMISION_ORIGINALSUP;
						sueldo += porcentajesueldo;

					}
					if (pedido.getVendedorAuxiliar().getDni() == empleadoModel.getDni()) {
						porcentajesueldo = pedidoService.getTotal(pedidoConverter.entityToModel(pedido)) 
								* StaticValuesHelper.COMISION_AUXILIAR;
						sueldo += porcentajesueldo;

					}

				}

			}

		}
		return sueldo;
	}
	
	
	@Override
	public void removeById(long idPersona) {
		empleadoRepository.deleteById(idPersona);
	}

	@Override
	public Empleado findByDni(int dni) {
		return empleadoRepository.findByDni(dni);
	}

}