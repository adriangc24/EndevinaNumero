package com.example.endevinanumero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Button registrar;
    public EditText etForgot;
    private Button bForgot;
    private String forgotMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos firebaseAuth
        mAuth = FirebaseAuth.getInstance();

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
        Toast.makeText(this, "ERROR: Falta 1 o mes camps", Toast.LENGTH_SHORT).show();
    }
    public void errorLogin(){
        Toast.makeText(LoginActivity.this, "ERROR EN EL LOGIN", Toast.LENGTH_SHORT).show();
    }

    public void logearUsuario(View view){
        String email,password;
        email  = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (etEmail.getText().toString().isEmpty()) {
            errorVuiltField();
        }
        if (etPass.getText().toString().isEmpty()) {
            errorVuiltField();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR: LOGIN",Toast.LENGTH_SHORT).show();
                        }
                    }
                });





    }

    public void registrarUsuario() {
        if (etEmail.getText().toString().isEmpty() && etPass.getText().toString().isEmpty()) {
            errorVuiltField();
        } else if (etEmail.getText().toString().isEmpty()) {
            errorVuiltField();
        } else if (etPass.getText().toString().isEmpty()) {
            errorVuiltField();
        }
         else {

        String email = etEmail.getText().toString().trim();
        String password = etEmail.getText().toString().trim();

        progressDialog.setMessage("Registrando Usuario..");
        progressDialog.show();

        // Creating a new User
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "OK, VERIFICA TU EMAIL PLEASE", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Comprova si la excepcio es del tipus que l'usuari ja existeix
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(LoginActivity.this, "ERROR: USUARI JA REGISTRAT", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "ERROR EN EL REGISTRE", Toast.LENGTH_SHORT).show();
                            }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Inserta tu mail");
        final EditText etPrueba = new EditText(this);
        // Fem que l'edit text sigui de tipus mail, al ser variable local no toquem l'XML
        etPrueba.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        // Afegim al dialog edittext
        builder.setView(etPrueba);
        // Afegim al dialog boto ok
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    // On click apareix el dialog
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!etPrueba.getText().toString().isEmpty()&&etPrueba.getText().toString().contains("@")&&etPrueba.getText().toString().contains(".")) {
                            forgotMail = etPrueba.getText().toString();
                            dialogInterface.cancel();

                            // Enviant mail per recuperar pass
                            FirebaseAuth.getInstance().sendPasswordResetEmail(forgotMail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),"Revisa la teva bustia d'entrada",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Mail no registrado",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"ERROR: MAIL INSERTAT INCORRECTE",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        // Afegim al dialog boto cancel
        builder.setNegativeButton("CANCELA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );
        // Constructor del dialog
        AlertDialog alert =builder.create();
        // Mostrem dialog
        alert.show();




    }




}
