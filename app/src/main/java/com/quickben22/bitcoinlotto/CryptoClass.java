package com.quickben22.bitcoinlotto;


import com.google.android.gms.analytics.Tracker;

import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.math.ec.ECPoint;
import java.util.Random;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rista on 20-02-2018.
 */

public final  class CryptoClass {


    public  static  SqliteClass cl;
public  static  KeysData keysD;
    public static Tracker mTracker;

    public static byte[] PrivToPub(byte[] PrivHex)
    {
        byte[] hex = ValidateAndGetHexPrivateKey((byte)0x00, PrivHex);
        org.spongycastle.asn1.x9.X9ECParameters ps = org.spongycastle.asn1.sec.SECNamedCurves.getByName("secp256k1");
        BigInteger Db =  new BigInteger(hex);
        ECPoint dd = ps.getG().multiply(Db);

        byte[] pubaddr = new byte[65];
        byte[] Y = dd.normalize().getYCoord().toBigInteger().toByteArray();
        System.arraycopy(Y, 0, pubaddr, 64 - Y.length + 1, Y.length);
        byte[] X =dd.normalize().getXCoord().toBigInteger().toByteArray();
        System.arraycopy(X, 0, pubaddr, 32 - X.length + 1, X.length);
        pubaddr[0] = 4;

        return (pubaddr);

    }

    public  static  int k=1;

    public static byte[] PrivToPub_compressed(byte[] PrivHex)
    {
        byte[] hex = ValidateAndGetHexPrivateKey((byte)0x00, PrivHex);
        org.spongycastle.asn1.x9.X9ECParameters ps = org.spongycastle.asn1.sec.SECNamedCurves.getByName("secp256k1");
        BigInteger Db =  new BigInteger(hex);
        ECPoint dd = ps.getG().multiply(Db);

        byte[] pubaddr = new byte[33];
        byte[] Y = dd.normalize().getYCoord().toBigInteger().toByteArray();
        byte[] X = dd.normalize().getXCoord().toBigInteger().toByteArray();


        if (Y[Y.length-1] % 2 == 0)
        {
            System.arraycopy(X, 0, pubaddr, 32 - X.length + 1, X.length);
            pubaddr[0] = 2;
        }
        else
        {
            System.arraycopy(X, 0, pubaddr, 32 - X.length + 1, X.length);
            pubaddr[0] = 3;
        }


        return (pubaddr);

    }


    public static byte[] PubHexToHash(byte[] hex)
    {
            byte[] rip=new byte[20];


            byte[] hash = sha(hex);
            RIPEMD160Digest dig2 = new RIPEMD160Digest();
            dig2.update(hash, 0, hash.length);
            dig2.doFinal(rip, 0);

        return  rip;
    }
    public   static  byte[] sha(byte[] hex)
    {
        byte[] hash = new byte[32];
        try {
            MessageDigest dig = MessageDigest.getInstance("SHA256");
            hash = dig.digest(hex);
        }
        catch (NoSuchAlgorithmException e)
        {

        }
        return  hash;
    }




    public static   String PubHashToAddress(byte[] hex)
    {

        byte[] hex2 = new byte[21];
        System.arraycopy(hex, 0, hex2, 1, 20);

        byte[] ba = new byte[hex2.length + 4];
        System.arraycopy(hex2,0, ba,0, hex2.length);

        byte[] thehash = sha(hex2);
        thehash = sha(thehash);
        for (int i = 0; i < 4; i++) ba[hex2.length + i] = thehash[i];


        BigInteger addrremain = new BigInteger( ba);

        BigInteger big0 = new BigInteger("0");
        BigInteger big58 =new BigInteger("58");

        String b58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

        String rv = "";

        while (addrremain.compareTo(big0) > 0)
        {

            int d = addrremain.mod(big58).intValue();
            addrremain = addrremain.divide(big58);
            rv = b58.substring(d, d+1) + rv;
        }

        // handle leading zeroes
        for (byte b : ba)
        {
            if (b != 0) break;
            rv = "1" + rv;

        }
        return rv;

    }


