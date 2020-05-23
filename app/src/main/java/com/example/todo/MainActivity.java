package com.example.todo;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

    }

    public void buttonOnClick(View V) {
        Intent i = new Intent(getApplicationContext(), WorkToDo.class);
        startActivity(i);
    }

    // public void fitnessButtonOnClick(View V) {
    //  Intent i = new Intent(getApplicationContext(), FitnessToDo.class);
    // startActivity(i);
    //  }

    //public void errandsButtonOnClick(View V) {
       // Intent i = new Intent(getApplicationContext(), ErrandsToDo.class);
        //startActivity(i);

        // }

        // public void personalButtonOnClick(View V) {
        //  Intent i = new Intent(getApplicationContext(), PersonalToDo.class);
        //  startActivity(i);
    }


















