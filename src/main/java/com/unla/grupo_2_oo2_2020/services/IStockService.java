package com.unla.grupo_2_oo2_2020.services;

import java.util.List;

import com.unla.grupo_2_oo2_2020.entities.Stock;
import com.unla.grupo_2_oo2_2020.models.StockModel;

public interface IStockService {
    
    public List<Stock> getAll();
 
    public Stock findById(long idLocal);
    
    public boolean comprobarStock(long idProducto, int cantidad);

	StockModel insertOrUpdate(StockModel stockModel);
}