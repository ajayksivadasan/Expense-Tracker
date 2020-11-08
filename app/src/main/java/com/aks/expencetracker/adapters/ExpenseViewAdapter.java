package com.aks.expencetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.expencetracker.R;
import com.aks.expencetracker.models.ExpenseModel;

import java.util.List;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewAdapterViewHolder> {
    Context context;
    List<ExpenseModel> expenseModels;

    public ExpenseViewAdapter(Context context, List<ExpenseModel> expenseModels) {
        this.context = context;
        this.expenseModels = expenseModels;
    }

    @NonNull
    @Override
    public ExpenseViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_page_list_items, parent, false);
        return new ExpenseViewAdapterViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseViewAdapterViewHolder holder, int position) {
        holder.tvReason.setText(expenseModels.get(position).getReason());
        holder.tvExpense.setText(expenseModels.get(position).getExpense() + " Rs");
        holder.tvIncome.setText(expenseModels.get(position).getIncome() + " Rs");
        holder.tvDate.setText(expenseModels.get(position).getDate());
        holder.tvSlNo.setText(String.valueOf(position + 1));
    }

    @Override

    public int getItemCount() {
        return expenseModels.size();
    }

    public class ExpenseViewAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvSlNo;
        TextView tvDate;
        TextView tvReason;
        TextView tvIncome;
        TextView tvExpense;

        public ExpenseViewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlNo = itemView.findViewById(R.id.tvSlNo);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvExpense = itemView.findViewById(R.id.tvExpense);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvIncome = itemView.findViewById(R.id.tvIncome);
        }
    }
}
