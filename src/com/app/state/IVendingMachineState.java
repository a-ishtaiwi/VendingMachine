package com.app.state;

public interface IVendingMachineState {
    public void pressKeypad(String productName, int price);
    public void insertPayment(double amount);
    public void dispenseProductAndChange(String productName, double amount);
    public void cancel(double amount);
}
