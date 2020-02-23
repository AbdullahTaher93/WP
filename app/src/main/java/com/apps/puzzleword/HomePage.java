package com.apps.puzzleword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


//List <String> word=read("");

/*for (int i=0;i<word.size();i++){
    Log.d("", "onCreate: "+word.get(i));
}*/


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.sp);


        // Spinner click listener


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Level 1: 7 X 7");
        categories.add("Level 2: 8 X 8");
        categories.add("Level 3: 9 X 9");
        categories.add("Level 4: 10 X 10");
        categories.add("Level 5: 11 X 11");
        categories.add("Level 6: 12 X 12");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Spinner click listener
        spinner.setOnItemSelectedListener( HomePage.this);
    }


    public void Animals(View view){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("ID","animals.txt");
        startActivity(intent);

    }
    public void Verbs(View view){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("ID","verbs.txt");
        startActivity(intent);

    }

    public void things(View view){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("ID","things.txt");
        startActivity(intent);
    }
    public void vegetables(View view){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("ID","vegetables.txt");
        startActivity(intent);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        MainActivity.nRows=position+7;
        MainActivity.nCols=position+7;
        MainActivity.cells=new String[(position+7)*(position+7)];
        MainActivity.GridSize=(position+7)*(position+7);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
