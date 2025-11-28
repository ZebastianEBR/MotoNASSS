package com.nasss.taller.motonasss.services;


import com.nasss.taller.motonasss.exceptions.MissingAnOperation;
import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.strategy.HelmetStrategy;
import com.nasss.taller.motonasss.strategy.LightStrategy;
import com.nasss.taller.motonasss.strategy.OilStrategy;

import java.util.List;

/**
 * Clase que representa el calculo del precio de venta para los productos
 */

public class ProductPriceService {
    private static ProductPriceService instance;
    private IProductPriceCalculator ppCalculator;


    private ProductPriceService(){}
    public static ProductPriceService getInstance(){
        if (instance == null) {
            instance = new ProductPriceService();
        }
        return instance;
    }


    public IProductPriceCalculator getPpCalculator() {
        return ppCalculator;
    }


    public void setPpCalculator(IProductPriceCalculator ppCalculator) {
        this.ppCalculator = ppCalculator;
    }


    public double calculatePrice (Product product) {
        getTypeStrategy(product);

        double price = ppCalculator.calculateSalePrice(product.getPurchasePrice());
        product.setSalePrice(price);
        return price;
    }


    private void getTypeStrategy(Product product) {

        if (!ProductCrudService.getInstance().existProductWithCode(product.getCode())) {
            throw new IllegalArgumentException("No se encuentra el producto proporcionado");
        }

        if (product instanceof Helmet) {
            setPpCalculator(new HelmetStrategy());
        }
        else if (product instanceof Light) {
            setPpCalculator(new LightStrategy());
        }
        else {
            setPpCalculator(new OilStrategy());
        }
    }


    private double calculateProfit(Product product) {
        if (!ProductCrudService.getInstance().existProductWithCode(product.getCode())) {
            throw new IllegalArgumentException("No se encuentra el producto proporcionado");
        }

        if (product.getSalePrice() == 0) {
            throw new MissingAnOperation("Falta calcular el precio de venta");
        }

        return product.calculateProfit(product.getPurchasePrice(), product.getSalePrice());
    }

}
