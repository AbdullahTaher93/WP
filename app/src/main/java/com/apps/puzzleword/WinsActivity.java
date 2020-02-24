package com.apps.puzzleword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WinsActivity extends AppCompatActivity {
    public static String WordType="";
    public static int Newlevel=0;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wins);
        start=findViewById(R.id.start);
        if(Newlevel<13){
            start.setText("New Level");
        MainActivity.nRows=Newlevel;
        MainActivity.nCols=Newlevel;
        MainActivity.cells=new String[(Newlevel)*(Newlevel)];
        MainActivity.GridSize=(Newlevel)*(Newlevel);}else {
            Toast.makeText(this,"You win all of level, to start again Click Start again.",Toast.LENGTH_LONG).show();
            start.setText("Start Again");
            MainActivity.nRows=7;
            MainActivity.nCols=7;
            MainActivity.cells=new String[(7)*(7)];
            MainActivity.GridSize=(7)*(7);
        }
    }
    public void newlevel(View view){

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("ID",WordType);
        startActivity(intent);

    }
    public void home(View view){
        Intent intent=new Intent(this,HomePage.class);
        startActivity(intent);
    }
}
