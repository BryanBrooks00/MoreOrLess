package com.darwin.moreorless;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class ShopActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd;
    TextView balance_tv;
    Button btn_ad;
    Button btn1_buy;
    Button btn2_buy;
    Button btn3_buy;
    Button btn_back;
    Context context;
    int price1 = 100;
    int price2 = 500;
    int price3 = 1000;
    final static String TAG = "LOG";
    String warning;
    String tryLater;
    String youEarned;
    final static String AD_ID = "ca-app-pub-2382402581294867/1488617897";
    final static String TEST_AD_ID = "ca-app-pub-3940256099942544/5224354917";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        context = ShopActivity.this;
        warning =  getString(R.string.warning);
        tryLater = getString(R.string.tryLater);
        youEarned = getString(R.string.youEarned);
        balance_tv = findViewById(R.id.balance_tv);
        btn_ad = findViewById(R.id.btn_ad);
        btn1_buy = findViewById(R.id.btn1_buy);
        btn2_buy = findViewById(R.id.btn2_buy);
        btn3_buy = findViewById(R.id.btn3_buy);
        btn_back = findViewById(R.id.btn_back);

        loadAd();
        checkBalance();
        checkState();

        btn_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAd();
            }
        });

        btn1_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money() >= price1){
                    btn1_buy.setEnabled(false);
                    btn1_buy.setClickable(false);
                    Preferences.setMoney(context, String.valueOf(money() + 250));
                    payMoney(100);
                }else{
                    makeMessage(warning);
                }
            }
        });

        btn2_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money() >= price2){
                    btn2_buy.setEnabled(false);
                    btn2_buy.setClickable(false);
                    Preferences.setCoefficient(context, String.valueOf(10));
                    payMoney(price2);
                }else{
                    makeMessage(warning);
                }
            }
        });

        btn3_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money() >= price3){
                    btn3_buy.setEnabled(false);
                    btn3_buy.setClickable(false);
                    Preferences.setIsBought(context, "yes");
                    payMoney(price3);
                }else{
                    makeMessage(warning);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void makeMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public int money(){
        return Integer.parseInt(Preferences.getMoney(context));
    }

    public void payMoney(int price){
        Preferences.setMoney(context, String.valueOf(money() - price));
        checkBalance();
    }

    public void checkBalance(){
        balance_tv.setText(Preferences.getMoney(context));
    }

    public void checkState(){
        if (Preferences.getIsBought(context).equals("yes")){
            btn1_buy.setEnabled(false);
            btn1_buy.setClickable(false);
        } else{
            btn1_buy.setEnabled(true);
            btn1_buy.setClickable(true);
        }

        if (Preferences.getCoefficient(context).equals("10")){
            btn2_buy.setEnabled(false);
            btn2_buy.setClickable(false);
        } else{
            btn2_buy.setEnabled(true);
            btn2_buy.setClickable(true);
        }

        if (Preferences.getIsBought(context).equals("yes")){
            btn3_buy.setEnabled(false);
            btn3_buy.setClickable(false);
        } else{
            btn3_buy.setEnabled(true);
            btn3_buy.setClickable(true);
        }
    }

    public void loadAd(){
        Log.i(TAG, "Ad is loading...");
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context,AD_ID,
                adRequest,new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad (@NonNull LoadAdError loadAdError){
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        Log.i(TAG, "Failed to load ad.");
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded (@NonNull RewardedAd rewardedAd){
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    public void showAd(){
        try {
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad was shown.");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.d(TAG, "Ad failed to show.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad was dismissed.");
                    mRewardedAd = null;
                }

            });
        } catch (Exception e){
            Log.i(TAG, "showAd error");
            loadAd();
        }
        if (mRewardedAd!= null) {
            Activity activityContext = ShopActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    makeMessage(youEarned + " " + rewardAmount + " " + rewardType);
                    Preferences.setMoney(context, String.valueOf(money() + rewardAmount));
                    checkBalance();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
            makeMessage(tryLater);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShopActivity.this, MainActivity.class));
        finish();
    }
}