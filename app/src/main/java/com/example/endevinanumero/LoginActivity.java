package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth fireBaseAuth;
    private Button registrar;
    public EditText etForgot;
    private Button bForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos firebaseAuth
        fireBaseAuth = FirebaseAuth.getInstance();

        // Referenciamos Views
        registrar = (Button)findViewById(R.id.buttonRegister);
        etEmail = (EditText)findViewById(R.id.editText3);
        etPass = (EditText)findViewById(R.id.editText2);
        etForgot = (EditText)findViewById(R.id.editTextDialogUserInput);
        bForgot = (Button)findViewById(R.id.button2);

        // Barrita progreso
        progressDialog = new ProgressDialog(this);

        registrar.setOnClickListener(this);
    }
    public void errorVuiltField(){
        Toast.makeText(this, "ERROR: Falta 1 o mas campos", Toast.LENGTH_SHORT).show();
    }

    public void registrarUsuario() {
        if (etEmail.getText().toString().isEmpty() && etPass.getText().toString().isEmpty()) {
            errorVuiltField();
        } else if (etEmail.getText().toString().isEmpty()) {
            errorVuiltField();
        } else if (etEmail.getText().toString().isEmpty()) {
            errorVuiltField();
        }
         else {

        String email = etEmail.getText().toString().trim();
        String password = etEmail.getText().toString().trim();

        progressDialog.setMessage("Registrando Usuario..");
        progressDialog.show();

        // Creating a new User
        fireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fireBaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "SUCCESS, VERIFICA TU EMAIL PLEASE", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "ERROR EN EL REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    }
                });
    }
    }

    // Click botó registrar
    public void onClick(View view){
        registrarUsuario();
    }

    public void forgotPass(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Forgot email");

       /* builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = "adriangcamacho24@gmail.com";
                emailAddress = etForgot.getText().toString().trim();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this,"MAIL SENT, CHECK YOUR INBOX",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,"ERROR: INVALID MAIL",Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                dialog.dismiss();
            }
        });*/
        bForgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(LoginActivity.this);
                View promptsView = li.inflate(R.layout.alert_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        LoginActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        FirebaseAuth auth = FirebaseAuth.getInstance();

                                        auth.sendPasswordResetEmail(userInput.getText().toString().trim())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(LoginActivity.this,"MAIL SENT, CHECK YOUR INBOX",Toast.LENGTH_LONG).show();
                                                        }
                                                        else {
                                                            Toast.makeText(LoginActivity.this,"ERROR: INVALID MAIL",Toast.LENGTH_LONG).show();

                                                        }

                                                    }
                                                });
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_dialog, null);
        builder.setView(dialoglayout);
        builder.show();


    }

   @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
