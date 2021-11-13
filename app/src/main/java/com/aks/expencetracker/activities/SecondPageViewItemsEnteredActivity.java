package com.aks.expencetracker.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.expencetracker.R;
import com.aks.expencetracker.adapters.ExpenseViewAdapter;
import com.aks.expencetracker.models.database_models.ExpenseTableRoom;
import com.aks.expencetracker.repositories.databases.DatabaseConnection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("SetTextI18n")
public class SecondPageViewItemsEnteredActivity extends AppCompatActivity implements ExpenseViewAdapter.ExpenseCountInterface {
    private List<ExpenseTableRoom> expenseTableRooms = new ArrayList<>();
    private Context context;
    private DatabaseConnection databaseConnection;
    private TextView tvTotalIncome;
    private TextView tvFromDate;
    private TextView tvToDate;
    private TextView tvTotalExpense;
    private RecyclerView rvSecondPage;
    private ExpenseViewAdapter expenseViewAdapter;
    private Calendar myCalendarFromDate;
    private Calendar myCalendarToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_second_page_view_items_entered);
        initIds();
        databaseConnection = new DatabaseConnection(context);
        expenseTableRooms = databaseConnection.getDataFromExpenseTable();
        expenseViewAdapter = new ExpenseViewAdapter(context, expenseTableRooms, this);
        rvSecondPage.setLayoutManager(new LinearLayoutManager(context));
        rvSecondPage.setAdapter(expenseViewAdapter);
        myCalendarFromDate = Calendar.getInstance();
        myCalendarToDate = Calendar.getInstance();
        updateFromDate();
        updateToDate();
        DatePickerDialog.OnDateSetListener pickerListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarFromDate.set(Calendar.YEAR, year);
            myCalendarFromDate.set(Calendar.MONTH, monthOfYear);
            myCalendarFromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFromDate();
        };DatePickerDialog.OnDateSetListener pickerListenerTo = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarToDate.set(Calendar.YEAR, year);
            myCalendarToDate.set(Calendar.MONTH, monthOfYear);
            myCalendarToDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateToDate();
        };
        tvFromDate.setOnClickListener(view -> new DatePickerDialog(context, pickerListener,
                myCalendarFromDate.get(Calendar.YEAR),
                myCalendarFromDate.get(Calendar.MONTH),
                myCalendarFromDate.get(Calendar.DAY_OF_MONTH))
                .show());
        tvToDate.setOnClickListener(view -> new DatePickerDialog(context, pickerListenerTo,
                myCalendarFromDate.get(Calendar.YEAR),
                myCalendarFromDate.get(Calendar.MONTH),
                myCalendarFromDate.get(Calendar.DAY_OF_MONTH))
                .show());
    }

    private void updateToDate() {
        tvFromDate.setText("From: " + myCalendarFromDate.get(Calendar.DATE) + "-" + (myCalendarFromDate.get(Calendar.MONTH) + 1) + "-" + myCalendarFromDate.get(Calendar.YEAR));
    }

    private void updateFromDate() {
        tvToDate.setText("From: " + myCalendarToDate.get(Calendar.DATE) + "-" + (myCalendarToDate.get(Calendar.MONTH) + 1) + "-" + myCalendarToDate.get(Calendar.YEAR));

    }

    private void initIds() {
        rvSecondPage = findViewById(R.id.rvSecondPage);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);
    }

    @Override
    public void setTotals(double incomeTotal, double expenseTotal) {
        tvTotalExpense.setText(expenseTotal + "");
        tvTotalIncome.setText(incomeTotal + "");
    }
}