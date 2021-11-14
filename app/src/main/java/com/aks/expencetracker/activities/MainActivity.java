package com.aks.expencetracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.expencetracker.R;
import com.aks.expencetracker.models.database_models.ExpenseTable;
import com.aks.expencetracker.repositories.databases.DatabaseConnection;
import com.aks.expencetracker.repositories.databases.RoomDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private RoomDB roomDBInstance;
    private Context context;
    private EditText etItemType;
    private EditText etItemRate;
    private Button btSubmit;
    private Date date;
    private ExpenseTable expenseTable;

    //comments added
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        initIds();
        btSubmit.setOnClickListener(v -> {
            String type = etItemType.getText().toString();
            String rate = etItemRate.getText().toString();
            saveData(type, rate);
            saveDataRoom(type, rate);
        });
    }

    private void saveDataRoom(String description, String rate) {
        date = Calendar.getInstance().getTime();
        expenseTable = new ExpenseTable();
        try {
            expenseTable.setDateOfExpense(sdf.format(date));
            Log.e("saveData: ", expenseTable.getDateOfExpense());
        } catch (Exception e) {
            e.printStackTrace();
            expenseTable.setDateOfExpense("date unavailable");
        }
        expenseTable.setExpenseAmount(Double.parseDouble(rate));
        expenseTable.setExpenseIncome(0.00f);
        expenseTable.setReason(description);
        roomDBInstance.mainDao().insert(expenseTable);
    }

    private void saveData(String type, String rate) {
        ArrayList<ExpenseTable> expenseTables = new ArrayList<>();
        expenseTable = new ExpenseTable();
        try {
            expenseTable.setDateOfExpense(sdf.format(date));
            Log.e("saveData: ", expenseTable.getDateOfExpense());
        } catch (Exception e) {
            e.printStackTrace();
        }
        expenseTable.setExpenseAmount(Double.parseDouble(rate));
        expenseTable.setExpenseIncome(0.00f);
        expenseTable.setReason(type);
        expenseTables.add(expenseTable);
        DatabaseConnection dbCon = new DatabaseConnection(this);
        if (dbCon.insertIntoTableExpense(expenseTables)) {
            Toast.makeText(context, "Successfully Inserted", Toast.LENGTH_SHORT).show();
            etItemRate.setText("");
            etItemRate.setHint(R.string.str_price_expense_debit);
            etItemType.setHint(getString(R.string.reason));
            etItemType.setText("");
            etItemType.setFocusable(true);
        } else {
            Toast.makeText(context, "Something Error Occurred", Toast.LENGTH_SHORT).show();
        }
        dbCon.close();
    }

    private void initIds() {
        //initialise ids
        etItemRate = findViewById(R.id.etItemRate);
        etItemType = findViewById(R.id.etItemType);
        btSubmit = findViewById(R.id.btSubmit);

        // roomDb Instance
        roomDBInstance = RoomDB.getInstance(context);
    }
}