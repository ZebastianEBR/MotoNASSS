package com.nasss.taller.motonasss.model;

public class Oil extends Product {

    public Oil(String code, String name, double purchasePrice, int stock) {
        super(code, name, purchasePrice, stock);
    }

    @Override
    public double calculateProfit(double purchasePrice, double salePrice) {
        return salePrice - purchasePrice;
    }
}
