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

public class MainActivity extends AppCompatActivity {

    int numRandom;
    int intentos=1;
    EditText et1;
    TextView tv1;
    Button b1;
    Button bRegist, bLogin;
    String email=null;

    public int numRandomMethod(int numRandom){
         numRandom=(int)(Math.random()*100);
        return numRandom;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numRandom=numRandomMethod(0);

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

           // Store info into firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = database.getReference("/users");
            dbRef.setValue("Hello");

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

}
