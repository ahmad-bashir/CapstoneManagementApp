package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class CreateProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
    }

    public void createGroup(MenuItem item) {
        Intent intent = new Intent(CreateProjectActivity.this,CreateGroupActivity.class);
        startActivity(intent);
    }

    public void notificationsShow(MenuItem item) {
        Intent intent = new Intent(CreateProjectActivity.this,NotificationsActivity.class);
        startActivity(intent);
    }

    public void openChat(MenuItem item) {
        Toast.makeText(CreateProjectActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }

    public void submitOwnProposal(MenuItem item) {
        Toast.makeText(CreateProjectActivity.this,"You are Already on Project Creation Screen",Toast.LENGTH_LONG).show();
    }
}
