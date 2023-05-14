package com.example.balanceher.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.balanceher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button homeBtn;
    static Intent WLBIntent, emergCallIntent,suggestionsIntent, complaintIntent;
    static FirebaseAuth FAuthObj;
    FirebaseUser FUserObj;
    CardView emergCallCard, complaintsCard, suggestionsCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
//View-> //Button
        homeBtn = findViewById(R.id.homeBtn);
        //Card
        emergCallCard = findViewById(R.id.emergCallCard);
        complaintsCard = findViewById(R.id.complaintsCard);
        suggestionsCard = findViewById(R.id.suggestionsCard);

        //Intent
        WLBIntent = new Intent(MainActivity.this, WLBAct.class);
        emergCallIntent = new Intent(MainActivity.this, EmergCallAct.class);
        suggestionsIntent = new Intent(MainActivity.this, SuggestionsAct.class);
        complaintIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","balanceher99@email.com", null));

        //Adding clickListener
        homeBtn.setOnClickListener(this);
        emergCallCard.setOnClickListener(this);
        complaintsCard.setOnClickListener(this);
        suggestionsCard.setOnClickListener(this);

        //Firebase
        FAuthObj = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FUserObj = FAuthObj.getCurrentUser();
        if(FUserObj == null){
            startActivity(new Intent(this, LoginAct.class));
            if(FAuthObj.getCurrentUser() == null)
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homeBtn){
            startActivity(WLBIntent);
        }else if(v.getId() == R.id.emergCallCard){
            startActivity(emergCallIntent);
        }else if(v.getId() == R.id.suggestionsCard){
            startActivity(suggestionsIntent);
        }else if(v.getId() == R.id.complaintsCard){
            processComplaints();
        }
    }

    private void processComplaints() {
        complaintIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        complaintIntent.putExtra(Intent.EXTRA_TEXT, "message");
        startActivity(Intent.createChooser(complaintIntent, "Choose an email client :"));
    }
}