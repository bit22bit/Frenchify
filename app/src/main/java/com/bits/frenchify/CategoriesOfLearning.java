package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesOfLearning extends AppCompatActivity {

    ListView list;
    Toolbar toolbar;
    Intent intent;

    String[] maintitle ={
            "WeekDays",
            "Months",
            "Greeting",
            "General Words",
            "Numbers",
            "Color",
            "Sentences"
    };

    String[] subtitle ={
            " In this Module we learn WeekDays",
            " In this Module we learn Months",
            " In this Module we learn Greeting",
            " In this Module we learn General words",
            " In this Module we learn Numbers",
            " In this Module we learn Color",
            " In this Module we learn Sentences"
    };

    Integer[] imgid={
            R.drawable.week_list_color,
            R.drawable.months_list_color,
            R.drawable.greeting_list_color,
            R.drawable.word_list_color,
            R.drawable.number_list_color,
            R.drawable.color_list_color,
            R.drawable.sentences_list_color,

    };

    public void sendCategories(String categories){
        intent= new Intent(getApplicationContext(),Learning.class);
        intent.putExtra("category",categories);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_of_learning);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories of Learning Modules");

        MyListView adapter=new MyListView(this, maintitle, subtitle,imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    sendCategories("WeekDay");
                }

                else if(position == 1) {
                 sendCategories("Months");
                }

                else if(position == 2) {
                    sendCategories("Greetings");
                }
                else if(position == 3) {

                    sendCategories("Words");
//
                }
                else if(position == 4) {

                    sendCategories("Numbers");
                }
                else if(position == 5) {

                    sendCategories("Colors");
                }
                else if(position == 6) {

                    sendCategories("Sentences");
                }

            }
        });



    }
}