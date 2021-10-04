package com.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.app.inventory.*;
import com.app.state.*;
import com.app.payment.*;

public class VendingMachine {

    private  IVendingMachineState vendingMachineState;
    private double balance;
    private Inventory<Integer, Integer> inventory;
    private Map<Integer, Slot> slots;
    private Snack selectedSnack;
    private int pressedKey;
    public VendingMachine() {
        vendingMachineState = new ProductNotSelected();
        inventory = new Inventory<>();
        slots = new HashMap<>();
        balance = 0.00;
        selectedSnack = null;
        initializeInventory();
    }
    private void initializeInventory() {
        AtomicInteger index = new AtomicInteger();

        String[] snacksNames = {"Sun Chips", "Mr Cheps", "Buntey", "Pop Corn", "Darck Chocolate",
            "Layz", "Doritos", "Cookies", "Gum", "Laviva", "Twix", "Galaxy"};

        List<Snack> products = Arrays.stream(snacksNames).map(str -> {
                    Snack item = new Snack(index.getAndIncrement(), str, (int) (Math.random() * 100));
                    return item;
                }).collect(Collectors.toList());

        for (int i = 0, j = 0; i < 5 * 5; i++, j = i / 5) {
            Snack item = j < products.size() ? products.get(j) : null;
            Slot SnacksSlot = new Slot(i + 1, item);
            inventory.putItem(i + 1, (int) (Math.random() * 15));
            slots.put(i + 1, SnacksSlot);
        }
    }
    public IVendingMachineState getVendingMachineState() {
        return vendingMachineState;
    }
    public void setVendingMachineState(IVendingMachineState vendingMachineState) {
        this.vendingMachineState = vendingMachineState;
    }
    public void cancel() {
        vendingMachineState.cancel(getBalance());
        reset();
    }

    public void insertCach(Cach payment) {
        double amount;
        if (selectedSnack == null){
            BigDecimal bd = new BigDecimal(payment.getAmount()/(double)100).setScale(2, RoundingMode.HALF_UP);
            amount = bd.doubleValue();
            vendingMachineState.insertPayment(amount);
            return;
        }
        int price = selectedSnack.getPrice();
        String name = selectedSnack.getName();
        if (getBalance() >= price) {
            return;
        } else {
            BigDecimal bd = new BigDecimal(payment.getAmount()/(double)100).setScale(2, RoundingMode.HALF_UP);
            amount = bd.doubleValue();
            setBalance(getBalance() + amount);
            vendingMachineState.insertPayment(getBalance());
            if(balance >= price && (vendingMachineState instanceof NoEnoughMoney || vendingMachineState instanceof ProductSelected)) {
                vendingMachineState = new HasEnoughMoney();
                dispenseProductAndChange(name, balance - price);
            } else if (vendingMachineState instanceof ProductSelected){
                vendingMachineState = new NoEnoughMoney();
            }
        }

    }
    public void insertCard(Card payment) {
        int price = selectedSnack.getPrice();
        String name = selectedSnack.getName();
        if (!payment.isValidAndHasEnoughBalance()) {
            return;
        } else {
            setBalance(price);
            vendingMachineState.insertPayment(getBalance());
            if(vendingMachineState instanceof ProductSelected) {
                vendingMachineState = new HasEnoughMoney();
                dispenseProductAndChange(name, 0);
            }
        }
    }

    private void dispenseProductAndChange(String productName, double amount) {
        //TO DO: decrease inventory
        decrementInventory(pressedKey);
        vendingMachineState.dispenseProductAndChange(productName, amount);
        reset();
    }

    private void reset() {
        vendingMachineState = new ProductNotSelected();
        selectedSnack = null;
        setBalance(0.00);
        pressedKey = -1;

    }
    public void pressKeypad(int key) {
        Slot slot = slots.getOrDefault(key, null);
        pressedKey = key;
        if (slot == null || slot.getSnack() == null) {
            System.out.println("Slot" + pressedKey + " is not available!");
        } else if (inventory.getItem(pressedKey) < 1) {
            System.out.println("Out of Stock!");
        }
        selectedSnack = slot.getSnack();
        int price = selectedSnack.getPrice();
        String name = selectedSnack.getName();

        vendingMachineState.pressKeypad(name, price);
        if (vendingMachineState instanceof ProductNotSelected) {
            vendingMachineState = new ProductSelected();
        }
    }
    private double getBalance() {
        return balance;
    }
    private void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     *
     * @param key
     */
    private void decrementInventory(int key) {
        inventory.putItem(key, inventory.getItem(key) - 1);
    }
}
