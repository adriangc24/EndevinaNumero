package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_custom_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.editTextMailF);
        Button btnAceptar = (Button) dialogView.findViewById(R.id.buttonAceptar);
        Button btnCancelar = (Button) dialogView.findViewById(R.id.buttonCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("forgotMail",editText.getText().toString());
                startActivity(intent);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
