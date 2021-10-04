package com.app;

import com.app.payment.Card;
import com.app.payment.Coin;
import com.app.payment.Note;

public class App {
    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.pressKeypad(12);
        vendingMachine.insertCach(Coin.FIFTY_CENT);
        vendingMachine.insertCach(Note.FIFTY_DOLLAR);
        vendingMachine.insertCach(Note.FIFTY_DOLLAR);
        vendingMachine.pressKeypad(5);
        vendingMachine.insertCach(Coin.FIFTY_CENT);
        vendingMachine.cancel();
        vendingMachine.pressKeypad(7);
        vendingMachine.insertCard(new Card("411111111", "1231", 123));
    }
}
