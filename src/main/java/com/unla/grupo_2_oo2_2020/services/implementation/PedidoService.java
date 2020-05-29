package com.unla.grupo_2_oo2_2020.services.implementation;

import com.unla.grupo_2_oo2_2020.entities.Local;
import com.unla.grupo_2_oo2_2020.models.PedidoModel;
import com.unla.grupo_2_oo2_2020.services.ILocalService;
import com.unla.grupo_2_oo2_2020.services.ILoteService;
import com.unla.grupo_2_oo2_2020.services.IPedidoService;
import com.unla.grupo_2_oo2_2020.services.IProductoService;
import com.unla.grupo_2_oo2_2020.services.IStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pedidoService")
public class PedidoService implements IPedidoService {

    @Autowired
    @Qualifier("localService")
    private ILocalService localService;

    @Autowired
    @Qualifier("productoService")
    private IProductoService productoService;

    @Autowired
    @Qualifier("stockService")
    private IStockService stockService;

    @Autowired
    @Qualifier("loteService")
    private ILoteService loteService;

	@Override
	public boolean validatePedido(PedidoModel pedidoModel) {

		boolean DemandFullFill = stockService.comprobarStock(pedidoModel);
		long i = 0;

		while (DemandFullFill == false && i < localService.getAll().size()) {
		 Local local = localService.findById(i); 
			if (local != null) {
				if (cederStock() = true) {
					stockService.comprobarStock(pedidoModel);
					DemandFullFill = true;
				} else {
					i++;
				}
				// implement exception that says "No more locals to ask for lotes"
			}

		}

		return DemandFullFill;
	}

	@Override
	public double getTotal(PedidoModel pedidoModel) {

		return ((productoService.findById(pedidoModel.getIdProducto())).getPrecio() * pedidoModel.getCantidad());
	}

}