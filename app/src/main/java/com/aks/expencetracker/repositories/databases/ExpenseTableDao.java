package com.aks.expencetracker.repositories.databases;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.aks.expencetracker.models.database_models.ExpenseTable;

import java.util.Date;
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

    @Query("select * from Table_Expense where Date_Of_Expense = :beforeDate ")
    List<ExpenseTable> getAllFromDate(Date beforeDate);

    @Query("select * from Table_Expense where Date_Of_Expense between :beforeDate and :afterDate")
    List<ExpenseTable> getAllFromDateRange(Date beforeDate, Date afterDate);
}
