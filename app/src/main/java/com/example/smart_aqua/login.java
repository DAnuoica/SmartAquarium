package com.example.smart_aqua;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private Button mSignup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mSignup = (Button) findViewById(R.id.signupBtn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() != null ){
                    startActivity(new Intent(login.this, MainActivity.class));
                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startSignIn();
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignup();
            }
        });
    }

    private void startSignup() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(login.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(login.this, "Sign up successful", Toast.LENGTH_LONG).show();

                    }
                    if (!task.isSuccessful()){

                        Toast.makeText(login.this, "There was a problem Signing up", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    @Override
    protected void onStart(){ //Listens for changes to Auth
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(login.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){

                        Toast.makeText(login.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}
//}
//
//    private EditText mEmailField;
//    private EditText mPasswordField;
//    private Button mLoginBtn;
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//        mEmailField = (EditText) findViewById(R.id.emailField);
//        mPasswordField = (EditText) findViewById(R.id.passwordField);
//        mLoginBtn = (Button) findViewById(R.id.loginBtn);
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
//                if(firebaseAuth.getCurrentUser() != null ){
//                    startActivity(new Intent(login.this, MainActivity.class));
//                }
//            }
//        };
//
//        mLoginBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                startSignIn();
//            }
//        });
//    }
//
//    @Override
//    protected void onStart(){ //Listens for changes to Auth
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    private void startSignIn(){
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
//            Toast.makeText(login.this, "thiếu email hoặc password ", Toast.LENGTH_LONG).show();
//        }
//        else {
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (!task.isSuccessful()){
//                        Toast.makeText(login.this, "sai tên hoặc password ", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
//    }
//}
