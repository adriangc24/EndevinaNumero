package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.endevinanumero.HacerFoto.mStorageRef;
import static com.example.endevinanumero.MainActivity.email;


import static com.example.endevinanumero.HacerFoto.uploadTask;

public class RankingActivity extends AppCompatActivity {

    static Map<String, Object> map;
    static ArrayList<User> array=new ArrayList<>();
    static StorageReference userRef;
    static Bitmap bitmap, defecto;

    static String valor=null;
    static String correo,puntuacion=null;
    static ListView lv;
    static Uri downloadUri;
    static ArrayList<Bitmap> arrayBitmap = new ArrayList<>();
    static Drawable myDrawable;
    final CustomAdapterUsuario adaptador = new CustomAdapterUsuario(array, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        myDrawable = getResources().getDrawable(R.drawable.perfil);
        defecto = BitmapFactory.decodeResource(getResources(),R.drawable.perfil);

        lv = findViewById(R.id.listView);
        lv.setAdapter(adaptador);

       FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                            map = dataSnapshot.getValue(genericTypeIndicator );
                        }

                        for(Object value : map.values()){
                            valor=value.toString();
                            correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf("}}"));
                            puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                            //getPhoto(correo);
                            array.add(new User(correo,puntuacion,bitmap));
                        }
                        Collections.sort(array, new Comparator<User>() {
                            @Override
                            public int compare(User o1 , User o2) {
                                return Integer.valueOf(o1.getPuntos())-Integer.valueOf(o2.getPuntos());
                            }
                        });
                        //quitarRepetidos();
                        lv.setAdapter(adaptador);
                        adaptador.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    public static void getPhoto(final String email) {
        try {
            userRef = mStorageRef.child("images/" + email + ".jpg");
            //userRef = mStorageRef.child("images/adriangcamacho24@gmail.com.jpg");

        }
        catch(Exception e){
            // Set Default Photo
            Log.d("Default","Photo");

        }
        final long ONE_MEGABYTE = 1024 * 1024;
        userRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try{
                    Log.d("SUCCESS","!");
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    arrayBitmap.add(bitmap);
                }
                catch(Exception e){
                    Log.e("ERROR:","bytes length = 0");
                }
                if(bitmap==null){
                    Log.e("ERROR","BITMAP NULO");
                }
                array.add(new User(correo,puntuacion,bitmap));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ERROR; ","al coger la foto");
                array.add(new User(correo,puntuacion,defecto));
            }
        });
    }

    public void quitarRepetidos() {
        Log.d("--array inicial",array.toString());

        Set<User> hashSet = new HashSet(array);
        array.clear();
        array.addAll(hashSet);


        Log.d("--array final",array.toString());
    }

}