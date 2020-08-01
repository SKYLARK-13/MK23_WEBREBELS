package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
 private EditText username2,password2,reenter, name;
 private Button signup;
 private TextView Login;
 private FirebaseAuth firebaseAuth;
 private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username2=(EditText)findViewById(R.id.username2);
        password2=(EditText)findViewById(R.id.password2);
        reenter=(EditText)findViewById(R.id.reenter);
        name=(EditText)findViewById(R.id.name);
        signup=(Button)findViewById(R.id.signup1);
        Login=(TextView) findViewById(R.id.login1);
        firebaseAuth=FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Registration.this,second.class);
                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    final String name1,username3,password3,reenter1;
                    name1=name.getText().toString().trim();
                    username3=username2.getText().toString().trim();
                    password3=password2.getText().toString().trim();
                    reenter1=reenter.getText().toString().trim();
                    if(name1.isEmpty()){
                        name.setError("Enter name");
                        name.requestFocus();
                    }
                    if (username3.isEmpty()) {
                        username2.setError("Enter email");
                        username2.requestFocus();
                        return;
                    }
                    if (password3.isEmpty()) {
                        password2.setError("password is madatory");
                        password2.requestFocus();
                        return;
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(username3).matches()) {
                        username2.setError("enter valid username");
                        username2.requestFocus();
                        return;
                    }
                    if (password3.length() < 6) {
                        password2.setError("invalid password");
                        password2.requestFocus();
                        return;
                    }
                    if(!(reenter1.equals(password3))) {
                        reenter.setError("password not match");
                        reenter.requestFocus();
                        return;

                    }
                    firebaseAuth.createUserWithEmailAndPassword(username3,password3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user=new User(name1,username3);
                                updatedatabase(user);
                                sendverificationmail();
                                updateui();

                            }}
                    });

                }

        });
    }

    private void updatedatabase(User user) {
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        String id=firebaseAuth.getInstance().getCurrentUser().getUid();
        if(id==null){
            Toast.makeText(Registration.this,"please try again",Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseDatabase.getInstance().getReference("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registration.this,"sucessfully created",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }}
    private void sendverificationmail() {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Registration.this,"verification mail sent",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Registration.this,"error",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public void updateui(){
        FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            Intent i=new Intent(this,second.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }
}

