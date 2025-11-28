package com.nasss.taller.motonasss.services;

import com.nasss.taller.motonasss.model.Sale;
import com.nasss.taller.motonasss.model.SaleItem;

public class SaleTotalService {
    private static SaleTotalService instance;

    private SaleTotalService() {}

    public static SaleTotalService getInstance(){
        if (instance == null) {
            instance = new SaleTotalService();
        }
        return instance;
    }

    public double calculateTotal(Sale sale) {
        double total = 0;
        for (SaleItem i : sale.getItems()) {
            total += i.getSalePrice();
        }
        sale.setTotal(total);
        return total;
    }
}
