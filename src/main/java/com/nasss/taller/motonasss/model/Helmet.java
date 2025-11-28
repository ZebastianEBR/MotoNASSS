package com.nasss.taller.motonasss.model;

public class Helmet extends Product {

    public Helmet(String code, String name, double purchasePrice, int stock) {
        super(code, name, purchasePrice, stock);
    }

    @Override
    public double calculateProfit(double purchasePrice, double salePrice) {
        return salePrice - purchasePrice;
    }
}
