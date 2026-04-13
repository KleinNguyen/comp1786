package com.example.anative.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.anative.models.Project;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        // Bước 1: Kiểm tra mạng
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

    public interface OnFirebaseSyncListener {
        void onSuccess();
        void onNoNetwork();
        void onFailure(String errorMessage);
    }
}