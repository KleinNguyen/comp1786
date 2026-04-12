package com.example.anative.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anative.R;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.helpers.ValidationHelper;
import com.example.anative.models.Expense;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class EditExpenseActivity extends AppCompatActivity {

    private TextInputEditText editExpenseId, editExpenseDate, editExpenseAmount, editCurrency, editExpenseClaimant, editExpenseLocation, editExpenseDescription;
    private AutoCompleteTextView dropExpenseType, dropPaymentMethod, dropPaymentStatus;
    private ImageButton btnBack;
    private Button btnSaveExpense;
    private DatabaseHelper dbHelper;
    private long expenseId;
    private Expense currentExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        dbHelper = new DatabaseHelper(this);
        expenseId = getIntent().getLongExtra("EXPENSE_ID", -1);

        initViews();
        setupDropdowns();
        loadExpenseData();

        btnBack.setOnClickListener(v -> finish());

        editExpenseDate.setOnClickListener(v -> showDatePicker());

        btnSaveExpense.setOnClickListener(v -> updateExpense());
    }

    private void initViews() {
        editExpenseId = findViewById(R.id.editExpenseId);
        editExpenseDate = findViewById(R.id.editExpenseDate);
        editExpenseAmount = findViewById(R.id.editExpenseAmount);
        editCurrency = findViewById(R.id.editCurrency);
        editExpenseClaimant = findViewById(R.id.editExpenseClaimant);
        editExpenseLocation = findViewById(R.id.editExpenseLocation);
        editExpenseDescription = findViewById(R.id.editExpenseDescription);

        dropExpenseType = findViewById(R.id.dropExpenseType);
        dropPaymentMethod = findViewById(R.id.dropPaymentMethod);
        dropPaymentStatus = findViewById(R.id.dropPaymentStatus);

        btnBack = findViewById(R.id.btnBack);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);
    }

    private void setupDropdowns() {
        String[] types = {"Travel", "Equipment", "Materials", "Service", "Software/Licenses", "Labour costs", "Utilities", "Miscellaneous"};
        dropExpenseType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types));

        String[] methods = {"Cash", "Credit Card", "Bank Transfer", "Cheque"};
        dropPaymentMethod.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, methods));

        String[] statuses = {"Pending", "Paid", "Reimbursed)"};

        dropPaymentStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statuses));
    }

    private void loadExpenseData() {
        if (expenseId == -1) {
            Toast.makeText(this, "Invalid Expense ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentExpense = dbHelper.getExpenseById(expenseId);

        if (currentExpense != null) {
            editExpenseId.setText(currentExpense.getExpenseCode());
            editExpenseDate.setText(currentExpense.getDate());
            editExpenseAmount.setText(String.valueOf(currentExpense.getAmount()));
            editCurrency.setText(currentExpense.getCurrency());
            editExpenseClaimant.setText(currentExpense.getClaimant());
            editExpenseLocation.setText(currentExpense.getLocation());
            editExpenseDescription.setText(currentExpense.getDescription());

            dropExpenseType.setText(currentExpense.getType(), false);
            dropPaymentMethod.setText(currentExpense.getPaymentMethod(), false);
            dropPaymentStatus.setText(currentExpense.getPaymentStatus(), false);
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editExpenseDate.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void updateExpense() {
        String code = editExpenseId.getText().toString().trim();
        String date = editExpenseDate.getText().toString().trim();
        String amountStr = editExpenseAmount.getText().toString().trim();
        String currency = editCurrency.getText().toString().trim();
        String type = dropExpenseType.getText().toString().trim();
        String method = dropPaymentMethod.getText().toString().trim();
        String claimant = editExpenseClaimant.getText().toString().trim();
        String status = dropPaymentStatus.getText().toString().trim();
        String location = editExpenseLocation.getText().toString().trim();
        String description = editExpenseDescription.getText().toString().trim();

        if (ValidationHelper.validateExpenseInput(this, date, amountStr, currency, type, method, claimant, status)) {
            currentExpense.setExpenseCode(code);
            currentExpense.setDate(date);
            currentExpense.setAmount(Double.parseDouble(amountStr));
            currentExpense.setCurrency(currency);
            currentExpense.setType(type);
            currentExpense.setPaymentMethod(method);
            currentExpense.setClaimant(claimant);
            currentExpense.setPaymentStatus(status);
            currentExpense.setLocation(location);
            currentExpense.setDescription(description);

            int rows = dbHelper.updateExpense(currentExpense);
            if (rows > 0) {
                Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}