package com.example.anative.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anative.adapters.ExpenseAdapter;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.R;
import com.example.anative.models.Project;
import com.example.anative.models.Expense;

import java.util.ArrayList;

public class ProjectDetailActivity extends AppCompatActivity {

    private TextView textProjectName, textProjectCode, textProjectOwner, textStartDate, textEndDate, textProjectStatus, textProjectBudget, textProjectDes, textSpecialRequirement, textDepartmentInformation;
    private ImageButton btnBack;
    private Button btnDeleteProject, btnEditProject, btnAddExpense;
    private RecyclerView expenseRecycle;
    private DatabaseHelper dbHelper;
    private long projectId;
    private ExpenseAdapter expenseAdapter;
    private ArrayList<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        projectId = getIntent().getLongExtra("PROJECT_ID", -1);

        initViews();
        loadProjectData();

        btnBack.setOnClickListener(v -> finish());

        btnDeleteProject.setOnClickListener(v -> confirmDelete());

        btnEditProject.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectDetailActivity.this, EditProjectActivity.class);
            intent.putExtra("PROJECT_ID", projectId);
            startActivity(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectDetailActivity.this, AddExpenseActivity.class);
            intent.putExtra("PROJECT_ID", projectId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProjectData();
    }

    private void initViews() {
        textProjectName = findViewById(R.id.textProjectName);
        textProjectCode = findViewById(R.id.textProjectCode);
        textProjectOwner = findViewById(R.id.textProjectOwner);
        textStartDate = findViewById(R.id.textStartDate);
        textEndDate = findViewById(R.id.textEndDate);
        textProjectStatus = findViewById(R.id.textProjectStatus);
        textProjectBudget = findViewById(R.id.textProjectBudget);
        textProjectDes = findViewById(R.id.textProjectDes);
        textSpecialRequirement = findViewById(R.id.textSpecialRequirement);
        textDepartmentInformation = findViewById(R.id.textDepartmentInformation);

        btnBack = findViewById(R.id.btnBack);
        btnDeleteProject = findViewById(R.id.btnDeleteProject);
        btnEditProject = findViewById(R.id.btnEditProject);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        expenseRecycle = findViewById(R.id.expenseRecycle);
        expenseRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadProjectData() {
        if (projectId == -1) {
            Toast.makeText(this, "Invalid Project ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ArrayList<Project> allProjects = dbHelper.getAllProjects();
        Project currentProject = null;

        for (Project p : allProjects) {
            if (p.getId() == projectId) {
                currentProject = p;
                break;
            }
        }

        if (currentProject != null) {
            textProjectName.setText(checkEmpty(currentProject.getProjectName()));
            textProjectCode.setText(checkEmpty(currentProject.getProjectCode()));
            textProjectOwner.setText(checkEmpty(currentProject.getProjectOwner()));
            textStartDate.setText(checkEmpty(currentProject.getStartDate()));
            textEndDate.setText(checkEmpty(currentProject.getEndDate()));
            textProjectDes.setText(checkEmpty(currentProject.getProjectDescription()));
            textSpecialRequirement.setText(checkEmpty(currentProject.getSpecialRequirement()));
            textDepartmentInformation.setText(checkEmpty(currentProject.getDepartmentInformation()));
            textProjectBudget.setText("$" + String.format("%.2f", currentProject.getProjectBudget()));

            String status = currentProject.getProjectStatus();
            textProjectStatus.setText(checkEmpty(status));

            if (status != null) {
                switch (status.toLowerCase()) {
                    case "active":
                        textProjectStatus.setTextColor(Color.parseColor("#1976D2"));
                        break;
                    case "on hold":
                        textProjectStatus.setTextColor(Color.parseColor("#FBC02D"));
                        break;
                    case "completed":
                        textProjectStatus.setTextColor(Color.parseColor("#2E7D32"));
                        break;
                    default:
                        textProjectStatus.setTextColor(Color.BLACK);
                        break;
                }
            }

            loadExpenses();
        }
    }

    private void loadExpenses() {
        expenseList = dbHelper.getExpensesByProject(projectId);

        if (expenseAdapter == null) {
            expenseAdapter = new ExpenseAdapter(this, expenseList, expense -> {
                Intent intent = new Intent(ProjectDetailActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("EXPENSE_ID", expense.getId());
                startActivity(intent);
            });
            expenseRecycle.setAdapter(expenseAdapter);
        } else {
            expenseAdapter.updateData(expenseList);
        }
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Project")
                .setMessage("Are you sure you want to delete this project?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean success = dbHelper.deleteProject(projectId);
                    if (success) {
                        Toast.makeText(this, "Project deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to delete project", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String checkEmpty(String value) {
        return (value == null || value.trim().isEmpty()) ? "None" : value;
    }
}