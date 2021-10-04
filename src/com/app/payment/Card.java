package com.app.payment;

public class Card {
    private String BIN;
    private String expDate;
    private int CVV;
    public Card(String bIN, String expDate, int cVV) {
        BIN = bIN;
        this.expDate = expDate;
        CVV = cVV;
    }
    public boolean isValidAndHasEnoughBalance() {
        //TO DO: Connect to a service to validate
        return true;
    }

}
