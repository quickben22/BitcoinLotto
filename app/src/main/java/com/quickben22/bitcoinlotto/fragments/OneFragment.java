package com.quickben22.bitcoinlotto.fragments;

import android.databinding.DataBindingUtil;
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
import com.quickben22.bitcoinlotto.databinding.FragmentOneBinding;


public class OneFragment extends Fragment{

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    FragmentOneBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_one, container, false);
            binding.setKeysD(CryptoClass.keysD);

        View view = binding.getRoot();
//        KeysData keys = new KeysData("1","1","1","1");


//        View view = inflater.inflate(R.layout.fragment_one,
//                container, false);


//        Button crack_button = (Button) view.findViewById(R.id.crack_button);
//
//
//
//        crack_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                TextView private_tb = getView().findViewById(R.id.private_tb);
////        EditText editText = findViewById(R.id.private_tx);
//
//                String PrivText = CryptoClass.remove_extra(private_tb.getText().toString());
//                closeThread();
//        if(PrivText.length()==64) {
//            start_crack();
//            runnable = new CrackingClass(private_tb,mEditText, cl);
//            myThread = new Thread(runnable);
//
//            myThread.start();
//        }
//
//
//            }
//        });


        return view;
    }

}
