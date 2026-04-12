package com.example.anative.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.anative.models.Project;
import com.example.anative.models.Expense;

import java.lang.annotation.Target;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "project_expense.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "DatabaseHelper";
//    project_db
    private static final String TABLE_PROJECTS = "projects";
    private static final String COLUMN_PROJECT_ID = "id";
    private static final String COLUMN_PROJECT_CODE = "project_code";
    private static final String COLUMN_PROJECT_NAME = "project_name";
    private static final String COLUMN_PROJECT_DES = "project_des";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    private static final String COLUMN_PROJECT_OWNER = "project_owner";
    private static final String COLUMN_PROJECT_STATUS = "project_status";
    private static final String COLUMN_PROJECT_BUDGET = "project_budget";
    private static final String COLUMN_SPECIAL_REQUIREMENT = "special_requirement";
    private static final String COLUMN_DEPT_INFO = "department_information";

//    expense_db
    private static final String TABLE_EXPENSES = "expenses";
    private static final String COLUMN_EXPENSE_ID = "id";
    private static final String COLUMN_FK_PROJECT_ID = "project_id";
    private static final String COLUMN_EXPENSE_CODE = "expense_code";
    private static final String COLUMN_EXPENSE_DATE = "expense_date";
    private static final String COLUMN_EXPENSE_CURRENCY = "expense_currency";
    private static final String COLUMN_EXPENSE_AMOUNT = "expense_amount";
    private static final String COLUMN_EXPENSE_TYPE = "expense_type";
    private static final String COLUMN_PAYMENT_METHOD = "payment_method";
    private static final String COLUMN_CLAIMANT = "claimant";
    private static final String COLUMN_PAYMENT_STATUS = "payment_status";
    private static final String COLUMN_EXPENSE_DES = "expense_des";
    private static final String COLUMN_EXPENSE_LOCATION = "expense_location";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECTS_TABLE = "CREATE TABLE " + TABLE_PROJECTS + "("
                + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROJECT_CODE + " TEXT NOT NULL UNIQUE,"
                + COLUMN_PROJECT_NAME + " TEXT NOT NULL,"
                + COLUMN_PROJECT_DES + " TEXT NOT NULL,"
                + COLUMN_START_DATE + " TEXT NOT NULL,"
                + COLUMN_END_DATE + " TEXT NOT NULL,"
                + COLUMN_PROJECT_OWNER + " TEXT NOT NULL,"
                + COLUMN_PROJECT_STATUS + " TEXT NOT NULL,"
                + COLUMN_PROJECT_BUDGET + " REAL NOT NULL,"
                + COLUMN_SPECIAL_REQUIREMENT + " TEXT,"
                + COLUMN_DEPT_INFO + " TEXT" + ")";

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FK_PROJECT_ID + " INTEGER NOT NULL,"
                + COLUMN_EXPENSE_CODE + " TEXT NOT NULL UNIQUE,"
                + COLUMN_EXPENSE_DATE + " TEXT NOT NULL,"
                + COLUMN_EXPENSE_CURRENCY + " TEXT NOT NULL,"
                + COLUMN_EXPENSE_AMOUNT + " REAL NOT NULL,"
                + COLUMN_EXPENSE_TYPE + " TEXT NOT NULL,"
                + COLUMN_PAYMENT_METHOD + " TEXT NOT NULL,"
                + COLUMN_CLAIMANT + " TEXT NOT NULL,"
                + COLUMN_PAYMENT_STATUS + " TEXT NOT NULL,"
                + COLUMN_EXPENSE_DES + " TEXT,"
                + COLUMN_EXPENSE_LOCATION + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_FK_PROJECT_ID + ") REFERENCES " + TABLE_PROJECTS + "(" + COLUMN_PROJECT_ID + ") ON DELETE CASCADE" + ")";

        db.execSQL(CREATE_PROJECTS_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    public long addProject(Project project){
        if(project == null){
            Log.e(TAG, "Cannot add null project");
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROJECT_CODE, project.getProjectCode());
            values.put(COLUMN_PROJECT_NAME, project.getProjectName());
            values.put(COLUMN_PROJECT_DES, project.getProjectDescription());
            values.put(COLUMN_START_DATE, project.getStartDate());
            values.put(COLUMN_END_DATE, project.getEndDate());
            values.put(COLUMN_PROJECT_OWNER, project.getProjectOwner());
            values.put(COLUMN_PROJECT_STATUS, project.getProjectStatus());
            values.put(COLUMN_PROJECT_BUDGET, project.getProjectBudget());
            values.put(COLUMN_SPECIAL_REQUIREMENT, project.getSpecialRequirement());
            values.put(COLUMN_DEPT_INFO, project.getDepartmentInformation());
            id = db.insert(TABLE_PROJECTS, null, values);
            if(id == -1){
                Log.e(TAG, "Failed to insert project: " + project.getProjectName());
            } else{
                Log.d(TAG, "Successful added project: " + project.getProjectName());
            }
        } catch (SQLException e){
            Log.e(TAG, "Error adding project", e);
        } finally {
            db.close();
        }
        return id;
    }
    public ArrayList<Project> getAllProjects() {
        ArrayList<Project> projects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PROJECTS, null, null, null, null, null, COLUMN_PROJECT_ID + " DESC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_ID));
                    String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_CODE));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_NAME));
                    String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_DES));
                    String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
                    String end = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE));
                    String owner = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_OWNER));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_STATUS));
                    double budget = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_BUDGET));
                    String req = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_REQUIREMENT));
                    String dept = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPT_INFO));

                    projects.add(new Project(id, code, name, des, start, end, owner, status, budget, req, dept));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return projects;
    }
    public ArrayList<Project> getProjectsByStatus(String status) {
        ArrayList<Project> projects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PROJECTS, null, COLUMN_PROJECT_STATUS + "=?",
                    new String[]{status}, null, null, COLUMN_PROJECT_ID + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_ID));
                    String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_CODE));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_NAME));
                    String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_DES));
                    String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
                    String end = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE));
                    String owner = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_OWNER));
                    String statusValue = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_STATUS));
                    double budget = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PROJECT_BUDGET));
                    String req = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_REQUIREMENT));
                    String dept = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPT_INFO));

                    projects.add(new Project(id, code, name, des, start, end, owner, statusValue, budget, req, dept));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return projects;
    }
    public boolean updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PROJECT_CODE, project.getProjectCode());
        cv.put(COLUMN_PROJECT_NAME, project.getProjectName());
        cv.put(COLUMN_PROJECT_DES, project.getProjectDescription());
        cv.put(COLUMN_START_DATE, project.getStartDate());
        cv.put(COLUMN_END_DATE, project.getEndDate());
        cv.put(COLUMN_PROJECT_OWNER, project.getProjectOwner());
        cv.put(COLUMN_PROJECT_STATUS, project.getProjectStatus());
        cv.put(COLUMN_PROJECT_BUDGET, project.getProjectBudget());
        cv.put(COLUMN_SPECIAL_REQUIREMENT, project.getSpecialRequirement());
        cv.put(COLUMN_DEPT_INFO, project.getDepartmentInformation());

        int result = db.update(TABLE_PROJECTS, cv, COLUMN_PROJECT_ID + "=?", new String[]{String.valueOf(project.getId())});
        db.close();
        return result > 0;
    }

    public boolean deleteProject(long id) {
        if (id <= 0) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_PROJECTS, COLUMN_PROJECT_ID + "=?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            db.close();
        }
    }

    public long addExpense(Expense expense) {
        if (expense == null) {
            Log.e(TAG, "Cannot add null expense");
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_PROJECT_ID, expense.getProjectId());
            values.put(COLUMN_EXPENSE_CODE, expense.getExpenseCode());
            values.put(COLUMN_EXPENSE_DATE, expense.getDate());
            values.put(COLUMN_EXPENSE_CURRENCY, expense.getCurrency());
            values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
            values.put(COLUMN_EXPENSE_TYPE, expense.getType());
            values.put(COLUMN_PAYMENT_METHOD, expense.getPaymentMethod());
            values.put(COLUMN_CLAIMANT, expense.getClaimant());
            values.put(COLUMN_PAYMENT_STATUS, expense.getPaymentStatus());
            values.put(COLUMN_EXPENSE_DES, expense.getDescription());
            values.put(COLUMN_EXPENSE_LOCATION, expense.getLocation());

            id = db.insert(TABLE_EXPENSES, null, values);
            if (id == -1) {
                Log.e(TAG, "Failed to insert expense");
            } else {
                Log.d(TAG, "Successful added expense with ID: " + id);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error adding expense", e);
        } finally {
            db.close();
        }
        return id;
    }

    public ArrayList<Expense> getExpensesByProject(long projectId) {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_EXPENSES, null, COLUMN_FK_PROJECT_ID + "=?",
                    new String[]{String.valueOf(projectId)}, null, null, COLUMN_EXPENSE_ID + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID));
                    long pId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FK_PROJECT_ID));
                    String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CODE));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DATE));
                    String currency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CURRENCY));
                    double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_AMOUNT));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_TYPE));
                    String method = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD));
                    String claimant = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLAIMANT));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_STATUS));
                    String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DES));
                    String loc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_LOCATION));

                    expenses.add(new Expense(id, pId, code, date, amount, currency,  type, method, claimant, status, des, loc));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return expenses;
    }
    public Expense getExpenseById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Expense expense = null;
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_EXPENSES, null, COLUMN_EXPENSE_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long pId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FK_PROJECT_ID));
                String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CODE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DATE));
                String currency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CURRENCY));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_TYPE));
                String method = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD));
                String claimant = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLAIMANT));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_STATUS));
                String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DES));
                String loc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_LOCATION));

                expense = new Expense(id, pId, code, date, amount, currency, type, method, claimant, status, des, loc);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return expense;
    }

    public int updateExpense(Expense expense) {
        if (expense == null) {
            Log.e(TAG, "Cannot update null expense");
            return 0;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_PROJECT_ID, expense.getProjectId());
            values.put(COLUMN_EXPENSE_CODE, expense.getExpenseCode());
            values.put(COLUMN_EXPENSE_DATE, expense.getDate());
            values.put(COLUMN_EXPENSE_CURRENCY, expense.getCurrency());
            values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
            values.put(COLUMN_EXPENSE_TYPE, expense.getType());
            values.put(COLUMN_PAYMENT_METHOD, expense.getPaymentMethod());
            values.put(COLUMN_CLAIMANT, expense.getClaimant());
            values.put(COLUMN_PAYMENT_STATUS, expense.getPaymentStatus());
            values.put(COLUMN_EXPENSE_DES, expense.getDescription());
            values.put(COLUMN_EXPENSE_LOCATION, expense.getLocation());

            rows = db.update(TABLE_EXPENSES, values, COLUMN_EXPENSE_ID + "=?",
                    new String[]{String.valueOf(expense.getId())});

            if (rows > 0) {
                Log.d(TAG, "Successful updated expense ID: " + expense.getId());
            } else {
                Log.w(TAG, "No rows updated for expense ID: " + expense.getId());
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error updating expense", e);
        } finally {
            db.close();
        }
        return rows;
    }

    public void deleteExpense(long id) {
        if (id <= 0) {
            Log.e(TAG, "Invalid expense ID for deletion: " + id);
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_EXPENSES, COLUMN_EXPENSE_ID + "=?",
                    new String[]{String.valueOf(id)});
            if (rows > 0) {
                Log.d(TAG, "Successfully deleted expense with ID: " + id);
            } else {
                Log.w(TAG, "No expense found with ID: " + id);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error deleting expense with ID: " + id, e);
        } finally {
            db.close();
        }
    }
}
