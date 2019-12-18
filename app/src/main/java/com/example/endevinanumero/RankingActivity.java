package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static androidx.core.graphics.drawable.IconCompat.getResources;
import static com.example.endevinanumero.HacerFoto.mStorageRef;
import static com.example.endevinanumero.MainActivity.email;


import static com.example.endevinanumero.HacerFoto.uploadTask;

public class RankingActivity extends AppCompatActivity {

    TextView tvPlayers;
    String userName;
    String punts;
    ArrayList<String> Userlist;
    static Map<String, Object> map;
    static ArrayList<User> array=new ArrayList<>();
    static String valor=null;
    static String correo,puntuacion=null;
    static ListView lv;
    int puntos;
    static Uri downloadUri;
    ArrayAdapter<User> itemsAdapter;
    static Drawable myDrawable;
    CustomAdapterUsuario adaptador = new CustomAdapterUsuario(array, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        myDrawable = getResources().getDrawable(R.drawable.avatarr);

        //tvPlayers = (TextView) findViewById(R.id.textViewPlayers);

        lv = findViewById(R.id.listView);
        getFirebaseUser();

        //itemsAdapter = new ArrayAdapter<User>(this, R.layout.custom_textview,array)

       FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //User user = snapshot.getValue(User.class);
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                            map = dataSnapshot.getValue(genericTypeIndicator );
                        }

                        for(Object value : map.values()){
                            //System.out.println(map.toString());
                            valor=value.toString();
                            correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf('@'));
                            puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                            System.out.println(correo+" "+puntuacion);
                            array.add(new User(correo,puntuacion));
                        }

                        Collections.sort(array, new Comparator<User>() {
                            @Override
                            public int compare(User o1 , User o2) {
                                return Integer.valueOf(o1.getPuntos())-Integer.valueOf(o2.getPuntos());
                            }
                        });

                        //lv.setAdapter(itemsAdapter);
                        lv.setAdapter(adaptador);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        lv.setAdapter(adaptador);

    }
    public static void getFirebaseUser(){
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //User user = snapshot.getValue(User.class);
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                            map = dataSnapshot.getValue(genericTypeIndicator );
                        }

                        for(Object value : map.values()){
                            //System.out.println(map.toString());
                            valor=value.toString();
                            correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf('@'));
                            puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                            System.out.println(correo+" "+puntuacion);
                            array.add(new User(correo,puntuacion));
                        }

                        Collections.sort(array, new Comparator<User>() {
                            @Override
                            public int compare(User o1 , User o2) {
                                return Integer.valueOf(o1.getPuntos())-Integer.valueOf(o2.getPuntos());
                            }
                        });

                        //lv.setAdapter(itemsAdapter);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public static void getPhoto() {
        final StorageReference ref = mStorageRef.child("images/"+email+".jpg");
        //UploadTask uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mStorageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    /*public void onClick (View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("email", email);
        startActivity(intent);
    }*/
}