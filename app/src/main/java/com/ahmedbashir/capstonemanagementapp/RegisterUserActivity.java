package com.ahmedbashir.capstonemanagementapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;

public class RegisterUserActivity extends AppCompatActivity {
    public static final String TAG = RegisterUserActivity.class.getSimpleName();
    //Teacher DOMAIN
    private static final String DOMAIN_TEACHER = "faculty.gift.edu.pk";
    //Student DOMAIN
    private static final String DOMAIN_STUDENT = "gift.edu.pk";
    private final Context context = RegisterUserActivity.this;


    @BindView(R.id.button_already_user_signin)
    Button alreadyMemberBtn;

    @BindView(R.id.button_signup)
    Button signUpButton;

    @BindView(R.id.editText_signup_email)
    EditText signUpEmail;

    @BindView(R.id.editText_singup_password)
    EditText signUpPassword;

    @BindView(R.id.editText_signUp_repassword)
    EditText signUpPasswordConfirmation;

    @BindView(R.id.editText_name)
    EditText fullName;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);
        //Getting the text from required fields


        //If the user is already a member then he/she clicks the respective button to got login screen
        //This method implements the click listener of that

        alreadyMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadyMemberSignIn();
            }
        });

        //Register User
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick:attempting to register user");

                //Check for null valued fields
                if(!isEmpty(fullName.getText().toString())
                        && !isEmpty(signUpEmail.getText().toString())
                        && !isEmpty(signUpPassword.getText().toString())
                        && !isEmpty(signUpPasswordConfirmation.getText().toString())){

                    if(isValidDomain(signUpEmail.getText().toString())){
                        //If Domain is valid
                        //Now check if passwords match
                        if(matchPasswords(signUpPassword.getText().toString(),signUpPasswordConfirmation.getText().toString())){
                            //Passwords Matched Registration should be done
                            if (signUpPassword.length() < 6) {
                                Toast.makeText(context, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                            }else {
                                registerNewEmail(signUpEmail.getText().toString(), signUpPassword.getText().toString());
                                Toast.makeText(context,"User: " + fullName.getText().toString() + " registered Successfully",Toast.LENGTH_LONG ).show();
                            }
                        }
                        else{
                            View vieww = findViewById(R.id.registerUserView);
                            String message =getString(R.string.password_mismatch_error);
                            int duration = Snackbar.LENGTH_LONG;
                            showSnackbar(vieww, message, duration);
                        }
                    }
                    else{
                        showSnackbar(findViewById(R.id.registerUserView),getString(R.string.not_valid_domain),Snackbar.LENGTH_INDEFINITE);
                    }
                }
                else{
                    showSnackbar(findViewById(R.id.registerUserView),getString(R.string.fill_all_fields),Snackbar.LENGTH_INDEFINITE);
                }
            }
        });
        hideSoftKeyboard();
    }



    private boolean isValidDomain(String email) {
        Log.d(TAG,"isValidDomain: verifying email domain: " + email);
        boolean isValid = false;
        String domain = email.substring(email.indexOf("@")+1).toLowerCase();
        Log.d(TAG,"isValidDomain: users domain: " + domain);
        if(domain.equals(DOMAIN_TEACHER) || domain.equals(DOMAIN_STUDENT)){
            isValid = true;
        }
        return isValid;
    }

    private boolean matchPasswords(String password, String confirmPassword) {
        Log.d(TAG,"matchPasswords: Verifying Passwords");
        return password.equals(confirmPassword);
    }

    private void alreadyMemberSignIn() {
        Log.d(TAG,"redirectLoginScreen: redirecting to login screen.");
        Intent i = new Intent(RegisterUserActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showSnackbar(View view, String message, int duration)
    {
        final Snackbar snackbar = Snackbar.make(view, message, duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getColor(R.color.snackBarColor));
        // Set an action on it, and a handler
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
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

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void registerNewEmail(final String email, String password){

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sendVerificationEmail();
                            FirebaseAuth.getInstance().signOut();
                            //redirect the user to the login screen
                            alreadyMemberSignIn();
                        }
                        if (!task.isSuccessful()) {
                            showSnackbar(findViewById(R.id.registerUserView),"Unable to Register. Try Again.",Snackbar.LENGTH_LONG);
                        }
                        hideDialog();

                    }
                });
    }


    private void sendVerificationEmail(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context, R.string.verficiation_email_success,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context, R.string.verification_email_failure,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
