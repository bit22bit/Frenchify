package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Learning extends AppCompatActivity {

    private TextView question_tv, ans_tv;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    private int i;
    Button nextButton;
    String categories,path;

    Toolbar toolbar;
     int documentLength;


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

            showData(findSizeofCollection(path)-1);



        });

    }

    public int findSizeofCollection(String path){

        fireStore.collection(path)
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
                            Toast.makeText(Learning.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return documentLength;
    }
    public void showData(int j){

        if(i==j){

            nextButton.setVisibility(View.INVISIBLE);
        }
        readFireStore(path,(i+1)+"");
        i++;

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


//                     if(i==11){
//
//                         nextButton.setVisibility(View.INVISIBLE);
//                     }

//                     readFireStore(path,(i+1)+"");
//                     i++;

//Toast.makeText(this, (i)+" Times", Toast.LENGTH_SHORT).show();
//             if(categories.equals("WeekDay")) {
//
//
//                 showData(findSizeofCollection(path)-1);
//
//
//             }

//             else if (categories.equals("Months")){
//
//                 showData(11);
//             }
//             else if (categories.equals("Colors")){
//
//                 showData(9);
//             }
//             else if (categories.equals("Greetings")){
//
//                 showData(17);
//             }
//             else if (categories.equals("Numbers")){
//
//                 showData(19);
//             }
//             else if (categories.equals("Sentences")){
//
//                 showData(20);
//             }
//             else if (categories.equals("Words")) {
//
//                 showData(11);
//             }