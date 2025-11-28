package com.nasss.taller.motonasss.app;

import com.nasss.taller.motonasss.factory.ProductFactory;
import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.*;

import java.time.LocalDateTime;

public class MainPruebas {
    public static void main(String[] args) {
        Motoshop mt = Motoshop.getInstance();
        mt.setName("MotoNasss");
        mt.setNit("123");

        ProductCrudService pcs = ProductCrudService.getInstance();
        ProductPriceService pps = ProductPriceService.getInstance();
        SaleCrudService scs = SaleCrudService.getInstance();
        SaleItemPriceService sis = SaleItemPriceService.getInstance();
        SaleTotalService ss = SaleTotalService.getInstance();

        // crear los productos
        Product h1 = ProductFactory.createProduct(ProductType.KITARRASTRE,"Casco Shaft 501", 50000, 10);
        Product o1 = ProductFactory.createProduct(ProductType.BANDAS,"Oil Honda", 20000, 10);
        Product l1 = ProductFactory.createProduct(ProductType.BATERIA,"Luz Led qwe", 1000000, 1);

        // registrar los productos, y atomaticamente calcula el precio de venta
        pcs.registerProduct(h1);
        pcs.registerProduct(o1);
        pcs.registerProduct(l1);

        System.out.println(mt.toString());
        System.out.println(mt.getProducts());


        System.out.println(mt.getProducts());

        // crear una venta
        Sale s1 = new Sale(LocalDateTime.now(), 0);

        // registrar una venta
        scs.registerSale(s1);

        // crear los items que se van a vender
        SaleItem si1 = new SaleItem(h1, 5, 0);
        SaleItem si2 = new SaleItem(h1, 5, 0);

        // agregar los items a la venta (como si fuera en una factura)
        scs.addSaleItem(s1, si1);
        scs.addSaleItem(s1, si2);
        scs.removeSaleItem(s1, si2);

        System.out.println(sis.calculatetotal(si1));
        System.out.println(sis.calculatetotal(si2));

        System.out.println(ss.calculateTotal(s1));

        System.out.println(s1);
        System.out.println(h1);

    }
}
