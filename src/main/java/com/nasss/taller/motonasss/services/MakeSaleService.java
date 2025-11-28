package com.nasss.taller.motonasss.services;

import com.nasss.taller.motonasss.model.Motoshop;
import com.nasss.taller.motonasss.model.Sale;
import com.nasss.taller.motonasss.model.SaleItem;

import java.util.ArrayList;

public class MakeSaleService {
    private static MakeSaleService instance;


    private MakeSaleService() {}
    public static MakeSaleService getInstance() {
        if (instance == null) {
            instance = new MakeSaleService();
        }
        return instance;
    }

    public void changeStockItem(SaleItem item) {
        int out = item.getQuantity();
        int present = item.getProduct().getStock();
        item.getProduct().setStock(present - out);
    }
}
