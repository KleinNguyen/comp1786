package com.example.anative.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.anative.R;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.helpers.ValidationHelper;
import com.example.anative.models.Project;

import java.util.Calendar;

public class AddProjectActivity extends AppCompatActivity {
    private EditText editProjectCode, editProjectName, editProjectDes, editStartDate, editEndDate, editProjectOwner, editProjectBudget, editSpecialRequirement, editDepartmentInformation;
    private AutoCompleteTextView dropStatus;
    private Button btnSaveProject;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);

        dbHelper = new DatabaseHelper(this);

        setupUI();
        setupClickListeners();
        setupDropdown();
        setupWindowInsets();
    }

    private void setupUI() {
        editProjectCode = findViewById(R.id.editProjectCode);
        editProjectName = findViewById(R.id.editProjectName);
        editProjectDes = findViewById(R.id.editProjectDes);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editProjectOwner = findViewById(R.id.editProjectOwner);
        editProjectBudget = findViewById(R.id.editProjectBudget);
        editSpecialRequirement = findViewById(R.id.editSpecialRequirement);
        editDepartmentInformation = findViewById(R.id.editDepartmentInformation);

        dropStatus = findViewById(R.id.dropStatus);
        btnSaveProject = findViewById(R.id.btnSaveProject);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        editStartDate.setOnClickListener(v -> showDatePicker(editStartDate));
        editEndDate.setOnClickListener(v -> showDatePicker(editEndDate));

        btnSaveProject.setOnClickListener(v -> addNewProject());
    }

    private void setupDropdown() {
        String[] statuses = {"Active", "On Hold", "Completed"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statuses);
        dropStatus.setAdapter(adapter);
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void addNewProject() {
        String code = editProjectCode.getText().toString().trim();
        String name = editProjectName.getText().toString().trim();
        String des = editProjectDes.getText().toString().trim();
        String start = editStartDate.getText().toString().trim();
        String end = editEndDate.getText().toString().trim();
        String owner = editProjectOwner.getText().toString().trim();
        String status = dropStatus.getText().toString().trim();
        String budgetStr = editProjectBudget.getText().toString().trim();
        String special = editSpecialRequirement.getText().toString().trim();
        String dept = editDepartmentInformation.getText().toString().trim();

        if (ValidationHelper.validateProjectInput(this, code, name, des, start, end, owner, status, budgetStr)) {
            Project project = new Project(0, code, name, des, start, end, owner, status, Double.parseDouble(budgetStr), special, dept);

            long result = dbHelper.addProject(project);
            if (result != -1) {
                Toast.makeText(this, "Project added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add project. Code might be duplicated.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}