package com.aks.expencetracker.data.database_models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Table_Expense")
public class ExpenseTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Primary_Key")
    private int primaryKey;
    @ColumnInfo(name = "Expense_Type")
    private String expenseType;
    @ColumnInfo(name = "Date_Of_Expense")
    private String dateOfExpense;
    @ColumnInfo(name = "Expense_Reason_Details")
    private String reason;
    @ColumnInfo(name = "Expense_Amount")
    private double expenseAmount;
    @ColumnInfo(name = "Expense_Income")
    private double expenseIncome;

    public ExpenseTable() {
        //something
    }

    public String getDateOfExpense() {
        return dateOfExpense;
    }

    public void setDateOfExpense(String dateOfExpense) {
        this.dateOfExpense = dateOfExpense;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public double getExpenseIncome() {
        return expenseIncome;
    }

    public void setExpenseIncome(double expenseIncome) {
        this.expenseIncome = expenseIncome;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
