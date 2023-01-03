package com.example.plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.plan.Adapter.DetailsAdapter;
import com.example.plan.Adapter.NoteAdapter;
import com.example.plan.Modal.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    String noteID;



    private RecyclerView recyclerView;
    private DetailsAdapter detailsAdapter;
    private List<Note> noteList;

    String myid;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        noteID = getIntent().getExtras().get("noteid").toString();

        mAuth = FirebaseAuth.getInstance();
        myid = mAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.listDetails);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(linearLayoutManager);

        noteList = new ArrayList<>();

        detailsAdapter = new DetailsAdapter(DetailsActivity.this , (ArrayList<Note>) noteList);
        recyclerView.setAdapter(detailsAdapter);

        readNote();


    }

    private void readNote() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Note").child(myid).child(noteID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList.clear();
                Note note = snapshot.getValue(Note.class);
                noteList.add(note);

                detailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}