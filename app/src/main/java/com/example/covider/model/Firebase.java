package com.example.covider.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covider.BuildingActivity;
import com.example.covider.ListAdapter;
import com.example.covider.ListAdapterSchedule;
import com.example.covider.databinding.ActivityListBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Firebase {



    public DatabaseReference getUserReference(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        return ref;

    };

    public DatabaseReference getFreq(String location){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(location);

        return ref;

    };

    public DatabaseReference getSched(String location){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(location);

        return ref;

    };



    public void getUser(TextView first, TextView last, TextView email, TextView haveCovid, TextView instructor){

        DatabaseReference ref = getUserReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                first.setText(user.getFirstName());
                last.setText(user.getLastName());
                email.setText(user.getEmail());
                String covid = new Boolean(user.isHaveCovid()).toString();
                String instruct = new Boolean (user.isInstructor()).toString();
                haveCovid.setText(covid);
                instructor.setText(instruct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    };

    public void getList(ActivityListBinding binding, String string, ArrayList<Building> buildingArrayList, Context context){



        DatabaseReference ref = null;

        if (string.equals("freq_visited")){
            ref = getFreq(string);
        }


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dSnap: snapshot.getChildren()){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.getValue().toString());



                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Building build = snapshot.getValue(Building.class);
                            buildingArrayList.add(build);
                            if (string.equals("freq_visited")){
                                ListAdapter listAdapter = new ListAdapter(context, buildingArrayList);
                                binding.listview.setAdapter(listAdapter);
                                binding.listview.setClickable(true);
                                binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Intent i = new Intent(context, BuildingActivity.class);
                                        i.putExtra("name", buildingArrayList.get(position).getName());
                                        i.putExtra("risk", buildingArrayList.get(position).getRisk().toString());
                                        i.putExtra("code", buildingArrayList.get(position).getCode());
                                        i.putExtra("freqOrSched", "Frequently Visited");

                                        context.startActivity(i);

                                    }
                                });
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getListSched(ActivityListBinding binding, String string, ArrayList<Building> buildingArrayList, Context context){



        DatabaseReference ref = null;



        if (string.equals("should_visit")){
            ref = getSched(string);
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dSnap: snapshot.getChildren()){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.child("building").getValue().toString());



                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Building build = snapshot.getValue(Building.class);
                            buildingArrayList.add(build);

                            if (string.equals("should_visit")){
                                ListAdapterSchedule listAdapter = new ListAdapterSchedule(context, buildingArrayList);
                                binding.listview.setAdapter(listAdapter);
                                binding.listview.setClickable(true);
                                binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Intent i = new Intent(context, BuildingActivity.class);
                                        i.putExtra("name", buildingArrayList.get(position).getName());
                                        i.putExtra("risk", buildingArrayList.get(position).getRisk().toString());
                                        i.putExtra("code", buildingArrayList.get(position).getCode());
                                        i.putExtra("freqOrSched", "Scheduled Locations");
                                        context.startActivity(i);

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void getMapLocations(GoogleMap mMap, float bitmapDescriptorFactory, String string){

        DatabaseReference ref = getFreq(string);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dSnap: snapshot.getChildren()){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.getValue().toString());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Building build = snapshot.getValue(Building.class);
                            LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
                            mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet("Covid Risk: " + build.getRisk().toString()+ " Entry Reqs: See List View ").icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory)));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getMapLocationsSchedule(GoogleMap mMap, float bitmapDescriptorFactory, String string){

        DatabaseReference ref = getSched(string);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dSnap: snapshot.getChildren()){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buildings").child(dSnap.child("building").getValue().toString());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Building build = snapshot.getValue(Building.class);
                            LatLng ltln = new LatLng(Double.parseDouble(build.getLatitude()), Double.parseDouble(build.getLongitude()));
                            mMap.addMarker(new MarkerOptions().position(ltln).title(build.getName()).snippet("Covid Risk: " + build.getRisk().toString()+ " Entry Reqs: See List View ").icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory)));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






}
