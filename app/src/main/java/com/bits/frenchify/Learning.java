package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Learning extends AppCompatActivity {

    private TextView question_tv, ans_tv;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    private int i;
    String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Button nextButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        question_tv = findViewById(R.id.question_tv);
        nextButton = findViewById(R.id.nextQuestionButton);
        ans_tv=findViewById(R.id.answer_tv);
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        i=0;


        nextButton.setOnClickListener(view-> {


            String week = weekDays[i];


            if(i==6){

                nextButton.setVisibility(View.INVISIBLE);
            }



            readFireStore(week);
            Toast.makeText(this, (i)+" Times", Toast.LENGTH_SHORT).show();
            i++;



        });

    }



    public void readFireStore(String document)
    {
        DocumentReference docRef = fireStore.collection("/Questions/Catagories/Weekdays").document(document);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists())
                {

                    question_tv.setText("English : "+doc.get("question"));
                    ans_tv.setText("French : "+doc.get("answer"));


                }
            }
        });
    }
}