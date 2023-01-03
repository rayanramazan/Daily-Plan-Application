package com.example.plan.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.plan.MainActivity;
import com.example.plan.Modal.User;
import com.example.plan.R;
import com.example.plan.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.github.muddz.styleabletoast.StyleableToast;

public class SettingFragment extends Fragment {

    TextView chPassword,chFullname,language,Logout;

    ProgressDialog pd;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        chPassword = view.findViewById(R.id.changePassword);
        chFullname = view.findViewById(R.id.changeName);
        Logout = view.findViewById(R.id.logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        chPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view1 = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
                builder.setView(view1);

                EditText oldPass,newPass;
                Button change,exit;

                exit = view1.findViewById(R.id.exit);
                change = view1.findViewById(R.id.changed);
                oldPass = view1.findViewById(R.id.oldPassword);
                newPass = view1.findViewById(R.id.newPassword);

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPassword = oldPass.getText().toString().trim();
                        String newPassword = newPass.getText().toString().trim();
                        if (oldPassword.isEmpty() || newPassword.isEmpty()){
                            StyleableToast.makeText(getContext(),"هیڤیدارین خانا پر بکە !", R.style.errorToast).show();
                        }
                        else if (newPassword.length() < 8){
                            StyleableToast.makeText(getContext(),"پەیڤا نهێنێ کێمتر ل ٨ پیتا ناچێبت !", R.style.errorToast).show();
                        }
                        else {
                            updatePass(oldPassword, newPassword);
                        }

                    }


                });
                alertDialog = builder.create();
                alertDialog = builder.show();
            }
        });

        chFullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view1 = getLayoutInflater().inflate(R.layout.dialog_change_name, null);
                builder.setView(view1);

                EditText fullname;
                Button change,exit;

                exit = view1.findViewById(R.id.exit);
                change = view1.findViewById(R.id.changed);
                fullname = view1.findViewById(R.id.username);
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        fullname.setText(user.getFullname());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateName(fullname.getText().toString());
                        alertDialog.dismiss();
                    }
                });


                alertDialog = builder.create();
                alertDialog = builder.show();
            }
        });




        return view;
    }

    private void updateName(String fullname) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        if(fullname.length() > 90){
            StyleableToast.makeText(getContext(),"دەربارە پتر ل ٤٠ پیتان دروست نینە", R.style.errorToast).show();
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("fullname", fullname);

            reference.updateChildren(hashMap);
            StyleableToast.makeText(getContext(),"زانیاریێن تە هاتنە گوهرین", R.style.successToast).show();
        }
    }

    private void updatePass(String oldPassword, String newPassword) {
        firebaseUser = firebaseAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
        firebaseUser.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firebaseUser.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        StyleableToast.makeText(getContext(),"وشەى نهێنى نوێ کراوە", R.style.successToast).show();
                                        alertDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        alertDialog.dismiss();
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StyleableToast.makeText(getContext(),"وشەى نهێنى ئێستا هەڵەیە", R.style.errorToast).show();
                    }
                });
    }
}