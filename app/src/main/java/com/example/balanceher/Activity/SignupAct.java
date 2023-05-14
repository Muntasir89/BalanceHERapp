package com.example.balanceher.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupAct extends AppCompatActivity{
    EditText nameET, emailET, mobileNoET, passET, confirmPassET;

    Button signupBtn;
    ProgressBar progressbar;
    static Intent intent;
    String name, email,  mobileNo, password, confirmPass, userID;

    FirebaseAuth FAuthObj;
    FirebaseFirestore FFStoreObj;
    DocumentReference DRObj;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeSignUp();
            }
        });
    }

    private void initializeSignUp() {
        name=nameET.getText().toString().trim();
        email=emailET.getText().toString().trim();
        mobileNo=mobileNoET.getText().toString().trim();
        password=passET.getText().toString().trim();
        confirmPass=confirmPassET.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            nameET.setError("This field is required");
            return;
        }else if(TextUtils.isEmpty(email)){
            emailET.setError("This is required");
            return;
        }else if(TextUtils.isEmpty(mobileNo)){
            mobileNoET.setError("This is required");
            return;
        }else if(TextUtils.isEmpty(password)){
            passET.setError("This is required");
            return;
        }else if(TextUtils.isEmpty(confirmPass)){
            confirmPassET.setError("This field is required");
            return;
        }else if(!password.equals(confirmPass)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }else{
            processSignUp();
        }
    }

    private void processSignUp() {
        progressbar.setVisibility(View.VISIBLE);
        FAuthObj.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupAct.this, "Signup successful", Toast.LENGTH_SHORT).show();

                    userID = FAuthObj.getCurrentUser().getUid();
                    DRObj = FFStoreObj.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("UserName", name);
                    user.put("Email", email);
                    user.put("MobileNo", mobileNo);
                    user.put("pass", password);
                    DRObj.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignupAct.this, "User profile updated", Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.INVISIBLE);
                            //Creating new Intent without coming back
                            intent = new Intent(SignupAct.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e){
                            Toast.makeText(SignupAct.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(SignupAct.this, MainActivity.class));
                        }
                    });
                    //Return Login Activity
                }else{
                    Toast.makeText(SignupAct.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initialize() {
        //Initializing EditText
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        mobileNoET = findViewById(R.id.mobileNoET);
        passET = findViewById(R.id.passET);
        confirmPassET = findViewById(R.id.confirmPassET);
        signupBtn = findViewById(R.id.signupBtn);
        progressbar = findViewById(R.id.progressbar); //ProgessBar

        //Firebase
        FAuthObj = FirebaseAuth.getInstance();
        FFStoreObj = FirebaseFirestore.getInstance();
    }
}