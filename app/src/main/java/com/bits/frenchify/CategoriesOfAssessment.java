package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesOfAssessment extends AppCompatActivity {

    ListView list;
    Toolbar toolbar;
    Intent intent;
    boolean guestBro=false;

    String[] maintitle ={
            "WeekDays",
            "Months",
            "Greeting",
            "General Words",
            "Numbers",
            "Color",
            "Sentences",
            "Random"
    };

    String[] subtitle ={
            " This is a Quiz of WeekDays",
            " This is a Quiz of Months",
            " This is a Quiz of learn Greeting",
            " This is a Quiz of learn General words",
            " This is a Quiz of learn Numbers",
            " This is a Quiz of learn Color",
            " This is a Quiz of learn Sentences",
            " This is Quiz of Random Question from any category"
    };

    Integer[] imgid={
            R.drawable.week_list_color,
            R.drawable.months_list_color,
            R.drawable.greeting_list_color,
            R.drawable.word_list_color,
            R.drawable.number_list_color,
            R.drawable.color_list_color,
            R.drawable.sentences_list_color,
            R.drawable.random_list

    };
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CategoriesOfAssessment.this,LearningOrAssessment.class);
        intent.putExtra("guestBro",guestBro);
        //intent.putExtra("UserName",etFirstName.getText().toString());
        startActivity(intent);
        finish();


    }

    public void sendCategories(String categories){
        intent= new Intent(getApplicationContext(),Quiz.class);
        intent.putExtra("category",categories);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catgories_of_assessment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories of Assessment");
        guestBro=getIntent().getExtras().getBoolean("guestBro");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesOfAssessment.this,LearningOrAssessment.class);
                intent.putExtra("guestBro",guestBro);
                //intent.putExtra("UserName",etFirstName.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        MyListView adapter=new MyListView(this, maintitle, subtitle,imgid);
        list=(ListView)findViewById(R.id.list_ass);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {

                    sendCategories("WeekDay");
                    Toast.makeText(CategoriesOfAssessment.this, "WeekDay", Toast.LENGTH_SHORT).show();

                }

                else if(position == 1) {
                    sendCategories("Months");
                    Toast.makeText(CategoriesOfAssessment.this, "Months", Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {
                    sendCategories("Greetings");
                    Toast.makeText(CategoriesOfAssessment.this, "Greeting", Toast.LENGTH_SHORT).show();
                }
                else if(position == 3) {

                    sendCategories("Words");
                    Toast.makeText(CategoriesOfAssessment.this, "Words", Toast.LENGTH_SHORT).show();
//
                }
                else if(position == 4) {

                    sendCategories("Numbers");
                    Toast.makeText(CategoriesOfAssessment.this, "Numbers", Toast.LENGTH_SHORT).show();
                }
                else if(position == 5) {

                    sendCategories("Color");
                }
                else if(position == 6) {

                    sendCategories("Sentences");
                    Toast.makeText(CategoriesOfAssessment.this, "Sentence", Toast.LENGTH_SHORT).show();
                }
                else if(position == 7) {


                    Toast.makeText(CategoriesOfAssessment.this, "Not Ready Yet..", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}