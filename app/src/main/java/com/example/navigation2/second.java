package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import java.security.InvalidKeyException;

public class second extends AppCompatActivity {

    private EditText username1,password1;
    private  Button login1,loginadmin1;
    private TextView signup,frgetpass;
    private  FirebaseAuth firebaseAuth;
    private ProgressBar pg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        firebaseAuth=FirebaseAuth.getInstance();
        username1=(EditText)findViewById(R.id.username);
        pg1=(ProgressBar)findViewById(R.id.pg1);
        password1=(EditText)findViewById(R.id.password);
        login1=(Button)findViewById(R.id.login);
        signup=(TextView) findViewById(R.id.signup);
        frgetpass=(TextView)findViewById(R.id.frgetpass);
        loginadmin1=(Button)findViewById(R.id.Loginasadmin);

        if(firebaseAuth.getCurrentUser()!=null){
            Intent i=new Intent(second.this,MainActivity.class);
            startActivity(i);
        }
        loginadmin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(second.this,loginadmin2.class);
                startActivity(intent);
            }
        });
        frgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username1.getText().toString().trim().isEmpty()){
                    username1.setError("Please Enter Email ");
                    username1.requestFocus();
                }
                else{
                    String mail=username1.getText().toString().trim();

                    firebaseAuth.sendPasswordResetEmail(mail);
                Toast.makeText(second.this,"password reset link send",Toast.LENGTH_SHORT).show();
            }}
        });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg1.setVisibility(ProgressBar.VISIBLE);

                Login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(second.this,Registration.class);
                startActivity(i);
            }
        });
    }
    public void Login() {
        pg1.setVisibility(ProgressBar.VISIBLE);
        pg1.requestFocus();
        String usern, passn;
        usern=username1.getText().toString().trim();
        passn=password1.getText().toString().trim();
        if (usern.isEmpty()) {
            username1.setError("Enter user name");
            username1.requestFocus();
            pg1.setVisibility(ProgressBar.INVISIBLE);
            return;
        }
        if (passn.isEmpty()) {
            password1.setError("password is madatory");
            password1.requestFocus();
            pg1.setVisibility(ProgressBar.INVISIBLE);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(usern).matches()) {
           username1.setError("enter valid username");
            username1.requestFocus();
            pg1.setVisibility(ProgressBar.INVISIBLE);
            return;
        }
        if (passn.length() < 6) {
            password1.setError("invalid password");
            password1.requestFocus();
            pg1.setVisibility(ProgressBar.INVISIBLE);
            return;
        }

        pg1.setTag("Signing in");

        firebaseAuth.signInWithEmailAndPassword(usern, passn).addOnCompleteListener(second.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser!=null && firebaseUser.isEmailVerified()){

                        Toast.makeText(second.this, "logged in", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(second.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pg1.setVisibility(ProgressBar.INVISIBLE);
                        startActivity(i);}
                    else{
                        Toast.makeText(second.this, "Email not verified", Toast.LENGTH_SHORT).show();
                        pg1.setVisibility(ProgressBar.INVISIBLE);
                        return;
                    }

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(second.this, " invalid credentials", Toast.LENGTH_SHORT).show();
                        pg1.setVisibility(ProgressBar.INVISIBLE);
                    } else {
                        Toast.makeText(second.this, "authentication failed", Toast.LENGTH_SHORT).show();
                        //Intent i=new Intent(second.this,MainActivity.class);
                        //startActivity(i);
                        pg1.setVisibility(ProgressBar.INVISIBLE);
                    }
                }
            }
        });
    }
    public boolean connectioncheck() {
        boolean connected= false;
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==NetworkInfo.State.CONNECTED){
            connected=true;
        }
        else {
            connected=false;
        }
        return  connected;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(connectioncheck()==true){

        }
        else{
            Toast.makeText(second.this,"Please connect to internet",Toast.LENGTH_LONG).show();
        }
    }
}
