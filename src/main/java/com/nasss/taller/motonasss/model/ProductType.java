package com.nasss.taller.motonasss.model;

import java.util.List;

public enum ProductType {
    KITARRASTRE(List.of("kitarrastre", "kitrelacion", "kit arrastre", "kit de arrastre")),
    BATERIA(List.of("bateria")),
    BANDAS(List.of("banda", "bandas")),
    PASTILLAS(List.of("pastilla", "pastillas")),
    BALINERA(List.of("balinera", "balineras")),
    GUAYA(List.of("guaya" ,"guayas")),
    ACEITE(List.of("aceite")),
    DIRECCIONAL(List.of("direccional", "direccionales")),
    CADENA(List.of("cadena"));

    private final List<String> keywords;

    ProductType(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public boolean matchesName(String name) {
        String lowerName = name.toLowerCase();
        return keywords.stream().anyMatch(lowerName::contains);
    }
}