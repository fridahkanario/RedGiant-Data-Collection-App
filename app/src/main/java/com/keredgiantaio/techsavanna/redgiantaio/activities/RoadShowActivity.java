package com.keredgiantaio.techsavanna.redgiantaio.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.keredgiantaio.techsavanna.redgiantaio.R;
import com.keredgiantaio.techsavanna.redgiantaio.app.AppConfig;
import com.keredgiantaio.techsavanna.redgiantaio.helpers.ApiRoadShowClient;
import com.keredgiantaio.techsavanna.redgiantaio.helpers.ApiRoadShowService;
import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.keredgiantaio.techsavanna.redgiantaio.methods.RoadShowResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

// for BAs who are doing road shows
public class RoadShowActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {
    private Spinner routes;
    EditText merchandise, crowdsize, comments, product_focus, outletname, baname;;
    String routess;
    List<String> list;
    String question;
    Button btnlogin;
    private ProgressDialog pDialog;

    String lat, lon;

    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadshow);

        merchandise = findViewById(R.id.input_merchandise);
        crowdsize = findViewById(R.id.input_crowdsize);
        comments = findViewById(R.id.input_comments);
        btnlogin = findViewById(R.id.btn_login);
        routes = findViewById(R.id.route);
        product_focus = findViewById(R.id.product_of_focus);
        outletname = findViewById(R.id.input_outlet_name);
        baname = findViewById(R.id.input_ba_name);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
        getRoutes();
        routes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                routess = parent.getItemAtPosition(position).toString();

                System.out.println("Data selected" + routess);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }


    private void getRoutes() {
        StringRequest stringRequest = new StringRequest(AppConfig.DATA_URL + "readroutes.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("print sasa");

                        try {
                            //Parsing the fetched Json String to JSON Object

                            JSONObject jObj = new JSONObject(response);
                            //  JSONObject responses = jObj.getJSONObject("");
                            System.out.println("print sasa" + response);
                            JSONArray jr = jObj.getJSONArray("records");
                            list = new ArrayList<String>();
                            for (int i = 0; i < jr.length(); i++) {
                                JSONObject jb1 = jr.getJSONObject(i);

                                question = jb1.getString("route_name");


                                // spinner.setAdapter(new ArrayAdapter<String>(StockTakeClass.this, android.R.layout.simple_spinner_dropdown_item, Collections.singletonList(question)));
                                //getProducts(question);

                                list.add(question);


                            }
                            System.out.println("Data Response: " + list);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RoadShowActivity.this,
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            routes.setAdapter(dataAdapter);

//                            String text = spinner.getSelectedItem().toString();
//
//                            System.out.println("selected item:"+text);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error sasa" + error);
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @SuppressLint("RestrictedApi")
    private void sendData() {
//
        pDialog = new ProgressDialog(RoadShowActivity.this,
                R.style.Theme_AppCompat_DayNight_DarkActionBar);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sending data...");
        pDialog.setCancelable(false);
        showpDialog();
        // Permissions ok, we get last location
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
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            //  locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());

            lat = Double.toString(location.getLatitude());
            lon = Double.toString(location.getLongitude());
            final String banames = baname.getText().toString();
            final String outletnames = outletname.getText().toString();
            final String merchandisee = merchandise.getText().toString();
            final String crowdsizee = crowdsize.getText().toString();
            String commentss = comments.getText().toString();
            String routie = routess;
            String product_focus = this.product_focus.getText().toString();
            String campaign = "Road Show";

            SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
            String id_user  = prefs.getString("telephone", "UNKNOWN");


            ApiRoadShowService service = ApiRoadShowClient.getClient().create(ApiRoadShowService.class);
            //User user = new User(name, email, password);


            Call<RoadShowResponse> userCall = service.sendRegister(banames,outletnames,merchandisee, crowdsizee, commentss, routie, product_focus, campaign, id_user,lat, lon);

            System.out.println("data outing" + merchandisee + " " + crowdsizee + " " + commentss + " " + " " + routie + " " + lat + " " + lon);
            userCall.enqueue(new Callback<RoadShowResponse>() {
                @Override
                public void onResponse(Call<RoadShowResponse> call, retrofit2.Response<RoadShowResponse> response) {
                    // hidepDialog();
                    //onSignupSuccess();

                    Log.d("onResponse", "" + response.body().getMessage());

                    String res = response.body().getMessage();

                    System.out.println("data result" + response.body().getSuccess());

                    if (response.body().getSuccess() == 1) {
                        Toast.makeText(RoadShowActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(RoadShowActivity.this, R.style.MyDialogTheme);

                        builder.setTitle("Confirmation");
                        builder.setIcon(R.drawable.resized);
                        builder.setMessage("Saved successfully");
                        builder.setPositiveButton(Html.fromHtml("<font color='orange'>Ok</font>"),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent intent = new Intent(RoadShowActivity.this, DashboardActivity.class);
//                                    intent.putExtra("nameofperson",nameofpersonn);
//                                    intent.putExtra("telephone",telephonee);
                                        startActivity(intent);

                                    }
                                });
                        builder.create().show();

                    } else {
                        Toast.makeText(RoadShowActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RoadShowResponse> call, Throwable t) {
                    hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            //locationTv.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            //  locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }

        startLocationUpdates();
    }

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            // locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(RoadShowActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }

}
