package com.example.navigation2.ui.banking;

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
import com.example.navigation2.ui.account.AccountViewModel;
import com.example.navigation2.ui.pension.PensionViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BankingFragment extends Fragment {
    private PensionViewModel pensionViewModel;
    private androidx.appcompat.widget.SearchView searchViewbanking;
    private RecyclerView recyclerView;
    private Listadapter listadapter2;
    private List<Listitem> listitems2;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    private BankingViewModel bankingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bankingViewModel =
                ViewModelProviders.of(this).get(BankingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_banking, container, false);
        searchViewbanking=root.findViewById(R.id.searchbanking);
        recyclerView=root.findViewById(R.id.recylerviewbanking);
        searchViewbanking.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
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
        listitems2=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        listadapter2=new Listadapter(listitems2,root.getContext());
        databaseReference= FirebaseDatabase.getInstance().getReference("File");


        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listitems2.clear();
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

                        if (d.equals("Banking sector")) {
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
                            listitems2.add(listitem1);
                        }

                    }
                }
                listadapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(listadapter2);

        return root;
    }
    private void filter(String s) {
        ArrayList<Listitem> filterlist =new ArrayList<>();
        for (Listitem item:listitems2){
            if(item.getTittle().toLowerCase().contains(s.toLowerCase()) || item.getCircular1().toLowerCase().contains(s.toLowerCase()) || item.getDate2().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(item);}
        }
        listadapter2.filteredList(filterlist);
    }
}