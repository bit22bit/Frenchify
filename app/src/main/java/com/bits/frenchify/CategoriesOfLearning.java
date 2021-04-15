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
            "In this Module we learn WeekDays",
            "In this Module we learn Months",
            "In this Module we learn Greeting",
            "In this Module we learn General words",
            "In this Module we learn Numbers",
            "In this Module we learn Color",
            "In this Module we learn Sentences"
    };

    Integer[] imgid={
            R.drawable.week_list,
            R.drawable.month_list,
            R.drawable.greet_list,
            R.drawable.word_list,
            R.drawable.numbers_list,
            R.drawable.color_list,
            R.drawable.sentences_list,

    };

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
                    //code specific to first list item

                    //Toast.makeText(getApplicationContext(),"WeekDays Learning (:",Toast.LENGTH_SHORT).show();
                    intent= new Intent(getApplicationContext(),Learning.class);
                    intent.putExtra("category","Weekdays");
                    startActivity(intent);

                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    //Toast.makeText(getApplicationContext(),"Months Learning (:",Toast.LENGTH_SHORT).show();
                    intent= new Intent(getApplicationContext(),Learning.class);
                    intent.putExtra("category","Months");
                    startActivity(intent);
                }

                else if(position == 2) {

                    Toast.makeText(getApplicationContext(), "Greeting Learning (:", Toast.LENGTH_SHORT).show();
                    //intent = new Intent(getApplicationContext(), Learning.class);
                    //intent.putExtra("cat_months", "Weekdays");
                    //startActivity(intent);
                }
                else if(position == 3) {

                    Toast.makeText(getApplicationContext(),"General Words Learning (:",Toast.LENGTH_SHORT).show();
//                    intent= new Intent(getApplicationContext(),Learning.class);
//                    intent.putExtra("cat_months","Weekdays");
//                    startActivity(intent);
                }
                else if(position == 4) {

                    Toast.makeText(getApplicationContext(),"Numbers Learning (:",Toast.LENGTH_SHORT).show();
                }
                else if(position == 5) {

                    Toast.makeText(getApplicationContext(),"colors Learning (:",Toast.LENGTH_SHORT).show();
                }
                else if(position == 6) {

                    Toast.makeText(getApplicationContext(),"Sentences Learning (:",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}