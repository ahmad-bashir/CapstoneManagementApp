package com.ahmedbashir.capstonemanagementapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();
    //Teacher DOMAIN
    private static final String DOMAIN_TEACHER = "faculty.gift.edu.pk";
    //Student DOMAIN
    private static final String DOMAIN_STUDENT = "gift.edu.pk";
    @BindView(R.id.button_register_user)
    Button registerUserButton;

    @BindView(R.id.button_login)
    Button loginButton;

    @BindView(R.id.editText_login_email)
    EditText loginEmail;

    @BindView(R.id.editText_login_password)
    EditText loginPassword;

    @BindView(R.id.progressBarLogin)
    ProgressBar mProgressBar;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUpFirebaseAuth();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(loginEmail.getText().toString()) && !isEmpty(loginPassword.getText().toString())){
                    Log.d(TAG,"onClick: attempting to authenticate");
                    showDialog();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(loginEmail.getText().toString(),loginPassword.getText().toString()) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            hideDialog();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Incorrect Email or Password. Try Again",Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, R.string.login_fail_message,Toast.LENGTH_LONG).show();
                }
            }
        });

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notMemberRegisterUser();
            }
        });


    }

    private void notMemberRegisterUser() {
        Intent intent = new Intent(LoginActivity.this,RegisterUserActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    if(user.isEmailVerified()){
                        //Checking the type of user here and setting up the UI accordingly
                        String usermail = user.getEmail().toString();
                        if(usermail.substring(usermail.indexOf("@")+1).equals(DOMAIN_STUDENT)){
                            Log.d(TAG,"onAuthChange: signed_in" + user.getUid() + user.getDisplayName());
                            Toast.makeText(LoginActivity.this,"Authenticated With: " + user.getEmail() + " as Student",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this,StudentHomeActivity.class));
                        }


                    }else {
                        Toast.makeText(LoginActivity.this,"Check Your Email Inbox for a Verification Link",Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }else {
                    Log.d(TAG,"onAuthChange: signed_out");
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    private boolean isEmpty(String someString){
        return someString.equals("");
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
