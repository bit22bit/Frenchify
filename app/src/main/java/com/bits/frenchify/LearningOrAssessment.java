package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URISyntaxException;

public class LearningOrAssessment extends AppCompatActivity {

    Button logout;
    Button gotoLearningAcivity;
    Button gotoAssessmentActivity;
    boolean guestBro;
    FirebaseUser user;
    FirebaseFirestore fireStore;
    TextView usernameTv;
    FirebaseAuth firebaseAuth;







    public void initViews(){
        logout = findViewById(R.id.logOut);
        gotoAssessmentActivity=findViewById(R.id.gotoAssessmentActivity_button);
        gotoLearningAcivity=findViewById(R.id.gotoLearningActivity_button);

        usernameTv = findViewById(R.id.userNameAL_tv);
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();




    }
    public void logout(View view){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(LearningOrAssessment.this, Login.class);
        startActivity(intent);
        finish();

    }
    public void gotoLearning(View view){

        Toast.makeText(this, "goto Learning Activity", Toast.LENGTH_SHORT).show();

    }
    public void gotoAssessment(View view){

        Toast.makeText(this, "goto Assessment Activity", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onBackPressed() {
//
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(LearningOrAssessment.this, Login.class);
//        startActivity(intent);
//        finish();
//
//
//    }

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
            usernameTv.setVisibility(View.INVISIBLE);

        }
        else{

            gotoAssessmentActivity.setVisibility(View.VISIBLE);
            readFireStore();

        }


    }



}