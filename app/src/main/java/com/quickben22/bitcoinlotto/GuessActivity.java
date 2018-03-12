package com.quickben22.bitcoinlotto;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;



import java.lang.Thread;
import com.mingle.widget.ShapeLoadingDialog;

public class GuessActivity extends AppCompatActivity {


    private TextView mTextView;
    private EditText mEditText;
    ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         shapeLoadingDialog = new ShapeLoadingDialog(this);




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mTextView=findViewById(R.id.LetterCounter);
        mEditText = findViewById(R.id.private_tx);
        mEditText.addTextChangedListener(mTextEditorWatcher);
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
//        EditText editText = findViewById(R.id.private_tx);
        String message=CryptoClass.GetPrivateKey(mEditText.getText().toString());
        if(message.length()==64)
            private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
    }



//    String updateWords;
    public void random_button_click(View view) {


        TextView private_tb = findViewById(R.id.private_tb);

        String message=CryptoClass.random();
        private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
//        EditText editText = findViewById(R.id.private_tx);
        mEditText.setText(message);

        shapeLoadingDialog.setLoadingText("test");
        shapeLoadingDialog.show();

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

        stop_crack();

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
//        EditText editText = findViewById(R.id.private_tx);

        String PrivText = CryptoClass.remove_extra(private_tb.getText().toString());
        closeThread();
        if(PrivText.length()==64) {
            start_crack();
            runnable = new CrackingClass(private_tb,mEditText, cl);
            myThread = new Thread(runnable);

            myThread.start();
        }


    }

    private  void start_crack()
    {

        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.button2);


        private_button.setEnabled(false);
        random_button.setEnabled(false);
        crackbutton.setEnabled(false);
        Button stopbutton=findViewById(R.id.button);
        stopbutton.setText("Stop cracking!");
    }

    private  void stop_crack()
    {
        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.button2);

        private_button.setEnabled(true);
        random_button.setEnabled(true);
        crackbutton.setEnabled(true);
        Button stopbutton=findViewById(R.id.button);
        stopbutton.setText("Convert private to public");
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTextView.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };







}



