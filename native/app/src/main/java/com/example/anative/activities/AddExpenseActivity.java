package com.example.anative.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.anative.R;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.models.Expense;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private TextInputEditText editExpenseId, editExpenseDate, editExpenseAmount, editCurrency, editExpenseClaimant, editExpenseLocation, editExpenseDescription;
    private AutoCompleteTextView dropExpenseType, dropPaymentMethod, dropPaymentStatus;
    private Button btnSaveExpense;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        projectId = getIntent().getLongExtra("PROJECT_ID", -1);

        if (projectId == -1) {
            Toast.makeText(this, "Project ID missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupSpinners();
        setupDatePicker();

        btnBack.setOnClickListener(v -> finish());
        btnSaveExpense.setOnClickListener(v -> saveExpense());
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

        btnSaveExpense = findViewById(R.id.btnSaveExpense);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupSpinners() {
        String[] types = {"Travel", "Equipment", "Materials", "Service", "Software/Licenses", "Labour costs", "Utilities", "Miscellaneous"};
        String[] methods = {"Cash", "Credit Card", "Bank Transfer", "Cheque"};
        String[] statuses = {"Pending", "Paid", "Reimbursed)"};

        dropExpenseType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, types));
        dropPaymentMethod.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, methods));
        dropPaymentStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statuses));
    }

    private void setupDatePicker() {
        editExpenseDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                editExpenseDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void saveExpense() {
        String code = editExpenseId.getText().toString().trim();
        String date = editExpenseDate.getText().toString().trim();
        String amountStr = editExpenseAmount.getText().toString().trim();
        String claimant = editExpenseClaimant.getText().toString().trim();


        Expense expense = new Expense(
                0,
                projectId,
                code,
                date,
                Double.parseDouble(amountStr),
                editCurrency.getText().toString(),
                dropExpenseType.getText().toString(),
                dropPaymentMethod.getText().toString(),
                claimant,
                dropPaymentStatus.getText().toString(),
                editExpenseDescription.getText().toString(),
                editExpenseLocation.getText().toString()
        );

        if (dbHelper.addExpense(expense) != -1) {
            Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add expense (Check unique ID)", Toast.LENGTH_SHORT).show();
        }
    }
}