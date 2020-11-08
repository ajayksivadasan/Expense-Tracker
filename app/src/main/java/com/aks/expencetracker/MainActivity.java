package com.aks.expencetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.expencetracker.databases.DatabaseConnection;
import com.aks.expencetracker.models.ExpenseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText etItemType;
    private EditText etItemRate;
    private Button btSubmit;
    private Button btPreviousEntries;
    private Spinner spEntryType;
    String type;
    String rate;
    Context context;
    ArrayList<String> spinnerArray = new ArrayList<>();

    //comments added
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        initIds();
        spinnerArray.add("--Select Type Of Entry--");
        spinnerArray.add("Income");
        spinnerArray.add("Expense");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        spEntryType.setAdapter(stringArrayAdapter);
        btSubmit.setOnClickListener(v -> {
            type = etItemType.getText().toString();
            rate = etItemRate.getText().toString();
            if (spEntryType.getSelectedItemId() != 0 && type != null && !type.equals("") && rate != null && !rate.equals("")) {
                saveData(type, rate, spEntryType.getSelectedItem().toString());
            } else {
                Toast.makeText(context, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
        btPreviousEntries.setOnClickListener(v -> {
            Intent intent = new Intent(context, SecondPageViewItemsEnteredActivity.class);
            startActivity(intent);
        });
    }

    private void saveData(String type, String rate, String selectedItem) {
        ArrayList<ExpenseModel> expenseModels = new ArrayList<>();
        ExpenseModel expenseModel = new ExpenseModel();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        try {
            expenseModel.setDate(sdf.format(date));
            Log.e("saveData: ", expenseModel.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectedItem.equals("Income")) {
            expenseModel.setExpense(0.00f);
            expenseModel.setIncome(Float.parseFloat(rate));
        } else {
            expenseModel.setExpense(Float.parseFloat(rate));
            expenseModel.setIncome(0.00f);
        }
        expenseModel.setReason(type);
        expenseModels.add(expenseModel);
        DatabaseConnection dbCon = new DatabaseConnection(this);
        if (dbCon.insertIntoTableExpense(expenseModels)) {
            Toast.makeText(context, "Successfully Inserted", Toast.LENGTH_SHORT).show();
            etItemRate.setText("");
            etItemRate.setHint(R.string.rate);
            etItemType.setHint(getString(R.string.reason));
            etItemType.setText("");
            etItemType.setFocusable(true);
        } else {
            Toast.makeText(context, "Something Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void initIds() {
        btPreviousEntries = findViewById(R.id.btPreviousEntries);
        etItemRate = findViewById(R.id.etItemRate);
        etItemType = findViewById(R.id.etItemType);
        btSubmit = findViewById(R.id.btSubmit);
        spEntryType = findViewById(R.id.spEntryType);
    }
}