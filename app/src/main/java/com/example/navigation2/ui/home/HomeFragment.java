package com.example.navigation2.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation2.DataEncryption;
import com.example.navigation2.Listadapter;
import com.example.navigation2.Listitem;
import com.example.navigation2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
   private RecyclerView recyclerView;
   private Listadapter listadapter;
    private List<Listitem> listitems;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private androidx.appcompat.widget.SearchView searchview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        searchview=(androidx.appcompat.widget.SearchView)root.findViewById(R.id.searchhome);
        searchview.clearFocus();


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

        recyclerView=(RecyclerView)root.findViewById(R.id.recylerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

      listadapter=new Listadapter(listitems,root.getContext());


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
        return root;
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