package com.thirdi.sensorsupervisor;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps extends FragmentActivity{
	private GoogleMap gMaps;
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maps);
	     if (gMaps == null) {
	        gMaps = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.haritafragment))
	                .getMap();
	        
	        if (gMaps != null) {
	        	gMaps.setMyLocationEnabled(true); 
	        	Location userLocation = gMaps.getMyLocation();
	            LatLng myLocation = null;
	            if (userLocation != null) {
	                myLocation = new LatLng(userLocation.getLatitude(),
	                        userLocation.getLongitude());
	                gMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
	                        gMaps.getMaxZoomLevel()-5));
	        	    
				gMaps.getUiSettings().setMyLocationButtonEnabled(false);
	        }
	    }

	}
	}
}

