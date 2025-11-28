package com.nasss.taller.motonasss.model;

import com.nasss.taller.motonasss.services.CodeGeneratorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private String id;
    private LocalDateTime date;
    private List<SaleItem> items;
    private double total;

    private static CodeGeneratorService cgs = CodeGeneratorService.getInstance();


    public Sale(LocalDateTime date, double total) {
        this.id = cgs.generarCodigoVenta();
        this.date = date;
        this.items = new ArrayList<>();
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Sale: \n " +
                "id: " + id + ' ' + "Fecha: " + date + "\n " +
                "Producto: \n" + items + "\n" +
                "Total" + total;
    }
}
