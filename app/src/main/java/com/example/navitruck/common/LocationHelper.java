package com.example.navitruck.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

public class LocationHelper {
    private Geocoder geocoder;

    double longtitude = 0.0;
    double latitude = 0.0;

    public LocationHelper(Context context) {
        geocoder = new Geocoder(context, Locale.US);
        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        Location gpsLoc, networkLoc, finalLoc=null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        try{
            gpsLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            networkLoc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(gpsLoc!=null)
                finalLoc = gpsLoc;
            else if(networkLoc!=null)
                finalLoc = networkLoc;

            if(finalLoc!=null){
                longtitude = finalLoc.getLongitude();
                latitude = finalLoc.getLatitude();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public String getZip() {
        String zipCode = "Fuck";
        if(geocoder!=null){
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longtitude, 1);
                if(addresses!=null && addresses.size()>0)
                    zipCode = addresses.get(0).getPostalCode();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return zipCode;
    }
}
