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


    public static  int nRows, nCols;
    boolean flag=false;
     public static String [] cells;
    //size of grid
    public static  int GridSize;

    //min numbers of words generated
    public static final int minWords=25;

    public static final Random RANDOM = new Random();
    public static List<String> solution=new ArrayList<>();
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

    TextView GridViewItems0,BackSelectedItem0,t;

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
        Bundle extras = getIntent().getExtras();

        words.clear();
        P00.clear();
        P11.clear();



        Toast.makeText(this, "Selected: " + nCols+" "+nRows, Toast.LENGTH_LONG).show();

        printResult(createWordSearch(read(extras.getString("ID"))));
        Log.d("", "onCreate: "+extras.getString("ID"));


        gridView=findViewById(R.id.g1);
        gridView.setNumColumns(nCols);
        g2=findViewById(R.id.g2);
        ItemsList = new ArrayList<String>(Arrays.asList(cells));
        gridView.setAdapter(new AdapterGrid(this,cells));
        ItemsList = new ArrayList<String>();
        gridView.setBackgroundColor(-271173);

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, words);
        g2.setAdapter(arrayAdapter);


        Log.d("", "onCreate: "+solution.size());

        for(int i=0,k=0;i<solution.size()-3;i+=3){
            P00.add(solution.get(i));
            P11.add(solution.get(i+1));
            words.add(solution.get(i+2));
            Log.d("", "onCreate: "+words.get(k));
            k++;
        }

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
                    colors[0]=new Random().nextInt(50)+200;
                    colors[1]=new Random().nextInt(50)+100;
                    colors[2]=new Random().nextInt(50)+150;
                    GridViewItems0.setBackgroundColor(Color.rgb(colors[0],colors[1],colors[2]));
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

                        BackSelectedItem0.setSelected(false);

                        BackSelectedItem0.setBackgroundColor(colorId);












                    }

                    else {

                        flag=false;
                        DrowLines(p0, p1);

                        words.set(PositionOfWords,"XXXXX");
                        arrayAdapter.notifyDataSetChanged();
                        Log.d("", "onItemClick: ya SEE"+p0+" "+p1);




                        for (int i = 0; i < ps.length; i++) {
                            Log.d("", "array: " + ps[i]);
                            t = (TextView) gridView.getChildAt(ps[i]);






                            t.setSelected(false);
                            t.setBackgroundColor(Color.rgb(colors[0],colors[1],colors[2]));

                            t.setSelected(false);

                        }


                    }
                    }








            }


        });








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
    solution=grid.solution;

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


        }
        if(size%2==1){
            System.out.println(grid.solution.get(size-1));
        }
    Log.d("", "printResult: "+grid.solution.size()/3+" "+P00.size());

    }


    private boolean Check(int p0,int p1) {

       boolean flag1=false;
       String p00=(p0/nRows)+"";
       p00+=(p0%nRows);
        String p11=(p1/nRows)+"";
        p11+=(p1%nRows);
        p0=Integer.parseInt(p00);
        p1=Integer.parseInt(p11);


       for (int i=0;i<P00.size();i++){
           Log.d("", "Check: Word "+ words.get(i) +" "+P00.get(i)+"   "+P11.get(i)+" "+p0+" "+p1);


          if((Integer.parseInt(P00.get(i))==p0 &&  Integer.parseInt(P11.get(i))==p1)||(Integer.parseInt(P00.get(i))==p1 &&  Integer.parseInt(P11.get(i))==p0)){

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

        RowMax=(max/nRows);
        ColMax=(max%nRows);

        RowMin=(min/nRows);
        ColMin=(min%nRows);





            if(RowMax==RowMin)
            {
                defcols=ColMax-ColMin;
                ps=new int[defcols+1];
                for (int co=ColMin,i=0;i<ps.length;i++){
                    ps[i]=(RowMax*nRows)+co;

                    co++;

                }
            }
            if (ColMax==ColMin){
                defrows=RowMax-RowMin;
                ps=new int[defrows+1];
                for (int i=0;i<ps.length;i++){
                    ps[i]=(RowMin*nRows)+(i*nRows)+ColMax;
                }

            }

            if(RowMax!=RowMin && ColMax!=ColMin){
                if (ColMax>ColMin){
                    defrows=ColMax-ColMin;
                    ps=new int[defrows+1];
                    for (int i=0;i<ps.length;i++){
                        ps[i]=(RowMin*nRows)+(i*nRows)+ColMin+i;
                    }


                }

                if (ColMax<ColMin){
                    defrows=ColMin-ColMax;
                    ps=new int[defrows+1];
                    for (int i=0;i<ps.length;i++){
                        ps[i]=(RowMax*nRows)-(i*nRows)+ColMax+i;
                    }


                }





            }





}


}

