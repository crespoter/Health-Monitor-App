package com.crespoter.healthangel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class PatientViewerAdapter extends RecyclerView.Adapter<PatientViewerAdapter.MyViewHolder> {

    private ArrayList<Patient> patients;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patient_list_name);
            image = (ImageView) itemView.findViewById(R.id.patient_dp);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
    }
    public PatientViewerAdapter(ArrayList<Patient> patients,Context context)
    {
        this.patients = patients;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(patients.get(position).name);

        try {

            URL url = new URL(NetworkDetails.getPatientImage+"?id="+patients.get(position).id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
            holder.image.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PatientInfo.class);
                intent.putExtra("id",patients.get(position).id);
                intent.putExtra("name",patients.get(position).name);
                intent.putExtra("age",patients.get(position).age);
                intent.putExtra("sex",patients.get(position).sex);
                intent.putExtra("temperature",patients.get(position).temperature);
                intent.putExtra("pulse",patients.get(position).pulse);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


}
