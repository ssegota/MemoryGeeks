package com.example.memorygeeks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import java.util.Objects;


public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    // On single player click
    public void openPlayComputer(View view){
        Integer tileNumber= 8;
        EditText editText = (EditText) findViewById(R.id.tile_number);
        String message = editText.getText().toString();
        Log.d("TAGTAGTAG", "message: " + message);
        if(message.equals("test")) {
            Intent intent = new Intent(this, SinglePlayerActivity.class);
            startActivity(intent);
        }
        tileNumber=Integer.parseInt(message);
        Log.d("TAGTAGTAG", "integer message: " + tileNumber);

        if(tileNumber < 8){
            tileNumber = 8;
        }
        else if(tileNumber > 18){
            tileNumber = 18;
        }

        if(tileNumber % 2 != 0){
            tileNumber=tileNumber+1;
        }

        switch (tileNumber){
            case 8:
                Intent intent8 = new Intent(this, SinglePlayerActivity8.class);
                startActivity(intent8);
                break;
            case 10:
                Intent intent10 = new Intent(this, SinglePlayerActivity10.class);
                startActivity(intent10);
                break;

            case 12:
                Intent intent12 = new Intent(this, SinglePlayerActivity12.class);
                startActivity(intent12);
                break;
            case 14:
                Intent intent14 = new Intent(this, SinglePlayerActivity14.class);
                startActivity(intent14);
                break;
            case 16:
                Intent intent16 = new Intent(this, SinglePlayerActivity16.class);
                startActivity(intent16);
                break;
            case 18:
                Intent intent18 = new Intent(this, SinglePlayerActivity18.class);
                startActivity(intent18);
                break;

            default:
                Intent intent_def = new Intent(this, SinglePlayerActivity.class);
                startActivity(intent_def);

        }

        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
    }

    // On multi player click
    public void openPlayPlayer(View view){

        //NAPRAVITI ACTIVITYJE I PROMJENITI IMENA U SWITCHU!!!
        Integer tileNumber= 8;
        EditText editText = (EditText) findViewById(R.id.tile_number);
        String message = editText.getText().toString();
        Log.d("TAGTAGTAG", "message: " + message);
        if(message.equals("test")) {
            Intent intent = new Intent(this, MultiPlayerActivity.class);
            startActivity(intent);
        }
        tileNumber=Integer.parseInt(message);
        Log.d("TAGTAGTAG", "integer message: " + tileNumber);

        if(tileNumber < 8){
            tileNumber = 8;
        }
        else if(tileNumber > 18){
            tileNumber = 18;
        }

        if(tileNumber%2!=0){
            tileNumber=tileNumber+1;
        }

        switch (tileNumber){
            case 8:
                Intent intent8 = new Intent(this, MultiPlayerActivity8.class);
                startActivity(intent8);
                break;
            case 10:
                Intent intent10 = new Intent(this, SinglePlayerActivity10.class);
                startActivity(intent10);
                break;

            case 12:
                Intent intent12 = new Intent(this, SinglePlayerActivity12.class);
                startActivity(intent12);
                break;
            case 14:
                Intent intent14 = new Intent(this, SinglePlayerActivity14.class);
                startActivity(intent14);
                break;
            case 16:
                Intent intent16 = new Intent(this, SinglePlayerActivity16.class);
                startActivity(intent16);
                break;
            case 18:
                Intent intent18 = new Intent(this, SinglePlayerActivity18.class);
                startActivity(intent18);
                break;

            default:
                Intent intent_def = new Intent(this, SinglePlayerActivity.class);
                startActivity(intent_def);

        }

    }


}
