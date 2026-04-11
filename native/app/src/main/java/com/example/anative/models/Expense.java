package com.example.anative.models;

public class Expense {
    private long id;
    private long project_id;
    private String expense_code;
    private String expense_date;
    private String expense_currency;
    private double expense_amount;
    private String expense_type;
    private String payment_method;
    private String claimant;
    private String payment_status;
    private String expense_des;
    private String expense_location;

    public Expense() {
    }

    public Expense(long project_id, String expense_code, String expense_date, String expense_currency, double expense_amount, String expense_type, String payment_method, String claimant, String payment_status, String expense_des, String expense_location) {
        this.project_id = project_id;
        this.expense_code = expense_code;
        this.expense_date = expense_date;
        this.expense_currency = expense_currency;
        this.expense_amount = expense_amount;
        this.expense_type = expense_type;
        this.payment_method = payment_method;
        this.claimant = claimant;
        this.payment_status = payment_status;
        this.expense_des = expense_des;
        this.expense_location = expense_location;
    }

    public Expense(long id, long project_id, String expense_code, String expense_date, String expense_currency, double expense_amount, String expense_type, String payment_method, String claimant, String payment_status, String expense_des, String expense_location) {
        this.id = id;
        this.project_id = project_id;
        this.expense_code = expense_code;
        this.expense_date = expense_date;
        this.expense_currency = expense_currency;
        this.expense_amount = expense_amount;
        this.expense_type = expense_type;
        this.payment_method = payment_method;
        this.claimant = claimant;
        this.payment_status = payment_status;
        this.expense_des = expense_des;
        this.expense_location = expense_location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getExpense_code() {
        return expense_code;
    }

    public void setExpense_code(String expense_code) {
        this.expense_code = expense_code;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    public String getExpense_currency() {
        return expense_currency;
    }

    public void setExpense_currency(String expense_currency) {
        this.expense_currency = expense_currency;
    }

    public double getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(double expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getExpense_des() {
        return expense_des;
    }

    public void setExpense_des(String expense_des) {
        this.expense_des = expense_des;
    }

    public String getExpense_location() {
        return expense_location;
    }

    public void setExpense_location(String expense_location) {
        this.expense_location = expense_location;
    }
}