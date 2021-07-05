package com.xdarssoftco.hotlinebd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.*;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    //for get current location button
    TextView textViewLatt, textViewLong, textViewAdd, textViewLoc, textViewCountry, textViewPostalCode;
    Button btn_location;
    FusedLocationProviderClient fusedLocationProviderClient;

    //for share it button
    public static String PACKAGE_NAME;

    //1.create a button variable
    private Button btn_bng;
    private Button btn_eng;
    //2. initialized the button using btn id from xml
    //3. declare the btn onclick listener i love android development
    //4. create a methood for activity which I want to open after clicking
    //5. Now we are ready to Intent our activity


    //for fb ads
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for get current location button
        textViewLatt = findViewById(R.id.TVlatt);
        textViewLong = findViewById(R.id.TVlong);
        textViewAdd = findViewById(R.id.TVadd);
        textViewLoc = findViewById(R.id.TVloc);
        textViewCountry = findViewById(R.id.TVCountry);
        textViewPostalCode = findViewById(R.id.TVPostalCode);
        btn_location = findViewById(R.id.btnLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    showLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

            }
        });

        //for share it Button
        PACKAGE_NAME = getApplicationContext().getPackageName();

        btn_bng = (Button) findViewById(R.id.main_btn_bng);
        btn_bng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_bng();

            }
        });

        btn_eng = (Button) findViewById(R.id.main_btn_eng);
        btn_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_eng();
            }
        });


        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "189005963057567_189884996302997", AdSize.BANNER_HEIGHT_50);

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
                        MainActivity.this,
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

    private void showLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if (location!= null){
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        textViewLatt.setText("Latitude "+addressList.get(0).getLatitude());
                        textViewLong.setText("Longitude "+addressList.get(0).getLongitude());
                        textViewAdd.setText(addressList.get(0).getAddressLine(0));
                        textViewLoc.setText(addressList.get(0).getLocality());
                        textViewCountry.setText(addressList.get(0).getCountryName());
                        textViewPostalCode.setText("Current Postal Code: "+addressList.get(0).getPostalCode());

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Location null error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void openActivity_bng() {
        Intent intent_bng = new Intent(this, MainActivity_bng.class);
        startActivity(intent_bng);
    }

    public void openActivity_eng() {
        Intent intent_eng = new Intent(this, MainActivity_eng.class);
        startActivity(intent_eng);
    }

    //Rate me button
    public void rateMe(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.xdarssoftco.hotlinebd")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http//play.google.com/store/apps/details?=" + getPackageName())));
        }
    }

    //share it method
    public void shareit(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String value = "https://play.google.com/store/apps/details?id=" + PACKAGE_NAME;
        intent.putExtra(Intent.EXTRA_TEXT, value);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    //About us button
    public void aboutUs(View v) {
        Intent intent_aboutUs = new Intent(this, AboutUs.class);
        startActivity(intent_aboutUs);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();


    }


}