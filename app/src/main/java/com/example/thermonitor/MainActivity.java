package com.example.thermonitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textMember;
    private EditText editusername;
    private EditText getEditTextPassword2;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        progressDialog   = new ProgressDialog(this);

        buttonRegister       = (Button)   findViewById(R.id.buttonRegister);
        editTextEmail        = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword     = (EditText) findViewById(R.id.editTextPassword);
        textMember           = (TextView) findViewById(R.id.textMember);
        editusername         = (EditText) findViewById(R.id.editusername);
        getEditTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);

        firebaseAuth     = firebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null){
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }

        buttonRegister.setOnClickListener(this);
        textMember.setOnClickListener(this);



    }


    private void registerUser() {

        String email    = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editusername.getText().toString().trim();
        String RePass   = getEditTextPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if  (!TextUtils.equals(password,RePass)){

            Toast.makeText(this, "Please enter the same Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {

            Toast.makeText(this, "Please enter Username", Toast.LENGTH_SHORT).show();
            return;
        }



        progressDialog.setMessage("Loading... " + "please wait");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    if (firebaseAuth.getCurrentUser()!= null) {

                        finish();
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                    }


                } else {
                    Toast.makeText(MainActivity.this, "Could not register ... please try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

        });
    }

        public void onClick (View view){

            if (view == buttonRegister) {
                registerUser();
            }

            if (view == textMember) {
                startActivity(new Intent(this, Login.class));
            }


        }
    }