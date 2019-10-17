package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    TextView tvPlayers;
    String email;
    String userName;
    String punts;
    ListView lv;
    ArrayList<String> Userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lv = (ListView) findViewById(R.id.listView);
        tvPlayers = (TextView) findViewById(R.id.textViewPlayers);
        email = getIntent().getStringExtra("email");
        /*ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(itemsAdapter);
        */

        //final FirebaseDatabase database = FirebaseDatabase.getInstance();
       //DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("adriangcamacho24");

            /*myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                        email = dataSnapshot.child("email").getValue().toString();
                        punts = dataSnapshot.child("puntos").getValue().toString();
                        userName = email.substring(0, email.indexOf('@'));
                        tvPlayers.setText("L'usuari "+userName+" amb "+punts+" intents");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/


        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //User user = snapshot.getValue(User.class);
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                            Map<String, Object> map = dataSnapshot.getValue(genericTypeIndicator );
                            System.out.println(map.toString());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });






    }
    public void onClick (View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}

