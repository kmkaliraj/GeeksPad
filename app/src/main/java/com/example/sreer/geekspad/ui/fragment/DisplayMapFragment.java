package com.example.sreer.geekspad.ui.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.db.FireBaseHelper;
import com.example.sreer.geekspad.model.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;

/**
 * Created by kalirajkalimuthu on 3/18/17.
 */

public class DisplayMapFragment extends Fragment implements OnMapReadyCallback,FireBaseHelper.GetAllUsersInterface {

    private List<User> usersList = new ArrayList<User>();
    private FireBaseHelper fireBaseHelper;

    private GoogleMap mMap;

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }


    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
       fireBaseHelper = new FireBaseHelper(DisplayMapFragment.this);
        MapView map = (MapView) view.findViewById(R.id.map);
        map.onCreate(bundle);
        map.onResume();
        map.getMapAsync(this);

        getActivity().setTitle("Users Map View");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_map_fragment, container, false);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fireBaseHelper.getAllUsers();

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
           Location myLocation = mMap.getMyLocation();
           LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                   myLocation.getLongitude());
           CameraPosition myPosition = new CameraPosition.Builder()
                   .target(myLatLng).build();
           googleMap.animateCamera(
                   CameraUpdateFactory.newCameraPosition(myPosition));
        }

    public void addMarker(User user){
        LatLng point = new LatLng(parseFloat(user.getLatitude()),parseFloat(user.getLatitude()));
        MarkerOptions marker = new MarkerOptions() .position(point);
        mMap.addMarker(marker.title(user.getFirstname()));
        CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(point, 6);
        mMap.moveCamera(newLocation);

    }

    public void markUsers(List<User> users){
        mMap.clear();
        for(User user: users){
            addMarker(user);
        }
    }

    @Override
    public void onSuccessGetAllUsers(List<User> users) {
       usersList = users;
        markUsers(usersList);
    }

    @Override
    public void onFailureGetAllUsers() {

    }
}