package com.nasss.taller.motonasss.services;

public class CodeGeneratorSale {
    private static CodeGeneratorSale instance;
    private static int contadorDiario = 0;
    private static String ultimaFecha = "";


    private CodeGeneratorSale() {}
    public static CodeGeneratorSale getInstance() {
        if (instance == null) {
            instance = new CodeGeneratorSale();
        }
        return instance;
    }


    public synchronized String generarCodigoVenta() {
        String fecha = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        if (!fecha.equals(ultimaFecha)) {
            contadorDiario = 1;
            ultimaFecha = fecha;
        } else {
            contadorDiario++;
        }
        return String.format("V-%s-%04d", fecha, contadorDiario);
    }
}
