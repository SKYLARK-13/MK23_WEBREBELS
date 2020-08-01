package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmailList extends AppCompatActivity {
private Spinner spinner;
private Button btnu,btnr,btna;
private EditText emailtxt,listname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        btnu=findViewById(R.id.updateemail);
        btnr=findViewById(R.id.removeemail);
        emailtxt=findViewById(R.id.upemail);
        listname=findViewById(R.id.listname);
        btna=findViewById(R.id.addlist);
        spinner=findViewById(R.id.emailspinner);
        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listname1=listname.getText().toString();
                DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference("Email");
                String key=databaseReference3.child(listname1).push().getKey();
                databaseReference3.child(listname1).child(key).setValue("aklalwani100@gmail.com");
               DatabaseReference databaseReference4=FirebaseDatabase.getInstance().getReference("Email").child("Emaillist");
               String key1=databaseReference4.push().getKey();
                databaseReference4.child(key1).setValue(listname1);

                Toast.makeText(EmailList.this,"List succesfully created",Toast.LENGTH_SHORT).show();
            }
        });

        final ArrayList<String> emaillist=new ArrayList<>();
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(EmailList.this, android.R.layout.simple_list_item_1, emaillist);
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
btnu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String mail=emailtxt.getText().toString();
        if(mail==null){
            emailtxt.setError("please enter email");
            emailtxt.requestFocus();
        }
        else{
            String maillist=spinner.getSelectedItem().toString();
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Email");
           String key=databaseReference1.child(maillist).push().getKey();
           databaseReference1.child(maillist).child(key).setValue(mail);
           Toast.makeText(EmailList.this,"added succesfully",Toast.LENGTH_SHORT).show();
        }
    }
});
btnr.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String mail=emailtxt.getText().toString();
        if(mail==null){
            emailtxt.setError("please enter email");
            emailtxt.requestFocus();
        }
        else{

            String maillist=spinner.getSelectedItem().toString();

           final DatabaseReference databaseReference6=FirebaseDatabase.getInstance().getReference("Email");
            databaseReference6.child(maillist).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int k=1;
                    for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                        if(emailtxt.equals(childsnapshot.getValue())){
                            k=0;
                           String key1= childsnapshot.getKey();
                            databaseReference6.child(key1).removeValue();

                            Toast.makeText(EmailList.this,"Deleted succesfully",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(k==1){
                        Toast.makeText(EmailList.this,"Email not available",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
});

    }
}
