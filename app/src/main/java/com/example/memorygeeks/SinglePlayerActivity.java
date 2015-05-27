package com.example.memorygeeks;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.memorygeeks.MyActivity;
import com.example.memorygeeks.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePlayerActivity extends ActionBarActivity {
    /*
    Batun, pločica, ploćica, gumb, button, tile svi znače isto u komentarima/kodu.
    --
    Pardon na mješanju engleskog i hrvatskog.
    Ako popijem još jedan red-bull biti će i starogrčkog.
    --
    Također pardon na mješanju naming konvencija između firstSecond i first_second,
    http://xkcd.com/1513
    */

    //Broj pločica
    //Promjeniti na broj pločica u Activity-u
    //Broj pločica bi trebao biti natuknut u imenu Activity-a (npr. SomethingSomethingActivity8)
    //Mora biti paran broj.
    public static final Integer numberOfTiles = 4;
    //Button definitions
    ImageButton button0;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;

    //...
    //Provjera "otvorenosti" pločice
    //Promjeniti u boolean maybe?
    //Otvorena => 1
    //Zatvorena => 0
    Integer[] checkField = new Integer[numberOfTiles];
    //Tip(sličica) pločice
    Integer[] tileType = new Integer[numberOfTiles];
    //Polje gumbova za pristup iz stranih metoda
    ImageButton[] button_field = new ImageButton[numberOfTiles];
    //Linkovi na sličice za pločice
    //Eventualno spičkati ovo u polje naknadno radi automatizacije
    //Na ove prve dodati dovoljno pločica za Activity (Broj Activity-a kroz 2)
    Integer tile1 = R.drawable.test;
    Integer tile2 = R.drawable.test2;
    //Donje dvije ne mijenjati
    Integer found = R.drawable.found;
    Integer nopen = R.drawable.bg;

    boolean[] isFound = new boolean[numberOfTiles];
    //true => PlayerOne
    ///false => PlayerTwo
    boolean turn = true;
    Integer scorePlayerOne = 0;
    Integer scorePlayerTwo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_player);

        //Pridrućivanje listeners batunima
        //Dodati listenere za sve gore definirane batune
        button0= (ImageButton)findViewById(R.id.imageButton0);
        button0.setOnClickListener(imgButton0Handler0);

        button1= (ImageButton)findViewById(R.id.imageButton1);
        button1.setOnClickListener(imgButton0Handler1);

        button2= (ImageButton)findViewById(R.id.imageButton2);
        button2.setOnClickListener(imgButton0Handler2);

        button3= (ImageButton)findViewById(R.id.imageButton3);
        button3.setOnClickListener(imgButton0Handler3);

        //setting buttons in array
        //Dodati unose za batune ako ih nema
        button_field[0]=button0;
        button_field[1]=button1;
        button_field[2]=button2;
        button_field[3]=button3;

        //setting tile types
        //Također dodati dovoljno tipova batuna
        //Po dva trebaju biti jednaka
        //Ovo če se automatizirati kako bi mogao raditi random
        tileType[0] = 1;
        tileType[1] = 0;
        tileType[2] = 1;
        tileType[3] = 0;

        //Inicijalizacija checkFielda
        for(int i = 0; i<numberOfTiles; i++){
            checkField[i]=0;
        }
        for(int i = 0; i<numberOfTiles; i++){
            isFound[i]=false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
        Nadalje počinju pisane metode.
        Lasciate Ogni Speranza Voi Chi Entrate.
     */

    //Pronalaženje otvorene pločice
    public Integer openButton(Integer buttID){
        for(int i = 0; i<numberOfTiles; i++){
            if(checkField[i]==1 && i!=buttID){
                return i;
            }
        }
        return -1; //ERR
    }

    //Provjera je li koja pločica otvorena
    public boolean anyOpen(){
        Integer sum=0;
        for(int i = 0; i<numberOfTiles; i++){
            sum += checkField[i];
        }
        if(sum>1){
            return true;
        }
        return false;
    }

    //Definicija listenera
    //Po jedan listener za svaki gumb kao i vlastita metoda

    View.OnClickListener imgButton0Handler0 = new View.OnClickListener() {

        public void onClick(View v) {
            //Promjeni identifier na primjereni (npr. 1)
            int identifier=0;
            if(isFound[identifier]==true)   return;
            //Postavi pločicu na otvorenu
            checkField[identifier]=1;
            int otherOpenButton;
            //Promjeni button_ u primjereni (npr. button1)
            //Postavi background na zadani
            button0.setBackgroundResource(tile2);
            Log.d("SinglePlayerActivity", "startingchck"); //DEBUG
            //Provjeri je li koji otvoren
            //Jeste
            if(anyOpen()){
                //Nađi drugi otvoreni
                otherOpenButton=openButton(identifier);
                //Jesu li isti?
                //Jesu
                if(tileType[identifier]==tileType[otherOpenButton]){
                    //Promjeni prikaz na blank
                    button_field[identifier].setBackgroundResource(found);
                    button_field[otherOpenButton].setBackgroundResource(found);
                    isFound[identifier] = true;
                    isFound[otherOpenButton] = true;
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                //Nisu
                else{
                    //Promjeni prikaz na početni
                    button_field[identifier].setBackgroundResource(nopen);
                    button_field[otherOpenButton].setBackgroundResource(nopen);
                    //Postavi ig kao zatvorene
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                turnPlay(isFound[identifier]);
            }
            //Nije
            //Ne radi ništa pusti ga otvorenog
            else    return;

        }
    };
    //Integer stat=0;
    View.OnClickListener imgButton0Handler1 = new View.OnClickListener() {

        public void onClick(View v) {
            //Promjeni identifier na primjereni (npr. 1)
            int identifier=1;
            if(isFound[identifier]==true)   return;
            //Postavi pločicu na otvorenu
            checkField[identifier]=1;
            int otherOpenButton;
            //Promjeni button_ u primjereni (npr. button1)
            //Postavi background na zadani
            button1.setBackgroundResource(tile1);
            Log.d("SinglePlayerActivity", "startingchck"); //DEBUG
            //Provjeri je li koji otvoren
            //Jeste
            if(anyOpen()){
                //Nađi drugi otvoreni
                otherOpenButton=openButton(identifier);
                //Jesu li isti?
                //Jesu
                if(tileType[identifier]==tileType[otherOpenButton]){
                    //Promjeni prikaz na blank
                    button_field[identifier].setBackgroundResource(found);
                    button_field[otherOpenButton].setBackgroundResource(found);
                    isFound[identifier] = true;
                    isFound[otherOpenButton] = true;
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                //Nisu
                else{
                    //Promjeni prikaz na početni
                    button_field[identifier].setBackgroundResource(nopen);
                    button_field[otherOpenButton].setBackgroundResource(nopen);
                    //Postavi ig kao zatvorene
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                turnPlay(isFound[identifier]);
            }
            //Nije
            //Ne radi ništa pusti ga otvorenog
            else    return;
        }
    };

    View.OnClickListener imgButton0Handler2 = new View.OnClickListener() {

        public void onClick(View v) {
            //Promjeni identifier na primjereni (npr. 1)
            int identifier=2;
            if(isFound[identifier]==true)   return;
            //Postavi pločicu na otvorenu
            checkField[identifier]=1;
            int otherOpenButton;
            //Promjeni button_ u primjereni (npr. button1)
            //Postavi background na zadani
            button2.setBackgroundResource(tile2);
            Log.d("SinglePlayerActivity", "startingchck"); //DEBUG
            //Provjeri je li koji otvoren
            //Jeste
            if(anyOpen()){
                //Nađi drugi otvoreni
                otherOpenButton=openButton(identifier);
                //Jesu li isti?
                //Jesu
                if(tileType[identifier]==tileType[otherOpenButton]){
                    //Promjeni prikaz na blank
                    button_field[identifier].setBackgroundResource(found);
                    button_field[otherOpenButton].setBackgroundResource(found);
                    isFound[identifier] = true;
                    isFound[otherOpenButton] = true;
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                //Nisu
                else{
                    //Promjeni prikaz na početni
                    button_field[identifier].setBackgroundResource(nopen);
                    button_field[otherOpenButton].setBackgroundResource(nopen);
                    //Postavi ig kao zatvorene
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                turnPlay(isFound[identifier]);
            }
            //Nije
            //Ne radi ništa pusti ga otvorenog
            else    return;
        }
    };

    View.OnClickListener imgButton0Handler3 = new View.OnClickListener() {

        public void onClick(View v) {
            //Promjeni identifier na primjereni (npr. 1)
            int identifier=3;
            if(isFound[identifier]==true)   return;
            //Postavi pločicu na otvorenu
            checkField[identifier]=1;
            int otherOpenButton;
            //Promjeni button_ u primjereni (npr. button1)
            //Postavi background na zadani
            button3.setBackgroundResource(tile1);
            Log.d("SinglePlayerActivity", "startingchck"); //DEBUG
            //Provjeri je li koji otvoren
            //Jeste
            if(anyOpen()){
                //Nađi drugi otvoreni
                otherOpenButton=openButton(identifier);
                //Jesu li isti?
                //Jesu
                if(tileType[identifier]==tileType[otherOpenButton]){
                    //Promjeni prikaz na blank
                    button_field[identifier].setBackgroundResource(found);
                    button_field[otherOpenButton].setBackgroundResource(found);
                    isFound[identifier] = true;
                    isFound[otherOpenButton] = true;
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                //Nisu
                else{
                    //Promjeni prikaz na početni
                    button_field[identifier].setBackgroundResource(nopen);
                    button_field[otherOpenButton].setBackgroundResource(nopen);
                    //Postavi ig kao zatvorene
                    checkField[identifier] = 0;
                    checkField[otherOpenButton] = 0;
                }
                turnPlay(isFound[identifier]);
            }
            //Nije
            //Ne radi ništa pusti ga otvorenog
            else    return;
        }

    };
    //TextView score1 = (TextView) findViewById(R.id.scoreOne);
    //TextView score2 = (TextView) findViewById(R.id.scoreTwo);
    public void turnPlay(boolean Hit){
            isDone();
            Log.d("SCORE", "One:" + scorePlayerOne);
            Log.d("SCORE", "Two:" + scorePlayerTwo);
            Log.d("TURN", "" + turn);

            if(Hit){
                if(turn) {
                    scorePlayerOne += 1;
                    if (scorePlayerOne >= numberOfTiles / 2) {
                        isDone();
                    }
                }
                else{
                    scorePlayerTwo += 1;
                    if(scorePlayerOne>= numberOfTiles/2 ){
                        isDone();
                    }
                }

            }
            else    turn = !turn;

            Log.d("SCORE", "One:" + scorePlayerOne);
            Log.d("SCORE", "Two:" + scorePlayerTwo);
            Log.d("TURN", "" + turn);

            //score1.setText(Integer.toString(scorePlayerOne));
            //score2.setText(Integer.toString(scorePlayerTwo));

            if(!turn){
                isDone();
                computerPlay();
            }
            isDone();
    };

    public void computerPlay(){
        isDone();
        //Uhvati dva random broja
        Integer firstChoice=0;
        Integer secondChoice=0;

        List<Integer> possible = new ArrayList<Integer>();

        for(int i = 0; i<numberOfTiles; i++){
            if(!isFound[i]) possible.add(i);
        }
        Integer[] possibleArray = new Integer[ possible.size() ];
        possible.toArray(possibleArray);

        firstChoice = randInt(0, possible.size());
        secondChoice = randInt(0, possible.size());

        while(true){
            if(secondChoice==firstChoice)   secondChoice = randInt(0, possible.size());
            else break;
        }
        //
        Log.d("COMPUTER_PLAY", "PLAYING:" + firstChoice);
        button_field[possibleArray[firstChoice]].performClick();
        Log.d("COMPUTER_PLAY", "PLAYING:" + secondChoice);
        button_field[possibleArray[secondChoice]].performClick();

    };

    public static int randInt(int min, int max) {
        Random rand = new Random();
        Integer randomNum = rand.nextInt((max - min)) + min;

        return randomNum;
    }

    public void isDone(){
        boolean done = true;
        for(int i = 0; i<numberOfTiles; i++){
            done = done && isFound[i];
        }

        if(done){
            Intent newIntent = new Intent(this, MyActivity.class);
            startActivity(newIntent);
        }
    }
}
