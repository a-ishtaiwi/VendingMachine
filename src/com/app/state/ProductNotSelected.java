package com.app.state;

public class ProductNotSelected implements IVendingMachineState{
    @Override
    public void cancel(double balance) {
        System.err.println("You haven't started yet, nothing to Cancel");
    }

    @Override
    public void insertPayment(double amount) {
        System.err.println("Please select product first");
    }

    @Override
    public void dispenseProductAndChange(String productName, double changeAmount) {
        System.err.println("Product not selected yet");
    }

    @Override
    public void pressKeypad(String productName, int price) {
        System.err.println("Snack: " + productName + ", Please pay "+ price +"$ to burchese");
    }
}
