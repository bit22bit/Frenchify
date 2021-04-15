package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    String categories;
    String path;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("English/French");

        categories = getIntent().getStringExtra("category");
        path = "/Questions/Catagories/"+categories;
        question_tv = findViewById(R.id.question_tv);
        nextButton = findViewById(R.id.nextQuestionButton);
        ans_tv=findViewById(R.id.answer_tv);
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        i=0;


        nextButton.setOnClickListener(view-> {




             if(categories.equals("Weekdays")) {
                 String week = weekDays[i];
                if (i == 6) {

                    nextButton.setVisibility(View.INVISIBLE);
                }


                    readFireStore(path,week);
                    i++;
                   // Toast.makeText(this, (i)+" Times", Toast.LENGTH_SHORT).show();

             }
             else{

                 //Toast.makeText(this, "No activity has been selected", Toast.LENGTH_SHORT).show();

                 if (categories.equals("Months")){

                     if(i==11){

                         nextButton.setVisibility(View.INVISIBLE);
                     }
                     readFireStore(path,(i+1)+"");
                     i++;
                     //Toast.makeText(this, (i)+" Times", Toast.LENGTH_SHORT).show();

                 }

             }


        });

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

                    question_tv.setText(i+") "+doc.get("question"));
                    ans_tv.setText("Ans: "+doc.get("answer"));


                }
                else
                {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}