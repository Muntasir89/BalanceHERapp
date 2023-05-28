package com.example.balanceher.Dialog;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.balanceher.Interface.NoteInsUpdInterface;
import com.example.balanceher.R;
import com.example.balanceher.databinding.NoteInsertDiaLayBinding;
import com.example.balanceher.fragments.TimePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class NoteInsertDia extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    //View
    NoteInsertDiaLayBinding binding;
    private static String titleStr, desStr, timeStr="";
    private long milliseconds=0;
    public static Boolean isAdd;

    NoteInsUpdInterface noteDiaInterface;

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        noteDiaInterface = (NoteInsUpdInterface) context;
        this.context = context;
    }

    public NoteInsertDia(Boolean isadd){
        isAdd = isadd;
    }

    public static Boolean getIsAdd() {
        return isAdd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = requireActivity().getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.note_insert_dia_lay, null);

        binding = NoteInsertDiaLayBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(binding.getRoot());

        //Setting OnclickListener
        binding.imgClose.setOnClickListener(this);
        binding.addBtn.setOnClickListener(this);
        binding.timeDateTV.setOnClickListener(this);

        return dialogBuilder.create();
    }

    private void processAddNote() {
        titleStr = binding.titleET.getText().toString().trim();
        desStr = binding.descET.getText().toString().trim();
        if(TextUtils.isEmpty(titleStr) || TextUtils.isEmpty(desStr)){
            Toast.makeText(getContext(), "Some field is empty", Toast.LENGTH_SHORT).show();
        }else if(milliseconds==0){
            Toast.makeText(context, "Set alarm time", Toast.LENGTH_SHORT).show();
        }else{
            noteDiaInterface.getData(titleStr, desStr, milliseconds);
            dismiss();
        }
    }

    private void onTimeclicked() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),this, 0, 0, false);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //Converting the time to milliseconds
        milliseconds = c.getTimeInMillis();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        timeFormat.setTimeZone(TimeZone.getDefault());
        timeStr = timeFormat.format(new Date(milliseconds));

        //Setting timeDateTV
        binding.timeDateTV.setText(timeStr);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==binding.imgClose.getId()){
            dismiss();
        }else if(v.getId()==binding.addBtn.getId()){
            processAddNote();
        }else if(v.getId()==binding.timeDateTV.getId()){
            onTimeclicked();
        }
    }

}
