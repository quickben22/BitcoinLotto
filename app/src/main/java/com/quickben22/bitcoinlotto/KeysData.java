package com.quickben22.bitcoinlotto;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Observable;
/**
 * Created by rista on 02-03-2018.
 */

public class KeysData extends BaseObservable {
    private String privateKey;
    private String publicKey_uncompressed;
    private String publicKey_compressed;
    private String inputKey;
    private String characterCount;
    private String keysCount;
    private String timer;
    private String speed;



    public KeysData(String PrivateKey, String PublicKey_uncompressed,String PublicKey_compressed,String InputKey,String CharacterCount,String KeysCount,String Timer,String Speed ) {
        this.privateKey = PrivateKey;
        this.publicKey_uncompressed = PublicKey_uncompressed;
        this.publicKey_compressed = PublicKey_compressed;
        this.inputKey = InputKey;
        this.characterCount = CharacterCount;
        this.keysCount = KeysCount;
        this.timer = Timer;
        this.speed = Speed;
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
    @Bindable
    public String getCharacterCount() {return characterCount;}
    @Bindable
    public String getKeysCount() {return keysCount;}
    @Bindable
    public String getTimer() {return timer;}
    @Bindable
    public String getSpeed() {return speed;}

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
        this.characterCount= String.valueOf(inputKey.length());
        notifyPropertyChanged(BR.characterCount);
        notifyPropertyChanged(BR.inputKey);
    }
    public void setCharacterCount(String CharacterCount) {
        this.characterCount = CharacterCount;
        notifyPropertyChanged(BR.characterCount);
    }
    public void setKeysCount(String KeysCount) {
        this.keysCount = KeysCount;
        notifyPropertyChanged(BR.keysCount);
    }
    public void setTimer(String Timer) {
        this.timer = Timer;
        notifyPropertyChanged(BR.timer);
    }
    public void setSpeed(String Speed) {
        this.speed = Speed;
        notifyPropertyChanged(BR.speed);
    }

}
