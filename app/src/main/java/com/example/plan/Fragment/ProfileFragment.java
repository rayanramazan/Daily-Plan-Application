package com.example.plan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plan.Modal.Note;
import com.example.plan.Modal.User;
import com.example.plan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    ImageView imageProfile;
    TextView gender, age, numPlan, fullname;

    FirebaseAuth auth;


    String profileid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        profileid = auth.getCurrentUser().getUid();

        imageProfile = view.findViewById(R.id.imageProfile);
        gender = view.findViewById(R.id.gender);
        numPlan = view.findViewById(R.id.numPlan);
        age = view.findViewById(R.id.numAge);
        fullname = view.findViewById(R.id.fullname);


        userInfo();
        getNrPost();
        return view;
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null) {
                    return;
                }
                User user = snapshot.getValue(User.class);
                Glide.with(getContext()).load(user.getImageurl()).into(imageProfile);
                fullname.setText(user.getFullname());
                age.setText(user.getAge());
                gender.setText(user.getGender());
                numPlan.setText(user.getNumplan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getNrPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Note").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numPlan.setText("" + snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}