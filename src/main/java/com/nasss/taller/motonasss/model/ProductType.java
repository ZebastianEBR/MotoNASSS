package com.nasss.taller.motonasss.model;

import java.util.List;

public enum ProductType {
    CASCO(List.of("casco", "helmet")),
    LUZ(List.of("luz", "farola", "light", "exploradora", "bombillo")),
    ACEITE(List.of("aceite", "oil"));

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