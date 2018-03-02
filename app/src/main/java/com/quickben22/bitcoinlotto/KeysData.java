package com.quickben22.bitcoinlotto;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.ViewDebug;

import java.util.Observable;
/**
 * Created by rista on 02-03-2018.
 */

public class KeysData extends BaseObservable {
    private String privateKey;
    private String publicKey_uncompressed;
    private String publicKey_compressed;
    private String inputKey;


    public KeysData(String PrivateKey, String PublicKey_uncompressed,String PublicKey_compressed,String InputKey) {
        this.privateKey = PrivateKey;
        this.publicKey_uncompressed = PublicKey_uncompressed;
        this.publicKey_compressed = PublicKey_compressed;
        this.inputKey = InputKey;
    }

    @Bindable
    public String getPrivateKey() {
        return privateKey;
    }

    @Bindable
    public String getPublicKey_uncompressed() {
        return publicKey_uncompressed;
    }
    @Bindable
    public String getPublicKey_compressed() {
        return publicKey_compressed;
    }

    @Bindable
    public String getInputKey() {
        return inputKey;
    }
    public  void setPrivateKey(String PrivateKey){
        this.privateKey = PrivateKey;
        notifyPropertyChanged(BR.privateKey);
    }

    public void setPublicKey_uncompressed(String PublicKey_uncompressed) {
        this.publicKey_uncompressed = PublicKey_uncompressed;
        notifyPropertyChanged(BR.publicKey_uncompressed);
    }
    public  void setPublicKey_compressed(String PublicKey_compressed){
        this.publicKey_compressed = PublicKey_compressed;
        notifyPropertyChanged(BR.publicKey_compressed);
    }

    public void setInputKey(String InputKey) {
        this.inputKey = InputKey;
        this.publicKey_compressed= String.valueOf(inputKey.length());
        notifyPropertyChanged(BR.publicKey_compressed);
        notifyPropertyChanged(BR.inputKey);
    }

}
