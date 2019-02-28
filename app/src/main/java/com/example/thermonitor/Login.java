package com.example.thermonitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button         buttonLogin;
    private TextView       textNotMember;
    private EditText       editTextPassword;
    private EditText       editTextEmail;
    private ProgressDialog progressDialog;
    private FirebaseAuth   firebaseAuth;
    private TextView       textForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail    = (EditText) findViewById(R.id.editTextEmail);
        buttonLogin      = (Button) findViewById(R.id.buttonLogin);
        textNotMember    = (TextView) findViewById(R.id.textNotMember);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textForgotPass   = (TextView) findViewById(R.id.textForgotPass);




        buttonLogin.setOnClickListener(this);
        textNotMember.setOnClickListener(this);
        textForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ResetPass.class));
            }
        });

        progressDialog   = new ProgressDialog(this);
        firebaseAuth     = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null){
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }


    }

    private void userLogin(){
        String email    = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Loading... " + "please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                }
                else {
                    Toast.makeText(Login.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {


            if (v==buttonLogin){
                userLogin();
            }
            if (v==textNotMember){
                if (firebaseAuth.getCurrentUser()== null) {
                    finish();

                    startActivity(new Intent(this, MainActivity.class));
                }

            }

        }


}
