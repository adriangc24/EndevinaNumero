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
    }

    public void button (View view){
        if(Integer.valueOf(et1.getText().toString())==numRandom){
            tv1.setText("Intentos: "+intentos++);
            tv1.setText("Felicitats has guanyat en "+intentos+" intents");
            numRandom=numRandomMethod(0);
            intentos=0;
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
