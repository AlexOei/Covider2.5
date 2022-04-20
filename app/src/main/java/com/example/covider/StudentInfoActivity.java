package com.example.covider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.databinding.ActivityStudentInfoBinding;
import com.example.covider.model.Health;
import com.example.covider.model.NotificationMessage;
import com.example.covider.model.Section;
import com.example.covider.model.User;
import com.example.covider.utils.TimeUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentInfoActivity extends AppCompatActivity {
    ActivityStudentInfoBinding binding;
    Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        section = (Section) getIntent().getSerializableExtra("section");
        if (section == null){
            throw new IllegalArgumentException("section must be not null");
        }

        requestUsers(1);
    }

    /**
     * @param type day 1 week 2 month 3
     */
    private void requestUsers(int type) {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        User user = child.getValue(User.class);
                        user.setKey(child.getKey());
                        users.add(user);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                users = users.stream().filter(user -> {
                    List<Section> sections = user.getShould_visit();
                    if (sections == null || user.isInstructor()) return false;
                    sections = sections.stream().filter(section1 ->
                            section1.getSectionNum().equals(section.getSectionNum()) && section1.getBuilding().equals(section.getBuilding())
                    ).collect(Collectors.toList());
                    return sections.size() > 0;
                }).collect(Collectors.toList());
                MyAdapter adapter = new MyAdapter(users, type);
                binding.listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkedHandler(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.day:
                    requestUsers(1);
                    break;
                case R.id.week:
                    requestUsers(2);
                    break;
                case R.id.month:
                    requestUsers(3);
                    break;
            }
        }
    }

    static class MyAdapter extends BaseAdapter {
        List<User> data;
        int type;

        public MyAdapter(List<User> data, int type) {
            this.data = data;
            this.type = type;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public User getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder(parent.getContext());
                convertView = holder.rootView;
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.update(getItem(position), type);
            return convertView;
        }

        static class Holder {
            TextView section;
            TextView code;
            Button viewStudent;
            Button viewNotification;

            View rootView;

            public Holder(Context context) {
                rootView = View.inflate(context, R.layout.item_student_info, null);
                this.section = rootView.findViewById(R.id.item_section);
                this.code = rootView.findViewById(R.id.item_code);
                this.viewStudent = rootView.findViewById(R.id.view_student);
                this.viewNotification = rootView.findViewById(R.id.view_notification);
            }

            void update(User user, int type) {
                this.section.setText(user.getLastName());
                this.code.setText(user.getEmail());
                this.viewNotification.setOnClickListener(v -> showDialot(v.getContext(),user));
                List<Health> healthList = user.getHealth_history();
                if (healthList == null || healthList.size() == 0) return;
                Health health;
                int count = 0;
                switch (type) {
                    case 1:
                        health = healthList.get(healthList.size() - 1);
                        boolean today = TimeUtil.today(health.getTime());
                        viewStudent.setText(String.format("Covid: %s", today ? String.valueOf(health.isStatus()) : "None"));
                        break;
                    case 2:
                        int weekDays = 0;
                        for (int i = healthList.size() - 1; i >= 0; i--) {
                            health = healthList.get(i);
                            if (TimeUtil.sameWeek(health.getTime())) {
                                weekDays++;
                                count += health.isStatus() ? 1 : -1;
                            } else {
                                break;
                            }
                        }
                        viewStudent.setText(String.format("Covid: %s", weekDays > 0 ? count > 0 ? "true" : "false" : "None"));
                        break;
                    case 3:

                        int monthDays = 0;
                        for (int i = healthList.size() - 1; i >= 0; i--) {
                            health = healthList.get(i);
                            if (TimeUtil.sameMonth(health.getTime())) {
                                monthDays++;
                                count += health.isStatus() ? 1 : -1;
                            } else {
                                break;
                            }
                        }
                        viewStudent.setText(String.format("Covid: %s", monthDays > 0 ? count > 0 ? "true" : "false" : "None"));
                        break;
                }
            }


            public void showDialot(@NonNull Context context,@NonNull User user){
                new AlertDialog.Builder(context)
                        .setTitle("Notification")
                        .setMessage("Class Method")
                        .setNegativeButton("offline", (dialog, which) -> sendNotification(context,user.getKey(),new NotificationMessage(System.currentTimeMillis(),"Class Notice","offline class")))
                        .setPositiveButton("online", (dialog, which) -> sendNotification(context,user.getKey(),new NotificationMessage(System.currentTimeMillis(),"Class Notice","online class")))
                        .create()
                        .show();
            }

            public void sendNotification(@NonNull Context context,@NonNull String userKey,@NonNull NotificationMessage message){
                Task<Void> task = FirebaseDatabase.getInstance().getReference("Users")
                        .child(userKey)
                        .child("message")
                        .setValue(message);
                task.addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Toast.makeText(context, "Notification successfully!", Toast.LENGTH_LONG ).show();
                    }else{
                        Toast.makeText(context, "Failed to Notification!", Toast.LENGTH_LONG ).show();
                    }
                });
            }
        }
    }
}
