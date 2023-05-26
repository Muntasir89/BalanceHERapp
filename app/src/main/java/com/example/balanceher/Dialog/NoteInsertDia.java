package com.example.balanceher.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.balanceher.Interface.NoteInsUpdInterface;
import com.example.balanceher.databinding.NoteInsertDiaLayBinding;


public class NoteInsertDia extends DialogFragment {
    //View
    NoteInsertDiaLayBinding binding;
    private static String titleStr, desStr, TimeStr;
    public static Boolean isAdd;

    NoteInsUpdInterface noteDiaInterface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        noteDiaInterface = (NoteInsUpdInterface) context;
    }

    public NoteInsertDia(Boolean isadd){
        isAdd = isadd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = requireActivity().getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.note_insert_dia_lay, null);

        binding = NoteInsertDiaLayBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(binding.getRoot());

        binding.imgClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                processAddNote();
            }
        });

        return dialogBuilder.create();
    }

    private void processAddNote() {
        titleStr = binding.titleET.getText().toString().trim();
        desStr = binding.descET.getText().toString().trim();
        if(TextUtils.isEmpty(titleStr) || TextUtils.isEmpty(desStr)){
            Toast.makeText(getContext(), "Some field is empty", Toast.LENGTH_SHORT).show();
        }else{
            noteDiaInterface.getData(titleStr, desStr);
            dismiss();
        }
    }


    public static Boolean getIsAdd() {
        return isAdd;
    }
}
