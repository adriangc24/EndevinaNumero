package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
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
    String email=null;
    Map<String,Object>mapita=new HashMap<String, Object>();

    public int numRandomMethod(int numRandom){
        numRandom=(int)(Math.random()*100);
        return numRandom;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numRandom=numRandomMethod(0);
        Log.d("chivato",String.valueOf(numRandom));
        et1=(EditText)findViewById(R.id.editText);
        tv1=(TextView)findViewById(R.id.textView);
        b1 = (Button)findViewById(R.id.button);
    }

    public void button (View view){
        b1.setText("Guardar");
        tv1.setTextSize(18);
        if(et1.getText().toString().isEmpty()){
            Toast.makeText(this,"Inserta un numero !",Toast.LENGTH_LONG).show();
        }

        else if(Integer.valueOf(et1.getText().toString())==numRandom){
            tv1.setText("Intentos: "+intentos++);

            // Recuperem email del login
            email=getIntent().getStringExtra("email");

            // Store info en la base de datos
            DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(); //Getting root reference
            String userName=email.substring(0,email.indexOf('@'));
            mapita.put(userName,new userCreden(email,intentos));
            myRef1.child(userName).setValue(mapita);

            Intent intent = new Intent(getApplicationContext(),RankingActivity.class);
            startActivity(intent);

        }

        else if(Integer.valueOf(et1.getText().toString())<numRandom){
            Toast.makeText(this,"El numero introduit es mes petit",Toast.LENGTH_LONG).show();
            tv1.setText("Intentos: "+intentos++);

        }
        else if(Integer.valueOf(et1.getText().toString())>numRandom){
            Toast.makeText(this,"El numero introduit es mes gran",Toast.LENGTH_LONG).show();
            tv1.setText("Intentos: "+intentos++);

        }
    }
    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}