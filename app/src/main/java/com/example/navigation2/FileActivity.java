package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Calendar;

public class FileActivity extends AppCompatActivity {
    private Button choosefile, choosedate, uploadfile;
    private TextView filename1, date1;
    private EditText circularno2, tittle1;
    public int PICK = 12;
    private ProgressBar progressbar1;
    private FirebaseDatabase firebaseDatabase;
    Spinner myspinner;
    Uri Fileuri;
    private DatePickerDialog.OnDateSetListener mDateSetListner;

    private DatabaseReference mdatabasereference;
    private StorageReference mStorageRef;
    String Filepath;
    Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        choosefile = (Button) findViewById(R.id.choosefile);
        progressbar1 = (ProgressBar) findViewById(R.id.progressBar1);
        filename1 = (TextView) findViewById(R.id.filename1);
        date1 = (TextView) findViewById(R.id.date1);
        choosedate = (Button) findViewById(R.id.choosedate);
        circularno2 = (EditText) findViewById(R.id.circularno1);
        uploadfile = (Button) findViewById(R.id.uploadfile);
        tittle1 = (EditText) findViewById(R.id.tittle1);

        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar1.setVisibility(ProgressBar.VISIBLE);
                String tittle2, date3, departmet, circularno3;
                tittle2 = tittle1.getText().toString();
                date3 = date1.getText().toString();
                departmet = myspinner.getSelectedItem().toString();
                circularno3 = circularno2.getText().toString();
                if (tittle2 != null && date3 != null && departmet != null && circularno3 != null && Filepath != null) {
                    uplodfile1(Fileuri);
                } else { progressbar1.setVisibility(ProgressBar.INVISIBLE);

                    Toast.makeText(FileActivity.this, "please complete all fields", Toast.LENGTH_LONG).show();
                }


            }
        });
        myspinner = (Spinner) findViewById(R.id.spinner1);
        ArrayList<String> mylist = new ArrayList<String>();
        mylist.add("Pension sector");
        mylist.add("Insurance sector");
        mylist.add("Banking sector");
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(FileActivity.this, android.R.layout.simple_list_item_1, mylist);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);
        choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(FileActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                date1.setText(date);
            }
        };

        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent = new Intent(Intent.ACTION_GET_CONTENT);
                myintent.setType("*/*");

                startActivityForResult(myintent, 12);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String path = data.getData().getLastPathSegment();
            Filepath = data.getData().getPath();
            filename1.setText(path);
            Fileuri = data.getData();


        } else {
            filename1.setError("Please try again");
            filename1.requestFocus();
            return;
        }

    }

    public void uplodfile1(Uri uri) {
        DataEncryption dataEncryption=new DataEncryption();

        String tittle2 = tittle1.getText().toString().trim();
       String departmet = myspinner.getSelectedItem().toString().trim();
        String circularno3 = circularno2.getText().toString().trim();
        final String filename=tittle2+departmet+circularno3;
        try {
            tittle2=dataEncryption.Encrypt(tittle2);
            departmet=dataEncryption.Encrypt(departmet);
            circularno3=dataEncryption.Encrypt(circularno3);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }






        mStorageRef = FirebaseStorage.getInstance().getReference("Notice");
        mStorageRef = mStorageRef.child(filename);

        final UploadTask uploadTask = mStorageRef.putFile(Fileuri);
        final Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()) {
                    progressbar1.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(FileActivity.this, "Please try again ", Toast.LENGTH_SHORT).show();

                    throw task.getException();
                }
                return mStorageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String k = task.getResult().toString();
                    String tittle2, date3, departmet, circularno3;
                    tittle2 = tittle1.getText().toString().trim();
                    date3 = date1.getText().toString();
                    departmet = myspinner.getSelectedItem().toString().trim();
                    circularno3 = circularno2.getText().toString().trim();
                    String filename1=tittle2+departmet+circularno3;
                    DataEncryption dataEncryption=new DataEncryption();
                    try {
                        tittle2=dataEncryption.Encrypt(tittle2);
                        date3=dataEncryption.Encrypt(date3);
                        departmet=dataEncryption.Encrypt(departmet);
                        circularno3=dataEncryption.Encrypt(circularno3);
                        k=dataEncryption.Encrypt(k);
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }





                    Listitem listitem = new Listitem(tittle2, date3, k, departmet, circularno3);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference("File");
                    reference.child(filename1).setValue(listitem);
                    DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference("Reports").child(filename1);
                   String key2= databaseReference3.push().getKey();
                   String name="app",mail="app",msage="Type Your Report Here";
                    try {
                        name=dataEncryption.Encrypt(name);
                        mail=dataEncryption.Encrypt(mail);
                        msage=dataEncryption.Encrypt(msage);
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    ChatSend chatSend=new ChatSend(name,mail,msage);
                   databaseReference3.child(key2).setValue(chatSend);

                    DatabaseReference reference1=firebaseDatabase.getReference("SEENBY");
                    String key4=reference1.child(filename1).push().getKey();
                    SeenbyData seenbyData=new SeenbyData("not available","Admin");
                    reference1.child(filename1).child(key4).setValue(seenbyData);
                    progressbar1.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(FileActivity.this, "Uploded succesfully", Toast.LENGTH_SHORT).show();


                }
                progressbar1.setVisibility(ProgressBar.INVISIBLE);
            }
        });
        progressbar1.setVisibility(ProgressBar.INVISIBLE);
    }
}















