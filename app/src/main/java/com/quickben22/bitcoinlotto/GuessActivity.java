package com.quickben22.bitcoinlotto;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.github.ndczz.infinityloading.InfinityLoading;
import com.quickben22.bitcoinlotto.databinding.ActivityGuessBinding;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import java.math.RoundingMode;

import com.quickben22.bitcoinlotto.databinding.ContentGuessBinding;
import com.quickben22.bitcoinlotto.fragments.OneFragment;
import com.quickben22.bitcoinlotto.fragments.TwoFragment;

import java.lang.Thread;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

//import com.mingle.widget.ShapeLoadingDialog;
//import com.victor.loading.rotate.RotateLoading;
//import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;
import android.os.Handler;

public class GuessActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    int seconds;
    boolean running;


    private TextView mTextView;
//    private EditText mEditText;
//    ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentGuessBinding bindings = DataBindingUtil.setContentView(this, R.layout.content_guess);
        CryptoClass.keysD = new KeysData("","","","","0","0","00:00:00","0");
        bindings.setKeysD(CryptoClass.keysD);

//        setContentView(R.layout.activity_guess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

//         shapeLoadingDialog = new ShapeLoadingDialog(this);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        mTextView=findViewById(R.id.LetterCounter);



       CryptoClass.cl=new SqliteClass(this,false);



        ArrayList<String> lista= CryptoClass.cl.GetSearchData();

        if(lista.size()>0)
        {
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(lista.get(1), " ", 2));
            CryptoClass.keysD.setInputKey(lista.get(1));
            CryptoClass.keysD.setKeysCount(lista.get(0));
        }
        else
            {
            String message = CryptoClass.random();
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message, " ", 2));
            CryptoClass.keysD.setInputKey(message);
                CryptoClass.cl=new SqliteClass(this,true);
        }
        runTimer();

//        CryptoClass.cl.InsertSearchData(0,CryptoClass.keysD.getInputKey(),0);

//        mEditText = findViewById(R.id.private_tx);
//        mEditText.addTextChangedListener(mTextEditorWatcher);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "MAIN");
        adapter.addFragment(new TwoFragment(), "ADVANCED");


        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//        public void private_button_click(View view) {
//
//
//        TextView private_tb = findViewById(R.id.private_tb);
//        private_tb.setText("kifla");
////        EditText mEditText = imageView.findViewById(R.id.private_tx);
////        String message= CryptoClass.GetPrivateKey(mEditText.getText().toString());
////        if(message.length()==64)
////            private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
//    }


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






//    String updateWords;
    public void random_button_click(View view) {


        TextView private_tb = findViewById(R.id.private_tb);

        String message=CryptoClass.random();
        private_tb.setText(CryptoClass.insertPeriodically(message," ",2));
//        EditText editText = findViewById(R.id.private_tx);
//        mEditText.setText(message);


//        RotateLoading l = (RotateLoading)findViewById(R.id.rotateloading);
//        l.start();
//        shapeLoadingDialog.setLoadingText("test");
//        shapeLoadingDialog.show();


//        DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);

// show progress bar and start animating
//        mDilatingDotsProgressBar.showNow();

// stop animation and hide
//        mDilatingDotsProgressBar.hideNow();
    }





    private Thread myThread = null;
    private CrackingClass runnable = null;

    /** Called when the user taps the Send button */


    public  boolean closeThread()
    {
        boolean povrat=true;
        if (myThread != null) {

        if(myThread.isAlive())
             povrat=false;
            runnable.terminate();
            try
            {
                myThread.join();
            }
            catch (Exception e)
            {


            }
        }
return  povrat;
    }



    public void crack_many(View view) {
        // Do something in response to button




        TextView private_tb = findViewById(R.id.private_tb);
//        EditText mEditText = findViewById(R.id.private_tx);


        String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
        if( closeThread()) {
            if (CryptoClass.keysD.getCharacterCount().equals("64")) {
                start_crack();
                runnable = new CrackingClass(private_tb, CryptoClass.cl);
                myThread = new Thread(runnable);

                myThread.start();
            }
        }
        else
        {
            stop_crack();

        }



    }




    private  void start_crack()
    {

        resetTimer();

startTimer();

        InfinityLoading infinity = findViewById(R.id.loading);
        infinity.setVisibility(View.VISIBLE);
        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.private_public_button);


        private_button.setEnabled(false);
        random_button.setEnabled(false);
        crackbutton.setEnabled(false);
        Button stopbutton=findViewById(R.id.crack_button);
        stopbutton.setText("Stop cracking!");
    }

    private  void stop_crack()
    {
        stopTimer();
        InfinityLoading infinity = findViewById(R.id.loading);
        infinity.setVisibility(View.GONE);
        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.private_public_button);

        private_button.setEnabled(true);
        random_button.setEnabled(true);
        crackbutton.setEnabled(true);
        Button stopbutton=findViewById(R.id.crack_button);
        stopbutton.setText("Start Cracking!");
        CryptoClass.keysD.setInputKey(CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()));

        CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),seconds);
    }

    public void startTimer(){
        running = true;
    }

    public void stopTimer(){
        running = false;
    }

    public void resetTimer(){
        running = true;
        seconds = 0;
    }


    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, sec);

                if(running) {
                    CryptoClass.keysD.setTimer(time);


                    seconds++;
                    CryptoClass.keysD.setSpeed(Double.toString(round(((double)CryptoClass.k)/seconds,2)));
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }





}



