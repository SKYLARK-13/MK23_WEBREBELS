package com.example.navigation2.ui.pension;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class PensionFragment extends Fragment {

    private PensionViewModel pensionViewModel;
    private androidx.appcompat.widget.SearchView searchViewpension;
    private RecyclerView recyclerView;
    private Listadapter listadapter1;
    private List<Listitem> listitems1;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       pensionViewModel =
                ViewModelProviders.of(this).get(PensionViewModel.class);
       final View root = inflater.inflate(R.layout.fragment_pension, container, false);
       searchViewpension=root.findViewById(R.id.searchpension);
       recyclerView=root.findViewById(R.id.recylerviewpension);
       searchViewpension.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
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
        listitems1=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        listadapter1=new Listadapter(listitems1,root.getContext());
        databaseReference= FirebaseDatabase.getInstance().getReference("File");


        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listitems1.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()) {
                    Listitem listitem = listsnapshot.getValue(Listitem.class);
                    if (listitem.getDepartment1() != null) {
                        String d=listitem.getDepartment1();
                        DataEncryption dataEncryption=new DataEncryption();
                        try {
                            d=dataEncryption.Dencrypt(d);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        if (d.equals("Pension sector")) {
                            String date=listitem.getDate2();
                            String tittle=listitem.getTittle();
                            String link=listitem.getLink1();
                            String sector=listitem.getDepartment1();
                            String circular=listitem.getCircular1();
                            DataEncryption dataEncryption1=new DataEncryption();
                            try {
                                date=dataEncryption1.Dencrypt(date);
                                tittle=dataEncryption1.Dencrypt(tittle);
                                link=dataEncryption1.Dencrypt(link);
                                sector=dataEncryption1.Dencrypt(sector);
                                circular=dataEncryption1.Dencrypt(circular);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Listitem listitem1=new Listitem(tittle,date,link,sector,circular);
                            listitems1.add(listitem1);
                        }

                    }
                }
                listadapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(listadapter1);
        return root;
    }
    private void filter(String s) {
        ArrayList<Listitem> filterlist =new ArrayList<>();
        for (Listitem item:listitems1){
            if(item.getTittle().toLowerCase().contains(s.toLowerCase()) || item.getCircular1().toLowerCase().contains(s.toLowerCase()) || item.getDate2().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(item);}
        }
        listadapter1.filteredList(filterlist);
    }
}
