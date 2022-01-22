package com.aks.expencetracker.data;

public class ExpenseModel {
    String item;

    public ExpenseModel(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
