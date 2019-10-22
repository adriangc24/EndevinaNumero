package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.Iterator;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    TextView tvPlayers;
    String email;
    String userName;
    String punts;
    ArrayList<String> Userlist;
    Map<String, Object> map;
    HashMap<String,String> mapita;
    String valor=null;
    String correo,puntuacion=null;
    ListView lv;
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        lv = (ListView) findViewById(R.id.listView);
        //tvPlayers = (TextView) findViewById(R.id.textViewPlayers);
        email = getIntent().getStringExtra("email");

        itemsAdapter = new ArrayAdapter<String>(this, R.layout.custom_textview);

        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //User user = snapshot.getValue(User.class);
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                            map = dataSnapshot.getValue(genericTypeIndicator );
                        }

                        for(Object value : map.values()){
                            System.out.println(map.toString());
                            valor=value.toString();
                            correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf('@'));
                            puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                            System.out.println(correo+puntuacion);
                            itemsAdapter.add(correo+" en "+puntuacion+" intentos");
                        }
                        lv.setAdapter(itemsAdapter);
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