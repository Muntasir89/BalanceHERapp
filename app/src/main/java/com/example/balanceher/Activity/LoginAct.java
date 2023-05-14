package com.example.balanceher.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balanceher.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {
    TextView registerTV;
    EditText emailET, passET;
    Button loginBtn;
    ProgressBar progressbar;
    static Intent signupIntent, homeIntent;
    //Firebase Objects
    FirebaseAuth FAuthObj;

    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

    }

    private void initialize() {
        //TextView
        registerTV = findViewById(R.id.registerTV);
        //EditText
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);
        loginBtn = findViewById(R.id.loginBtn);
        registerTV = findViewById(R.id.registerTV);

        progressbar = findViewById(R.id.progressbar);
        //Firebase
        FAuthObj = FirebaseAuth.getInstance();
        //Intent
        signupIntent = new Intent(this, SignupAct.class);
        homeIntent = new Intent(this, MainActivity.class);;

        loginBtn.setOnClickListener(this);
        registerTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginBtn){
            initializeLogin();
        }
        else if(v.getId()==R.id.registerTV){
            startActivity(signupIntent);
        }
    }

    private void initializeLogin() {
        email = emailET.getText().toString().trim();
        password = passET.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
        }else{
            processLogin();
        }
    }

    private void processLogin() {
        progressbar.setVisibility(View.VISIBLE);
        FAuthObj.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginAct.this, "Login successful", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    //Creating new Intent without coming back
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("EXIT", true);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginAct.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                emailET.setText("");
                passET.setText("");
            }
        });
    }
}