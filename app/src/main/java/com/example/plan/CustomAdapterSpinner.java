package com.example.plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterSpinner extends ArrayAdapter<CustomItemSpinner> {

    public CustomAdapterSpinner(@NonNull Context context, ArrayList<CustomItemSpinner> customItemSpinners) {
        super(context, 0, customItemSpinners);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View covertView, @NonNull ViewGroup parent){
        if (covertView == null){
            covertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }
        CustomItemSpinner itemSpinner = getItem(position);
        ImageView spinnerIV = covertView.findViewById(R.id.iconSpinnerLayout);
        TextView spinnerTV = covertView.findViewById(R.id.txtSpinnerLayout);
        if (itemSpinner != null){
            spinnerIV.setImageResource(itemSpinner.getSpinerItemIcon());
            spinnerTV.setText(itemSpinner.getSpinnerItemName());
        }
        return covertView;
    }

    @Override
    public View getDropDownView(int postion, @NonNull View covertView, @NonNull ViewGroup parent){
        if (covertView == null){
            covertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_spinner, parent, false);
        }
        CustomItemSpinner itemSpinner = getItem(postion);
        ImageView dropDownIV = covertView.findViewById(R.id.iconDropdownLayout);
        TextView dropDownTV = covertView.findViewById(R.id.txtDropDownLayout);
        if (itemSpinner != null){
            dropDownIV.setImageResource(itemSpinner.getSpinerItemIcon());
            dropDownTV.setText(itemSpinner.getSpinnerItemName());
        }
        return covertView;
    }

}
