package com.example.anative.models;

public class Project {
    private long id;
    private String projectCode;
    private String projectName;
    private String projectDescription;
    private String startDate;
    private String endDate;
    private String projectOwner;
    private String projectStatus;
    private double projectBudget;
    private String specialRequirement;
    private String departmentInformation;

    public Project() {}

    public Project(long id, String projectCode, String projectName, String projectDescription,
                   String startDate, String endDate, String projectOwner, String projectStatus,
                   double projectBudget, String specialRequirement, String departmentInformation) {
        this.id = id;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
        this.projectStatus = projectStatus;
        this.projectBudget = projectBudget;
        this.specialRequirement = specialRequirement;
        this.departmentInformation = departmentInformation;
    }

    public Project(String projectCode, String projectName, String projectDescription,
                   String startDate, String endDate, String projectOwner, String projectStatus,
                   double projectBudget, String specialRequirement, String departmentInformation) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
        this.projectStatus = projectStatus;
        this.projectBudget = projectBudget;
        this.specialRequirement = specialRequirement;
        this.departmentInformation = departmentInformation;
    }


    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getProjectCode() { return projectCode; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getProjectOwner() { return projectOwner; }
    public void setProjectOwner(String projectOwner) { this.projectOwner = projectOwner; }

    public String getProjectStatus() { return projectStatus; }
    public void setProjectStatus(String projectStatus) { this.projectStatus = projectStatus; }

    public double getProjectBudget() { return projectBudget; }
    public void setProjectBudget(double projectBudget) { this.projectBudget = projectBudget; }

    public String getSpecialRequirement() { return specialRequirement; }
    public void setSpecialRequirement(String specialRequirement) { this.specialRequirement = specialRequirement; }

    public String getDepartmentInformation() { return departmentInformation; }
    public void setDepartmentInformation(String departmentInformation) { this.departmentInformation = departmentInformation; }
}