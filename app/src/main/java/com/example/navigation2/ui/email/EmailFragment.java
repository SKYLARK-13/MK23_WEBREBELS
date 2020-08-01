package com.example.navigation2.ui.email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigation2.EmailList;
import com.example.navigation2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmailFragment extends Fragment {
private Spinner spinner;
private Button enableupdates,disableupdate;
    private EmailViewModel emailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        emailViewModel =
                ViewModelProviders.of(this).get(EmailViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_email, container, false);
        spinner=root.findViewById(R.id.spinner1);
        enableupdates=root.findViewById(R.id.enable);
        disableupdate=root.findViewById(R.id.disable);
        final ArrayList<String> emaillist=new ArrayList<>();
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, emaillist);
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
enableupdates.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(mail!=null){
        String maillist=spinner.getSelectedItem().toString();
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Email");
        String key=databaseReference1.child(maillist).push().getKey();
        databaseReference1.child(maillist).child(key).setValue(mail);
        Toast.makeText(root.getContext(),"Succefully enabled",Toast.LENGTH_SHORT).show();
    }}
});
disableupdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String emailtxt=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String maillist=spinner.getSelectedItem().toString();
        final DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Email").child(maillist);
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Email");
        databaseReference1.child(maillist).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k=1;
                for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                    if(emailtxt.equals(childsnapshot.getValue())){
                        k=0;
                        String key1= childsnapshot.getKey();
                        databaseReference2.child(key1).removeValue();

                        Toast.makeText(root.getContext(),"Deleted succesfully",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(k==1){
                    Toast.makeText(root.getContext(),"Email not available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
});
        return root;
    }
}