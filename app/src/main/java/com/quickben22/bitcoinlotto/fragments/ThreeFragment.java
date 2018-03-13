package com.quickben22.bitcoinlotto.fragments;

import android.databinding.DataBindingUtil;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quickben22.bitcoinlotto.CryptoClass;
import com.quickben22.bitcoinlotto.KeysData;
import com.quickben22.bitcoinlotto.R;
import com.quickben22.bitcoinlotto.SqliteClass;
import com.quickben22.bitcoinlotto.databinding.FragmentThreeBinding;

import java.util.ArrayList;


public class ThreeFragment extends Fragment{


    ArrayList<TextView> riddles=new ArrayList<>();
    ArrayList<EditText> solutions=new ArrayList<>();
int selected=0;
int duzina=1;
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


        TextView t1 =  view.findViewById(R.id.Riddle1);
        TextView t2 =  view.findViewById(R.id.Riddle2);
        riddles.add(t1);
        riddles.add(t2);
        EditText e1 =  view.findViewById(R.id.solutionText1);
        EditText e2 =  view.findViewById(R.id.solutionText2);
        solutions.add(e1);
        solutions.add(e2);
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

            }
        });


        return view;
    }

}
