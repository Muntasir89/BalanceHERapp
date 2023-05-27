package com.example.balanceher.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.balanceher.Adapter.TodoAdapter;
import com.example.balanceher.Dialog.NoteInsertDia;
import com.example.balanceher.Interface.NoteDeleteInterface;
import com.example.balanceher.Interface.NoteInsUpdInterface;
import com.example.balanceher.MVVM.Model.Note;
import com.example.balanceher.MVVM.ViewModel.NoteViewModel;
import com.example.balanceher.databinding.ActivityTodoBinding;

import java.util.List;

public class TodoAct extends AppCompatActivity implements NoteInsUpdInterface, NoteDeleteInterface {
    ActivityTodoBinding binding;
    public static Boolean isAdd;
    NoteInsertDia noteInsertDia;
    private NoteViewModel noteViewModel;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        initRecycAndAdapter();

        noteViewModel.getNoteList().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        binding.addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddNote();
            }
        });
    }

    private void processAddNote() {
        isAdd = true;
        noteInsertDia = new NoteInsertDia(isAdd);
        noteInsertDia.show(getSupportFragmentManager(), "InsertData Dialog");
    }

    private void processUpdateNote() {
        isAdd = false;
        noteInsertDia = new NoteInsertDia(isAdd);
        noteInsertDia.show(getSupportFragmentManager(), "UpdateData Dialog");
    }

    private void initRecycAndAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        adapter = new TodoAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void getData(String title, String desc, long millisecons) {
        if (isAdd) {
            Note note = new Note(title, desc, millisecons);
            //note.setId(data.getIntExtra("id", 0));
            noteViewModel.insert(note);
        } else {
            //Update Note
        }
        Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDeleteNote(Note note) {
        noteViewModel.delete(note);
    }
}