package com.example.anative.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anative.R;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.models.Expense;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView textExpenseID, textClaimant, textExpenseLocation, textExpenseDate,
            textExpenseType, textPaymentMethod, textExpenseStatus,
            textExpenseAmount, textExpenseDes;
    private ImageButton btnBack;
    private Button btnDeleteExpense, btnEditExpense;
    private DatabaseHelper dbHelper;
    private long expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        dbHelper = new DatabaseHelper(this);
        expenseId = getIntent().getLongExtra("EXPENSE_ID", -1);

        initViews();
        loadExpenseDetails();

        btnBack.setOnClickListener(v -> finish());

        btnDeleteExpense.setOnClickListener(v -> confirmDelete());

        btnEditExpense.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseDetailActivity.this, EditExpenseActivity.class);
            intent.putExtra("EXPENSE_ID", expenseId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenseDetails();
    }

    private void initViews() {
        textExpenseID = findViewById(R.id.textExpenseID);
        textClaimant = findViewById(R.id.textClaimant);
        textExpenseLocation = findViewById(R.id.textExpenseLocation);
        textExpenseDate = findViewById(R.id.textExpenseDate);
        textExpenseType = findViewById(R.id.textExpenseType);
        textPaymentMethod = findViewById(R.id.textPaymentMethod);
        textExpenseStatus = findViewById(R.id.textExpenseStatus);
        textExpenseAmount = findViewById(R.id.textExpenseAmount);
        textExpenseDes = findViewById(R.id.textExpenseDes);

        btnBack = findViewById(R.id.btnBack);
        btnDeleteExpense = findViewById(R.id.btnDeleteExpense);
        btnEditExpense = findViewById(R.id.btnEditExpense);
    }

    private void loadExpenseDetails() {
        if (expenseId == -1) {
            Toast.makeText(this, "Error: Invalid Expense ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Expense expense = dbHelper.getExpenseById(expenseId);

        if (expense != null) {
            textExpenseID.setText(checkEmpty(expense.getExpenseCode()));
            textClaimant.setText(checkEmpty(expense.getClaimant()));
            textExpenseLocation.setText(checkEmpty(expense.getLocation()));
            textExpenseDate.setText(checkEmpty(expense.getDate()));
            textExpenseType.setText(checkEmpty(expense.getType()));
            textPaymentMethod.setText(checkEmpty(expense.getPaymentMethod()));
            textExpenseDes.setText(checkEmpty(expense.getDescription()));

            String amountValue = String.format("%.2f %s", expense.getAmount(), expense.getCurrency());
            textExpenseAmount.setText(amountValue);

            String status = expense.getPaymentStatus();
            textExpenseStatus.setText(checkEmpty(status));

            if (status != null && status.equalsIgnoreCase("Paid")) {
                textExpenseStatus.setTextColor(Color.parseColor("#2E7D32"));
            } else {
                textExpenseStatus.setTextColor(Color.parseColor("#1976D2"));
            }
        } else {
            Toast.makeText(this, "Expense not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteExpense(expenseId);
                    Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String checkEmpty(String value) {
        return (value == null || value.trim().isEmpty()) ? "None" : value;
    }
}