package com.example.covider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.Section;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class InstructorNotification extends AppCompatActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_notification);

        listview = findViewById(R.id.listview);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("should_visit");

        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Section>> t = new GenericTypeIndicator<List<Section>>() {};
                List<Section> sectionCode = snapshot.getValue(t);
                if (sectionCode == null) return;
                MyAdapter adapter = new MyAdapter(sectionCode);
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    static class MyAdapter extends BaseAdapter{
        List<Section> data;

        public MyAdapter(List<Section> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Section getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if (convertView == null){
                holder = new Holder(parent.getContext());
                convertView = holder.rootView;
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }

            holder.update(getItem(position));
            return convertView;
        }

        static class Holder{
            TextView section;
            TextView code;
            Button viewStudent;

            View rootView;

            public Holder(Context context) {
                rootView = View.inflate(context,R.layout.item_notification,null);
                this.section = rootView.findViewById(R.id.item_section);
                this.code = rootView.findViewById(R.id.item_code);
                this.viewStudent = rootView.findViewById(R.id.view_student);
            }

            void update(Section section){
                this.section.setText(section.getSectionNum());
                this.code.setText(section.getBuilding());
                viewStudent.setOnClickListener(view -> {
                    Intent i = new Intent(view.getContext(), StudentInfoActivity.class);
                    i.putExtra("section",section);
                    view.getContext().startActivity(i);
                });
            }
        }
    }
}