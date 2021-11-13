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
import com.aks.expencetracker.models.database_models.ExpenseTableRoom;
import com.aks.expencetracker.repositories.databases.DatabaseConnection;

import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewAdapterViewHolder> {
    final Context context;
    final DatabaseConnection databaseConnection;
    private final ExpenseCountInterface countInterface;
    private List<ExpenseTableRoom> expenseTableRooms;

    public ExpenseViewAdapter(Context context, List<ExpenseTableRoom> expenseTableRooms, ExpenseCountInterface countInterface) {
        this.context = context;
        this.expenseTableRooms = expenseTableRooms;
        databaseConnection = new DatabaseConnection(context);
        this.countInterface = countInterface;
    }

    void updateAdapter(List<ExpenseTableRoom> expenseTableRooms) {
        this.expenseTableRooms = expenseTableRooms;
        double sumIncome = 0;
        double expenseTotal = 0;
        for (ExpenseTableRoom model : expenseTableRooms) {
            sumIncome += model.getExpenseIncome();
            expenseTotal += model.getExpenseIncome();
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
        holder.tvReason.setText(expenseTableRooms.get(position).getReason());
        if (expenseTableRooms.get(position).getExpenseAmount() < 0) {
            holder.tvExpense.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.tvExpense.setText(expenseTableRooms.get(position).getExpenseAmount() + "");
        holder.tvIncome.setText(expenseTableRooms.get(position).getExpenseIncome() + "");
        holder.tvDate.setText(expenseTableRooms.get(position).getDateOfExpense());
        holder.tvSlNo.setText(String.valueOf(position + 1));
        holder.llLongPressDel.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("do You want to delete " + expenseTableRooms.get(position).getReason())
                    .setPositiveButton("yes", (dialog, which) -> {
                        databaseConnection.deleteFromExpense(expenseTableRooms.get(position).getPrimaryKey());
                        dialog.dismiss();
                        updateAdapter(databaseConnection.getDataFromExpenseTable());
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        });
    }

    @Override

    public int getItemCount() {
        return expenseTableRooms.size();
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
