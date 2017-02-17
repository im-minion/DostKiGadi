package com.example.vinayak.mapsapi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;   //from GoogleApiClient
    private Location mLastLocation;     //
    TextView mLatitudeText;
    TextView mLongitudeText;
    private Geocoder geocoder;          //geocoder for gettng the addres from latitude and longitude
    List<Address> addresses;            //for storing the addresslines street city state
    Double lati, longi;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLongitudeText = (TextView) findViewById(R.id.textView3);

        geocoder = new Geocoder(this, Locale.getDefault());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            //Toast.makeText(MainActivity.this, "Latitude" + mLastLocation.getLatitude(), Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this, "Latitude" + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
            lati = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();
            //String link="https://maps.googleapis.com/maps/api/geocode/json?latlng="+lati+","+longi+"&key=AIzaSyAFzdySfoQ5vaZ8tnVCOoAYSApUhf0hP7I";

            try {
                addresses = geocoder.getFromLocation(lati, longi, 2);//passing lati and longi (double type) values to get adress
            } catch (IOException e) {
                e.printStackTrace();
            }
            String address = addresses.get(0).getAddressLine(0);//get the street
            String city = addresses.get(0).getLocality();       //get city name
            String state = addresses.get(0).getAdminArea();     //get state name

            location = address + " " + city + " " + state; //this will conacat street, city and state

            Toast.makeText(MainActivity.this,"Location is:"+location,Toast.LENGTH_SHORT).show();
            mLongitudeText.setText(location);


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void shareLocation(View view) {
        //give the intent to gmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));                           // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Location");           //subject to gmail
        intent.putExtra(Intent.EXTRA_TEXT, "My Location is :" + location);//message content to gmail
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void newActivity(View view) {

        startActivity(new Intent(MainActivity.this, BookActivity.class));

    }
}
