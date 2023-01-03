package com.example.plan.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plan.Modal.Note;
import com.example.plan.R;
import com.example.plan.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddFragment extends Fragment {

    ImageView water1,water2,water3,water4,water5,
            happy,sad,normal,baseline;
    TextView num1,num2,num3,num4,num5;
    EditText ETThanks,ETGrng,ETArk,ETTb, ETRotin,subject;
    RelativeLayout first,seconde,therd;
    Button next,preview,next1,preview1,finsh;
    int[] a = {R.drawable.waterblack, R.drawable.watermor};
    int[] colorText = {R.drawable.bg_text, R.drawable.bg_select_text};
    int[] black = {R.drawable.ic_happy_black, R.drawable.ic_sad_black,
            R.drawable.ic_baseline_black, R.drawable.ic_normal_black};
    int[] mor = {R.drawable.ic_happy_mor, R.drawable.ic_sad_mor,
            R.drawable.ic_baseline_mor, R.drawable.ic_normal_mor};
    String health = "2";
    String today = "1";
    String smile = "کەیف خوشم";

    String noteid;
    String userid;

    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();

        water1 = view.findViewById(R.id.water1);
        water2 = view.findViewById(R.id.water2);
        water3 = view.findViewById(R.id.water3);
        water4 = view.findViewById(R.id.water4);
        water5 = view.findViewById(R.id.water5);
        happy = view.findViewById(R.id.happy);
        sad = view.findViewById(R.id.sad);
        normal = view.findViewById(R.id.normal);
        baseline = view.findViewById(R.id.baseline);
        num1 = view.findViewById(R.id.num1);
        num2 = view.findViewById(R.id.num2);
        num3 = view.findViewById(R.id.num3);
        num4 = view.findViewById(R.id.num4);
        num5 = view.findViewById(R.id.num5);
        first = view.findViewById(R.id.first);
        seconde = view.findViewById(R.id.seconde);
        therd = view.findViewById(R.id.therd);
        preview = view.findViewById(R.id.preview);
        preview1 = view.findViewById(R.id.preview1);
        next = view.findViewById(R.id.next);
        next1 = view.findViewById(R.id.next1);
        finsh = view.findViewById(R.id.finsh);
        ETThanks = view.findViewById(R.id.ETThanks);
        ETArk = view.findViewById(R.id.ETArk);
        ETGrng = view.findViewById(R.id.ETGrng);
        ETTb = view.findViewById(R.id.ETTb);
        ETRotin = view.findViewById(R.id.ETRotin);
        subject = view.findViewById(R.id.subject);
        // if click next1 visibilioty preview 2


        seconde.setVisibility(View.GONE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ETThanks.getText().toString().isEmpty() ||
                        subject.getText().toString().isEmpty() ||
                        subject.getText().toString().length() > 100){
                    Toast.makeText(getContext(), "تکایە خانەکان پر بکەوە", Toast.LENGTH_SHORT).show();
                } else {
                    preview.setVisibility(View.VISIBLE);
                    seconde.setVisibility(View.VISIBLE);
                    next1.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    first.setVisibility(View.GONE);
                }
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                first.setVisibility(View.VISIBLE);
                seconde.setVisibility(View.GONE);
            }
        });
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ETGrng.getText().toString().isEmpty() ||
                ETArk.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "تکایە خانەکان پر بکەوە", Toast.LENGTH_SHORT).show();
                } else {
                    seconde.setVisibility(View.GONE);
                    therd.setVisibility(View.VISIBLE);
                    next1.setVisibility(View.GONE);
                    finsh.setVisibility(View.VISIBLE);
                    preview.setVisibility(View.GONE);
                    preview1.setVisibility(View.VISIBLE);
                }
            }
        });
        preview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                therd.setVisibility(View.GONE);
                seconde.setVisibility(View.VISIBLE);
                finsh.setVisibility(View.GONE);
                next1.setVisibility(View.VISIBLE);
                preview1.setVisibility(View.GONE);
                preview.setVisibility(View.VISIBLE);
            }
        });

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1.setBackgroundResource(colorText[1]);
                num2.setBackgroundResource(colorText[0]);
                num3.setBackgroundResource(colorText[0]);
                num4.setBackgroundResource(colorText[0]);
                num5.setBackgroundResource(colorText[0]);
                today = "1";
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1.setBackgroundResource(colorText[0]);
                num2.setBackgroundResource(colorText[1]);
                num3.setBackgroundResource(colorText[0]);
                num4.setBackgroundResource(colorText[0]);
                num5.setBackgroundResource(colorText[0]);
                today = "2";
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1.setBackgroundResource(colorText[0]);
                num2.setBackgroundResource(colorText[0]);
                num3.setBackgroundResource(colorText[1]);
                num4.setBackgroundResource(colorText[0]);
                num5.setBackgroundResource(colorText[0]);
                today = "3";
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1.setBackgroundResource(colorText[0]);
                num2.setBackgroundResource(colorText[0]);
                num3.setBackgroundResource(colorText[0]);
                num4.setBackgroundResource(colorText[1]);
                num5.setBackgroundResource(colorText[0]);
                today = "4";
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1.setBackgroundResource(colorText[0]);
                num2.setBackgroundResource(colorText[0]);
                num3.setBackgroundResource(colorText[0]);
                num4.setBackgroundResource(colorText[0]);
                num5.setBackgroundResource(colorText[1]);
                today = "5";
            }
        });

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happy.setImageResource(mor[0]);
                sad.setImageResource(black[1]);
                baseline.setImageResource(black[2]);
                normal.setImageResource(black[3]);
                smile = "کەیف خوشم";
            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happy.setImageResource(black[0]);
                sad.setImageResource(mor[1]);
                baseline.setImageResource(black[2]);
                normal.setImageResource(black[3]);
                smile = "خەمگینم";
            }
        });
        baseline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happy.setImageResource(black[0]);
                sad.setImageResource(black[1]);
                baseline.setImageResource(mor[2]);
                normal.setImageResource(black[3]);
                smile = "مەندەهۆشم";
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happy.setImageResource(black[0]);
                sad.setImageResource(black[1]);
                baseline.setImageResource(black[2]);
                normal.setImageResource(mor[3]);
                smile = "ئاسایى";
            }
        });


        water1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water1.setImageResource(a[1]);
                water2.setImageResource(a[0]);
                water3.setImageResource(a[0]);
                water4.setImageResource(a[0]);
                water5.setImageResource(a[0]);
                health = "2";
            }
        });
        water2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water1.setImageResource(a[1]);
                water2.setImageResource(a[1]);
                water3.setImageResource(a[0]);
                water4.setImageResource(a[0]);
                water5.setImageResource(a[0]);
                health = "4";
            }
        });
        water3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water2.setImageResource(a[1]);
                water3.setImageResource(a[1]);
                water4.setImageResource(a[0]);
                water5.setImageResource(a[0]);
                health = "6";
            }
        });
        water4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water2.setImageResource(a[1]);
                water3.setImageResource(a[1]);
                water4.setImageResource(a[1]);
                water5.setImageResource(a[0]);
                health = "8";
                Toast.makeText(getContext(), health, Toast.LENGTH_SHORT).show();
            }
        });
        water5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water2.setImageResource(a[1]);
                water3.setImageResource(a[1]);
                water4.setImageResource(a[1]);
                water5.setImageResource(a[1]);
                health = "10";
            }
        });

        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ETRotin.getText().toString().isEmpty() ||
                ETTb.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "تکایە خانەکان پر بکەوە", Toast.LENGTH_SHORT).show();
                else {
                    String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Note")
                            .child(myid);

                    String noteid = reference.push().getKey();

                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("noteid", noteid);
                    hashMap.put("userid", myid);
                    hashMap.put("subject", subject.getText().toString());
                    hashMap.put("face" , smile);
                    hashMap.put("health", health);
                    hashMap.put("thanks", ETThanks.getText().toString());
                    hashMap.put("today", today);
                    hashMap.put("importand", ETGrng.getText().toString());
                    hashMap.put("ark", ETArk.getText().toString());
                    hashMap.put("rotin", ETRotin.getText().toString());
                    hashMap.put("tb", ETTb.getText().toString());
                    hashMap.put("date", currentDate);


                    reference.child(noteid).setValue(hashMap);




                    health = "2";
                    today = "1";
                    smile = "کەیف خوشم";
                    ETTb.setText("");
                    ETRotin.setText("");
                    ETArk.setText("");
                    ETThanks.setText("");
                    ETGrng.setText("");
                    subject.setText("");
                    happy.setImageResource(mor[0]);
                    sad.setImageResource(black[1]);
                    baseline.setImageResource(black[2]);
                    normal.setImageResource(black[3]);
                    num1.setBackgroundResource(colorText[1]);
                    num2.setBackgroundResource(colorText[0]);
                    num3.setBackgroundResource(colorText[0]);
                    num4.setBackgroundResource(colorText[0]);
                    num5.setBackgroundResource(colorText[0]);
                    water1.setImageResource(a[1]);
                    water2.setImageResource(a[0]);
                    water3.setImageResource(a[0]);
                    water4.setImageResource(a[0]);
                    water5.setImageResource(a[0]);
                    therd.setVisibility(View.GONE);
                    finsh.setVisibility(View.GONE);
                    preview1.setVisibility(View.GONE);
                    first.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);



                }
            }
        });

        return view;

    }






}



