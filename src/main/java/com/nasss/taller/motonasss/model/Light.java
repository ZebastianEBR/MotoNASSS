package com.nasss.taller.motonasss.model;

public class Light extends Product {


    public Light(String code, String name, double purchasePrice, int stock) {
        super(code, name, purchasePrice, stock);
    }

    @Override
    public double calculateProfit(double purchasePrice, double salePrice) {
        return salePrice - purchasePrice;
    }
}
