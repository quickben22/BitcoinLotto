package com.quickben22.bitcoinlotto;

import android.widget.TextView;
import android.graphics.Color;
import java.util.ArrayList;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
/**
 * Created by rista on 26-02-2018.
 */

public class CrackingClass implements Runnable {

    private volatile boolean running = true;
    private TextView private_tb;
    private SqliteClass sqlcl;
    private Context context;
    private String start;
//    private  EditText editText;
   public CrackingClass(TextView t, SqliteClass sq,Context c)
   {
       private_tb=t;
//       editText=e;
       sqlcl=sq;
        context=c;

   }
    public void terminate() {
        running = false;
    }


    public  boolean get_running(){return  running;}
    public  void set_running()
    {
//        start=CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
//        String[] povrat=sqlcl.CheckIfAlreadyChecked(start);
//        if (povrat[1]!="") // vec je pregledan
//        {
//
//
//        }
        running=true;
    }

    @Override
    public void run() {

        boolean ima_bingo=false;
        if(CryptoClass.keysD.getPrivateKey().contains("BINGO"))
            ima_bingo=true;
        String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
        byte[] PrivHex = CryptoClass.hexStringToByteArray(PrivText);
        if(!ima_bingo)
            PrivHex[31]--;
        int k = Integer.parseInt( CryptoClass.keysD.getKeysCount())+1;
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        int k2=1;
        while (running) {
            try {
                CryptoClass.keysD.setKeysCount(Integer.toString(k));
                CryptoClass.k=k2;
                k2++;
                k++;
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

             String   ispis=    CryptoClass.insertPeriodically(PrivText," ",2);


               boolean flag=false;
                if (k % 20 == 0)
                {
                    CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),0);

                    ArrayList<String> izlaz= sqlcl.CheckIsDataAlreadyInDBorNot(list,list2);
                    if(izlaz.size()>0)
                    {
                        ispis="BINGO - " + izlaz.get(0);
                        running = false;
                        flag=true;

                    }
                    list.clear();
                    list2.clear();
                }

                final String updateWords = ispis;
                final  boolean updateColor=flag;
                private_tb.post(
                        new Runnable() {
                    @Override
                    public void run() {
                        CryptoClass.keysD.setPrivateKey(updateWords);
                        //                            private_tb.setText(updateWords);
                        if(updateColor) {

                            CryptoClass.keysD.setInputKey(updateWords);

//                            editText.setText(updateWords);

                            private_tb.setTextColor(Color.parseColor("#FF0000"));

                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("BINGO!");
                            alertDialog.setMessage("You have found a private key - " + updateWords.replace("BINGO - ",""));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                        else {

                            private_tb.setTextColor(Color.parseColor("#B3FFFF"));
                        }

                    }
                });
            } catch (Exception e)
            {

                running = false;
            }
        }

    }

}
