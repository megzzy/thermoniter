package com.example.thermonitor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {

    private Button       buttonResetPass;
    private EditText     Emailpassreset;
    private TextView     textReset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        buttonResetPass       = (Button)   findViewById(R.id.buttonResetPass);
        Emailpassreset        = (EditText) findViewById(R.id.Emailpassreset);
        textReset             = (TextView) findViewById(R.id.textReset);
        firebaseAuth          = FirebaseAuth.getInstance();

        buttonResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserMail = Emailpassreset.getText().toString().trim();

                if (UserMail.equals("")){
                    Toast.makeText(ResetPass.this,"Please enter your registered Email",Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(UserMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ResetPass.this,"Password reset link sent successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetPass.this,Login.class));

                            }
                            else{
                                Toast.makeText(ResetPass.this,"Error sending password reset link",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            }
        });
    }
}
