package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public class AdminReportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter listadapter;
    private List<ChatSend> listitems;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private Button btn1;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report2);
        Intent intent;
        intent=getIntent();
        final String filename=intent.getStringExtra("filename");
        final int type=intent.getIntExtra("type",1);
        btn1=findViewById(R.id.button_chatbox_send);
        editText=findViewById(R.id.edittext_chatbox);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString().trim();
                String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String  name="";
                if(type==1){
                    name="User";
                }
                else{
                    name="Admin";
                }
                DataEncryption dataEncryption=new DataEncryption();
                try {
                    message=dataEncryption.Encrypt(message);
                    mail=dataEncryption.Encrypt(mail);
                    name=dataEncryption.Encrypt(name);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }


                ChatSend chatSend=new ChatSend(name,mail,message);
                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Reports");


                String key=databaseReference2.child(filename).push().getKey();
                databaseReference2.child(filename).child(key).setValue(chatSend);
                Toast.makeText(AdminReportActivity.this,"Sended Succesfully",Toast.LENGTH_SHORT).show();
            }
        });
        listitems=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.reyclerview_message_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminReportActivity.this));
        listadapter=new ChatAdapter(listitems,AdminReportActivity.this);
        recyclerView.setAdapter(listadapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("Reports");

        databaseReference=databaseReference.child(filename);


        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listitems.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()){
                    ChatSend listitem=listsnapshot.getValue(ChatSend.class);
                    DataEncryption dataEncryption=new DataEncryption();
                    String msg=listitem.getMsage();
                    String mail=listitem.getMail();
                    String name=listitem.getName();
                    try {
                        msg=dataEncryption.Dencrypt(msg);
                        mail=dataEncryption.Dencrypt(mail);
                        name=dataEncryption.Dencrypt(name);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    ChatSend chatSend=new ChatSend(name,mail,msg);
                    listitems.add(chatSend);


                }
                listadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        recyclerView.setAdapter(listadapter);

    }
}
