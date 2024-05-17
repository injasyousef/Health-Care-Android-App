package com.example.lastprojectandroid;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private List<Appointment> appointments;

    private Student student;
    private Map<String,String> doctors=new HashMap<>();

    public AppointmentsAdapter(List<Appointment> appointments,Map<String,String> doctors) {
        this.appointments = appointments;
        this.doctors=doctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        View view=holder.itemView;
        TextView pills=view.findViewById(R.id.pills);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        String dateString = dateFormat.format(appointment.getDate());
        pills.setText(dateString);

        TextView doctorName=view.findViewById(R.id.txtmsedlab2);

        if(appointment.getDoctorID()==2){
            doctorName.setText("الطبيب العام");
        }else if(appointment.getDoctorID()==3){
            doctorName.setText("طبيب العيون");
        }else if(appointment.getDoctorID()==4){
            doctorName.setText("المختبر");
        }

        view.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), BookingDetailsPage.class);
                intent.putExtra("appintmentID", String.valueOf(appointment.getAppointmentID()));
                intent.putExtra("id2", String.valueOf(appointment.getStID()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void reomveItems(){
        appointments.clear();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDoctorName;
        TextView txtDateAndTime;
        ImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoctorName = itemView.findViewById(R.id.txtmsedlab2);
            txtDateAndTime = itemView.findViewById(R.id.pills);
            imgProfile = itemView.findViewById(R.id.profile_pic);
        }
    }

}
