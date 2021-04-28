package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URISyntaxException;

public class LearningOrAssessment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Button gotoLearningAcivity,gotoAssessmentActivity;
    Toolbar toolbar;
    boolean guestBro;
    FirebaseUser user;
    FirebaseFirestore fireStore;
    TextView usernameTv;
    FirebaseAuth firebaseAuth;

    NavigationView nav_view;
    DrawerLayout rootLayout;

    ActionBarDrawerToggle toggle;
    Intent intent;





    public void initViews(){

        gotoAssessmentActivity=findViewById(R.id.gotoAssessmentActivity_button);
        gotoLearningAcivity=findViewById(R.id.gotoLearningActivity_button);

        usernameTv = findViewById(R.id.userNameAL_tv);
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        nav_view = findViewById(R.id.nav_view);
        nav_view.setItemIconTintList(null);
        toolbar = findViewById(R.id.toolbar_for_nav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        rootLayout = findViewById(R.id.rootLayoutLA);
         toggle= new ActionBarDrawerToggle(this, rootLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        rootLayout.addDrawerListener(toggle);

        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);




    }

    public void gotoLearning(View view){

        //Toast.makeText(this, "goto Learning Activity", Toast.LENGTH_SHORT).show();
       // startActivity(new Intent(this,CategoriesOfLearning.class));
        Intent intent=new Intent(this,CategoriesOfLearning.class);
        intent.putExtra("guestBro",guestBro);
        startActivity(intent);


    }
    public void gotoAssessment(View view){

        //Toast.makeText(this, "goto Assessment Activity", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,CategoriesOfAssessment.class);
        intent.putExtra("guestBro",guestBro);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (rootLayout.isDrawerOpen(GravityCompat.START)) {
            rootLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    public void readFireStore()
    {
        DocumentReference docRef = fireStore.collection("users").document(user.getUid());

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists())
                {
                    Log.d("DashboardFragment",doc.getData().toString());

                    usernameTv.setText("Welcome "+doc.get("UserName") + " !");


                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_or_assessment);
        initViews();
        guestBro=getIntent().getExtras().getBoolean("guestBro");
        if(guestBro==true){

            gotoAssessmentActivity.setVisibility(View.INVISIBLE);
            usernameTv.setText("You are in Guest Mode");


        }
        else{

            gotoAssessmentActivity.setVisibility(View.VISIBLE);
            readFireStore();

        }



    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_profile:

                if(guestBro){
                    Toast.makeText(this, "You can not in Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent = new Intent(LearningOrAssessment.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
                
                
                
            case R.id.nav_scorecard:

                if(guestBro){
                    Toast.makeText(this, "You can not do it in Guest Mode", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(this, "open firebase scoreCard", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_mail:

                Toast.makeText(this, "For feedback please email us on phd257@gmail.com, Thanks!", Toast.LENGTH_SHORT).show();
                
                break;
                
            case R.id.nav_Materials:
                
                if(guestBro){

                    Toast.makeText(this, "You can't do it in guest mode!", Toast.LENGTH_SHORT).show();
                    
                }
                else {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1VyJbKoRR8yHxOGomDSC0f7fZgrbWUlXH/view?usp=sharing")));

                    Toast.makeText(this, "You will be able to download soon", Toast.LENGTH_SHORT).show();
                }

                break;
                
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(LearningOrAssessment.this, Login.class);
                startActivity(intent);
                finish();
                break;
                
                
        }
        rootLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}