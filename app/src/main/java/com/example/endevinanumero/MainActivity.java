package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int numRandom;
    int intentos=1;
    EditText et1;
    TextView tv1;
    public int numRandomMethod(int numRandom){
        return numRandom=(int)(Math.random()*100);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1=(EditText)findViewById(R.id.editText);
        tv1=(TextView)findViewById(R.id.textView);
    }

    public void button (View view){
        if(Integer.valueOf(et1.getText().toString())==numRandom){
            tv1.setText("Intentos: "+intentos++);
            Toast.makeText(this,"NUMERO TROBAT !",Toast.LENGTH_LONG).show();
            numRandom=numRandomMethod(0);
            intentos=0;
            tv1.setText("Intentos: "+intentos++);

        }

         if(Integer.valueOf(et1.getText().toString())<numRandom){
            Toast.makeText(this,"El numero introduit es mes petit",Toast.LENGTH_LONG).show();
            tv1.setText("Intentos: "+intentos++);

        }
         if(Integer.valueOf(et1.getText().toString())>numRandom){
            Toast.makeText(this,"El numero introduit es mes gran",Toast.LENGTH_LONG).show();
            tv1.setText("Intentos: "+intentos++);

        }
    }

}
