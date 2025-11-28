package com.nasss.taller.motonasss.strategy;

import com.nasss.taller.motonasss.model.IProductPriceCalculator;

public class ChainKitStrategy implements IProductPriceCalculator {


    @Override
    public double calculateSalePrice(double purchasePrice) {
        return purchasePrice + 30000;
    }
}
