package com.xdarssoftco.hotlinebd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.*;

public class AboutUs extends AppCompatActivity {

    //for share it button
    public static String PACKAGE_NAME;

    private ImageView fbBtn, instraBtn, twitterBtn, youtubeBtn, gitBtn;

    //for fb ads
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //for share it Button
        PACKAGE_NAME = getApplicationContext().getPackageName();

        fbBtn = findViewById(R.id.fbBtn);
        instraBtn = findViewById(R.id.instraBtn);
        twitterBtn = findViewById(R.id.twitterBtn);
        youtubeBtn = findViewById(R.id.ytBtn);
        gitBtn = findViewById(R.id.gitBtn);

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFbUrl("https://www.facebook.com/smhabibjr");
            }
        });

        instraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInstraUrl("https://www.instagram.com/simply.leojr/");
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTwitterUrl("https://twitter.com/smhabib_abir_jr");
            }
        });

        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToYoutubeUrl("https://www.youtube.com/channel/UCAb6zCUBSCTGhXLME12XD5A/videos");
            }
        });

        gitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotogiturl("https://github.com/smhabibjr");
            }
        });

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "189005963057567_191790682779095", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(
                        AboutUs.this,
                        "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    private void gotogiturl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToYoutubeUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToTwitterUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToInstraUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToFbUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    //Rate me button , alway we need to change the package name
    public void rateMe(View v){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ "com.xdarssoftco.hotlinebd")));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http//play.google.com/store/apps/details?=" + getPackageName())));
        }
    }

    //share it method
    public void shareit(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String value = "https://play.google.com/store/apps/details?id="+PACKAGE_NAME;
        intent.putExtra(Intent.EXTRA_TEXT,value);
        startActivity(Intent.createChooser(intent, "Spread Your Love"));
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
        
    }


}