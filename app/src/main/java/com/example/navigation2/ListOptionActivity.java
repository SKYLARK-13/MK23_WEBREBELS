package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListOptionActivity extends AppCompatActivity {
Intent intent;
private Button btn1,btn2,sharebtn, downloadbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_option);
        intent=getIntent();
        String tittle2 = intent.getStringExtra("Tittle");
        String departmet = intent.getStringExtra("department");
        String circularno3 = intent.getStringExtra("circularno");
       final String link=intent.getStringExtra("link");
        final String filename=tittle2+departmet+circularno3;
        btn1=findViewById(R.id.ReadNotice);
        sharebtn=findViewById(R.id.share_btn);
        btn2=findViewById(R.id.report);
        downloadbtn = findViewById(R.id.download_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeenbyData seenbyData=new SeenbyData();

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser!=null){
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);

                            SeenbyData seenbyData=new SeenbyData(user.getEmail(),user.getNameuser());
                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("SEENBY");
                            String key4=reference1.child(filename).push().getKey();

                            reference1.child(filename).child(key4).setValue(seenbyData);



                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });}
                 Intent intent=new Intent();

                 intent.setType(Intent.ACTION_VIEW);
                 intent.setDataAndType(Uri.parse(link),"pdf");
                 startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListOptionActivity.this,AdminReportActivity.class);
                intent.putExtra("filename",filename);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Hey there is a good initiative taken by DFS, Check it out by given Url : "+link);
                startActivity(intent);

            }
        });

        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                request.setTitle("Download");
                request.setDescription("Downloading file");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis()+".pdf");

                //download service and enqueue
                DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ListOptionActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
