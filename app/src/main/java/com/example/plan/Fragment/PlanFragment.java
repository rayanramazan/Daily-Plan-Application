package com.example.plan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plan.Adapter.NoteAdapter;
import com.example.plan.Modal.Note;
import com.example.plan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PlanFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    NoteAdapter noteAdapter;
    ArrayList<Note> notes;
    String myid;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);


//        myid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mAuth = FirebaseAuth.getInstance();
        myid = mAuth.getCurrentUser().getUid();


        recyclerView = view.findViewById(R.id.listplan);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(getContext(),notes);
        recyclerView.setAdapter(noteAdapter);

        getNote();


        return view;
    }

    private void getNote(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(myid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                        if (myid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            notes.add(note);
                        } else{
                            return;
                        }
                }
                Collections.reverse(notes);
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}