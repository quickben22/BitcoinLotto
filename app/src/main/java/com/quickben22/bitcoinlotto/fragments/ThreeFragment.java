package com.quickben22.bitcoinlotto.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.quickben22.bitcoinlotto.CrackingClass;
import com.quickben22.bitcoinlotto.CryptoClass;
import com.quickben22.bitcoinlotto.KeysData;
import com.quickben22.bitcoinlotto.R;
import com.quickben22.bitcoinlotto.SqliteClass;
import com.quickben22.bitcoinlotto.databinding.FragmentThreeBinding;

import java.util.ArrayList;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation.AnimationListener;


public class ThreeFragment extends Fragment{


  private  ArrayList<TextView> riddles;
  private  ArrayList<EditText> solutions;
private int selected=0;
private int duzina=1;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        FragmentThreeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_three, container, false);
        binding.setKeysD(CryptoClass.keysD);
        View view = binding.getRoot();

        riddles=new ArrayList<>();
        solutions=new ArrayList<>();


        TextView t1 =  view.findViewById(R.id.Riddle1);
        TextView t2 =  view.findViewById(R.id.Riddle2);
        TextView t3 =  view.findViewById(R.id.Riddle3);
        TextView t4 =  view.findViewById(R.id.Riddle4);
        TextView t5 =  view.findViewById(R.id.Riddle5);
        TextView t6 =  view.findViewById(R.id.Riddle6);
        TextView t7 =  view.findViewById(R.id.Riddle7);
        TextView t8 =  view.findViewById(R.id.Riddle8);
        TextView t9 =  view.findViewById(R.id.Riddle9);
        TextView t10 =  view.findViewById(R.id.Riddle10);
        riddles.add(t1);
        riddles.add(t2);
        riddles.add(t3);
        riddles.add(t4);
        riddles.add(t5);
        riddles.add(t6);
        riddles.add(t7);
        riddles.add(t8);
        riddles.add(t9);
        riddles.add(t10);
        EditText e1 =  view.findViewById(R.id.solutionText1);
        EditText e2 =  view.findViewById(R.id.solutionText2);
        EditText e3 =  view.findViewById(R.id.solutionText3);
        EditText e4 =  view.findViewById(R.id.solutionText4);
        EditText e5 =  view.findViewById(R.id.solutionText5);
        EditText e6 =  view.findViewById(R.id.solutionText6);
        EditText e7 =  view.findViewById(R.id.solutionText7);
        EditText e8 =  view.findViewById(R.id.solutionText8);
        EditText e9 =  view.findViewById(R.id.solutionText9);
        EditText e10 =  view.findViewById(R.id.solutionText10);
        solutions.add(e1);
        solutions.add(e2);
        solutions.add(e3);
        solutions.add(e4);
        solutions.add(e5);
        solutions.add(e6);
        solutions.add(e7);
        solutions.add(e8);
        solutions.add(e9);
        solutions.add(e10);
        duzina=solutions.size();

        Button next_button = (Button) view.findViewById(R.id.nextButton);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Next button")
                        .build());
                riddles.get(selected).setVisibility(View.INVISIBLE);
                solutions.get(selected).setVisibility(View.INVISIBLE);
                selected=(selected+1)%duzina;
                riddles.get(selected).setVisibility(View.VISIBLE);
                solutions.get(selected).setVisibility(View.VISIBLE);
                insert();

            }
        });
        Button back_button = (Button) view.findViewById(R.id.backButton);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Back button")
                        .build());
                riddles.get(selected).setVisibility(View.INVISIBLE);
                solutions.get(selected).setVisibility(View.INVISIBLE);
                selected=(duzina+selected-1)%duzina;
                riddles.get(selected).setVisibility(View.VISIBLE);
                solutions.get(selected).setVisibility(View.VISIBLE);
                insert();
            }
        });

        final TextView mSwitcher = (TextView) view.findViewById(R.id.error1);
        final TextView mSwitcher2 = (TextView) view.findViewById(R.id.error2);




        Button inputRiddle_button = (Button) view.findViewById(R.id.inputRiddle);
        inputRiddle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Input Riddle")
                        .build());

                String message=CryptoClass.keysD.getSolution1()+CryptoClass.keysD.getSolution2()+CryptoClass.keysD.getSolution3()+
                        CryptoClass.keysD.getSolution4()+CryptoClass.keysD.getSolution5()+CryptoClass.keysD.getSolution6()+CryptoClass.keysD.getSolution7()+
                        CryptoClass.keysD.getSolution8()+CryptoClass.keysD.getSolution9()+CryptoClass.keysD.getSolution10();

                boolean isHex = message.toUpperCase().matches("[0-9A-F]+");
                if(!CryptoClass.keysD.getSolutionCount().equals("64"))
                {

                    mSwitcher.setVisibility(View.VISIBLE);
                    fadeOutAndHideImage(mSwitcher);

                }
                else if(!isHex)
                {
                    mSwitcher2.setVisibility(View.VISIBLE);
                    fadeOutAndHideImage(mSwitcher2);

                }
                else
                {
                    CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message," ",2));
                    CryptoClass.keysD.setInputKey(message);

                }

             insert();


            }
        });




        Button question_button = (Button) view.findViewById(R.id.question_button);
        question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Riddle info")
                        .build());

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("INFO");
                alertDialog.setMessage("By solving this puzzle you will receive a private key that contains bitcoins. There are 10 riddles, " +
                        "and each riddle contains a part of the private key. To get the full private key you need to solve them all, and combine them. The combined length of all solutions has to be 64. " +
                        "The solutions are comprised of only hexadecimal numbers (123456789ABCDEF). New hints will be published with newer versions. Good luck!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }
        });

        Button copy_button3 = (Button) view.findViewById(R.id.copy_button3);
        copy_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Copy my key")
                        .build());

                ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Public key mine",CryptoClass.keysD.getMyKey());
                clipboard.setPrimaryClip(clip);


            }
        });

        return view;
    }

    private void fadeOutAndHideImage(final TextView img)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(3000);

        fadeOut.setAnimationListener(new AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                img.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }

    private  void insert()
    {
        CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),0,
                CryptoClass.keysD.getSolution1(),CryptoClass.keysD.getSolution2(),CryptoClass.keysD.getSolution3(),CryptoClass.keysD.getSolution4(),CryptoClass.keysD.getSolution5()
                ,CryptoClass.keysD.getSolution6(),CryptoClass.keysD.getSolution7(),CryptoClass.keysD.getSolution8(),CryptoClass.keysD.getSolution9(),CryptoClass.keysD.getSolution10());

    }


}
