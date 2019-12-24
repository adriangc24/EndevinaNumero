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

import static com.example.endevinanumero.RankingActivity.array;
import static com.example.endevinanumero.RankingActivity.defecto;


public class CustomAdapterUsuario extends BaseAdapter implements ListAdapter {
    private ArrayList<User> list = new ArrayList<>();
    private Context context;
    static int i = 0;
    static Bitmap bitmap, foto;
    String correo, valor, puntuacion;
    static StorageReference userRef;
    static Uri downloadUri;
    static ImageView imageView;
    static String user;

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
        ArrayList<String> classified = new ArrayList<>();
        boolean trobat = false;
        imageView = view.findViewById(R.id.imageView4);
        TextView listItemText = view.findViewById(R.id.list_item_string1);

        try {

            Log.d("------",array.toString());
            correo = array.get(position).email;
            user = correo.substring(0,correo.indexOf("@"));
            foto = array.get(position).getFoto();
            puntuacion = array.get(position).puntos;

            listItemText.setText(user + " con " + puntuacion + " intentos");
            getPhoto(correo);
            //imageView.setImageBitmap(foto);
            //imageView.setImageBitmap(arrayBitmap.get(position));

        } catch (Exception e) {
            e.printStackTrace();
        }
        i++;

        return view;
    }

    public static void getPhoto(String email) {
        userRef = mStorageRef.child("images/" + email + ".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;

         userRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // si es ok la descarga de imagen:
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // si no es ok ponemos la default
                imageView.setImageBitmap(defecto);
            }
        });
    }
    }

