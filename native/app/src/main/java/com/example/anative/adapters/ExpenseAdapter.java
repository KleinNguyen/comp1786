package com.example.anative.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        if (position < 0 || position >= expenseList.size()) return;

        Expense expense = expenseList.get(position);
        if (expense == null) return;

        holder.expenseID.setText("ID: " + expense.getExpenseCode());
        holder.expenseDate.setText(expense.getDate() != null ? expense.getDate() : "");
        holder.expenseClaimant.setText("By: " + (expense.getClaimant() != null ? expense.getClaimant() : ""));
        holder.expenseType.setText(expense.getType() != null ? expense.getType() : "");

        String amountValue = String.format("%.2f %s", expense.getAmount(),
                expense.getCurrency() != null ? expense.getCurrency() : "");

        SpannableStringBuilder amountBuilder = new SpannableStringBuilder(amountValue);
        amountBuilder.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1976D2")),
                0,
                amountValue.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        holder.expenseAmount.setText(amountBuilder);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseID, expenseDate, expenseClaimant, expenseType, expenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseID = itemView.findViewById(R.id.expenseID);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expenseClaimant = itemView.findViewById(R.id.expenseClaimant);
            expenseType = itemView.findViewById(R.id.expenseType);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
        }
    }
}