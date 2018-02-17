package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class ViewSupervisorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_supervisor);
    }

    public void createGroup(MenuItem item) {
        Intent intent = new Intent(ViewSupervisorActivity.this,CreateGroupActivity.class);
        startActivity(intent);
    }

    public void notificationsShow(MenuItem item) {
        Intent intent = new Intent(ViewSupervisorActivity.this,NotificationsActivity.class);
        startActivity(intent);
    }

    public void openChat(MenuItem item) {
        Toast.makeText(ViewSupervisorActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }

    public void submitOwnProposal(MenuItem item) {
        Intent intent = new Intent(ViewSupervisorActivity.this,CreateProjectActivity.class);
        startActivity(intent);
    }
}
