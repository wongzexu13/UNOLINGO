package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class login_screen extends AppCompatActivity {

    public TextView register, forgetPassword;
    public Button signIn;
    public ProgressBar progressBar1;
    public RelativeLayout progressBar2;
    private TextView google;
    private GoogleSignInClient client;
    public EditText email_id;
    public EditText password_id;
    public FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        register = findViewById(R.id.linkToRegister);
        signIn = findViewById(R.id.signInBtn);
        google = findViewById(R.id.googleSignIn);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        email_id = findViewById(R.id.loginEmail);
        password_id = findViewById(R.id.loginPassword);
        forgetPassword = findViewById(R.id.forgetPassword);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_screen.this, register_screen.class);
                startActivity(i);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_screen.this, forget_password.class);
                startActivity(i);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                loginUserAccount();
            }
        });

        //Google Sign In Options
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();

        client = GoogleSignIn.getClient(this, options);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
    }

    protected void loginUserAccount(){

        String email = email_id.getText().toString();
        String password = password_id.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                            "Please enter email",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                            "Please enter password",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(login_screen.this, home.class);
                            startActivity(intent);
                        }
                        else{
                            // sign-in failed
                            progressBar1.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),
                                            "Failed to login, please try again",
                                            Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), home.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(login_screen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }catch(ApiException e){
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!= null){
//            Intent intent = new Intent(login_screen.this, home.class);
//            startActivity(intent);
//        }
//    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            Intent intent = new Intent(login_screen.this, home.class);
            startActivity(intent);
        }
    }

}