package com.example.lastprojectandroid;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Time> {
    private int selectedPosition = -1;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Time> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.appointment_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        String dateString = dateFormat.format(getItem(position).getDate());
        viewHolder.appointmentDateTextView.setText(dateString);
        viewHolder.radioButton.setTag(position);
        viewHolder.radioButton.setChecked(position == selectedPosition);
        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = (int) v.getTag();
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private static class ViewHolder {
        TextView appointmentDateTextView;
        RadioButton radioButton;

        ViewHolder(View view) {
            appointmentDateTextView = view.findViewById(R.id.appointmentDateTextView);

            radioButton = view.findViewById(R.id.radioButton2);
        }
    }
    public int getSelectedPosition(){
        return selectedPosition;
    }
    public void clearSelection() {
        selectedPosition = -1;
    }
}