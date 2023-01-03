package com.example.plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView forgetPass, register;

    ProgressDialog pd;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // agar user login kri bit o account verifies kri bit bla eksar chita home activity
        if (firebaseUser != null && firebaseUser.isEmailVerified()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }

        email = findViewById(R.id.Etemail);
        password = findViewById(R.id.Etpassword);
        login = findViewById(R.id.btnLogin);
        forgetPass = findViewById(R.id.txtForgotPass);
        register = findViewById(R.id.txtRegister);

        auth = FirebaseAuth.getInstance();

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecoverPasswordDialog();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password))
                    StyleableToast.makeText(MainActivity.this, "زانیاریەکانت بنووسە", R.style.errorToast).show();
                else {
                    // alter progress loading
                    pd = new ProgressDialog(MainActivity.this);
                    pd.show();
                    pd.setContentView(R.layout.progress_dialog);
                    pd.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

                    auth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(auth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                FirebaseUser user = auth.getCurrentUser();
                                                if (user.isEmailVerified()){
                                                    pd.dismiss();
                                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    pd.dismiss();
                                                    StyleableToast.makeText(MainActivity.this, "تکایە هەژمارەکەت پشتڕاستبکەوە", R.style.errorToast).show();
                                                    auth.signOut();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();
                                            }
                                        });

                                    } else {
                                        pd.dismiss();
                                        StyleableToast.makeText(MainActivity.this, "دووبارە هەوڵبدە", R.style.errorToast).show();
                                    }
                                }
                            });
                }



            }
        });
    }

    private void RecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.dialog_recover_password, null);
        builder.setView(view);
        EditText emailRec;
        Button Reco,exit;

        emailRec = view.findViewById(R.id.RecEmail);
        Reco = view.findViewById(R.id.ok);
        exit = view.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Reco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailRec.getText().toString().trim();
                beginRecovery(email);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog = builder.show();
    }

    private void beginRecovery(String email) {
        pd = new ProgressDialog(MainActivity.this);
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    StyleableToast.makeText(MainActivity.this, "نامە بۆ هەژمارى ئیمەیلەکەت نێردرا", R.style.successToast).show();
                }
                else {
                    StyleableToast.makeText(MainActivity.this, "هەژمار بەم ناونیشانە نییە", R.style.errorToast).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                StyleableToast.makeText(MainActivity.this, "دووبارە هەوڵبدە", R.style.errorToast).show();
            }
        });
    }
}