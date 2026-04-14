package com.example.anative.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.example.anative.models.Project;
import com.example.anative.models.Expense;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {
    private Context context;
    private DatabaseReference mDatabase;

    public FirebaseHelper(Context context) {
        this.context = context;
        this.mDatabase = FirebaseDatabase.getInstance("https://admin-app-firebase-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("projects");
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void uploadAllProjects(ArrayList<Project> projectList, OnFirebaseSyncListener listener) {
        if (!isNetworkAvailable()) {
            listener.onNoNetwork();
            return;
        }

        if (projectList == null || projectList.isEmpty()) {
            listener.onFailure("No data to sync.");
            return;
        }

        mDatabase.setValue(projectList)
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public void downloadAllProjects(OnDownloadListener listener) {
        if (!isNetworkAvailable()) {
            listener.onNoNetwork();
            return;
        }

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Project> remoteProjects = new ArrayList<>();
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Project p = data.getValue(Project.class);

                        if (p != null) {
                            try {
                                p.setId(Long.parseLong(data.getKey()));
                            } catch (Exception e) {
                            }
                            DataSnapshot expenseSnapshot = data.child("expenses");
                            ArrayList<Expense> expensesList = new ArrayList<>();

                            if (expenseSnapshot.exists()) {
                                for (DataSnapshot expData : expenseSnapshot.getChildren()) {
                                    Expense exp = expData.getValue(Expense.class);
                                    if (exp != null) {
                                        try {
                                            exp.setId(Long.parseLong(expData.getKey()));
                                        } catch (Exception e) {}

                                        exp.setProjectId(p.getId());
                                        expensesList.add(exp);
                                    }
                                }
                            }
                            p.setExpenses(expensesList);
                            remoteProjects.add(p);
                        }
                    }
                    android.util.Log.d("FirebaseSync", "Tải về thành công: " + remoteProjects.size() + " projects");

                    listener.onDownloadSuccess(remoteProjects);
                } catch (Exception e) {
                    android.util.Log.e("FirebaseSync", "Lỗi Parse: " + e.getMessage());
                    listener.onFailure("Parse error: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure(error.getMessage());
            }
        });
    }
    public interface OnFirebaseSyncListener {
        void onSuccess();
        void onNoNetwork();
        void onFailure(String errorMessage);
    }

    public interface OnDownloadListener {
        void onDownloadSuccess(ArrayList<Project> remoteProjects);
        void onNoNetwork();
        void onFailure(String errorMessage);
    }
}