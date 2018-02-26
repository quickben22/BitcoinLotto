package com.quickben22.bitcoinlotto;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.Thread;


public class GuessActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guess, menu);
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


    public void private_button_click(View view) {


        TextView private_tb = findViewById(R.id.private_tb);
        EditText editText = findViewById(R.id.private_tx);
        String message=CryptoClass.GetPrivateKey(editText.getText().toString());
        if(message.length()==64)
            private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
    }



//    String updateWords;
    public void random_button_click(View view) {


        TextView private_tb = findViewById(R.id.private_tb);

        String message=CryptoClass.random();
        private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
        EditText editText = findViewById(R.id.private_tx);
        editText.setText(message);



    }





    private Thread myThread = null;
    private CrackingClass runnable = null;

    /** Called when the user taps the Send button */
    public void crack_one(View view) {
        // Do something in response to button
        TextView private_tb = findViewById(R.id.private_tb);
        String PrivText = CryptoClass.remove_extra(private_tb.getText().toString());

        if(PrivText.length()!=64) return;
        byte[] hex = CryptoClass.hexStringToByteArray(PrivText);


        String[] message = CryptoClass.generateAddresses(hex);


        TextView textView = findViewById(R.id.slova);
        textView.setText(message[0]+"\n"+message[1]);


        closeThread();

    }

    public  void closeThread()
    {
        if (myThread != null) {
            runnable.terminate();
            try
            {
                myThread.join();
            }
            catch (Exception e)
            {


            }
        }

    }



    public void crack_many(View view) {
        // Do something in response to button



        SqliteClass cl=new SqliteClass(this);
        TextView private_tb = findViewById(R.id.private_tb);
        EditText editText = findViewById(R.id.private_tx);

        String PrivText = CryptoClass.remove_extra(private_tb.getText().toString());
        closeThread();
        if(PrivText.length()==64) {
            runnable = new CrackingClass(private_tb,editText, cl);
            myThread = new Thread(runnable);

            myThread.start();
        }


    }








}



