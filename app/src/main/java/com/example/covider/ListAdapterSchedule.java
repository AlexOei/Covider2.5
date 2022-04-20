package com.example.covider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covider.model.Building;

import java.util.ArrayList;

public class ListAdapterSchedule extends ArrayAdapter<Building> {

    public ListAdapterSchedule(Context context, ArrayList<Building> buildingArrayList){

        super(context,R.layout.list_building,buildingArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Building building = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_building,parent,false);

        }

        TextView risk = convertView.findViewById(R.id.risk);
        TextView name = convertView.findViewById(R.id.name);
        TextView freq = convertView.findViewById(R.id.freqorSched);

        name.setText(building.getName());
        risk.setText("Covid Risk: " + building.getRisk()+"%");
        freq.setText("Scheduled Location");




        return convertView;
    }
}
