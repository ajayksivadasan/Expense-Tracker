package com.aks.expencetracker.utils.databases;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.aks.expencetracker.data.database_models.ExpenseTable;

import java.util.List;

@Dao
public interface ExpenseTableDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseTable expenseTable);

    @Insert(onConflict = REPLACE)
    void insert(List<ExpenseTable> expenseTableList);

    @Delete
    void delete(ExpenseTable expenseTable);

    @Delete
    void reset(List<ExpenseTable> mainData);

    @Query("SELECT * FROM Table_Expense")
    List<ExpenseTable> getAllExpense();
}
