package com.quickben22.bitcoinlotto.fragments;

import android.databinding.DataBindingUtil;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        riddles.add(t1);
        riddles.add(t2);
        riddles.add(t3);
        riddles.add(t4);
        riddles.add(t5);
        riddles.add(t6);
        EditText e1 =  view.findViewById(R.id.solutionText1);
        EditText e2 =  view.findViewById(R.id.solutionText2);
        EditText e3 =  view.findViewById(R.id.solutionText3);
        EditText e4 =  view.findViewById(R.id.solutionText4);
        EditText e5 =  view.findViewById(R.id.solutionText5);
        EditText e6 =  view.findViewById(R.id.solutionText6);
        solutions.add(e1);
        solutions.add(e2);
        solutions.add(e3);
        solutions.add(e4);
        solutions.add(e5);
        solutions.add(e6);
        duzina=solutions.size();

        Button next_button = (Button) view.findViewById(R.id.nextButton);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                riddles.get(selected).setVisibility(View.INVISIBLE);
                solutions.get(selected).setVisibility(View.INVISIBLE);
                selected=(selected+1)%duzina;
                riddles.get(selected).setVisibility(View.VISIBLE);
                solutions.get(selected).setVisibility(View.VISIBLE);
                CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),0,
                        CryptoClass.keysD.getSolution1(),CryptoClass.keysD.getSolution2(),CryptoClass.keysD.getSolution3(),CryptoClass.keysD.getSolution4(),CryptoClass.keysD.getSolution5());

            }
        });
        Button back_button = (Button) view.findViewById(R.id.backButton);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                riddles.get(selected).setVisibility(View.INVISIBLE);
                solutions.get(selected).setVisibility(View.INVISIBLE);
                selected=(duzina+selected-1)%duzina;
                riddles.get(selected).setVisibility(View.VISIBLE);
                solutions.get(selected).setVisibility(View.VISIBLE);
                CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),0,
                        CryptoClass.keysD.getSolution1(),CryptoClass.keysD.getSolution2(),CryptoClass.keysD.getSolution3(),CryptoClass.keysD.getSolution4(),CryptoClass.keysD.getSolution5());
            }
        });

        final TextView mSwitcher = (TextView) view.findViewById(R.id.error1);





        Button inputRiddle_button = (Button) view.findViewById(R.id.inputRiddle);
        inputRiddle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(CryptoClass.keysD.getSolutionCount()=="64")
                {

                    String message=CryptoClass.keysD.getSolution1()+CryptoClass.keysD.getSolution2()+CryptoClass.keysD.getSolution3()+CryptoClass.keysD.getSolution4()+CryptoClass.keysD.getSolution5();

                    CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message," ",2));
                    CryptoClass.keysD.setInputKey(message);
                }
                else
                {
                    mSwitcher.setVisibility(View.VISIBLE);
                    fadeOutAndHideImage(mSwitcher);
                }


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


}
