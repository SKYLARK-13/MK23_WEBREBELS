package com.example.navigation2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.navigation2.ui.feedback.FeedbackFragment;
import com.example.navigation2.ui.finance.FinanceFragment;
import com.example.navigation2.ui.banking.BankingFragment;
import com.example.navigation2.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private BottomNavigationView mbottomNavigationView;

    public static final String channel_id="webrebels";              //notification
    public static final String CHANNEL_NAME="webrebels";            //purpose
    public static final String CHANNEL_DESC="giving notification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hview=navigationView.getHeaderView(0);
        final TextView email=(TextView)hview.findViewById(R.id.textViewemail);
        final  TextView name=(TextView)hview.findViewById(R.id.textviewname);
        final ImageView imageView=(ImageView)hview.findViewById(R.id.imageViewnav);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                email.setText(user.getEmail());
                name.setText(user.getNameuser());
                if(user.getProfile()!=null){
                    imageView.setImageURI(Uri.parse(user.getProfile()));}

                else{
                    imageView.setImageDrawable(getDrawable(R.drawable.login1));

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_email, R.id.nav_settings, R.id.nav_share ,R.id.nav_faq, R.id.nav_account,R.id.nav_logout, R.id.nav_notification, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();

        mbottomNavigationView = (BottomNavigationView)findViewById(R.id.main_nav);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bank, R.id.nav_finance, R.id.nav_pension)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(mbottomNavigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()==null){
            Intent intent=new Intent(MainActivity.this,com.example.navigation2.second.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}