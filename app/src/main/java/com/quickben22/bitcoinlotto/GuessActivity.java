package com.quickben22.bitcoinlotto;


import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.adcolony.sdk.AdColony;
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

import com.google.android.gms.ads.reward.RewardItem;
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
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.applovin.sdk.AppLovinSdk;
//import android.support.multidex.MultiDex;
import android.os.Handler;

public class GuessActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mRewardedVideoAd;


    private Thread myThread = null;
    private CrackingClass runnable = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    int seconds;
    boolean running;
    private  Button crack_button;
    private AdView mAdView;
    private TextView mTextView;
//    private EditText mEditText;
//    ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MultiDex.install(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    ContentGuessBinding bindings = DataBindingUtil.setContentView(this, R.layout.content_guess);
boolean prviput=true;
if(CryptoClass.keysD!=null) {
    prviput = false;
}
if(prviput)
{
    CryptoClass.keysD = new KeysData("", "", "", "",
            "0", "0", "00:00:00", "0", "", "", "1. 849- or sex", "",
            "2. Bones, Vision, Antioxidant, Nerves, Immunity", "", "3. Monsters don't always stay under the bed.",
            "", "4. Five Black Dragons", "", "5. Strutt's home number", "", "6. Bickle's workplace(3)", "",
            "7. Pelham Warner's descendant's tempest", "", "8. His value is either the highest or the lowest", "",
            "9. Int that overflows: 1906, 6406, 25606, 153094...", "", "10. Last 2 characters you will have to guess", "",
            "0", "0", "18k9UZ2cdqH9GxCtXEm41v121Rpf9aDv24");
}
    bindings.setKeysD(CryptoClass.keysD);
    // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
    MobileAds.initialize(this, "ca-app-pub-3535032153893847~2319490240");

    // Use an activity context to get the rewarded video instance.
    mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
    mRewardedVideoAd.setRewardedVideoAdListener(this);

    AdColony.configure(this,           // activity context
            "app178a944cc6314107b0",
            "vzfdbc9070c0e24a6482", "vz26bb4ad1b278412c95"); // list of all your zones set up on the AdColony Dashboard
        if(prviput)
    AppLovinSdk.initializeSdk(this);

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


    mTextView = findViewById(R.id.LetterCounter);
        crack_button = findViewById(R.id.crack_button);
if(prviput)
    CryptoClass.cl = new SqliteClass(this);

    TextView private_tb = findViewById(R.id.private_tb);


            runnable = new CrackingClass(private_tb, CryptoClass.cl, this, crack_button);
        if(prviput) {
            ArrayList<String> lista = CryptoClass.cl.GetSearchData();

            if (lista.size() > 0 && lista.get(13) != null) {
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

                CryptoClass.keysD.setSolution11(lista.get(13));
            } else {
                String message = CryptoClass.random();
                CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message, " ", 2));
                CryptoClass.keysD.setInputKey(message);
                CryptoClass.cl.update(true);
            }


            CryptoClass.mTracker = AnalyticsHelper.getTracker(this);


            loadRewardedVideoAd();

        }


        if(prviput) {
            int k = Integer.valueOf(CryptoClass.keysD.getKeysCount());
            if ((k == runnable.domet1 || k % runnable.domet2 == 0) && k != 0)
                crack_button.setEnabled(false);
        }


    runTimer();






//        CryptoClass.cl.InsertSearchData(0,CryptoClass.keysD.getInputKey(),0);

//        mEditText = findViewById(R.id.private_tx);
//        mEditText.addTextChangedListener(mTextEditorWatcher);
    }


    private void loadRewardedVideoAd() {


        if( CryptoClass.keysD.getSolution11().equals("0")) { //riddle
            mRewardedVideoAd.loadAd("ca-app-pub-3535032153893847/8803492174",
                    new AdRequest.Builder().build());
        }
        else // search
        {
            mRewardedVideoAd.loadAd("ca-app-pub-3535032153893847/7859744469",
                    new AdRequest.Builder().build());

        }

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

    @Override
    public void onRewardedVideoAdLoaded() {

        Button enableRiddle=  findViewById(R.id.enableRiddle);
        if(enableRiddle!=null)
            enableRiddle.setEnabled(true);
//        Button enableSearch=  findViewById(R.id.enableSearch);
//        enableSearch.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        if(rewardItem.getAmount()==1000 || (rewardItem.getAmount()==10 && !CryptoClass.riddle)) // enable search
        {


            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("REWARD");
            alertDialog.setMessage("You got "+runnable.domet2+" searches! Good luck searching!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            crack_button.setEnabled(true);

        }
            else if(rewardItem.getAmount()==10 && CryptoClass.riddle)  // enable riddle
        {
//            Button enableRiddle=  findViewById(R.id.enableRiddle);
//            enableRiddle.setEnabled(true);

            CryptoClass.keysD.setSolution11("1");

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("REWARD");
            alertDialog.setMessage("You have successfully enabled this riddle! Good luck!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            reward_riddle();


        }

    }



    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

//    @Override
//    public void onRewardedVideoCompleted() {
//
//    }

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


    private  void reward_riddle()
    {
        Button nextButton=  findViewById(R.id.nextButton);
        Button backButton= findViewById(R.id.backButton);
        Button inputButton=  findViewById(R.id.inputRiddle);
        Button enableRiddle=  findViewById(R.id.enableRiddle);
        nextButton.setEnabled(true);
        backButton.setEnabled(true);
        inputButton.setEnabled(true);
        enableRiddle.setEnabled(false);



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




    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Reklama search")
                            .build());

                    mRewardedVideoAd.show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    public void enable_search(View view) {

stop_crack();
        if (mRewardedVideoAd.isLoaded()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to watch a video ad for 100,000 more searches?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("RESTART");
            alertDialog.setMessage("Restart the app to get more searches.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

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
        Button enableRiddle=findViewById(R.id.enableRiddle);
        Button wifbutton=findViewById(R.id.private_wif_button);
        wifbutton.setEnabled(false);
        private_button.setEnabled(false);
        random_button.setEnabled(false);
        crackbutton.setEnabled(false);
        nextButton.setEnabled(false);
        backButton.setEnabled(false);
        inputRiddle.setEnabled(false);
        enableRiddle.setEnabled(false);

        crack_button.setText("Stop searching!");
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
        Button enableRiddle=findViewById(R.id.enableRiddle);
        Button wifbutton=findViewById(R.id.private_wif_button);
        wifbutton.setEnabled(true);
        private_button.setEnabled(true);
        random_button.setEnabled(true);
        crackbutton.setEnabled(true);

        if( !CryptoClass.keysD.getSolution11().equals("0")) {
            nextButton.setEnabled(true);
            backButton.setEnabled(true);
            inputRiddle.setEnabled(true);

        }
        else
            enableRiddle.setEnabled(true);
        crack_button.setText("Start searching!");
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



