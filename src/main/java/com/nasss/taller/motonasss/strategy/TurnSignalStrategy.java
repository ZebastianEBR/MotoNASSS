package com.nasss.taller.motonasss.strategy;

import com.nasss.taller.motonasss.model.IProductPriceCalculator;

public class TurnSignalStrategy implements IProductPriceCalculator  {
    @Override
    public double calculateSalePrice(double purchasePrice) {
        return purchasePrice + 4000;
    }
}
