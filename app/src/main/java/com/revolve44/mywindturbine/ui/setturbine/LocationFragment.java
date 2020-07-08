package com.revolve44.mywindturbine.ui.setturbine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.revolve44.mywindturbine.R;


import static android.content.Context.MODE_PRIVATE;

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    // Store instance variables
    private String title;
    private int page;

    private GoogleMap mMap;
    public LatLng lol;
    Marker marker;
    double latitude;
    double longitude;
    public Float NominalPower = 0.0f;

    public String Latitude;
    public String Longitude;

    Boolean check = false;

    LatLng MYLOCATION =  new LatLng(latitude, longitude);

    LinearLayout Loader;

    TextView textView;

    EditText inputnominalpower;


    boolean tempFahrenheit;
    CheckBox checkImperial;

    LocationActivity locact;


    // newInstance constructor for creating fragment with arguments
    public static LocationFragment newInstance(int page, String title) {
        LocationFragment fragmentFirst = new LocationFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");


    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlocation_map, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager() // *** change to childFM
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        return view;
    }



    ////////////////////
    /** ШАБЛОННЫЙ ГУГОЛОВСКИЙ КОМЕНТАРИЙ ПО ПОВОДУ ИХ КАРТ, РЕЛАКС. донт ворри
     * просто оставил почитать
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences sharedPreferences = ((LocationActivity) getActivity()).getSharedPreferences("MasterSave", MODE_PRIVATE);

        latitude = sharedPreferences.getFloat("latitudeF",(float)latitude);
        longitude = sharedPreferences.getFloat("longitudeF",(float)longitude);
        check = sharedPreferences.getBoolean("CHECK_SAVINGS",check);
        mMap = googleMap;


        if(check = true){
            LatLng resumedPosition = new LatLng(latitude,longitude);

            marker = googleMap.addMarker(new MarkerOptions()
                    .position(resumedPosition)
                    .draggable(true)
            );
            mMap.setOnMarkerDragListener(this); // bridge for connect marker with methods located below
            mMap.animateCamera(CameraUpdateFactory.newLatLng(resumedPosition)); // move camera to current position
        }else{
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(MYLOCATION)
                    .draggable(true)
            );
            mMap.setOnMarkerDragListener(this); // bridge for connect marker with methods located below
            mMap.animateCamera(CameraUpdateFactory.newLatLng(MYLOCATION)); // move camera to current position

        }

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //setUpTracking();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                // create marker
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");

                // adding marker
                mMap.addMarker(marker);

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                //Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                // get coord
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                Toast.makeText(getActivity(), ""+latitude+" "+longitude, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(getActivity(), "onMarkerDragStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(getActivity(), "onMarkerDrag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        MYLOCATION = marker.getPosition();
        textView.setText(lol+"");
        Toast.makeText(getActivity(), ""+lol, Toast.LENGTH_SHORT).show();
    }





    public void SaveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MasterSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        editor.putFloat("latitudeF",(float)latitude);
        editor.putFloat("longitudeF",(float)longitude);
        editor.putFloat("Nominal_Power",(float)NominalPower);

        editor.putBoolean("CHECK_SAVINGS",check);
        editor.putString("lati",Latitude);
        editor.putString("long",Longitude);

//        editor.putString(TEXT, textView.getText().toString());
//        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();// change from .commit()

        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

}
