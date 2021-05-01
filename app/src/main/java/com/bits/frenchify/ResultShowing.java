package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultShowing extends AppCompatActivity {

    TextView resultTv,howsScore;
    Button backToAssessment;
    ImageView trophyImg;

    int correctAns;
    int emoji;
    boolean guestBro=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_showing);
        correctAns=getIntent().getIntExtra("score",4);
        backToAssessment=findViewById(R.id.backToAssessment);
        resultTv=findViewById(R.id.resultTv);
        howsScore=findViewById(R.id.howsTheScore);
        trophyImg=findViewById(R.id.trophy_img);
        resultTv.setText("Your Score is "+correctAns+"/10");

        if(correctAns<4){


            howsScore.setText("Bad Score!!"+getEmoji(0x2639));
            howsScore.setTextColor(Color.parseColor("#FF0000"));


        }
        else if(correctAns>=4 && correctAns<=7){

            howsScore.setText("Average Score"+getEmoji(0x1F636));
            howsScore.setTextColor(Color.parseColor("#FFC107"));

        }
        else if(correctAns>7){
            howsScore.setText("Good Score!"+getEmoji(0x1F603));
            howsScore.setTextColor(Color.parseColor("#4CAF50"));


        }


    }
    public void backToAssessment(View view){
        Intent intent = new Intent(ResultShowing.this,CategoriesOfAssessment.class);
        intent.putExtra("guestBro",guestBro);
        //intent.putExtra("UserName",etFirstName.getText().toString());
        startActivity(intent);
        finish();

    }


    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }
}