package com.example.navigation2.ui.finance;

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

import com.example.navigation2.Listadapter;
import com.example.navigation2.Listitem;
import com.example.navigation2.R;
import com.example.navigation2.ui.pension.PensionViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FinanceFragment extends Fragment {
    private PensionViewModel pensionViewModel;
    private androidx.appcompat.widget.SearchView searchViewfinance;
    private RecyclerView recyclerView3;
    private Listadapter listadapter3;
    private List<Listitem> listitems3;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    private FinanceViewModel financeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        financeViewModel =
                ViewModelProviders.of(this).get(FinanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finance, container, false);
        searchViewfinance=root.findViewById(R.id.searchfinance);
        recyclerView3=root.findViewById(R.id.recylerviewfinance);
        searchViewfinance.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
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
        listitems3=new ArrayList<>();
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(root.getContext()));

        listadapter3=new Listadapter(listitems3,root.getContext());
        databaseReference= FirebaseDatabase.getInstance().getReference("File");


        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listitems3.clear();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren()){
                    Listitem listitem=listsnapshot.getValue(Listitem.class);
                    if(listitem.getDepartment1()!=null){
                    if(listitem.getDepartment1().equals("Insurance sector")){
                        listitems3.add(listitem);}

                }}
                listadapter3.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView3.setAdapter(listadapter3);


        return root;
    }
    private void filter(String s) {
        ArrayList<Listitem> filterlist =new ArrayList<>();
        for (Listitem item:listitems3){
            if(item.getTittle().toLowerCase().contains(s.toLowerCase()) || item.getCircular1().toLowerCase().contains(s.toLowerCase()) || item.getDate2().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(item);}
        }
        listadapter3.filteredList(filterlist);
    }
}
