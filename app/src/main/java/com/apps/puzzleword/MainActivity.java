package com.apps.puzzleword;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    //numbers of rows and columns


    public static final int nRows=10, nCols=10;
    boolean flag=false;
     public static String [] cells=new String[nCols*nRows];
    //size of grid
    public static final int GridSize=nRows*nCols;

    //min numbers of words generated
    public static final int minWords=25;

    public static final Random RANDOM = new Random();

    public static class grid{
      int numAttemptes;
        char [][] cells =new char[nRows][nCols];
        List<String> solution=new ArrayList<>();
        //8 directions to generate




    }

    public static final int[][] DIRS={
            {1,0},{0,1},{1,1},{1,-1},{-1,0},{0,-1},{-1,-1},{-1,1}

    };

    GridView gridView;
    List<String> ItemsList;
    String selectedItem0;
    String selectedItem1;
    TextView GridViewItems0,BackSelectedItem0,t;
    TextView GridViewItems1,BackSelectedItem1;
    int backposition0 = -1;
    int backposition1 = -1;
    int p0,p1;

    public static int ps[];

    public static List<String> P00=new ArrayList<>();
    public static List<String> P11=new ArrayList<>();
    public static List<String> words=new ArrayList<>();
    public static int PositionOfWords=0;
    public int[] colors={251,220,187};
    int colorId=0;
    ColorDrawable listDrawableBackground;
    GridView g2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        printResult(createWordSearch(read("u.txt")));



        gridView=findViewById(R.id.g1);
        gridView.setNumColumns(nCols);
        g2=findViewById(R.id.g2);
        ItemsList = new ArrayList<String>(Arrays.asList(cells));
        gridView.setAdapter(new AdapterGrid(this,cells));
        ItemsList = new ArrayList<String>();

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, words);
        g2.setAdapter(arrayAdapter);




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                if(flag==false)
                {
                    flag=true;
                    p0=position;



                    selectedItem0 = parent.getItemAtPosition(position).toString();
                    GridViewItems0 = (TextView) view;
                    listDrawableBackground= (ColorDrawable )GridViewItems0.getBackground();
                    colorId = listDrawableBackground.getColor();

                    GridViewItems0.setBackgroundColor(Color.rgb(colors[0]+10,colors[1]+10,colors[2]+10));
                    listDrawableBackground= (ColorDrawable )GridViewItems0.getBackground();

                   // String colorCode = (String)GridViewItems0.getTag();
                    Log.d("", "onItemClick: "+colorId);
                   // GridViewItems0.setBackgroundColor(colorId);

                   // GridViewItems0.setTextColor(Color.parseColor("#fdfcfa"));




                }
                else if (flag==true) {
                    flag = false;
                    p1 = position;


                    if (!Check(p0,p1)) {
                        flag=false;

                        backposition0=p0;
                        backposition1=p1;
                        Toast.makeText(MainActivity.this,"Wrong!",Toast.LENGTH_LONG).show();

                        BackSelectedItem0 = (TextView) gridView.getChildAt(backposition0);
                        BackSelectedItem1 = (TextView) gridView.getChildAt(backposition1);
                        BackSelectedItem0.setSelected(false);

                        BackSelectedItem0.setBackgroundColor(colorId);


                        BackSelectedItem1.setSelected(false);

                        BackSelectedItem1.setBackgroundColor(Color.rgb(251,220,187));











                    }

                    else {

                        flag=false;
                        DrowLines(p0, p1);

                        words.remove(PositionOfWords);
                        arrayAdapter.notifyDataSetChanged();
                        Log.d("", "onItemClick: ya SEE"+p0+" "+p1);
                        colors[0]=new Random().nextInt(255);
                        colors[1]=new Random().nextInt(255);
                        colors[2]=new Random().nextInt(255);


                        for (int i = 0; i < ps.length; i++) {
                            Log.d("", "array: " + ps[i]);
                            t = (TextView) gridView.getChildAt(ps[i]);
                            t.setSelected(false);
                            t.setBackgroundColor(Color.rgb(colors[0],colors[1],colors[2]));

                            t.setSelected(false);

                        }


                    }
                    }



             if ((backposition0 != -1 && backposition1 !=-1 ) && flag==false)
                {
                  /*  BackSelectedItem0 = (TextView) gridView.getChildAt(backposition0);
                    BackSelectedItem1 = (TextView) gridView.getChildAt(backposition1);
                    BackSelectedItem0.setSelected(false);

                    BackSelectedItem0.setBackgroundColor(Color.parseColor("#fbdcbb"));

                    BackSelectedItem0.setTextColor(Color.parseColor("#040404"));

                    BackSelectedItem1.setSelected(false);

                    BackSelectedItem1.setBackgroundColor(Color.parseColor("#fbdcbb"));

                    BackSelectedItem1.setTextColor(Color.parseColor("#040404"));*/


                }






            }


        });




        //readword("u.txt");
        //read();
      /* String te="";
        try {
            InputStream is= getAssets().open("u.txt");
            int size=is.available();
            byte [] buffer=new byte[size];
            is.read(buffer);
            is.close();
            te=new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(te);*/



    }

    public List<String> read(String filename){
        int maxlength= Math.max(nRows,nCols);
        List<String> word=new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;

            while ((mLine = reader.readLine()) != null) {


                String s=mLine.trim().toLowerCase();
                if (s.matches("^[a-z]{3,"+maxlength+"}$")){
                    word.add(s.toUpperCase());
                    Log.d("", "read: "+s.toUpperCase());
                }

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return word;

    }






public static List<String> readword(String filename ){
    int maxlength= Math.max(nRows,nCols);
    List<String> word=new ArrayList<>();
    try(Scanner sc = new Scanner(new FileReader(filename))) {
        while (sc.hasNext()){
            String s=sc.next().trim().toLowerCase();
            if (s.matches("^[a-z]{3,"+maxlength+"}$")){
                word.add(s.toUpperCase());
                System.out.println(s);
            }
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }


    return word;

}

    public static grid createWordSearch(List<String> words){
        grid grid=null;
        int numAttemptes =0;
        //we make 100 attemptes
        while (++numAttemptes<100){
            Collections.shuffle(words);
            grid=new grid();
            int mess=placeMessage(grid,"SSaurel Mobile Apps");
            int target=GridSize-mess;
            int cellsFilled=0;

            for(String word: words){
                cellsFilled+=tryPlaceWord(grid,word);
                if (cellsFilled==target){
                    if(grid.solution.size()>=minWords){
                        grid.numAttemptes=numAttemptes;
                        return grid;

                    }else break;//we full the grid but we have not enough words

                }
            }

        }
        return grid;
    }

public static int placeMessage(grid grid,String msg){
        msg=msg.toUpperCase().replaceAll("[^A-Z]","");
        int messagelength=msg.length();
        if(messagelength>0 && messagelength<GridSize){
            int gapSize=GridSize/messagelength;
            for (int i=0;i<messagelength;i++){
                int pos=i*gapSize*RANDOM.nextInt(gapSize);
               // grid.cells[ pos/nCols ][ pos % nCols]=msg.charAt(i);
                Log.d("", "placeMessage: "+pos/nCols+" "+pos%nCols);
            }
            return messagelength;

        }

        return 0;
}

public  static int tryPlaceWord(grid grid,String word){
     int randDir=RANDOM.nextInt(DIRS.length);
     int randPos=RANDOM.nextInt(GridSize);
     for(int dir=0;dir<DIRS.length;dir++){
         dir=(dir+randDir)%DIRS.length;
         for (int pos=0;pos<GridSize;pos++){
             pos=(pos+randPos) % GridSize;
             int lettersPlaced=tryLocation(grid,word,dir,pos);
             if(lettersPlaced>0)
                 return lettersPlaced;
         }
     }

        return 0;
}

public static int  tryLocation(grid grid,String word, int dir,int pos){
        int r=pos/nCols;
        int c=pos%nCols;
        int len=word.length();
        //we ckech bounds
        if(((      DIRS[dir][0]==1)&&(len+c )>nCols )
                ||(DIRS[dir][0]==-1 && (len-1)>c)
                ||(DIRS[dir][1]==1 && (len+r)>nRows)
                ||(DIRS[dir][1]==-1 && (len-1)>r)

        )
        return 0;

        int overlaps=0;
        int i,cc,rr=0;
        for( i=0,rr=r,cc=c;i<len;i++){
            if (grid.cells[rr][cc]!=0&& grid.cells[rr][cc]!=word.charAt(i))
                return 0;
            cc+=DIRS[dir][0];
            rr+=DIRS[dir][1];

        }
    for( i=0,rr=r,cc=c;i<len;i++){
        if (grid.cells[rr][cc]==word.charAt(i))
            overlaps++;
        else
            grid.cells[rr][cc]=word.charAt(i);

        if (i<len-1){
            cc+=DIRS[dir][0];
            rr+=DIRS[dir][1];

        }

    }
    int lettersPlaced=len-overlaps;
    if (lettersPlaced>0){
        grid.solution.add(r+""+c);
        grid.solution.add(rr+""+cc);
        grid.solution.add(word);

    }

    return lettersPlaced;
}

public static void printResult(grid grid){
        if(grid==null || grid.numAttemptes==0) {
            System.out.println("No grid to display");
            return;
        }
        int size=grid.solution.size();
        System.out.println("Atts: "+ grid.numAttemptes);
        System.out.println("No. of word : "+ size);
        System.out.println("\n        ");
        for(int c=0;c<nCols;c++){
            System.out.print(c+" ");
        }
        System.out.println();
        int k=0;
        for (int r=0;r<nCols;r++){
            System.out.printf("%n%d ",r);
            for (int c=0;c<nCols;c++){
                cells[k]=grid.cells[r][c]+"";
                System.out.printf(" %c ",grid.cells[r][c]);
                k++;
            }
        }


        System.out.println();
        for (int i=0;i<size-2;i+=3){
            System.out.println(grid.solution.get(i+2)+" "+grid.solution.get(i)+" " +grid.solution.get(i+1));
            P00.add(grid.solution.get(i));
            P11.add(grid.solution.get(i+1));
            words.add(grid.solution.get(i+2));
        }
        if(size%2==1){
            System.out.println(grid.solution.get(size-1));
        }

    }


    private boolean Check(int p0,int p1) {

       boolean flag1=false;

       for (int i=0;i<P00.size();i++){


          if(P00.get(i).equalsIgnoreCase(""+p0) &&  P11.get(i).equalsIgnoreCase(""+p1)){

              Toast.makeText(this,"Correct true P1",Toast.LENGTH_LONG).show();
              flag1=true;
              PositionOfWords=i;
              break;
          }
       }

        return flag1;
    }

public void DrowLines(int p0,int p1){

        int RowMax,ColMax,RowMin,ColMin=0;
        int defcols,defrows=0;
        int max=0,min=0;

        if(p0>p1){
            max=p0;
            min=p1;

        }else if(p1>p0){
            max=p1;
            min=p0;
        }

        RowMax=(max/10);
        ColMax=(max%10);

        RowMin=(min/10);
        ColMin=(min%10);





            if(RowMax==RowMin)
            {
                defcols=ColMax-ColMin;
                ps=new int[defcols+1];
                for (int co=ColMin,i=0;i<ps.length;i++){
                    ps[i]=(RowMax*10)+co;

                    co++;

                }
            }
            if (ColMax==ColMin){
                defrows=RowMax-RowMin;
                ps=new int[defrows+1];
                for (int i=0;i<ps.length;i++){
                    ps[i]=(RowMin*10)+(i*10)+ColMax;
                }

            }

            if(RowMax!=RowMin && ColMax!=ColMin){
                if (ColMax>ColMin){
                    defrows=ColMax-ColMin;
                    ps=new int[defrows+1];
                    for (int i=0;i<ps.length;i++){
                        ps[i]=(RowMin*10)+(i*10)+ColMin+i;
                    }


                }

                if (ColMax<ColMin){
                    defrows=ColMin-ColMax;
                    ps=new int[defrows+1];
                    for (int i=0;i<ps.length;i++){
                        ps[i]=(RowMax*10)-(i*10)+ColMax+i;
                    }


                }





            }





}


}