    public static  byte[] ValidateAndGetHexPrivateKey(byte leadingbyte, byte[] PrivHex)
    {
        byte[] hex = PrivHex;
        byte[] hex2 = new byte[33];
        System.arraycopy(hex, 0, hex2, 1, 32);
        hex2[0] = (byte) 0x80;
        hex = hex2;
        hex[0] = leadingbyte;
        return hex;

    }

    public  static String GetPrivateKey(String key)
    {

        if (key.matches("[0-9a-zA-Z]+") == true)
        {
            if (key.length() == 64)
            {
                String[] s = new String[32];
                String s2="";
                for (int i = 0; i < key.length(); i += 2)
                {
                    s[i / 2] = key.substring(i, i+2);
                s2+=s[i / 2];
                }
                return  s2;
            }
            else
            {
                byte[] hex = PrivWIFToHex(key.toCharArray());
                if (hex != null)
                {
                    byte[] hex2 = new byte[32];
                    System.arraycopy(hex, 1, hex2, 0, 32);
                    return  bytesToHex(hex2 );
                }
            }
        }
        return  "";

    }

    private static byte[] PrivWIFToHex(char[] base58)
    {
        BigInteger bi2 =new BigInteger("0");
        String b58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

        boolean IgnoreChecksum = false;

        for (char c : base58)
        {
            if (b58.indexOf(c) != -1)
            {
                bi2 = bi2.multiply(new BigInteger("58"));
                bi2 = bi2.add(new BigInteger(String.valueOf(b58.indexOf(c))));
            }
            else if (c == '?')
            {
                IgnoreChecksum = true;
            }
            else
            {
                return null;
            }
        }

        byte[] bbc = bi2.toByteArray();
        byte[] bb=new byte[bbc.length-1];
        System.arraycopy(bbc,1,bb,0, bb.length);
        // interpret leading '1's as leading zero bytes
        for (char c : base58)
        {
            if (c != '1') break;
            byte[] bbb = new byte[bb.length + 1];
            System.arraycopy(bb, 0, bbb, 1, bb.length);
            bb = bbb;
        }

        if (bb.length < 4) return null;

        if (IgnoreChecksum == false)
        {
            byte[] bb2 = new byte[bb.length -4];
            System.arraycopy(bb, 0, bb2, 0, bb2.length);

            byte[] checksum = sha(bb2);
            checksum = sha(checksum);
            for (int i = 0; i < 4; i++)
            {
                if (checksum[i] != bb[bb.length - 4 + i]) return null;
            }
        }

        byte[] rv = new byte[bb.length - 4];
        System.arraycopy(bb, 0, rv, 0, bb.length - 4);
        return rv;

    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    public  static  String[] generateAddresses(byte[] hex)
    {
        byte[] pubHex=CryptoClass.PrivToPub(hex);
        byte[] pubHash=CryptoClass.PubHexToHash(pubHex);
        String address=CryptoClass.PubHashToAddress(pubHash);
        pubHex=CryptoClass.PrivToPub_compressed(hex);
        pubHash=CryptoClass.PubHexToHash(pubHex);
       String address2=CryptoClass.PubHashToAddress(pubHash);
        String[] povrat={address,address2};
       return  povrat;

    }

    public static String byteToHex(byte b) {
        char[] hexChars = new char[2];
            int v = b & 0xFF;
            hexChars[0] = hexArray[v >>> 4];
            hexChars[1] = hexArray[v & 0x0F];

        return new String(hexChars);
    }


    public static String random()
    {
        Random rand = new Random();
        String s="";
        for(int i=0;i<32;i++) {
            int n = rand.nextInt(256);
            String h=Integer.toHexString(n);
            if(h.length()==1)
                h="0"+h;
            s+=h;
        }
        return  s;
    }

    public static String insertPeriodically(
            String text, String insert, int period)
    {
        StringBuilder builder = new StringBuilder(
                text.length() + insert.length() * (text.length()/period)+1);

        int index = 0;
        String prefix = "";

        while (index < text.length())
        {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            if(index==text.length()/2-period)
                prefix = "\n";
            else
                prefix = insert;
            builder.append(text.substring(index,
                    Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }



    public  static  String remove_extra(String s)
    {

        return  s.replace("BINGO -","").replace(" ","").replace("\n","");
    }


}
