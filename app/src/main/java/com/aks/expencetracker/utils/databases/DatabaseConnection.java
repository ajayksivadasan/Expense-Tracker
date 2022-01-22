package com.aks.expencetracker.utils.databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aks.expencetracker.data.database_models.ExpenseTable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection extends SQLiteOpenHelper {
    private static final String DATABASE = "ExpenseDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String EXPENSE_TABLE = "EXPENSE";
    private static final String SL_NO = "SL_NO";
    private static final String DATE = "DATE";
    private static final String REASON = "REASON";
    private static final String EXPENSE = "EXPENSE";
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + EXPENSE_TABLE + "(" + SL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE + " TEXT," + REASON + " TEXT, " + EXPENSE + " TEXT)";
    private static final String DROP_TABLE_EXPENSE = "DROP TABLE IF EXISTS " + EXPENSE_TABLE;
    private static final String SELECT_STAR_FROM_EXPENSE_TABLE = "SELECT * FROM " + EXPENSE_TABLE;
    Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseConnection(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    public boolean insertIntoTableExpense(ArrayList<ExpenseTable> expenseTables) {
        ExpenseTable expenseModels1;
        sqLiteDatabase = this.getReadableDatabase();
        if (!expenseTables.isEmpty()) {
            for (int i = 0; i < expenseTables.size(); i++) {
                expenseModels1 = expenseTables.get(i);
                cursor = sqLiteDatabase.rawQuery(SELECT_STAR_FROM_EXPENSE_TABLE, null);
                sqLiteDatabase = this.getWritableDatabase();
                if (!cursor.moveToFirst()) {
                    sqLiteDatabase.execSQL("INSERT INTO " + EXPENSE_TABLE + " VALUES (1,'" + expenseModels1.getDateOfExpense() + "','" + expenseModels1.getReason() + "'," + expenseModels1.getExpenseAmount() + ")");
                } else {
                    sqLiteDatabase.execSQL("INSERT INTO " + EXPENSE_TABLE + "(" + DATE + "," + REASON + "," + EXPENSE + ") VALUES ('" + expenseModels1.getDateOfExpense() + "','" + expenseModels1.getReason() + "'," + expenseModels1.getExpenseAmount() + ")");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void deleteFromExpense(int slNo) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + EXPENSE_TABLE + " where " + SL_NO + "=" + slNo);
    }

    @SuppressLint("Range")
    public List<ExpenseTable> getDataFromExpenseTable() {
        List<ExpenseTable> expenseTables = new ArrayList<>();
        ExpenseTable expenseTable;
        sqLiteDatabase = this.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery(SELECT_STAR_FROM_EXPENSE_TABLE, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                expenseTable = new ExpenseTable();
                try {
                    expenseTable.setPrimaryKey(cursor.getInt(cursor.getColumnIndex(SL_NO)));
                    expenseTable.setDateOfExpense(cursor.getString(cursor.getColumnIndex(DATE)));
                    Log.e("getDataFromExpense: ", cursor.getString(cursor.getColumnIndex(DATE)));
                    expenseTable.setReason(cursor.getString(cursor.getColumnIndex(REASON)));
                    expenseTable.setExpenseAmount(cursor.getFloat(cursor.getColumnIndex(EXPENSE)));
//                        expenseTable.setIncome(cursor.getFloat(cursor.getColumnIndex(INCOME)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                expenseTables.add(expenseTable);
            } while (cursor.moveToNext());
        }
        return expenseTables;
    }
}
