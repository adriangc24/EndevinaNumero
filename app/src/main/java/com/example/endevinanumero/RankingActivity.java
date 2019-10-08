package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RankingActivity extends AppCompatActivity {

    TextView tvPlayers;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        email=getIntent().getStringExtra("email");
        // Get info from FireBase

    }
    public void onClick(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
