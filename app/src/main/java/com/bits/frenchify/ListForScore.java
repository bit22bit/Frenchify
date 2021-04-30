package com.bits.frenchify;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

public class ListForScore extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> category;
    private final ArrayList<String> score;
    private final ArrayList<String> date;

    public ListForScore(Activity context, ArrayList<String> category,ArrayList<String> score, ArrayList<String> date) {
        super(context, R.layout.list_for_score,category);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.category =category;
        this.score=score;
        this.date=date;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_for_score, null,true);

        TextView categoryText = (TextView) rowView.findViewById(R.id.WCategory_score);
        TextView scoreText = (TextView) rowView.findViewById(R.id.score_score);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView dateText = (TextView) rowView.findViewById(R.id.date_score);

        categoryText.setText(category.get(position));
        scoreText.setText(score.get(position));
        dateText.setText(date.get(position));

        return rowView;

    };
}
