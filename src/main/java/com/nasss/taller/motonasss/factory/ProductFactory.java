package com.nasss.taller.motonasss.factory;

import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.CodeGeneratorService;

public class ProductFactory {
    private static CodeGeneratorService cgs = CodeGeneratorService.getInstance();

    public static Product createProduct(ProductType type, String name ,double purchasePrice, int stock) {
        return switch (type) {
            case KITARRASTRE -> new ChainKit(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case BATERIA -> new Battery(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case BANDAS -> new BrakeShoes(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case PASTILLAS -> new BreakPads(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case BALINERA -> new Bearing(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case GUAYA -> new Cable(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case ACEITE -> new Oil(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case DIRECCIONAL -> new TurnSignal(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
            case CADENA -> new Chain(cgs.generarCodigoProducto(name), name, purchasePrice, stock);
        };
    }
}
