package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.navigation2.ui.home.HomeViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private Listadminadapter listadapter;
    private List<Listitem> listitems;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private androidx.appcompat.widget.SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        searchview=(androidx.appcompat.widget.SearchView)findViewById(R.id.searchnotice);

        searchview.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        listitems=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recylerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeActivity.this));

        listadapter=new Listadminadapter(listitems,NoticeActivity.this);


        databaseReference= FirebaseDatabase.getInstance().getReference("File");


        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listitems.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()){
                    Listitem listitem=listsnapshot.getValue(Listitem.class);
                    String date=listitem.getDate2();
                    String tittle=listitem.getTittle();
                    String link=listitem.getLink1();
                    String sector=listitem.getDepartment1();
                    String circular=listitem.getCircular1();
                    DataEncryption dataEncryption=new DataEncryption();
                    try {
                        date=dataEncryption.Dencrypt(date);
                        tittle=dataEncryption.Dencrypt(tittle);
                        link=dataEncryption.Dencrypt(link);
                        sector=dataEncryption.Dencrypt(sector);
                        circular=dataEncryption.Dencrypt(circular);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Listitem listitem1=new Listitem(tittle,date,link,sector,circular);
                    listitems.add(listitem1);

                }
                listadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(listadapter);
    }

    private void filter(String s) {
        ArrayList<Listitem> filterlist =new ArrayList<>();

        for (Listitem item:listitems){
            if(item.getTittle().toLowerCase().contains(s.toLowerCase()) || item.getCircular1().toLowerCase().contains(s.toLowerCase()) || item.getDate2().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(item);}
        }
        listadapter.filteredList(filterlist);
    }


}

