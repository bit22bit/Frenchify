package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LearningOrAssessment extends AppCompatActivity {

    Button logout;
    Button gotoLearningAcivity;
    Button gotoAssessmentActivity;



    public void initViews(){
        logout = findViewById(R.id.logOut);
        gotoAssessmentActivity=findViewById(R.id.gotoAssessmentActivity_button);
        gotoLearningAcivity=findViewById(R.id.gotoLearningActivity_button);


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_or_assessment);
        initViews();
    }
}