package com.example.anative.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.anative.models.Project;
import com.example.anative.models.Expense;

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
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
                    projects.add(new Project());
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return projects;
    }
}
