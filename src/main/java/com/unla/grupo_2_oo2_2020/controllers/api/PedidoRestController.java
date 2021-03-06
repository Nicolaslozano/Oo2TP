package com.unla.grupo_2_oo2_2020.controllers.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.unla.grupo_2_oo2_2020.converters.PedidoConverter;
import com.unla.grupo_2_oo2_2020.entities.Pedido;
import com.unla.grupo_2_oo2_2020.helpers.StaticValuesHelper;
import com.unla.grupo_2_oo2_2020.helpers.ViewRouteHelper;
import com.unla.grupo_2_oo2_2020.models.PedidoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo_2_oo2_2020.services.IEmpleadoService;
import com.unla.grupo_2_oo2_2020.services.ILocalService;
import com.unla.grupo_2_oo2_2020.services.IPedidoService;
import com.unla.grupo_2_oo2_2020.services.IStockService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoRestController {

    @Autowired
    @Qualifier("stockService")
    private IStockService stockService;

    @Autowired
    @Qualifier("pedidoService")
    private IPedidoService pedidoService;

    @Autowired
    @Qualifier("empleadoService")
    private IEmpleadoService empleadoService;

    @Autowired
    @Qualifier("pedidoConverter")
    private PedidoConverter pedidoConverter;

    @Autowired
    @Qualifier("localService")
    private ILocalService localService;

    @GetMapping("/getPedidos")
    public ResponseEntity<List<PedidoModel>> getPedidos() {

        List<PedidoModel> pedidos = new ArrayList<PedidoModel>();

        for (Pedido pedido : pedidoService.getAll()) {
            // why? porque sino no anda el JSON.parse cuando lo manda a la vista
            pedidos.add(pedidoConverter.entityToModel(pedido));
        }

        return new ResponseEntity<List<PedidoModel>>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/getPedido/{idPedido}")
    public ResponseEntity<PedidoModel> getPedido(@PathVariable("idPedido") long id) {

        PedidoModel pedido = new PedidoModel();

        pedido = pedidoConverter.entityToModel(pedidoService.findById(id));

        return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
    }

    @GetMapping("/getPedidos/{idLocal}")
    public ResponseEntity<List<PedidoModel>> get(@PathVariable("idLocal") long id) {

        List<PedidoModel> pedidos = new ArrayList<PedidoModel>();

        for (Pedido pedido : pedidoService.findByLocal(localService.findById(id))) {
            // why? porque sino no anda el JSON.parse cuando lo manda a la vista
            pedidos.add(pedidoConverter.entityToModel(pedido));
        }

        return new ResponseEntity<List<PedidoModel>>(pedidos, HttpStatus.OK);
    }

    @PostMapping("/sendPedido")
    public ResponseEntity<?> sendPedido(@Valid @RequestBody PedidoModel pedidoModel, Errors errors) {

        Map<String, String> result = new HashMap<String, String>();

        if (errors.hasErrors()) {

            for (ObjectError error : errors.getAllErrors()) {

                result.put(error.getDefaultMessage(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(result);
        }

        if (pedidoModel.getIdVendedorAuxiliar() > 0) { //luego de enviar el pedido al otro negocio

            pedidoModel.setEstado(StaticValuesHelper.PEDIDO_PENDIENTE);
            pedidoService.insertOrUpdate(pedidoModel);
            result.put(StaticValuesHelper.ORDER_PENDING_SENT, "Pedido pendiente enviado");
        } else {

            if (stockService.comprobarStock(pedidoModel, true)) {

                pedidoModel.setEstado(StaticValuesHelper.PEDIDO_ACEPTADO);
                pedidoService.insertOrUpdate(pedidoModel);
                result.put(StaticValuesHelper.ORDER_ACCEPTED,
                        "Pedido despachado correctamente a "
                                + localService.findById(pedidoModel.getIdLocal()).getDireccion() + " por un total de $"
                                + pedidoService.getTotal(pedidoModel));

                result.put("redirect", ViewRouteHelper.CLIENTE_ROOT);
            } else if (!localService.getValidLocals(pedidoModel).isEmpty()) {
                //significa que al usuario le salta el modal para decidir si enviar el pedido a otro local
                result.put(StaticValuesHelper.ORDER_PENDING, "Pedido pendiente");
            } else {

                pedidoModel.setEstado(StaticValuesHelper.PEDIDO_RECHAZADO);
                pedidoService.insertOrUpdate(pedidoModel);
                result.put(StaticValuesHelper.ORDER_REJECTED, "Pedido rechazado");
            }
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/handlePedido")
    public ResponseEntity<?> handlePedido(@Valid @RequestBody PedidoModel pedidoModel, Errors errors) {

        HashMap<String, String> result = new HashMap<String, String>();
        PedidoModel pedidoExterno = new PedidoModel(pedidoModel.getIdPedido(), pedidoModel.getIdProducto(), pedidoModel.getCantidad(),
                pedidoModel.getIdLocal(), pedidoModel.getIdCliente(), pedidoModel.getIdVendedorOriginal(),
                pedidoModel.getIdVendedorAuxiliar(), pedidoModel.getEstado(), pedidoModel.getFecha());

        pedidoExterno.setIdLocal(empleadoService.findById(pedidoModel.getIdVendedorAuxiliar()).getLocal().getIdLocal());

        if (errors.hasErrors()) {

            for (ObjectError error : errors.getAllErrors()) {

                result.put(error.getDefaultMessage(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(result);
        } else {

            switch (pedidoModel.getEstado()) {
                case StaticValuesHelper.PEDIDO_ACEPTADO:
                    if (!stockService.comprobarStock(pedidoExterno, true)) { // si ya no hay stock disponible
                        pedidoModel.setEstado(StaticValuesHelper.PEDIDO_RECHAZADO);
                        result.put(StaticValuesHelper.ORDER_REJECTED,
                                "Ya no hay stock disponible para satisfacer el pedido");
                    } else {
                        result.put(StaticValuesHelper.ORDER_ACCEPTED, "Pedido aceptado");
                    }
                    break;
                case StaticValuesHelper.PEDIDO_RECHAZADO:
                    result.put(StaticValuesHelper.ORDER_REJECTED,
                    "Pedido rechazado");
                    break;
                default:
                    break;
            }

            pedidoService.insertOrUpdate(pedidoModel);
        }

        return ResponseEntity.ok(result);
    }

}