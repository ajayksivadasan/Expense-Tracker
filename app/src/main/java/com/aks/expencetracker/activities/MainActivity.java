package com.aks.expencetracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.expencetracker.R;
import com.aks.expencetracker.models.database_models.ExpenseTableRoom;
import com.aks.expencetracker.repositories.databases.DatabaseConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final ArrayList<String> spinnerArray = new ArrayList<>();
    String type;
    String rate;
    Context context;
    private EditText etItemType;
    private EditText etItemRate;
    private Button btSubmit;
    private Button btPreviousEntries;
    private Spinner spEntryType;

    //comments added
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        initIds();
        btSubmit.setOnClickListener(v -> {
            type = etItemType.getText().toString();
            rate = etItemRate.getText().toString();
            saveData(type, rate);
        });
        btPreviousEntries.setOnClickListener(v -> {
            Intent intent = new Intent(context, SecondPageViewItemsEnteredActivity.class);
            startActivity(intent);
        });
    }

    private void saveData(String type, String rate) {
        ArrayList<ExpenseTableRoom> expenseTableRooms = new ArrayList<>();
        ExpenseTableRoom expenseTableRoom = new ExpenseTableRoom();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        try {
            expenseTableRoom.setDateOfExpense(sdf.format(date));
            Log.e("saveData: ", expenseTableRoom.getDateOfExpense());
        } catch (Exception e) {
            e.printStackTrace();
        }
        expenseTableRoom.setExpenseAmount(Double.parseDouble(rate));
        expenseTableRoom.setExpenseIncome(0.00f);
        expenseTableRoom.setReason(type);
        expenseTableRooms.add(expenseTableRoom);
        DatabaseConnection dbCon = new DatabaseConnection(this);
        if (dbCon.insertIntoTableExpense(expenseTableRooms)) {
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
        btPreviousEntries = findViewById(R.id.btPreviousEntries);
        etItemRate = findViewById(R.id.etItemRate);
        etItemType = findViewById(R.id.etItemType);
        btSubmit = findViewById(R.id.btSubmit);
        spEntryType = findViewById(R.id.spEntryType);
    }
}