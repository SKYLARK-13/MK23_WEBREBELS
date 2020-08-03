package com.example.navigation2.ui.account;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigation2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {
 private Button deleteacc;
 private EditText emailacc;
FirebaseAuth firebaseAuth;
    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        final View root = inflater.inflate(R.layout.account_fragment, container, false);
        deleteacc=root.findViewById(R.id.delete);
        emailacc=root.findViewById(R.id.deletaccount);
        firebaseAuth=FirebaseAuth.getInstance();
        deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseUser user=firebaseAuth.getCurrentUser();
               if(user!=null){
                 String t=  user.getEmail();
                 if(t.equals(emailacc.getText().toString())){
                    FirebaseUser user1=FirebaseAuth.getInstance().getCurrentUser();
                            user1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(root.getContext(),"Scusesfully deleted",Toast.LENGTH_SHORT).show();
                                 Intent i=new Intent(root.getContext(),com.example.navigation2.second.class);
                                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(i);
                             }
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(root.getContext(),"Please try again",Toast.LENGTH_SHORT).show();
                         }
                     });


                 }
                 else{
                     emailacc.setError("please enter correct details");
                     emailacc.requestFocus();
                     Toast.makeText(root.getContext(),"please enter your email correctly",Toast.LENGTH_SHORT).show();
                 }

               }
            }
        });
        return root;
    }

}
