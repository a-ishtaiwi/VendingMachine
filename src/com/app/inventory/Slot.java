package com.app.inventory;

public class Slot {
    private int ID;
    private Snack item;

    public Slot(int ID, Snack item) {
        this.ID = ID;
        this.item = item;
    }

    public int getNum() {
        return ID;
    }

    public void setNum(int ID) {
        this.ID = ID;
    }

    public Snack getSnack() {
        return item;
    }

    public void setSnack(Snack item) {
        this.item = item;
    }

}
