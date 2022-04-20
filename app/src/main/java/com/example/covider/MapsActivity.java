package com.example.covider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.covider.databinding.ActivityMapsBinding;
import com.example.covider.model.Building;
import com.example.covider.model.Firebase;
import com.example.covider.model.NotificationMessage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ArrayList<Building> buildings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button locations, profile, healthCheck, checkIn, logOut, sections, schedule;
        locations = binding.locations;
        profile = binding.profile;
        healthCheck = binding.healthCheck;
        checkIn = binding.checkIn;
        logOut = binding.logOut;
        sections = binding.section;
        schedule = binding.sched;

        locations.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), ListActivity.class);
            startActivity(i);
        });

        schedule.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), ListActivitySchedule.class);
            startActivity(i);
        });

        profile.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), Profile.class);
            startActivity(i);
        });

        sections.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), ClassSectionActivity.class);
            startActivity(i);
        });

//        checkIn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick (View view){
//                Intent i = new Intent(view.getContext(), SafetyMeasures.class);
//                startActivity(i);
//            }
//        });

        healthCheck.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), CovidSymptoms.class);
            startActivity(i);
        });

        logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(view.getContext(), Login.class);
            startActivity(i);
        });


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ListActivity.class);
                startActivity(i);
            }
        });*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("message");
        df.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NotificationMessage message = snapshot.getValue(NotificationMessage.class);
                if (message == null) return;

                df.removeEventListener(this);
                df.setValue(null);
                df.addValueEventListener(this);

                sendNotification(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(@NonNull NotificationMessage message) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "covider_school";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getMessage())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Firebase firebase = new Firebase();

        firebase.getMapLocationsSchedule(mMap, BitmapDescriptorFactory.HUE_RED, "should_visit");

        firebase.getMapLocations(mMap, BitmapDescriptorFactory.HUE_BLUE, "freq_visited");
        // Add a marker in Sydney and move the camera
        /*String test = "79.4";
        String lol = String.format("A string %s", test);
        LatLng tommy = new LatLng(34.0203590393, -118.2853240967);
        mMap.addMarker(new MarkerOptions().position(tommy).title("tommy trojan").snippet(lol).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));*/

        /*try {
            this.buildings = readJsonStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child("freq_visited");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dSnap: snapshot.getChildren()){
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.getValue().toString());
//                    reference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Building build = snapshot.getValue(Building.class);
//                            LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
//                            mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet("Covid Risk: " + build.getRisk().toString()+ " Entry Reqs: See List View ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child("should_visit");
//
//        ref2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dSnap: snapshot.getChildren()){
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.getValue().toString());
//                    reference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Building build = snapshot.getValue(Building.class);
//                            LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
//                            mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet("Covid Risk: " + build.getRisk().toString()+ " Entry Reqs: See List View ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child("ABA");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Building build = snapshot.getValue(Building.class);
                    LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet(build.getCode()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        /*for (int i = 0; i < 1/*this.buildings.size(); i++ ){
            Building test = this.buildings.get(i);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Building build = snapshot.getValue(Building.class);
                        LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet(build.getCode()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            LatLng ltlng = new LatLng(Double.parseDouble(test.getLatitude()), Double.parseDouble(test.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(ltlng).title(test.getName()).snippet(test.getCode()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        }*/


        LatLng start =new LatLng (34.0216751099, -118.2854461670);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));



    }




}