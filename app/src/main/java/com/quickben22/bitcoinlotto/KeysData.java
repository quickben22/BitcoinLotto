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
    private String startAddress;
    private String endAddress;
    private String speed;
    private String solutionCount;
    private String riddle1;
    private String solution1;
    private String riddle2;
    private String solution2;
    private String riddle3;
    private String solution3;
    private String riddle4;
    private String solution4;
    private String riddle5;
    private String solution5;
    private String riddle6;
    private String solution6;
    private String riddle7;
    private String solution7;
    private String riddle8;
    private String solution8;
    private String riddle9;
    private String solution9;
    private String riddle10;
    private String solution11;
    private String solution10;
    private String myKey;
    public KeysData(String PrivateKey, String PublicKey_uncompressed,String PublicKey_compressed,String InputKey,String CharacterCount,String KeysCount,
                    String Timer,String Speed,String StartAddress,String EndAddress,String Riddle1,String Solution1,String Riddle2,String Solution2,String Riddle3,String Solution3,
                    String Riddle4,String Solution4,String Riddle5,String Solution5,String Riddle6,String Solution6,String Riddle7,String Solution7,String Riddle8,String Solution8,
                    String Riddle9,String Solution9,String Riddle10,String Solution10,String Solution11,
                    String SolutionCount,String MyKey   ) {
        this.privateKey = PrivateKey;
        this.publicKey_uncompressed = PublicKey_uncompressed;
        this.publicKey_compressed = PublicKey_compressed;
        this.inputKey = InputKey;
        this.characterCount = CharacterCount;
        this.keysCount = KeysCount;
        this.timer = Timer;
        this.speed = Speed;
        this.startAddress = StartAddress;
        this.endAddress = EndAddress;
        this.solutionCount = SolutionCount;
        this.riddle1 = Riddle1;
        this.solution1 = Solution1;
        this.riddle2 = Riddle2;
        this.solution2 = Solution2;
        this.riddle3 = Riddle3;
        this.solution3 = Solution3;
        this.riddle4 = Riddle4;
        this.solution4 = Solution4;
        this.riddle5 = Riddle5;
        this.solution5 = Solution5;
        this.riddle6 = Riddle6;
        this.solution6 = Solution6;
        this.riddle7 = Riddle7;
        this.solution7 = Solution7;
        this.riddle8 = Riddle8;
        this.solution8 = Solution8;
        this.riddle9 = Riddle9;
        this.solution9 = Solution9;
        this.riddle10 = Riddle10;
        this.solution11 = Solution11;
        this.solution10 = Solution10;
        this.myKey = MyKey;
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
    @Bindable
    public String getStartAddress() {return startAddress;}
    @Bindable
    public String getEndAddress() {return endAddress;}
    @Bindable
    public String getSolutionCount() {return solutionCount;}
    @Bindable
    public String getRiddle1() {return riddle1;}
    @Bindable
    public String getSolution1() {return solution1;}
    @Bindable
    public String getRiddle2() {return riddle2;}
    @Bindable
    public String getSolution2() {return solution2;}
    @Bindable
    public String getRiddle3() {return riddle3;}
    @Bindable
    public String getSolution3() {return solution3;}
    @Bindable
    public String getRiddle4() {return riddle4;}
    @Bindable
    public String getSolution4() {return solution4;}
    @Bindable
    public String getRiddle5() {return riddle5;}
    @Bindable
    public String getSolution5() {return solution5;}
    @Bindable
    public String getRiddle6() {return riddle6;}
    @Bindable
    public String getSolution6() {return solution6;}
    @Bindable
    public String getRiddle7() {return riddle7;}
    @Bindable
    public String getSolution7() {return solution7;}
    @Bindable
    public String getRiddle8() {return riddle8;}
    @Bindable
    public String getSolution8() {return solution8;}
    @Bindable
    public String getRiddle9() {return riddle9;}
    @Bindable
    public String getSolution9() {return solution9;}
    @Bindable
    public String getRiddle10() {return riddle10;}
    @Bindable
    public String getSolution11() {return solution11;}
    @Bindable
    public String getSolution10() {return solution10;}
    @Bindable
    public String getMyKey() {return myKey;}
    
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
    public void setStartAddress(String StartAddress) {
        this.startAddress = StartAddress;
        notifyPropertyChanged(BR.startAddress);
    }
    public void setEndAddress(String EndAddress) {
        this.endAddress = EndAddress;
        notifyPropertyChanged(BR.endAddress);
    }
    public void setRiddle1(String Riddle1) {
        this.riddle1 = Riddle1;
        notifyPropertyChanged(BR.riddle1);
    }
    public void setSolution1(String Solution1) {
        this.solution1 = Solution1;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution1);
    }

    public void setRiddle2(String Riddle2) {
        this.riddle2 = Riddle2;
        notifyPropertyChanged(BR.riddle2);
    }
    public void setSolution2(String Solution2) {
        this.solution2 = Solution2;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution2);
    }
    public void setRiddle3(String Riddle3) {
        this.riddle3 = Riddle3;
        notifyPropertyChanged(BR.riddle3);
    }
    public void setSolution3(String Solution3) {
        this.solution3 = Solution3;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution3);
    }
    public void setRiddle4(String Riddle4) {
        this.riddle4 = Riddle4;
        notifyPropertyChanged(BR.riddle4);
    }
    public void setSolution4(String Solution4) {
        this.solution4 = Solution4;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution4);
    }
    public void setRiddle5(String Riddle5) {
        this.riddle5 = Riddle5;
        notifyPropertyChanged(BR.riddle5);
    }
    public void setSolution5(String Solution5) {
        this.solution5 = Solution5;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution5);
    }

    public void setRiddle6(String Riddle6) {
        this.riddle6 = Riddle6;
        notifyPropertyChanged(BR.riddle6);
    }
    public void setSolution6(String Solution6) {
        this.solution6 = Solution6;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution6);
    }
    public void setRiddle7(String Riddle7) {
        this.riddle7 = Riddle7;
        notifyPropertyChanged(BR.riddle7);
    }
    public void setSolution7(String Solution7) {
        this.solution7 = Solution7;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution7);
    }
    public void setRiddle8(String Riddle8) {
        this.riddle8 = Riddle8;
        notifyPropertyChanged(BR.riddle8);
    }
    public void setSolution8(String Solution8) {
        this.solution8 = Solution8;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution8);
    }
    public void setRiddle9(String Riddle9) {
        this.riddle9 = Riddle9;
        notifyPropertyChanged(BR.riddle9);
    }
    public void setSolution9(String Solution9) {
        this.solution9 = Solution9;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution9);
    }
    public void setRiddle10(String Riddle10) {
        this.riddle10 = Riddle10;
        notifyPropertyChanged(BR.riddle10);
    }
    public void setSolution11(String Solution11) {
        this.solution11 = Solution11;
        notifyPropertyChanged(BR.solution11);
    }
    public void setSolution10(String Solution10) {
        this.solution10 = Solution10;
        this.solutionCount= zbroj();
        notifyPropertyChanged(BR.solutionCount);
        notifyPropertyChanged(BR.solution10);
    }
    
    public void setSolutionCount(String SolutionCount) {
        this.solutionCount = SolutionCount;
        notifyPropertyChanged(BR.solutionCount);
    }
    public void setMyKey(String MyKey) {
        this.myKey = MyKey;
        notifyPropertyChanged(BR.myKey);
    }
    
    private String zbroj()
    {
        return  String.valueOf(this.solution1.length()+this.solution2.length()+this.solution3.length()+this.solution4.length()+this.solution5.length()+
                this.solution6.length()+this.solution7.length()+this.solution8.length()+this.solution9.length()+this.solution10.length());

    }

}
