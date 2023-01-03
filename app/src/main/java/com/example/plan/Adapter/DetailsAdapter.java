package com.example.plan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plan.DetailsActivity;
import com.example.plan.Modal.Note;
import com.example.plan.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Note> list;


    int[] img = {R.drawable.ic_happy_mor,R.drawable.ic_normal_mor,
            R.drawable.ic_baseline_mor,R.drawable.ic_sad_mor};

    public DetailsAdapter(Context context, ArrayList<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_details_plan, parent, false);
        return new DetailsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int valueProgress2 = 0;
        int valueProgress = 0;
        Note note = list.get(position);

        String noteId = list.get(position).getNoteid();

        holder.date.setText(note.getDate());
        holder.tebini.setText(note.getTb());
        holder.txtface.setText(note.getFace());
        holder.thanks.setText(note.getThanks());
        holder.grng.setText(note.getImportand());
        holder.ark.setText(note.getArk());
        holder.rotin.setText(note.getRotin());
        holder.subject.setText(note.getSubject());

//        holder.numHealth.setText(note.getHealth()+"/10");

//        holder.numToday.setText(note.getToday()+"/5");


        //ژمارە عەرز نابن ل رەن کرنێ دا

        if (note.getToday().equals("1")){
            valueProgress2 = 20;
            holder.txtToday.setText("رۆژەکەم زۆر باش نییە");
        } else if (note.getToday().equals("2")){
            valueProgress2 = 40;
            holder.txtToday.setText("رۆژەکەم باش نییە");
        } else if (note.getToday().equals("3")){
            valueProgress2 = 60;
            holder.txtToday.setText("رۆژەکەم پەسەندە");
        } else if (note.getToday().equals("4")){
            valueProgress2 = 80;
            holder.txtToday.setText("رۆژەکەم باشە");
        } else if (note.getToday().equals("5")){
            valueProgress2 = 100;
            holder.txtToday.setText("رۆژەکەم زۆر یاشە");
        }

        if (note.getHealth().equals("2")) {
            valueProgress = 20;
            holder.txtWater.setText("تەندروستیم ئالۆزە");
            holder.progressBar1.setProgress(valueProgress);
        }
        else if(note.getHealth().equals("4")) {
            valueProgress = 40;
            holder.txtWater.setText("تەندروستیم باش نییە");
            holder.progressBar1.setProgress(valueProgress);
        }
        else if (note.getHealth().equals("6")) {
            valueProgress = 60;
            holder.txtWater.setText("تەندروستیم باشە");
            holder.progressBar1.setProgress(valueProgress);
        }
        else if (note.getHealth().equals("8")) {
            valueProgress = 80;
            holder.txtWater.setText("تەندروستیم زۆر باشە");
            holder.progressBar1.setProgress(valueProgress);
        }
        else if (note.getHealth().equals("10")) {
            valueProgress = 100;
            holder.txtWater.setText("تەندروستیم نایابە");
            holder.progressBar1.setProgress(valueProgress);
        }

        holder.progressBar1.setProgress(valueProgress);
        holder.txtProgressNumHealth.setText(valueProgress + "%");


        holder.progressBar2.setProgress(valueProgress2);
        holder.txtProgressNumToday.setText(valueProgress2+"%");


        if (note.getFace().equals("کەیف خوشم"))
            holder.imgFace.setImageResource(img[0]);
        else if(note.getFace().equals("خەمگینم"))
            holder.imgFace.setImageResource(img[3]);
        else if(note.getFace().equals("مەندەهۆشم"))
            holder.imgFace.setImageResource(img[2]);
        else if(note.getFace().equals("ئاسایى"))
            holder.imgFace.setImageResource(img[1]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,numToday,thanks,grng,ark,rotin,
                tebini,txtface,txtProgressNumToday,txtWater
                ,txtToday,subject,txtProgressNumHealth;
        ProgressBar progressBar1,progressBar2;
        ImageView imgFace;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.txtDate);
            tebini = itemView.findViewById(R.id.txtTebini);
            txtface = itemView.findViewById(R.id.faceTxt);
            txtToday = itemView.findViewById(R.id.txtToday);
            txtWater = itemView.findViewById(R.id.txtWater);
            numToday = itemView.findViewById(R.id.txtProgressNumToday);
            thanks = itemView.findViewById(R.id.txtThanks);
            grng = itemView.findViewById(R.id.txtGrng);
            ark = itemView.findViewById(R.id.txtArk);
            rotin = itemView.findViewById(R.id.txtRotin);
            imgFace = itemView.findViewById(R.id.faceImg);
            progressBar1 = itemView.findViewById(R.id.progress1);
            progressBar2 = itemView.findViewById(R.id.progress2);
            txtProgressNumToday = itemView.findViewById(R.id.txtProgressNumToday);
            txtProgressNumHealth = itemView.findViewById(R.id.txtProgressNumHealth);

        }

    }
}
