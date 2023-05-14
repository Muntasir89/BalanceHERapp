package com.example.balanceher.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.balanceher.Adapter.EmergCallAda;
import com.example.balanceher.R;

public class EmergCallAct extends AppCompatActivity {
    RecyclerView recyclerView;
    EmergCallAda emergCallAda;

    ImageView go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emerg_call);

        go_back = findViewById(R.id.go_back);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emergCallAda = new EmergCallAda(this);
        recyclerView.setAdapter(emergCallAda);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}