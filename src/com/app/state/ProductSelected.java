package com.app.state;

public class ProductSelected implements IVendingMachineState{
    @Override
    public void cancel(double balance) {
        System.err.println("Process canceled and " + balance + "$ returned");
    }

    @Override
    public void insertPayment(double amount) {
        System.err.println("Total balance: " + amount + "$");
    }

    @Override
    public void dispenseProductAndChange(String productName, double changeAmount) {
        System.err.println("Please pay first" );
    }

    @Override
    public void pressKeypad(String productName, int price) {
        System.err.println("Snack: " + productName + ", Please pay "+ price +"$ to burchese");
    }
}
