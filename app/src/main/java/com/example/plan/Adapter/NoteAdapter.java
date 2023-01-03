package com.example.plan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plan.DetailsActivity;
import com.example.plan.EditActivity;
import com.example.plan.Fragment.AddFragment;
import com.example.plan.Fragment.EditFragment;
import com.example.plan.Modal.Note;
import com.example.plan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    Context context;
    AlertDialog alertDialog;
    ArrayList<Note> list;
    FirebaseAuth auth;
    String userid;

    int[] img = {R.drawable.ic_happy_mor,R.drawable.ic_normal_mor,
                 R.drawable.ic_baseline_mor,R.drawable.ic_sad_mor};

    public NoteAdapter(Context context, ArrayList<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note note = list.get(position);

        String noteId = list.get(position).getNoteid();
        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();

        holder.date.setText(note.getDate());
        holder.txtface.setText(note.getFace());
        holder.tebini.setText(note.getImportand());

        if (note.getFace().equals("کەیف خوشم"))
            holder.imgSmile.setImageResource(img[0]);
        else if(note.getFace().equals("خەمگینم"))
            holder.imgSmile.setImageResource(img[3]);
        else if(note.getFace().equals("مەندەهۆشم"))
            holder.imgSmile.setImageResource(img[2]);
        else if(note.getFace().equals("ئاسایى"))
            holder.imgSmile.setImageResource(img[1]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("noteid", noteId);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_plan, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view1);

                Button delete,edit;

                edit = view1.findViewById(R.id.editBtn);
                delete = view1.findViewById(R.id.deleteBtn);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("noteid", noteId);
                        context.startActivity(intent);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Note")
                                .child(userid).child(noteId);

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().removeValue();
                                alertDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                alertDialog = builder.create();
                alertDialog = builder.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,tebini,txtface;
        ImageView imgSmile;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            date = itemView.findViewById(R.id.txtDate);
            tebini = itemView.findViewById(R.id.tebinii);
            txtface = itemView.findViewById(R.id.txtSmile);
            imgSmile = itemView.findViewById(R.id.imgSmile);
        }

    }
}
