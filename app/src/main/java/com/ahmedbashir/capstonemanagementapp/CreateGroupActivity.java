package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void createGroup(MenuItem item) {
        Toast.makeText(CreateGroupActivity.this,"You are already on same Screen",Toast.LENGTH_LONG).show();
    }

    public void notificationsShow(MenuItem item) {
        Intent intent = new Intent(CreateGroupActivity.this,NotificationsActivity.class);
        startActivity(intent);
    }

    public void openChat(MenuItem item) {
        Toast.makeText(CreateGroupActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }

    public void submitOwnProposal(MenuItem item) {
        Intent intent = new Intent(CreateGroupActivity.this,CreateProjectActivity.class);
        startActivity(intent);
    }
}
