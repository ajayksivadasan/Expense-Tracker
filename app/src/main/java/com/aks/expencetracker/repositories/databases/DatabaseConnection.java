package com.aks.expencetracker.repositories.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aks.expencetracker.models.ExpenseModel;

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

    public boolean insertIntoTableExpense(ArrayList<ExpenseModel> expenseModels) {
        ExpenseModel expenseModels1;
        sqLiteDatabase = this.getReadableDatabase();
        if (!expenseModels.isEmpty()) {
            for (int i = 0; i < expenseModels.size(); i++) {
                expenseModels1 = expenseModels.get(i);
                cursor = sqLiteDatabase.rawQuery(SELECT_STAR_FROM_EXPENSE_TABLE, null);
                sqLiteDatabase = this.getWritableDatabase();
                if (!cursor.moveToFirst()) {
                    sqLiteDatabase.execSQL("INSERT INTO " + EXPENSE_TABLE + " VALUES (1,'" + expenseModels1.getDate() + "','" + expenseModels1.getReason() + "'," + expenseModels1.getExpense() + ")");
                } else {
                    sqLiteDatabase.execSQL("INSERT INTO " + EXPENSE_TABLE + "(" + DATE + "," + REASON + "," + EXPENSE + ") VALUES ('" + expenseModels1.getDate() + "','" + expenseModels1.getReason() + "'," + expenseModels1.getExpense() + ")");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void deleteFromExpense(int slNo) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+EXPENSE_TABLE+" where "+SL_NO+"="+slNo);
    }

    public List<ExpenseModel> getDataFromExpenseTable() {
        List<ExpenseModel> expenseModels = new ArrayList<>();
        expenseModels.clear();
        ExpenseModel expenseModel;
        sqLiteDatabase = this.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery(SELECT_STAR_FROM_EXPENSE_TABLE, null);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy/MM/dd", Locale.getDefault());
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    expenseModel = new ExpenseModel();
                    try {
                        expenseModel.setPrimaryKey(cursor.getInt(cursor.getColumnIndex(SL_NO)));
                        expenseModel.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        Log.e("getDataFromExpense: ", cursor.getString(cursor.getColumnIndex(DATE)));
                        expenseModel.setReason(cursor.getString(cursor.getColumnIndex(REASON)));
                        expenseModel.setExpense(cursor.getFloat(cursor.getColumnIndex(EXPENSE)));
//                        expenseModel.setIncome(cursor.getFloat(cursor.getColumnIndex(INCOME)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    expenseModels.add(expenseModel);
                } while (cursor.moveToNext());
            }
        }
        return expenseModels;
    }
}
