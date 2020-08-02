package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewedbyActivity extends AppCompatActivity {
    Intent intent;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private ViewbyAdapter viewbyAdapter;
    private List<SeenbyData> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedby);
        intent=getIntent();
        String filename=intent.getStringExtra("filename");
        listitems=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_seenby);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewedbyActivity.this));
        databaseReference= FirebaseDatabase.getInstance().getReference("SEENBY");
        databaseReference=databaseReference.child(filename);

        viewbyAdapter=new ViewbyAdapter(listitems,ViewedbyActivity.this);
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                listitems.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()){
                    SeenbyData listitem=listsnapshot.getValue(SeenbyData.class);
                    listitems.add(listitem);

                }
                viewbyAdapter.notifyDataSetChanged();

            }
            else {listitems.clear();
                    SeenbyData listitem=new SeenbyData("Nothing to Show","FAS");
            listitems.add(listitem);
                    viewbyAdapter.notifyDataSetChanged();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(viewbyAdapter);
    }

    }
