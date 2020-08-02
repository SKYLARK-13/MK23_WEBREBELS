package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class loginadmin2 extends AppCompatActivity {
    private  EditText secretcode,emailadmin,passwordadmin;
    private Button loginadmin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin2);

        secretcode=(EditText)findViewById(R.id.secretcode);
        loginadmin=(Button)findViewById(R.id.loginadmin12);
        emailadmin=findViewById(R.id.emailadmin);
        passwordadmin=findViewById(R.id.passwordadmin);
        firebaseAuth=FirebaseAuth.getInstance();
        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = "12345";
                String k = secretcode.getText().toString().trim();
                String usern = emailadmin.getText().toString().trim();
                String passn = passwordadmin.getText().toString().trim();
                if ( usern.isEmpty()|| passn.isEmpty()) {
                    Toast.makeText(loginadmin2.this, "please fill all the fields", Toast.LENGTH_LONG).show();
                } else {
                    if (code.equals(k)) {
                        firebaseAuth.signInWithEmailAndPassword(usern, passn).addOnCompleteListener(loginadmin2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if (firebaseUser != null && firebaseUser.isEmailVerified()) {


                                        Toast.makeText(loginadmin2.this, "logged in", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(loginadmin2.this, AdminActivity.class);


                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(loginadmin2.this, "Email not verified", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(loginadmin2.this, " invalid credentials", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(loginadmin2.this, "authentication failed", Toast.LENGTH_SHORT).show();
                                        //Intent i=new Intent(second.this,MainActivity.class);
                                        //startActivity(i);
                                    }
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(loginadmin2.this, "Please enter code correctly", Toast.LENGTH_SHORT).show();
                        secretcode.setText("");
                }

            }}
        });}

    @Override
    public void onBackPressed() {

        this.finishAffinity();
    }
}

