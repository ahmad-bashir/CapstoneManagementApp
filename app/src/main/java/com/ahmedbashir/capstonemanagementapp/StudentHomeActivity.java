package com.ahmedbashir.capstonemanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

import Model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentHomeActivity extends AppCompatActivity {
    public static final String TAG = StudentHomeActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthListener;

    RecyclerView studentRecyclerView;

    private RecyclerView.Adapter adapter;

    private List<Project> projectsList;

    private ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //NavigationView navigationView;



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


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        setupFirebaseAuth();
        ButterKnife.bind(this);

        drawerToggle = new ActionBarDrawerToggle(StudentHomeActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentRecyclerView = findViewById(R.id.studentRecyclerView);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        for (int i=0; i < 10; i++ ){
            Project project = new Project(
                    "Project " + i + 1,"By QSD"
            );
            projectsList.add(project);

        }
        adapter = new StudentAdapter(projectsList,this);
        studentRecyclerView.setAdapter(adapter);

       /* //Drawer Items listener
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notification_item:
                        Intent intent = new Intent(StudentHomeActivity.this,ProjectDetailsActivity.class);
                        startActivity(intent);

                }
                return false;
            }
        });
*/


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                    Intent intent = new Intent(StudentHomeActivity.this, LoginActivity.class);
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

            Intent intent = new Intent(StudentHomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }


    public void notificationsShow(MenuItem item) {
        Intent intent = new Intent(StudentHomeActivity.this,NotificationsActivity.class);
        startActivity(intent);
    }

    public void submitOwnProposal(MenuItem item) {
        Intent intent = new Intent(StudentHomeActivity.this,CreateProjectActivity.class);
        startActivity(intent);
    }

    public void createGroup(MenuItem item) {
        Intent intent = new Intent(StudentHomeActivity.this,CreateGroupActivity.class);
        startActivity(intent);
    }

    public void openChat(MenuItem item) {
        Toast.makeText(StudentHomeActivity.this,"Easy Chat",Toast.LENGTH_LONG).show();
    }
}
