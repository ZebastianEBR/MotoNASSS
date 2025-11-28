package com.nasss.taller.motonasss.factory;

import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.CodeGeneratorService;

public class ProductFactory {
    private static CodeGeneratorService cgs = CodeGeneratorService.getInstance();

    public static Product createProduct(ProductType type, String name ,double purchasePrice, int stock) {
        return switch (type) {
            case CASCO -> new Helmet(cgs.generarCodigoCasco(name), name, purchasePrice, stock);
            case LUZ -> new Light(cgs.generarCodigoLuz(name), name, purchasePrice, stock);
            case ACEITE -> new Oil(cgs.generarCodigoAceite(name), name, purchasePrice, stock);
        };
    }
}
