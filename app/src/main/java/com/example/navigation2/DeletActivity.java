package com.example.navigation2;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.security.InvalidKeyException;
import java.util.ArrayList;


public class DeletActivity extends AppCompatActivity {
private Intent myintent;
private Button btn1,btn2,btn3,btn4;
private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet);
        spinner=findViewById(R.id.spinner2);
        btn1=findViewById(R.id.deletNotice);
        btn2=findViewById(R.id.send);
        btn3=findViewById(R.id.report);
        myintent=getIntent();
          final String circular,date,tittle,department,filename,link;
          link=myintent.getStringExtra("link");
          circular=myintent.getStringExtra("circularno");
          date=myintent.getStringExtra("date");
          tittle=myintent.getStringExtra("Tittle");
          department=myintent.getStringExtra("department");
          btn4=findViewById(R.id.Viewedby);
        filename=tittle+department+circular;

        final ArrayList<String> emaillist=new ArrayList<>();
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(DeletActivity.this, android.R.layout.simple_list_item_1, emaillist);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myadapter);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Email");
        databaseReference=databaseReference.child("Emaillist");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emaillist.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()){
                    emaillist.add(listsnapshot.getValue().toString());
                }
                myadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeletActivity.this,AdminReportActivity.class);



                intent.putExtra("filename",filename);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("File");

                        databaseReference.child(filename).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    System.out.println(filename);
                                    Toast.makeText(DeletActivity.this,"Deleted Succesfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(DeletActivity.this,NoticeActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DeletActivity.this,"please try again",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String maillist=spinner.getSelectedItem().toString();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Email");
                databaseReference.child(maillist).addListenerForSingleValueEvent(new ValueEventListener() {
                    String url1="https://us-central1-fass1-7615b.cloudfunctions.net/sendEmail?dest=";
                    String k="aklalwani100@gmail.com";
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                            k=k+","+childsnapshot.getValue();

                        }
                        url1=url1+k+"&msg="+"<h1>New Update</h1><br><pr>Click below link to see</pr><br><a href="+link+">link</a>";
                        RequestQueue queue= Volley.newRequestQueue(DeletActivity.this);
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, url1,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(DeletActivity.this,response,Toast.LENGTH_LONG).show();

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DeletActivity.this,"error occured..." ,Toast.LENGTH_LONG).show();

                            }
                        });
                        queue.add(stringRequest);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeletActivity.this,ViewedbyActivity.class);
                intent.putExtra("filename",filename);
                startActivity(intent);

            }
        });

    }
}
