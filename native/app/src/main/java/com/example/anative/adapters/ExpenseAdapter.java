package com.example.anative.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anative.R;
import com.example.anative.models.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private ArrayList<Expense> expenseList;
    private Context context;
    private OnExpenseClickListener listener;

    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
    }

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseList, OnExpenseClickListener listener) {
        this.context = context;
        this.expenseList = expenseList;
        this.listener = listener;
    }

    public void updateData(ArrayList<Expense> newList) {
        this.expenseList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        if (expense == null) return;

        holder.expenseID.setText("ID: " + expense.getExpenseCode());
        holder.expenseDate.setText(expense.getDate());
        holder.expenseClaimant.setText("By: " + expense.getClaimant());
        holder.expenseType.setText(expense.getType());

        String amountValue = String.format("%.2f %s", expense.getAmount(), expense.getCurrency());
        holder.expenseAmount.setText(amountValue);
        holder.expenseAmount.setTextColor(Color.parseColor("#2E7D32"));

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExpenseClick(expense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList != null ? expenseList.size() : 0;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseID, expenseDate, expenseClaimant, expenseType, expenseAmount;
        public View cardView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseID = itemView.findViewById(R.id.expenseID);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expenseClaimant = itemView.findViewById(R.id.expenseClaimant);
            expenseType = itemView.findViewById(R.id.expenseType);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            cardView = itemView.findViewById(R.id.cardExpense);

        }
    }
}