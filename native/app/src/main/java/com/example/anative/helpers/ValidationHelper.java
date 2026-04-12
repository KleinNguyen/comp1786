package com.example.anative.helpers;

import android.content.Context;
import android.widget.Toast;

public class ValidationHelper {

    public static boolean validateProjectInput(Context context,
                                               String code,
                                               String name,
                                               String description,
                                               String startDate,
                                               String endDate,
                                               String owner,
                                               String status,
                                               String budgetStr) {

        if (code == null || code.trim().isEmpty()) {
            Toast.makeText(context, "Project ID/Code is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            Toast.makeText(context, "Project Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            Toast.makeText(context, "Project Description is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (startDate == null || startDate.trim().isEmpty()) {
            Toast.makeText(context, "Start Date is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (endDate == null || endDate.trim().isEmpty()) {
            Toast.makeText(context, "End Date is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (owner == null || owner.trim().isEmpty()) {
            Toast.makeText(context, "Project Owner is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (status == null || status.trim().isEmpty()) {
            Toast.makeText(context, "Project Status is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (budgetStr == null || budgetStr.trim().isEmpty()) {
            Toast.makeText(context, "Project Budget is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double budget = Double.parseDouble(budgetStr.trim());
            if (budget < 0) {
                Toast.makeText(context, "Budget cannot be negative", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Please enter a valid budget number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean validateExpenseInput(Context context,
                                               String date,
                                               String amountStr,
                                               String currency,
                                               String type,
                                               String method,
                                               String claimant,
                                               String status) {

        if (date == null || date.trim().isEmpty()) {
            Toast.makeText(context, "Date of Expense is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (amountStr == null || amountStr.trim().isEmpty()) {
            Toast.makeText(context, "Amount is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double amount = Double.parseDouble(amountStr.trim());
            if (amount <= 0) {
                Toast.makeText(context, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (currency == null || currency.trim().isEmpty()) {
            Toast.makeText(context, "Currency is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (type == null || type.trim().isEmpty()) {
            Toast.makeText(context, "Type of Expense is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (method == null || method.trim().isEmpty()) {
            Toast.makeText(context, "Payment Method is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (claimant == null || claimant.trim().isEmpty()) {
            Toast.makeText(context, "Claimant is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (status == null || status.trim().isEmpty()) {
            Toast.makeText(context, "Payment Status is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}