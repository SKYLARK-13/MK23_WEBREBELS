package com.example.navigation2.ui.notification;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigation2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private Button bankingOnbtn,bankingoffbtn,insuranceOnbtn,insuranceOffbtn,pensionOnbtn,pensionOffbtn,commonOnbtn,commonOffbtn;
    View root;

    private NotificationViewModel notificationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        root = inflater.inflate(R.layout.notification_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_notification);

        bankingOnbtn=root.findViewById(R.id.banking_on);
        bankingoffbtn=root.findViewById(R.id.banking_of);
        insuranceOnbtn=root.findViewById(R.id.insurance_on);
        insuranceOffbtn=root.findViewById(R.id.insurance_of);
        pensionOnbtn=root.findViewById(R.id.pension_on);
        pensionOffbtn=root.findViewById(R.id.pension_of);
        commonOnbtn=root.findViewById(R.id.common_on);
        commonOffbtn=root.findViewById(R.id.common_of);

        bankingOnbtn.setOnClickListener(this);
        bankingoffbtn.setOnClickListener(this);
        pensionOnbtn.setOnClickListener(this);
        pensionOffbtn.setOnClickListener(this);
        insuranceOnbtn.setOnClickListener(this);
        insuranceOffbtn.setOnClickListener(this);
        commonOnbtn.setOnClickListener(this);
        commonOffbtn.setOnClickListener(this);



        notificationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.banking_on:
                //
                FirebaseMessaging.getInstance().subscribeToTopic("banking")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="on";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });

                break;
            case R.id.banking_of:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("banking")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="off";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.insurance_on:
                //
                FirebaseMessaging.getInstance().subscribeToTopic("insurance")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="on";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.insurance_of:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("insurance")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="off";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.pension_on:
                //
                FirebaseMessaging.getInstance().subscribeToTopic("pension")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="on";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.pension_of:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("pension")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="off";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.common_on:
                //
                FirebaseMessaging.getInstance().subscribeToTopic("common")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="on";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            case R.id.common_of:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("common")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="off";
                                if(!task.isSuccessful())
                                    msg="failed";
                                Toast.makeText(root.getContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        });
                break;

        }
    }
}
