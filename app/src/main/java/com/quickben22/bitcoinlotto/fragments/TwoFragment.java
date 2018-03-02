package com.quickben22.bitcoinlotto.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickben22.bitcoinlotto.CryptoClass;
import com.quickben22.bitcoinlotto.R;
import com.quickben22.bitcoinlotto.databinding.FragmentTwoBinding;

import org.w3c.dom.Text;


public class TwoFragment extends Fragment{

    public TwoFragment() {
        // Required empty public constructor
    }

    TextView input_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        FragmentTwoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_two, container, false);
        binding.setKeysD(CryptoClass.keysD);
        View view = binding.getRoot();

//         View view = inflater.inflate(R.layout.fragment_two,
//                container, false);




        Button private_input_button = (Button) view.findViewById(R.id.private_button);



        private_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TextView private_tb = (TextView)  getView().findViewById(R.id.private_tb);
//                EditText mEditText = (EditText)  getView().findViewById(R.id.private_tx);
        String message= CryptoClass.GetPrivateKey(CryptoClass.keysD.getInputKey());
        if(message.length()==64)
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message," ",2));


            }
        });

        Button random_button = (Button) view.findViewById(R.id.random_button);

        random_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message=CryptoClass.random();
                CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message," ",2));
                CryptoClass.keysD.setInputKey(message);


            }
        });


        Button private_public_button = (Button) view.findViewById(R.id.private_public_button);

        private_public_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());

                if(PrivText.length()!=64) return;
                byte[] hex = CryptoClass.hexStringToByteArray(PrivText);


                String[] message = CryptoClass.generateAddresses(hex);

                CryptoClass.keysD.setPublicKey_compressed(message[0]);
                CryptoClass.keysD.setPublicKey_uncompressed(message[1]);


//                TextView textView = getView().findViewById(R.id.public_tb);
//                textView.setText(message[0]+"\n"+message[1]);


            }
        });

         EditText meditext = (EditText) view.findViewById(R.id.private_tx);
        meditext.addTextChangedListener(mTextEditorWatcher);
        input_text = view.findViewById(R.id.LetterCounter);

       return view;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length

            input_text.setText (String.valueOf(CryptoClass.keysD.getInputKey().length()));
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };






}
