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



    public String generarCodigoCasco(String marca) {
        String[] palabras = marca.split("\\s+"); // separa por espacios
        StringBuilder codigo = new StringBuilder("C"); // prefijo fijo

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                codigo.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }

        // Agregar sufijo aleatorio de 4 caracteres
        String sufijo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        codigo.append(sufijo);

        return codigo.toString();
    }

    public String generarCodigoAceite(String marca) {
        String[] palabras = marca.split("\\s+");
        StringBuilder codigo = new StringBuilder("A"); // prefijo fijo

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                codigo.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }

        String sufijo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        codigo.append(sufijo);

        return codigo.toString();
    }

    public String generarCodigoLuz(String marca) {
        String[] palabras = marca.split("\\s+");
        StringBuilder codigo = new StringBuilder("L"); // prefijo fijo

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                codigo.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }

        String sufijo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        codigo.append(sufijo);

        return codigo.toString();
    }

    public String generarCodigoVenta() {
        return cgs.generarCodigoVenta();
    }

    // Método genérico si luego quieres centralizar la lógica
    public String generarCodigo(String tipo, String marca) {
        char prefijoTipo = Character.toUpperCase(tipo.charAt(0)); // C, A, L...
        char letraMarca = Character.toUpperCase(marca.charAt(0));
        int numero = 100 + random.nextInt(900);
        return "" + prefijoTipo + letraMarca + numero;
    }
}
