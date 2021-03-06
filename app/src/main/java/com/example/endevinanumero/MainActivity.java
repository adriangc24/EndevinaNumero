package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int numRandom;
    int intentos=0;
    EditText et1;
    TextView tv1;
    Button b1;
    Button bRegist, bLogin;
    static String email=null;
    String userName=null;
    static Map<String,Object>mapita=new HashMap<String, Object>();

    public int numRandomMethod(int numRandom){
        numRandom=(int)(Math.random()*100);
        return numRandom;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bloqueja rotació del móvil
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1=(EditText)findViewById(R.id.editText);
        tv1=(TextView)findViewById(R.id.textView);
        b1 = (Button)findViewById(R.id.button);

    }

    @Override
    protected void onResume() {
        super.onResume();
        numRandom=numRandomMethod(0);
        Log.d("chivato",String.valueOf(numRandom));

    }

    public void button (View view) {
        b1.setText("Guardar");
        tv1.setTextSize(18);

        // Comprovacio camp no sigui buit
        if (et1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Inserta un numero !", Toast.LENGTH_LONG).show();
        } else {
            // Comprovacio camp no sigui menor a zero ni major a 100
            if (Float.valueOf(et1.getText().toString()) < 0 || Float.valueOf(et1.getText().toString()) > 100) {
                Toast.makeText(this, "ERROR: El numero debe estar entre 0 y 100", Toast.LENGTH_LONG).show();
            } else {
                intentos++;
                tv1.setText("Intentos: " + intentos);
                if (Integer.valueOf(et1.getText().toString()) == numRandom) {

                    // Recuperem email del login
                    email = getIntent().getStringExtra("email");
                    DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("users"); //Getting root reference
                    try{
                        userName = email.substring(0, email.indexOf('@'));
                        mapita.put(userName, new User(email, String.valueOf(intentos)));
                        // Store info en la base de datos sin overwrite
                        myRef1.push().setValue(mapita);

                        Intent intent = new Intent(getApplicationContext(), HacerFoto.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                    catch(Exception e){

                    }


                } else if (Integer.valueOf(et1.getText().toString()) < numRandom) {
                    Toast.makeText(this, "El numero introduit es mes petit", Toast.LENGTH_LONG).show();

                } else if (Integer.valueOf(et1.getText().toString()) > numRandom) {
                    Toast.makeText(this, "El numero introduit es mes gran", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
    /*// Boto per anar enrere
    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }*/

}