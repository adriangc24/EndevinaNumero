package com.example.endevinanumero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static com.example.endevinanumero.HacerFoto.mStorageRef;
import static com.example.endevinanumero.MainActivity.email;
import static com.example.endevinanumero.MainActivity.mapita;
import static com.example.endevinanumero.RankingActivity.array;
import static com.example.endevinanumero.RankingActivity.correo;
import static com.example.endevinanumero.RankingActivity.downloadUri;
import static com.example.endevinanumero.RankingActivity.map;
import static com.example.endevinanumero.RankingActivity.myDrawable;
import static com.example.endevinanumero.RankingActivity.puntuacion;
import static com.example.endevinanumero.RankingActivity.valor;

public class CustomAdapterUsuario extends BaseAdapter implements ListAdapter {
    private ArrayList<User> list = new ArrayList<>();
    private Context context;
    static int i=0;
    String correo, valor, puntuacion;
    static Uri downloadUri;
    static ImageView imageView;
    static Bitmap my_image;

    public CustomAdapterUsuario(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_customadapterusuario, null);
        }
        ArrayList<String> classified=new ArrayList<>();
        boolean trobat=false;
        imageView = view.findViewById(R.id.imageView4) ;
        TextView listItemText = view.findViewById(R.id.list_item_string1);
        //listItemText.setText(list.get(position).getEmail());
        //listItemText.setText(mapita.get());

            try {
                    //valor=map.get(i).toString();
                    //correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf('@'));
                    //puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                    correo=array.get(i).email;
                    getPhoto(array.get(i).getEmail());
                    puntuacion=array.get(i).puntos;
                    for(String s : classified) {
                        if(classified.isEmpty()){
                            classified.add(correo+","+puntuacion);
                        }
                        if (s.equals(correo)) {
                            trobat=true;
                        }
                    }
                    if(!trobat){
                        listItemText.setText(correo + " con " + puntuacion +" Puntos");
                    }
                    trobat=false;
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap( context.getApplicationContext().getContentResolver(),downloadUri);
                    //imageView.setImageBitmap(bitmap);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        i++;
            if(i==array.size()){
                i=0;
            }
        return view;
    }
    public static void getPhoto(String email) {


        mStorageRef.child("images/adriangcamacho24@gmail.com.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        })

        .addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                //here, task is completed task returned by onComplete.
                Bitmap bm = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                Log.d("pepe---",task.getResult().toString());
                imageView.setImageBitmap(bm);
            }
        });


    }

    }

