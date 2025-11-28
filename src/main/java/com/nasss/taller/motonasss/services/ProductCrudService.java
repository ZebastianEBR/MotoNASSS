package com.nasss.taller.motonasss.services;

import com.nasss.taller.motonasss.exceptions.IllegalDuplicateIdException;
import com.nasss.taller.motonasss.model.Motoshop;
import com.nasss.taller.motonasss.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Clase que representa los servicios del CRUD para el producto
 */
public class ProductCrudService {
    private static ProductCrudService instance;

    private final Motoshop mt = Motoshop.getInstance();
    private final List<Product> products = mt.getProducts();
    private final ProductPriceService pps = ProductPriceService.getInstance();

    private ProductCrudService() {}

    public static ProductCrudService getInstance() {
        if (instance == null) {
            instance = new ProductCrudService();
        }
        return instance;
    }

    // CREATE
    public void registerProduct(Product product) {
        if (existProductWithCode(product.getCode())) {
            throw new IllegalDuplicateIdException("Producto duplicado por código: " + product.getCode());
        }

        if (existProductByName(product.getName())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el mismo nombre: " + product.getName()
            );
        }

        mt.getProducts().add(product);
        pps.calculatePrice(product);
    }

    // READ
    public String readProduct(Product product) {
        Product p = findProductById(product.getCode())
                .orElseThrow(() -> new IllegalArgumentException("No existe el producto proporcionado"));
        return p.toString();
    }

    // UPDATE
    public void updateProduct(Product product) {
        findProductById(product.getCode())
                .ifPresentOrElse(
                        p -> {
                            p.setPurchasePrice(product.getPurchasePrice());
                            p.setSalePrice(product.getSalePrice());
                            p.setStock(product.getStock());
                        },
                        () -> { throw new IllegalArgumentException("Producto no encontrado"); }
                );
    }

    // DELETE
    public void deleteProduct(Product product) {
        findProductById(product.getCode())
                .ifPresentOrElse(
                        p -> mt.getProducts().remove(p),
                        () -> { throw new IllegalArgumentException("No existe el producto proporcionado"); }
                );
    }

    // Buscar producto por código
    public Optional<Product> findProductById(String code) {
        return products.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst();
    }

    // Verificar si existe producto por código
    public boolean existProductWithCode(String code) {
        return products.stream()
                .anyMatch(p -> p.getCode().equals(code));
    }

    // Verificar si existe producto por nombre
    public boolean existProductByName(String name) {
        String normalizedName = name.replaceAll("\\s+", "").toLowerCase();
        return products.stream()
                .anyMatch(p -> p.getName().replaceAll("\\s+", "").toLowerCase().equals(normalizedName));
    }
}
