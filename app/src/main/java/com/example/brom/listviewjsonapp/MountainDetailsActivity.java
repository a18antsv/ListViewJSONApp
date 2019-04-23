package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MountainDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.MOUNTAIN_ID, 0);
        String name = intent.getStringExtra(MainActivity.MOUNTAIN_NAME);
        String location = intent.getStringExtra(MainActivity.MOUNTAIN_LOCATION);
        int height = intent.getIntExtra(MainActivity.MOUNTAIN_HEIGHT, 0);
        String imgURL = intent.getStringExtra(MainActivity.MOUNTAIN_IMAGE);
        String articleURL = intent.getStringExtra(MainActivity.MOUNTAIN_ARTICLE);

        ImageView imgImageView = (ImageView) findViewById(R.id.mountain_img);
        TextView nameTextView = (TextView) findViewById(R.id.mountain_name);
        TextView heightTextView = (TextView) findViewById(R.id.mountain_height);
        TextView locationTextView = (TextView) findViewById(R.id.mountain_location);
        TextView urlTextView = (TextView) findViewById(R.id.mountain_url);
        TextView idTextView = (TextView) findViewById(R.id.mountain_id);

        new DownloadImage(imgImageView).execute(imgURL);
        nameTextView.setText(name);
        heightTextView.setText("Height: " + Integer.toString(height) + "m");
        locationTextView.setText("Location: " + location);
        urlTextView.setText("Article: " + articleURL);
        idTextView.setText("ID: " + id);
    }
}
