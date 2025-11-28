package com.nasss.taller.motonasss.model;

public class SaleItem {
    private Product product;
    private int quantity;
    private double salePrice;

    public SaleItem(Product product, int quantity, double salePrice) {
        this.product = product;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return  product.getName() + " " + quantity + " " + salePrice;
    }
}
