package com.nasss.taller.motonasss.services;

import com.nasss.taller.motonasss.model.Motoshop;
import com.nasss.taller.motonasss.model.SaleItem;

public class SaleItemPriceService {
    private static SaleItemPriceService instance;
    Motoshop mt = Motoshop.getInstance();

    private SaleItemPriceService() {}
    public static SaleItemPriceService getInstance() {
        if (instance == null) {
            instance = new SaleItemPriceService();
        }
        return instance;
    }

    public double calculatetotal(SaleItem sale) {
        double total = sale.getQuantity() * sale.getProduct().getSalePrice();
        sale.setSalePrice(total);
        return total;
    }


}
