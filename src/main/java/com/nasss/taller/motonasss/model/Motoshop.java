package com.nasss.taller.motonasss.model;


import com.nasss.taller.motonasss.services.ProductCrudService;
import com.nasss.taller.motonasss.services.ProductPriceService;

import java.util.ArrayList;
import java.util.List;

public class Motoshop {
    private static Motoshop instance;

    private String nit;
    private String name;
    private List<Product> products;
    private List<Sale> sales;
    //private List<Customer> customers;
    //private List<Bike> bikes;


    private Motoshop() {
        this.products = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public static Motoshop getInstance(){
        if (instance == null){
            instance = new Motoshop();
        }
        return instance;
    }


    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> employees) {
        this.sales = sales;
    }

    //public List<Customer> getCustomers() {
    //    return customers;
    //}

    //public void setCustomers(List<Customer> customers) {
    //    this.customers = customers;
    //}

    //public List<Bike> getBikes() {
    //    return bikes;
    //}

    //public void setBikes(List<Bike> bikes) {
    //    this.bikes = bikes;
    //}

    @Override
    public String toString() {
        return name      + " " +
                nit      + " " +
                products + " " +
                sales;
    }
}