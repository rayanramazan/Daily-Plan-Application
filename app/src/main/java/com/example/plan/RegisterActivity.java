package com.example.plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList<CustomItemSpinner> itemSpinners;
    String gender;

    EditText fullname,age,email,password;
    Button register;
    TextView txtLogin;
    ProgressDialog pd;

    AlertDialog dialog;
    int[] arrayImage = {R.drawable.man1, R.drawable.man2, R.drawable.man3};
    String[] img = new String[3];

    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.Etfullname);
        age = findViewById(R.id.EtAge);
        email = findViewById(R.id.EtEmail);
        password = findViewById(R.id.EtPassword);
        register = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        storageReference = FirebaseStorage.getInstance().getReference("UserImg");
        auth = FirebaseAuth.getInstance();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });




        spinner = findViewById(R.id.spinner);
        itemSpinners = getCustomList();
        CustomAdapterSpinner adapterSpinner = new CustomAdapterSpinner(this, itemSpinners);
        if (itemSpinners != null) {
            spinner.setAdapter(adapterSpinner);
            spinner.setOnItemSelectedListener(this);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fullname.getText().toString().isEmpty() && age.getText().toString().isEmpty() && gender == "رەگەز"
                && email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    StyleableToast.makeText(RegisterActivity.this, "زانیاریەکانت بنووسە", R.style.errorToast).show();
                }
                else if(fullname.getText().toString().isEmpty()){
                    StyleableToast.makeText(RegisterActivity.this, "ناوت بنووسە", R.style.errorToast).show();
                }
                else if(age.getText().toString().isEmpty()){
                    StyleableToast.makeText(RegisterActivity.this, "تەمەنت بنووسە", R.style.errorToast).show();
                }
                else if(email.getText().toString().isEmpty()){
                    StyleableToast.makeText(RegisterActivity.this, "ناونیشانى ئەڵێکترۆنى بنووسە", R.style.errorToast).show();
                }
                else if(password.getText().toString().isEmpty()){
                    StyleableToast.makeText(RegisterActivity.this, "وشەى نهێنى بنووسە", R.style.errorToast).show();
                }
                else if(password.getText().toString().length() < 8){
                    StyleableToast.makeText(RegisterActivity.this, "وشەى نهێنى کەمتر لە ٨ پیت نابێت", R.style.errorToast).show();
                }
                else if (gender == "رەگەز"){
                    StyleableToast.makeText(RegisterActivity.this, "رەگەزى خۆت دیارى بکە", R.style.errorToast).show();
                } else {
                    if (gender == "مێ") {
                        arrayImage[0] = R.drawable.woman1;
                        arrayImage[1] = R.drawable.woman2;
                        arrayImage[2] = R.drawable.woman3;
                        img[0] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fwoman1.png?alt=media&token=2212d20d-b0d9-409d-b48b-7bb39227c92c";
                        img[1] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fwoman2.png?alt=media&token=9ff5379f-dd16-4bd8-b52c-14ebb137cfb1";
                        img[2] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fwoman3.png?alt=media&token=0e01f93e-ff7d-4cd6-93ea-60cb4b1d4b9e";
                    } else if (gender == "نێر") {
                        arrayImage[0] = R.drawable.man1;
                        arrayImage[1] = R.drawable.man2;
                        arrayImage[2] = R.drawable.man3;
                        img[0] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fman1.png?alt=media&token=7c9b57d0-5514-4feb-87dd-95d24d68f315";
                        img[1] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fman2.png?alt=media&token=a76cd10b-b98e-45b5-97dd-e768bc4bbc00";
                        img[2] = "https://firebasestorage.googleapis.com/v0/b/planm-fd2de.appspot.com/o/userimg%2Fman3.png?alt=media&token=1542ba74-b1f9-44fb-8d74-222768a9e029";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                    View view1 = getLayoutInflater().inflate(R.layout.dialog_character, null);
                    builder.setView(view1);
                    ImageView imageuser, image1, image2, image3, imageUser;
                    Button okRegis;

                    imageuser = view1.findViewById(R.id.imageUser);
                    image1 = view1.findViewById(R.id.img1);
                    image2 = view1.findViewById(R.id.img2);
                    image3 = view1.findViewById(R.id.img3);
                    okRegis = view1.findViewById(R.id.btnOkRegister);

                    imageuser.setImageResource(arrayImage[0]);
                    image1.setImageResource(arrayImage[0]);
                    image2.setImageResource(arrayImage[1]);
                    image3.setImageResource(arrayImage[2]);

                    myUri = img[0];
                    image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myUri = img[0];
                            imageuser.setImageResource(arrayImage[0]);
                            image1.setBackgroundResource(R.drawable.bg_character_selected);
                            image2.setBackgroundResource(R.drawable.bg_character);
                            image3.setBackgroundResource(R.drawable.bg_character);
                        }
                    });
                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myUri = img[1];
                            imageuser.setImageResource(arrayImage[1]);
                            image1.setBackgroundResource(R.drawable.bg_character);
                            image2.setBackgroundResource(R.drawable.bg_character_selected);
                            image3.setBackgroundResource(R.drawable.bg_character);
                        }
                    });
                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myUri = img[2];
                            imageuser.setImageResource(arrayImage[2]);
                            image1.setBackgroundResource(R.drawable.bg_character);
                            image2.setBackgroundResource(R.drawable.bg_character);
                            image3.setBackgroundResource(R.drawable.bg_character_selected);
                        }
                    });

                    okRegis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                            pd = new ProgressDialog(RegisterActivity.this);
                            pd.show();
                            pd.setContentView(R.layout.progress_dialog);
                            pd.getWindow().setBackgroundDrawableResource(
                                    android.R.color.transparent
                            );


                            auth.createUserWithEmailAndPassword(email.getText().toString() , password.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                                String userid = firebaseUser.getUid();

                                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("id", userid);
                                                hashMap.put("fullname", fullname.getText().toString().toLowerCase());
                                                hashMap.put("age" , age.getText().toString());
                                                hashMap.put("gender", gender);
                                                hashMap.put("imageurl", myUri);

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            // فرێکرنا ناما پشتڕاستکرنێ
                                                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()){
                                                                        pd.dismiss();
                                                                        StyleableToast.makeText(RegisterActivity.this, "نامەى پشتڕاست کردن بۆ ئیمەیلەکەت نێردرا", R.style.successToast).show();
                                                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    } else {
                                                                        pd.dismiss();
                                                                        auth.signOut();
                                                                        StyleableToast.makeText(RegisterActivity.this, "دووبارە هەوڵبدە", R.style.errorToast).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            } else{
                                                pd.dismiss();
                                                StyleableToast.makeText(RegisterActivity.this, "ناتوانى بەم ناونیشانە خۆت تۆمار بکەى", R.style.errorToast).show();
                                            }
                                        }
                                    });
                            }

                    });
                    dialog = builder.create();
                    dialog = builder.show();

                }
            }
        });

    }

    private ArrayList<CustomItemSpinner> getCustomList() {
        itemSpinners = new ArrayList<>();
        itemSpinners.add(new CustomItemSpinner("رەگەز", R.drawable.equality));
        itemSpinners.add(new CustomItemSpinner("نێر", R.drawable.male));
        itemSpinners.add(new CustomItemSpinner("مێ", R.drawable.female));
        return itemSpinners;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CustomItemSpinner itemSpinner = (CustomItemSpinner) adapterView.getSelectedItem();
        gender = itemSpinner.getSpinnerItemName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}