package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Quiz extends AppCompatActivity {
    TextView questionTv,timerTv,scoreTv;
    Button optionA,optionB,optionC,optionD,next;
    Toolbar toolbar;
    FirebaseFirestore fireStore;
    String categories;
    String path;
    LinearLayout ll;
    int i;
    int thisQestion;
    int documentLength;
    String rightAns;
    int correctAns;
    String currentTime;



    FirebaseUser user;
    String uid;
    FirebaseAuth firebaseAuth;

    int okay;
    int k;

    DataSnapshot snapshot;



    ArrayList<Integer> optionRandom;
    ArrayList<Integer> questionRandom;



//    public int findSizeofCollection(String path){
//
//        fireStore.collection(path)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            int count=0;
//
//                            for (DocumentSnapshot document : task.getResult()) {
//                                count++;
//
//                            }
//                            documentLength =count;
//                            //Toast.makeText(Learning.this, count+" Documents", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(Quiz.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//        return documentLength;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getCurrentUser().getUid();
        fireStore=FirebaseFirestore.getInstance();
        k=findSizeofCollection();



        //extra
        optionRandom= new ArrayList<Integer>();
        questionRandom = new ArrayList<Integer>();




        toolbar=findViewById(R.id.toolbar);
        ll=findViewById(R.id.mainLL_quiz);
        toolbar.setTitle("Quiz has been started");

        questionTv = findViewById(R.id.question_ass);

        scoreTv= findViewById(R.id.scoreTv);

        optionA= findViewById(R.id.option1);
        optionB=findViewById(R.id.option2);
        optionC=findViewById(R.id.option3);
        optionD=findViewById(R.id.option4);
        ///next=findViewById(R.id.nextButton_ass);


        categories = getIntent().getStringExtra("category");

        path = "/Quiz/Quizque/"+categories;












        randomNumberGenerator(15,questionRandom);
        correctAns=0;
        i=1;
        thisQestion=1;
        readFireStore(path,questionRandom.get(i)+"");



        optionA.setOnClickListener(view->{

            quizProcess(optionA);

        });
        optionB.setOnClickListener(view->{

            quizProcess(optionB);

        });
        optionC.setOnClickListener(view->{

            quizProcess(optionC);

        });
        optionD.setOnClickListener(view->{

           quizProcess(optionD);

        });

        //store the Data


    }
    public int findSizeofCollection(){

        fireStore.collection("/userScore/Score/"+uid)
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
                            Toast.makeText(Quiz.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return documentLength;
    }

    public ArrayList<Integer> randomNumberGenerator(int x,ArrayList<Integer> randomArray){

        while (randomArray.size() < x) {

            Random rand=new Random();

            int random = rand.nextInt(x - 1 + 1) + 1;
            if (!randomArray.contains(random)) {
                randomArray.add(random);
            }

        }
        return  randomArray;
    }



    public void quizProcess(Button button) {


        i++;

        if (button.getText().toString().equals(rightAns)) {

            //Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            correctAns++;
        } else {
            //Toast.makeText(this, button.getText().toString() + " " + rightAns, Toast.LENGTH_SHORT).show();
        }
        scoreTv.setText((i - 1) + " /10");
        thisQestion++;
        readFireStore(path, questionRandom.get(i) + "");


        if (i == 11) {

            scoreTv.setText("kdjn");
            ll.setVisibility(View.INVISIBLE);
            //timerTv.setText(correctAns + " ");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            currentTime = sdf.format(new Date());


            ScoreSender scoreSender= new ScoreSender(currentTime,categories,correctAns);
            Map<String , Object> user = new HashMap<>();
            user.put("Category",categories);
            user.put("Score",correctAns+"");
            user.put("Date",currentTime);

            DocumentReference documentReference=fireStore.collection("/userScore/Score/"+uid).document("1");

            fireStore.collection("/userScore/Score/"+uid).document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (!task.getResult().exists()){

                        Toast.makeText(Quiz.this, "in else", Toast.LENGTH_SHORT).show();
                        documentReference.set(user).addOnSuccessListener(aVoid -> {


                            Toast.makeText(Quiz.this, "UserProfile has been created ", Toast.LENGTH_SHORT).show();
                        });





                    }else{

                        int s= findSizeofCollection();
                        DocumentReference doci=fireStore.collection("/userScore/Score/"+uid).document((((++s)))+"");
                        doci.set(user).addOnCompleteListener(kVoid->{

                            Toast.makeText(Quiz.this, "new data has been updated and size"+findSizeofCollection(), Toast.LENGTH_SHORT).show();
                        });



                    }

                    Intent intent=new Intent(Quiz.this,ResultShowing.class);
                    intent.putExtra("score",correctAns);
                    startActivity(intent);
                    finish();
                }
            });







//            documentReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if(snapshot.exists()){
//
//                        documentReference=documentReference.child(uid);
//
//                        documentReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                ScoreSender scoreSender= new ScoreSender(currentTime,categories,correctAns);
//
//                                if(snapshot.exists()){
//
//                                resultDataCounter = (int) snapshot.getChildrenCount();
//
//                                String docResult= (resultDataCounter+1)+"";
//
//                                documentReference.child(docResult).setValue(scoreSender);}
//                                else{
//                                    documentReference.child(uid).child("1").setValue(scoreSender);
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//
//
//                    }
//                    else{
//
//
//                        Log.i("error","error");
//                        Toast.makeText(Quiz.this, "error", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            //documentReference.child(uid).setValue(scoreSender);
//
//        }
        }
    }






    public void readFireStore(String path,String document)
    {
        DocumentReference docRef = fireStore.collection(path).document(document);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists())
                {


                    randomNumberGenerator(4,optionRandom);


                    questionTv.setText(""+doc.get("question"));
                    optionA.setText(""+doc.get(optionRandom.get(0)+""));
                    optionB.setText(""+doc.get(optionRandom.get(1)+""));
                    optionC.setText(""+doc.get(optionRandom.get(2)+""));
                    optionD.setText(""+doc.get(optionRandom.get(3)+""));
                    rightAns=doc.get("answer").toString();

                }
                else
                {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void checkAns(String path,String document,Button option)
//    {
//        DocumentReference docRef = fireStore.collection(path).document(document);
//
//        docRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful())
//            {
//                DocumentSnapshot doc = task.getResult();
//
//                if (doc.exists())
//                {
//
////                    question_tv.setText(i+") "+doc.get("question"));
////                    ans_tv.setText("Ans: "+doc.get("answer"));
//                    if(doc.get("answer").toString().equals(option.getText().toString())){
//                        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
//                        correctQuestion++;
//
//                    }
//                    else{
//                        Toast.makeText(this, doc.get("answer").toString()+" = "+option.getText().toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//
//                }
//                else
//                {
//                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}