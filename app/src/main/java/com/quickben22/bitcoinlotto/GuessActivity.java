package com.quickben22.bitcoinlotto;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;

public class GuessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        private_tb.setText(message);
    }



//    String updateWords;
    public void random_button_click(View view) {


        TextView private_tb = findViewById(R.id.private_tb);

        String message=CryptoClass.random();
        private_tb.setText(message);





    }

   Runnable myRunnable =new Runnable() {
       @Override
       public void run() {
           while (true) {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               final TextView private_tb = findViewById(R.id.private_tb);
               final String updateWords = updateAuto();
               private_tb.post(new Runnable() {
                   @Override
                   public void run() {
                       private_tb.setText(updateWords);
                   }
               });
           }
       }
   };



    /** Called when the user taps the Send button */
    public void crack_one(View view) {
        // Do something in response to button
        TextView private_tb = findViewById(R.id.private_tb);
        String PrivText =  (private_tb.getText().toString());
        byte[] hex = CryptoClass.hexStringToByteArray(PrivText);


        String[] message = CryptoClass.generateAddresses(hex);


        TextView textView = findViewById(R.id.slova);
        textView.setText(message[0]+"\n"+message[1]);
    }
    public void crack_many(View view) {
        // Do something in response to button


        Thread myThread = new Thread(myRunnable);
        myThread.start();

        SqliteClass cl=new SqliteClass(this);


        TextView private_tb = findViewById(R.id.private_tb);
        String PrivText = (private_tb.getText().toString());
        byte[] PrivHex = CryptoClass.hexStringToByteArray(PrivText);
        PrivHex[31]--;
        int k = 1;
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        for (k = 1; k < 500000; k++)
        {


            PrivHex[31]++;

            PrivText=  PrivText.substring(0,62)+ CryptoClass.byteToHex( PrivHex[31]);

            if (PrivHex[31] == 0)
            {
                for (int l = 30; l >= 0; l--)
                {
                    PrivHex[l]++;
                    PrivText=PrivText.substring(0,l*2)+CryptoClass.byteToHex( PrivHex[l])+PrivText.substring(l*2+2,64);

                    if (PrivHex[l] != 0)
                        break;
                }
            }


            String[] message = CryptoClass.generateAddresses(PrivHex);
            list.add(message[0]);
            list.add(message[1]);
            list2.add(PrivText);
            list2.add(PrivText);

            private_tb.setText(PrivText);

            if (k % 50 == 0)
            {

                ArrayList<String> izlaz= cl.CheckIsDataAlreadyInDBorNot(list,list2);

                list.clear();
                list2.clear();
            }

        }

    }





    public  String updateAuto()
    {
        Random rand = new Random();

            String n =   Integer.toString(rand.nextInt(256));

        return n;

    }


}



