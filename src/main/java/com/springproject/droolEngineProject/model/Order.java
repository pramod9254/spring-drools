package com.springproject.droolEngineProject.model;

// Explicitly adding getters/setters instead of relying on Lombok
// since Drools might have issues with the generated methods
public class Order {

    private String name;
    private String cardType;
    private int discount;
    private Integer price;

    // Explicit getters
    public String getName() {
        return name;
    }

    public String getCardType() {
        return cardType;
    }

    public int getDiscount() {
        return discount;
    }

    public Integer getPrice() {
        return price;
    }

    // Explicit setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
