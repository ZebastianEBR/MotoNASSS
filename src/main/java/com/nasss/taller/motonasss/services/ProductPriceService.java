package com.nasss.taller.motonasss.services;


import com.nasss.taller.motonasss.exceptions.MissingAnOperation;
import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.strategy.*;

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

        if (product instanceof ChainKit) {
            setPpCalculator(new ChainKitStrategy());
        }
        else if (product instanceof Battery) {
            setPpCalculator(new BatteryStrategy());
        }
        else if (product instanceof BreakPads) {
            setPpCalculator(new BreakPadsStrategy());
        }
        else if (product instanceof Cable) {
            setPpCalculator(new CableStrategy());
        }
        else if (product instanceof Oil) {
            setPpCalculator(new OilStrategy());
        }
        else if (product instanceof TurnSignal) {
            setPpCalculator(new TurnSignalStrategy());
        }
        else if (product instanceof Chain) {
            setPpCalculator(new ChainStrategy());
        }
        else if (product instanceof Bearing) {
            setPpCalculator(new BearingStrategy());
        }
        else {
            setPpCalculator(new BreakShoesStrategy());
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
