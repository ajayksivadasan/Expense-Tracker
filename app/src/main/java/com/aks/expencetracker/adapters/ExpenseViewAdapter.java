package com.aks.expencetracker.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.expencetracker.R;
import com.aks.expencetracker.databases.DatabaseConnection;
import com.aks.expencetracker.models.ExpenseModel;

import java.util.List;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewAdapterViewHolder> {
    final Context context;
    final DatabaseConnection databaseConnection;
    private final ExpenseCountInterface countInterface;
    private List<ExpenseModel> expenseModels;

    public ExpenseViewAdapter(Context context, List<ExpenseModel> expenseModels, ExpenseCountInterface countInterface) {
        this.context = context;
        this.expenseModels = expenseModels;
        databaseConnection = new DatabaseConnection(context);
        this.countInterface = countInterface;
    }

    void updateAdapter(List<ExpenseModel> expenseModels) {
        this.expenseModels = expenseModels;
        double sumIncome = 0;
        double expenseTotal = 0;
        for (ExpenseModel model : expenseModels) {
            sumIncome += model.getIncome();
            expenseTotal += model.getIncome();
        }
        countInterface.setTotals(sumIncome, expenseTotal);
        notifyDataSetChanged();
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
        if (expenseModels.get(position).getExpense() < 0) {
            holder.tvExpense.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.tvExpense.setText(expenseModels.get(position).getExpense() + "");
        holder.tvIncome.setText(expenseModels.get(position).getIncome() + "");
        holder.tvDate.setText(expenseModels.get(position).getDate());
        holder.tvSlNo.setText(String.valueOf(position + 1));
        holder.llLongPressDel.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context).setTitle("Delete").setMessage("do You want to delete " + expenseModels.get(position).getReason()).setPositiveButton("yes", (dialog, which) -> {
                databaseConnection.deleteFromExpense(expenseModels.get(position).getPrimaryKey());
                dialog.dismiss();
                updateAdapter(databaseConnection.getDataFromExpenseTable());
            }).setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        });
    }

    @Override

    public int getItemCount() {
        return expenseModels.size();
    }

    public interface ExpenseCountInterface {
        void setTotals(double incomeTotal, double expenseTotal);
    }

    public static class ExpenseViewAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView tvSlNo;
        final TextView tvDate;
        final TextView tvReason;
        final TextView tvIncome;
        final TextView tvExpense;
        final LinearLayout llLongPressDel;

        public ExpenseViewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlNo = itemView.findViewById(R.id.tvSlNo);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvExpense = itemView.findViewById(R.id.tvExpense);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvIncome = itemView.findViewById(R.id.tvIncome);
            llLongPressDel = itemView.findViewById(R.id.llLongPressDel);
        }
    }
}
