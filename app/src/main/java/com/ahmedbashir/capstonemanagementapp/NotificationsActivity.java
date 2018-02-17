package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    public void createGroup(MenuItem item) {
        Intent intent = new Intent(NotificationsActivity.this,CreateGroupActivity.class);
        startActivity(intent);
    }

    public void notificationsShow(MenuItem item) {
        Toast.makeText(NotificationsActivity.this,"You are already in Notifications Screen ", Toast.LENGTH_LONG).show();

    }

    public void openChat(MenuItem item) {
        Toast.makeText(NotificationsActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }

    public void submitOwnProposal(MenuItem item) {
        Intent intent = new Intent(NotificationsActivity.this,CreateProjectActivity.class);
        startActivity(intent);
    }
}
