package com.aks.expencetracker.repositories.databases;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.aks.expencetracker.models.database_models.ExpenseTableRoom;

import java.util.Date;
import java.util.List;

@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseTableRoom expenseTableRoom);

    @Delete
    void delete(ExpenseTableRoom expenseTableRoom);

    @Delete
    void reset(List<ExpenseTableRoom> mainData);

    @Query("SELECT * FROM table_expense")
    List<ExpenseTableRoom> getAll();

    @Query("select * from table_expense where Date_Of_Expense = :beforeDate ")
    List<ExpenseTableRoom> getAllFromDate(Date beforeDate);

    @Query("select * from table_expense where Date_Of_Expense between :beforeDate and :afterDate")
    List<ExpenseTableRoom> getAllFromDateRange(Date beforeDate, Date afterDate);
}
