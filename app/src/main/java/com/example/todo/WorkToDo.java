package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.todo.R.layout.activity_work_to_do;

public class WorkToDo extends AppCompatActivity {

    final List<String> list = new ArrayList<>();
    int [] backgroundColors;
    int [] textColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_work_to_do);

        final ListView listView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();

        int maxItems = 100;
        backgroundColors = new int[maxItems];
        textColors = new int[maxItems];

        for (int  i= 0; i < maxItems; i++){
            int currentColor = i%2;
            if( currentColor== 0){
                backgroundColors[i] = Color.parseColor("#9debff");
                textColors[i] = Color.parseColor("#6200EE");
            }else  {
                backgroundColors[i] = Color.parseColor("#6200EE");
                textColors[i] = Color.parseColor("#ecfcff");
            }
        }

        readInfo();

        adapter.setData(list, backgroundColors,textColors);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(WorkToDo.this)
                        .setTitle("Delete Completed Task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.setData(list,backgroundColors,textColors);
                                saveInfo();

                            }
                        })

                        .setNegativeButton("No", null)
                        .create();
                dialog.show();


            }
        });

        final Button newTaskButton = findViewById(R.id.newTaskButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskInput = new EditText(WorkToDo.this);
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(WorkToDo.this)
                        .setTitle(" NEW TASK")
                        .setMessage("Add tasks:")
                        .setView(taskInput)
                        .setPositiveButton(" Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskInput.getText().toString());
                                adapter.setData(list,backgroundColors,textColors);
                                saveInfo();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });

        final Button deleteAllTasksButton = findViewById(R.id.deleteAllTasksButton);

        deleteAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this is to verify that the user actually wants to delete all the tasks
                AlertDialog dialog = new AlertDialog.Builder(WorkToDo.this)
                        .setTitle("Are you sure you want to clear all tasks?")
                        .setPositiveButton("Clear all Tasks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.clear();
                                adapter.setData(list,backgroundColors,textColors);
                                saveInfo();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }

    private void saveInfo(){
        try {
            File file = new File(this.getFilesDir(),"Saved");

            FileOutputStream fOut = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));

            for ( int i = 0; i < list.size(); i++){
                bw.write(list.get(i));
                bw.newLine();
            }

            bw.close();
            fOut.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readInfo() {
        File file = new File(this.getFilesDir(), "Saved");
        if (!file.exists()) {
            return;
        }

        try {

            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line!= null){
                list.add(line);
                line = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TextAdapter extends BaseAdapter {

        List<String> list = new ArrayList<>();

        int[] backgroundColors;
        int[] textColors;


        void setData(List<String> mList , int [] mBackgroundColors, int[] mTextColors) {
            list.clear();
            list.addAll(mList);
            backgroundColors = new int [list.size()];
            textColors = new int[list.size()];
            for (int i = 0;  i < list.size(); i++) {
                backgroundColors[i]= mBackgroundColors[i];
                textColors[i]= mTextColors[i];
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        WorkToDo.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item, parent, false);

            }

            final TextView textView = convertView.findViewById(R.id.task);


            textView.setBackgroundColor(backgroundColors[position]);
            textView.setTextColor(textColors[position]);
            textView.setText(list.get(position));

                return convertView;

            }
        }
    }

    // 1:48.47 tells us how to add 3 colors if you want to take a look at that later.