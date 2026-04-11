package com.example.anative.models;

public class Project {
    private long id;
    private String project_code;
    private String project_name;
    private String project_des;
    private String start_date;
    private String end_date;
    private String project_owner;
    private String project_status;
    private double project_budget;
    private String special_requirement;
    private String department_information;
    private long expenseId;

    public Project(){

    }
    public Project(long id,long expenseId, String project_code, String project_name,String project_des,String start_date, String end_date, String project_owner, String project_status, double project_budget, String special_requirement, String department_information){
        this.id = id;
        this.expenseId = expenseId;
        this.project_code = project_code;
        this.project_name = project_name;
        this.project_des = project_des;
        this.start_date = start_date;
        this.end_date = end_date;
        this.project_owner = project_owner;
        this.project_status = project_status;
        this.project_budget = project_budget;
        this.special_requirement = special_requirement;
        this.department_information = department_information;
    }
    public Project(long expenseId, String project_code, String project_name,String project_des,String start_date, String end_date, String project_owner, String project_status, double project_budget, String special_requirement, String department_information){
        this.expenseId = expenseId;
        this.project_code = project_code;
        this.project_name = project_name;
        this.project_des = project_des;
        this.start_date = start_date;
        this.end_date = end_date;
        this.project_owner = project_owner;
        this.project_status = project_status;
        this.project_budget = project_budget;
        this.special_requirement = special_requirement;
        this.department_information = department_information;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_des() {
        return project_des;
    }

    public void setProject_des(String project_des) {
        this.project_des = project_des;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getProject_owner() {
        return project_owner;
    }

    public void setProject_owner(String project_owner) {
        this.project_owner = project_owner;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public double getProject_budget() {
        return project_budget;
    }

    public void setProject_budget(double project_budget) {
        this.project_budget = project_budget;
    }

    public String getSpecial_requirement() {
        return special_requirement;
    }

    public void setSpecial_requirement(String special_requirement) {
        this.special_requirement = special_requirement;
    }

    public String getDepartment_information() {
        return department_information;
    }

    public void setDepartment_information(String department_information) {
        this.department_information = department_information;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }
}
