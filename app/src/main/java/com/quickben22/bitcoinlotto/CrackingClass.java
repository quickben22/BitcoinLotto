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
        start=CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
        String[] povrat=sqlcl.CheckIfAlreadyChecked(start);
        if (povrat[1]!="") // vec je pregledan
        {

            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(povrat[1], " ", 2));
            CryptoClass.keysD.setEndAddress(povrat[1]);

        }
        else
        {
            sqlcl.InsertAddressData(povrat[0],povrat[0]);

        }
        CryptoClass.keysD.setStartAddress(povrat[0]);
        running=true;
    }

    @Override
    public void run() {

        boolean ima_bingo=false;
        if(CryptoClass.keysD.getPrivateKey().contains("BINGO") || CryptoClass.keysD.getPrivateKey().contains("CHECKED"))
            ima_bingo=true;
        String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());
        byte[] PrivHex = CryptoClass.hexStringToByteArray(PrivText);
        if(!ima_bingo)
            PrivHex[31]--;
        int k = Integer.parseInt( CryptoClass.keysD.getKeysCount());
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
                boolean flag2=false;
                if (k % 10 == 0)
                {
                    CryptoClass.cl.InsertSearchData(Integer.parseInt(CryptoClass.keysD.getKeysCount()), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()),0,
                            CryptoClass.keysD.getSolution1(),CryptoClass.keysD.getSolution2(),CryptoClass.keysD.getSolution3(),CryptoClass.keysD.getSolution4(),CryptoClass.keysD.getSolution5()
                            ,CryptoClass.keysD.getSolution6(),CryptoClass.keysD.getSolution7(),CryptoClass.keysD.getSolution8(),CryptoClass.keysD.getSolution9(),CryptoClass.keysD.getSolution10());

                    String[] povrat=sqlcl.CheckIfAlreadyChecked(CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()));
                    if (povrat[1]!="") // vec je pregledan
                    {
                        CryptoClass.cl.InsertAddressData(CryptoClass.keysD.getStartAddress(), povrat[0]);
                         PrivHex = CryptoClass.hexStringToByteArray(povrat[1]);
                        ispis=    CryptoClass.insertPeriodically(povrat[1]," ",2);
                        CryptoClass.keysD.setEndAddress(povrat[1]);
                        CryptoClass.keysD.setStartAddress(povrat[0]);

                    }
                    else {
                        CryptoClass.cl.InsertAddressData(CryptoClass.keysD.getStartAddress(), CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey()));
                    }




                    ArrayList<String> izlaz= sqlcl.CheckIsDataAlreadyInDBorNot(list,list2);

                    if(izlaz.size()>0)
                    {
                        ispis="BINGO - " + izlaz.get(0);
                        running = false;
                        flag=true;

                    }
                    if(list.contains("1NJB66wEsqtFBd3zSFfcdZoLpV6fCrzEP1"))
                    {
                        ispis=list2.get(list.indexOf("1NJB66wEsqtFBd3zSFfcdZoLpV6fCrzEP1"));
                        running = false;
                        flag2=true;

                    }
                    list.clear();
                    list2.clear();
                }

                final String updateWords = ispis;
                final  boolean updateColor=flag;
                final  boolean riddleFlag=flag2;
                private_tb.post(
                        new Runnable() {
                    @Override
                    public void run() {
                        if(running)
                            CryptoClass.keysD.setPrivateKey(updateWords);
                        //                            private_tb.setText(updateWords);
                        if(updateColor) {

//                            CryptoClass.keysD.setPrivateKey(updateWords);
//                            CryptoClass.keysD.setInputKey(updateWords);

//                            editText.setText(updateWords);
                            CryptoClass.keysD.setEndAddress(updateWords);
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
                        else  if(riddleFlag) {
//                            CryptoClass.keysD.setPrivateKey(updateWords);
//                            CryptoClass.keysD.setInputKey(updateWords);
                            CryptoClass.keysD.setEndAddress(updateWords);
                            private_tb.setTextColor(Color.parseColor("#FF0000"));
                            private_tb.setTextColor(Color.parseColor("#FF0000"));

                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("PUZZLE SOLVED!");
                            alertDialog.setMessage("CONGRATULATIONS! You have solved the puzzle! To get the private key where the prize is, add 1 to EVERY character in the currently written private key.");
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
