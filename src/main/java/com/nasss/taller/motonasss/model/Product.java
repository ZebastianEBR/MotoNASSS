package com.nasss.taller.motonasss.model;

public abstract class Product {
    protected String code;
    protected String name;
    protected double purchasePrice;
    protected double salePrice;
    protected int stock;

    protected Product(String code, String name, double purchasePrice, int stock) {
        this.code = code;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // solo se usa una vez calculado el precio de venta
    public abstract double calculateProfit(double purchasePrice, double salePrice);


    @Override
    public String toString() {
        return code +
                " " + name +
                " " + purchasePrice +
                " " + salePrice +
                " " + stock;
    }
}
