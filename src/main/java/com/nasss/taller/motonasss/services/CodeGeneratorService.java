package com.nasss.taller.motonasss.services;

import java.util.Random;
import java.util.UUID;

public class CodeGeneratorService {
    private static CodeGeneratorService instance;
    private CodeGeneratorSale cgs = CodeGeneratorSale.getInstance();
    private final Random random = new Random();

    private CodeGeneratorService() {}
    public static CodeGeneratorService getInstance() {
        if (instance == null) {
            instance = new CodeGeneratorService();
        }
        return instance;
    }


    public String generarCodigoProducto(String nombre) {
        // Dividir el nombre en palabras
        String[] palabras = nombre.trim().split("\\s+");
        StringBuilder codigo = new StringBuilder();

        // Tomar la primera letra de cada palabra
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                codigo.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }

        // Agregar sufijo aleatorio (4 caracteres)
        String sufijo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        codigo.append(sufijo);

        return codigo.toString();
    }

    public String generarCodigoVenta() {
        return cgs.generarCodigoVenta();
    }
}
