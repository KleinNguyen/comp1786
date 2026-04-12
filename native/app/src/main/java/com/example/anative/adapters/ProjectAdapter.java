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
import com.example.anative.models.Project;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private ArrayList<Project> projectList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProjectAdapter(Context context, ArrayList<Project> projectList){
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);
        if(project == null) return;
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(project);
            }
        });
        holder.projectName.setText(project.getProjectName() != null ? project.getProjectName() : "");
        holder.projectCode.setText("Code: " + (project.getProjectCode() != null ? project.getProjectCode() : ""));
        holder.projectOwner.setText("By: " + (project.getProjectOwner() != null ? project.getProjectOwner() : ""));
        String labelStatus = "Status: ";
        String status = project.getProjectStatus() != null ? project.getProjectStatus() : "";
        String fullTextStatus = labelStatus + status;
        SpannableStringBuilder builderStatus = new SpannableStringBuilder(fullTextStatus);

        if (!status.isEmpty()) {
            int statusColor;
            switch (status.toLowerCase()) {
                case "active": statusColor = Color.parseColor("#1976D2"); break;
                case "on hold": statusColor = Color.parseColor("#FBC02D"); break;
                case "completed": statusColor = Color.parseColor("#2E7D32"); break;
                default: statusColor = Color.BLACK; break;
            }
            builderStatus.setSpan(new ForegroundColorSpan(statusColor), labelStatus.length(), fullTextStatus.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.projectStatus.setText(builderStatus);

        String labelBudget = "Budget: ";
        String budget = String.format("%.2f", project.getProjectBudget());
        String fullTextBudget = labelBudget + budget;
        SpannableStringBuilder builderBudget = new SpannableStringBuilder(fullTextBudget);
        builderBudget.setSpan(new ForegroundColorSpan(Color.parseColor("#2E7D32")), labelBudget.length(), fullTextBudget.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.projectBudget.setText(builderBudget);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        public TextView projectName, projectCode, projectOwner, projectStatus, projectBudget;
        public View cardView;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            projectCode = itemView.findViewById(R.id.projectCode);
            projectOwner = itemView.findViewById(R.id.projectOwner);
            projectStatus = itemView.findViewById(R.id.projectStatus);
            projectBudget = itemView.findViewById(R.id.projectBudget);
            cardView = itemView.findViewById(R.id.cardProject);
        }
    }
}