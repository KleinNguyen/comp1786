package com.example.anative.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.example.anative.helpers.FirebaseHelper;
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
    private SearchView searchProject;
    private ImageButton btnUploadCloud;
    private ImageButton btnDownloadCloud;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_project_activity);

        dbHelper = new DatabaseHelper(this);
        firebaseHelper = new FirebaseHelper(this);
        setupUI();
        setupClickListeners();
        setupWindowInsets();
        setupTabLayout();
        setupSearchView();
    }

    private void setupUI() {
        btnUploadCloud = findViewById(R.id.btnUploadCloud);
        btnDownloadCloud = findViewById(R.id.btnDownloadCloud);
        recyclerView = findViewById(R.id.projectRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(this, projectsList);
        projectAdapter.setOnItemClickListener(project -> {
            Intent intent = new Intent(ViewProjectActivity.this, ProjectDetailActivity.class);
            intent.putExtra("PROJECT_ID", project.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(projectAdapter);
        fabAdd = findViewById(R.id.fabAddProject);
        projectTab = findViewById(R.id.projectTab);
        searchProject = findViewById(R.id.searchProject);
    }

    private void navigateToAddProject() {
        Intent intent = new Intent(this, AddProjectActivity.class);
        startActivity(intent);
    }

    private void setupClickListeners() {
        fabAdd.setOnClickListener(v -> navigateToAddProject());

        btnUploadCloud.setOnClickListener(v -> {
            showUploadConfirmation();
        });

        btnDownloadCloud.setOnClickListener(v -> {
            showDownloadConfirmation();
        });
    }

    private void showUploadConfirmation() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Upload to Cloud")
                .setMessage("Push all local data to Firebase?")
                .setPositiveButton("Upload", (dialog, which) -> {
                    performUpload();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performUpload() {
        ArrayList<Project> localData = dbHelper.getAllProjectsWithExpenses();
        firebaseHelper.uploadAllProjects(localData, new FirebaseHelper.OnFirebaseSyncListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ViewProjectActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoNetwork() {
                Toast.makeText(ViewProjectActivity.this, "No Network!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(ViewProjectActivity.this, "Upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDownloadConfirmation() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Download from Cloud")
                .setMessage("Clear local data and replace with cloud data?")
                .setPositiveButton("Download", (dialog, which) -> {
                    performDownload();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performDownload() {
        firebaseHelper.downloadAllProjects(new FirebaseHelper.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(ArrayList<Project> remoteProjects) {
                dbHelper.syncRemoteDataToLocal(remoteProjects);

                String currentStatus = "All Project";
                if (projectTab != null && projectTab.getTabAt(projectTab.getSelectedTabPosition()) != null) {
                    currentStatus = projectTab.getTabAt(projectTab.getSelectedTabPosition()).getText().toString();
                }
                loadProject(currentStatus);
                Toast.makeText(ViewProjectActivity.this, "Download Successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoNetwork() {
                Toast.makeText(ViewProjectActivity.this, "No Network!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(ViewProjectActivity.this, "Download failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTabLayout() {
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

    private void setupSearchView() {
        searchProject.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        projectsList.clear();
        ArrayList<Project> filteredProjects;

        if (query.isEmpty()) {
            String currentStatus = "All Project";
            if (projectTab != null && projectTab.getTabAt(projectTab.getSelectedTabPosition()) != null) {
                currentStatus = projectTab.getTabAt(projectTab.getSelectedTabPosition()).getText().toString();
            }
            loadProject(currentStatus);
        } else {
            filteredProjects = dbHelper.searchProjects(query);
            if (filteredProjects != null) {
                projectsList.addAll(filteredProjects);
            }
            projectAdapter.notifyDataSetChanged();
        }
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.project_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentStatus = "All Project";
        if (projectTab != null && projectTab.getTabAt(projectTab.getSelectedTabPosition()) != null) {
            currentStatus = projectTab.getTabAt(projectTab.getSelectedTabPosition()).getText().toString();
        }
        loadProject(currentStatus);
    }
}