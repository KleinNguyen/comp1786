package com.example.anative.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.anative.R;
import com.example.anative.helpers.DatabaseHelper;
import com.example.anative.models.Project;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditProjectActivity extends AppCompatActivity {

    private TextInputEditText editProjectCode, editProjectName, editProjectDes, editStartDate, editEndDate, editProjectOwner, editProjectBudget, editSpecialRequirement, editDepartmentInformation;
    private AutoCompleteTextView dropStatus;
    private Button btnSaveProject;
    private ImageButton btnBack;
    private TextView tvHeaderTitle;
    private DatabaseHelper dbHelper;
    private long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        projectId = getIntent().getLongExtra("PROJECT_ID", -1);

        initViews();
        setupStatusSpinner();
        loadCurrentData();

        btnBack.setOnClickListener(v -> finish());
        btnSaveProject.setOnClickListener(v -> updateProject());
    }

    private void initViews() {
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
        tvHeaderTitle = findViewById(R.id.textView);

        tvHeaderTitle.setText("Edit Project");
        btnSaveProject.setText("Update Project");
    }

    private void setupStatusSpinner() {
        String[] statuses = {"Active", "Completed", "On Hold"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statuses);
        dropStatus.setAdapter(adapter);
    }

    private void loadCurrentData() {
        ArrayList<Project> allProjects = dbHelper.getAllProjects();
        Project project = null;
        for (Project p : allProjects) {
            if (p.getId() == projectId) {
                project = p;
                break;
            }
        }

        if (project != null) {
            editProjectCode.setText(project.getProjectCode());
            editProjectName.setText(project.getProjectName());
            editProjectDes.setText(project.getProjectDescription());
            editStartDate.setText(project.getStartDate());
            editEndDate.setText(project.getEndDate());
            editProjectOwner.setText(project.getProjectOwner());
            editProjectBudget.setText(String.valueOf(project.getProjectBudget()));
            editSpecialRequirement.setText(project.getSpecialRequirement());
            editDepartmentInformation.setText(project.getDepartmentInformation());
            dropStatus.setText(project.getProjectStatus(), false);
        } else {
            Toast.makeText(this, "Project not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateProject() {
        String name = editProjectName.getText().toString().trim();
        String budgetStr = editProjectBudget.getText().toString().trim();

        if (name.isEmpty() || budgetStr.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Project updatedProject = new Project(
                projectId,
                editProjectCode.getText().toString(),
                name,
                editProjectDes.getText().toString(),
                editStartDate.getText().toString(),
                editEndDate.getText().toString(),
                editProjectOwner.getText().toString(),
                dropStatus.getText().toString(),
                Double.parseDouble(budgetStr),
                editSpecialRequirement.getText().toString(),
                editDepartmentInformation.getText().toString()
        );

        boolean success = dbHelper.updateProject(updatedProject);
        if (success) {
            Toast.makeText(this, "Project updated!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }
}