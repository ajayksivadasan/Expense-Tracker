package com.aks.expencetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.expencetracker.adapters.ExpenseViewAdapter;
import com.aks.expencetracker.databases.DatabaseConnection;
import com.aks.expencetracker.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class SecondPageViewItemsEnteredActivity extends AppCompatActivity {
    List<ExpenseModel> expenseModels = new ArrayList<>();
    Context context;
    DatabaseConnection databaseConnection;
    TextView tvTotalIncome, tvTotalExpense;
    RecyclerView rvSecondPage;
    ExpenseViewAdapter expenseViewAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_second_page_view_items_entered);
        initIds();
        databaseConnection = new DatabaseConnection(context);
        expenseModels = databaseConnection.getDataFromExpenseTable();
        expenseViewAdapter = new ExpenseViewAdapter(context, expenseModels);
        rvSecondPage.setLayoutManager(new LinearLayoutManager(context));
        rvSecondPage.setAdapter(expenseViewAdapter);
        float totalIncome = 0.00f, totalExpense = 0.00f;
        for (int i = 0; i < expenseModels.size(); i++) {
            totalExpense += expenseModels.get(i).getExpense();
            totalIncome += expenseModels.get(i).getIncome();
        }
        tvTotalExpense.setText(totalExpense + " Rs");
        tvTotalIncome.setText(totalIncome + " Rs");
    }

    private void initIds() {
        rvSecondPage = findViewById(R.id.rvSecondPage);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
    }
}