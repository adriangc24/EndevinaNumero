package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int numRandom;
    int intentos=1;
    EditText et1;
    TextView tv1;
    Button b1;
    Button bRegist, bLogin;
    
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
            b1.setText("Reiniciar");
            tv1.setText("Felicitats has guanyat en "+intentos+" intents");
            tv1.setTextColor(Color.GREEN);
            tv1.setTextSize(24);
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
