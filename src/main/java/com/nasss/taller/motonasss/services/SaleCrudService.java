package com.nasss.taller.motonasss.services;

import com.nasss.taller.motonasss.exceptions.IllegalDuplicateIdException;
import com.nasss.taller.motonasss.model.Motoshop;
import com.nasss.taller.motonasss.model.Sale;
import com.nasss.taller.motonasss.model.SaleItem;

import java.util.List;
import java.util.Optional;

public class SaleCrudService {
    private static SaleCrudService instance;
    private MakeSaleService mss = MakeSaleService.getInstance();

    private Motoshop mt = Motoshop.getInstance();
    private List<Sale> sales = mt.getSales();

    private SaleCrudService() {}

    public static SaleCrudService getInstance(){
        if (instance == null) {
            instance = new SaleCrudService();
        }
        return instance;
    }

    // CREATE
    public void registerSale(Sale sale) {
        if (existSaleWithId(sale.getId())) {
            throw new IllegalDuplicateIdException("Venta duplicada: " + sale.getId());
        }
        mt.getSales().add(sale);
    }


    // READ
    public String readSale(Sale sale) {
        Sale s = findSaleById(sale.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la venta proporcionada"));
        return s.toString();
    }

    // UPDATE
    public void updateSale(Sale sale){
        findSaleById(sale.getId())
                .ifPresentOrElse(
                        p -> {
                            p.setDate(sale.getDate());
                            p.setItems(sale.getItems());
                            p.setTotal(sale.getTotal());
                        },
                        () -> { throw new IllegalArgumentException("Venta no encontrado"); }
                );
    }

    // DELETE
    public void deleteSale(Sale sale) {
        findSaleById(sale.getId())
                .ifPresentOrElse(
                        s -> mt.getSales().remove(s),
                        () -> { throw new IllegalArgumentException("No existe la venta proporcionado"); }
                );
    }

    // metodo para buscar un producto y devolverlo como optional
    public Optional<Sale> findSaleById (String id){
        return sales.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    // metodo para verificar si ya hay un empleado con el code en la lista
    public boolean existSaleWithId(String id){
        return sales.stream()
                .anyMatch(s -> s.getId().equals(id));
    }



    public void addSaleItem(Sale sale, SaleItem saleItem) {
        int itemQuantity = saleItem.getQuantity();
        int productQuantity = saleItem.getProduct().getStock();
        if ((itemQuantity <= productQuantity) && itemQuantity > 0 ) {
            mss.changeStockItem(saleItem);
            sale.getItems().add(saleItem);
        } else {
            throw new IllegalArgumentException("No hay stock suficiente");
        }
    }

    public void removeSaleItem(Sale sale, SaleItem saleItem) {
        sale.getItems().remove(saleItem);
    }
}
