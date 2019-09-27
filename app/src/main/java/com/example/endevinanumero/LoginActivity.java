package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText)findViewById(R.id.editText3);
        etPass = (EditText)findViewById(R.id.editText2);


            }
    public void auth(View view) {
        // Check if user is signed in (non-null) and update UI accordingly.
        if(!etEmail.getText().toString().isEmpty()&&!etPass.getText().toString().isEmpty()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
        }
        else {
            Toast.makeText(this,"ERROR: 1 o mas campos vacíos",Toast.LENGTH_LONG).show();
        }


    }

}
