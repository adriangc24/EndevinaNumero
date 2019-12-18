package com.example.endevinanumero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import androidx.annotation.RequiresApi;

import static androidx.core.graphics.drawable.IconCompat.getResources;
import static com.example.endevinanumero.HacerFoto.mStorageRef;
import static com.example.endevinanumero.MainActivity.email;
import static com.example.endevinanumero.MainActivity.mapita;
import static com.example.endevinanumero.RankingActivity.array;
import static com.example.endevinanumero.RankingActivity.correo;
import static com.example.endevinanumero.RankingActivity.downloadUri;
import static com.example.endevinanumero.RankingActivity.getFirebaseUser;
import static com.example.endevinanumero.RankingActivity.map;
import static com.example.endevinanumero.RankingActivity.myDrawable;
import static com.example.endevinanumero.RankingActivity.puntuacion;
import static com.example.endevinanumero.RankingActivity.valor;

public class CustomAdapterUsuario extends BaseAdapter implements ListAdapter {
    private ArrayList<User> list = new ArrayList<>();
    private Context context;
    int i=0;

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
        ImageView imageView = view.findViewById(myDrawable) ;
        //Handle TextView and display string from your list
        final TextView listItemText = view.findViewById(R.id.list_item_string1);
        //listItemText.setText(list.get(position).getEmail());
        //listItemText.setText(mapita.get());

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( context.getApplicationContext().getContentResolver(),downloadUri);
                imageView.setImageBitmap(bitmap);

                for(Object value : map.values()){
                    Log.d("-------","dentro");
                    //System.out.println(map.toString());
                    valor=value.toString();
                    correo=valor.substring(valor.indexOf("email=")+6,valor.indexOf('@'));

                    puntuacion=valor.substring(valor.indexOf("puntos=")+7,valor.indexOf(','));
                    listItemText.setText(valor + " " + puntuacion);

                    System.out.println(correo+" "+puntuacion);
                    array.add(new User(correo,puntuacion));
                }


            }
            catch(Exception e){

            }
        i++;
        return view;
    }
}
