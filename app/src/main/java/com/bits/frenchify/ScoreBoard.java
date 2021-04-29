package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    static int s;
    String[] categories;
    String[] score;
    String[] date;

    ListView list;
    ArrayList<String> date1=new ArrayList<String>();
    ArrayList<String> score1=new ArrayList<String>();
    ArrayList<String> category1=new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getCurrentUser().getUid();
        firestore=FirebaseFirestore.getInstance();
        path="/userScore/Score/"+uid;



        Toast.makeText(this, findSizeofCollection(path)+" Document", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, +"", Toast.LENGTH_SHORT).show();






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
                        Toast.makeText(ScoreBoard.this, documentLength+"", Toast.LENGTH_SHORT).show();

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






                }
                else
                {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}