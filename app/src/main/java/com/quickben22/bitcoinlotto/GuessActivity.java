package com.quickben22.bitcoinlotto;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;


import com.github.ndczz.infinityloading.InfinityLoading;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.math.RoundingMode;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.quickben22.bitcoinlotto.databinding.ContentGuessBinding;
import com.quickben22.bitcoinlotto.fragments.OneFragment;
import com.quickben22.bitcoinlotto.fragments.TwoFragment;
import com.quickben22.bitcoinlotto.fragments.ThreeFragment;

import java.lang.Thread;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import android.support.multidex.MultiDex;
import android.os.Handler;

public class GuessActivity extends AppCompatActivity {

    private Thread myThread = null;
    private CrackingClass runnable = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    int seconds;
    boolean running;

    private AdView mAdView;
    private TextView mTextView;
//    private EditText mEditText;
//    ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MultiDex.install(this);

        ContentGuessBinding bindings = DataBindingUtil.setContentView(this, R.layout.content_guess);
        CryptoClass.keysD = new KeysData("","","","",
                "0","0","00:00:00","0","","","1. 849- or sex","",
                "2. Bones, Vision, Antioxidant, Nerves, Immunity","","3. Monsters don't always stay under the bed.",
                "","4. Five Black Dragons","","5. Strutt's home number","","6. Bickle's workplace(3)","",
                "7. Pelham Warner's descendant's tempest","","8. His value is either the highest or the lowest","",
                "9. Int that overflows: 1906, 6406, 25606, 153094...","","10. Last 2 characters you will have to guess","",
                "0","18k9UZ2cdqH9GxCtXEm41v121Rpf9aDv24");
        bindings.setKeysD(CryptoClass.keysD);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3535032153893847~2319490240");
//        setContentView(R.layout.activity_guess);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

//         shapeLoadingDialog = new ShapeLoadingDialog(this);



//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        mTextView=findViewById(R.id.LetterCounter);


       CryptoClass.cl=new SqliteClass(this);

        TextView private_tb = findViewById(R.id.private_tb);
        runnable = new CrackingClass(private_tb, CryptoClass.cl,this);

        ArrayList<String> lista= CryptoClass.cl.GetSearchData();

        if(lista.size()>0)
        {
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(lista.get(1), " ", 2));
            CryptoClass.keysD.setInputKey(lista.get(1));
            CryptoClass.keysD.setKeysCount(lista.get(0));
            CryptoClass.keysD.setSolution1(lista.get(3));
            CryptoClass.keysD.setSolution2(lista.get(4));
            CryptoClass.keysD.setSolution3(lista.get(5));
            CryptoClass.keysD.setSolution4(lista.get(6));
            CryptoClass.keysD.setSolution5(lista.get(7));
            CryptoClass.keysD.setSolution6(lista.get(8));
            CryptoClass.keysD.setSolution7(lista.get(9));
            CryptoClass.keysD.setSolution8(lista.get(10));
            CryptoClass.keysD.setSolution9(lista.get(11));
            CryptoClass.keysD.setSolution10(lista.get(12));
        }
        else
        {
            String message = CryptoClass.random();
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message, " ", 2));
            CryptoClass.keysD.setInputKey(message);
            CryptoClass.cl.update(true);
        }


            CryptoClass.mTracker=   AnalyticsHelper.getTracker(this);



        runTimer();

//        CryptoClass.cl.InsertSearchData(0,CryptoClass.keysD.getInputKey(),0);

//        mEditText = findViewById(R.id.private_tx);
//        mEditText.addTextChangedListener(mTextEditorWatcher);
    }


    private  void neznam()
    {



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "MAIN");
        adapter.addFragment(new TwoFragment(), "ADVANCED");
        adapter.addFragment(new ThreeFragment(), "PUZZLE");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.i("TAG", "Setting screen name: " + position);
                CryptoClass.mTracker.setScreenName("Image~" + position);
                CryptoClass.mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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





//        EditText mEditText = findViewById(R.id.private_tx);


        String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
        if( closeThread()) {
            if (PrivText.length()==64) {
                runnable.set_running();
                start_crack();

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

        CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Start Crack")
                .build());

        resetTimer();

startTimer();

        InfinityLoading infinity = findViewById(R.id.loading);
        infinity.setVisibility(View.VISIBLE);
        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.private_public_button);


        Button nextButton=findViewById(R.id.nextButton);
        Button backButton=findViewById(R.id.backButton);
        Button inputRiddle=findViewById(R.id.inputRiddle);
        private_button.setEnabled(false);
        random_button.setEnabled(false);
        crackbutton.setEnabled(false);
        nextButton.setEnabled(false);
        backButton.setEnabled(false);
        inputRiddle.setEnabled(false);
        Button stopbutton=findViewById(R.id.crack_button);
        stopbutton.setText("Stop searching!");
    }

    public   void stop_crack()
    {

        CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Stop Crack")
                .build());

        stopTimer();
        InfinityLoading infinity = findViewById(R.id.loading);
        infinity.setVisibility(View.GONE);
        Button private_button=findViewById(R.id.private_button);
        Button random_button=findViewById(R.id.random_button);
        Button crackbutton=findViewById(R.id.private_public_button);
        Button nextButton=findViewById(R.id.nextButton);
        Button backButton=findViewById(R.id.backButton);
        Button inputRiddle=findViewById(R.id.inputRiddle);
        private_button.setEnabled(true);
        random_button.setEnabled(true);
        crackbutton.setEnabled(true);
        nextButton.setEnabled(true);
        backButton.setEnabled(true);
        inputRiddle.setEnabled(true);
        Button stopbutton=findViewById(R.id.crack_button);
        stopbutton.setText("Start searching!");
        CryptoClass.keysD.setInputKey(CryptoClass.remove_extra(CryptoClass.keysD.getEndAddress()));
        String s1=CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()).substring(62,64);
        String s2=CryptoClass.keysD.getEndAddress().substring(62,64);
        int razlika = Integer.valueOf(CryptoClass.keysD.getKeysCount())- (Integer.valueOf(s1, 16)-Integer.valueOf(s2, 16));
        CryptoClass.keysD.setKeysCount(Integer.toString(razlika));
        CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(CryptoClass.keysD.getEndAddress(), " ", 2));
//        CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),seconds,
//                CryptoClass.keysD.getSolution1(),CryptoClass.keysD.getSolution2(),CryptoClass.keysD.getSolution3());
//
//        CryptoClass.cl.InsertAddressData(CryptoClass.keysD.getStartAddress(),CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()));
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

                    if(!runnable.get_running())
                        stop_crack();

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



