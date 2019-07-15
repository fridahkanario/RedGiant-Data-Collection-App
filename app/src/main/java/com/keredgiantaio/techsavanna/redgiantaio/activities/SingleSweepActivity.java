package com.keredgiantaio.techsavanna.redgiantaio.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.keredgiantaio.techsavanna.redgiantaio.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//To start project depending on the activity chosen
public class SingleSweepActivity extends AppCompatActivity  {
    Button startProject;

// private GoogleMap mMap;

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sweep);


//        date = findViewById(R.id.confirm_date);
//        time = findViewById(R.id.btn_start_Time);
//
//      // Show current date and Format
//        final Date currentdate = Calendar.getInstance().getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy ");
//        final String strDate = formatter.format(currentdate);


        //Show current time
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
//        final String strTime = dateFormat.format(currentTime);
//


//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(SingleSweepActivity.this, "Today is on " +strDate, Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(),"Start Time Is :" + strTime, Toast.LENGTH_LONG).show();
//                //Toast.makeText(SingleSweepActivity.this, "Today is on " +currentdate+strDate, Toast.LENGTH_LONG).show();
//
//            }
//        });

        startProject = findViewById(R.id.btn_start);


        startProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (names.equals("roadshow")) {
                        Intent intent1 = new Intent(SingleSweepActivity.this, RoadShowActivity.class);
                        startActivity(intent1);
                    }
                else if(names.equals("marketstorm")){
                    Intent intent2 = new Intent(SingleSweepActivity.this, MarketStormActivity.class);
                    startActivity(intent2);
                }
                else if(names.equals("instore")){
                    Intent intent2 = new Intent(SingleSweepActivity.this, InStoreActivity.class);
                    startActivity(intent2);
                }
                else if(names.equals("doortodoor")){
                    Intent intent2 = new Intent(SingleSweepActivity.this, DoorToDoorActivity.class);
                    startActivity(intent2);
                }
                else if (names.equals("merchandising")){
                    Intent intent3 = new Intent(SingleSweepActivity.this, MerchandisingActivity.class);
                    startActivity(intent3);
                } */


                Intent campaign = getIntent();
                String names = campaign.getStringExtra("STRUCTURE_NAME");

                switch (names) {
                    case "roadshow":
                        Intent intent_roadshow = new Intent(SingleSweepActivity.this, RoadShowActivity.class);
                        startActivity(intent_roadshow);
                        break;

                    case "marketstorm":
                        Intent intent_marketstorm = new Intent(SingleSweepActivity.this, MarketStormActivity.class);
                        startActivity(intent_marketstorm);
                        break;
                    case "instore":
                        Intent intent_instore = new Intent(SingleSweepActivity.this, InStoreActivity.class);
                        startActivity(intent_instore);
                        break;
                    case "doortodoor":
                        Intent intent_doortodoor = new Intent(SingleSweepActivity.this, DoorToDoorActivity.class);
                        startActivity(intent_doortodoor);
                        break;
                    case "merchandising":
                        Intent intent_merchandising = new Intent(SingleSweepActivity.this, MerchandisingActivity.class);
                        startActivity(intent_merchandising);
                        break;
                }
            }
        });
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//
//        //Show my location button
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//
//        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                //Do something with your location. You can use mMap.getMyLocation();
//                //anywhere in this class to get user location
//                Toast.makeText(SingleSweepActivity.this, String.format("%f : %f",
//                        mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()),
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//    }
    }

