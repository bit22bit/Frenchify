package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.perfmark.Tag;

public class ScoreBoard extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String uid;
    private int documentLengthOkay;
    String path;
    static int documentLength=0;

    String[] categories;
    String[] score;
    String[] date;
    Button loadResult;
    Toolbar toolbar;
    boolean guestBro=false;
    int c;

    ListView list;
    ArrayList<String> date1=new ArrayList<String>();
    ArrayList<String> score1=new ArrayList<String>();
    ArrayList<String> category1=new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        loadResult=findViewById(R.id.resultLoader);
        c=0;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getCurrentUser().getUid();
        firestore=FirebaseFirestore.getInstance();
//        guestBro=getIntent().getExtras().getBoolean("guestBro");
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Click on your result");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreBoard.this,LearningOrAssessment.class);
                intent.putExtra("guestBro",guestBro);
                //intent.putExtra("UserName",etFirstName.getText().toString());
                startActivity(intent);
                finish();
            }
        });



        
        path="/userScore/Score/"+uid;



        //Toast.makeText(this, setLength(findSizeofCollection(path))+"", Toast.LENGTH_SHORT).show();

        loadResult.setOnClickListener(view->{
           c++;


            Toast.makeText(this, findSizeofCollection(path)+"", Toast.LENGTH_SHORT).show();

            for(int i=1;i<=findSizeofCollection(path);i++){


                categories=new String[findSizeofCollection(path)+1];
                score=new String[findSizeofCollection(path)+1];
                date=new String[findSizeofCollection(path)+1];
                readFireStore(path,i+"");
                ListForScore adapter = new ListForScore(this, category1, score1, date1);
                list = (ListView) findViewById(R.id.list_score);
                list.setAdapter(adapter);

               


            }
        });





    }




    public int findSizeofCollection(String path){

        firestore.collection(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count=0;

                            for (DocumentSnapshot document : task.getResult()) {
                                count++;

                            }
                            documentLength =count;



                            //Toast.makeText(Learning.this, count+" Documents", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ScoreBoard.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(ScoreBoard.this, documentLength+"", Toast.LENGTH_SHORT).show();

                    }
                });
        return documentLength;
    }



    public void readFireStore(String path,String document)
    {
        DocumentReference docRef = firestore.collection(path).document(document);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists())
                {

                        category1.add("Category :"+doc.get("Category")+"");
                           score1.add("Score    :"+doc.get("Score")+"");
                            date1.add("Date     :"+doc.get("Date")+"");



                }
                else
                {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
        Intent intent = new Intent(ScoreBoard.this,LearningOrAssessment.class);
        intent.putExtra("guestBro",guestBro);
        //intent.putExtra("UserName",etFirstName.getText().toString());
        startActivity(intent);
        finish();


    }


}