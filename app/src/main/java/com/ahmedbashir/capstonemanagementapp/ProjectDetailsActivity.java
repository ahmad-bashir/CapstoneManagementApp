package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectDetailsActivity extends AppCompatActivity {
    public static final String TAG = ProjectDetailsActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Referring Views of activity
    @BindView(R.id.project_title_label)
    TextView projectLabel;

    @BindView(R.id.project_title_textView)
    TextView projectTitle;

    @BindView(R.id.project_description_textView)
    TextView projectDescription;

    @BindView(R.id.send_proposal_request_button)
    Button sendProposalButton;

    //Navigation Drawer
    private ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.drawer_layout_project_detail)
    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        ButterKnife.bind(this);
        setupFirebaseAuth();

        //Setting Up the drawer

        drawerToggle = new ActionBarDrawerToggle(ProjectDetailsActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Send Proposal Request Button's Click Listener
        sendProposalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProjectDetailsActivity.this,"Send Request Button Clicked",Toast.LENGTH_LONG).show();
            }
        });



    }
    /**
     * Options Menu Methods
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        MenuItem item = menu.add(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        item.setIcon(R.mipmap.ic_account_circle_black_24dp);
        MenuItem item1 = menu.add("Sign Out");
        item1.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                signOut();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        /**
         *        Firebase Methods
         */
    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    /**
     * Sign out the current user
     */
    private void signOut(){
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
         */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(ProjectDetailsActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    private void checkAuthenticationState(){
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");

            Intent intent = new Intent(ProjectDetailsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }



    public void notificationsShow(MenuItem item) {
        Intent intent = new Intent(ProjectDetailsActivity.this,NotificationsActivity.class);
        startActivity(intent);
    }

    public void createGroup(MenuItem item) {
        Intent intent = new Intent(ProjectDetailsActivity.this,CreateGroupActivity.class);
        startActivity(intent);
    }

    public void openChat(MenuItem item) {
        Toast.makeText(ProjectDetailsActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }

    public void submitOwnProposal(MenuItem item) {
        Intent intent = new Intent(ProjectDetailsActivity.this,CreateProjectActivity.class);
        startActivity(intent);
    }
}
