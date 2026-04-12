package com.example.anative.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.anative.helpers.DatabaseHelper;

import com.example.anative.R;
import com.example.anative.adapters.ProjectAdapter;
import com.example.anative.models.Project;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewProjectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Project> projectsList;
    private ProjectAdapter projectAdapter;
    private FloatingActionButton fabAdd;
    private TabLayout projectTab;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_project_activity);

        dbHelper = new DatabaseHelper(this);
        setupUI();
        setupClickListeners();
        setupWindowInsets();
        setupTabLayout();
    }
    private void setupUI(){
        recyclerView = findViewById(R.id.projectRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(this,projectsList);
        projectAdapter.setOnItemClickListener(project -> {
            Intent intent = new Intent(ViewProjectActivity.this, ProjectDetailActivity.class);
            intent.putExtra("PROJECT_ID", project.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(projectAdapter);
        fabAdd = findViewById(R.id.fabAddProject);
        projectTab = findViewById(R.id.projectTab);
    }
    private void navigateToAddProject(){
        Intent intent = new Intent(this, AddProjectActivity.class);
        startActivity(intent);
    }
    private void setupClickListeners(){
        fabAdd.setOnClickListener(v->navigateToAddProject());
    }
    private void setupTabLayout(){
        projectTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadProject(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void loadProject(String status) {
        projectsList.clear();
        ArrayList<Project> projects;

        if (status == null || status.equalsIgnoreCase("All Project")) {
            projects = dbHelper.getAllProjects();
        } else {
            projects = dbHelper.getProjectsByStatus(status);
        }

        if (projects != null) {
            projectsList.addAll(projects);
        }
        projectAdapter.notifyDataSetChanged();

    }
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.project_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        String currentStatus = "All Project";
        if (projectTab != null && projectTab.getTabAt(projectTab.getSelectedTabPosition()) != null) {
            currentStatus = projectTab.getTabAt(projectTab.getSelectedTabPosition()).getText().toString();
        }
        loadProject(currentStatus);
    }
}